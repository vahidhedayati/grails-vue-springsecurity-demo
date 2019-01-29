import axios from 'axios'
import Auth from '@/services/Auth'

const instance = axios.create({
  baseURL: `http://localhost:8080/api/`
})

/*
      config.headers['Content-type']= [
        'application/json'
      ].join(' ')
 */

instance.interceptors.request.use((config, next) => {

    if (JSON.parse(localStorage.getItem('vuex')).user.token) {
      //console.log(">>VFF "+JSON.parse(localStorage.getItem('vuex')).user.token+"------------"+ JSON.stringify(localStorage));
      config.headers['Authorization'] = [
        'Bearer', JSON.parse(localStorage.getItem('vuex')).user.token
      ].join(' ')

    } else {
      //console.log(">>VFF2 "+JSON.parse(localStorage.getItem('vuex'))+"------------"+ JSON.stringify(localStorage));
      delete config.headers['Authorization']
    }
     //console.log("config >>>> "+JSON.parse(localStorage.getItem('vuex')).auth.isAuthenticated.roles+' --->' +JSON.parse(localStorage.getItem('vuex')).user+"---"+JSON.stringify(localStorage)+"");
    return config;

  }, function (error) {
    return Promise.reject(error);
  });


/**
 * Attempts to re-authenticate against backend spring security if token expired
 */
instance.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  const originalRequest = error.config;
  if (error.response.status === 401 && !originalRequest._retry) {
    originalRequest._retry = true;
    //const rToken = window.localStorage.getItem('rToken');
    const authUser = JSON.parse(window.localStorage.getItem('vuex'));
    const tok=authUser.auth.isAuthenticated.refresh_token;
    console.log('attempt to refresh token here -'+'http://localhost:8080/oauth/access_token?grant_type=refresh_token&refresh_token='+tok)
   // console.log('r token '+authUser.auth.isAuthenticated.refresh_token+'_____________-'+JSON.stringify(authUser.auth.isAuthenticated))
    //, {grant_type:'refresh_token',refresh_token:tok}
    return axios.post('http://localhost:8080/oauth/access_token?grant_type=refresh_token&refresh_token='+tok)
      .then(({data}) => {

        console.log('==got the following token back'+ data.access_token+"------------"+JSON.stringify(data)+' ')
       // console.log('==got the following refreshToken back'+data.refreshToken)
        //window.localStorage.setItem('token', data.token);
        window.localStorage.setItem('vuex.user.token', data.access_token);
       // window.localStorage.setItem('vuex.auth.isAuthenticated.refresh_token', data.refreshToken);
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + data.access_token;
        originalRequest.headers['Authorization'] = 'Bearer ' + data.access_token;
        return axios(originalRequest);
      });
  }

  return Promise.reject(error);
});

export default () => {
  return instance
}

import axios from 'axios'
import Auth from '@/services/Auth'

const instance = axios.create({
  baseURL: `http://localhost:8080/`
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


// Add a response interceptor
instance.interceptors.response.use((response) =>{
    return response;
  }, function (error) {
    return Promise.reject(error);
  });

export default () => {
  return instance
}

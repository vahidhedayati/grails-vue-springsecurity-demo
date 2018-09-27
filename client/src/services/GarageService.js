import Api from '@/services/Api'

export default {
  fetchName (component) {
    return Api().get(component )
    .catch((error) => {
        if (error.response) {
            console.log(error.response);
        } else if (error.request) {
            console.log(error.request);
        } else {
            console.log('Error', error.message);
        }
    });
  },
  create (component, params) {
    return Api().post(component, params)
    .catch((error) => {
        if (error.response) {
            console.log(error.response);
        } else if (error.request) {
            console.log(error.request);
        } else {
            console.log('Error', error.message);
        }
    });
  },
  update(component,params) {
    return Api().patch(component, params)
    .catch((error) => {
        if (error.response) {
            console.log(error.response);
        } else if (error.request) {
            console.log(error.request);
        } else {
            console.log('Error', error.message);
        }
    });
  },
  delete (component,id) {
    return Api().delete(component+'/' + id)
    .catch((error) => {
        if (error.response) {
            console.log(error.response);
        } else if (error.request) {
            console.log(error.request);
        } else {
            console.log('Error', error.message);
        }
    });
  }
}

import Vue from 'vue'
import Router from 'vue-router'
import Garage from '@/components/Garage'
import Login from '@/components/login/Login'
import calc from '@/components/example/calc'
import Logout from '@/pages/Logout'
import Home from '@/pages/Home'
import Profile from '@/pages/account/Profile'
import EditProfile from '@/pages/account/EditProfile'
Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: "Home",
      component: Home ,
      meta: { requiresAuth:false}
    },
    {
      path: '/calc',
      name: "calc",
      component: calc ,
      meta: { requiresAuth:false}
    },
    {
      path: '/logout',
      name: "Logout",
      component: Logout ,
      meta: { requiresAuth:false}
    },

    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      meta: { requiresAuth: true , roles:['ROLE_ADMIN', 'ROLE_DRIVER']},
      children: [
        {
          path: '/profile/edit',
          component: EditProfile,
          meta: { requiresAuth: true , roles:['ROLE_ADMIN', 'ROLE_DRIVER']}
        }
      ]
    },
    {
      path: '/garage',
      name: 'Garage', // <1>
      component: Garage ,
      meta: { requiresAuth: true , roles:['ROLE_ADMIN', 'ROLE_DRIVER']}
    },

    {
      path: '/login',
      name: 'Login',
      component: Login
    },
  ],
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
});
var findOne = function (haystack, arr) {
  return arr.some(function (v) {
    return haystack.indexOf(v) >= 0;
  });
};
router.beforeEach((to, from, next) => {
  const authUser = JSON.parse(window.localStorage.getItem('vuex'))
  if(!to.meta.requiresAuth && !to.meta.roles) {
  return next()
}
if(!authUser || !authUser.user.token) {
  return next({name:'Login'})
}

if (findOne(to.meta.roles,authUser.auth.isAuthenticated.roles)) {
  console.log(' yes >'+to.meta.roles+ " <> "+authUser.auth.isAuthenticated.roles);
  return next()
} else {
  console.log('NO'+to.meta.roles.includes(authUser.auth.isAuthenticated.roles)+' '+to.meta.roles+' >> '+authUser.auth.isAuthenticated.roles)
  return next({name:'Home'})
}
})

export default router


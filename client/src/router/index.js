import Vue from 'vue'
import Router from 'vue-router'
import Garage from '@/components/Garage'
import Examples from '@/components/Examples'
import Custom from '@/components/custom/Custom'
import Rental from '@/components/rental/Rental'
import Login from '@/components/login/Login'
import calc from '@/components/example/calculator/calc'
import testcalc from '@/components/example/calculator/test'
import Logout from '@/components/Logout'
import Cart from '@/components/shoppingCart/Cart'
import Home from '@/components/Home'
import Profile from '@/components/account/Profile'
import EditProfile from '@/components/account/EditProfile'
import Form from '@/components/petstore/Form'
import Main from '@/components/petstore/Main'
import Main2 from '@/components/petstore/Main2'
import Product from '@/components/petstore/Product'
import EditProduct from '@/components/petstore/EditProduct'
Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      props: true,
      name: "Home",
      component: Home ,
      meta: { requiresAuth:false}
    },
    {
      path: '/examples',
      props: true,
      name: "examples",
      component: Examples ,
      meta: { requiresAuth:false}
    },
    {
      path: '/calc',
      name: "calc",
      component: calc ,
      meta: { requiresAuth:false}
    },
    {
      path: '/testcalc',
      name: "testcalc",
      component: testcalc ,
      meta: { requiresAuth:false}
    },
    {
      path: '/shop',
      name: "shop",
      component: Cart ,
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
      path: '/rental',
      name: "rental",
      component: Rental ,
      props: true,
      meta: { requiresAuth: false}
    },
    {
      path: '/garage',
      name: 'Garage', // <1>
      component: Garage ,
      meta: { requiresAuth: true , roles:['ROLE_ADMIN', 'ROLE_DRIVER']}
    },
    {
      path: '/custom',
      name: 'custom', // <1>
      component: Custom ,
      meta: { requiresAuth: true , roles:['ROLE_ADMIN', 'ROLE_DRIVER']}
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/petstore',
      name: 'petstore',
      component: Main2,
      props: true,
    },
    {
      path: '/product/:id',
      name: 'Id',
      component: Product,
      props: true,
      children: [
        {
          path: 'edit',
          name: 'Edit',
          component: EditProduct,
          props: true
        }
      ]
    },
    {
      path: '/form',
      name: 'Form',
      component: Form,
      props: true
    },
    {
      path: '*',
      redirect:"/"
    }
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


<template>
  <div>
  <router-link :to="{name: 'Profile'}" >
    <button class="btn btn-xs btn-danger">Profile</button>
  </router-link>


    <router-link :to="{name: 'testcalc'}">
      <button class="btn btn-xs btn-success">Parent -> Calc</button>
    </router-link>

    <router-link :to="{name: 'calc'}">
      <button class="btn btn-xs btn-success">Calc</button>
    </router-link>

    <router-link :to="{name: 'shop'}">
      <button class="btn btn-xs btn-success">Shop</button>
    </router-link>


  <router-link v-show="!loggedIn" :to="{name: 'Login'}">

    <button class="btn btn-xs btn-primary">Login</button>
  </router-link>


  <router-link :to="{name: 'Garage'}">
    <button class="btn btn-xs btn-success">Garage</button>
  </router-link>

    <router-link :to="{name: 'custom'}">
      <button class="btn btn-xs btn-success">Custom</button>
    </router-link>


  <router-link v-show="loggedIn" :to="{name: 'Logout'}">
    <button class="btn btn-xs btn-success">Logout</button>
  </router-link>


  </div>
</template>
<script>
  export default {
    data() {
      return {
        loggedIn:false
      }
    },
    created () {
      this.loggedIn=((JSON.parse(localStorage.getItem('vuex')).user.token)?true:false)
    }
  }
</script>

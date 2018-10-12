<template>
  <div>
  <router-link :to="{name: 'Profile'}" >
    <button class="btn btn-danger">Profile</button>
  </router-link>


    <router-link :to="{name: 'testcalc'}">
      <button class="btn btn-success">Parent -> Calc</button>
    </router-link>

    <router-link :to="{name: 'calc'}">
      <button class="btn btn-success">Calc</button>
    </router-link>

    <router-link :to="{name: 'shop'}">
      <button class="btn btn-success">Shop</button>
    </router-link>


  <router-link :to="{name: 'Login'}">

    <button class="btn btn-primary">Login</button>
  </router-link>


  <router-link :to="{name: 'Garage'}">
    <button class="btn btn-success">Garage</button>
  </router-link>

    <router-link :to="{name: 'custom'}">
      <button class="btn btn-success">Custom</button>
    </router-link>


  <router-link :to="{name: 'Logout'}">
    <button class="btn btn-success">Logout</button>
  </router-link>


  </div>
</template>
<script>

</script>

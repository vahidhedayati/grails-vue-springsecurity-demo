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


    <router-link  @click.native="++counter" :to="{ name: 'rental', params: {showSearch: false, counter:counter } }">
      <button  class="btn btn-xs btn-success">Rent a vehicle  {{counter}}</button>
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
        loggedIn:false,
        counter:0,
        lastPath:'',
        lastCounter:0,
      }
    },
    created () {
      this.loggedIn=((JSON.parse(localStorage.getItem('vuex')).user.token)?true:false)
    },

  beforeUpdate: function (from, to, next) {
    if (this.$route.path==='/rental') {
      if (this.$route.path==this.lastPath) {
        console.log('new after', this.$route.path);
        this.$eventHub.$emit('rentalcounter',this.counter);
        //lastCounter++
        //
        //this.$root.$emit('rental-counter',this.counter);
        // this.$bus.$emit('rentalCounter', this.counter)
      } else {
        //this.$eventHub.$off('rentalcounter');
      }
    } else {
      this.$eventHub.$off('rentalcounter');
    }

    this.lastPath=this.$route.path

    //this.showSearch=this.searchDetails;
  },	//#
  beforeRouteUpdate(to, from, next) {
    console.log('aaaaaaaaabefore', this.$route.path);
    next();
  },
    methods: {
      emitCounter() {
        console.log('emitting')
        //this.$emit("counter", this.counter);
      }
    }
  }
</script>

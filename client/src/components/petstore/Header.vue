<template>
  <div>

<header>
  <div class="navbar navbar-default">
    <div class="navbar-header">
      <h1><router-link :to="{name: 'Home'}">{{ sitename }}</router-link></h1>
    </div>
    <div class="nav navbar-nav navbar-right cart">
      <div v-if="!mySession">
        <button type="button" class="btn btn-default btn-lg" v-on:click="signIn">
          Sign In
        </button>
      </div>
      <div v-else>
        <button type="button" class="btn btn-default btn-lg" v-on:click="signOut">
          <img class="photo" :src="mySession.photoURL" />
          Sign Out
        </button>
      </div>
    </div>
    <div class="nav navbar-nav navbar-right cart">
        <router-link active-class="active" tag="button" class="btn btn-default btn-lg" :to="{name: 'Form'}">
        <span class="glyphicon glyphicon-shopping-cart">{{ cartItemCount}}</span> Checkout
        </router-link>
      </button>
    </div>
  </div>
</header>
  </div>
</template>

<script>
export default {
  name: 'my-header',
  data () {
    return {
      loggedIn:false,
      sitename: "Vue.js Pet Depot",
    }
  },
  props: ['cartItemCount'],
  beforeCreate() {
    this.loggedIn=((JSON.parse(localStorage.getItem('vuex')).user.token)?true:false)
    console.log('logged in '+this.loggedIn)
    //firebase.auth().onAuthStateChanged((user)=> {
      this.$store.commit('SET_SESSION', [this.loggedIn? (JSON.parse(localStorage.getItem('vuex')).user) : false ])
    //});
  },
  methods: {
    showCheckout() {
      this.$router.push({name: 'Form'});
    },
    signIn() {
      this.$router.push({name: 'Login'})
    },
    signOut() {
      this.$store.dispatch('user/userLogged',new Boolean(false));
      localStorage.setItem('id_token', null);
      this.$store.dispatch('auth/login',  null);
      this.$store.dispatch('user/setToken', null);


      this.$router.push({name: 'Home'})
    }
  },
  computed: {
    mySession() {
      return ((JSON.parse(localStorage.getItem('vuex')).user.token)?true:false)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  a {
    text-decoration: none;
    color: black;
  }

  .photo {

    width: 25px;
    height: 25px;
  }

  .router-link-exact-active {
    color: black;
  }
</style>

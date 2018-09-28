<template>

  <v-container fill-height>
    <v-layout row wrap align-center>
      <v-flex xs12 sm8 offset-sm2 align-center justify-center>
        <v-card class="elevation-12">
          <v-toolbar dark color="primary">
            <v-toolbar-title>Sign In</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-container>
      <form  @submit.prevent="processForm">
        <div class="ui stacked segment">
          <div class="field">
            <div class="ui left icon input">
              <i class="user icon"></i>
              <input type="text" name="username" placeholder="username"  v-model="username">
            </div>
          </div>
          <div class="field">
            <div class="ui left icon input">
              <i class="lock icon"></i>
              <input type="password" name="password" placeholder="Password" v-model="password">
            </div>
          </div>
          <div class="field">
            <div class="ui left icon input">
              <label><input v-model="data.rememberMe" type="checkbox" /> Remember Me</label>
            </div>
          </div>
          <button type="submit" class="button is-danger">Login</button>

        </div>

        <div class="ui error message"></div>

      </form>

            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>

</template>

<script>
import UsersService from '@/services/UsersService'
import ProfileService from '@/services/ProfileService'
export default {
  data () {
    return {
      context: 'login context',
      username:'',
      password:'',
      data: {
        rememberMe: false,
        fetchUser: true
      },
      error: null
    }
  },
  computed: {
    isAuthenticated: function () {
      return this.$store.getters.isAuthenticated
    }
  },
  methods: {
    processForm: function () {
      console.log({name: this.username, password: this.password});
      UsersService.login({
        username:  this.username,
        password:this.password
      })
        .then((response) => {
          if (response.data) {
            console.log('Login >>>>>>>>', response.data)//.access_token);
            //localStorage.setItem('id_token',  response.access_token)
            this.$store.dispatch('auth/login',  response.data);
            this.$store.dispatch('user/setToken', response.data.access_token);
            this.$store.dispatch('user/userLogged',new Boolean(true));

            this.getProfile(response.data.id);

            if(response.data.roles[0] === 'ROLE_ADMIN') {
              this.$router.push({name: 'Admin'})
            } else if(response.data.roles[0] === 'ROLE_DRIVER') {
              this.$router.push({name: 'Garage'})
            } else {
              this.$router.push({name: 'Home'})
            }
        }
      })
    },
    getProfile (userId) {
      console.log(userId)
      ProfileService.fetchProfile(userId)
        .then((res) => {
        console.log("PROFILE" +res.data)
      this.$store.dispatch('user/setProfile', res.data)
      //this.$router.push({name: 'Home'})
    })
    },
    authenticate: function (provider) {
      this.$auth.authenticate(provider).then(function () {
        // Execute application logic after successful social authentication
      })
    }
  }
}

/*



        this.$auth.login({
             username: this.data.body.username,
             password: this.data.body.password
           })
           .then((response) => {
             console.log('Login >>>', response)
             this.$store.dispatch('auth/login',  response)
             this.$store.dispatch('user/setToken', response.data.token)
             this.$store.dispatch('user/userLogged', true)
             //this.getProfile(response.data.set._id);
             //console.log(response.data.set.role+"------------------");

      if(response.data.roles[0] === 'Admin') {
        this.$router.push({name: 'Admin'})
      } else if(response.data.roles[0] === 'ROLE_DRIVER') {
        this.$router.push({name: 'Driver'})
      } else {
        this.$router.push({name: 'User'})
      }
         })


  }
 */
</script>

<style lang="scss">
</style>

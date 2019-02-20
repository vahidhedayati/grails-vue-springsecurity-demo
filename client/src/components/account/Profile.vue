<template>
  <div class="">
    <app-header></app-header>
    <h4>Profilo</h4>
    <p>{{profile.name}}</p>
    <p>{{profile.username}}</p>
    <p>{{profile.roles}}</p>
    <router-view></router-view>
    <router-link to="/profile/edit" class="ui animated green button" tabindex="0">
      <div class="visible content">Edit</div>
      <div class="hidden content">
        <i class="right arrow icon"></i>
      </div>
    </router-link>
    <div class="ui animated red button" tabindex="0" @click="deleteProfile(profile.id)">
      <div class="visible content">Delete</div>
      <div class="hidden content">
        <i class="right arrow icon"></i>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileService from '@/services/ProfileService'
import AppHeader from '../AppHeader'
export default {
  components: {
    AppHeader
  },
  computed: {
    profile () {
      return this.$store.state.user.profile
    }
  },
  methods: {
    deleteProfile (profileId) {
      console.log('delete profile', profileId)
      ProfileService.deleteProfile(profileId)
      .then((res) => {
        console.log(res.data.set)
        this.$store.dispatch('user/setProfile', '')
        this.$router.push({name: 'Home'})
      })
    }
  }
}
</script>

<style lang="scss">
</style>

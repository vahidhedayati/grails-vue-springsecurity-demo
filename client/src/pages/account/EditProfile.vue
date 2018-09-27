<template>
  <div class="">
      <h4>Profile Edit</h4>
      <input type="text" name="firstname" value="" placeholder="Firstname" v-model="firstname">
      <input type="text" name="firstname" value="" placeholder="Lastname" v-model="lastname">
      <button type="button" name="button" @click="editProfile">Create</button>
  </div>
</template>

<script>
import ProfileService from '@/services/ProfileService'
export default {
  data () {
    return {
      firstname: this.firstname,
      lastname: this.lastname
    }
  },
  computed: {
    profile () {
      return this.$store.state.user.profile
    }
  },
  methods: {
    editProfile () {
      console.log(this.profile._id);
      const profile = {
        id: this.profile._id,
        firstname: this.firstname,
        lastname: this.lastname
      }
      ProfileService.updateProfile(profile)
      .then((res) => {

      })

    }
  }
}
</script>

<style lang="scss">
</style>

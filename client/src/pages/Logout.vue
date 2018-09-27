<template>
  <div>Logout</div>
</template>
<script>
  import UsersService from '@/services/UsersService'
  export default {
    data (){
      return {
      }
    },
    created:function(){
      UsersService.logout();
      this.$router.push({name: 'Home'})
    }
  }
</script>

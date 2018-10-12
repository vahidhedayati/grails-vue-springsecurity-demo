<template id="app-header-template">
  <div class="container">
    <div class="jumbotron">
      <img src="../assets/logo.png">
      <h1>Welcome to the Garage</h1>
      <Home/>
    </div>
  </div>
</template>

<script>
  import Home from '@/components/Home'

export default {
  name: 'app-header',
  components: {
    Home
  }
}
</script>

<style>
</style>

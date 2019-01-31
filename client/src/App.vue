<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  watch: {
    $route(to, from) {
      console.log('after', this.$route.path);
    }
  },
  beforeRouteUpdate(to, from, next) {
    console.log('before', this.$route.path);
    next();
  }
}
</script>

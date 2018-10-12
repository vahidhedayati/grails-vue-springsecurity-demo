
<template>
  <div id="test">
    <app-header></app-header>
    <p>This is the parent page calling calc object when you press = the calculator results are passed to parent page</p>
    <p>Calc value: {{result}}</p>
    <calc class="reduceZoom" v-model="result"/>
  </div>
</template>

<script>
import AppHeader from '../../AppHeader'
import Calc from './calc'
export default {
  components: {
    AppHeader,
    Calc
  },
  data: function () {
    return {
      result:'',
    }
  },

  methods: {


  }
}
</script>
<style>
  .reduceZoom {
    zoom:25.50%;

  }
</style>

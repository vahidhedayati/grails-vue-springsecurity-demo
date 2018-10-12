<template id="fulltable-template" xmlns="http://www.w3.org/1999/xhtml">
  <table class="table">
    <thead class="thead-inverse">
      <tr>
        <th @click="sort('id')">ID</th>
        <th @click="sort('contractName')">contractName</th>
        <th @click="sort('vehicleName')">VehicleName</th>
        <th @click="sort('makeName')">Make</th>
        <th @click="sort('modelName')">Model</th>
        <th @click="sort('driverName')">Driver</th>
        <th>Action</th>
      </tr>
    </thead> <!--1-->
      <table-row v-for="vehicle in sortedCats"
                 :item="vehicle" :key="vehicle.id"
                 :makes="makes"
                 :models="models"
                 :reload="reload"
                 :drivers="drivers"></table-row> <!--2-->
  </table>
</template>

<script>
import TableRow from './TableRow.vue' // <3>

export default {
   props: ['vehicles', 'reload', 'makes', 'models', 'drivers'],
  components: { // <3>
    TableRow
  },
  data: function () {
  return {
    currentSort:'contractName',
    currentSortDir:'asc'
  }
},
  methods: {
    sort: function (s) {
      //if s == current sort, reverse
      if (s === this.currentSort) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
      }
      this.currentSort = s;
    },
  },
  computed:{
    sortedCats:function() {
      return this.vehicles.sort((a,b) => {
        let modifier = 1;
      if(this.currentSortDir === 'desc') modifier = -1;
      if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
      if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
      return 0;
    });
    }
  }
}

</script>

<style>
  /* Add custom rules here */
</style>

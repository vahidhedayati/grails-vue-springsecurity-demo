<template id="fulltable-template" xmlns="http://www.w3.org/1999/xhtml">
  <table class="table">
    <thead class="thead-inverse">
      <tr>
        <th @click="sort('id')" :class="{active: this.column === 'id'}">
          ID

          <span :class="{blank:this.column != 'id',
          arrow: (this.column === 'id'),
          asc: (this.column === 'id' && this.currentSortDir==='asc'),
           desc: this.column === 'id' && this.currentSortDir==='desc'}">
          </span>

        </th>
        <th @click="sort('contractName')" :class="{active: this.column === 'contractName'}">
          contractName
          <span :class="{blank:(this.column != 'contractName'),
          arrow: (this.column === 'contractName'),
          asc: (this.column === 'contractName' && this.currentSortDir==='asc'),
           desc: (this.column ==='contractName' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('vehicleName')" :class="{active: this.column === 'vehicleName'}">
          VehicleName
          <span :class="{blank:(this.column != 'vehicleName'),
          arrow: (this.column === 'vehicleName'),
          asc: (this.column === 'vehicleName' && this.currentSortDir==='asc'),
           desc: (this.column ==='vehicleName' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('makeName')"  :class="{active: this.column === 'makeName'}">
          Make
          <span :class="{blank:(this.column != 'makeName'),
          arrow: (this.column === 'makeName'),
          asc: (this.column === 'makeName' && this.currentSortDir==='asc'),
           desc: (this.column ==='makeName' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('modelName')" :class="{active: this.column =='modelName'}">
          Model
          <span :class="{blank:(this.column != 'modelName'),
          arrow: (this.column === 'modelName'),
          asc: (this.column === 'modelName' && this.currentSortDir==='asc'),
           desc: (this.column ==='modelName' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('driverName')" :class="{active: this.column === 'driverName'}">
          Driver
          <span :class="{blank:(this.column != 'driverName'),
          arrow: (this.column === 'driverName'),
          asc: (this.column === 'driverName' && this.currentSortDir==='asc'),
           desc: (this.column ==='driverName' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th>Action</th>
      </tr>
    </thead> <!--1-->
      <!-- sortedCats replaces vehicles and is only sorting by current paginated dataset -->
      <table-row v-for="vehicle in vehicles"
                 :item="vehicle" :key="vehicle.id"
                 :makes="makes"
                 :models="models"
                 v-bind="{fetchVehicles}"
                 :drivers="drivers"></table-row> <!--2-->
  </table>
</template>

<script>
import TableRow from './TableRow.vue' // <3>

export default {
   props: ['vehicles', 'reload', 'makes', 'models', 'drivers','fetchVehicles','sortSearch'],
  components: { // <3>
    TableRow
  },
  data: function () {
  return {
    currentSort:'contractName',
    currentSortDir:'asc',
    column:'id',
    activeColumn:'id',
    currentStyle:'',
  }
},
  methods: {
    sort: function (s) {
      this.column=s;
      this.activeColumn=s;
      //if s == current sort, reverse
      if (s === this.currentSort) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';

      }
      this.currentSort = s;

      //This is full on search and sortedSearch sits in the parent page Custom.vue
      this.sortSearch(s,this.currentSortDir)

    }
  },
  computed:{

     //This is now ignored this was sorting based on paginated data
    //simply replace vehicle in vehicles to vehicle in sortedCats above line 14/15
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
  th.active .arrow {
    opacity: 1;
  }

  .arrow {
    display: inline-block;
    vertical-align: middle;
    width: 0;
    height: 0;
    margin-left: 5px;
    opacity: 0.66;
  }

  .arrow.asc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #42b983;
  }

  .arrow.desc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #42b983;
  }


  blank {

  }
</style>

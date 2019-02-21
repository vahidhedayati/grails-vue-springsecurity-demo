<template id="fulltable-template" xmlns="http://www.w3.org/1999/xhtml">
  <div>
  <table class="table">
    <thead class="thead-inverse">
      <tr>
        <th>Make</th>
        <th>Model</th>
        <th>Cost Per day</th>
        <th>Deposit</th>
        <th>Stock</th>
        <th>Rating</th>
        <th>Registration Plate</th>
        <th>Action</th>
      </tr>
    </thead>
      <table-row v-for="vehicle in vehicles"
                 :actualItem="vehicle" :key="vehicle.id"
                 :makes="makes"
                 :updatedResults="currentVehicle"
                 :models="models"
                 :reload="reloadVehicles"
                 v-model="result"
                 ></table-row>
  </table>
   Current results from vehicle selection =  {{result}}
  <hire-modal  v-bind="{result,rentalContract,reloadVehicles}"
               :show="result && result.id"
               v-model="updatedResults"
               @close="result = null"></hire-modal>
  </div>
</template>

<script>
import TableRow from './RentalTableRow.vue'
import HireModal from './HireModal';
export default {
   props: ['vehicles', 'reloadVehicles', 'makes', 'models'],
  data: function () {
    return {
      result:{},
      showModal:false,
      updatedResults:null,
      rentalContract:{driver:{id:''},username:'', name:'',password:'',vehicle:{id:''}, fromDate:'',toDate:'',fromDate1:'',toDate1:''},
    }
  },
  computed: {
    currentVehicle() {
      if (this.updatedResults) {
        var a = this.updatedResults;
        console.log('returning updated vehicle from table '+JSON.stringify(a))
        this.updatedResults = null;
        return a
      } else {
        console.log('returning normal vehicle from table')
        return this.vehicle
      }
    }
  },
  components: {
    TableRow,
    HireModal,
  }
}
</script>

<style>
</style>

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
    <!--  :updatedResults="currentVehicle" -->

      <table-row v-for="vehicle in vehicles"
                 :actualItem="vehicle" :key="vehicle.id"
                 :makes="makes"
                 :models="models"
                 :reload="reloadVehicles"
                 v-model="result"
                 ></table-row>
  </table>
   Current results from vehicle selection =  {{result}}
    <!-- v-model="updatedResults"  no longer used-->
  <hire-modal  v-bind="{result,rentalContract,reloadVehicles}"
               :show="result && result.id"

               @update-vehicles="updatedVehicleContent"
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
     //no longer user
     /*
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
    */
  },
  methods: {
     //replaces currentVehicles work
    updatedVehicleContent: function (vehicle) {
      //This is actually passed in by HireModal.vue - when the user saves the save action returns
      // actual vehicle object this is now -re-emitted which the parent page
      // Rental.vue picks up this from custom-table tag and passes to another function
      //which that then passes to vuex cache to update underlying vehicle with this new one
      this.$emit('update-vehicles', vehicle);
    },
  },
  components: {
    TableRow,
    HireModal,
  }
}
</script>

<style>
</style>

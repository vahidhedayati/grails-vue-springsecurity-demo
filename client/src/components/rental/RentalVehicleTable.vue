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
                 :item="vehicle" :key="vehicle.id"
                 :makes="makes"
                 :models="models"
                 :reload="reload"
                 v-model="result"
                 ></table-row>
  </table>
    {{result}}
  <hire-modal  v-bind="{result,rentalContract}"
               :show="result && result.id"

               @close="result = null"></hire-modal>
  </div>
</template>

<script>
import TableRow from './RentalTableRow.vue'
import HireModal from './HireModal';
export default {
   props: ['vehicles', 'reload', 'makes', 'models'],
  data: function () {
    return {
      result:{},
      showModal:false,
      rentalContract:{driver:{id:''},username:'', name:'',password:'',vehicle:{id:''}, fromDate:'',toDate:'',fromDate1:'',toDate1:''},
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

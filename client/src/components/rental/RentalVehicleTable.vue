<template id="fulltable-template" xmlns="http://www.w3.org/1999/xhtml">
  <table class="table">
    <thead class="thead-inverse">
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Make</th>
        <th>Model</th>
        <th>Cost Per day</th>
        <th>Deposit</th>
        <th>Stock</th>
        <th>Action</th>
      </tr>
    </thead>
      <table-row v-for="vehicle in vehicles"
                 :item="vehicle" :key="vehicle.id"
                 :makes="makes"
                 :models="models"
                 :reload="reload"
                 ></table-row>
  </table>
</template>

<script>
import TableRow from './RentalTableRow.vue'

export default {
   props: ['vehicles', 'reload', 'makes', 'models'],
  components: {
    TableRow
  }
}
</script>

<style>
</style>

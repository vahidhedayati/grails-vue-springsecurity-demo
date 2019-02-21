<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>{{ actualItem.id }}</td>
      <td>{{ actualItem.contractName }}</td>
      <td>{{ actualItem.vehicleName }}</td>
      <td>{{ actualItem.makeName }}</td>
      <td>{{ actualItem.modelName }}</td>
      <td>
      <span class="hidden">{{ actualItem.driverId}}</span>
        <span>

        {{ actualItem.driverName }}
          </span>
      </td>
      <td>{{ actualItem.fromDate | shortMoment() }}</td>
      <td>{{ actualItem.toDate | shortMoment() }}</td>

      <td>
        <span v-if="actualItem.returnDate">
        Returned {{ actualItem.returnDate | shortMoment() }}
        </span>
        <span v-else>
           <button v-on:click="returnVehicle(actualItem)">Return hire vehicle</button>
        </span>
      </td>


    </tr>

</template>

<script>
//This is needed for the select component to work
import FieldSelect from '../../form/FieldSelect'
import GarageService from '@/services/GarageService'
import moment from 'moment';
import VueMoment from 'vue-moment'
export default {
    //You must declare what is being passed in otherwise they wont work..
   props: ['actualItem', 'makes', 'models', 'drivers','reload'],
   data () {
        return {
          response: [],
          errors: [],
          vehicle:{},
          serverURL: process.env.SERVER_URL,
          showForm: false,
          retrievedVehicle: {}

        }
      },
      //This is needed for the select component to work
      components: {
          FieldSelect,
        VueMoment,
        moment
        },
      filters: {
        moment: function (date) {
          return moment(date).format('MMMM Do YYYY, h:mm:ss a');
        },
        shortMoment: function (date) {
          return moment(date).format('DD MMM YYYY');
        }

      },
       methods: {
         moment: function () {
           return moment();
         },

         returnVehicle() {
           this.updateValue(this.actualItem );
         },
         updateValue: function (value) {
           this.$emit('input', value);
         },

     }

}

</script>

<!-- Per Component Custom CSS Rules -->
<style>
  /* Add custom rules here */
</style>

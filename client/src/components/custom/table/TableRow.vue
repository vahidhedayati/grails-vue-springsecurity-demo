<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>{{ item.id }}</td>
      <td>{{ item.contractName }}</td>
      <td>{{ item.vehicleName }}</td>
      <td>{{ item.makeName }}</td>
      <td>{{ item.modelName }}</td>
      <td>
      <span class="hidden">{{ item.driverId}}</span>
        <span>

        {{ item.driverName }}
          </span>
      </td>
      <td>{{ item.fromDate | shortMoment() }}</td>
      <td>{{ item.toDate | shortMoment() }}</td>

      <td>
        <span v-if="item.returnDate">
        Returned {{ item.returnDate | shortMoment() }}
        </span>
        <span v-else>
           <button v-on:click="returnVehicle(item)">Return hire vehicle</button>
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
   props: ['item', 'makes', 'models', 'drivers','reload'],
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
           this.updateValue(this.item );
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

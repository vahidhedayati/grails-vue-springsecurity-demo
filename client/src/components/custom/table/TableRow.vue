<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>{{ currentVehicle.id }}</td>
      <td>{{ currentVehicle.contractName }}</td>
      <td>{{ currentVehicle.vehicleName }}</td>
      <td>{{ currentVehicle.makeName }}</td>
      <td>{{ currentVehicle.modelName }}</td>
      <td>
      <span class="hidden">{{ currentVehicle.driverId}}</span>
        <span>

        {{ currentVehicle.driverName }}
          </span>
      </td>
      <td>{{ currentVehicle.fromDate | shortMoment() }}</td>
      <td>{{ currentVehicle.toDate | shortMoment() }}</td>

      <td>
        <span v-if="currentVehicle.returnDate">
        Returned {{ currentVehicle.returnDate | shortMoment() }}
        </span>
        <span v-else>
           <button v-on:click="returnVehicle(currentVehicle)">Return hire vehicle</button>
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
   props: ['actualItem', 'makes', 'models', 'drivers','reload','updatedResults'],
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
  computed: {
    currentVehicle() {
      if (this.updatedResults) {
        console.log('returning updated TABLE ROW ' + JSON.stringify(this.updatedResults))
        if (this.updatedResults.id === this.actualItem.id) {
          return this.updatedResults;
        } else {
          return this.actualItem
        }
      } else {
        console.log('returning normal VEH from row')
        return this.actualItem
      }
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

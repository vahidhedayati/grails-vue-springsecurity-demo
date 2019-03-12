<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">
    <tr>
      <td>{{ currentCountry.id }}</td>
      <td>{{ currentCountry.name }}</td>
      <td>{{ currentCountry.code }}</td>
      <td>{{ currentCountry.updateUser.username }}</td>
      <td>  {{ currentCountry.lastUpdated | shortMoment() }}</td>
      <td>
          <button v-on:click="editCounty(currentCountry)">Edit</button>
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
    currentCountry() {
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

         editCounty() {
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


<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">
    <tr>
      <td>{{ country.id }}</td>
      <td>
          <span v-if="showForm">
              <input  v-model="updatedCountry.name">
          </span>
          <span v-else>
              {{ country.name }}
          </span v-else>
      </td>
      <td>
          <span v-if="showForm">
                <input  v-model="updatedCountry.code">
          </span>
          <span v-else>
              {{ country.code }}
            </span v-else>
       </td>
      <td>{{ country.updateUser.username }}</td>
      <td>  {{ country.lastUpdated | shortMoment() }}
      </td>
      <td>
        <span v-if="showForm">
            <button v-on:click="save(updatedCountry)">Save</button>
        </span>
        <span v-else>
          <button v-on:click="editCounty(country)">Edit</button>
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
   props: ['country', 'makes', 'models', 'drivers','reload','updatedResults'],
   data () {
        return {
          response: [],
          errors: [],
          updatedCountry:{},
          serverURL: process.env.SERVER_URL,
          showForm: false,
          retrievedVehicle: {}

        }
      },
  computed: {
    
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
          this.showForm=true;
          this.updatedCountry=this.country;
         },
         updateValue: function (value) {
           this.$emit('input', value);
         },
         save() {
           const newName = this.updatedCountry;

           console.log('new Name =  '+JSON.stringify(newName))
           return GarageService.createRootNoCatch('guest/countries', newName)
             .then((res) => {
             if (res) {
                console.log('RES: '+JSON.stringify(res));
             }
             if (res.data) {
               this.showForm=false;
               console.log('resData '+JSON.stringify(res.data))
               this.$emit('country-update', res.data);
             }
           }).catch((error) => {
              if (error.response) {
                   this.$emit('country-errors', error.response.data.error);

            } else if ( error.request) {
              console.log("dddd"+error.request);
            } else {
              console.log('Error', error.message);
            }
          });
         }
     }
}

</script>

<!-- Per Component Custom CSS Rules -->
<style>
  /* Add custom rules here */
</style>


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
      <td class="dropdown">
        <span v-if="showForm">
            <button v-on:click="save(updatedCountry)">Save</button>
        </span>
        <span v-else>
          <button v-on:click="editCounty(country)">Edit</button>
      <vue-dropdown :config="config"
                    @setSelectedOption="actionDropDown($event,country.id)"
      ></vue-dropdown>
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
import VueDropdown from 'vue-dynamic-dropdown'
export default {
    //You must declare what is being passed in otherwise they wont work..
   props: ['country', 'makes', 'models', 'drivers','reload','updatedResults'],
   data () {
        return {
          response: [],
          errors: [],
          showCountry:null,
          updatedCountry:{},
          serverURL: process.env.SERVER_URL,
          showForm: false,
          retrievedVehicle: {},
          showDialog:false,
          config: {
            options: [
              {
                value: "display"
              },
              {
                value: "delete"
              },
            ],
            prefix: "",
            backgroundColor: "transparent"
          }

        }
      },
  computed: {
    
  },
      //This is needed for the select component to work
      components: {
          FieldSelect,
        VueMoment,
        moment,
        VueDropdown
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
         /**
          * This controls the drop down functionality
          * @param event
          * @param id
          * @returns {boolean}
          */
         actionDropDown(event,id) {
           console.log(' jj '+JSON.stringify(event)+' --- '+id)

           if (event.value==='display') {
                console.log('display '+id)
             return GarageService.fetchRoot('guest/countries/'+id)
               .then((res) => {
               if (res) {
                 console.log('RES: '+JSON.stringify(res));
               }
               if (res.data) {
               this.showForm=false;
               console.log('show resData '+JSON.stringify(res.data))
               //this.$emit('country-update', res.data);
               showCountry=res.data;
               showDialog=true
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


           if (event.value==='delete') {
             if (confirm('Delete record?')) {
               console.log('delete '+id)

               return GarageService.deleteNoCatch('guest/countries', id)
                 .then((res) => {
                 if (res) {
                   console.log('RES: '+JSON.stringify(res));
                 }
                 if (res.data) {
                 // we need to reload page
                 this.$emit('refresh-list');
                 }
             }).catch((error) => {
                 if (error.response) {
                   console.log('errors '+ JSON.stringify(error.response))
                 //this.$emit('country-errors', error.response.data.error);

               } else if ( error.request) {
                 console.log("dddd"+error.request);
               } else {
                 console.log('Error', error.message);
               }
             });


             } else {
               console.log('denied')
             }

           }


         },
         updateValue: function (value) {
           this.$emit('input', value);
         },
         save() {
           const newName = this.updatedCountry;
           console.log('new Name =  '+JSON.stringify(newName))
           return GarageService.patchRootNoCatch('guest/countries/'+newName.id, newName)
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
  .dropdown-label.text {
    font-size:0.8em;
  }
  .dropdown-label-container {
    margin-top: -30px !important;
  }
  .dropdown-label-container .angle-down {
    margin-top: -10px !important;
    margin-left: -10px !important;
    background-color: orange !important;
  }
  .dropdown {
    margin-right: 50px;
    float:right !important;
    display: inline-block !important;
    width:30px !important;
    font-size:0.8em;

  }
  .dropdown .options {
    width:80px !important;
    font-size:0.8em;
    background: red;
  }
  /* Add custom rules here */
</style>

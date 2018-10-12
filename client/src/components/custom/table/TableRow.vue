<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>
      {{ item.id }}
      </td>
      <td>
        {{ item.contractName }}
      </td>
      <td>

            {{ item.vehicleName }}

      </td>
      <td>

            {{ item.makeName }}

      </td>
      <td>

           {{ item.modelName }}

      </td>
      <td>

          {{ item.driverName }}


      </td>

      <td>
        {{item.returnDate ? (item.returnDate| moment("dddd, MMMM Do YYYY")) : '' }}
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
       methods: {
         edit () {
         //These must be delcated in the data block above the this items or must be the direct object queried
           return GarageService.fetchName('vehicle/'+this.item.id)
             .then((res) => {
             if (res) {
               if (res.data) {
                 //this.vehicles.push(res.data) // <2>
                 //this.vehicle = {name: '', make: null, model: null, driver: null}
                 this.showForm=true;
                 this.vehicle=this.item;
                 console.log('json '+JSON.stringify(res.data));
                 this.retrievedVehicle=res.data
               }
             }
           });


        },
        save() {
          const newName = this.vehicle;
          //console.log( {id:newName.id,name:newName.name,make:{id: newName.make.id}, model:{id: newName.model.id}, driver:{id: newName.driver.id}} +" <<>>"+JSON.stringify(newName)+"---------");
          console.log(' '+JSON.stringify(newName))
          return GarageService.update('vehicle/'+this.item.id, newName)
            .then((res) => {
            if (res) {
             // if (res.data) {
               // this.vehicles.push(res.data) // <2>
               // this.vehicle = {name: '', make: null, model: null, driver: null}
                this.showForm=false;
                //This is passed through from Garage.vue as its actual function as a variable called reload
                //The reload is passed from VehicleTable to child vue page TableRow as reload
                //When triggered here - it reloads all the vehicles in parent parent file Garage.vue
                this.fetchVehicles(0);

              //}
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

<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>
      {{ actualItem.id }}
      </td>
      <td>
        <span v-if="showForm">
          <input  v-model="vehicle.name">
        </span>
        <span v-else>
            {{ actualItem.name }}
        </span>
      </td>
      <td>
       <span v-if="showForm">
          <field-select v-model="vehicle.make" :actualItem="actualItem.make.id" :field="'Make'" :values="makes"></field-select>
        </span>
        <span v-else>
            {{ actualItem.make.name }}
        </span>
      </td>
      <td>
        <span v-if="showForm">
          <field-select v-model="vehicle.model" :field="'Model'" :actualItem="actualItem.model.id" :values="models"></field-select>
        </span>
        <span v-else>
           {{ actualItem.model.name }}
        </span>
      </td>
      <td>
        <span v-if="showForm">
          <field-select v-model="vehicle.driver" :actualItem="actualItem.driver.id" :field="'Driver'" :values="drivers"></field-select>
        </span>
        <span v-else>
          {{ actualItem.driver.name }}
        </span>

      </td>
      <td>
      <form action="javascript:void(0);">
       <span v-if="showForm">
            <button v-on:click="save(actualItem)">Save</button>
        </span>
        <span v-else>
            <button v-on:click="edit(actualItem)">Edit</button>
        </span>
        </form>
      </td>
    </tr>

</template>

<script>
//This is needed for the select component to work
import FieldSelect from '../form/FieldSelect'
import GarageService from '@/services/GarageService'
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
          FieldSelect
        },
       methods: {
         edit () {
         //These must be delcated in the data block above the this actualItems or must be the direct object queried
           return GarageService.fetchName('vehicle/'+this.actualItem.id)
             .then((res) => {
             if (res) {
               if (res.data) {
                 //this.vehicles.push(res.data) // <2>
                 //this.vehicle = {name: '', make: null, model: null, driver: null}
                 this.showForm=true;
                 this.vehicle=this.actualItem;
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
          return GarageService.update('vehicle/'+this.actualItem.id, newName)
            .then((res) => {
            if (res) {
             // if (res.data) {
               // this.vehicles.push(res.data) // <2>
               // this.vehicle = {name: '', make: null, model: null, driver: null}
                this.showForm=false;
                //This is passed through from Garage.vue as its actual function as a variable called reload
                //The reload is passed from VehicleTable to child vue page TableRow as reload
                //When triggered here - it reloads all the vehicles in parent parent file Garage.vue
                this.reload;

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

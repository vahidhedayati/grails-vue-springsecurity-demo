<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>
      {{ currentItem.id }}
      </td>
      <td>
        <span v-if="showForm">
          <input  v-model="vehicle.name">
        </span>
        <span v-else>
            {{ currentItem.name }}
        </span>
      </td>
      <td>
       <span v-if="showForm">
          <field-select v-model="vehicle.make" :actualItem="currentItem.make.id" :field="'Make'" :values="makes"></field-select>
        </span>
        <span v-else>
            {{ currentItem.make.name }}
        </span>
      </td>
      <td>
        <span v-if="showForm">
          <field-select v-model="vehicle.model" :field="'Model'" :actualItem="currentItem.model.id" :values="models"></field-select>
        </span>
        <span v-else>
           {{ currentItem.model.name }}
        </span>
      </td>
      <td>
        <span v-if="showForm">
          <field-select v-model="vehicle.driver" :actualItem="currentItem.driver.id" :field="'Driver'" :values="drivers"></field-select>
        </span>
        <span v-else>
          {{ currentItem.driver.name }}
        </span>

      </td>
      <td>
      <form action="javascript:void(0);">
       <span v-if="showForm">
            <button v-on:click="save(currentItem)">Save</button>
        </span>
        <span v-else>
            <button v-on:click="edit(currentItem)">Edit</button>
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
   props: ['actualItem','vehicle', 'makes', 'models', 'drivers','reload'],
   data () {
        return {
          response: [],
          errors: [],
          updatedVehicle:null,
          serverURL: process.env.SERVER_URL,
          showForm: false,
          retrievedVehicle: {}

        }
      },
  computed: {
    currentItem() {
      if (this.updatedVehicle) {
        var a = this.updatedVehicle;
        this.updatedVehicle = null;
        return a
      } else {
        return this.actualItem
      }
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
              this.showForm=false;
              //This is passed through from Garage.vue as its actual function as a variable called reload
              //The reload is passed from VehicleTable to child vue page TableRow as reload
              //When triggered here - it reloads all the vehicles in parent parent file Garage.vue
//              this.reload;


              if (res.data) {
                console.log('resData '+JSON.stringify(res.data))
                this.updatedVehicle = res.data
               // this.parent.vehicle=res.data
              }
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

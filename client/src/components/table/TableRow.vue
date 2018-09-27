<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">

    <tr>
      <td>
      {{ item.id }}
      </td>
      <td>
        <span v-if="showForm">
          <input  v-model="vehicle.name">
        </span>
        <span v-else>
            {{ item.name }}
        </span>
      </td>
      <td>
       <span v-if="showForm">
          <field-select v-model="vehicle.make" :item="item.make.id" :field="'Make'" :values="makes"></field-select>
        </span>
        <span v-else>
            {{ item.make.name }}
        </span>
      </td>
      <td>
        <span v-if="showForm">
          <field-select v-model="vehicle.model" :field="'Model'" :item="item.model.id" :values="models"></field-select>
        </span>
        <span v-else>
           {{ item.model.name }}
        </span>
      </td>
      <td>
        <span v-if="showForm">
          <field-select v-model="vehicle.driver" :item="item.driver.id" :field="'Driver'" :values="drivers"></field-select>
        </span>
        <span v-else>
          {{ item.driver.name }}
        </span>

      </td>
      <td>
      <form action="javascript:void(0);">
       <span v-if="showForm">
            <button v-on:click="save(item)">Save</button>
        </span>
        <span v-else>
            <button v-on:click="edit(item)">Edit</button>
        </span>
        </form>
      </td>
    </tr>

</template>

<script>
//This is needed for the select component to work
import FieldSelect from '../form/FieldSelect'
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
          FieldSelect
        },
       methods: {
         edit () {

          //These must be delcated in the data block above the this items or must be the direct object queried
          console.log(`${this.serverURL}/vehicle/${this.item.id}`);

          fetch(`${this.serverURL}/vehicle/${this.item.id}`, {
             method: 'GET',
             headers: {'Content-Type': 'application/json'},
           }).then(r => r.json()).then(json => {
             this.showForm=true;
             this.vehicle=this.item;
             console.log('json '+JSON.stringify(json));
             this.retrievedVehicle=json
           }).catch(ex => console.error('s', ex))
        },
        save() {
          const newName = this.vehicle;
          console.log( JSON.stringify(newName)+"---------");
                fetch(`${this.serverURL}/vehicle/${this.item.id}`, {
                  method: 'PUT',
                  headers: {'Content-Type': 'application/json'},
                  body: JSON.stringify(newName)
                }).then(r => r.json()).then(json => {
                   this.showForm=false;
                   //This is passed through from Garage.vue as its actual function as a variable called reload
                   //The reload is passed from VehicleTable to child vue page TableRow as reload
                   //When triggered here - it reloads all the vehicles in parent parent file Garage.vue
                   this.reload;
                }).catch(ex => console.error('Unable to save vehicle', ex))
        }
     }

}

</script>

<!-- Per Component Custom CSS Rules -->
<style>
  /* Add custom rules here */
</style>

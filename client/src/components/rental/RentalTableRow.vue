<template id="tablerow-template" xmlns="http://www.w3.org/1999/xhtml">
    <tr>

      <td>
        <span class="hidden">{{ item.id}}</span>
        <span>{{ item.make.name }}</span>
      </td>
      <td>{{ item.model.name }}</td>
      <td>{{ item.cost }}</td>
      <td>{{ item.deposit }}</td>
      <td>{{item.inStock}}</td>
      <td class="rating">
        <span v-bind:class="{'rating-active' :checkRating(n, item.rating)}" v-for="n in 5">☆</span>
      </td>
      <td>{{item.regPlate}}</td>
      <td>
      <form action="javascript:void(0);">
        <button v-show="item.inStock>0" v-on:click="rentVehicle(item)">Hire vehicle</button>

        </form>
      </td>
    </tr>

</template>

<script>
//This is needed for the select component to work
import FieldSelect from '../form/FieldSelect';
import GarageService from '@/services/GarageService';

export default {
    //You must declare what is being passed in otherwise they wont work..
   props: ['item', 'reload'],
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
         rentVehicle () {
           this.updateValue(this.item );
         //These must be delcated in the data block above the this items or must be the direct object queried

           /*
           const userId=JSON.parse(localStorage.getItem('vuex')).user.profile.id
           //console.log('---'+JSON.stringify(JSON.parse(localStorage.getItem('vuex')).user))
           return GarageService.fetchRoot('/guest/hireVehicle?vehicle.id='+this.item.id+'&driver.id='+(userId?userId:''))
             .then((res) => {
             if (res) {
               if (res.data) {
                 //this.vehicles.push(res.data) // <2>

                 //this.vehicle = {name: '', make: null, model: null, driver: null}
                 //this.showForm=true;
                 //this.vehicle=this.item;
                 //console.log('json '+JSON.stringify(res.data));
                 //this.retrievedVehicle=res.data

               }
             }
           });

           */

        },
         updateValue: function (value) {
           this.$emit('input', value);
         },
         checkRating(n, rating) {
           return rating - n >= 0;
         },
        save() {
          const newName = this.vehicle;
          //console.log( {id:newName.id,name:newName.name,make:{id: newName.make.id}, model:{id: newName.model.id}, driver:{id: newName.driver.id}} +" <<>>"+JSON.stringify(newName)+"---------");
          console.log(' '+JSON.stringify(newName))
          return GarageService.update('rental/'+this.item.id, newName)
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
  .rating-active:before {
    content: "\2605";
    position: absolute;
    color:gold;
  }
  .rating {
  }
  .rating > span {
    display: inline-block;
    position: relative;
    width: 1.1em;
  }
</style>

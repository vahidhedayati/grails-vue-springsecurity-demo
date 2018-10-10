
<template>
  <div id="garage">
    <app-header></app-header>
    <p>Calc value: {{result}}</p>
    <calc class="reduceZoom" v-model="result"/>
    <vehicle-form v-model="vehicle"
                  :makes="makes"
                  :models="models"
                  :drivers="drivers"
                  @submit="submitNewVehicle()">

    </vehicle-form>
    <vehicle-table :vehicles="vehicles"
                    :makes="makes"
                    :models="models"
                    :drivers="drivers"
                    :reload="fetchVehicles()"
     ></vehicle-table>
  </div>
</template>

<script>
import AppHeader from './AppHeader' // <1>
import GarageService from '@/services/GarageService'
import VehicleForm from './form/VehicleForm'
import VehicleTable from './table/VehicleTable'
import Calc from './example/calc'

export default {
  components: {
    AppHeader,
    VehicleForm,
    VehicleTable,
    Calc
  },
  data: function () {
    return {
      vehicles: [],
      vehicle: {name: '', make: {id:null}, model: {id:null}, driver: {id:null}},
      models: [],
      makes: [],
      result:'',
      newName: '',
      drivers: [],
      serverURL: process.env.SERVER_URL
    }
  },
  // end::component[]
  // tag::fetch[]
  created () { // <1>
    this.fetchData()
  },
  methods: {
    fetchData: async function () {
      try {
        Promise.all([
          this.fetchVehicles(),
          this.fetchModels(),
          this.fetchModels(),
          this.fetchMakes(),
          this.fetchDrivers()
        ])
      } catch (error) {
        console.log(error)
      }
    },

    fetchVehicles: function () {
      return GarageService.fetchName('vehicle')
        .then((res) => {
        if (res) {
          if (res.data) {
            this.vehicles = res.data;

          }
        }
      });
    },
    fetchModels: function () {
      return GarageService.fetchName('model')
        .then((res) => {
        if (res) {
          if (res.data) {
            this.models = res.data;

          }
        }
      });
    },
    fetchMakes: function () {
      return GarageService.fetchName('make')
        .then((res) => {
        if (res) {
          if (res.data) {
            this.makes = res.data;

          }
        }
      });

    },
    fetchDrivers: function () {
      return GarageService.fetchName('driver')
        .then((res) => {
        if (res) {
          if (res.data) {
            this.drivers = res.data;

          }
        }
      });

    },

    submitNewVehicle: function () {
      const vehicle = {
        vehicle: this.vehicle
      }
      ///console.log(JSON.stringify({name:vehicle.name, make:{id:vehicle.make}, model:{id:vehicle.model}, driver:{id:vehicle.driver}})+" "+JSON.stringify(vehicle));
      console.log(' > '+JSON.stringify(vehicle))
      return GarageService.createName('vehicle',this.vehicle)
        .then((res) => {
        if (res) {
          if (res.data) {
            this.vehicles.push(res.data)
            this.vehicle = {name: '', make: null, model: null, driver: null}

          }
        }
      });
    },
    updateItem () {
      const newName = this.newName;
      return GarageService.update('vehicle/'+this.item.id, newName)
        .then((res) => {
        if (res) {
          if (res.data) {
            this.vehicles.push(res.data)
            this.vehicle = {name: '', make: null, model: null, driver: null}

          }
        }
      });

    }
  }
}
</script>
<style>
  #garage {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    text-align: center;
    color: #2c3e50;
  }
  .reduceZoom {
    zoom:25.50%;

  }
</style>

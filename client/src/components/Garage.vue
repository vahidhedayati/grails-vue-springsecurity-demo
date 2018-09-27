<!--tag::template[]-->
<template>
  <div id="garage">
    <app-header></app-header>
    <vehicle-form v-model="vehicle"
                  :makes="makes"
                  :models="models"
                  :drivers="drivers"
                  @submit="submitNewVehicle()"> <!--1-->

    </vehicle-form>
    <vehicle-table :vehicles="vehicles"
                    :makes="makes"
                    :models="models"
                    :drivers="drivers"
                    :reload="fetchVehicles()"
     ></vehicle-table> <!--2-->
  </div>
</template>
<!--end::template[]-->
<!--tag::component[]-->
<script>
import AppHeader from './AppHeader' // <1>
import GarageService from '@/services/GarageService'
import VehicleForm from './form/VehicleForm'
import VehicleTable from './table/VehicleTable'

export default {
  components: { // <1>
    AppHeader,
    VehicleForm,
    VehicleTable
  },
  data: function () { // <2>
    return {
      vehicles: [],
      vehicle: {name: '', make: null, model: null, driver: null},
      models: [],
      makes: [],
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
        Promise.all([// <3>
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
      return GarageService.create('vehicle', JSON.stringify(vehicle))
        .then((res) => {
        if (res) {
          if (res.data) {
            this.vehicles.push(res.data) // <2>
            this.vehicle = {name: '', make: null, model: null, driver: null}

          }
        }
      });
    },
    updateItem () {
      const newName = this.newName;
      console.log('n ddddddddddddddddddddddddddddd'+newName)
      return GarageService.update('vehicle/'+this.item.id, JSON.stringify(newName))
        .then((res) => {
        if (res) {
          if (res.data) {
            this.vehicles.push(res.data) // <2>
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
</style>

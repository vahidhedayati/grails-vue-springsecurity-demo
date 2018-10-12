
<template>
  <div id="custom">
    <app-header></app-header>
    <custom-table :vehicles="vehicles"
                    :makes="makes"
                    :models="models"
                    :drivers="drivers"
                    @reload="fetchVehicles()"
     ></custom-table>
    <Pagination
      :maxVisibleButtons=3
      :totalPages="numberOfPages"
      :total="total"
      :currentPage="currentPage"
      @pagechanged="pagechanged"/>
  </div>
</template>

<script>
import AppHeader from '../AppHeader'
import GarageService from '@/services/GarageService'
import CustomTable from './table/VehicleTable'
import Pagination from '../Pagination'
export default {
  components: {
    AppHeader,
    CustomTable,
    Pagination
  },
  data: function () {
    return {
      vehicles: [],
      vehicle: {},
      models: [],
      makes: [],
      newName: '',
      drivers: [],
      serverURL: process.env.SERVER_URL,
      total: 0,
      max: 10,
      currentPage:1,
      numberOfPages:0,
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
          this.fetchVehicles(0),
          this.fetchModels(),
          this.fetchModels(),
          this.fetchMakes(),
          this.fetchDrivers()
        ])
      } catch (error) {
        console.log(error)
      }
    },
    pagechanged: function(page) {
      console.log("Page = "+page)
      this.currentPage = page;
      this.fetchVehicles((page*this.max)-this.max)
    },
    fetchVehicles: function (pageNumber) {
      console.log("Fetching vehicles "+pageNumber)
//,{offset:pageNumber, max:this.max}
      return GarageService.fetchName('customRest?offset='+pageNumber)
        .then((res) => {
        if (res) {
          //console.log(' rees'+JSON.stringify(res))
          if (res.data.instanceList) {
            console.log("rr "+res.data.instanceList)
            this.vehicles = res.data.instanceList;
            this.total=res.data.instanceTotal;
            this.numberOfPages=res.data.numberOfPages;
          } else {
            if (res.data) {
              //console.log("rr "+res.data.objects)
              this.vehicles = res.data;

            }
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
    sendVehicles: function () {
      console.log('Trying to export vehicles')
      return GarageService.fetchName('exportVehicle')
        .then((res) => {
        if (res) {
          if (res.data) {
          //  this.makes = res.data;
            //console.log(' '+res.data)
            var data, filename, link,dt;
            filename = 'export.csv';
            dt = 'data:text/csv;charset=utf-8,' + res.data;
            data = encodeURI(dt);
            link = document.createElement('a');
            link.setAttribute('href', data);
            link.setAttribute('download', filename);
            link.click();
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
  #custom {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    text-align: center;
    color: #2c3e50;
  }
  .reduceZoom {
    zoom:25.50%;

  }
</style>

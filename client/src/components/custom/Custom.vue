
<template>
  <div id="custom">
    <app-header></app-header>
    <search-form v-model="search"
                  :makes="makes"
                  :models="models"
                  :drivers="drivers"
                 @submit="searchVehicles()"></search-form>

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
import $ from 'jquery';
import AppHeader from '../AppHeader'
import SearchForm from './SearchForm'
import GarageService from '@/services/GarageService'
import CustomTable from './table/VehicleTable'
import Pagination from '../Pagination'
export default {
  components: {
    SearchForm,
    AppHeader,
    CustomTable,
    Pagination
  },
  data: function () {
    return {
      vehicles: [],
      search:{contractName:'', vehicleName: '', make: {id:null}, model: {id:null}, driver: {id:null}},
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
    searchVehicles: function () {
    console.log("searching vehicles "+$.param(this.search))
//,{offset:pageNumber, max:this.max}
    return GarageService.fetchName('customRest?'+$.param(this.search))
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

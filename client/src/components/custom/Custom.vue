
<template>
  <div id="custom">
    <app-header></app-header>
    <search-form v-model="search"

                 v-bind="{contract}"
                  :makes="makes"
                  :models="models"
                  :drivers="drivers"
                 :contracts="contracts"
                 @createHistory="createHistory()"
                 @submit="searchVehicles()"></search-form>

    <custom-table :vehicles="vehicles"
                    :makes="makes"
                    :models="models"
                    :drivers="drivers"
                  v-bind="{fetchVehicles,sortSearch}"
     ></custom-table>



    <Pagination
      :maxVisibleButtons=3
      :totalPages="numberOfPages"
      :total="total"
      @sortSearch="sortSearch"
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
import moment from 'moment';
export default {
  components: {
    SearchForm,
    AppHeader,
    CustomTable,
    Pagination,
    moment
  },
  data: function () {
    return {
      vehicles: [],
      search:{contractName:'', vehicleName: '', make: {id:null}, model: {id:null}, driver: {id:null},returnDate1:'',fromDate1:'',toDate1:''},
      vehicle: {},
      contracts:[],
      models: [],
      makes: [],
      newName: '',
      drivers: [],
      serverURL: process.env.SERVER_URL,
      total: 0,
      max: 10,
      offset:0,
      currentPage:1,
      numberOfPages:0,
      currentSort:'',
      currentSortDir:'asc',
      contract:{contractName:'', vehicleName: '', make: {id:null}, model: {id:null}, driverName:'',returnDate1:''},
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
    initialiseVehicles(params){
      return GarageService.fetchName('customRest?'+params)
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
    pagechanged: function(page) {
      console.log("Page = "+page)
      this.currentPage = page;
      this.offset=(page*this.max)-this.max
      this.fetchVehicles(this.offset)
    },
    sortSearch(currentSort,currentSortDir) {
      //This is coming back grom VehicleTable sortable column full sorting is method 2 here
      var variables = $.param(this.search);
      if (this.search.returnDate1) {
        variables+="&returnDate="+moment(this.search.returnDate1).format('DD MMM YYYY')
      }
      this.currentSort=currentSort;
      this.currentSortDir=currentSortDir;
      variables+="&sort="+currentSort+"&order="+currentSortDir+'&offset='+ this.offset;
      this.initialiseVehicles(variables);
    },
    fetchVehicles: function (pageNumber) {
      var variables=''
      if (this.search) {
        variables += $.param(this.search);
        if (this.search.returnDate1) {
          variables+="&returnDate="+moment(this.search.returnDate1).format('DD MMM YYYY')
        }

      }
      if (this.currentSort) {
        variables+="&sort="+this.currentSort+"&order="+this.currentSortDir;
      }
      if (variables!='') {
        variables+='&offset='+pageNumber
      } else {
        variables='?offset='+pageNumber
      }

      console.log("Fetching vehicles "+pageNumber)
      this.initialiseVehicles(variables);
    },
    searchVehicles: function () {
      console.log("searching vehicles "+$.param(this.search))
      var variables = $.param(this.search);
      if (this.search.returnDate1) {
        variables+="&returnDate="+moment(this.search.returnDate1).format('DD MMM YYYY')
      }
      if (this.currentSort) {
        variables+="&sort="+this.currentSort+"&order="+this.currentSortDir;
      }
      this.initialiseVehicles(variables);
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
    fetchContracts: function () {
      return GarageService.fetchName('contract')
        .then((res) => {
        if (res) {
          if (res.data) {
            this.contracts = res.data;

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

<template>
  <div id="custom">
    <app-header></app-header>
    <search-form v-model="search" @submit="searchVehicles()"></search-form>

    <country-table :countries="countries"
                   v-bind="{fetchCountries,sortSearch}"></country-table>


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
import CountryTable from './table/CountryTable'
import Pagination from '../Pagination'
import moment from 'moment';
export default {
  components: {
    SearchForm,
    AppHeader,
    CountryTable,
    Pagination,
    moment
  },
  data: function () {
    return {
      countries: [],
      search:{name:''},
      country: {},
      serverURL: process.env.SERVER_URL,
      total: 0,
      max: 10,
      offset:0,
      currentPage:1,
      numberOfPages:0,
      currentSort:'',
      currentSortDir:'asc'
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
          this.fetchCountries(0)
        ])
      } catch (error) {
        console.log(error)
      }
    },
    initialiseCountries(params){
      //return GarageService.fetchName('countries?'+params)

      return GarageService.fetchRoot('/guest/countries?'+params)
        .then((res) => {
        if (res) {
          //console.log(' rees'+JSON.stringify(res))
          if (res.data.instanceList) {
            console.log("rr "+res.data.instanceList)
            this.countries = res.data.instanceList;
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
      this.initialiseCountries(variables);
    },
    fetchCountries: function (pageNumber) {
      var variables=''
      if (this.search) {
        variables += $.param(this.search);
      }
      if (this.currentSort) {
        variables+="&sort="+this.currentSort+"&order="+this.currentSortDir;
      }
      if (variables!='') {
        variables+='&offset='+pageNumber
      } else {
        variables='?offset='+pageNumber
      }
      console.log("Fetching countries "+pageNumber)
      this.initialiseCountries(variables);
    },
    searchVehicles: function () {
      console.log("--------------------searching countries "+$.param(this.search))
      var variables = $.param(this.search);
      this.initialiseCountries(variables);
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

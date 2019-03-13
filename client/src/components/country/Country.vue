<template>
  <div id="custom">

    <app-header></app-header>

    <!-- The refresh and country errors are being returned by CountryForm which is the add tab of search form -->
    <search-form v-model="search"
              @submit="searchCountries()"
              @refresh-list="refreshCountries"
              @country-errors="errorCountries"
    ></search-form>

    <!-- any errors caused by any underlying processes on this page -->
    <ul v-show="errors.length>0"  class="errors"><li v-for="error in errors">{{ error }}</li></ul>


    <!-- this loads up the entire country listing -->
    <country-table :countries="countries"
                   v-bind="{fetchCountries,sortSearch}"
                   @country-update="updateCountries"
                    @country-errors="errorCountries"
                   >
    </country-table>


    <!-- generic pagination -->
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


const newObjInInitialArr = function(initialArr, newObject) {
  let id = newObject.id;
  let newArr = [];
  for (let i = 0; i < initialArr.length; i++) {
    if (id === initialArr[i].id) {
      newArr.push(newObject);
    } else {
      newArr.push(initialArr[i]);
    }
  }
  return newArr;
};

const updateObjectsInArr = function(initialArr, newArr) {
  let finalUpdatedArr = initialArr;
  for (let i = 0; i < newArr.length; i++) {
    finalUpdatedArr = newObjInInitialArr(finalUpdatedArr, newArr[i]);
  }
  return finalUpdatedArr
}

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
    errors:[],
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
  created () {
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
      return GarageService.fetchRoot('/guest/countries?'+params)
        .then((res) => {
        if (res) {
          if (res.data.instanceList) {
            console.log("rr "+res.data.instanceList)
            this.countries = res.data.instanceList;
            this.total=res.data.instanceTotal;
            this.numberOfPages=res.data.numberOfPages;
          } else {
            if (res.data) {
              //console.log("rr "+res.data.objects)
              this.countries = res.data;
            }
          }
        }
      });
    },
    pagechanged: function(page) {
      console.log("Page = "+page)
      this.currentPage = page;
      this.offset=(page*this.max)-this.max
      this.fetchCountries(this.offset)
    },
    sortSearch(currentSort,currentSortDir) {
      var variables = $.param(this.search);
      this.currentSort=currentSort;
      this.currentSortDir=currentSortDir;
      variables+="&sort="+currentSort+"&order="+currentSortDir+'&offset='+ this.offset;
      this.initialiseCountries(variables);
    },
   refreshCountries: function () {
      console.log('refreshing entire list after a new entry was added')
      this.fetchCountries(0)
   },
   updateCountries: function (country) {
    this.errors=[];
      console.log('country.vue updating country list')
      this.countries=updateObjectsInArr(this.countries, [country])
    },
     errorCountries: function (errors) {
               console.log('countryTable.vue updating error list')
                  this.errors=errors;
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
    searchCountries: function () {
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

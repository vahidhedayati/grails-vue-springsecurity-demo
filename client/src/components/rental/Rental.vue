
<template>
  <div id="custom">
    <app-header
      header-image="retail"
      header-text="Arrrr welcome to the shop"
    ></app-header>
    <search-form v-model="vehicle"
                  :makes="makes"
                  :models="models"
                  @submit="searchVehicles()"/>

    <custom-table :vehicles="vehicles"
                  :makes="makes"
                  :models="models"

                  v-bind="{fetchVehicles,sortSearch}"
    ></custom-table>
  </div>
</template>
<script>
  import $ from 'jquery';
  import AppHeader from '../AppHeader'
  import SearchForm from './SearchForm'
  import GarageService from '@/services/GarageService'
  import CustomTable from './RentalVehicleTable'
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
      search: {
        contractName: '',
        vehicleName: '',
        cost:'',
        deposit:'',
        make: {id: null},
        model: {id: null},
        fromDate1: '',
        toDate1: ''
      },
      vehicle: {},
      contracts: [],
      models: [],
      makes: [],
      newName: '',
      drivers: [],
      serverURL: process.env.SERVER_URL,
      total: 0,
      max: 10,
      offset: 0,
      currentPage: 1,
      numberOfPages: 0,
      currentSort: '',
      currentSortDir: 'asc'
    }
  },
  // end::component[]
  // tag::fetch[]
  created() { // <1>
    this.fetchData()
  },
  methods: {
  fetchData: async function () {
    try {
      Promise.all([
        //this.fetchVehicles(0),
        //this.listDefaults(),
        this.fetchModels(),
        this.fetchModels(),
        this.fetchMakes(),

      ])
    } catch (error) {
      console.log(error)
    }
  },
  initialiseVehicles(params){

    return GarageService.fetchName('rental?'+params)
      .then((res) => {
      if (res) {
        console.log(' rees'+JSON.stringify(res))
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

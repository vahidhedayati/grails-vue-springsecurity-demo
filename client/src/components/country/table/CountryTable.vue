<template id="fulltable-template" xmlns="http://www.w3.org/1999/xhtml">
  <div>
  <table class="table">
    <thead class="thead-inverse">
      <tr>
        <th @click="sort('id')" :class="{active: this.column === 'id'}">
          ID
          <span :class="{blank:this.column != 'id',
          arrow: (this.column === 'id'),
          asc: (this.column === 'id' && this.currentSortDir==='asc'),
           desc: this.column === 'id' && this.currentSortDir==='desc'}">
          </span>

        </th>
        <th @click="sort('name')" :class="{active: this.column === 'name'}">
          Name
          <span :class="{blank:(this.column != 'name'),
          arrow: (this.column === 'name'),
          asc: (this.column === 'name' && this.currentSortDir==='asc'),
           desc: (this.column ==='name' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('code')" :class="{active: this.column === 'code'}">
          Code
          <span :class="{blank:(this.column != 'code'),
          arrow: (this.column === 'code'),
          asc: (this.column === 'code' && this.currentSortDir==='asc'),
           desc: (this.column ==='code' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('updateUser')" :class="{active: this.column =='updateUser'}">
          Updated By
          <span :class="{blank:(this.column != 'updateUser'),
          arrow: (this.column === 'updateUser'),
          asc: (this.column === 'updateUser' && this.currentSortDir==='asc'),
           desc: (this.column ==='updateUser' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th @click="sort('lastUpdated')"  :class="{active: this.column === 'lastUpdated'}">
          last Updated
          <span :class="{blank:(this.column != 'lastUpdated'),
          arrow: (this.column === 'lastUpdated'),
          asc: (this.column === 'lastUpdated' && this.currentSortDir==='asc'),
           desc: (this.column ==='lastUpdated' && this.currentSortDir==='desc')}">
          </span>
        </th>
        <th>Action</th>

      </tr>
    </thead> <!--1-->
      <!-- sortedCats replaces vehicles and is only sorting by current paginated dataset -->
      <table-row v-for="country in countries"
                  :key="country.id"
                  :country="country"
                  @country-update="updateCountries"
                  @country-errors="errorCountries"
                 v-bind="{fetchCountries}"
                 v-model="result"
                ></table-row> <!--2-->
  </table>

  </div>
</template>

<script>
import TableRow from './TableRow.vue';

export default {
   props: ['countries', 'reload','fetchCountries','sortSearch'],
  components: { // <3>
    TableRow
  },
  data: function () {
  return {
    updatedResults:null,
    result:{},
    currentSort:'contractName',
    currentSortDir:'asc',
    column:'id',
    activeColumn:'id',
    currentStyle:'',
  }
},

  methods: {
    sort: function (s) {
      this.column=s;
      this.activeColumn=s;
      //if s == current sort, reverse
      if (s === this.currentSort) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';

      }
      this.currentSort = s;

      //This is full on search and sortedSearch sits in the parent page Custom.vue
      this.sortSearch(s,this.currentSortDir)

    },
     updateCountries: function (country) {
     console.log('countryTable.vue updating country list')
        this.$emit('country-update',country);
      },

           errorCountries: function (errors) {
           console.log('countryTable.vue updating country list')
              this.$emit('country-errors',errors);
            },
  },
  computed:{

  }
}

</script>

<style>
  th.active .arrow {
    opacity: 1;
  }

  .arrow {
    display: inline-block;
    vertical-align: middle;
    width: 0;
    height: 0;
    margin-left: 5px;
    opacity: 0.66;
  }

  .arrow.asc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #42b983;
  }

  .arrow.desc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #42b983;
  }
  th.active {
    color:orange !important;
  }


  blank {

  }
</style>

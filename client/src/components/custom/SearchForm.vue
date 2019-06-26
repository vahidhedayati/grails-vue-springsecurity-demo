<template id="search-contract-template" xmlns="http://www.w3.org/1999/xhtml">
  <div>


    <div id="inputRow" class="row">
      <div class="col-sm-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="contractName" v-model.lazy="search.contractName">
        </div>
      </div>
      <div class="col-sm-2">
        <div class="input-group">
          <!-- input type="text" class="form-control" placeholder="vehicleName" v-model.trim="search.vehicleName" -->
           <autocomplete form-field="search"
                                 @key-press="updateAutoCompleteItems"
                                 @search-value="updateSearchValue"
                                 @search-key="updateSearchKey"
                                 key-field="id" value-field="vehicleName"
                                 :items="vehicles" />

        </div>
      </div>
      <div class="col-sm-8">
        <div class="row">
          <div class="col-sm-2">
            <field-select v-model="search.make" :field="'Make'" :item=null :values="makes"></field-select> <!--3-->
          </div>
          <div class="col-sm-2">
            <field-select v-model="search.model" :field="'Model'" :item=null :values="models"></field-select>  <!--4-->
          </div>
          <div class="col-sm-2">
            <field-select v-model="search.driver" :field="'Driver'" :item="null" :values="drivers"></field-select>
          </div>



        <div class="col-sm-3">
          <datepicker v-model="search.returnDate1" placeholder="returnDate"  :format="customFormatter"></datepicker>
        </div>

        <div class="col-sm-1">
          <div class="btn-group" role="group" aria-label="Search Contracts">
            <button type="button" class="btn btn-success" @click="submit()">Search</button>

            <button class="btn btn-primary" @click="showModal = true">Add History</button>
            <test-modal  v-bind="{contract}"
                        :show="showModal"
                        :makes="makes"
                        :models="models"
                        :drivers="drivers"
                        :contracts="contracts"
                        @close="showModal = false"></test-modal>
          </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import $ from 'jquery';
import FieldSelect from '../form/FieldSelect';
import Datepicker from 'vuejs-datepicker';
import Autocomplete from '../Autocomplete'
import moment from 'moment';
import TestModal from './TestModal';
import GarageService from '@/services/GarageService'
export default {
  props: ['search','contract' ,'makes', 'models', 'drivers','contracts'],

  model: {
    prop: 'search',
    event: 'change',


  },
  components: {
    FieldSelect,
    Datepicker,
    moment,
    TestModal,
    Autocomplete,
    GarageService
  },
  data: function () {
    return {
      showModal:false,
      vehicles:[]
    }

  },
  methods: {
   updateSearchValue: function (value) {
     this.search.vehicleName=value
    },
    updateSearchKey: function (key) {
     //this.hotel.updateUserId=key
    },
    updateAutoCompleteItems: function (searchValue) {
      if (searchValue.length>2) {
        this.users=[];
        var variables = $.param(searchValue);
        variables+="&max=10&offset=0";
        this.initialiseVehicles(variables);
      }
    },
    initialiseVehicles(params){
       return GarageService.fetchName('customRest?'+params)
       .then((res) => {
        if (res) {
          if (res.data.instanceList) {
            this.vehicles = res.data.instanceList;
          } else {
            if (res.data) {
              this.vehicles = res.data;

            }
          }
        }
       });
    },
    submit () {
      this.$emit('submit')
    },
    customFormatter(date) {
      return moment(date).format('DD MMM YYYY');
    }
  }
}
</script>

<style>

</style>

<template id="search-contract-template" xmlns="http://www.w3.org/1999/xhtml">
  <div>


    <div id="inputRow" class="row">
      <div class="col-sm-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="contractName" v-model="search.contractName">
        </div>
      </div>
      <div class="col-sm-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="vehicleName" v-model="search.vehicleName">
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

            <button class="btn btn-primary" @click="showModal = true">Add Comment</button>
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
import FieldSelect from '../form/FieldSelect';
import Datepicker from 'vuejs-datepicker';
import moment from 'moment';
import TestModal from './TestModal';
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
    TestModal
  },
  data: function () {
    return {
      showModal:false,
    }

  },
  methods: {
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

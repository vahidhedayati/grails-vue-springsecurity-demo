<template id="search-contract-template" xmlns="http://www.w3.org/1999/xhtml">
  <div>
    <div id="inputRow" class="row">
      <span v-if="showAllSearch">
      <div class="col-sm-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="cost" v-model="search.cost">
        </div>
      </div>
      <div class="col-sm-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="deposit" v-model="search.deposit">
        </div>
      </div>
      <div class="col-sm-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="vehicle Name" v-model.trim="search.vehicleName">
        </div>
      </div>


      </span>
      <div class="col-sm-2">
        <field-select :class="showAllSearch?'':'hidden'" v-model="search.make" :field="'Make'" :actualItem="actualItem.make.id" :obj="item.make" :values="makes"></field-select>
      </div>
      <div class="col-sm-2">
        <field-select :class="showAllSearch?'':'hidden'" v-model="search.model" :field="'Model'" :actualItem="actualItem.model.id" :obj="item.model" :values="models"></field-select>
      </div>
      <div class="col-sm-3">
        <div class="btn-group" role="group" aria-label="Search Contracts">
            <span v-if="showAllSearch">
          <button type="button" class="btn btn-success" @click="submit()">Search</button>
            </span>

          <button type="button" class="btn btn-primary" v-on:click="hideSearch">Back to offers</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import FieldSelect from '../form/FieldSelect';
  import Datepicker from 'vuejs-datepicker';
  import moment from 'moment';

  export default {
    props: ['search','actualItem','makes', 'models','hideSearch'],
    model: {
      prop: 'search',
      event: 'change',
    },
    components: {
      FieldSelect
    },
    data: function () {
      return {
        showAllSearch:false,
      }
    },
    methods: {
      submit () {
        this.$emit('submit')
      }
    }
  }
</script>
<style>
</style>

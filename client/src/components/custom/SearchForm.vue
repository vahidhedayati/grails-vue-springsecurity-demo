<template id="search-contract-template" xmlns="http://www.w3.org/1999/xhtml">
  <div>


    <div id="inputRow" class="row">
      <div class="col-sm-3">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="contractName" v-model="search.contractName">
        </div>
      </div>
      <div class="col-sm-3">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="vehicleName" v-model="search.vehicleName">
        </div>
      </div>
      <div class="col-sm-7">
        <div class="row">
          <div class="col-sm-4">
            <field-select v-model="search.make" :field="'Make'" :item=null :values="makes"></field-select> <!--3-->
          </div>
          <div class="col-sm-4">
            <field-select v-model="search.model" :field="'Model'" :item=null :values="models"></field-select>  <!--4-->
          </div>
          <div class="col-sm-4">
            <field-select v-model="search.driver" :field="'Driver'" :item="null" :values="drivers"></field-select>
          </div>
        </div>
      </div>
      <div class="col-sm-2">
        <div class="btn-group" role="group" aria-label="Search Contracts">
          <button type="button" class="btn btn-success" @click="submit()">Search</button> <!--5-->
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import FieldSelect from '../form/FieldSelect'

export default {
  props: ['search', 'makes', 'models', 'drivers'], // <6>

  model: {
    prop: 'search', // <4>
    event: 'change'
  },
  components: {
    FieldSelect
  },
  methods: {
    submit () { // <5>
      this.$emit('submit')
    }
  }
}
</script>

<style>
  /* Add custom rules here */
</style>

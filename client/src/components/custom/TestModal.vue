<template>
  <modal :show="show" @close="close">
    <div class="modal-header">
      <h3>Contract history</h3>
    </div>
    <div class="modal-body">
      <div class="form-label col-sm-6">
          ContractName
          <input type="text" class="form-control" v-model="contract.contractName" required>
       </div>
      <div class="form-label col-sm-6">
    VehicName
        <input type="text" class="form-control" placeholder="vehicleName" v-model="contract.vehicleName" required>
      </div>


      <div class="form-label">
        <field-select v-model="contract.make.id" :field="'Make'" :item=null :values="makes" class="col-sm-6" required></field-select>
        <field-select v-model="contract.model.id" :field="'Model'" :item=null :values="models" class="col-sm-6" required></field-select>
        <field-select v-model="contract.driver.id" :field="'Driver'" :item="null" :values="drivers" class="col-sm-6" required></field-select>

      </div>
      <div style="clear:both;"/>
      <div class="form-label">

      <span class="col-sm-6">
           Returned
          <datepicker v-model="contract.returnDate1" class="dateField" required></datepicker>
        </span>

        <span class="col-sm-6 dateField">
        FromDate
        <datepicker v-model="contract.fromDate1" class="dateField" required></datepicker>
        </span>
        <span class="col-sm-6 dateField">
        FromDate
        <datepicker v-model="contract.toDate1"   class="dateField" required></datepicker>
        </span>
      </div>
    </div>
    <div class="modal-footer text-right">
      <button class="modal-default-button" @click="savePost()">
        Save
      </button>
    </div>
  </modal>
</template>

<script>
  import GarageService from '@/services/GarageService'
  import FieldSelect from '../form/FieldSelect';
  import Datepicker from 'vuejs-datepicker';
  import modal from '../Modal'
  import moment from 'moment';
  export default {
    props: ['show','contract' ,'makes', 'models', 'drivers','contracts'],

    data: function () {
      return {
        title: '',
        body: ''
      };
    },
    components: {
      modal,
      Datepicker,
      FieldSelect,
      moment
    },
    methods: {
      close: function () {
        this.$emit('close');
        this.title = '';
        this.body = '';
      },
      savePost: function () {
        // Some save logic goes here...

        if (this.contract.returnDate1) {
          this.contract.returnDate=moment(this.contract.returnDate1).format('DD MMM YYYY')
        }
        if (this.contract.fromDate1) {
          this.contract.fromDate=moment(this.contract.fromDate1).format('DD MMM YYYY')
        }
        if (this.contract.toDate1) {
          this.contract.toDate=moment(this.contract.toDate1).format('DD MMM YYYY')
        }
        console.log(' > '+JSON.stringify(this.contract))
        return GarageService.createName('customRest',this.contract)
          .then((res) => {
          if (res) {
            if (res.data) {
              console.log('res'+JSON.stringify(res.data))
              //this.close();
            }
          }
        });


      }
    }
  }

</script>
<style>
  .dateField input {
  color: red;
    max-width:90px !important;
  }
</style>

<template>
  <modal :show="show" @close="close"  v-if="show">
    <div class="modal-header">
      <h3>Display country {{country.name}}</h3>
    </div>
    <div class="modal-body">
      <div class="row">
      <div class="form-label col-sm-12">
        Name
        {{country.name}}
      </div>

      <div class="form-label col-sm-12">
        code
        {{country.code}}
      </div>

      <div class="form-label col-sm-12">
        username
        {{country.updateUser.username}}
      </div>
      <div class="form-label col-sm-12">
        last updated
        {{country.lastUpdated}}
      </div>
    </div>
      <div class="modal-footer text-right">
           <button class="modal-default-button" @click="close()">
             Close
           </button>
      </div>
    </div>
  </modal>
</template>

<script>
  import Datepicker from 'vuejs-datepicker';
  import modal from '../../Modal'
  import moment from 'moment';
  export default {
    props: ['show','country' ],

    data: function () {
      return {
        errors:[],
      };
    },
    components: {
      modal,
      Datepicker,
      moment
    },
    methods: {
      close: function () {
        console.log('running close')
        this.$emit('close');
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

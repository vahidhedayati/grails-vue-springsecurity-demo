<template>
  <modal :show="show" @close="close">
    <div class="modal-header">
      <h3>Vehicle hire questions</h3>
      <ul v-show="errors.length>0"  class="errors"><li v-for="error in errors">{{ error }}</li></ul>
    </div>
    <div class="modal-body">
      <div class="form-label col-sm-6">
        CONTENT TODO
      </div>
      <div class="form-label col-sm-6">
        TODO
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
  import Datepicker from 'vuejs-datepicker';
  import modal from '../Modal'
  import moment from 'moment';
  export default {
    props: ['show', 'item'],

    data: function () {
      return {
        title: '',
        body: '',
        errors: [],
      };
    },
    components: {
      modal,
      Datepicker,
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

        this.close();
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

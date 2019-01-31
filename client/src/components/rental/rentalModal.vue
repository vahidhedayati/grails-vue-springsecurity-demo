<template>
  <modal :show="show" @close="close">
    <div class="modal-header">
      <h3>Vehicle hire questions</h3>
      <ul v-show="errors.length>0"  class="errors"><li v-for="error in errors">{{ error }}</li></ul>
    </div>
    <div class="modal-body">
      <div class="form-label col-sm-6">
      </div>
    </div>
  </modal>

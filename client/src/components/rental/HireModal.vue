<template>
  <modal :show="show" @close="close">
    <div class="modal-header">
      <h3>Vehicle hire questions</h3>
      <ul v-show="errors.length>0"  class="errors"><li v-for="error in errors">{{ error }}</li></ul>
    </div>
    <div class="modal-body">
      <div>
        <input type="hidden"  v-model="rentalContract.vehicle.id" value="">
        User:
        <span v-if="authRecord">
           {{authRecord.username}}
        </span>
        <span v-else>
          guest
        </span>
        <br>
        <span v-if="result">
        Hiring {{result.vehicleName}}<hr/>
          </span>
      </div>
      <div v-if="authRecord">
        <!-- Value has not been set instead gets set in created conflicts with v-model on the same element because the latter already expands to a value binding internally-->
        <input type="hidden" v-model="rentalContract.driver.id" value="">

      </div>
      <div v-else>
        <div class="form-label col-sm-6">
          <label>Name</label>
          <input type="text" class="form-control" placeholder="name" v-model="rentalContract.name"  required>
        </div>
        <div class="form-label col-sm-6">
          <label>Username</label>
          <input type="text" class="form-control" placeholder="username" v-model="rentalContract.username"  required>
        </div>
        <div class="form-label col-sm-6">
          <label>Password</label>
          <input type="password" class="form-control" placeholder="password" v-model="rentalContract.password"  required>
        </div>

      </div>
      <div>
         <span class="col-sm-6 dateField">
        FromDate
        <datepicker v-model="rentalContract.fromDate1" class="dateField" required></datepicker>
        </span>
        <span class="col-sm-6 dateField">
        ToDate
        <datepicker v-model="rentalContract.toDate1"   class="dateField" required></datepicker>
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
  import Datepicker from 'vuejs-datepicker';
  import modal from '../Modal'
  import moment from 'moment';
  export default {
    props: ['show', 'reloadVehicles', 'reload', 'actualItem', 'rentalContract','result'],

    data: function () {
      return {
        title: '',
        body: '',
        errors: [],
        userId:null,
        authRecord:null,
      };
    },
    components: {
      modal,
      Datepicker,
      moment
    },
    created: function () {
      this.authRecord=JSON.parse(localStorage.getItem('vuex')).auth.isAuthenticated;
      // Some unusual behaviour around :value="userId"
      //https://forum.vuejs.org/t/conflict-v-bind-value-with-v-model/34118
      this.rentalContract.driver.id=this.authRecord.id;
    },
    beforeUpdate: function() {
      // Some unusual behaviour around :value="userId"
      //https://forum.vuejs.org/t/conflict-v-bind-value-with-v-model/34118
      //console.log(' result' +JSON.stringify(this.result))
      if (this.result && this.result.id ) {
        this.rentalContract.vehicle.id=this.result.id;
      } else {
        this.close();

      }



    },
    methods: {
      close: function () {
        this.$emit('close');
        this.title = '';
        this.body = '';
      },
      savePost: function () {
        // Some save logic goes here...

        if (this.rentalContract.fromDate1) {
          this.rentalContract.fromDate=moment(this.rentalContract.fromDate1).format('DD MMM YYYY')
        }
        if (this.rentalContract.toDate1) {
          this.rentalContract.toDate=moment(this.rentalContract.toDate1).format('DD MMM YYYY')
        }
        console.log(' > '+JSON.stringify(this.rentalContract))
        return GarageService.createRootNoCatch('/guest/rental',this.rentalContract)
          .then((res) => {
          if (res) {
            if (res.data) {
              console.log('resDAA----------------AA'+res.data.instanceList)

              //This is emitted to parent page RentalVehicleTable the current updated
              //object as per DB once it has been saved another search was run to return
              //just this id from search listing - this be in back end grails application
              this.$emit('update-vehicles', res.data.instanceList[0]);

              this.close()

            } else {
              console.log(' dddd '+res.errors)
            }
          } else {
            console.log('no res')
        }
      }).catch((error) => {
          if (error.response) {
          this.errors=error.response.data.error
          error.response.data.error.forEach(function(element) {
            console.log("..."+element);

          });

        } else if ( error.request) {
          console.log("dddd"+error.request);
        } else {
          console.log('Error', error.message);
        }
      });
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

<template>
  <modal :show="show" @close="close">
    <div class="modal-header">
      <h3>
        <span v-if="result">
        Return vehicle: {{result.contractName}}<hr/>
          </span>
      </h3>
      <ul v-show="errors.length>0"  class="errors"><li v-for="error in errors">{{ error }}</li></ul>
    </div>
    <div class="modal-body">
      <div>
        <input type="hidden"  v-model="rentalContract.id" value="">

      </div>
      <div class="col-sm-6  rating">
        <span v-on:click="checkRating1(n)" v-for="n in 5" v-model="rentalContract.rating">☆</span>
      </div>

      <div>
         <span class="col-sm-6 dateField">
        return date
        <datepicker v-model="rentalContract.returnDate1" class="dateField" required></datepicker>
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
  import modal from '../../Modal'
  import moment from 'moment';
  export default {
    props: ['show', 'item', 'rentalContract','result'],

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
      //this.rentalContract.driver.id=this.authRecord.id;
    },
    beforeUpdate: function() {
      // Some unusual behaviour around :value="userId"
      //https://forum.vuejs.org/t/conflict-v-bind-value-with-v-model/34118
      //console.log(' result' +JSON.stringify(this.result))
      if (this.result && this.result.id ) {
      //  this.rentalContract.vehicle.id=this.result.id;
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
      checkRating1(n) {
        return 5 - n >= 0;

       // (n, rentalContract.rating)}
      },
      checkRating(n, rating) {
        return rating - n >= 0;
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
              console.log('res'+JSON.stringify(res.data))
              this.close();
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
   .rating-active:before {
     content: "\2605";
     position: absolute;
     color:gold;
   }
  .rating {
  }
  .rating > span {
    display: inline-block;
    position: relative;
    width: 1.1em;
  }
</style>

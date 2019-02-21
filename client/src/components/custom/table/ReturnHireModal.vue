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
        <input type="hidden"  v-model="rentalContract.contract.id" value="">
        <input type="hidden"  v-model="rentalContract.user.id" value="">
      </div>
      <div class="col-sm-12  rating" id="ratingContainer">
        <label>Provide a rating back</label><br/>
        <!-- this binds the number selected to current value which then calls checkRating to set the class -->
        <span  v-bind:class="{'rating-active' :checkRating(n, rentalContract.rating)}"
               v-on:click="checkRating1(n)" v-for="n in 5" v-model="rentalContract.rating">
          ☆
        </span>
      </div>

      <div>
         <span class="col-sm-12 dateField">
           <label>return date</label><br/>
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
  //import $ from 'jquery';
  import GarageService from '@/services/GarageService'
  import Datepicker from 'vuejs-datepicker';
  import modal from '../../Modal'
  import moment from 'moment';
  export default {
    props: ['show', 'actualItem', 'rentalContract','result'],

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
      this.rentalContract.user.id=this.authRecord.id;
    },
    beforeUpdate: function() {
      // Some unusual behaviour around :value="userId"
      //https://forum.vuejs.org/t/conflict-v-bind-value-with-v-model/34118
      //console.log(' result' +JSON.stringify(this.result))
      if (this.result && this.result.id ) {
        this.rentalContract.contract.id=this.result.id;
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
        this.rentalContract.rating=n;
        /**
         * One way was to use jquery to bind the active class -
         * but by setting the actual object value above and defining another checkRating for class
         * of overall rate span which calls the checkRating below function and does exactly the same as below jquery code - but much much shorter now
         *
         *

          $('#ratingContainer').find('span').removeClass('rating-active')
          $('#ratingContainer span').each(function (i) {
            if (i+1 <= Number(n)) {
              $(this).addClass('rating-active')
            }
          })

         */
      },
      checkRating(n, rating) {
        return rating - n >= 0;
      },
      savePost: function () {
        // Some save logic goes here...

        if (this.rentalContract.returnDate1) {
          this.rentalContract.returnDate=moment(this.rentalContract.returnDate1).format('DD MMM YYYY')
        }

        console.log(' > '+JSON.stringify(this.rentalContract))
        return GarageService.createRootNoCatch('/guest/returnRental',this.rentalContract)
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

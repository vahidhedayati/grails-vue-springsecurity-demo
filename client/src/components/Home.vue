<template>
  <div>
  <router-link :to="{name: 'Profile'}" >
    <button class="btn btn-xs btn-danger">Profile</button>
  </router-link>


    <router-link :to="{name: 'testcalc'}">
      <button class="btn btn-xs btn-success">Parent -> Calc</button>
    </router-link>

    <router-link :to="{name: 'calc'}">
      <button class="btn btn-xs btn-success">Calc</button>
    </router-link>

    <router-link :to="{name: 'shop'}">
      <button class="btn btn-xs btn-success">Shop</button>
    </router-link>


  <router-link v-show="!loggedIn" :to="{name: 'Login'}">

    <button class="btn btn-xs btn-primary">Login</button>
  </router-link>


  <router-link :to="{name: 'Garage'}">
    <button class="btn btn-xs btn-success">Garage</button>
  </router-link>

    <router-link :to="{name: 'custom'}">
      <button class="btn btn-xs btn-success">Custom</button>
    </router-link>


    <router-link  @click.native="++counter" :to="{ name: 'rental', params: {showSearch: false, counter:counter } }">
      <button  class="btn btn-xs btn-success">Rent a vehicle  {{counter}}</button>
    </router-link>

  <router-link v-show="loggedIn" :to="{name: 'Logout'}">
    <button class="btn btn-xs btn-success">Logout</button>

  </router-link>

    <my-component3></my-component3>
    <button v-on:click="incrementCounter">Increment Counter</button>
    <my-component :my-counter.sync="ccounter"></my-component>

    <my-component2 :my-info="message" inline-template>
      <span>
        <b>
          inline-template - {{myInfo}}
        </b>
      </span>
    </my-component2>

    <form-component
      :author="authorLabel"
      :title="titleLabel">

    </form-component>

  </div>
</template>
<script>
  const FormComponent ={
    template: `
  <span>
    <form>
      <slot></slot>
      <label for="title">{{title}}</label> <input id="title" type="text" />
      <label for="author">{{author}}</label><input id="author" type="text" />

    </form>
  </span>

  `,
    props: ['title', 'author']
  }
  const MyComponent2 = {
    props: ['myInfo']
  };
  const MyComponent3= {
    template:'<span><button v-on:click="counter +=1">{{counter}} cc</button></span>',
    data() {
      return {
        counter: 0
      }
    }
  }
  const MyComponent = {
    template: `<span>
      <button v-on:click="childIncrementCounter">Increment From Child {{this.myCounter}}</button>
    </span>`,
    methods: {
      childIncrementCounter() {
        this.$emit('update:myCounter', this.myCounter+1);
      }
    },
    props:['my-counter']
  }

  export default {

    components:{'my-component': MyComponent,MyComponent2,MyComponent3,FormComponent},

    data() {
      return {
        loggedIn:false,
        counter:0,
        lastPath:'',
        lastCounter:0,
        message: 'Hello World',
        titleLabel: 'The Title:',
        authorLabel: 'The Author:',
        ccounter: 0
      }
    },
    created () {
      this.loggedIn=((JSON.parse(localStorage.getItem('vuex')).user.token)?true:false)
    },

  beforeUpdate: function (from, to, next) {
    if (this.$route.path==='/rental') {
      if (this.$route.path==this.lastPath) {
        console.log('new after', this.$route.path);
        this.$eventHub.$emit('rentalcounter',this.counter);
        //lastCounter++
        //
        //this.$root.$emit('rental-counter',this.counter);
        // this.$bus.$emit('rentalCounter', this.counter)
      } else {
        //this.$eventHub.$off('rentalcounter');
      }
    } else {
      this.$eventHub.$off('rentalcounter');
    }

    this.lastPath=this.$route.path

    //this.showSearch=this.searchDetails;
  },	//#
  beforeRouteUpdate(to, from, next) {
    console.log('aaaaaaaaabefore', this.$route.path);
    next();
  },

    methods: {
      childIncrementCounter() {
        this.$emit('update:myCounter', this.myCounter+1);
      },
      emitCounter() {
        console.log('emitting')
        //this.$emit("counter", this.counter);
      },
      incrementCounter() {
        this.ccounter++;
      }
    },
  }
</script>

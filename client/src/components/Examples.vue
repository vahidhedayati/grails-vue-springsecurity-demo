<template>
  <div id="custom">
    <app-header></app-header>

    <div class="col-sm-3">
    <my-component3></my-component3>
    </div>

    <div class="col-sm-3">
    <button v-on:click="incrementCounter">Increment Counter</button>
    <my-component :my-counter.sync="ccounter"></my-component>
    </div>

    <div class="col-sm-3">
    <my-component2 :my-info="message" inline-template>
      <span>
        <b>
          inline-template - {{myInfo}}
        </b>
      </span>
    </my-component2>
    </div>

    <div class="col-sm-3">
      Name scoped<br/>
    <form-component
      :author="authorLabel"
      :title="titleLabel">

    </form-component>
    </div>


    <div class="col-sm-3">
      <hr/>
      Dynamic content<br/>
    <button @click="cycle">Cycle</button>
      <transition name="component-fade" mode="out-in">
        <component :is="currentView"/>
      </transition>
    </div>


    <div class="col-sm-3">
    <my-component5>
      <h3 slot="erik">Text From Erik</h3>
      <h3>{{message}}</h3>
    </my-component5>

    </div>

    <div class="col-sm-3">
    <book-component2 :books="books">
      <h1 slot="header">{{header}}</h1>
      <template slot="book" slot-scope="props">
        <h2>
          <i>{{props.text.title}}</i>
          <small>by: {{props.text.author}}</small>
        </h2>
      </template>
    </book-component2>
    </div>


    <div class="col-sm-3">
      <div @click="show = !show">
        <h2>{{title}}</h2>
      </div>
      <transition name="bounce">
        <div v-if="show">
          <h1>{{description}}</h1>
        </div>
      </transition>
      JS Hooks example<br/>
      <transition name="fade"
                  @before-enter="beforeEnter"
                  @enter="enter"
                  @after-enter="afterEnter"
                  @enter-cancelled="enterCancelled"
                  @before-leave="beforeLeave"
                  @leave="leave"
                  @after-leave="afterLeave"
                  @leave-cancelled="leaveCancelled"
                  :css="false">
        <div v-if="show">
          <h1>{{description}}</h1>
        </div>
      </transition>
    </div>


    <div class="col-sm-3">
      <div v-bind:class="[show1 ? blurClass : '', backClass]">
        <h1> Transition Demo</h1>
      </div>
      <button @click="show1 = !show1">
        Toggle
      </button>
      <transition name="fade">
        <div v-if="show1">
          <h1>{{demo}}</h1>
        </div>
      </transition>
    </div>


    <div class="col-sm-3">
      <hello-world id="app" level="5" name="Erik">4</hello-world>

      <HelloWorld2 header="1" name="Erik"></HelloWorld2>
    </div>

    <div class="col-sm-3">
    <p v-style-me>
      {{welcome}}
    </p>
    <div v-style-me>Hi everybody</div>
    </div>

    <div class="col-sm-3">
      <h1>{{header}}</h1>
      <h2>{{welcome2}}</h2>
      <h3>{{counter2}}</h3>
      <button @click="increment">Press Me</button>
    </div>


    <div class="col-sm-12">
      <div class="col-md-3 col-md-offset-2">
        <my-comp1 class="comp1"></my-comp1>
      </div>
      <div class="col-md-3">
        <h2 class="text-center">Or</h2>
      </div>
      <div class="col-md-3">
        <my-comp2 class="comp2"></my-comp2>
      </div> <!--end col-md-2-->
    </div><!-- end row -->
  </div>


</template>
<script>
  import AppHeader from './AppHeader'
  import HelloWorld2 from './HelloWorld'
  import Vue from 'vue'
  import Vuex from 'vuex'

  const store = new Vuex.Store({
    state: {
      msg: 'Hello World',
      count: 0
    },
    mutations: {
      increment(state,payload) {
        state.count += payload;
      }
    },
    actions: {

    },
    getters: {
      blah(state) {
        return state.blah
      }
    }

  });
  const { mapGetters } = Vuex;


  const myButton = {
    methods: {
      pressed(val) {
        alert(val);
      }
    },
    data() {
      return {
        item: ''
      }
    }
  }

  const comp1 = {
    template: `<div>
    <h1>Enter Email</h1>
    <form>
      <div class="form-group">
        <input v-model="item" type="email" class="form-control" placeholder="Email Address"/>
      </div>
      <div class="form-group">
        <button class="btn btn-primary btn-lg" @click.prevent="pressed(item)">Press Button 1</button>
      </div>
    </form>
    </div>`,
    mixins: [myButton]

  }
  const comp2 = {
    template: `<div>
    <h1>Enter Number</h1>
      <form>
        <div class="form-group">
            <input v-model="item" class="form-control" placeholder="Phone Number"/>
        </div>
        <div class="form-group">
          <button class="btn btn-warning btn-lg" @click.prevent="pressed(item)">Press Button 2</button>
        </div>
      </form>
    </div>`,
    mixins:[myButton],

  }

  function addEventListener(el, done) {
    el.addEventListener("animationend", function() {
      el.style="";
      done();
    });
  };

  const BookComponent2 ={
    template: `
  <div>
      <slot name="header"></slot>
      <slot name="book"
        v-for="book in books"
        :text="book">
      </slot>
  </div>
  `,
    props: ['books']
  };
  const BookComponent ={
    template: `
  <div>
    Book Component
  </div>
  `
  };

  const FormComponent2 = {
    template: `
  <div>
    Form Component
  </div>
  `
  };

  const HeaderComponent = {
    template: `
  <div>
    Header Component
  </div>
  `
  };
  const FormComponent ={
    template: `
  <div>
    <form>
      <slot></slot>
      <label for="title">{{title}}</label> <input id="title" type="text" /><br/>
      <label for="author">{{author}}</label><input id="author" type="text" /><br/>

    </form>
  </div>

  `,
    props: ['title', 'author']
  };
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
  };
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
  const MyComponent5 ={
    template: `
  <div>
    <h1>Hello From Component</h1>
    <h2>More Stuff</h2>
    <slot name="erik"></slot>
  </div>
  `


  }
  export default {

    components:{'my-component': MyComponent,
      'book-component': BookComponent,
      'form-component2': FormComponent2,
      'header-component': HeaderComponent,
      myComp1: comp1,
      myComp2: comp2,
      MyComponent2,MyComponent3,MyComponent5,FormComponent,AppHeader,BookComponent2,
      HelloWorld2

    },


    data() {
      return {
        loggedIn:false,
        counter:0,
        lastPath:'',
        lastCounter:0,
        currentView: BookComponent,
        message: 'Hello World',
        titleLabel: 'The Title:',
        authorLabel: 'The Author:',
        header: 'Book List',
        books: [{author: 'John Smith', title: 'Best Of Times' },
          {author: 'Jane Doe', title: 'Go West Young Man' },
          {author: 'Avery Katz', title: 'The Life And Times Of Avery' }
        ],

        ccounter: 0,
        title: 'War and Peace',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elim',
        show: false,
        demo: 'Transition Me',
        show1: false,
        backClass: 'bk',
        blurClass: 'blur',
        welcome: 'Hello World'
      }
    },
    directives: {
      styleMe(el, binding, vnode, oldVnode) {
        bind: {
          el.style.color = "blue"
          el.style.fontSize = "42px";
          el.className = "text-center";
        }
      }
    },
    computed: {
      welcome2() {
        return store.state.msg
      },
      counter2() {
        return store.state.count;
      },
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
      },

      cycle() {
        if(this.currentView === HeaderComponent)
          this.currentView = BookComponent
        else
          this.currentView = this.currentView === BookComponent ? FormComponent2 : HeaderComponent;
      },

      enter(el, done) {
        console.log("enter");
        addEventListener(el,done);
        el.style.animationName = "bounceIn"
        el.style.animationDuration = "1.5s";
      },
      leave(el, done) {
        console.log("leave");
        addEventListener(el,done);
        el.style.animationName = "bounceIn"
        el.style.animationDuration = "1.5s";
        el.style.animationDirection="reverse";
      },
      beforeEnter(el) {
        console.log("before enter");
      },
      afterEnter(el) {
        console.log("after enter");
      },
      enterCancelled(el) {
        console.log("enter cancelled");
      },
      beforeLeave(el) {
        console.log("before leave");
      },
      afterLeave(el) {
        console.log("after leave");
      },
      leaveCancelled(el) {
        console.log("leave cancelled");
      },

      increment() {
        store.commit('increment', 10)
      }
    },


  }
</script>
<style>
  .bounce-enter-active {
    animation: bounceIn 2s;
  }
  .bounce-leave-active {
    animation: bounceIn 2s reverse;
  }

  @keyframes bounceIn {
    0% {
      transform: scale(0.1);
      opacity: 0;
    }
    60% {
      transform: scale(1.2);
      opacity: 1;
    }
    100% {
      transform: scale(1);
    }
  }
  .component-fade-enter-active, .component-fade-leave-active {
    transition: opacity 2.0s ease;
  }
  .component-fade-enter, .component-fade-leave-to {
    opacity: 0;
  }


  .fade-enter-active, .fade-leave-active {
    transition: opacity 2.5s ease-out;

  }

  .fade-enter, .fade-leave-to {
    opacity: 0;

  }

  .bk {
    transition: all 0.1s ease-out;
  }

  .blur {
    filter: blur(2px);
    opacity: 0.4;
  }

</style>

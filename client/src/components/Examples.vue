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
    <h1>
      <component :is="currentView"/>
    </h1>
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
  </div>
</template>
<script>
  import AppHeader from './AppHeader'
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
      MyComponent2,MyComponent3,MyComponent5,FormComponent,AppHeader,BookComponent2},


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

        ccounter: 0
      }
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
      }
    },

  }
</script>

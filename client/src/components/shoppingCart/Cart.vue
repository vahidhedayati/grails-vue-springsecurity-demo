<template>
  <div id="app">
    <app-header></app-header>
    <h1>Shopping Cart Example</h1>
    <hr>
    <h2>Products</h2>
    <ProductList/>
    <hr>
    <ShoppingCart/>
  </div>
</template>

<script>
import ProductList from './ProductList.vue'
import ShoppingCart from './ShoppingCart.vue'
import AppHeader from '../AppHeader'
export default {
  components: { ProductList, ShoppingCart,AppHeader }
}
</script>

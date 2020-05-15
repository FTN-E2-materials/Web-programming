const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: WebShop},
	    { path: '/sc', component: ShoppingCart }
	  ]
});

var app = new Vue({
	router,
	el: '#webShop'
});


Vue.component("web-shop", {
	data: function () {
		    return {
		      products: null
		    }
	},
	template: ` 
<div>
	Raspoloživi proizvodi:
	<table border="1">
	<tr bgcolor="lightgrey">
		<th>Naziv</th>
		<th>Cena</th>
		<th>&nbsp;</th>
	</tr>
		
	<tr v-for="p in products">
		<td>{{p.name }}</td>
		<td>{{p.price}}</td>
		<td>
			<input type="number" style="width:40px" size="3" v-model="p.count" name="itemCount"> 
			<input type="hidden" name="itemId" v-model="p.id"> 
			<button v-on:click="addToCart(p)">Dodaj</button>
		</td>
	</tr>
</table>
	<p>
		<a href="#/sc">Pregled sadržaja korpe</a>
	</p>
</div>		  
`
	, 
	methods : {
		addToCart : function (product) {
			axios
			.post('rest/proizvodi/add', {"id":''+product.id, "count":parseInt(product.count)})
			.then(response => (toast('Product ' + product.name + " added to the Shopping Cart")))
		}
	},
	mounted () {
        axios
          .get('rest/proizvodi/getJustProducts')
          .then(response => (this.products = response.data))
    },
});
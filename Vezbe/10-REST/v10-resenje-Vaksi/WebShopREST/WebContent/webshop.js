// Closure koji će zapamtiti trenutni proizvod
function clickClosure(product){
	return function() {
		// Parametar product prosleđen u gornju funkciju će biti vidljiv u ovoj
		// Ovo znači da je funkcija "zapamtila" za koji je proizvod vezana
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	};
}

function addProductTr(product) {
	let tr = $('<tr></tr>');
	let tdNaziv = $('<td>' + product.name + '</td>');
	let tdCena = $('<td>' + product.price + '</td>');
	tr.append(tdNaziv).append(tdCena);
	tr.click(clickClosure(product));
	$('#tabela tbody').append(tr);
}

$(document).ready(function() {
	$.get({
		url: 'rest/products',
		success: function(products) {
			for (let product of products) {
				addProductTr(product);
			}
		}
	});
	
	$('button#dodaj').click(function() {
		$('form#forma').show();
	});
	
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let name = $('input[name="name"]').val();
		let price = $('input[name="price"]').val();
		if (!price || isNaN(price)) {
			$('#error').text('Cena mora biti broj!');
			$("#error").show().delay(3000).fadeOut();
			return;
		}
		$('p#error').hide();
		$.post({
			url: 'rest/products',
			data: JSON.stringify({id: '', name: name, price: price}),
			contentType: 'application/json',
			success: function(product) {
				$('#success').text('Novi proizvod uspešno kreiran.');
				$("#success").show().delay(3000).fadeOut();
				// Dodaj novi proizvod u tabelu
				addProductTr(product);
			}
		});
	});
});
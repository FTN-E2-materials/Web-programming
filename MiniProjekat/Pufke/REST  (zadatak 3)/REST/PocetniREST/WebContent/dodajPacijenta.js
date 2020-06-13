$(document).ready(function() {
	
	$('button#p').click(function() {
		event.preventDefault();
		
		let br = $('input[name="brojZdravstvenogOsiguranja"]').val();
		let ime = $('input[name="imePacijenta"]').val();
		let prezime = $('input[name="prezimePacijenta"]').val();
		let datumRodjenja = $('input[name="datumRodjenjaPacijenta"]').val();
		let pol = $('select[name="pol"]').val();
		let zdravstveniStatus = $('select[name="zdravstveniStatus"]').val();
		
		let data = {
				"brZdravstvenogOsig" : br,
				"datumRodjenja" : datumRodjenja,
				"ime" :ime ,
				"pol" :pol ,
				"prezime" :prezime ,
				"zdravstveniStatus" : zdravstveniStatus};
		
		$.post({
			url: 'rest/pacijenti/setPacijenti',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success: function() {
				$('#success').text(' uspešno kreiran.');
				$("#success").show().delay(3000).fadeOut();
				
			}
		});
		
	});
	
	
	
});
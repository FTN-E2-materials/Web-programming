$(document).ready(function() {
	
	$('button#p').click(function() {
		event.preventDefault();
		
		let br = $('input[name="brojIzrade"]').val();
		let ime = $('input[name="imeNarucioca"]').val();
		let brTel = $('input[name="brojTelefona"]').val();
		let nazFoldera = $('input[name="nazivFoldera"]').val();
		let formatFoto = $('select[name="formatFotografija"]').val();
		let cenaIzrade = $('select[name="cenaIzrade"]').val();
		
		let data = {
				"statusIzrade" : "UIZRADI",
				"brojIzrade" : br,
				"imeNarucioca" : ime,
				"brojTelefona" :brTel ,
				"nazivFoldera" :nazFoldera ,
				"formatFotografija" :formatFoto ,
				"cenaIzrade" : cenaIzrade};
		
		$.post({
			url: 'rest/porucioci/setPorucioci',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success: function() {
				$('#success').text(' uspešno kreiran.');
				$("#success").show().delay(3000).fadeOut();
				Console.Write("BRAVOOO");
			}
		});
		
	});
	
	
	
});
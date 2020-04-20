// $(document).ready(function(){ ... }) je ekvivalentan kodu document.addEventListener('ready', function(){ ... })
$(document).ready(function() {
	// Postavi stil th elementima kroz JQuery
	$('th').css('background', 'lightblue').css('font-weight', 'bold');
	
	//Šalje se GET zahtev na adresu ispod
	$.get({
		url: '/OnlineService/UserServlet',
		success: function(data) {
			// Odgovor se nalazi u data
			// Pošto očekujemo JSON listu, možemo da iteriramo kroz data
			for (let user of data) {
				let tbody = $('#korisnici tbody');
				// Kreiramo DOM elemente za svakog korisnika uz
				// pomoć JQuery biblioteke
				let firstName = $('<td>' + user.firstName + '</td>');
				let lastName = $('<td>' + user.lastName + '</td>');
				let username = $('<td>' + user.username + '</td>');
				let email = $('<td>' + user.email + '</td>');
				let phoneNumber = $('<td>' + user.phoneNumber + '</td>');
				// Dodajemo sva polja u red, koji dodajemo u telo tabele
				let tr = $('<tr></tr>');
				tr.append(firstName)
				.append(lastName)
				.append(username)
				.append(email)
				.append(phoneNumber);
				tbody.append(tr);
			}
		}
	});
});


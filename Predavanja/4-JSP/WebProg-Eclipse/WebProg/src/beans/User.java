package beans;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = -6365144622373103590L;

	public User() {
		username = "";
		password = "";
		loggedIn = false;
		address = new Address();

		// inicializacija atributa za primere Expression Language
		niz2 = new java.util.ArrayList<String>();
		niz2.add("ABC");
		niz2.add("DEF");
		mapa = new java.util.HashMap<String, String>();
		mapa.put("jedan", "ONE");
		mapa.put("dva", "TWO");
	}

	private String[] niz1 = { "prvi", "drugi", "treci" };

	public String[] getNiz1() {
		return niz1;
	}

	private java.util.ArrayList<String> niz2;

	public java.util.ArrayList<String> getNiz2() {
		return niz2;
	}

	private java.util.HashMap<String, String> mapa;

	public java.util.HashMap<String, String> getMapa() {
		return mapa;
	}

	public void setUsername(String x) {
		username = x;
		System.out.println("User.username: " + x);
	}

	public void setPassword(String x) {
		password = x;
		System.out.println("User.password: " + x);
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Address getAddress() {
		return address;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean b) {
		loggedIn = b;
	}

	public boolean login() {
		if (username.equals("proba") && password.equals("proba")) {
			loggedIn = true;
			address.setStreet("JSP Street");
			address.setNumber(2);
		} else {
			loggedIn = false;
			address.setStreet("");
			address.setNumber(-1);
		}
		return loggedIn;
	}

	public void logoff() {
		username = "";
		password = "";
		loggedIn = false;
		address.setStreet("");
		address.setNumber(-1);
	}

	private String username;
	private String password;
	private boolean loggedIn;
	private Address address;
}

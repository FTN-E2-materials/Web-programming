package model;

/**
 * Predstavnica modela YYYYY
 * @author Vaxi
 *
 */
public class User {
	
	private String name;
	private String username;
	

	public User(String name, String username) {
		super();
		this.name = name;
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}

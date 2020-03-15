package primer02;

public class Auto implements java.io.Serializable {

	private static final long serialVersionUID = -862084465132757215L;

	public Auto() {
		upaljen = false;
	}

	public void upali() {
		upaljen = true;
	}

	public boolean radi() {
		return upaljen;
	}

	private boolean upaljen;
}
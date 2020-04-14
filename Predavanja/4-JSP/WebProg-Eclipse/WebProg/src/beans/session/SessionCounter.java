package beans.session;

public class SessionCounter implements java.io.Serializable {

	private static final long serialVersionUID = 455872145001355823L;
	
	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int c) {
		count = c;
	}

	public void inc() {
		count++;
	}
}

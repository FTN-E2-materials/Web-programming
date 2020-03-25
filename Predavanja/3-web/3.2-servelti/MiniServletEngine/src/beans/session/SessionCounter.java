package beans.session;

public class SessionCounter {
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
package primer04;

import java.util.concurrent.Semaphore;

public class ResourcePool {

	private static final int MAX_AVAILABLE = 10;
	// Not a particularly efficient data structure; just for demo
	protected Object[] items = new Object[MAX_AVAILABLE]; // whatever kinds of
															// items being
															// managed
	protected boolean[] used = new boolean[MAX_AVAILABLE];
	private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

	public ResourcePool() {
		for (int i = 0; i < MAX_AVAILABLE; i++) {
			items[i] = "Object" + i;
		}
	}

	public Object getItem() throws InterruptedException {
		available.acquire();
		return getNextAvailableItem();
	}

	public void putItem(Object x) {
		if (markAsUnused(x))
			available.release();
	}

	protected synchronized Object getNextAvailableItem() {
		for (int i = 0; i < MAX_AVAILABLE; ++i) {
			if (!used[i]) {
				used[i] = true;
				return items[i];
			}
		}
		return null; // not reached
	}

	protected synchronized boolean markAsUnused(Object item) {
		for (int i = 0; i < MAX_AVAILABLE; ++i) {
			if (item == items[i]) {
				if (used[i]) {
					used[i] = false;
					return true;
				} else
					return false;
			}
		}
		return false;
	}

}
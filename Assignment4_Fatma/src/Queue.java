import java.util.*;

public class Queue<T> {

	protected Vector<T> buffer; 

	public Queue() {
		buffer = new Vector<T>();
	}

	public synchronized void insert(T item) {
		buffer.add(item);
		this.notifyAll();
	}

	public synchronized T extract() throws InterruptedException {
		while (buffer.isEmpty()){
			this.wait();
		}
		T t = buffer.elementAt(0);
		buffer.remove(t);
		return t;
	}
	public synchronized boolean isEmpty() {
		if(this.buffer.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public Vector<T> getBuffer() {
		return this.buffer;
	}
}

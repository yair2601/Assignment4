import java.util.*;

public class Queue<T> {

	private Vector<T> buffer; 

	public Queue() {
		buffer = new Vector<T>();
	}

	public synchronized void insert(T item) {
		buffer.add(item);
		this.notifyAll();
		System.out.println("i am in ");
	}

	public synchronized T extract() throws InterruptedException {
		while (buffer.isEmpty()){
			this.wait();
		}
		T t = buffer.elementAt(0);
		buffer.remove(t);
		return t;
	}
}

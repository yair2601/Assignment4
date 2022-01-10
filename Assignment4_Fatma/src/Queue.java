import java.util.*;

public class Queue<T> {

	protected Vector<T> buffer; 

	public Queue() {
		buffer = new Vector<T>();
	}

	public synchronized void insert(T item) {//insert the item to the queue
		buffer.add(item);
		this.notifyAll();
	}

	public synchronized T extract()  {//extract the item from the queue
		while (buffer.isEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T t = buffer.elementAt(0);
		buffer.remove(t);
		return t;
	}
	public synchronized boolean isEmpty() {//check if the queue is empty
		if(this.buffer.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public Vector<T> getBuffer() {//get the queue
		return this.buffer;
	}
}

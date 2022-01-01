import java.util.Vector;

public class BoundedQueue<T> extends Queue<T>{
	//private Vector<T> buffer;
	private int maxSize;

	public BoundedQueue(int maxSize){
		super();
		this.maxSize=maxSize;
		}

	public synchronized void insert(T item) {
		while(buffer.size()>=maxSize) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		buffer.add(item);
		this.notifyAll(); }

}
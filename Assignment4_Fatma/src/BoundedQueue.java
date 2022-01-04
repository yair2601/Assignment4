import java.util.Vector;

public class BoundedQueue<T> extends Queue<T>{
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
				e.printStackTrace();
			}
		}


		buffer.add(item);
		this.notifyAll(); }

	public synchronized T extract(){
		while (buffer.isEmpty())
			try {
				this .wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		T item = buffer.elementAt(0);
		buffer.remove(item);
		this.notifyAll();
		return item;}

}
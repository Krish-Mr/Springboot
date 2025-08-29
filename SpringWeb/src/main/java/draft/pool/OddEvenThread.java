package draft.pool;

public class OddEvenThread{

	public static void main(String[] args) throws InterruptedException {
		Runnable r1 = ()->{
			for(int i=0; i<10; i+=2) {
				System.out.println(Thread.currentThread().getName() + " : Even Numbers: "+i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable r2 = ()->{
			for(int i=1; i<10; i+=2) {
				System.out.println(Thread.currentThread().getName() + " : Odd Numbers: "+i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}
}

package draft.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class ThreadRCE {
	//No args, No return type
	public static Runnable rt = ()-> {
		System.out.println("Runnable -> run: " + Thread.currentThread().getName());
		try { Thread.currentThread().sleep(2000); } catch (Exception e) {  e.printStackTrace(); }
	};
	
	//No args, with return type
	public static Callable ct = () -> {System.out.println("Callable -> call: " + Thread.currentThread().getName());
	Thread.currentThread().sleep(2000);
	return 0;};
	
	//Runnable as args, No return type
	public static Executor et = (r)-> {
		System.out.println("Executor -> execute: " + Thread.currentThread().getName());
		r.run();
		try { Thread.currentThread().sleep(2000); } catch (Exception e) {  e.printStackTrace(); }
	};
	
	public static void main(String[] args) throws Exception{
		//which all run in main thread only. no thread are created
		rt.run();
		ct.call();
		et.execute(rt);
	}

}

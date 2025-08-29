package draft.pool;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorExample {

	public static void main(String[] args) {
		System.out.println("Available Processor:  "+Runtime.getRuntime().availableProcessors());

		ExecutorService ex;

		//No args, No return type
		Runnable r = ()-> System.out.println(Thread.currentThread().getName() + "-Runnable-> run method");
		//No args, with return type
		Callable<?> c = ()-> {System.out.println(Thread.currentThread().getName() + "-Callable-> call method");return -1;};
		Callable<?> c1 = ()-> {
			System.out.println(Thread.currentThread().getName() + "-Callable-> call method and wait for 5sec");
			Thread.currentThread().sleep(5000);
				return new Random().nextInt();
			};
		//Runnable as a args, No return type
		Executor e = (r1)-> System.out.println(Thread.currentThread().getName() + "-Executor-> execute method");
		
		e.execute(r);
		r.run();
		try {
			c.call();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		List task = Arrays.asList(c,c1);
		ex = Executors.newCachedThreadPool();
		Executors.newScheduledThreadPool(2);
		Future<?> submit = ex.submit(r);

		 try {
			int taskRes = (int) ex.invokeAny(task);
			System.out.println("Result of invokeAny: "+taskRes);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		 
		 try {
			List<Future<?>> invokeAll = ex.invokeAll(task);
			invokeAll.forEach(e1->{
				try {
					System.out.println("InvokeAll result: " + e1.get());
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				} catch (ExecutionException e2) {
					e2.printStackTrace();
				}
			});
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		 
		 System.out.println("isShutdown: "+ex.isShutdown());
		 System.out.println("isTerminated: "+ex.isTerminated());
		 
		 try {
			ex.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		 List<Runnable> shutdownNow = ex.shutdownNow();
		 System.out.println("If empty all task are completed" + shutdownNow);
		 
		 System.out.println("isShutdown: "+ex.isShutdown());
		 System.out.println("isTerminated: "+ex.isTerminated());
	}
}

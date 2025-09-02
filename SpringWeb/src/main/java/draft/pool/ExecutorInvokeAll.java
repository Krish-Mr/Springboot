package draft.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorInvokeAll {
	public static Callable<Integer> c1 = () -> {
			Thread.currentThread().sleep(1000);
			System.out.println("Callable - c1");
			return 1;
		};
	public static Callable<Integer> c2 = () -> {
		Thread.currentThread().sleep(2000);
		System.out.println("Callable - c2");
		return 2;
		};
	public static Callable<Integer> c3 = () -> {
		Thread.currentThread().sleep(3000);
		System.out.println("Callable - c3");
		return 3;
		};
	public static Callable<Integer> c4 = () -> {
		Thread.currentThread().sleep(4000);
		System.out.println("Callable - c4");
		return 4;
		};

	public static void main(String[] args) {
//		executorService();
//		completableFutureStructure();
		completableFuture();
	}

	public static void executorService() {
		ExecutorService ex = Executors.newFixedThreadPool(4);
		try {
			List<Callable<Integer>> callableList = Arrays.asList(c1,c2,c3,c4);
			System.out.println("InvokeAll");
			List<Future<Integer>> resultList = ex.invokeAll(callableList);
			resultList.forEach(e->{
				try {
					System.out.println(e.get());
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			});
			System.out.println("\nInvokeAny:");
			Integer resultAny = ex.invokeAny(callableList);
			System.out.println(resultAny);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void completableFutureStructure() {
		ExecutorService ex = Executors.newFixedThreadPool(3);
		 ex = new ThreadPoolExecutor(1, 5, 5, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>());
		 /* Runnable - runAsync 
		  * No return So No Result */
		 CompletableFuture.runAsync(()->{});

		/* Supplier - supplyAsync 
		 * Return value
		 * used for callback chaining */
		CompletableFuture<Object> supplyAsync = CompletableFuture.supplyAsync(()-> 0);

			/* Function - thenApply 
			 * return CompletableFuture<U> */
			supplyAsync.thenApply(r -> r);
			supplyAsync.thenApplyAsync(r->r);
			supplyAsync.thenApplyAsync(r->r, ex);
			/* Consumer - thenAccept
			 * Return but CompletableFuture<void> - no use */
			supplyAsync.thenAccept(r-> {});
			supplyAsync.thenAcceptAsync(r-> {});
			supplyAsync.thenAcceptAsync(r-> {}, ex);
			/* Runnable - thenRun 
			 * Return but CompletableFuture<void> - no use */
			supplyAsync.thenRun( ()->{} );
			supplyAsync.thenRunAsync( ()->{} );
			supplyAsync.thenRunAsync( ()->{}, ex );
			
	}

	public static void completableFuture() {
		CompletableFuture<String> s1 = CompletableFuture.supplyAsync(()->" Hello ");
		CompletableFuture<String> s2 = CompletableFuture.supplyAsync(()->" World! ");
		CompletableFuture<String> s3 = CompletableFuture.supplyAsync(()->", Welcome to Learning ");
		CompletableFuture<String> s4 = CompletableFuture.supplyAsync(()-> " Combine the result: ");
		//Return Void, just check isDone
		CompletableFuture<Void> allRes = CompletableFuture.allOf(s1,s2,s3);

		try {
		//Perform on the result of two task (after computation)
		CompletableFuture<String> res = s1.thenCombine(s2, (e1,e2)-> e1.concat(e2).trim())
				.thenCombine(s3, (e1,e2)-> e1.concat(e2).trim());
		System.out.println(res.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		//thenApply - with the result of the Computation
		CompletableFuture<String> thenApply = s1.thenApply(e->e.toLowerCase() + " -> apply");
		//the task is dependant on the previous result
		s4.thenCompose(r-> s1.thenApply((e)-> r + e + " Gokul...")).thenAccept(System.out::println);
	}
}
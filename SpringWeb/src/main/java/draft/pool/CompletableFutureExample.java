package draft.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompletableFutureExample {

	private static volatile int i = 0;
	static Runnable rt = ThreadRCE.rt;
	static Callable ct = ThreadRCE.ct;
	static Executor et = ThreadRCE.et;
	static Supplier st = ()->{System.out.println("Supplier -> get: "+Thread.currentThread().getName());try{Thread.currentThread().sleep(2000);} catch (InterruptedException e){e.printStackTrace();} return i++;};
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		CompletableFuture cf = new CompletableFuture();
//		cf.get();	//it'll run infinity
		System.out.println("Default-> ForkJoin");
		CompletableFuture<Void> rRes = cf.runAsync(rt);
		CompletableFuture cRes = cf.supplyAsync(st);
		System.out.println(rRes.get());
		System.out.println(cRes.get());
		
		System.out.println("\nExecutor");
		Executor ex = Executors.newFixedThreadPool(2);
		CompletableFuture cxRes1 = cf.supplyAsync(st, ex);
		CompletableFuture cxRes2 = cf.supplyAsync(st, ex);
		CompletableFuture cxRes3 = cf.supplyAsync(st, ex);

		System.out.println(cxRes1.get());
		System.out.println(cxRes2.get());
		System.out.println(cxRes3.get());

		System.out.println("\nAll Of");
		CompletableFuture<Void> allOf = CompletableFuture.allOf(cxRes1,cxRes2,cxRes3);
		allOf.join();

//		CompletableFuture.anyOf(cxRes1,cxRes2,cxRes3);
	}

}

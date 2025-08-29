package draft.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Future.State;

public class ThreadExecutorExample {

	static Runnable rt = ThreadRCE.rt;
	static Callable ct = ThreadRCE.ct;
	static Executor et = ThreadRCE.et;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(rt);
		Future<?> runnableRes = es.submit(rt);
		Future callableRes = es.submit(ct);	//waiting for thread to execute a task

		System.out.println(runnableRes.get());
//		callableRes.cancel(true); //can't do any further action with callable once cancelled [cancellationException]
		System.out.println(callableRes.get());
		System.out.println(callableRes.get());
//		State.RUNNING, State.SUCCESS, State.FAILED, State.CANCELLED 
		State state = callableRes.state();
		System.out.println(state);

		es.shutdown();

	}

}

package mddemo.library.com.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日20:12:42
 * Description:创建一个线程
 */

public class ThreadUtils {

	private ThreadUtils() {

	}
	private static ThreadUtils instance = new ThreadUtils();
	private ThreadPoolProxy longPool;
	private ThreadPoolProxy shortPool;
	public static ThreadUtils getInstance() {
		return instance;
	}
	public synchronized ThreadPoolProxy createLongPool() {
		if (longPool == null) {
			longPool = new ThreadPoolProxy(5, 5, 5000L);
		}
		return longPool;
	}
	public synchronized ThreadPoolProxy createShortPool() {
		if(shortPool==null){
			shortPool = new ThreadPoolProxy(3, 3, 5000L);
		}
		return shortPool;
	}
	public class ThreadPoolProxy {
		private ThreadPoolExecutor pool;
		private int corePoolSize;
		private int maximumPoolSize;
		private long time;
		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;

		}
		public void execute(Runnable runnable) {
			if (pool == null) {
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						time, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable); 
		}
		public void cancel(Runnable runnable) {
			if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
				pool.remove(runnable); 
			}
		}
	}
}

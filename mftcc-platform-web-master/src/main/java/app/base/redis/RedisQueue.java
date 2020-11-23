package app.base.redis;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

public  class RedisQueue<T> implements InitializingBean,DisposableBean{
	
 	private RedisTemplate redisTemplate;  
	private RedisConnectionFactory factory;
	private RedisConnection connection;
	private Lock lock = new ReentrantLock();
	private RedisQueueListener<T> listener;
	private Thread listenerThread;  
    private boolean isClosed;  
    private String key;
    
    public void setRedisTemplate(RedisTemplate redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    }  
  
    public  void setListener(RedisQueueListener<T> listener) {
    	this.listener = listener;
    	
    }
    
    public  void setKey(String key) {
    	this.key = key;
    }
    
    
    @Override  
    public void afterPropertiesSet() throws Exception {  
        factory = redisTemplate.getConnectionFactory();  
        connection = RedisConnectionUtils.getConnection(factory);  
        if(listener != null){  
            listenerThread = new ListenerThread();  
            listenerThread.setDaemon(true);  
            listenerThread.start();  
        }  
    }  
    
    public T takeFromHead() throws InterruptedException{  
        return takeFromHead(0);  
    }  
    
    public T takeFromHead(int timeout) throws InterruptedException{  
        lock.lockInterruptibly();  
        try{  
            List<byte[]> results = connection.bLPop(timeout, key.getBytes());  
            
            if(CollectionUtils.isEmpty(results)){  
                return null;  
            }
            String result = new String(results.get(1));
            return (T)result;
        }finally{  
            lock.unlock();  
        }  
    }  
    
    @Override  
    public void destroy() throws Exception {  
        if(isClosed){  
            return;  
        }  
        shutdown();  
        RedisConnectionUtils.releaseConnection(connection, factory);  
    }  
    
    private void shutdown(){  
        try{  
            listenerThread.interrupt();  
        }catch(Exception e){  
            //  
        }  
    }  
	
	 public class ListenerThread extends Thread {
         
	        @Override  
	        public void run(){  
	            try{  
	                while(true){  
	                    T value = takeFromHead();//cast exceptionyou should check.  
	                    if(value != null){  
	                        try{  
	                            listener.onMessage(value);  
	                        }catch(Exception e){  
	                            e.printStackTrace();
	                        }  
	                    }  
	                }  
	            }catch(InterruptedException e){  
	                e.printStackTrace();
	            }  
	        }  
	    }  


}

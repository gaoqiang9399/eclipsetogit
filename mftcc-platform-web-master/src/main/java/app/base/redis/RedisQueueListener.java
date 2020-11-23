package app.base.redis;

public interface RedisQueueListener<T> {
	public void onMessage(T value);  
}

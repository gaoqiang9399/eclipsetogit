package app.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 
 * @author hxf
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	private static Logger logger = LoggerFactory.getLogger(ServiceException.class);

	public ServiceException(String message) {
		super(message);
		logger.error("捕获ServiceException-{}", message, this);
	}

	public ServiceException(Exception cause) {
		super(cause);
		logger.error("捕获ServiceException", cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		logger.error("捕获ServiceException-{}", message, cause);
	}
}

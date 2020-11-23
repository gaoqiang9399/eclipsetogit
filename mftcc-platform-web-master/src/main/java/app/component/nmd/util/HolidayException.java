package app.component.nmd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HolidayException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 53561246655366L;
	private static Logger log = LoggerFactory.getLogger(HolidayException.class);

	public HolidayException() {
		super();
	}

	public HolidayException(String message) {
		super(message);
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	public HolidayException(Exception cause) {
		super(cause);
		if (log.isDebugEnabled()) {
			log.debug(cause.getMessage());
		}
	}

	public HolidayException(String message, Throwable cause) {
		super(message, cause);
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}
}

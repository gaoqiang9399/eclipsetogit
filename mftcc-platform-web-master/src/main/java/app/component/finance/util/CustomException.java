/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CustomException.java
 * 包名： cn.mftcc.util
 * 说明：
 * @author Javelin
 * @date 2017-1-4 下午4:23:04
 * @version V1.0
 */ 
package app.component.finance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类名： CustomExp
 * 描述：自定义异常，使用该异常类抛出异常可捕获显示
 * @author Javelin
 * @date 2017-1-4 下午4:23:04
 *
 *
 */
public class CustomException extends RuntimeException {
private static final long serialVersionUID = 3583566093089790852L;
	
	private static Logger log = LoggerFactory.getLogger(CustomException.class);
	
	public CustomException() {
		super();
	}

	public CustomException(String message) {
		super(message);
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	public CustomException(Exception cause) {
		super(cause);
		if (log.isDebugEnabled()) {
			log.debug(cause.getMessage());
		}
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}
}

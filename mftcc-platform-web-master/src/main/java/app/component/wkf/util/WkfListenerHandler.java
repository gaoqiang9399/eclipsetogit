package app.component.wkf.util;

import java.lang.reflect.Method;
public class WkfListenerHandler {
	
	public static void eventHandler(String className,String relNo) throws Exception {
		Class c = Class.forName(className);
		Method method = null;
		method = c.getDeclaredMethod("notify", new Class[]{String.class});
		if(method!=null) {
			method.invoke(c.newInstance(),relNo);
		}
	}
}

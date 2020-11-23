package app.base;



import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SourceTemplate implements ApplicationContextAware {
//	@Autowired
	private static ApplicationContext context;
	
	@Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
		SourceTemplate.context = applicationContext;  
    } 

	public static BeanFactory getSpringContextInstance() {
		if (context == null) {
//			context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
			throw new RuntimeException("could not find spring context, please initialize and set context.");
		}
		return (BeanFactory) context;
	}
	public static Object getBean(String name) {
		return getSpringContextInstance().getBean(name);
	}

	public static ApplicationContext getContext() {
		return context;
	}
	
	public static ApplicationContext getAc() {
		return context;
	}

	public static void setContext(ApplicationContext applicationContext) {
		if( null == context ) {
			SourceTemplate.context = applicationContext;
		}
	}

}

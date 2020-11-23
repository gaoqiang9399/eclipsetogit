package app.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.core.struts.SystemData;
import com.core.util.oscache.ScreenCache;


@Component
@Order(value = 1)
public class CacheInitStartRunner implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public void run(String... as) throws Exception {
		logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加载redis缓存开始<<<<<<<<<<<<<");
		
		logger.info("****************** 加载表单缓存 开始 ******************");
		try {
			SystemData.loadPath();
			ScreenCache screenCache = new ScreenCache();
			screenCache.reloadFormXmlInCache();
			screenCache.reloadTableXmlInCache();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("****************** 加载表单缓存  结束 ******************");
		
		logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加载redis缓存结束<<<<<<<<<<<<<");
	}
}

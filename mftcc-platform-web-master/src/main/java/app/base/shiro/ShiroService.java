package app.base.shiro;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.component.pms.entity.PmsEntrance;
import app.component.pms.entity.PmsViewpoint;
import app.component.pms.feign.PmsEntranceFeign;
import app.component.pms.feign.PmsViewpointFeign;

/**
 * 
 * @author 作者: John.Yu
 * @date 创建时间：2017年10月13日 下午3:16:07
 */
@Service
public class ShiroService {
	private static final Logger logger = LoggerFactory.getLogger(ShiroService.class);
	@Autowired(required = false)
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	@Autowired
	private PmsEntranceFeign pmsEntranceFeign;
	@Autowired
	private PmsViewpointFeign pmsViewpointFeign;

	@Autowired(required = false)
	private SysShiroRealm sysShiroRealm;

	/**
	 * 初始化权限
	 * @throws Exception 
	 */
	public Map<String, String> loadFilterChainDefinitions() throws Exception {
		/////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		// filterChainDefinitionMap.put("/user", "authc");//
		// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
		// anon：它对应的过滤器里面是空的,什么都没做
		logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
		// filterChainDefinitionMap.put("/sysUser/main", "anon");//登陆提交url
		// //filterChainDefinitionMap.put("/user/edit/**", "perms[user:edit]");//
		// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取//perms[roleIDt]

		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/CodeImage.jsp", "anon");
		filterChainDefinitionMap.put("/UIplug/**", "anon");
//		filterChainDefinitionMap.put("/themes/**", "anon");
		filterChainDefinitionMap.put("/sysLogin/403", "anon");
		filterChainDefinitionMap.put("/sysLogin/userLogin", "anon");
		filterChainDefinitionMap.put("/sysLogin/shiroCheck", "anon");
		filterChainDefinitionMap.put("/sysLogin/userLogout", "anon");
//		filterChainDefinitionMap.put("/left", "anon");
		filterChainDefinitionMap.put("/sysLogin/login", "anon");
		filterChainDefinitionMap.put("/sysLogin/checkExist", "anon");
		filterChainDefinitionMap.put("/sysLogin/sessionInvalid", "anon");
		filterChainDefinitionMap.put("/sendMsg/sendMessageAuto/**", "anon");//为了service调用而放开权限控制。
		filterChainDefinitionMap.put("/helpUserMsg/**", "anon");
		filterChainDefinitionMap.put("/dwr/**", "anon");
		filterChainDefinitionMap.put("/report_new/**", "anon");
		filterChainDefinitionMap.put("/component/talk/SysTalkRecord_Main.jsp", "anon");
		
		filterChainDefinitionMap.put("/sysSkip/skipToC", "authc");// authc 需要认证
		filterChainDefinitionMap.put("/sysTaskInfo/**", "authc");
		filterChainDefinitionMap.put("/bwmTaskRoleRel/**", "authc");
		filterChainDefinitionMap.put("/pmsUserFilter/**", "authc");
		filterChainDefinitionMap.put("/sysDescTemp/updateSession", "authc");
		List<PmsEntrance> entranceList = pmsEntranceFeign.getAllPmsEntrance();
		List<PmsViewpoint> pointList = pmsViewpointFeign.getAllPmsViewpoint();
		for (Iterator iterator = entranceList.iterator(); iterator.hasNext();) {
			PmsEntrance pmsEntrance = (PmsEntrance) iterator.next();
		filterChainDefinitionMap.put(pmsEntrance.getEntranceUrl(), pmsEntrance.getRoleNo());// "anyofroles[\"01,150541\"]",在sql中做的处理
		// "perms[\"01,150541\"]"-------这个是and关系
		System.out.println(pmsEntrance.getEntranceUrl() + "===" + pmsEntrance.getRoleNo());
	}
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			PmsViewpoint pmsViewpoint = (PmsViewpoint) iterator.next();
		filterChainDefinitionMap.put(pmsViewpoint.getViewpointMenuUrl(), pmsViewpoint.getRoleNo());// "anyofroles[\"01,150541\"]",在sql中做的处理
		System.out.println(pmsViewpoint.getViewpointMenuUrl() + "===" + pmsViewpoint.getRoleNo());
	}
//		filterChainDefinitionMap.put("/**", "roles[admin]");// anon 可以理解为不拦截roles[admin]

		// 写在上面的优先级最高
		return filterChainDefinitionMap;
	}

	/**
	 * 重新加载权限
	 * @throws Exception 
	 */
	public void updatePermission() throws Exception {
		synchronized (shiroFilterFactoryBean) {
			// 清空shiro缓存
			sysShiroRealm.clearAllCachedAuthorizationInfo();
			AbstractShiroFilter shiroFilter = null;
			try {
				shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
			} catch (Exception e) {
				throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
			}
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
			// 清空老的权限控制
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
			// 重新构建生成
			Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim().replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
			System.out.println("更新权限成功！！");
		}
	}
}
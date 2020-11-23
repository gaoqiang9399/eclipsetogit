package app.config;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import app.base.shiro.SysShiroRealm;
import app.base.shiro.MShiroFilterFactoryBean;
import app.base.shiro.AnyOfRolesAuthorizationFilter;
import app.base.shiro.CustomFormAuthenticationFilter;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.component.pms.feign.PmsEntranceFeign;
import app.component.pms.feign.PmsViewpointFeign;

/**
 * Shiro 配置
 */
@Configuration
public class ShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.shiro.sessionTimeOut}")
    private int sessionTimeOut;

    @Value("${mftcc.webservice.enable}")
    private String enable;

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @throws Exception
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean, PmsEntranceFeign pmsEntranceFeign, PmsViewpointFeign pmsViewpointFeign) throws Exception {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // filterChainDefinitionMap.put("/user", "authc");//
        // 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
        // anon：它对应的过滤器里面是空的,什么都没做
        logger.info


                ("##################从数据库读取权限规则，加载到shiroFilter中##################");
        // filterChainDefinitionMap.put("/sysUser/main", "anon");//登陆提交url
        // //filterChainDefinitionMap.put("/user/edit/**",
        // "perms[user:edit]");//
        // 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取//perms[roleIDt]
//		filterChainDefinitionMap.put("/newmain20", "anon");// anon 匿名，可以理解为不拦截
//		filterChainDefinitionMap.put("/main", "anon");
//		filterChainDefinitionMap.put("/include/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/CodeImage.jsp", "anon");
        filterChainDefinitionMap.put("/UIplug/**", "anon");
//		filterChainDefinitionMap.put("/themes/**", "anon");
        filterChainDefinitionMap.put("/sysLogin/403", "anon");
        filterChainDefinitionMap.put("/sysLogin/userLogin", "anon");
        filterChainDefinitionMap.put("/sysLogin/shiroCheck", "anon");
        filterChainDefinitionMap.put("/sysLogin/coreLogin", "anon");
//		filterChainDefinitionMap.put("/left", "anon");
        filterChainDefinitionMap.put("/sysLogin/login", "anon");
        filterChainDefinitionMap.put("/sysLogin/checkExistAjax", "anon");
        filterChainDefinitionMap.put("/sysLogin/sessionInvalid", "anon");
        filterChainDefinitionMap.put("/sendMsg/sendMessageAuto/**", "anon");//为了service调用而放开权限控制。
        filterChainDefinitionMap.put("/helpUserMsg/**", "anon");
        filterChainDefinitionMap.put("/dwr/**", "anon");
        filterChainDefinitionMap.put("/component/talk/SysTalkRecord_Main.jsp", "anon");
        filterChainDefinitionMap.put("/error.jsp", "anon");
        filterChainDefinitionMap.put("/mfToPageOffice/**", "anon");
        filterChainDefinitionMap.put("/mfSysTemplate/**", "anon");
        filterChainDefinitionMap.put("/pssPrintBill/**", "anon");
        filterChainDefinitionMap.put("/pageOffice/**", "anon");
        filterChainDefinitionMap.put("/pageOfficeFactor/**", "anon");
        filterChainDefinitionMap.put("/mfTrenchUser/trench", "anon");
        filterChainDefinitionMap.put("/report_new/**", "anon");
        filterChainDefinitionMap.put("/sysImg/**", "anon");
        filterChainDefinitionMap.put("/hystrix.stream", "anon");
        filterChainDefinitionMap.put("/cusConform/**", "anon");
        filterChainDefinitionMap.put("/batchPrint/**", "anon");
        filterChainDefinitionMap.put("/component/batchprint/*.jsp", "anon");
        filterChainDefinitionMap.put("/component/batchprint/batchDoc/*.doc", "anon");
        filterChainDefinitionMap.put("/component/batchprint/batchDocTemp/*.doc", "anon");

        filterChainDefinitionMap.put("/", "anon");

        filterChainDefinitionMap.put("/mfTrenchUser/trench", "anon");// 渠道商登陆
        filterChainDefinitionMap.put("/mfTrenchUser/checkTrenchUserExistAjax", "anon");// 渠道商账户验证

        filterChainDefinitionMap.put("/trenchLogin/login", "anon");// 渠道商登陆
        filterChainDefinitionMap.put("/trenchLogin/checkTrenchUserExistAjax", "anon");// 渠道商账户验证
        filterChainDefinitionMap.put("/pcInterface/uploadDocFile", "anon");// PC端调用


        filterChainDefinitionMap.put("/poserver.zz", "anon");
        filterChainDefinitionMap.put("/poserver.do", "anon");
        filterChainDefinitionMap.put("/pageoffice.cab", "anon");
        filterChainDefinitionMap.put("/popdf.cab", "anon");
        filterChainDefinitionMap.put("/sealsetup.exe", "anon");
        filterChainDefinitionMap.put("/posetup.exe", "anon");
        filterChainDefinitionMap.put("/adminseal.do", "anon");
        filterChainDefinitionMap.put("/loginseal.do", "anon");
        filterChainDefinitionMap.put("/sealimage.do", "anon");
        filterChainDefinitionMap.put("/component/model/templateInclude*.jsp", "anon");
        filterChainDefinitionMap.put("/component/model/saveModelInfo.jsp", "anon");
        filterChainDefinitionMap.put("/mfTemplateModelRel/**", "anon");
        filterChainDefinitionMap.put("/mfTemplateBizConfig/**", "anon");
        filterChainDefinitionMap.put("/testThird/**", "anon");

        filterChainDefinitionMap.put("/actuator/**", "anon");//健康监控


        filterChainDefinitionMap.put("/**", "authc");// authc 需要认证

//		filterChainDefinitionMap.put("/sysSkip/skipToC", "authc");
//		filterChainDefinitionMap.put("/sysTaskInfo/**", "authc");
//		filterChainDefinitionMap.put("/bwmTaskRoleRel/**", "authc");
//		filterChainDefinitionMap.put("/pmsUserFilter/**", "authc");
//		filterChainDefinitionMap.put("/sysDescTemp/updateSession", "authc");
//		List<PmsEntrance> entranceList = pmsEntranceFeign.getAllPmsEntrance();
//		List<PmsViewpoint> pointList = pmsViewpointFeign.getAllPmsViewpoint();
//		for (Iterator iterator = entranceList.iterator(); iterator.hasNext();) {
//			PmsEntrance pmsEntrance = (PmsEntrance) iterator.next();
//		filterChainDefinitionMap.put(pmsEntrance.getEntranceUrl(), pmsEntrance.getRoleNo());// "anyofroles[\"01,150541\"]",在sql中做的处理
//		// "perms[\"01,150541\"]"-------这个是and关系
//		System.out.println(pmsEntrance.getEntranceUrl() + "===" + pmsEntrance.getRoleNo());
//	}
//		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
//			PmsViewpoint pmsViewpoint = (PmsViewpoint) iterator.next();
//		filterChainDefinitionMap.put(pmsViewpoint.getViewpointMenuUrl(), pmsViewpoint.getRoleNo());// "anyofroles[\"01,150541\"]",在sql中做的处理
//		System.out.println(pmsViewpoint.getViewpointMenuUrl() + "===" + pmsViewpoint.getRoleNo());
//	}
        //filterChainDefinitionMap.put("/**", "roles[admin]");// anon
        // 可以理解为不拦截roles[admin]
        // 按钮暂时放开。
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }


    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）其他机构访问配置
     *
     * @throws Exception
     */
    private void loadShiroFilterChainStorage(ShiroFilterFactoryBean shiroFilterFactoryBean, PmsEntranceFeign pmsEntranceFeign, PmsViewpointFeign pmsViewpointFeign) throws Exception {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // filterChainDefinitionMap.put("/user", "authc");//
        // 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
        // anon：它对应的过滤器里面是空的,什么都没做
        logger.info


                ("##################从数据库读取权限规则，加载到shiroFilter中##################");
        //仓储机构相关权限
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/CodeImage.jsp", "anon");
        filterChainDefinitionMap.put("/UIplug/**", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/403", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/userLogin", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/shiroCheck", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/wareLogin", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/checkExistAjax", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/sessionInvalid", "anon");
        filterChainDefinitionMap.put("/sysWareLogin/logout", "anon");
        filterChainDefinitionMap.put("/mfSysCompanyMst.getLoginPageLogoImg", "anon");
        filterChainDefinitionMap.put("/sendMsg/sendMessageAuto/**", "anon");//为了service调用而放开权限控制。
        filterChainDefinitionMap.put("/helpUserMsg/**", "anon");
        filterChainDefinitionMap.put("/dwr/**", "anon");
        filterChainDefinitionMap.put("/component/talk/SysTalkRecord_Main.jsp", "anon");
        filterChainDefinitionMap.put("/error.jsp", "anon");
        filterChainDefinitionMap.put("/sysLogin/changePassWordt", "authc");
        filterChainDefinitionMap.put("/sysWareLogin/logout", "authc");
        //首页基本信息
        filterChainDefinitionMap.put("/sysUser/**", "authc");
        filterChainDefinitionMap.put("/mfCusWarehouseOrg/**", "authc");
        filterChainDefinitionMap.put("/mfBusTrench/**", "authc");
        filterChainDefinitionMap.put("/mfCusCreditApply/getCreditDataAjax", "authc");
        filterChainDefinitionMap.put("/docManage/**", "authc");
        filterChainDefinitionMap.put("/mfCusRelation/forSEDetail", "authc");
        filterChainDefinitionMap.put("/mfTrenchUser/**", "authc");
        filterChainDefinitionMap.put("/mfCusCustomer/getIfUploadHeadImg", "authc");
        filterChainDefinitionMap.put("/mfCusCustomer/submitUploadHeadImg", "authc");
        filterChainDefinitionMap.put("/mfCusCustomer/getCusInfIntegrityList", "authc");
        filterChainDefinitionMap.put("/mfCusRelation/ForView", "authc");
        filterChainDefinitionMap.put("/mfCusRelation/getListPage", "authc");
        filterChainDefinitionMap.put("/mfCusWarehouseOrg/getListPage", "roles[00000]");

        //仓库信息修改
        filterChainDefinitionMap.put("/mfCusWarehouse/**", "authc");

        //角色
        filterChainDefinitionMap.put("/sysRole/**", "authc");

        //首页logo
        filterChainDefinitionMap.put("/mfSysCompanyMst.getSystemLogoImg", "authc");

        //消息信息
        filterChainDefinitionMap.put("/sysMsgConfig/**", "authc");

        //头像上传
        filterChainDefinitionMap.put("/uploadFile/**", "authc");

        //自定义筛选
        filterChainDefinitionMap.put("/pmsUserFilter/**", "authc");

        //资产管理信息
        filterChainDefinitionMap.put("/pledgeBaseInfo/**", "authc");
        filterChainDefinitionMap.put("/mfBusCollateral/**", "authc");
        //新增资产选择客户

        filterChainDefinitionMap.put("/mfCusCustomer/getListPageForSelect", "authc");
        filterChainDefinitionMap.put("/mfCusCustomer/findPerAndCoreByPageAjax", "authc");
        filterChainDefinitionMap.put("/mfBusCollateral/getEntrance", "authc");
        filterChainDefinitionMap.put("/mfCusCustomer/getCusByIdAjax", "authc");
        filterChainDefinitionMap.put("/mfCusCustomer/findByPageForSelectAjax", "authc");


        //右边业务历史
        filterChainDefinitionMap.put("/mfBusPact/getCompleteBusDataAjax", "authc");
        filterChainDefinitionMap.put("/wkfChart/getHisWkfChartInfoAjax", "authc");
        filterChainDefinitionMap.put("/mfCusTrack/getTopListAjax", "authc");


        //押品信息完善
        filterChainDefinitionMap.put("/pledgeBaseInfoBill/**", "authc");
        filterChainDefinitionMap.put("/evalInfo/**", "authc");
        filterChainDefinitionMap.put("/mfMoveableCheckInventoryInfo/**", "authc");
        filterChainDefinitionMap.put("/fairInfo/**", "authc");
        filterChainDefinitionMap.put("/insInfo/**", "authc");
        filterChainDefinitionMap.put("/certiInfo/**", "authc");
        filterChainDefinitionMap.put("/chkInfo/**", "authc");

        //押品信息完善模块管理
        filterChainDefinitionMap.put("/mfCollateralTable/**", "authc");

        //资产预警
        filterChainDefinitionMap.put("/mfMsgPledge/**", "authc");


        //押品出库
        filterChainDefinitionMap.put("/keepInfo/**", "authc");




        //核心企业想关权限
        filterChainDefinitionMap.put("/sysCoreLogin/403", "anon");
        filterChainDefinitionMap.put("/sysCoreLogin/userLogin", "anon");
        filterChainDefinitionMap.put("/sysCoreLogin/shiroCheck", "anon");
        filterChainDefinitionMap.put("/sysCoreLogin/coreLogin", "anon");
        filterChainDefinitionMap.put("/sysCoreLogin/checkExistAjax", "anon");
        filterChainDefinitionMap.put("/sysCoreLogin/sessionInvalid", "anon");
        filterChainDefinitionMap.put("/sysCoreLogin/logout", "anon");


        //首页基本信息
        filterChainDefinitionMap.put("/mfCusCoreCompany/**", "authc");
        filterChainDefinitionMap.put("mfCusCoreCompany//findByPageAjax", "roles[00000]");

        //区域选择
        filterChainDefinitionMap.put("/nmdArea/**", "authc");


        //核心企业上传头像
        filterChainDefinitionMap.put("/mfCusCustomer/uploadHeadImg", "authc");

        //企业重大变更
        filterChainDefinitionMap.put("/mfCusCorpMajorChange/**", "authc");
        //仓库信息
        filterChainDefinitionMap.put("/mfCusWarehouse/**", "authc");

        //账户管理
        filterChainDefinitionMap.put("/mfCusBankAccManage/**", "authc");

        //对外担保情况
        filterChainDefinitionMap.put("/mfCusGuaranteeOuter/**", "authc");

        //信息块删除回调
        filterChainDefinitionMap.put("/mfCusCustomer/updateCusTableAndIntegrityAjax", "authc");

        //完善资料
        filterChainDefinitionMap.put("/mfCusTable/getPageUpdateStas", "authc");

        //委托付款协议电子签约
        filterChainDefinitionMap.put("/mfTemplateEsignerList/**", "authc");

        //锁死全部请求页面
        filterChainDefinitionMap.put("/**", "roles[00000]");// authc 需要认证

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }




    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @param myShiroRealm
     * @param stuService
     * @param scoreDao
     * @return
     * @throws Exception
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, PmsEntranceFeign pmsEntranceFeign, PmsViewpointFeign pmsViewpointFeign) throws Exception {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new MShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();// 获取filters
        filters.put("anyofroles", new AnyOfRolesAuthorizationFilter());// 将自定义的AuthorizationFilter注入shiroFilter中
        filters.put("authc", new CustomFormAuthenticationFilter());//登录的权限验证，如果是session失效重定向的空页加跳转login
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        // 登录成功后要跳转的连接
//		shiroFilterFactoryBean.setSuccessUrl("/index20");
        if (enable.equals("1")) {
            shiroFilterFactoryBean.setSuccessUrl("/sysWareLogin/shiroCheck");
            shiroFilterFactoryBean.setUnauthorizedUrl("/403");// todo:换成现在的未授权提示页面/403
            shiroFilterFactoryBean.setLoginUrl("/sysWareLogin/wareLogin");
            loadShiroFilterChainStorage(shiroFilterFactoryBean, pmsEntranceFeign, pmsViewpointFeign);
        }else {
            shiroFilterFactoryBean.setSuccessUrl("/sysLogin/shiroCheck");
            shiroFilterFactoryBean.setUnauthorizedUrl("/sysLogin/403");// todo:换成现在的未授权提示页面/403
            shiroFilterFactoryBean.setLoginUrl("/sysLogin/sessionInvalid");
            loadShiroFilterChain(shiroFilterFactoryBean, pmsEntranceFeign, pmsViewpointFeign);
        }

        return shiroFilterFactoryBean;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        if (StringUtil.isNotEmpty(password)) {
            redisManager.setPassword(password);
        }
        redisManager.setPort(port);
        redisManager.setExpire(sessionTimeOut);// 配置过期时间
        redisManager.setTimeout(timeout);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     *
     * @return
     */
    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    // @Bean
    // public EhCacheManager getEhCacheManager() {
    // EhCacheManager em = new EhCacheManager();
    // em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
    // return em;
    // }

    @Bean(name = "sysShiroRealm")
    public SysShiroRealm sysShiroRealm(RedisCacheManager cacheManager) {
        SysShiroRealm realm = new SysShiroRealm();
        realm.setCacheManager(cacheManager);
        realm.setAuthorizationCache(cacheManager.getCache(realm.getName()));// AuthorizationCache
        // 开启
        return realm;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro） 集成Shiro有2种方法： 1.
     * 按这个方法自己组装一个FilterRegistrationBean（这种方法更为灵活，可以自己定义UrlPattern） 2.
     * 直接使用ShiroFilterFactoryBean（这种方法比较简单，其内部对ShiroFilter做了组装工作，无法自己定义UrlPattern，
     * 默认拦截 /*）
     *
     * @param dispatcherServlet
     */
    // @Bean
    // public FilterRegistrationBean filterRegistrationBean() {
    // FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
    // filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
    // //
    // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
    // filterRegistration.addInitParameter("targetFilterLifecycle", "true");
    // filterRegistration.setEnabled(true);
    // filterRegistration.addUrlPatterns("/*");//
    // 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
    // return filterRegistration;
    // }

    /**
     * static一定要保留，否则@Value、@Autowired不起作用
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * Session Manager
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setSessionIdCookie(simpleCookie());
        return sessionManager;
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie cookie = new SimpleCookie("shiro_redis_session");
        cookie.setMaxAge(-1);
        return cookie;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(SysShiroRealm sysShiroRealm, SessionManager defaultWebSessionManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(sysShiroRealm);
        dwsm.setCacheManager(cacheManager());
        dwsm.setSessionManager(defaultWebSessionManager);
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }


}
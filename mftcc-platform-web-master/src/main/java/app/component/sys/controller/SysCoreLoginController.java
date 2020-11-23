package app.component.sys.controller;

import app.base.JsonMenuUtil;
import app.base.SpringUtil;
import app.base.User;
import app.base.UserAgentParser;
import app.base.cacheinterface.BusiCacheInterface;
import app.base.shiro.SessionUserCheckService;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusCoreCompany;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.feign.MfCusCoreCompanyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.warehouse.entity.MfCusWarehouseOrg;
import app.component.cus.warehouse.feign.MfCusWarehouseOrgFeign;
import app.component.nmd.entity.WorkCalendar;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pms.entity.PmsDataRangRole;
import app.component.pms.entity.PmsEntranceRole;
import app.component.pmsinterface.PmsInterfaceFeign;
import app.component.secinterface.SecinterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.entity.MfSysSkinUser;
import app.component.sys.entity.SysGlobal;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysMenu;
import app.component.sys.entity.SysLoginLog;
import app.component.sys.feign.SysUserFeign;
import app.component.sys.feign.SysGlobalFeign;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysMenuFeign;
import app.component.sys.feign.SysLoginLogFeign;
import app.component.sys.feign.MfSysSkinUserFeign;
import app.component.sys.util.Encryption;
import app.component.sys.util.LoginEncodeUtil;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import config.YmlConfig;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/sysCoreLogin"})
public class SysCoreLoginController extends BaseFormBean {
    private static final String LOGIN_TYPE = "浏览器登录";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BusiCacheInterface busiCacheInterface;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private SysUserFeign sysUserFeign;
    @Autowired
    private SecinterfaceFeign secinterfaceFeign;
    @Autowired
    private PmsInterfaceFeign pmsInterfaceFeign;
    @Autowired
    private SysGlobalFeign sysGlobalFeign;
    @Autowired
    private SysOrgFeign sysOrgFeign;
    @Autowired
    private SysMenuFeign sysMenuFeign;
    @Autowired
    private NmdInterfaceFeign nmdInterfaceFeign;
    @Autowired
    private SysLoginLogFeign sysLoginLogFeign;
    @Autowired
    private MfBusTrenchFeign mfBusTrenchFeign;
    @Autowired
    private MfBusAgenciesFeign mfBusAgenciesFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfSysSkinUserFeign mfSysSkinUserFeign;
    @Autowired
    private MfCusWarehouseOrgFeign mfCusWarehouseOrgFeign;
    @Autowired
    private SessionUserCheckService sessionUserCheckService;
    @Value("${spring.shiro.switch}")
    private String shiroSwitch;
    @Value("${spring.shiro.sessionTimeOut}")
    private String sessionTimeOut;
    @Autowired
    private YmlConfig ymlConfig;

    @Autowired
    private MfCusCoreCompanyFeign mfCusCoreCompanyFeign;

    @RequestMapping(value = "/coreLogin", method = RequestMethod.GET)
    public String index(Model model) {
        //核心企业登录
        YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
        String  exitStr = ymlConfig.getWebservice().get("coreLoginJsp");
        return exitStr;

    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/index")
    public String index() throws Exception {
        String userAgent = request.getHeader("User-Agent");
        //读取配置文件，查看是否单独使用财务系统，“true”是单独使用独立系统，“false”不使用财务的独立系统
        String aloneUsedCw = PropertiesUtil.getWebServiceProperty("alone_used_cw");
        if ("true".equals(aloneUsedCw)) {
            return "cwLogin";
        }
		/*if(CheckMobile.check(userAgent)){
			return "mobile";
		}*/
        return "web";
    }

    @RequestMapping(value = "/userLogin")
    public String userLogin(Model model) {
        if ("1".equals(shiroSwitch)) {// 开启shiro权限验证
            return "forward:shiroCheck";
        } else {// 不开启shiro权限验证
            return "forward:noShiro";
        }
    }

    /**
     * 登录
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unused", "deprecation"})
    @RequestMapping(value = "/shiroCheck")
    public String shiroCheck(Model model, String opNo, String opName, String passwordhash, String frame, String ztName) throws Exception {
        model.addAttribute("opNo", opNo);
        model.addAttribute("opName", opName);
        model.addAttribute("passwordhash", passwordhash);
        String sysProjectName = ymlConfig.getSysParams().get("sys.project.name");
        model.addAttribute("sysProjectName", sysProjectName);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession(true);// 强制制重新生成Session
        String exitStr = "entryLease";
        //核心企业登录
        YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
        String isUse = ymlConfig.getWebservice().get("enable");
        if(isUse.equals("1")){
            exitStr= ymlConfig.getWebservice().get("coreLoginJsp");
        }
        if ("".equals(opNo) || opNo == null) {
            this.addActionError(model, "用户名不能为空！");
            return exitStr;
        }
        if ("".equals(passwordhash) || passwordhash == null) {
            this.addActionError(model, "密码不能为空！");
            return exitStr;
        }


        /**
         * 增加css、js的版本号,解决缓存问题，在具体文件中只需要在路径后追加?v=${cssJsVersion}即可。
         * 同拦截器中的用法。
         * add by LiuYF 2017-9-11
         */
        request.getSession().setAttribute("cssJsVersion", "v" + DateUtil.getDate());

        String USER_LOGIN_COLUMN = new CodeUtils().getSingleValByKey("USER_LOGIN_COLUMN");// 操作员登录账号列
        SysUser sysUser = new SysUser();
        sysUser.setColumnQuery(USER_LOGIN_COLUMN);
        sysUser.setColumnVal(opNo);
        sysUser = sysUserFeign.getById(sysUser);

        String remeg = "main";//XL安全审计调用返回值
        if (sysUser != null) {
            opNo = sysUser.getOpNo();
            // 根据操作员类型不同，首页展示不同内容
            String opNoType = sysUser.getOpNoType();
            //核心企业系统，开启后只支持核心企业人员登录
            if(isUse.equals("1") && !BizPubParm.OP_NO_TYPE5.equals(opNoType)){
                this.addActionError(model, "该用户不存在,无法登陆！");
                return exitStr;
            }
            if (BizPubParm.OP_NO_TYPE2.equals(opNoType)) {
                MfBusTrench mfbusTrench = mfBusTrenchFeign.getByOpNo(opNo);
                if(mfbusTrench == null){
                    this.addActionError(model, "该用户不存在,无法登陆！");
                    return exitStr;
                }
                model.addAttribute("trenchId", mfbusTrench.getTrenchId());
                model.addAttribute("trenchUid", mfbusTrench.getTrenchUid());
            } else if (BizPubParm.OP_NO_TYPE3.equals(opNoType)) {
                MfBusAgencies mfbusAgencies = mfBusAgenciesFeign.getByOpNo(opNo);
                if(mfbusAgencies == null){
                    this.addActionError(model, "该用户不存在,无法登陆！");
                    return exitStr;
                }
                model.addAttribute("agenciesId", mfbusAgencies.getAgenciesId());
                model.addAttribute("agenciesUid", mfbusAgencies.getAgenciesUid());
            } else if (BizPubParm.OP_NO_TYPE4.equals(opNoType)) {
                MfCusWarehouseOrg mfCusWarehouseOrg =mfCusWarehouseOrgFeign.getByOpNo(opNo);
                if(mfCusWarehouseOrg == null){
                    this.addActionError(model, "该用户不存在,无法登陆！");
                    return exitStr;
                }
                model.addAttribute("warehouseOrgId", mfCusWarehouseOrg.getId());
                model.addAttribute("warehouseOrgUId", mfCusWarehouseOrg.getCusNo());
            }else if (BizPubParm.OP_NO_TYPE5.equals(opNoType)) {
                MfCusCoreCompany mfCusCoreCompany = mfCusCoreCompanyFeign.getByOpNo(opNo);;
                if(mfCusCoreCompany == null){
                    this.addActionError(model, "该用户不存在,无法登陆！");
                    return exitStr;

                }
                model.addAttribute("coreCompanyUid", mfCusCoreCompany.getCoreCompanyUid());
                model.addAttribute("coreCompanyId", mfCusCoreCompany.getCoreCompanyId());
                String baseType=mfCusCoreCompany.getCusType().substring(0,1);
                model.addAttribute("baseType",baseType);
            }else {
            }
            model.addAttribute("opNoType", opNoType);

            model.addAttribute("brNo", sysUser.getBusinessfax());
            //FIXME 密码传输都没加密。密码加密
            String password = null;
            password = Encryption.md5(passwordhash);
            if (password.equals(sysUser.getPasswordhash())) {

                if (sysUser.getOpSts() == null || sysUser.getOpSts().equals("0")) {
                    //？？防止密码验证通过后不能登陆把密码带回页面
                    sysUser.setPasswordhash(null);
                    this.addActionError(model, "该用户已失效,无法登陆!");
                    return exitStr;
                } else if (sysUser.getOpSts() == null || sysUser.getOpSts().equals("2")) {
                    //？？防止密码验证通过后不能登陆把密码带回页面
                    sysUser.setPasswordhash(null);
                    this.addActionError(model, "该用户已经被注销,无法登陆！");
                    return exitStr;
                }else {
                }

                String returnFlag = handleLoginSuccessBusiness(model, remeg, sysUser, null, frame, opNo, request);

                UsernamePasswordToken token = new UsernamePasswordToken(opNo, password);
                currentUser.login(token);
                busiCacheInterface.setSessionAttr(request.getSession().getId(), sysUser);
                //使用财务独立登录入口，需要账套名称
                if ("cwView".equals(returnFlag) && StringUtil.isNotEmpty(ztName)) {
                    request.getSession().setAttribute("finBooks", ztName);
                    request.setAttribute("userno", opNo);//财务需要
                }
                //获取当前用户的皮肤信息
                MfSysSkinUser mfSysSkinUser = new MfSysSkinUser();
                mfSysSkinUser.setOpNo(sysUser.getOpNo());
                mfSysSkinUser = mfSysSkinUserFeign.getById(mfSysSkinUser);
                request.getSession().setAttribute("skinSuffix", "");
                if (mfSysSkinUser != null) {
                    if (!"default".equals(mfSysSkinUser.getSkin())) {
                        request.getSession().setAttribute("skinSuffix", "_" + mfSysSkinUser.getSkin());
                    }
                }

                if ("login".equals(returnFlag)) {
                    return exitStr;
                }
                return returnFlag;
            } else {
                remeg = "toln密码错误";//XL安全审计调用接口，记录登录信息,加toln是为了代码简化
                secinterfaceFeign.insertOrUpdate(opNo, remeg.substring(4, remeg.length()));
                remeg = secinterfaceFeign.PwdTimes(opNo);//XL安全审计调用校验接口
                //如果不超多错误次数不显示提示信息
                if ("main".equals(remeg)) {
                    remeg = "toln用户名或密码错误！";
                }
                this.addActionError(model, remeg.substring(4, remeg.length()));
                return exitStr;
            }
        } else {
            this.addActionError(model, "用户名或密码错误！");
            return exitStr;
        }
    }

    /**
     * 登录
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/noShiro")
    public String noShiro(Model model, String opNo, String passwordhash, String frame, String ztName) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String exitStr = "entryLease";
        //读取配置文件，查看是否单独使用财务系统，“true”是单独使用独立系统，“false”不使用财务的独立系统
//		String aloneUsedCw = PropertiesUtil.getWebServiceProperty("alone_used_cw");
//		if("true".equals(aloneUsedCw)){
//			exitStr = "cwLogin";
//		}
        if ("".equals(opNo) || opNo == null) {
            this.addActionError(model, "用户名不能为空！");
            return exitStr;
        }
        if ("".equals(passwordhash) || passwordhash == null) {
            this.addActionError(model, "密码不能为空！");
            return exitStr;
        }


        /**
         * 增加css、js的版本号,解决缓存问题，在具体文件中只需要在路径后追加?v=${cssJsVersion}即可。
         * 同拦截器中的用法。
         * add by LiuYF 2017-9-11
         */
        request.getSession().setAttribute("cssJsVersion", "v" + DateUtil.getDate());

        //if(code==null || this.getSession().get("code")==null){
        //		this.addActionError("验证码失效,请重新登陆!");
        //		return "login";
        //	}else if(code==null || this.getSession().get("code")==null || !code.equalsIgnoreCase((String)this.getSession().get("code"))){
        //		this.addActionError("验证码不正确,请重新输入!");
        //		return "login";
        //	}

        String USER_LOGIN_COLUMN = new CodeUtils().getSingleValByKey("USER_LOGIN_COLUMN");// 操作员登录账号列
        SysUser sysUser = new SysUser();
        sysUser.setColumnQuery(USER_LOGIN_COLUMN);
        sysUser.setColumnVal(opNo);
        sysUser = sysUserFeign.getById(sysUser);
        String remeg = "main";//XL安全审计调用返回值
        if (sysUser != null) {
            String opName = sysUser.getOpName();
            //密码加密
            String password = null;
            password = Encryption.md5(passwordhash);
            if (password.equals(sysUser.getPasswordhash())) {
                //？？防止密码验证通过后不能登陆把密码带回页面
                if (sysUser.getOpSts() == null || sysUser.getOpSts().equals("0")) {
                    //？？防止密码验证通过后不能登陆把密码带回页面
                    sysUser.setPasswordhash(null);
                    this.addActionError(model, "该用户已失效,无法登陆!");
                    return exitStr;
                } else if (sysUser.getOpSts() == null || sysUser.getOpSts().equals("2")) {
                    //？？防止密码验证通过后不能登陆把密码带回页面
                    sysUser.setPasswordhash(null);
                    this.addActionError(model, "该用户已经被注销,无法登陆！");
                    return exitStr;
                }else {
                }

                String returnFlag = handleLoginSuccessBusiness(model, remeg, sysUser, null, frame, opNo, request);
                //使用财务独立登录入口，需要账套名称
                if ("cwView".equals(returnFlag) && StringUtil.isNotEmpty(ztName)) {
                    request.getSession().setAttribute("finBooks", ztName);
                    request.setAttribute("userno", opNo);//财务需要
                }
                return returnFlag;

            } else {
                remeg = "toln密码错误";//XL安全审计调用接口，记录登录信息,加toln是为了代码简化
                secinterfaceFeign.insertOrUpdate(opNo, remeg.substring(4, remeg.length()));
                remeg = secinterfaceFeign.PwdTimes(opNo);//XL安全审计调用校验接口
                //如果不超多错误次数不显示提示信息
                if ("main".equals(remeg)) {
                    remeg = "toln密码错误";
                }
                this.addActionError(model, remeg.substring(4, remeg.length()));
                return exitStr;
            }
        } else {
            this.addActionError(model, "用户名或密码错误！");
            return exitStr;
        }
    }

    @RequestMapping(value = "/qrcodeUserLogin")
    public String qrcodeUserLogin(Model model, String tel, String timeTamp, String secretCode, String frame) throws Exception {
        if (null == tel || "".equals(tel)
                || null == timeTamp || "".equals(timeTamp)
                || null == secretCode || "".equals(secretCode)) {
            this.addActionError(model, "扫码失败，请重试！");
            return "qrlogin";
        }
        long currentTime = System.currentTimeMillis();
        double d = MathExtend.subtract(currentTime, Double.parseDouble(timeTamp));
        if (d > 60000 || d <= 0) {//1分钟后失效
            this.addActionError(model, "登陆超时，请重试！");
            return "qrlogin";
        }
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(tel);
        sysUser = sysUserFeign.getById(sysUser);
        if (sysUser != null) {
            if (sysUser.getOpSts() == null || sysUser.getOpSts().equals("0")) {
                //？？防止密码验证通过后不能登陆把密码带回页面
                sysUser.setPasswordhash(null);
                this.addActionError(model, "该用户已失效,无法登陆!");
                return "qrlogin";
            } else if (sysUser.getOpSts() == null || sysUser.getOpSts().equals("2")) {
                //？？防止密码验证通过后不能登陆把密码带回页面
                sysUser.setPasswordhash(null);
                this.addActionError(model, "该用户已经被注销,无法登陆！");
                return "qrlogin";
            }else {
            }
            String mobile = sysUser.getMobile();
            String md5Str = LoginEncodeUtil.EncodeByMD5(tel, mobile, timeTamp);
            if (secretCode.equals(md5Str)) {//登陆成功
                String remeg = "main";//XL安全审计调用返回值
                frame = "view";//浏览器模式 从前台传入，现在写死
                String opNo = tel;//登陆成功赋值给操作员账户
                model.addAttribute("opNo", opNo);
                String resultFlag = handleLoginSuccessBusiness(model, remeg, sysUser, "1", frame, opNo, request);
                return resultFlag;
            } else {
                this.addActionError(model, "二维码失效，请重试！");
                return "qrlogin";
            }
        } else {
            this.addActionError(model, "用户名或密码错误！");
            return "qrlogin";
        }
    }

    @RequestMapping(value = "/redirectUserLogin")
    public String redirectUserLogin(Model model) {//页面从request获取操作员opno,从session 取出放到request
        String opNo = (String) request.getSession().getAttribute("regNo");
        model.addAttribute("opNo", opNo);
        return "view";
    }

    /**
     * 处理登陆成功业务
     *
     * @param remeg
     * @param sysUser
     * @param returnflag 默认：null 返回的是用户名密码登陆失败返回的错误页面;"1" : 扫码登陆失败页面 <br/> 区分扫描二维码 还是 输入用户名密码 登陆失败要返回的页面 字符串信息
     * @return
     * @throws Exception
     */
    private String handleLoginSuccessBusiness(Model model, String remeg, SysUser sysUser, String returnflag, String frame, String opNo, HttpServletRequest request) throws Exception {
        String returnFlag = "login";
        //读取配置文件，查看是否单独使用财务系统，“true”是单独使用独立系统，“false”不使用财务的独立系统
        String aloneUsedCw = PropertiesUtil.getWebServiceProperty("alone_used_cw");
        if ("true".equals(aloneUsedCw)) {
            returnFlag = "cwLogin";
        }
        if (!StringUtil.isEmpty(returnflag) && "1".equals(returnflag)) {
            returnFlag = "qrlogin";
        }
        if (frame.equals("viewIE8")) {
            returnFlag = viewIE8Mode(sysUser);
        } else if (frame.equals("menu")) {
            returnFlag = menuMode(sysUser);

        } else {
            returnFlag = viewMode(sysUser, request);
            returnFlag="/layout/view/coreView";
            /* 仅开启财务模式（阳光银行）时，直接跳转到财务页面。
             * add by LiuYF 20170705
             */
            if ("cwView".equals(frame)) {
                returnFlag = "cwView";
            }
        }
        //判断操作员是否已经登录，登录则T下线
        //if(LoginSessionListener.isOnline(opNo)){
        this.sessionUserCheckService.kickFirstOper(request.getSession(), opNo);//把第一个用户T下线
        //}
        //往map中添加操作员sessionid和op_name的映射关系
        //	LoginSessionListener.putSessionMap(request.getSession(),opNo);
        remeg = secinterfaceFeign.SecurityPwd(opNo);//XL安全审计调用校验接口
        //remeg = "main";//（*****************校验开启注此行代码************）
        if (remeg.equals("main"))//XL安全审计调用接口，记录登录信息
            secinterfaceFeign.insertOrUpdate(opNo, remeg);
        else {//XL安全审计根据返回值判断跳转页是修改密码页面还是登陆页面
            String result = "";
            //首页入口添加第一次登录强制修改密码限制。20190325陈迪
            if (remeg.substring(0, 4).equals("must")) {
                request.getSession().setAttribute("opNo", opNo);
                request.getSession().setAttribute("opName", sysUser.getOpName());
                result = "component/sys/SysUser_PwdForceUpdate";//除值为0时不修改密码，其他都强制修改密码
            } else if (remeg.substring(0, 4).equals("toup")) {
                result = "updatepwd";//跳转页面至修改密码
            } else if (remeg.substring(0, 4).equals("toln")) {
                //判断是否启用单独财务系统
                if ("true".equals(aloneUsedCw)) {
                    result = "cwLogin";
                } else {
                    result = "login";
                }
            }else {
            }
            secinterfaceFeign.insertOrUpdate(opNo, remeg.substring(4, remeg.length()));
            this.addActionError(model, remeg.substring(4, remeg.length()));
            return result;
        }
        SysLoginLogInsert(sysUser);
        return returnFlag;
    }

    /**
     * 后台设置跳转页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setView")
    public String setView(Model model) throws Exception {
        String SYSLOGIN_SETVIEW_VALIDATE = new CodeUtils().getSingleValByKey("SYSLOGIN_SETVIEW_VALIDATE");// 后台设置跳转页面密码验证, 1-启用, 0-关闭
        //后台管理入口添加第一次登录强制修改密码限制。20190325陈迪
        Object opNoSession = request.getSession().getAttribute("opNo");
        if (opNoSession!=null){
            String opNo = (String) opNoSession;
            SysUser sysUser = new SysUser();
            sysUser.setOpNo(opNo);
            sysUser = sysUserFeign.getById(sysUser);
            String remeg = secinterfaceFeign.SecurityPwd(opNo);
            if (remeg.substring(0, 4).equals("must")) {
                request.getSession().setAttribute("opNo", opNo);
                request.getSession().setAttribute("opName", sysUser.getOpName());
                return "component/sys/SysUser_PwdForceUpdate";//除值为0时不修改密码，其他都强制修改密码
            }
        }
        for (String orgNo : User.getRoleNo(request)) {
            List<PmsEntranceRole> pmsEntranceRoleList = pmsInterfaceFeign.findByRoleNo(orgNo);
            if (pmsEntranceRoleList != null) {
                for (PmsEntranceRole pmsEntranceRole : pmsEntranceRoleList) {
                    // 10037表示入口设置权限编号
                    if ("10037".equals(pmsEntranceRole.getPmsNo())) {
                        if ("1".equalsIgnoreCase(SYSLOGIN_SETVIEW_VALIDATE)) {

                            FormService formService = new FormService();
                            FormData setViewValidate = formService.getFormData("setViewValidate");
                            model.addAttribute("setViewValidate", setViewValidate);
                            model.addAttribute("query", "");

                            return "/layout/view/setView_validate";
                        } else {
                            return "/layout/view/setView";
                        }
                    }
                }
            }
        }
        addActionMessage(model, "您没有此操作权限!");
        return "noAuthorityException";
    }

    @RequestMapping(value = "/validateSub")
    public String validateSub(Model model, String psword) throws Exception {

        psword = Encryption.md5(psword);

        SysUser sysUser = new SysUser();
        sysUser.setOpSts(BizPubParm.YES_NO_Y);
        sysUser.setBuildInFlag(BizPubParm.YES_NO_Y);
        sysUser.setPasswordhash(psword);
        List<SysUser> userList = sysUserFeign.getAllUserList(sysUser);
        if (userList.size() > 1) {
            return "/layout/view/setView";
        }
        // 当前登陆操作员的密码不可用于解锁。
        if (userList.size() == 1) {
            if (!userList.get(0).getOpNo().equals(User.getRegNo(request))) {
                return "/layout/view/setView";
            }
        }

        FormService formService = new FormService();
        FormData setViewValidate = formService.getFormData("setViewValidate");
        model.addAttribute("setViewValidate", setViewValidate);
        model.addAttribute("query", "");

        addActionMessage(model, "您没有此操作权限!");
        return "/layout/view/setView_validate";
    }

    /**
     * 财务跳转页面。
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cwView")
    public String cwView() throws Exception {
        return "/layout/view/cwView";
    }

    /**
     * 进销存跳转页面。
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pssView")
    public String pssView() throws Exception {
        return "pssView";
    }

    /**
     * 网贷跳转页面。
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/p2pView")
    public String p2pView() throws Exception {
        return "layout/view/p2pView";
    }

    /**
     * 车证跳转页面。
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/vcmsView")
    public String vcmsView() throws Exception {
        return "layout/view/vcmsView";
    }

    /**
     * @param @return
     * @return String
     * @throws Exception
     * @throws
     * @Description: 视角模式登录加载项
     * @author wangcong
     * @date 2015年12月24日下午2:35:36
     */
    private String viewMode(SysUser sysUser, HttpServletRequest request) throws Exception {
        String[] roleNo = sysUser.getRoleNo().split("\\|");
        List<PmsDataRangRole> pdrrlist = null;
        String viewMenuData = null, viewPointData = null;
        try {
            pdrrlist = pmsInterfaceFeign.getPmsDataRangRoleByRole(roleNo);
            viewMenuData = pmsInterfaceFeign.getEntorByRole(roleNo);
            viewPointData = pmsInterfaceFeign.getPmsViewpointByRole(roleNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Map<String, String>> pdrrMap = new HashMap<String, Map<String, String>>();
        for (PmsDataRangRole pdrr : pdrrlist) {
            HashMap<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("funRoleType", pdrr.getFunRoleType());
            tempMap.put("pmsField", pdrr.getPmsField());
            tempMap.put("pmsSts", pdrr.getPmsSts());
            pdrrMap.put(pdrr.getFunNo(), tempMap);
        }
        request.getSession().setAttribute("roleNo", roleNo);
        request.getSession().setAttribute("regNo", sysUser.getOpNo());
        request.getSession().setAttribute("regName", sysUser.getOpName());
        request.getSession().setAttribute("regJob", sysUser.getJob());
        request.getSession().setAttribute("orgNo", sysUser.getBrNo());
        request.getSession().setAttribute("funRoleType", sysUser.getFunRoleType());

        //此处的权限数据已经移入MybatisInterceptor类中
//		request.getSession().setAttribute("funRoleAssignOrg", funRoleAssignOrg);
        request.getSession().setAttribute("funNoType", pdrrMap);
        System.out.println(request.getSession().getId());
        request.getSession().setAttribute("viewMenuData", viewMenuData);
        request.getSession().setAttribute("viewPointData", viewPointData);
        request.getSession().setAttribute("pageInfo", "");
        SysGlobal sg = sysGlobalFeign.getSysGlobal();
        request.getSession().setAttribute("sysDate", sg.getSysDate());
        request.getSession().setAttribute("docSize", sg.getDocSize());
        /*
         * 首页设置参数-factor
         */
        request.getSession().setAttribute("mobile", sysUser.getMobile());
        request.getSession().setAttribute("email", sysUser.getEmail());
        request.getSession().setAttribute("homePage", sysUser.getHomePage());
        //表单权限使用
        request.getSession().setAttribute("sys_date", sg.getSysDate());
        request.getSession().setAttribute("tlrno", sysUser.getOpNo());
        SysOrg so = sysOrgFeign.getByBrNo(sysUser.getBrNo());
        String orgName = so == null ? "" : so.getBrName();
//		int infoCount = sysTaskInfoFeign.getCountByUser(sysUser.getOpNo());
//		if(infoCount==0){
//			sysTaskInfoInterfaceFeign.insertMessage(PasConstant.PAS_SUB_TYPE_SYS_MSG, "欢迎消息", "欢迎登录系统", sysUser.getOpNo(), PasConstant.PAS_IMPORT_LEV_1, PasConstant.PAS_IS_REPLY_NO,sysUser.getOpName());
//			System.out.println("消息任务数："+infoCount);
//		}
        String bizType = so == null ? "" : so.getBizType();
        request.getSession().setAttribute("bizType", bizType);
        request.getSession().setAttribute("orgName", orgName);
        List<WorkCalendar> workCalendarList = nmdInterfaceFeign.fullCalendarDaylist(sysUser.getOpNo());
        if (workCalendarList != null && workCalendarList.size() > 0) {
            request.getSession().setAttribute("calendarDaylist", JSONArray.fromObject(workCalendarList));
        }
        /*
         * 判断是否有工作正在托管
         */
//		boolean accreditRe = oaInterfaceFeign.getAccrditInfo(sysUser.getOpNo());
//		request.getSession().setAttribute("accreditRe", accreditRe);
//		request.setAttribute("accreditReMsg", MessageEnum.CONFIRM_ACCREDIT_ALL.getMessage());
        return "/layout/view/view";
    }

    /**
     * @param @return
     * @return String
     * @throws Exception
     * @throws
     * @Description: IE8模式登录加载项
     * @author wangcong
     * @date 2015年12月24日下午2:35:36
     */
    private String viewIE8Mode(SysUser sysUser) throws Exception {
        String[] roleNo = sysUser.getRoleNo().split("\\|");
        List<PmsDataRangRole> pdrrlist = null;
        String viewMenuData = null, viewPointData = null;
        try {
            pdrrlist = pmsInterfaceFeign.getPmsDataRangRoleByRole(roleNo);
            viewMenuData = pmsInterfaceFeign.getEntorByRole(roleNo);
            viewPointData = pmsInterfaceFeign.getPmsViewpointByRole(roleNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 跟ViewMode保持一致。权限问题。add by LiuYF
         */
        HashMap<String, Map<String, String>> pdrrMap = new HashMap<String, Map<String, String>>();
        for (PmsDataRangRole pdrr : pdrrlist) {
            HashMap<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("funRoleType", pdrr.getFunRoleType());
            tempMap.put("pmsField", pdrr.getPmsField());
            tempMap.put("pmsSts", pdrr.getPmsSts());
            pdrrMap.put(pdrr.getFunNo(), tempMap);
        }
        request.getSession().setAttribute("roleNo", roleNo);
        request.getSession().setAttribute("regNo", sysUser.getOpNo());
        request.getSession().setAttribute("regName", sysUser.getOpName());
        request.getSession().setAttribute("regJob", sysUser.getJob());
        request.getSession().setAttribute("orgNo", sysUser.getBrNo());
        request.getSession().setAttribute("funNoType", pdrrMap);
        request.getSession().setAttribute("viewMenuData", viewMenuData);
        request.getSession().setAttribute("viewPointData", viewPointData);
        request.getSession().setAttribute("pageInfo", "");
        /*
         * 首页设置参数-factor
         */
        request.getSession().setAttribute("homePage", sysUser.getHomePage());
        SysGlobal sg = sysGlobalFeign.getSysGlobal();
        request.getSession().setAttribute("sysDate", sg.getSysDate());
        request.getSession().setAttribute("docSize", sg.getDocSize());
        SysOrg so = sysOrgFeign.getByBrNo(sysUser.getBrNo());
        String orgName = so == null ? "" : so.getBrName();
//		int infoCount = sysTaskInfoFeign.getCountByUser(sysUser.getOpNo());
//		if(infoCount==0){
//			sysTaskInfoInterfaceFeign.insertMessage(PasConstant.PAS_SUB_TYPE_SYS_MSG, "欢迎消息", "欢迎登录系统", sysUser.getOpNo(), PasConstant.PAS_IMPORT_LEV_1, PasConstant.PAS_IS_REPLY_NO,sysUser.getOpName());
//			System.out.println("消息任务数："+infoCount);
//		}
        String bizType = so == null ? "" : so.getBizType();
        request.getSession().setAttribute("bizType", bizType);
        request.getSession().setAttribute("orgName", orgName);
        request.getSession().setAttribute("calendarDaylist", JSONArray.fromObject(nmdInterfaceFeign.fullCalendarDaylist(sysUser.getOpNo())));
        //表单权限使用
        request.getSession().setAttribute("sys_date", sg.getSysDate());
        request.getSession().setAttribute("tlrno", sysUser.getOpNo());
        return "viewIE8";
    }

    /**
     * @param @return
     * @return String
     * @throws
     * @Description: 菜单模式登录加载项
     * @author wangcong
     * @date 2015年12月24日下午2:45:44
     */
    private String menuMode(SysUser sysUser) throws Exception {
        String[] roleNo = sysUser.getRoleNo().split("\\|");
        request.getSession().setAttribute("roleNo", roleNo);
        request.getSession().setAttribute("brNo", sysUser.getBrNo());
        request.getSession().setAttribute("opName", sysUser.getOpName());
        request.getSession().setAttribute("opNo", sysUser.getOpNo());
        request.getSession().setAttribute("isLogin", true);//
        List<SysMenu> sysMenuList = sysMenuFeign.getAllMenuByRole_no(roleNo);
        Map<String, String> menuTreeMap = JsonMenuUtil.ulist2tree(sysMenuList);
        List<SysMenu> sysMenuLev1List = sysMenuFeign.findMenuLev1ByRole(roleNo);
        request.getSession().setAttribute("sysMenuLev1List", sysMenuLev1List);
        request.getSession().setAttribute("menuTreeMap", menuTreeMap);
        SysGlobal sg = sysGlobalFeign.getSysGlobal();
        request.getSession().setAttribute("sysDate", sg.getSysDate());
        request.getSession().setAttribute("docSize", sg.getDocSize());
        SysOrg so = sysOrgFeign.getByBrNo(sysUser.getBrNo());
        String brName = so == null ? "" : so.getBrName();
        String bizType = so == null ? "" : so.getBizType();
        request.getSession().setAttribute("brName", brName);
        request.getSession().setAttribute("bizType", bizType);
        //表单权限使用
        request.getSession().setAttribute("sys_date", sg.getSysDate());
        request.getSession().setAttribute("tlrno", sysUser.getOpNo());
        return "menu";
    }


    /**
     * 退出
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public Map<String,Object> userLogout() throws Exception {
        HttpSession session = request.getSession();
        Map<String,Object> dataMap = new HashMap<>();
        //操作员登出写日志
        Object tlrno = session.getAttribute("opName");
        String opNo = User.getRegNo(request);
        if (tlrno != null) {
        }
        String exitStr ="entryLease_HXQY";
        try {
            secinterfaceFeign.insertOrUpdate(User.getRegNo(request), "logout");
            this.sessionUserCheckService.deleteSession(request.getSession(), opNo);//把第一个用户T下线;//销毁session
            Subject currentUser = SecurityUtils.getSubject();
            Session session1 = currentUser.getSession(true);// 强制制重新生成Session
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        dataMap.put("flag","success");
        return dataMap;
    }

    /**
     * 功能描述：修改登录密码
     *
     * @return
     * @throws Exception
     * @author zhaomin(mailto : zhaomin @ dhcc.com.cn)
     */
    @RequestMapping(value = "/changePassWord")
    public String changePassWord(Model model, String changePWInfo) throws Exception {
        String getCPwd;
        //对更改后的密码加密
        String[] pwds = changePWInfo.split("/");

        changePWInfo = Encryption.md5(pwds[0]) + "/" + Encryption.md5(pwds[1]);
        String regNo = User.getRegNo(request);
        getCPwd = sysUserFeign.changePassWord(changePWInfo, regNo);
        String exitStr = "changefork";
        //读取配置文件，查看是否单独使用财务系统，“true”是单独使用独立系统，“false”不使用财务的独立系统
        String aloneUsedCw = PropertiesUtil.getWebServiceProperty("alone_used_cw");
        if ("true".equals(aloneUsedCw)) {
            exitStr = "cwLogin";
        }
        if (getCPwd.equals("")) {
            this.addActionError(model, "修改成功");
            return exitStr;
        } else {
            this.addActionError(model, getCPwd);
            return "changePassWordInfo";
        }
    }

    @RequestMapping(value = "/sessionInvalid")
    public String getsessionInvalid() throws Exception {
        return "layout/view/sessionInvalid";
    }

    /**
     * 功能描述：修改登录密码
     *
     * @return
     * @throws Exception
     * @author
     */

    public void changePwdByAjax(String changePWInfo) throws Exception {
        String getCPwd;
        //对更改后的密码加密
        String[] pwds = changePWInfo.split("/");
        changePWInfo = Encryption.md5(pwds[0]) + "/" + Encryption.md5(pwds[1]);
        String regNo = User.getRegNo(request);
        getCPwd = sysUserFeign.changePassWord(changePWInfo, regNo);
        HttpServletResponse response = getHttpResponse();
        response.setContentType("text/html;charset=GBK");
        PrintWriter pw = response.getWriter();
        if (getCPwd.equals("")) {
            pw.write("changeOK");
        } else {
            pw.write(getCPwd);
        }
        pw.flush();
        pw.close();
    }

    /***
     * 校验用户是否存在
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkExistAjax")
    @ResponseBody
    public Map<String, Object> checkExistAjax(Model model, String opName) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {
            String USER_LOGIN_COLUMN = new CodeUtils().getSingleValByKey("USER_LOGIN_COLUMN");// 操作员登录账号列
            SysUser sysUser = new SysUser();
            sysUser.setColumnQuery(USER_LOGIN_COLUMN);
            sysUser.setColumnVal(opName);
            sysUser = sysUserFeign.getById(sysUser);

            dataMap.put("flag", "success");
            dataMap.put("data", sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    private void SysLoginLogInsert(SysUser sysUser) {
        String Agent = request.getHeader("User-agent");//返回客户端浏览器的版本号、类型
        String sessionId = request.getSession().getId();
        try {
            SysLoginLog sysLoginLog = new SysLoginLog();
            sysLoginLog.setSessionId(sessionId.replace("-", ""));
            sysLoginLog.setBrNo(sysUser.getBrNo());
            sysLoginLog.setBrName(User.getOrgName(request));
            sysLoginLog.setOpNo(sysUser.getOpNo());
            sysLoginLog.setOpName(sysUser.getOpName());
            sysLoginLog.setLoginDate(DateUtil.getDate());
            sysLoginLog.setLoginTime(DateUtil.getTime());
            sysLoginLog.setRoleNo(sysUser.getRoleNo());
            sysLoginLog.setJob(sysUser.getJob());
            try {
                String ip = UserAgentParser.getClientIP(request);
                sysLoginLog.setLoginIp(ip);
            } catch (Exception e) {
                sysLoginLog.setLoginIp("0.0.0.0");
            }
            try {
                String[] os = UserAgentParser.getOS(Agent).split(" ");
                if (os.length == 2) {
                    sysLoginLog.setOsName(os[0]);
                    sysLoginLog.setOsVersion(os[1]);
                } else {
                    sysLoginLog.setOsName(UserAgentParser.getOS(Agent));
                    sysLoginLog.setOsVersion(UserAgentParser.getOS(Agent));
                }
            } catch (Exception e) {
                sysLoginLog.setOsName("未知的操作系统");
                sysLoginLog.setOsVersion("0.0");
            }
            try {
                String[] ie = UserAgentParser.getBrowser(Agent).split(" ");
                if (ie.length == 2) {
                    sysLoginLog.setIeName(ie[0]);
                    sysLoginLog.setIeVersion(ie[1]);
                } else {
                    sysLoginLog.setIeName(UserAgentParser.getBrowser(Agent));
                    sysLoginLog.setIeVersion(UserAgentParser.getBrowser(Agent));
                }
            } catch (Exception e) {
                sysLoginLog.setIeName("未知的浏览器");
                sysLoginLog.setIeVersion("0.0");
            }
            sysLoginLog.setLoginType(LOGIN_TYPE);
            sysLoginLogFeign.insert(sysLoginLog);
        } catch (Exception e) {

        }

    }

}

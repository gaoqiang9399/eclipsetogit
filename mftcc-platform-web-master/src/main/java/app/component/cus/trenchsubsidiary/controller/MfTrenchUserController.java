package app.component.cus.trenchsubsidiary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.base.ServiceException;
import app.component.doc.entity.DocBizManage;
import app.component.doc.entity.DocBizManageParam;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.HttpClientUtil;
import cn.mftcc.util.Md5Util;
import com.google.gson.Gson;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.base.UserAgentParser;
import app.base.shiro.SessionUserCheckService;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.trenchsubsidiary.feign.MfTrenchUserFeign;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.component.interfaces.mobileinterface.feign.WebCusLineRegFeign;
import app.component.interfacesinterface.InterfacesFeign;
import app.component.pmsinterface.PmsInterfaceFeign;
import app.component.secinterface.SecinterfaceFeign;
import app.component.sys.entity.SysLoginLog;
import app.component.sys.entity.SysRole;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.sys.util.Encryption;
import app.component.sysInterface.SysInterfaceFeign;
import app.util.JsonStrHandling;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfTrenchShareProfitRateAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 06 15:00:20 CST 2018
 * <p>
 * 因海马项目需求，此类被TrenchLoginController继承重写，全局参数及方法尽量使用protected。by wangchao 2018-04-19
 **/
@Controller
@RequestMapping("/mfTrenchUser")
public class MfTrenchUserController extends BaseFormBean {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected MfBusTrenchFeign mfBusTrenchFeign;

    @Autowired
    protected MfCusCustomerFeign mfCusCustomerFeign;

    @Autowired
    protected MfCusFormConfigFeign mfCusFormConfigFeign;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    protected MfTrenchUserFeign mfTrenchUserFeign;

    @Autowired
    protected SecinterfaceFeign secinterfaceFeign;

    @Autowired
    protected PmsInterfaceFeign pmsInterfaceFeign;

    @Autowired
    protected SysInterfaceFeign sysInterfaceFeign;

    @Autowired
    protected InterfacesFeign interfacesFeign;

    @Autowired
    protected WebCusLineRegFeign webCusLineRegFeign;
    @Autowired
    private SessionUserCheckService sessionUserCheckService;
    @Autowired
    private SysUserFeign sysUserFeign;

    @Value("${spring.shiro.sessionTimeOut}")
    protected String sessionTimeOut;

    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String htmlStr = "";
        try {
            Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);
            FormData formTrenchUserBase = new FormService().getFormData(ajaxDataMap.get("formId").toString());
            getFormValue(formTrenchUserBase, getMapByJson(ajaxData));
            if (this.validateFormData(formTrenchUserBase)) {
                WebCusLineReg webCusLineReg = new WebCusLineReg();
                setObjValue(formTrenchUserBase, webCusLineReg);
                WebCusLineReg cusLineReg = new WebCusLineReg();
                cusLineReg.setCusAccount(webCusLineReg.getCusAccount());
                List<WebCusLineReg> cusLineRegList = mfTrenchUserFeign.getCusLineRegList(cusLineReg);
                if (cusLineRegList != null && cusLineRegList.size() > 0) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("登录账号"));
                    return dataMap;
                }
                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(webCusLineReg.getChannelSourceNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                webCusLineReg.setCusNo(webCusLineReg.getChannelSourceNo());
                webCusLineReg.setCusType(mfCusCustomer.getCusType());
                webCusLineReg.setChannelSourceName(mfCusCustomer.getCusName());
                String projectName = ymlConfig.getSysParams().get("sys.project.name");
                if ("HNDFA".equals(projectName)) {
                    Gson gson = new Gson();
                    String hzsUrl = ymlConfig.getSysParams().get("sys.project.hzs.url");
                    webCusLineReg = mfTrenchUserFeign.insert(webCusLineReg);
                    webCusLineReg.setAreaName(mfCusCustomer.getCommAddress());
                    webCusLineReg.setCusTel(mfCusCustomer.getContactsTel());
                    String paramJson = gson.toJson(webCusLineReg);
                    HttpClientUtil.sendPostJson(paramJson, hzsUrl+"/mfTrenchUser/insertSysuser");
                } else {
                    webCusLineReg = mfTrenchUserFeign.insertCusLineReg(webCusLineReg);
                }
                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(webCusLineReg.getCusNo(),
                        webCusLineReg.getCusNo());// 更新客户信息完整度
                String tableId = "tableTrenchUserList";
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfTrenchUserAction");
                if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();//TODO 新增客户时存在bug，base中配置该表单没有带过来
                }
                cusLineReg = new WebCusLineReg();
                cusLineReg.setChannelSourceNo(webCusLineReg.getChannelSourceNo());
                cusLineReg.setActiveFlag(BizPubParm.YES_NO_Y);
                JsonTableUtil jtu = new JsonTableUtil();
                htmlStr = jtu.getJsonStr(tableId, "tableTag", mfTrenchUserFeign.getCusLineRegList(cusLineReg), null, true);
                dataMap.put("htmlStr", htmlStr);
                dataMap.put("infIntegrity", infIntegrity);
                dataMap.put("htmlStrFlag", "1");
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "新增失败");
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/updateByOneAjax")
    @ResponseBody
    public Map<String, Object> updateByOneAjax(String formId, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        // 这里得到的formId是带form字符串的，比如formcuscorp0001
        if (StringUtil.isEmpty(formId)) {
            formId = mfCusFormConfigFeign.getByCusType("base", "MfTrenchUserAction").getShowModel();
        } else {
            if (formId.indexOf("form") == -1) {
            } else {
                formId = formId.substring(4);
            }
        }
        FormData formTrenchUserBase = new FormService().getFormData(formId);
        getFormValue(formTrenchUserBase, getMapByJson(ajaxData));
        WebCusLineReg cusLineReg = new WebCusLineReg();
        setObjValue(formTrenchUserBase, cusLineReg);
        webCusLineReg.setId(cusLineReg.getId());
        webCusLineReg = mfTrenchUserFeign.getCusLineReg(webCusLineReg);
        if (webCusLineReg != null) {
            try {
                webCusLineReg = (WebCusLineReg) EntityUtil.reflectionSetVal(webCusLineReg, cusLineReg, getMapByJson(ajaxData));

                // 检测登录账号唯一性
                SysUser sysUser = new SysUser();
                sysUser.setLoginUser(cusLineReg.getCusAccount());
                sysUser.setOpNo(webCusLineReg.getOpNo());
                sysUser.setMobile(cusLineReg.getCusTel());
                Map<String, Object> checkMap = sysUserFeign.checkOnlyNo(sysUser, "edit");
                Boolean checkFlag = (Boolean) checkMap.get("checkFlag");
                if (!checkFlag) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage((String) checkMap.get("msg")));
                    return dataMap;
                }
                mfTrenchUserFeign.updateCusLineReg(webCusLineReg);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }

        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        webCusLineReg.setId(id);
        try {
            mfTrenchUserFeign.deteleCusLineReg(webCusLineReg);
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);

        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        String baseType = mfCusCustomer.getCusBaseType();
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfTrenchUserAction");
        FormData formTrenchUserBase = new FormService().getFormData(mfCusFormConfig.getAddModelDef());

        WebCusLineReg webCusLineReg = new WebCusLineReg();
        String id = WaterIdUtil.getWaterId();
        webCusLineReg.setId(id);
        webCusLineReg.setChannelSourceNo(mfCusCustomer.getCusNo());
        webCusLineReg.setChannelSourceName(mfCusCustomer.getCusName());
        webCusLineReg.setIdType(BizPubParm.ID_TYPE_ID_CARD);
        getObjValue(formTrenchUserBase, webCusLineReg);
        String opNoType = BizPubParm.OP_NO_TYPE2;
        if (BizPubParm.CUS_BASE_TYPE_QUDAO.equals(baseType) || BizPubParm.CUS_BASE_TYPE_QUDAOB.equals(baseType)) {
            opNoType = BizPubParm.OP_NO_TYPE2;
        } else if (BizPubParm.CUS_BASE_TYPE_ZIJIN.equals(baseType)) {
            opNoType = BizPubParm.OP_NO_TYPE3;
        } else if (BizPubParm.CUS_BASE_TYPE_WAERHOUSE.equals(baseType)) {
            opNoType = BizPubParm.OP_NO_TYPE4;
        }else if (BizPubParm.CUS_BASE_TYPE_HEXIAN.equals(baseType)) {
            opNoType = BizPubParm.OP_NO_TYPE5;//核心企业操作员
        }else {
        }
        Map<String, Object> dataMap = sysUserFeign.initSysUser(cusNo, opNoType);
        String ajaxData = new Gson().toJson(dataMap.get("json"));

        DocBizManageParam dbmp = new DocBizManageParam();
        dbmp.setScNo("0000000001");// 场景编号
        dbmp.setRelNo(id);// 业务编号
        dbmp.setDime(opNoType);

        mfTrenchUserFeign.initiaBiz(dbmp);

        model.addAttribute("formTrenchUserBase", formTrenchUserBase);
        model.addAttribute("id", id);
        model.addAttribute("opNoType", opNoType);
        model.addAttribute("webCusLineReg", webCusLineReg);
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("query", "");
        return "/component/cus/trenchsubsidiary/MfTrenchUser_Insert";
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String id, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("id", id);
        return "/component/cus/trenchsubsidiary/MfTrenchUser_Detail";
    }

    // 列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfTrenchUserAction");
        String formId = null;
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getShowModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
            // logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfTrenchUserAction表单信息没有查询到");
            dataMap.put("msg", "客户类型为" + mfCusCustomer.getCusType() + "的MfTrenchUserAction表单信息没有查询到");
            dataMap.put("flag", "error");
        } else {
            Map<String, Object> formData = new HashMap<String, Object>();
            request.setAttribute("ifBizManger", "3");
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            FormData formTrenchUserBase = new FormService().getFormData(formId);
            WebCusLineReg webCusLineReg = new WebCusLineReg();
            webCusLineReg.setId(id);
            webCusLineReg = mfTrenchUserFeign.getCusLineReg(webCusLineReg);
            webCusLineReg.setChannelSourceName(mfCusCustomer.getCusName());
            webCusLineReg.setIdType(BizPubParm.ID_TYPE_ID_CARD);
            getObjValue(formTrenchUserBase, webCusLineReg, formData);
            String query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
            if (query == null) {
                query = "";
            }
            String htmlStrCorp = jsonFormUtil.getJsonStr(formTrenchUserBase, "propertySeeTag", query);
            dataMap.put("formHtml", htmlStrCorp);
            dataMap.put("flag", "success");
            dataMap.put("formData", webCusLineReg);
        }

        return dataMap;
    }

    /**
     * 方法描述： 渠道登录验证
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-7 下午8:51:25
     */
    @RequestMapping(value = "/trench")
    public String userLogin(Model model, String opNo, String passwordhash, String frame) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 是否允许登录渠道业务系统 0不允许1允许。
        String trenchLoginFlag = PropertiesUtil.getWebServiceProperty("trench_login_flag");
        if ("0".equals(trenchLoginFlag)) {
            return "entryLease";
        }

        String exitStr = "trenchLogin";
        if ("".equals(opNo) || opNo == null) {
            this.addActionError(model, "用户名不能为空！");

            return exitStr;
        }
        if ("".equals(passwordhash) || passwordhash == null) {
            this.addActionError(model, "密码不能为空！");

            return exitStr;
        }
        /**
         * 增加css、js的版本号,解决缓存问题，在具体文件中只需要在路径后追加?v=${cssJsVersion}即可。 同拦截器中的用法。 add by LiuYF 2017-9-11
         */
        request.getSession().setAttribute("cssJsVersion", "v" + DateUtil.getDate());

        WebCusLineReg webCusLineReg = new WebCusLineReg();
        webCusLineReg.setCusTel(opNo);
        webCusLineReg.setCusAccount(opNo);
        webCusLineReg.setCusPassword(passwordhash);
        dataMap = mfTrenchUserFeign.checkLogin(webCusLineReg);
        String flag = (String) dataMap.get("flag");

        webCusLineReg = JsonStrHandling.handlingStrToBean(dataMap.get("cusLineReg"), WebCusLineReg.class);
        MfBusTrench mfBusTrench = JsonStrHandling.handlingStrToBean(dataMap.get("mfBusTrench"), MfBusTrench.class);

        // String password = Encryption.md5(passwordhash);

        String remeg = "main";// XL安全审计调用返回值
        if ("noExsit".equals(flag)) {
            this.addActionError(model, "用户不存在！");
        } else if ("trenchDisable".equals(flag)) {
            this.addActionError(model, "该用户所在渠道商已终止或暂停,无法登陆!");
        } else if ("noActive".equals(flag)) {
            this.addActionError(model, "该用户没有激活,无法登陆!");
        } else if ("passwordError".equals(flag)) {
            this.addActionError(model, "密码错误!");
        } else if ("success".equals(flag)) {
            String returnFlag = handleLoginSuccessBusiness(remeg, mfBusTrench, webCusLineReg, null, frame, opNo, model);

//			Subject currentUser = SecurityUtils.getSubject();
//			SecurityUtils.getSubject().getSession().setTimeout(Integer.valueOf(sessionTimeOut));
//			UsernamePasswordToken token = new UsernamePasswordToken(opNo, passwordhash);
//			Session session = currentUser.getSession(true);// 强制制重新生成Session
//			currentUser.login(token);

            exitStr = returnFlag;
        }else {
        }

        model.addAttribute("dataMap", dataMap);
        model.addAttribute("webCusLineReg", webCusLineReg);
        model.addAttribute("mfBusTrench", mfBusTrench);

        return exitStr;
    }

    /**
     * 处理登陆成功业务
     *
     * @param remeg
     * @param webCusLineReg
     * @param returnflag    默认：null 返回的是用户名密码登陆失败返回的错误页面;"1" : 扫码登陆失败页面 <br/>
     *                      区分扫描二维码 还是 输入用户名密码 登陆失败要返回的页面 字符串信息
     * @return
     * @throws Exception
     */
    protected String handleLoginSuccessBusiness(String remeg, MfBusTrench mfBusTrench, WebCusLineReg webCusLineReg, String returnflag, String frame, String opNo, Model model) throws Exception {
        String returnFlag = "trenchLogin";

        if ("viewIE8".equals(frame)) {
            returnFlag = viewIE8Mode(webCusLineReg, mfBusTrench);
        } else if ("menu".equals(frame)) {
            returnFlag = menuMode(webCusLineReg, mfBusTrench);
        } else {
            returnFlag = viewMode(webCusLineReg, mfBusTrench);
        }
        // 判断操作员是否已经登录，登录则T下线
        //if (LoginSessionListener.isOnline(opNo)) {
        sessionUserCheckService.kickFirstOper(this.getHttpRequest().getSession(), opNo);// 把第一个用户T下线
        //}
        // 往map中添加操作员sessionid和op_name的映射关系
        //LoginSessionListener.putSessionMap(this.getHttpRequest().getSession(), opNo);
        remeg = secinterfaceFeign.SecurityPwd(opNo);// XL安全审计调用校验接口
        // remeg = "main";//（*****************校验开启注此行代码************）
        if ("main".equalsIgnoreCase(remeg))// XL安全审计调用接口，记录登录信息
        {
            secinterfaceFeign.insertOrUpdate(opNo, remeg);
        } else {// XL安全审计根据返回值判断跳转页是修改密码页面还是登陆页面
            String result = "";
            if ("toup".equals(remeg.substring(0, 4))) {
                result = "updatepwd";// 跳转页面至修改密码
            } else if ("toln".equals(remeg.substring(0, 4))) {
                result = "trenchLogin";
            }else {
            }
            secinterfaceFeign.insertOrUpdate(opNo, remeg.substring(4, remeg.length()));
            this.addActionError(model, remeg.substring(4, remeg.length()));
            return result;
        }
        // 记录登录历史
        SysLoginLogInsert(webCusLineReg);
        return returnFlag;
    }

    /**
     * @param @return
     * @return String
     * @throws Exception
     * @throws @author   wangcong
     * @Description: IE8模式登录加载项
     * @date 2015年12月24日下午2:35:36
     */
    protected String viewIE8Mode(WebCusLineReg webCusLineReg, MfBusTrench mfBusTrench) throws Exception {
        this.getHttpRequest().getSession().setAttribute("sysFlag", "trench");
        this.getHttpRequest().getSession().setAttribute("cusTel", webCusLineReg.getCusTel());
        this.getHttpRequest().getSession().setAttribute("cusNickname", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("channelSourceNo", webCusLineReg.getChannelSourceNo());
        this.getHttpRequest().getSession().setAttribute("channelSourceName", webCusLineReg.getChannelSourceName());
        this.getHttpRequest().getSession().setAttribute("trenchOpNo", webCusLineReg.getCusAccount());
        this.getHttpRequest().getSession().setAttribute("trenchOpName", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("mobile", webCusLineReg.getCusTel());
        this.getHttpRequest().getSession().setAttribute("email", webCusLineReg.getCusEmail());
        this.getHttpRequest().getSession().setAttribute("dataRang", webCusLineReg.getExt1());
        this.getHttpRequest().getSession().setAttribute("trenchOpNos", webCusLineReg.getExt2());
        String[] roleNo = {"Advance"};
        this.getHttpRequest().getSession().setAttribute("roleNo", roleNo);
        // 验证登录使用
        this.getHttpRequest().getSession().setAttribute("regNo", webCusLineReg.getCusAccount());
        this.getHttpRequest().getSession().setAttribute("regName", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("orgNo", mfBusTrench.getAttachBrNo());
        this.getHttpRequest().getSession().setAttribute("orgName", mfBusTrench.getAttachBrName());
        this.getHttpRequest().getSession().setAttribute("businessAreaNo", mfBusTrench.getBusinessAreaNo());
        this.getHttpRequest().getSession().setAttribute("businessArea", mfBusTrench.getBusinessArea());
        /*
         * 首页设置参数-factor
         */
        this.getHttpRequest().getSession().setAttribute("homePage", "1");
        // 表单权限使用
        this.getHttpRequest().getSession().setAttribute("sys_date", DateUtil.getDate());
        this.getHttpRequest().getSession().setAttribute("tlrno", webCusLineReg.getCusAccount());
        return "viewIE8";
    }

    /**
     * @param @return
     * @return String
     * @throws @author wangcong
     * @Description: 菜单模式登录加载项
     * @date 2015年12月24日下午2:45:44
     */
    protected String menuMode(WebCusLineReg webCusLineReg, MfBusTrench mfBusTrench) throws Exception {
        this.getHttpRequest().getSession().setAttribute("sysFlag", "trench");
        this.getHttpRequest().getSession().setAttribute("cusTel", webCusLineReg.getCusTel());
        this.getHttpRequest().getSession().setAttribute("cusNickname", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("channelSourceNo", webCusLineReg.getChannelSourceNo());
        this.getHttpRequest().getSession().setAttribute("channelSourceName", webCusLineReg.getChannelSourceName());
        this.getHttpRequest().getSession().setAttribute("trenchOpNo", webCusLineReg.getCusAccount());
        this.getHttpRequest().getSession().setAttribute("trenchOpName", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("mobile", webCusLineReg.getCusTel());
        this.getHttpRequest().getSession().setAttribute("email", webCusLineReg.getCusEmail());
        this.getHttpRequest().getSession().setAttribute("dataRang", webCusLineReg.getExt1());
        this.getHttpRequest().getSession().setAttribute("trenchOpNos", webCusLineReg.getExt2());
        String[] roleNo = {"Advance"};
        this.getHttpRequest().getSession().setAttribute("roleNo", roleNo);
        // 验证登录使用
        this.getHttpRequest().getSession().setAttribute("regNo", webCusLineReg.getCusAccount());
        this.getHttpRequest().getSession().setAttribute("regName", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("orgNo", mfBusTrench.getAttachBrNo());
        this.getHttpRequest().getSession().setAttribute("orgName", mfBusTrench.getAttachBrName());
        this.getHttpRequest().getSession().setAttribute("businessAreaNo", mfBusTrench.getBusinessAreaNo());
        this.getHttpRequest().getSession().setAttribute("businessArea", mfBusTrench.getBusinessArea());
        /*
         * 首页设置参数-factor
         */
        this.getHttpRequest().getSession().setAttribute("homePage", "1");
        // 表单权限使用
        this.getHttpRequest().getSession().setAttribute("sys_date", DateUtil.getDate());
        this.getHttpRequest().getSession().setAttribute("tlrno", webCusLineReg.getCusAccount());
        return "menu";
    }

    /**
     * @param @return
     * @return String
     * @throws Exception
     * @throws @author   wangcong
     * @Description: 视角模式登录加载项
     * @date 2015年12月24日下午2:35:36
     */
    protected String viewMode(WebCusLineReg webCusLineReg, MfBusTrench mfBusTrench) throws Exception {
        this.getHttpRequest().getSession().setAttribute("sysFlag", "trench");
        this.getHttpRequest().getSession().setAttribute("cusTel", webCusLineReg.getCusTel());
        this.getHttpRequest().getSession().setAttribute("cusNickname", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("channelSourceNo", webCusLineReg.getChannelSourceNo());
        this.getHttpRequest().getSession().setAttribute("channelSourceName", webCusLineReg.getChannelSourceName());
        this.getHttpRequest().getSession().setAttribute("trenchOpNo", webCusLineReg.getCusAccount());
        this.getHttpRequest().getSession().setAttribute("trenchOpName", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("mobile", webCusLineReg.getCusTel());
        this.getHttpRequest().getSession().setAttribute("email", webCusLineReg.getCusEmail());
        this.getHttpRequest().getSession().setAttribute("dataRang", webCusLineReg.getExt1());
        this.getHttpRequest().getSession().setAttribute("trenchOpNos", webCusLineReg.getExt2());

        String[] roleNo = {"Advance"};
        if (StringUtil.isNotEmpty(webCusLineReg.getRoleNo())) {
            roleNo = webCusLineReg.getRoleNo().split("\\|");
        }
        this.getHttpRequest().getSession().setAttribute("roleNo", roleNo);

        // 验证登录使用
        this.getHttpRequest().getSession().setAttribute("regNo", webCusLineReg.getCusAccount());
        this.getHttpRequest().getSession().setAttribute("regName", webCusLineReg.getCusNickname());
        this.getHttpRequest().getSession().setAttribute("orgNo", mfBusTrench.getAttachBrNo());
        this.getHttpRequest().getSession().setAttribute("orgName", mfBusTrench.getAttachBrName());
        this.getHttpRequest().getSession().setAttribute("businessAreaNo", mfBusTrench.getBusinessAreaNo());
        this.getHttpRequest().getSession().setAttribute("businessArea", mfBusTrench.getBusinessArea());
        /*
         * 首页设置参数-factor
         */
        this.getHttpRequest().getSession().setAttribute("homePage", "1");
        // 表单权限使用
        this.getHttpRequest().getSession().setAttribute("sys_date", DateUtil.getDate());
        this.getHttpRequest().getSession().setAttribute("tlrno", webCusLineReg.getCusAccount());
        return "/layout/view/trenchView";
    }

    /**
     * 方法描述： 记录登录信息
     *
     * @param webCusLineReg void
     * @author 沈浩兵
     * @date 2018-3-14 下午5:15:17
     */
    protected void SysLoginLogInsert(WebCusLineReg webCusLineReg) {
        String Agent = request.getHeader("User-agent");// 返回客户端浏览器的版本号、类型
        String sessionId = request.getSession().getId();
        try {
            SysLoginLog sysLoginLog = new SysLoginLog();
            sysLoginLog.setSessionId(sessionId);
            sysLoginLog.setBrNo("");
            sysLoginLog.setBrName(webCusLineReg.getChannelSourceName());
            sysLoginLog.setOpNo(webCusLineReg.getCusAccount());
            sysLoginLog.setOpName(webCusLineReg.getCusNickname());
            sysLoginLog.setLoginDate(DateUtil.getDate());
            sysLoginLog.setLoginTime(DateUtil.getTime());
            sysLoginLog.setRoleNo("");
            sysLoginLog.setJob("");
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
            sysLoginLog.setLoginType("浏览器登录");
            sysInterfaceFeign.insertSysLoginLog(sysLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 方法描述：退出登录
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-13 下午2:43:04
     */
    @RequestMapping(value = "/userLogout")
    public String userLogout() throws Exception {
        HttpSession session = this.getHttpRequest().getSession();
        // 操作员登出写日志
        Object tlrno = session.getAttribute("opName");
        if (tlrno != null) {
        }
        String exitStr = "trenchLogin";
        // 是否允许登录渠道业务系统 0不允许1允许。
        String trenchLoginFlag = PropertiesUtil.getWebServiceProperty("trench_login_flag");
        if ("0".equals(trenchLoginFlag)) {
            return "entryLease";
        }
        try {
            secinterfaceFeign.insertOrUpdate(User.getRegNo(this.getHttpRequest()), "logout");
            this.getHttpRequest().getSession().invalidate();// 销毁session
        } catch (Exception e) {
            return exitStr;// 如果清session出现异常说明session已经销毁一次，则直接返回登录界面
        }

        return exitStr;
    }

    /**
     * 方法描述： 验证用户是否存在
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-7 下午7:46:25
     */
    @RequestMapping(value = "/checkUserExistAjax")
    @ResponseBody
    public Map<String, Object> checkUserExistAjax(String checkType, String cusAccount, String cusTel) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            SysUser sysUser = new SysUser();
            sysUser.setLoginUser(cusAccount);
            sysUser.setOpNo(cusAccount);
            sysUser.setMobile(cusTel);
            sysUser = sysUserFeign.getByMobile(sysUser);
            if (sysUser != null) {
                dataMap.put("flag", "success");
                if ("1".equals(checkType)) {// 验证登录账号
                    dataMap.put("msg", "登录账号");
                } else if ("2".equals(checkType)) {// 验证手机号码
                    dataMap.put("msg", "手机号码");
                }else {
                }
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }

    /**
     * 验证渠道操作员是否存在
     */
    @RequestMapping(value = "/checkTrenchUserExistAjax")
    @ResponseBody
    public Map<String, Object> checkTrenchUserExistAjax(String val) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        List<WebCusLineReg> cusLineRegsList = null;
        try {
            webCusLineReg.setCusAccount(val);
            webCusLineReg.setCusTel(val);
            webCusLineReg.setCusNickname(val);
            cusLineRegsList = webCusLineRegFeign.getCusLineRegList(webCusLineReg);
            if (cusLineRegsList != null && cusLineRegsList.size() == 1) {
                webCusLineReg = cusLineRegsList.get(0);
                MfBusTrench mfBusTrench = new MfBusTrench();
                mfBusTrench.setTrenchUid(webCusLineReg.getChannelSourceNo());
                mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
                if (mfBusTrench != null) {
                    // 渠道状态为非正常站台
                    if ("2".equals(mfBusTrench.getTrenchStatus()) || "3".equals(mfBusTrench.getTrenchStatus())) {
                        resultMap.put("flag", "trenchDisable");
                    } else if ("1".equals(mfBusTrench.getTrenchStatus())) {
                        if ("0".equals(webCusLineReg.getActiveFlag())) {
                            resultMap.put("flag", "userDisable");
                        } else if ("1".equals(webCusLineReg.getActiveFlag())) {
                            resultMap.put("webCusLineReg", webCusLineReg);
                            resultMap.put("flag", "success");
                        } else {
                            resultMap.put("flag", "userDisable");
                        }
                    }else {
                    }
                }
            } else if (cusLineRegsList != null && cusLineRegsList.size() >= 1) {
                resultMap.put("flag", "repeat");// 用户名重复
            } else if (cusLineRegsList != null && cusLineRegsList.size() == 0) {
                resultMap.put("flag", "no");
            } else {
                resultMap.put("flag", "no");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resultMap;
    }

    /**
     * 方法描述： 跳转渠道中心页面
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-8 上午10:07:13
     */
    @RequestMapping(value = "/getTrenchCenterInfo")
    public String getTrenchCenterInfo(Model model) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String trenchUid = (String) request.getSession().getAttribute("channelSourceNo");
        String opNo = (String) request.getSession().getAttribute("trenchOpNo");
        String dataRang = (String) request.getSession().getAttribute("dataRang");
        /*
         * mfBusTrench.setTrenchUid(trenchUid); mfBusTrench=mfBusTrenchBo.getByUId(mfBusTrench);
         */
        dataMap = mfTrenchUserFeign.getTenchCenterData(trenchUid, opNo, dataRang);
        MfBusTrench mfBusTrench = JsonStrHandling.handlingStrToBean(dataMap.get("mfBusTrench"), MfBusTrench.class);
        // String[] roleNoStr=(String[]) ServletActionContext.getRequest().getSession().getAttribute("roleNo");
        // mfDeskMsgItem = new MfDeskMsgItem();
        // mfDeskMsgItem.setOpNo(opNo);
        // mfDeskMsgItem.setRoleNoStr(roleNoStr);
        // mfDeskMsgItemList = deskInterface.getMsgList(mfDeskMsgItem);

        model.addAttribute("trenchUid", trenchUid);
        model.addAttribute("opNo", opNo);
        model.addAttribute("mfBusTrench", mfBusTrench);

        return "/component/cus/trenchsubsidiary/TrenchCenterInfo";
    }

    /**
     * 方法描述： 获得渠道用户信息列表html
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-13 下午4:36:01
     */
    @RequestMapping(value = "/getTrenchUserListHtmlAjax")
    @ResponseBody
    public Map<String, Object> getTrenchUserListHtmlAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JsonTableUtil jtu = new JsonTableUtil();
            WebCusLineReg webCusLineReg = new WebCusLineReg();
            webCusLineReg.setId(id);
            webCusLineReg = mfTrenchUserFeign.getCusLineReg(webCusLineReg);
            if (webCusLineReg != null) {
                WebCusLineReg cusLineReg = new WebCusLineReg();
                cusLineReg.setChannelSourceNo(webCusLineReg.getChannelSourceNo());
                List<WebCusLineReg> WebCusLineRegList = mfTrenchUserFeign.getCusLineRegList(cusLineReg);
                if (WebCusLineRegList != null && WebCusLineRegList.size() > 0) {
                    String htmlStr = jtu.getJsonStr("tableTrenchUserList", "tableTag", WebCusLineRegList, null, true);
                    dataMap.put("flag", "success");
                    dataMap.put("htmlStr", htmlStr);
                } else {
                    dataMap.put("flag", "error");
                }
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }

    /**
     * 方法描述： 修改密码
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-13 下午5:07:44
     */
    @RequestMapping(value = "/editPasswordInput")
    public String editPasswordInput(Model model) throws Exception {
        ActionContext.initialize(request, response);

        FormData formTrenchUserBase = new FormService().getFormData("editTrenchUserPassword");
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        webCusLineReg.setCusAccount((String) request.getSession().getAttribute("regNo"));
        webCusLineReg.setCusNickname((String) request.getSession().getAttribute("regName"));
        getObjValue(formTrenchUserBase, webCusLineReg);

        model.addAttribute("formTrenchUserBase", formTrenchUserBase);
        model.addAttribute("webCusLineReg", webCusLineReg);

        return "/component/cus/trenchsubsidiary/MfTrenchUser_EditPassword";
    }

    /**
     * 方法描述： 验证原密码是否正确
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-13 下午5:33:52
     */
    @RequestMapping(value = "/checkUserPasswordAjax")
    @ResponseBody
    public Map<String, Object> checkUserPasswordAjax(String passwordhash) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        try {
            webCusLineReg.setCusAccount((String) request.getSession().getAttribute("regNo"));
            webCusLineReg.setCusPassword(Md5Util.getMD5Str(passwordhash));
            List<WebCusLineReg> WebCusLineRegList = mfTrenchUserFeign.getCusLineRegList(webCusLineReg);
            if (WebCusLineRegList != null && WebCusLineRegList.size() > 0) {
                dataMap.put("flag", "success");
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }

    /**
     * 方法描述： 修改密码
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-13 下午5:45:01
     */
    @RequestMapping(value = "/updateUserPasswordAjax")
    @ResponseBody
    public Map<String, Object> updateUserPasswordAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        try {
            FormData formTrenchUserBase = new FormService().getFormData("editTrenchUserPassword");
            getFormValue(formTrenchUserBase, getMapByJson(ajaxData));
            if (this.validateFormData(formTrenchUserBase)) {
                dataMap = getMapByJson(ajaxData);
                String passwordhash = (String) dataMap.get("password1");
                webCusLineReg.setCusAccount((String) request.getSession().getAttribute("regNo"));
                webCusLineReg.setCusPassword(Md5Util.getMD5Str(passwordhash));
                mfTrenchUserFeign.updateCusLineReg(webCusLineReg);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }

    /**
     * 方法描述： 重置密码
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-14 上午11:55:27
     */
    @RequestMapping(value = "/resetPasswordAjax")
    @ResponseBody
    public Map<String, Object> resetPasswordAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        try {
            String passwordhash = "000000";
            webCusLineReg.setId(id);
            webCusLineReg.setCusPassword(Encryption.md5(passwordhash));// 密码MD5加密;
            mfTrenchUserFeign.updateCusLineReg(webCusLineReg);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_SYSUSER_PASSWORD.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }

    /**
     * 方法描述： 验证用户账号是否存在
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-7 下午7:46:25
     */
    @RequestMapping(value = "/checkAccountExistAjax")
    @ResponseBody
    public Map<String, Object> checkAccountExistAjax(String checkType, String cusAccount, String cusTel) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        WebCusLineReg webCusLineReg = new WebCusLineReg();
        try {
            if ("1".equals(checkType)) {// 验证登录账号
                webCusLineReg.setCusAccount(cusAccount);
            } else if ("2".equals(checkType)) {// 验证手机号码
                webCusLineReg.setCusTel(cusTel);
            }else {
            }
            List<WebCusLineReg> WebCusLineRegList = mfTrenchUserFeign.getCusLineRegList(webCusLineReg);
            if (WebCusLineRegList != null && WebCusLineRegList.size() > 0) {
                dataMap.put("user", WebCusLineRegList.get(0));
                dataMap.put("flag", "success");
                if ("1".equals(checkType)) {// 验证登录账号
                    dataMap.put("msg", "登录账号");
                } else if ("2".equals(checkType)) {// 验证手机号码
                    dataMap.put("msg", "手机号码");
                }else {
                }
            } else {
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }

        return dataMap;
    }
}

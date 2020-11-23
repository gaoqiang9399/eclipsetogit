package app.component.sys.controller;

import app.base.User;
import cn.mftcc.util.PropertiesUtil;
import app.base.cache.feign.BusiCacheFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.PasConstant;
import app.component.desk.entity.MfDeskMsgItem;
import app.component.deskinterface.DeskInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.oainterface.OaInterfaceFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pms.entity.PmsEntranceRole;
import app.component.pmsinterface.PmsInterfaceFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysRole;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.EntityUtilFeign;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysRoleFeign;
import app.component.sys.feign.SysTaskInfoFeign;
import app.component.sys.feign.SysUserFeign;
import app.component.sys.util.Encryption;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import app.component.deskinterface.DeskInterfaceFeign;
//import app.component.pactinterface.PactInterfaceFeign;

/**
 * Title: SysUserAction.java Description:
 *
 * @author:@dhcc.com.cn
 * @Mon Jan 11 07:02:47 GMT 2016
 **/
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseFormBean {
    protected final Logger log = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private SysUserFeign sysUserFeign;
    @Autowired
    private EntityUtilFeign entityUtilFeign;
    @Autowired
    private SysRoleFeign sysRoleFeign;
    @Autowired
    private SysOrgFeign sysOrgFeign;
    @Autowired
    private SysTaskInfoFeign sysTaskInfoFeign;

    @Autowired
    private DeskInterfaceFeign deskInterfaceFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private PmsInterfaceFeign pmsInterfaceFeign;
    //	private ConsExtendInterface consExtendInterface;
    @Autowired
    private OaInterfaceFeign oaInterfaceFeign;
    @Autowired
    private BusiCacheFeign busiCacheFeign;

    // 全局变量
    private String query;
    // 表单变量
    private FormData formsys5001;
    private FormData formsys5002;
    private FormData formsys5005;
    private FormData formsysuploadhead0001;
    private FormService formService = new FormService();

    public SysUserController() {
        query = "";
    }

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model, String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        JSONObject json = new JSONObject();
//		CodeUtils cu = new CodeUtils();
//		JSONArray sexArray = JSONArray.fromObject(cu.getCacheByKeyName("SEX"));
//		for (int i = 0; i < sexArray.size(); i++) {
//			sexArray.getJSONObject(i).put("id",
//					sexArray.getJSONObject(i).getString("optCode"));
//			sexArray.getJSONObject(i).put("text",
//					sexArray.getJSONObject(i).getString("optName"));
//		}
//		json.put("sex", sexArray);
//		JSONArray userArray = JSONArray.fromObject(cu
//				.getCacheByKeyName("USER_STS"));
//		for (int i = 0; i < userArray.size(); i++) {
//			userArray.getJSONObject(i).put("id",
//					userArray.getJSONObject(i).getString("optCode"));
//			userArray.getJSONObject(i).put("text",
//					userArray.getJSONObject(i).getString("optName"));
//		}
//		json.put("userSts", userArray);
//		JSONArray roleArray = JSONArray.fromObject(sysRoleFeign.getAllList(new SysRole()));
//		for (int i = 0; i < roleArray.size(); i++) {
//			roleArray.getJSONObject(i).put("id",
//					roleArray.getJSONObject(i).getString("roleNo"));
//			roleArray.getJSONObject(i).put("text",
//					roleArray.getJSONObject(i).getString("roleName"));
//		}
//		json.put("role", roleArray);
//		JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAllOrg());
//		for (int i = 0; i < orgArray.size(); i++) {
//			orgArray.getJSONObject(i).put("id",
//					orgArray.getJSONObject(i).getString("brNo"));
//			orgArray.getJSONObject(i).put("text",
//					orgArray.getJSONObject(i).getString("brName"));
//		}
//		json.put("org", orgArray);
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("opNoType", opNoType);
        return "/component/sys/SysUser_List";
    }

    /**
     * @author czk
     * @Description: 获得选择用户页面
     * date 2017-1-18
     */
    @RequestMapping(value = "/getListPageForSel")
    public String getListPageForSel(Model model, String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        JSONObject json = new JSONObject();
        CodeUtils cu = new CodeUtils();
        JSONArray sexArray = JSONArray.fromObject(cu.getCacheByKeyName("SEX"));
        for (int i = 0; i < sexArray.size(); i++) {
            sexArray.getJSONObject(i).put("id",
                    sexArray.getJSONObject(i).getString("optCode"));
            sexArray.getJSONObject(i).put("text",
                    sexArray.getJSONObject(i).getString("optName"));
        }
        json.put("sex", sexArray);
        JSONArray userArray = JSONArray.fromObject(cu
                .getCacheByKeyName("USER_STS"));
        for (int i = 0; i < userArray.size(); i++) {
            userArray.getJSONObject(i).put("id",
                    userArray.getJSONObject(i).getString("optCode"));
            userArray.getJSONObject(i).put("text",
                    userArray.getJSONObject(i).getString("optName"));
        }
        json.put("userSts", userArray);
        SysRole sysRole = new SysRole();
        sysRole.setOpNoType(opNoType);
        JSONArray roleArray = JSONArray.fromObject(sysRoleFeign.getAllList(sysRole));
        for (int i = 0; i < roleArray.size(); i++) {
            roleArray.getJSONObject(i).put("id",
                    roleArray.getJSONObject(i).getString("roleNo"));
            roleArray.getJSONObject(i).put("text",
                    roleArray.getJSONObject(i).getString("roleName"));
        }
        json.put("role", roleArray);
        SysOrg sysOrg = new SysOrg();
        sysOrg.setOpNoType(opNoType);
        JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAllOrg(sysOrg));
        for (int i = 0; i < orgArray.size(); i++) {
            orgArray.getJSONObject(i).put("id",
                    orgArray.getJSONObject(i).getString("brNo"));
            orgArray.getJSONObject(i).put("text",
                    orgArray.getJSONObject(i).getString("brName"));
        }
        json.put("org", orgArray);
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        return "/component/sys/SysUser_listForSel";
    }

    /**
     * @author czk
     * @Description: 获得选择用户页面
     * date 2017-1-18
     */
    @RequestMapping(value = "/getListPageForSel2")
    public String getListPageForSel2(Model model, String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        JSONObject json = new JSONObject();
        CodeUtils cu = new CodeUtils();
        JSONArray sexArray = JSONArray.fromObject(cu.getCacheByKeyName("SEX"));
        for (int i = 0; i < sexArray.size(); i++) {
            sexArray.getJSONObject(i).put("id",
                    sexArray.getJSONObject(i).getString("optCode"));
            sexArray.getJSONObject(i).put("text",
                    sexArray.getJSONObject(i).getString("optName"));
        }
        json.put("sex", sexArray);
        JSONArray userArray = JSONArray.fromObject(cu
                .getCacheByKeyName("USER_STS"));
        for (int i = 0; i < userArray.size(); i++) {
            userArray.getJSONObject(i).put("id",
                    userArray.getJSONObject(i).getString("optCode"));
            userArray.getJSONObject(i).put("text",
                    userArray.getJSONObject(i).getString("optName"));
        }
        json.put("userSts", userArray);
        SysRole sysRole = new SysRole();
        sysRole.setOpNoType(opNoType);
        JSONArray roleArray = JSONArray.fromObject(sysRoleFeign.getAllList(sysRole));
        for (int i = 0; i < roleArray.size(); i++) {
            roleArray.getJSONObject(i).put("id",
                    roleArray.getJSONObject(i).getString("roleNo"));
            roleArray.getJSONObject(i).put("text",
                    roleArray.getJSONObject(i).getString("roleName"));
        }
        json.put("role", roleArray);
        SysOrg sysOrg = new SysOrg();
        sysOrg.setOpNoType(opNoType);
        JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAllOrg(sysOrg));
        for (int i = 0; i < orgArray.size(); i++) {
            if ("100011".equals(orgArray.getJSONObject(i).getString("brNo"))) {
                orgArray.getJSONObject(i).put("id",
                        orgArray.getJSONObject(i).getString("brNo"));
                orgArray.getJSONObject(i).put("text",
                        orgArray.getJSONObject(i).getString("brName"));
            }
        }
        json.put("org", orgArray);
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        return "/component/sys/SysUser_listForSel2";
    }

    @RequestMapping(value = "/getListPageForNotice")
    public String getListPageForNotice() throws Exception {
        ActionContext.initialize(request, response);
        return "/component/sys/SysUser_List_Notice";
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/findByPageForNoticeAjax")
    @ResponseBody
    public Map<String, Object> findByPageForNoticeAjax(String ajaxData, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
            // this.getRoleConditions(sysUser,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            sysUser.setBrNo(User.getOrgNo(request));
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = sysUserFeign.findByPage(ipage);
            List<SysUser> suList = (List<SysUser>) ipage.getResult();
            CodeUtils cu = new CodeUtils();
            List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
            for (Iterator iterator = suList.iterator(); iterator.hasNext(); ) {
                SysUser su = (SysUser) JSONObject.toBean(JSONObject.fromObject(iterator.next()), SysUser.class);
                String role = su.getRoleNo();
                String[] roleArr = new String[0];
                if (role != null) {
                    if (role.indexOf("|") > -1) {
                        roleArr = role.split("\\|");
                    } else {
                        roleArr = new String[]{role};
                    }
                }
                for (int i = 0; i < roleArr.length; i++) {
                    for (ParmDic pd : pdList) {
                        if (roleArr[i].equals(pd.getOptCode())) {
                            roleArr[i] = pd.getOptName();
                        }
                    }
                }
                role = "";
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < roleArr.length; i++) {
                    if (!roleArr[i].isEmpty()) {
                        list.add(roleArr[i]);
                    }
                }
                String[] newRoleArr = list.toArray(new String[1]);
                for (int i = 0; i < newRoleArr.length; i++) {
                    if (i == 0) {
                        role += newRoleArr[i];
                    } else {
                        role += "|" + newRoleArr[i];
                    }
                }
                su.setRoleName(role);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType,
                    (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 获取用户号前缀
     */
    @RequestMapping(value = "/getUserPreAjax")
    @ResponseBody
    public Map<String, Object> getUserPreAjax(String brNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            SysOrg sysOrg = sysOrgFeign.getByBrNo(brNo);
            String userPre = sysOrg.getUserPre();
            String opNo = sysUserFeign.getMaxOpNo(userPre);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("生成用户号"));
            dataMap.put("data", opNo);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("生成用户号"));
        }
        return dataMap;
    }

    /***
     * 查询客户经理（客户移交用）
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllAjax")
    @ResponseBody
    public Map<String, Object> getAllAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        String parm = "";
        try {
            EntityUtil entityUtil = new EntityUtil();
            dataMap.put("data", entityUtil.prodAutoMenu(sysUser, ajaxData, query, parm, null));
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 查询客户(登录用户关联用)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAll2Ajax")
    @ResponseBody
    public Map<String, Object> getAll2Ajax(String opNo, String opName, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        String parm = "opNo=" + opNo + ";opName=" + opName;
        try {
            EntityUtil entityUtil = new EntityUtil();
            dataMap.put("data", entityUtil.prodAutoMenu(sysUser, ajaxData, query, parm, null));
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCustomSorts(ajaxData);
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
            // this.getRoleConditions(sysUser,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = sysUserFeign.findByPage(ipage);
            boolean noChoose = true;               //记录条件查询的查询条件是否存在
            noChoose = ajaxData.indexOf("roleNoTree") == -1;          //判断条件查询的查询条件是否是角色号
            List<SysUser> suList = (List<SysUser>) ipage.getResult();
            List<SysUser> suList1 = new ArrayList<SysUser>();
            CodeUtils cu = new CodeUtils();
            List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
            for (Iterator iterator = suList.iterator(); iterator.hasNext(); ) {
                SysUser su = (SysUser) JSONObject.toBean(JSONObject.fromObject(iterator.next()), SysUser.class);
                String role = su.getRoleNo();
                String[] roleArr = new String[0];
                if (role != null) {
                    if (role.indexOf("|") > -1) {
                        roleArr = role.split("\\|");
                    } else {
                        roleArr = new String[]{role};
                    }
                }
                boolean contain = false;          //记录当前记录中是否是我们需要的记录
                for (int i = 0; i < roleArr.length; i++) {          //判断当前记录中是否是我们要查找的记录
                    if (ajaxData.indexOf(roleArr[i]) != -1 && !"".equals(roleArr[i])) {
                        contain = true;
                    }
                }
                if (noChoose) {                         //如果没有筛选条件，则认为其不用筛选
                    contain = true;
                }
                if (noChoose || contain) {               //如果没有设置筛选条件或是满足当前记录满足筛选条件则找到对应的角色名称
                    for (int i = 0; i < roleArr.length; i++) {
                        for (ParmDic pd : pdList) {
                            if (roleArr[i].equals(pd.getOptCode())) {
                                roleArr[i] = pd.getOptName();
                            }
                        }
                    }
                }
                if (contain == false) {               //如果不满足筛选条件，则移除此条记录
                    iterator.remove();
                    continue;
                }
                role = "";
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < roleArr.length; i++) {
                    if (!roleArr[i].isEmpty()) {
                        list.add(roleArr[i]);
                    }
                }
                String[] newRoleArr = list.toArray(new String[1]);
                for (int i = 0; i < newRoleArr.length; i++) {
                    if (i == 0) {
                        role += newRoleArr[i];
                    } else {
                        role += "|" + newRoleArr[i];
                    }
                }
                su.setRoleNo(role);
                suList1.add(su);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, suList1, ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/findByPageAjax2")
    @ResponseBody
    public Map<String, Object> findByPageAjax2(String ajaxData, int pageNo, String tableId, String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCustomSorts(ajaxData);
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
            sysUser.setBrNo("100011");
            // this.getRoleConditions(sysUser,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = sysUserFeign.findByPage(ipage);
            boolean noChoose = true;               //记录条件查询的查询条件是否存在
            noChoose = ajaxData.indexOf("roleNoTree") == -1;          //判断条件查询的查询条件是否是角色号
            List<SysUser> suList = (List<SysUser>) ipage.getResult();
            List<SysUser> suList1 = new ArrayList<SysUser>();
            CodeUtils cu = new CodeUtils();
            List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
            for (Iterator iterator = suList.iterator(); iterator.hasNext(); ) {
                SysUser su = (SysUser) JSONObject.toBean(JSONObject.fromObject(iterator.next()), SysUser.class);
                if (!"100011".equals(su.getBrNo())) {
                    continue;
                }
                String role = su.getRoleNo();
                String[] roleArr = new String[0];
                if (role != null) {
                    if (role.indexOf("|") > -1) {
                        roleArr = role.split("\\|");
                    } else {
                        roleArr = new String[]{role};
                    }
                }
                boolean contain = false;          //记录当前记录中是否是我们需要的记录
                for (int i = 0; i < roleArr.length; i++) {          //判断当前记录中是否是我们要查找的记录
                    if (ajaxData.indexOf(roleArr[i]) != -1 && !"".equals(roleArr[i])) {
                        contain = true;
                    }
                }
                if (noChoose) {                         //如果没有筛选条件，则认为其不用筛选
                    contain = true;
                }
                if (noChoose || contain) {               //如果没有设置筛选条件或是满足当前记录满足筛选条件则找到对应的角色名称
                    for (int i = 0; i < roleArr.length; i++) {
                        for (ParmDic pd : pdList) {
                            if (roleArr[i].equals(pd.getOptCode())) {
                                roleArr[i] = pd.getOptName();
                            }
                        }
                    }
                }
                if (contain == false) {               //如果不满足筛选条件，则移除此条记录
                    iterator.remove();
                    continue;
                }
                role = "";
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < roleArr.length; i++) {
                    if (!roleArr[i].isEmpty()) {
                        list.add(roleArr[i]);
                    }
                }
                String[] newRoleArr = list.toArray(new String[1]);
                for (int i = 0; i < newRoleArr.length; i++) {
                    if (i == 0) {
                        role += newRoleArr[i];
                    } else {
                        role += "|" + newRoleArr[i];
                    }
                }
                su.setRoleNo(role);
                suList1.add(su);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, suList1, ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 选择组件列表数据查询
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/findByPageForSelectAjax")
    @ResponseBody
    public Map<String, Object> findByPageForSelectAjax(String ajaxData, int pageNo) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCustomSorts(ajaxData);
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
//			 this.getRoleConditions(sysUser,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = sysUserFeign.findByPage(ipage);
            boolean noChoose = true;               //记录条件查询的查询条件是否存在
            noChoose = ajaxData.indexOf("roleNoTree") == -1;          //判断条件查询的查询条件是否是角色号
            List<SysUser> suList = (List<SysUser>) ipage.getResult();
            CodeUtils cu = new CodeUtils();
            List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
            for (Iterator iterator = suList.iterator(); iterator.hasNext(); ) {
                SysUser su = (SysUser) JSONObject.toBean(JSONObject.fromObject(iterator.next()), SysUser.class);
                String role = su.getRoleNo();
                String[] roleArr = new String[0];
                if (role != null) {
                    if (role.indexOf("|") > -1) {
                        roleArr = role.split("\\|");
                    } else {
                        roleArr = new String[]{role};
                    }
                }
                boolean contain = false;          //记录当前记录中是否是我们需要的记录
                for (int i = 0; i < roleArr.length; i++) {          //判断当前记录中是否是我们要查找的记录
                    if (ajaxData.indexOf(roleArr[i]) != -1 && !"".equals(roleArr[i])) {
                        contain = true;
                    }
                }
                if (noChoose) {                         //如果没有筛选条件，则认为其不用筛选
                    contain = true;
                }
                if (noChoose || contain) {               //如果没有设置筛选条件或是满足当前记录满足筛选条件则找到对应的角色名称
                    for (int i = 0; i < roleArr.length; i++) {
                        for (ParmDic pd : pdList) {
                            if (roleArr[i].equals(pd.getOptCode())) {
                                roleArr[i] = pd.getOptName();
                            }
                        }
                    }
                }
                if (contain == false) {               //如果不满足筛选条件，则移除此条记录
                    iterator.remove();
                    continue;
                }
                role = "";
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < roleArr.length; i++) {
                    if (!roleArr[i].isEmpty()) {
                        list.add(roleArr[i]);
                    }
                }
                String[] newRoleArr = list.toArray(new String[1]);
                for (int i = 0; i < newRoleArr.length; i++) {
                    if (i == 0) {
                        role += newRoleArr[i];
                    } else {
                        role += "|" + newRoleArr[i];
                    }
                }
                su.setRoleNo(role);
            }
//			JsonTableUtil jtu = new JsonTableUtil();
//			String tableHtml = jtu.getJsonStr(tableId, tableType,(List) ipage.getResult(), ipage, true);
//			ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /***
     * 选择组件辅办列表数据查询(本部门及其以下的同角色操作人员)
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/findSameDownBrAndRoleByPageAjax")
    @ResponseBody
    public Map<String, Object> findSameDownBrAndRoleByPageAjax(String ajaxData, int pageNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCustomSorts(ajaxData);
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
            // this.getRoleConditions(sysUser,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            sysUser.setOpNo(User.getRegNo(request));
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = sysUserFeign.findSameDownBrAndRoleByPage(ipage);
            boolean noChoose = true;               //记录条件查询的查询条件是否存在
            noChoose = ajaxData.indexOf("roleNoTree") == -1;          //判断条件查询的查询条件是否是角色号
            List<SysUser> suList = (List<SysUser>) ipage.getResult();
            CodeUtils cu = new CodeUtils();
            List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
            for (Iterator iterator = suList.iterator(); iterator.hasNext(); ) {
                SysUser su = (SysUser) JSONObject.toBean(JSONObject.fromObject(iterator.next()), SysUser.class);
                String role = su.getRoleNo();
                String[] roleArr = new String[0];
                if (role != null) {
                    if (role.indexOf("|") > -1) {
                        roleArr = role.split("\\|");
                    } else {
                        roleArr = new String[]{role};
                    }
                }
                boolean contain = false;          //记录当前记录中是否是我们需要的记录
                for (int i = 0; i < roleArr.length; i++) {          //判断当前记录中是否是我们要查找的记录
                    if (ajaxData.indexOf(roleArr[i]) != -1 && !"".equals(roleArr[i])) {
                        contain = true;
                    }
                }
                if (noChoose) {                         //如果没有筛选条件，则认为其不用筛选
                    contain = true;
                }
                if (noChoose || contain) {               //如果没有设置筛选条件或是满足当前记录满足筛选条件则找到对应的角色名称
                    for (int i = 0; i < roleArr.length; i++) {
                        for (ParmDic pd : pdList) {
                            if (roleArr[i].equals(pd.getOptCode())) {
                                roleArr[i] = pd.getOptName();
                            }
                        }
                    }
                }
                if (contain == false) {               //如果不满足筛选条件，则移除此条记录
                    iterator.remove();
                    continue;
                }
                role = "";
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < roleArr.length; i++) {
                    if (!roleArr[i].isEmpty()) {
                        list.add(roleArr[i]);
                    }
                }
                String[] newRoleArr = list.toArray(new String[1]);
                for (int i = 0; i < newRoleArr.length; i++) {
                    if (i == 0) {
                        role += newRoleArr[i];
                    } else {
                        role += "|" + newRoleArr[i];
                    }
                }
                su.setRoleNo(role);
            }
//			JsonTableUtil jtu = new JsonTableUtil();
//			String tableHtml = jtu.getJsonStr(tableId, tableType,(List) ipage.getResult(), ipage, true);
//			ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 根据部门编号获得部门下的员工
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-2-21 下午4:55:40
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/getAllUserByBrNoByPageAjax")
    @ResponseBody
    public Map<String, Object> getAllUserByBrNoByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId, String tableType, String brNo, String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            //当前操作员，用于判断删除、修改密码等权限。
            SysUser curOpUser = new SysUser();
            curOpUser.setOpNo(User.getRegNo(request));
            curOpUser = sysUserFeign.getById(curOpUser);

            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
            //如果是只能搜索不传递部门编号，在所有用户中查询
            JSONArray jsonarray = JSONArray.fromObject(JSONArray.fromObject(ajaxData).get(0));
            JSONObject jsonObject = JSONObject.fromObject(jsonarray.get(0));
            if (jsonObject.get("customQuery") == null || "".equals(jsonObject.get("customQuery").toString())) {
                sysUser.setBrNo(brNo);
            }
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            sysUser.setOpNoType(opNoType);
            ipage = sysUserFeign.getAllUserByBrNoByPage(ipage);
            boolean noChoose = true;               //记录条件查询的查询条件是否存在
            noChoose = ajaxData.indexOf("roleNoTree") == -1;          //判断条件查询的查询条件是否是角色号
            List<SysUser> suList = (List<SysUser>) ipage.getResult();
            //List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
            SysRole sysRole = new SysRole();
            sysRole.setOpNoType(opNoType);
            List<SysRole> roleList = sysRoleFeign.getAllList(sysRole);
            List<SysUser> suList1 = new ArrayList<SysUser>();
            for (Iterator iterator = suList.iterator(); iterator.hasNext(); ) {
                SysUser su = (SysUser) JSONObject.toBean(JSONObject.fromObject(iterator.next()), SysUser.class);
                String role = su.getRoleNo();
                String[] roleArr = new String[0];
                if (role != null) {
                    if (role.indexOf("|") > -1) {
                        roleArr = role.split("\\|");
                    } else {
                        roleArr = new String[]{role};
                    }
                }
                boolean contain = false;          //记录当前记录中是否是我们需要的记录
                for (int i = 0; i < roleArr.length; i++) {          //判断当前记录中是否是我们要查找的记录
                    if (ajaxData.indexOf(roleArr[i]) != -1 && !"".equals(roleArr[i])) {
                        contain = true;
                    }
                }
                if (noChoose) {                         //如果没有筛选条件，则认为其不用筛选
                    contain = true;
                }
                if (noChoose || contain) {               //如果没有设置筛选条件或是满足当前记录满足筛选条件则找到对应的角色名称
                    for (int i = 0; i < roleArr.length; i++) {
                        for (SysRole bean : roleList) {
                            if (roleArr[i].equals(bean.getRoleNo())) {
                                roleArr[i] = bean.getRoleName();
                            }
                        }
                    }
                }
                if (contain == false) {               //如果不满足筛选条件，则移除此条记录
                    iterator.remove();
                    continue;
                }
                role = "";
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < roleArr.length; i++) {
                    if (!roleArr[i].isEmpty()) {
                        list.add(roleArr[i]);
                    }
                }
                String[] newRoleArr = list.toArray(new String[1]);
                for (int i = 0; i < newRoleArr.length; i++) {
                    if (i == 0) {
                        role += newRoleArr[i];
                    } else {
                        role += "|" + newRoleArr[i];
                    }
                }
                su.setRoleName(role);

                /* 用于标识系统内置账户，可以进行重置密码。
                 * 如果su是内置账户，那么当前操作员必须也是内置账户，才可以进行重置。
                 */
                su.setResetPWDFlag(BizPubParm.YES_NO_Y);
                if (BizPubParm.YES_NO_Y.equals(su.getBuildInFlag()) && !BizPubParm.YES_NO_Y.equals(curOpUser.getBuildInFlag())) {
                    su.setResetPWDFlag(BizPubParm.YES_NO_N);
                }
                suList1.add(su);
            }
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, suList1, ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            /**
             * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
             * dataMap.put("tableData",tableHtml);
             */
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getUserListAjax")
    @ResponseBody
    public Map<String, Object> getUserListAjax(String ajaxData, String limit, String query) throws Exception {
//		ActionContext.initialize(request,response);
        Gson gson = new Gson();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        String parm = "opSts=1";
        try {
            JSONArray jSONArray = entityUtilFeign.prodAutoMenu(gson.toJson(sysUser), ajaxData,
                    query, parm, null, SysUser.class);
            dataMap.put("data", jSONArray);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/getAllUserAjax")
    @ResponseBody
    public Map<String, Object> getAllUserAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            SysUser sysUser = new SysUser();
            if (!"".equals(ajaxData) && ajaxData != null) {
                formsys5002 = formService.getFormData("sys5002");
                getFormValue(formsys5002, getMapByJson(ajaxData));
                setObjValue(formsys5002, sysUser);
            }
            sysUser.setOpNoType(BizPubParm.OP_NO_TYPE1);
            List<SysUser> suList = sysUserFeign.getAllUserList(sysUser);
            JSONArray userArray = JSONArray.fromObject(suList);
            for (int i = 0; i < userArray.size(); i++) {
                userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
                userArray.getJSONObject(i).put("text", userArray.getJSONObject(i).getString("opName"));
            }
            dataMap.put("flag", "success");
            dataMap.put("json", userArray);
            dataMap.put("suList", suList);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述：选择组件Ztree 查询操作员方法
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-12-8 下午3:04:25
     */
    @RequestMapping(value = "/getSysUserListAjax")
    @ResponseBody
    public Map<String, Object> getSysUserListAjax(String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        SysOrg sysOrg = new SysOrg();
        sysOrg.setOpNoType(opNoType);
        JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAllOrg(sysOrg));
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setOpSts("1");
        sysUser.setOpNoType(opNoType);
        JSONArray userArray = JSONArray.fromObject(sysUserFeign.getAllUserList(sysUser));
        for (int i = 0; i < orgArray.size(); i++) {
            orgArray.getJSONObject(i).put("id", orgArray.getJSONObject(i).getString("brNo"));
            orgArray.getJSONObject(i).put("name", orgArray.getJSONObject(i).getString("brName"));
            orgArray.getJSONObject(i).put("pId", "0");
            orgArray.getJSONObject(i).put("open", false);
            orgArray.getJSONObject(i).put("isParent", true);
        }
        for (int i = 0; i < userArray.size(); i++) {
            userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
            userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
            userArray.getJSONObject(i).put("pId", userArray.getJSONObject(i).getString("brNo"));
            orgArray.add(userArray.getJSONObject(i));
        }
        dataMap.put("items", orgArray.toString());
        return dataMap;
    }

    /**
     * AJAX新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData, String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            formsys5002 = formService.getFormData("sys5002");
            getFormValue(formsys5002, getMapByJson(ajaxData));
            if (this.validateFormData(formsys5002)) {
                SysUser sysUser = new SysUser();
                setObjValue(formsys5002, sysUser);
                //对用户密码进行加密
                sysUser.setPasswordhash(Encryption.md5(sysUser.getPasswordhash()));
                Map<String, Object> checkMap = sysUserFeign.checkOnlyNo(sysUser, "add");
                Boolean checkFlag = (Boolean) checkMap.get("checkFlag");
                if (!checkFlag) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage((String) checkMap.get("msg")));
                } else {
                    sysUser.setOpNoType(opNoType);
                    sysUser.setLoginUser(sysUser.getOpNo());
                    sysUserFeign.insert(sysUser);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
        }
        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        formsys5002 = formService.getFormData("sys5002");
        getFormValue(formsys5002, getMapByJson(ajaxData));
        SysUser sysUserJsp = new SysUser();
        setObjValue(formsys5002, sysUserJsp);
        SysUser sysUser = sysUserFeign.getById(sysUserJsp);
        if (sysUser != null) {
            try {
                sysUser = (SysUser) EntityUtil.reflectionSetVal(sysUser,
                        sysUserJsp, getMapByJson(ajaxData));
                sysUserFeign.update(sysUser);
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
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            formsys5005 = formService.getFormData("sys5005");
            getFormValue(formsys5005, getMapByJson(ajaxData));
            if (this.validateFormData(formsys5005)) {
                sysUser = new SysUser();
                setObjValue(formsys5005, sysUser);
                //如果更改的用户信息时更改了用户密码
                SysUser user = new SysUser();
                user.setOpNo(sysUser.getOpNo());
                String pwd = sysUserFeign.getById(user).getPasswordhash();
                if (!sysUser.getPasswordhash().equals(pwd)) {
                    sysUser.setPasswordhash(Encryption.md5(sysUser.getPasswordhash()));
                }
                Map<String, Object> checkMap = sysUserFeign.checkOnlyNo(sysUser, "edit");
                Boolean checkFlag = (Boolean) checkMap.get("checkFlag");
                if (checkFlag!= null && !checkFlag) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage((String) checkMap.get("msg")));
                } else {
                    sysUserFeign.updateStsNew(sysUser);
                    // 修改用户信息时有可能导致用户部门发生变化，刷新缓存。
                    refreshParmKey();
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
                }
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
        }
        return dataMap;
    }

    /**
     * 刷新缓存
     *
     * @return
     * @throws Exception
     */
    public boolean refreshParmKey() throws Exception {
        busiCacheFeign.refreshAllParmDic();
        return true;
    }

    /**
     * 用户修改密码跳转
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyPasswordInput")
    public String modifyPasswordInput(Model model) throws Exception {
        ActionContext.initialize(request, response);
        formsys5001 = formService.getFormData("sysModifyPwd");
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(User.getRegNo(request));
        sysUser = sysUserFeign.getById(sysUser);
        getObjValue(formsys5005, sysUser);
        this.changeFormProperty(formsys5001, "opName", "initValue", sysUser.getOpName());
        model.addAttribute("formsys5001", formsys5001);
        model.addAttribute("query", "");
        return "component/sys/SysUser_ModifyPwd";
    }

    /**
     * ajax更改状态
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateStsAjax")
    @ResponseBody
    public Map<String, Object> updateStsAjax(String homePage) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String opNo = User.getRegNo(request);
            SysUser sysUser = new SysUser();
            sysUser.setOpNo(opNo);
            sysUser = sysUserFeign.getById(sysUser);
            if ("0".equals(sysUser.getOpSts())) {

            } else {

            }
            sysUser.setHomePage(homePage);
            sysUserFeign.update(sysUser);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 密码重置
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetPasswordAjax")
    @ResponseBody
    public Map<String, Object> resetPasswordAjax() throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        boolean flag = false;
        for (String orgNo : User.getRoleNo(request)) {
            List<PmsEntranceRole> pmsEntranceRoleList = pmsInterfaceFeign.findByRoleNo(orgNo);
            if (pmsEntranceRoleList != null) {
                for (PmsEntranceRole pmsEntranceRole : pmsEntranceRoleList) {
                    // 10037表示入口设置权限编号
                    if ("10037".equals(pmsEntranceRole.getPmsNo())) {
                        flag = true;
                    }
                }
            }
        }
        if (!flag) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
            return dataMap;
        }
        dataMap = new HashMap<String, Object>();
        try {
            String opNo = request.getParameter("opNo");
            sysUserFeign.updatePwdDefault(opNo);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_SYSUSER_PASSWORD.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
            throw e;
        }

        return dataMap;
    }

    /**
     * 滑块ajax更改状态
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateStsNewAjax")
    @ResponseBody
    public Map<String, Object> updateStsNewAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            //Map map = getMapByJson(ajaxData);
            JSONObject jobj = JSONObject.fromObject(ajaxData);
            String opNo = (String) jobj.get("opNo");
            if (StringUtil.isEmpty(opNo)) {
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
                return dataMap;
            }
            sysUser.setOpNo(opNo);
            String opSts = (String) jobj.get("opSts");
            sysUser = sysUserFeign.getById(sysUser);
            if (StringUtil.isNotEmpty(opSts)) {

                // 内置账户不允许禁用。
                if (BizPubParm.YES_NO_Y.equals(sysUser.getBuildInFlag()) && BizPubParm.YES_NO_N.equals(opSts)) {
                    dataMap.put("flag", "error");
                    dataMap.put("msg", MessageEnum.FAILED_DELETE_USER.getMessage());
                    return dataMap;
                }
                sysUser.setOpSts(opSts);
                sysUserFeign.updateStsNew(sysUser);
            }
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }

    @RequestMapping(value = "/validatePassword")
    @ResponseBody
    public Map<String, Object> validatePassword(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {
            SysUser sysUser = new SysUser();
            sysUser.setOpNo(User.getRegNo(request));
            sysUser = sysUserFeign.getById(sysUser);
            ajaxData = Encryption.md5(ajaxData);
            if (ajaxData.equals(sysUser.getPasswordhash())) {
                dataMap.put("flag", true);
                dataMap.put("sysUser", sysUser);
            } else {
                dataMap.put("flag", false);
                dataMap.put("msg", "密码错误！");
            }
        } catch (Exception e) {
            dataMap.put("flag", false);
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
        }
        return dataMap;
    }

    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public Map<String, Object> updatePassword(String ajaxData, String opName) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            SysUser sysUser = new SysUser();
            sysUser.setOpNo(User.getRegNo(request));
            sysUser = sysUserFeign.getById(sysUser);
            sysUser.setOpName(opName);
            ajaxData = Encryption.md5(ajaxData);
            sysUser.setPasswordhash(ajaxData);
            sysUserFeign.update(sysUser);
            dataMap.put("flag", true);
            dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
        } catch (Exception e) {
            dataMap.put("flag", false);
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
        }
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        formsys5002 = formService.getFormData("sys5002");
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        sysUser = sysUserFeign.getById(sysUser);
        CodeUtils cu = new CodeUtils();
        Map<String, String> map = cu.getMapByKeyName("DUTIES");
        sysUser.setJob(map.get(sysUser.getJob()));
        dataMap.put("ifUploadHead", sysUser.getIfUploadHead());
        dataMap.put("headImg", sysUser.getHeadImg());
        dataMap.put("brNo", sysUser.getBrNo());
        dataMap.put("brName", sysUser.getBrName());

        getObjValue(formsys5002, sysUser, formData);
        int sum = 0;//任务数
        SysTaskInfo sysTaskInfo = new SysTaskInfo();
        sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
        sysTaskInfo.setPasAware("0");
//		sysTaskInfo.setOptType("0");//消息类型 0-纯消息
        List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMaxTypeCount(sysTaskInfo);
        for (SysTaskInfo sti : stiList) {
            //只统计审批任务、催收任务、检查任务数量
            if (PasConstant.PAS_BIG_TYPE_APPROVAL.equals(sti.getPasMaxNo()) || PasConstant.PAS_BIG_TYPE_REC.equals(sti.getPasMaxNo())
                    || PasConstant.PAS_BIG_TYPE_LLC.equals(sti.getPasMaxNo())) {
                sum = sum + Integer.parseInt(sti.getPasContent());
            }
        }
        String count = String.valueOf(sum);
        dataMap.put("flag", "success");
        dataMap.put("count", count);
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSysUserByIdAjax")
    @ResponseBody
    public Map<String, Object> getSysUserByIdAjax(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        sysUser = sysUserFeign.getById(sysUser);
//		CodeUtils cu = new CodeUtils();
//		Map<String,String> map = cu.getMapByKeyName("DUTIES");
//		sysUser.setJob(map.get(sysUser.getJob()));
        if (sysUser != null) {
            dataMap.put("flag", "success");
            dataMap.put("sysUser", sysUser);
        } else {
            dataMap.put("flag", "error");
        }
        return dataMap;
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getNewsWarningCntAjax")
    @ResponseBody
    public Map<String, Object> getNewsWarningCntAjax(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        int sum = 0;//任务数
        SysTaskInfo sysTaskInfo = new SysTaskInfo();
        sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
        sysTaskInfo.setUserNo(opNo);
        List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMaxTypeCount(sysTaskInfo);
        for (SysTaskInfo sti : stiList) {
            //只统计审批任务、催收任务、检查任务数量
            if (PasConstant.PAS_BIG_TYPE_APPROVAL.equals(sti.getPasMaxNo()) || PasConstant.PAS_BIG_TYPE_REC.equals(sti.getPasMaxNo())
                    || PasConstant.PAS_BIG_TYPE_LLC.equals(sti.getPasMaxNo())) {
                sum = sum + Integer.parseInt(sti.getPasContent());
            }
        }
        String count = String.valueOf(sum);
        dataMap.put("flag", "success");
        dataMap.put("count", count);
        return dataMap;
    }

    /**
     * 方法描述： 获取个人中心数据
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-2-14 上午11:48:53
     */
    @RequestMapping(value = "/getPersonalCenterInfo")
    public String getPersonalCenterInfo(String opNo, Model model) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //获取个人基本信息
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        sysUser = sysUserFeign.getById(sysUser);
        CodeUtils cu = new CodeUtils();
        Map<String, String> map = cu.getMapByKeyName("DUTIES");
        sysUser.setJob(map.get(sysUser.getJob()));
        SysOrg sysOrg = new SysOrg();
        sysOrg.setBrNo(sysUser.getBrNo());
        sysOrg = sysOrgFeign.getById(sysOrg);
        sysUser.setBrNo(sysOrg.getBrName());
        //业务经理相关统计信息
        dataMap = pactInterfaceFeign.getMfBusPactByCusmngNo(opNo);
        //费用口径
        dataMap.putAll(pactInterfaceFeign.getMfBusPactByCusmngNoForFee(opNo));
        //根据操作员获取数据(客户量、办理中的业务、逾期的情况)
        Map<String, Object> mapCusInformation = sysUserFeign.getCusInformation(opNo);
        dataMap.putAll(mapCusInformation);
        //请假事宜、借款报销
        dataMap.putAll(oaInterfaceFeign.getPersonCenterCount(opNo));
        String[] roleNoStr = (String[]) request.getSession().getAttribute("roleNo");
        MfDeskMsgItem mfDeskMsgItem = new MfDeskMsgItem();
        mfDeskMsgItem.setOpNo(opNo);
        mfDeskMsgItem.setRoleNoStr(roleNoStr);
        List<MfDeskMsgItem> mfDeskMsgItemList = deskInterfaceFeign.getMsgList(mfDeskMsgItem);
        //是否启用现金贷登录首页
        model.addAttribute("mfDeskMsgItemList", mfDeskMsgItemList);
        model.addAttribute("sysUser", sysUser);
        String isCashLoginHome = new CodeUtils().getSingleValByKey("IS_CASH_LOGIN_HOME");//是否启用现金贷登录首页  1:启用; 0:不启用
        if ("1".equals(isCashLoginHome)) {//启用 -- 现金贷登录首页
            CodeUtils codeUtils = new CodeUtils();
            for (int i = 0; i < roleNoStr.length; i++) {
                if (roleNoStr[i] != null && !"".equals(roleNoStr[i])) {
                    String pmBizNo = (String) codeUtils.getObjCache4ParmDic(roleNoStr[i]);
                    if (pmBizNo != null && !"".equals(pmBizNo)) {
                        String[] pmBizNoArr = pmBizNo.split(",");
                        for (String pmsBizNo : pmBizNoArr) {
                            if ("cashpageauth".equals(pmsBizNo)) {//有首页数据权限
                                //今日数据统计信息
                                SysUser user = new SysUser();
                                user.setOpNo(User.getRegNo(request));
                                user = sysUserFeign.getById(user);
                                Map<String, Object> paraMap = new HashMap<String, Object>();
                                if ("1".equals(user.getFunRoleType())) {//数据权限类型1登记人2本机构3本机构及其向下4客户经理5上级机构及其向下9	查看全部
                                    paraMap.put("brNo", User.getRegNo(request));
                                } else if ("2".equals(user.getFunRoleType())) {
                                    paraMap.put("brNo", User.getOrgNo(request));
                                }else {
                                }
                                paraMap.put("nowDate", DateUtil.getDate());
                                dataMap.putAll(pactInterfaceFeign.getTodayData(paraMap));
                                dataMap.put("isAuth", "1");
                                model.addAttribute("dataMap", dataMap);
                                return "/component/sys/SysUser_UserInfoForCash";
                            }
                        }
                    }
                }
            }
            dataMap.put("isAuth", "0");
            model.addAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("reportURL"));
            model.addAttribute("dataMap", dataMap);
            return "/component/sys/SysUser_UserInfoForCash";
        } else {//不启用-- 原有登录首页
            model.addAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("reportURL"));
            model.addAttribute("dataMap", dataMap);
            return "/component/sys/SysUser_UserInfo";
        }
    }

    /**
     * 根据操作员获取对应审批角色中的对应个数
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCountByOpNoAjax")
    @ResponseBody
    public Map<String, Object> getCountByOpNoAjax(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        try {
            int count = sysUserFeign.getCountByOpNo(opNo);
            dataMap.put("flag", "success");
            dataMap.put("count", count);
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
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
    public Map<String, Object> deleteAjax(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        try {
            sysUserFeign.delete(sysUser);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 获得短消息条数
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getNewsCnt")
    @ResponseBody
    public Map<String, Object> getNewsCnt() throws Exception {
        ActionContext.initialize(request, response);
        int sum = 0;//任务数
        SysTaskInfo sysTaskInfo = new SysTaskInfo();
        sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
        List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMaxTypeCount(sysTaskInfo);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("approvalCount", "0");
        dataMap.put("recCount", "0");
        dataMap.put("llcCount", "0");
        dataMap.put("noticeCount", "0");
        for (SysTaskInfo sti : stiList) {
            if (PasConstant.PAS_BIG_TYPE_APPROVAL.equals(sti.getPasMaxNo())) {
                dataMap.put("approvalCount", sti.getPasContent());
            } else if (PasConstant.PAS_BIG_TYPE_REC.equals(sti.getPasMaxNo())) {
                dataMap.put("recCount", sti.getPasContent());
            } else if (PasConstant.PAS_BIG_TYPE_LLC.equals(sti.getPasMaxNo())) {
                dataMap.put("llcCount", sti.getPasContent());
            } else if (PasConstant.PAS_BIG_TYPE_OANOTICE.equals(sti.getPasMaxNo())) {
                dataMap.put("noticeCount", sti.getPasContent());
            }else {
            }
            sum = sum + Integer.parseInt(sti.getPasContent());
        }
        String count = String.valueOf(sum);
//		dataMap = new HashMap<String, Object>();
        dataMap.put("newsCnt", count);
        return dataMap;
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String brNo, String opNoType) throws Exception {
        ActionContext.initialize(request,
                response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        formsys5002 = formService.getFormData("sys5002");
        dataMap = sysUserFeign.initSysUser(brNo, opNoType);
        Gson gson = new Gson();
        SysUser sysUser = gson.fromJson(gson.toJson(dataMap.get("sysUser")), SysUser.class);
        getObjValue(formsys5002, sysUser);
        String ajaxData = gson.toJson(dataMap.get("json"));
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("opNoType", opNoType);
        model.addAttribute("formsys5002", formsys5002);
        model.addAttribute("query", "");
        return "/component/sys/SysUser_Insert";
    }

    /***
     * 新增
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        ActionContext.initialize(request, response);
        formsys5002 = formService.getFormData("sys5002");
        getFormValue(formsys5002);
        SysUser sysUser = new SysUser();
        setObjValue(formsys5002, sysUser);
        sysUserFeign.insert(sysUser);
        getObjValue(formsys5002, sysUser);
        this.addActionMessage(model, "保存成功");
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("sysUser", sysUser));
        List<SysUser> sysUserList = (List<SysUser>) sysUserFeign.findByPage(ipage).getResult();
        model.addAttribute("sysUserList", sysUserList);
        model.addAttribute("formsys5002", formsys5002);
        return "/component/sys/SysUser_Detail";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String opNo, String opNoType) throws Exception {
//		ActionContext.initialize(request,response);
        boolean flag = false;
        for (String orgNo : User.getRoleNo(request)) {
            List<PmsEntranceRole> pmsEntranceRoleList = pmsInterfaceFeign.findByRoleNo(orgNo);
            if (pmsEntranceRoleList != null) {
                for (PmsEntranceRole pmsEntranceRole : pmsEntranceRoleList) {
                    // 10037表示入口设置权限编号
                    if ("10037".equals(pmsEntranceRole.getPmsNo())) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (!flag) {
            addActionMessage(model, "您没有此操作权限!");
            return "/component/pub/noAuthorityException";
        }
        formsys5005 = formService.getFormData("sys5005");
        getFormValue(formsys5005);
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        sysUser = sysUserFeign.getById(sysUser);
        if (StringUtil.isNotEmpty(sysUser.getBrNo()) && ("".equals(sysUser.getBrName()) || sysUser.getBrName() == null)) {
            SysOrg sysOrg = new SysOrg();
            sysOrg.setBrNo(sysUser.getBrNo());
            sysOrg = sysOrgFeign.getById(sysOrg);
            if (sysOrg != null) {
                sysUser.setBrName(sysOrg.getBrName());
            }
        }
        getObjValue(formsys5005, sysUser);
        JSONObject json = new JSONObject();
        SysRole sysRole = new SysRole();
        sysRole.setOpNoType(opNoType);
        List<SysRole> sysOrgList = sysRoleFeign.getAllList(sysRole);

        JSONArray roleArray = JSONArray.fromObject(sysOrgList);
        for (int i = 0; i < roleArray.size(); i++) {
            roleArray.getJSONObject(i).put("id",
                    roleArray.getJSONObject(i).getString("roleNo"));
            roleArray.getJSONObject(i).put("name",
                    roleArray.getJSONObject(i).getString("roleName"));
        }
        json.put("role", roleArray);
        SysOrg sysOrg = new SysOrg();
        sysOrg.setOpNoType(opNoType);
        JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
        for (int i = 0; i < orgArray.size(); i++) {
            orgArray.getJSONObject(i).remove("iconSkin");
        }
        json.put("org", orgArray);
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("opNoType", opNoType);
        model.addAttribute("formsys5005", formsys5005);
        model.addAttribute("query", "");
        return "/component/sys/SysUser_Detail";
    }

    //上传头像-
    @RequestMapping(value = "/uploadHeadImg")
    public String uploadHeadImg(String opNo, Model model) throws Exception {
        ActionContext.initialize(request,
                response);
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        sysUser = sysUserFeign.getById(sysUser);
        String headImg = sysUser.getHeadImg();
        String ifUploadHead = sysUser.getIfUploadHead();
        formsysuploadhead0001 = formService.getFormData("sysuploadhead0001");
        model.addAttribute("headImg", headImg);
        model.addAttribute("query", "");
        model.addAttribute("ifUploadHead", ifUploadHead);
        model.addAttribute("opNo", opNo);
        model.addAttribute("formsysuploadhead0001", formsysuploadhead0001);
        return "/component/sys/SysUser_UploadImg";
    }

    @RequestMapping(value = "/submitUploadHeadImg")
    @ResponseBody
    public Map<String, Object> submitUploadHeadImg(String opNo, String headImg) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        sysUser = sysUserFeign.getById(sysUser);
        sysUser.setHeadImg(headImg);
        sysUser.setIfUploadHead("1");
        try {
            sysUserFeign.update(sysUser);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }

    //方法描述： 获得员工是否上传过头像
    @RequestMapping(value = "/getIfUploadHeadImg")
    @ResponseBody
    public Map<String, Object> getIfUploadHeadImg(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setOpNo(opNo);
        try {
            sysUser = sysUserFeign.getById(sysUser);
            dataMap.put("flag", sysUser.getIfUploadHead());
            dataMap.put("headImg", sysUser.getHeadImg());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 启用停用
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateSts")
    public String updateSts(String opNo) throws Exception {
        ActionContext.initialize(request, response);
        try {
            SysUser sysUser = new SysUser();
            sysUser.setOpNo(opNo);
            sysUser = sysUserFeign.getById(sysUser);
            if ("1".equalsIgnoreCase(sysUser.getOpSts())) {
                sysUser.setOpSts("2");
            } else {
                sysUser.setOpSts("1");
            }
            sysUserFeign.updateOpSts(sysUser);
            this.setMessage("修改状态成功。");
        } catch (Exception ex) {
            this.setMessage("修改状态失败!");
        }
        return "/component/sys/SysUser_List";
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateInsert() throws Exception {
        ActionContext.initialize(request,
                response);
        formsys5002 = formService.getFormData("sys5002");
        getFormValue(formsys5002);
        boolean validateFlag = this.validateFormData(formsys5002);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void validateUpdate() throws Exception {
        ActionContext.initialize(request,
                response);
        formsys5005 = formService.getFormData("sys5005");
        getFormValue(formsys5005);
        boolean validateFlag = this.validateFormData(formsys5005);
    }

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListForAppPage")
    public String getListForAppPage(Model model, String opNoType) throws Exception {
        ActionContext.initialize(request, response);
        JSONObject json = new JSONObject();
        CodeUtils cu = new CodeUtils();
        JSONArray sexArray = JSONArray.fromObject(cu.getCacheByKeyName("SEX"));
        for (int i = 0; i < sexArray.size(); i++) {
            sexArray.getJSONObject(i).put("id",
                    sexArray.getJSONObject(i).getString("optCode"));
            sexArray.getJSONObject(i).put("text",
                    sexArray.getJSONObject(i).getString("optName"));
        }
        json.put("sex", sexArray);
        JSONArray userArray = JSONArray.fromObject(cu
                .getCacheByKeyName("USER_STS"));
        for (int i = 0; i < userArray.size(); i++) {
            userArray.getJSONObject(i).put("id",
                    userArray.getJSONObject(i).getString("optCode"));
            userArray.getJSONObject(i).put("text",
                    userArray.getJSONObject(i).getString("optName"));
        }
        json.put("userSts", userArray);
        SysRole sysRole = new SysRole();
        sysRole.setOpNoType(opNoType);
        JSONArray roleArray = JSONArray.fromObject(sysRoleFeign.getAllList(sysRole));
        for (int i = 0; i < roleArray.size(); i++) {
            roleArray.getJSONObject(i).put("id",
                    roleArray.getJSONObject(i).getString("roleNo"));
            roleArray.getJSONObject(i).put("text",
                    roleArray.getJSONObject(i).getString("roleName"));
        }
        json.put("role", roleArray);
        SysOrg sysOrg = new SysOrg();
        sysOrg.setOpNoType(opNoType);
        JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAllOrg(sysOrg));
        for (int i = 0; i < orgArray.size(); i++) {
            orgArray.getJSONObject(i).put("id",
                    orgArray.getJSONObject(i).getString("brNo"));
            orgArray.getJSONObject(i).put("text",
                    orgArray.getJSONObject(i).getString("brName"));
        }
        json.put("org", orgArray);
        JSONArray channelArray = JSONArray.fromObject(cu.getCacheByKeyName("CHANNEL_TYPE"));
        json.put("channel", channelArray);
        this.getHttpRequest().setAttribute("dicList", channelArray.toString());
        String ajaxData = json.toString();
        model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("opNoType", opNoType);
        model.addAttribute("query", "");
        return "/component/sys/SysUserForApp_List";
    }

    /**
     * 方法描述： 验证系统当前登录操作员对当前数据的可操作权限
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2017-12-14 下午2:37:11
     */
    @RequestMapping(value = "/checkOperableAjax")
    @ResponseBody
    public Map<String, Object> checkOperableAjax(String opNo, String dataOpNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            dataMap = sysUserFeign.checkOperable(opNo, dataOpNo, User.getRegNo(request));
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", " 验证系统当前登录操作员对当前数据的可操作权限失败");
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 根据工作类型获取本部门相关人员
     *
     * @return
     * @throws Exception
     * @author LiJuntao
     * @date 2018-6-2 下午3:49:11
     */
    @RequestMapping(value = "/getSysUserByJobAjax")
    @ResponseBody
    public Map<String, Object> getSysUserByJobAjax(int pageNo, String jobType) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ActionContext.initialize(request, response);
        Ipage ipage = this.getIpage();
        ipage.setPageNo(pageNo);// 异步传页面翻页参数
        SysUser sysUser = new SysUser();
        sysUser.setJob(jobType);  //设置工作类型
        sysUser.setBrNo(User.getOrgNo(request));  //获取操作员所在部门
        sysUser.setOpSts(BizPubParm.YES_NO_Y);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sysUser", sysUser);
        ipage.setParams(paramMap);
        ipage = sysUserFeign.findByPage(ipage);
        dataMap.put("ipage", ipage);
        return dataMap;
    }

    /**
     * 方法描述： 选择组件Ztree 根据指定部门选择操作员方法
     *
     * @param brNo
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2018-3-5 下午6:36:18
     */
    @RequestMapping(value = "/getSysUserListByBrNosAjax")
    @ResponseBody
    public Map<String, Object> getSysUserListByBrNosAjax(String brNo) throws Exception {
        ActionContext.initialize(request, response);
        if (StringUtil.isNotEmpty(brNo)) {
            String[] brNoArr = brNo.split("\\|");
            if (brNoArr.length == 1) {
                brNo = sysOrgFeign.getAllChildren(brNo);
            }
        }
        JSONArray orgArray = JSONArray.fromObject(sysOrgFeign.getAOrgListByBrNos(brNo));
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JSONArray userArray = JSONArray.fromObject(sysUserFeign.getAllUserByBrNos(brNo));
        for (int i = 0; i < orgArray.size(); i++) {
            orgArray.getJSONObject(i).put("id", orgArray.getJSONObject(i).getString("brNo"));
            orgArray.getJSONObject(i).put("name", orgArray.getJSONObject(i).getString("brName"));
            orgArray.getJSONObject(i).put("pId", "0");
            orgArray.getJSONObject(i).put("open", false);
            orgArray.getJSONObject(i).put("isParent", true);
        }
        for (int i = 0; i < userArray.size(); i++) {
            userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
            userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
            userArray.getJSONObject(i).put("pId", userArray.getJSONObject(i).getString("brNo"));
            orgArray.add(userArray.getJSONObject(i));
        }
        dataMap.put("items", orgArray.toString());
        return dataMap;
    }


    //获取未入业务客户信息
    @RequestMapping(value = "/getCusMugajix")
    @ResponseBody
    public Map<String, Object> getCusMugajix(Ipage ipage, String ajaxData, Integer pageNo, String tableId, String tableType, String cusmugno) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        try {
            sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
            sysUser.setCustomSorts(ajaxData);
            sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
            sysUser.setOpNo(cusmugno);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("sysUser", sysUser));
            ipage = sysUserFeign.getCusMug(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }



    /**
     * 方法描述： 强制修改密码表单初始化
     *
     * @param
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-25 下午8:58:18
     */
    @RequestMapping(value = "/pwdUpdteInfoForm")
    @ResponseBody
    public Map<String, Object> pwdUpdteInfoForm(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormService formService = new FormService();
            String htmlStr = "";
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String formId = "sysModifyPwd";
            FormData formsysModifyPwd = formService.getFormData(formId);
            getFormValue(formsysModifyPwd);
            htmlStr = jsonFormUtil.getJsonStr(formsysModifyPwd, "bootstarpTag", query);
            dataMap.put("htmlStr", htmlStr);
        } catch (Exception e) {
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
        }
        return dataMap;
    }



    /**
     * 方法描述： 强制修改密码处理
     *
     * @param
     * @return
     * @throws Exception String
     * @author cd
     * @date 2019-3-25 下午8:58:18
     */
    @RequestMapping(value = "/pwdForceUpdate")
    @ResponseBody
    public Map<String,Object> pwdForceUpdate(String ajaxData,String opName) throws Exception{
        ActionContext.initialize(request,response);
        Map<String,Object> dataMap=new HashMap<String,Object>();
        try {
                SysUser sysUser=new SysUser();
                sysUser.setOpNo(User.getRegNo(request));
                sysUser=sysUserFeign.getById(sysUser);
                sysUser.setOpName(opName);
                ajaxData=Encryption.encoderByMd5(ajaxData);
                sysUser.setPasswordhash(ajaxData);
                sysUserFeign.forceUpdate(sysUser);
                dataMap.put("flag",true);
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
        }catch (Exception e){
                dataMap.put("flag",false);
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
        }
        return  dataMap;
    }




}

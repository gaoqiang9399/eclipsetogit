package  app.component.sys.controller;
import app.base.User;
import app.base.cache.feign.BusiCacheFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.sys.entity.MfSysCompanyMst;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysRole;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.MfSysCompanyMstFeign;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysUserFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: SysOrgAction.java
 * Description:
 * @author:wangcong@dhcc.com.cn
 * @Tue Nov 24 08:32:55 GMT 2015
 **/
@Controller
@RequestMapping("/sysOrg")
public class SysOrgController extends BaseFormBean {

	//页面传值
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//spring注入BO
	@Autowired
	//注入sysOrgFeign
	private SysOrgFeign sysOrgFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private MfSysCompanyMstFeign mfSysCompanyMstFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;
	private String query;

	private FormData formsys6001;
	private FormData formsys6002;
	private FormData formsys6003;
	private FormData formsyscompany0001;
	private FormService formService = new FormService();
	
	public SysOrgController() {
		query = "";
	}
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		formsys6001 = formService.getFormData("sys6001");
		SysOrg sysOrg = new SysOrg();
		getFormValue(formsys6001);
		setObjValue(formsys6001, sysOrg);
		sysOrg.setBrNo(User.getOrgNo(this.getHttpRequest()));
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysOrg", sysOrg));
		List<SysOrg> sysOrgList = (List) sysOrgFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys6001", formsys6001);
		model.addAttribute("sysOrgList", sysOrgList);
		return "/component/sys/SysOrg_List";
	}
	
	
	/**
	 * 获取新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String opNoType) throws Exception {
		ActionContext.initialize(request, response);
		formsys6002 = formService.getFormData("sys6002");
		SysOrg sysOrg = new SysOrg();
		sysOrg=sysOrgFeign.initSysOrg();
		getObjValue(formsys6002, sysOrg);
		model.addAttribute("formsys6002", formsys6002);
		model.addAttribute("opNoType", opNoType);
		model.addAttribute("query", "");
		return "/component/sys/SysOrg_Insert";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForNotice")
	public String getListPageForNotice() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysOrg_List_Notice";
	}
	
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageForNoticeAjax")
	@ResponseBody
	public Map<String, Object> findByPageForNoticeAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysOrg sysOrg = new SysOrg();
		try {
			sysOrg.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysOrg.setCriteriaList(sysOrg, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysOrg", sysOrg));
			ipage = sysOrgFeign.findByPageForNotice(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
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
	 * 新增保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsys6002 = formService.getFormData("sys6002");
		getFormValue(formsys6002);
		SysOrg sysOrg = new SysOrg();
		setObjValue(formsys6002, sysOrg);
		sysOrg.setBrNo(User.getOrgNo(this.getHttpRequest()));
		sysOrg.setOpNoType(BizPubParm.OP_NO_TYPE1);
		sysOrgFeign.insert(sysOrg);
		getObjValue(formsys6002, sysOrg);
		refreshParmKey();
		model.addAttribute("formsys6002", formsys6002);
		return "/component/sys/SysOrg_Detail";
	}
	
	
	/**
	 * 修改保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsys6002 = formService.getFormData("sys6002");
		getFormValue(formsys6002);
		SysOrg sysOrg = new SysOrg();
		setObjValue(formsys6002, sysOrg);
		sysOrgFeign.update(sysOrg);
		getObjValue(formsys6002, sysOrg);
		refreshParmKey();
		model.addAttribute("formsys6002", formsys6002);
		return "detail";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String opNoType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map map  =  getMapByJson(ajaxData);
			String upOne =(String)map.get("upOne");
			if(StringUtil.isEmpty(upOne)){
				formsys6002 = formService.getFormData("syscompany0001");
				getFormValue(formsys6002, getMapByJson(ajaxData));
				if(this.validateFormData(formsys6002)){
					MfSysCompanyMst sysCompany = new MfSysCompanyMst();
					
					setObjValue(formsys6002, sysCompany);
					//logo设置相关不更新
					MfSysCompanyMst sysCompanyOld =  new MfSysCompanyMst();
					sysCompanyOld.setComNo(sysCompany.getComNo());
					sysCompanyOld = mfSysCompanyMstFeign.getById(sysCompanyOld);
					sysCompany = (MfSysCompanyMst)EntityUtil.reflectionSetVal(sysCompanyOld, sysCompany, getMapByJson(ajaxData));
					mfSysCompanyMstFeign.update(sysCompany);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
					refreshParmKey();
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}else{
				formsys6002 = formService.getFormData("sys6002");
				getFormValue(formsys6002, getMapByJson(ajaxData));
				if(this.validateFormData(formsys6002)){
					SysOrg sysOrg = new SysOrg();
					setObjValue(formsys6002, sysOrg);
					sysOrgFeign.updateOrgAndUserBrName(sysOrg);
					sysOrg.setOpNoType(opNoType);
					JSONArray dicArray =   JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
					dataMap.put("json", dicArray.toString());
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
					refreshParmKey();
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/insertAjax")
	@ResponseBody	
	public Map<String, Object> insertAjax(String ajaxData,String opNoType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys6002 = formService.getFormData("sys6002");
			getFormValue(formsys6002, getMapByJson(ajaxData));
			SysOrg sysOrg = new SysOrg();
			setObjValue(formsys6002, sysOrg);
				if(this.validateFormData(formsys6002)){
					if(StringUtil.isNotEmpty(opNoType)){
						sysOrg.setOpNoType(opNoType);
					}else {
						sysOrg.setOpNoType(BizPubParm.OP_NO_TYPE1);
					}
					sysOrg = sysOrgFeign.insert(sysOrg);
					JSONArray dicArray =   JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
					dataMap.put("newSysOrg",sysOrg);
					dataMap.put("json", dicArray.toString());
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
					refreshParmKey();
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/delAjax")
	@ResponseBody	
	public Map<String, Object> delAjax(String ajaxData,String opNoType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			String brNo = ajaxData;
			SysUser sysUser=new SysUser();
			sysUser.setBrNo(brNo);
			sysUser.setOpNoType(BizPubParm.OP_NO_TYPE1);
			List<SysUser> list = sysUserFeign.getAllUserList(sysUser);
			if(list!=null&&list.size()>0){
				dataMap.put("flag", "error");
				dataMap.put("msg", "该部门下存在员工，请删除员工后再删除！");
			}else{
				SysOrg sysOrg = new SysOrg();
				sysOrg.setBrNo(brNo);
				sysOrgFeign.del(sysOrg);
				sysOrg.setOpNoType(opNoType);
				JSONArray dicArray =   JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
				dataMap.put("json", dicArray.toString());
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
				refreshParmKey();
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getAllAjax")
	@ResponseBody	
	public Map<String, Object> getAllAjax(String brNo,String brName,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysOrg sysOrg = new SysOrg();
		String parm = "brNo="+brNo+";brName="+brName;
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(sysOrg,ajaxData,query,parm,null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 刷新缓存
	 * @return
	 * @throws Exception
	 */
	public boolean refreshParmKey()throws Exception {
		busiCacheFeign.refreshAllParmDic();
		return true;
	}
	
	/**
	 * 租赁公司信息的通过机构名带回机构名和机构号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllByOrgNameAjax")
	@ResponseBody
	public Map<String, Object> getAllByOrgNameAjax(String belongOrgName ) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysOrg sysOrg = new SysOrg();
		String parm = "brName="+belongOrgName;
		String ajaxData = "brName";
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(sysOrg,ajaxData,query,parm,"getAllByOrgName"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 删除操作
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/del")
	public String del(String brNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsys6001 = formService.getFormData("sys6001");
		SysOrg sysOrg = new SysOrg();
		sysOrg.setBrNo(brNo);
		sysOrgFeign.del(sysOrg);
		this.addActionMessage(model,"删除成功");
		sysOrg = new SysOrg();
		sysOrg.setBrNo(User.getOrgNo(this.getHttpRequest()));
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysOrg", sysOrg));
		List<SysOrg> sysOrgList = (List) sysOrgFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys6001", formsys6001);
		model.addAttribute("sysOrgList", sysOrgList);
		return "/component/sys/SysOrg_List";
	}

	
	/**
	 * 查询操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String pId,String brNo,Model model,String opNoType) throws Exception {
		ActionContext.initialize(request,response);
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo(brNo);
		mfSysCompanyMst = mfSysCompanyMstFeign.getById(mfSysCompanyMst);
		if(mfSysCompanyMst != null){
			MfSysCompanyMst sysCompany = new MfSysCompanyMst();
			sysCompany.setComNo(brNo);			
			sysCompany = mfSysCompanyMstFeign.getById(sysCompany);
			formsys6002 = formService.getFormData("syscompany0001");
			getObjValue(formsys6002, sysCompany);
			model.addAttribute("ifcompany", "1");
		}else{
			SysOrg sysOrg = new SysOrg();
			sysOrg.setBrNo(brNo);
			sysOrg = sysOrgFeign.getById(sysOrg);
			formsys6002 = formService.getFormData("sys6002");
			SysOrg org = new SysOrg();
			org.setBrNo(sysOrg.getUpOne());
			org = sysOrgFeign.getById(org);
			if(org != null ){
				sysOrg.setUpOneName(org.getBrName());
			}
			getObjValue(formsys6002, sysOrg);
		}
		model.addAttribute("pId", pId);
		model.addAttribute("opNoType", opNoType);
		model.addAttribute("formsys6002", formsys6002);
		model.addAttribute("query", "");
		return "/component/sys/SysOrg_Detail";
	}
	
	/**
	 * 新增保存操作校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert(){
		 ActionContext.initialize(request, response);
		 formsys6002 = formService.getFormData("sys6002");
		 getFormValue(formsys6002);
		 validateFormData(formsys6002);
   	}
   
	/**
	 * 修改保存操作校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate(){
		 ActionContext.initialize(request, response);
		 formsys6002 = formService.getFormData("sys6002");
		 getFormValue(formsys6002);
		 validateFormData(formsys6002);
  	}
	@RequestMapping(value = "/listSysOrg")
	public String listSysOrg(Model model,String opNoType) throws Exception {
		formsys6002 = formService.getFormData("sys6002");
		ActionContext.initialize(request, response);
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOpNoType(opNoType);
		JSONArray dicArray =  JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
		String ajaxData = dicArray.toString();
		model.addAttribute("formsys6002", formsys6002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("opNoType", opNoType);
		return "/component/sys/SysOrg_treeList";
	}
	/**
	 * 
	 * 方法描述： 部门树放大镜选择，单选
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-28 下午10:20:36
	 */
	@RequestMapping(value = "/sysOrgSelect")
	public String sysOrgSelect(Model model,String opNoType) throws Exception {
		formsys6002 = formService.getFormData("sys6002");
		ActionContext.initialize(request, response);
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOpNoType(opNoType);
		JSONArray dicArray =   JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
		String ajaxData = dicArray.toString();
		model.addAttribute("formsys6002", formsys6002);
		model.addAttribute("ajaxData", ajaxData);
		return "/component/sys/SysOrg_selectTree";
	}
	/**
	 * 
	 * 方法描述： 部门树放大镜选择,多选
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-3 下午2:43:10
	 */
	@RequestMapping(value = "/sysOrgSelectCheck")
	public String sysOrgSelectCheck(Model model,String opNoType) throws Exception {
		formsys6002 = formService.getFormData("sys6002");
		ActionContext.initialize(request, response);
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOpNoType(opNoType);
		JSONArray dicArray =   JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
		String ajaxData = dicArray.toString();
		model.addAttribute("formsys6002", formsys6002);
		model.addAttribute("ajaxData", ajaxData);
		return "/component/sys/SysOrg_selectTreeCheck";
	}
	/**
	 * 
	 * 方法描述： 部门选择组件获取数据源
	 * @return
	 * @throws Exception
	 * String
	 * @author 姚文豪
	 * @date 2017-7-3 下午2:43:10
	 */
	@RequestMapping(value = "/sysOrgForSelectAjax")
	@ResponseBody
	public Map<String, Object> sysOrgForSelectAjax(String opNoType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOpNoType(opNoType);
		JSONArray dicArray =  sysOrgFeign.getAllOrgForSelect(sysOrg);
		dataMap.put("items", dicArray);
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得部门树数据源
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-24 上午11:43:06
	 */
	@RequestMapping(value = "/getOrgTreeDataAjax")
	@ResponseBody
	public Map<String, Object> getOrgTreeDataAjax(String opNoType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			SysOrg sysOrg = new SysOrg();
			sysOrg.setOpNoType(opNoType);
			JSONArray dicArray =   JSONArray.fromObject(sysOrgFeign.getAllOrgJson(sysOrg));
			String ajaxData = dicArray.toString();
			dataMap.put("flag", "success");
			dataMap.put("ajaxData", ajaxData);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 新增保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertByTree")
	public String insertByTree() throws Exception {
		ActionContext.initialize(request, response);
		String brNo=request.getParameter("brNo"); 
		String brName=request.getParameter("brName");
		String brLev=request.getParameter("brLev");
		String upOne=request.getParameter("upOne");
		String upTwo=request.getParameter("upTwo");
		String brTel=request.getParameter("brTel");
		String brFax=request.getParameter("brFax");
		String brType=request.getParameter("brType");
		String brAddr=request.getParameter("brAddr");
		String brArea=request.getParameter("brArea");
		String brPost=request.getParameter("brPost");
		String brFinCode=request.getParameter("brFinCode");
		String brSts=request.getParameter("brSts");
		SysOrg sysOrg = new SysOrg();
		sysOrg.setBrAddr(brAddr);
		sysOrg.setBrArea(brArea);
		sysOrg.setBrFax(brFax);
		sysOrg.setBrFinCode(brFinCode);
		sysOrg.setBrLev(brLev);
		sysOrg.setBrNo(brNo);
		sysOrg.setBrPost(brPost);
		sysOrg.setBrSts(brSts);
		sysOrg.setBrTel(brTel);
		sysOrg.setUpOne(getUpBr(upOne));
		sysOrg.setUpTwo(getUpBr(upTwo));
		sysOrg.setBizType("1");
		sysOrg.setBrType(brType);
		sysOrg.setBrName(brName);
		sysOrgFeign.insert(sysOrg);
		return "/component/sys/SysOrgTree_List";
	}
	
	/**
	 * 删除操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delByTree")
	public String delByTree(Model model) throws Exception {
		String brNo =request.getParameter("brNo");
		SysOrg sysOrg = new SysOrg();
		sysOrg.setBrNo(brNo);
		sysOrgFeign.del(sysOrg);
		this.addActionMessage(model,"删除成功");
		return "/component/sys/SysOrgTree_List";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectDeptmentDealOrSupPop")
	public String selectDeptmentDealOrSupPop(String bizType,Model model)throws Exception{
		ActionContext.initialize(request, response);
		formsys6001 = formService.getFormData("sys6001");
		getFormValue(formsys6001);
		SysOrg sysOrg = new SysOrg();
		setObjValue(formsys6001, sysOrg);
		sysOrg.setBizType(bizType);//2-供货商，3-经销商(自身业务)，4-经销商(供货商业务)
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysOrg", sysOrg));
		List<SysOrg> sysOrgList = (List<SysOrg>)sysOrgFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys6001", formsys6001);
		model.addAttribute("sysOrgList", sysOrgList);
		return "/component/sys/SysOrg_ListPop";
	}
	
	/**
	 * 修改保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByTree")

	public String updateByTree(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String brName=request.getParameter("brName");
		String brNo=request.getParameter("brNo"); 
		String brLev=request.getParameter("brLev");
		String upOne=request.getParameter("upOne");
		String upTwo=request.getParameter("upTwo");
		String brTel=request.getParameter("brTel");
		String brFax=request.getParameter("brFax");
		String brAddr=request.getParameter("brAddr");
		String brArea=request.getParameter("brArea");
		String brPost=request.getParameter("brPost");
		String brFinCode=request.getParameter("brFinCode");
		String brSts=request.getParameter("brSts");
		String brType=request.getParameter("brType");
		SysOrg sysOrg = new SysOrg();
		sysOrg.setBrNo(brNo);
		sysOrg = sysOrgFeign.getById(sysOrg);
		sysOrg.setBrAddr(brAddr);
		sysOrg.setBrArea(brArea);
		sysOrg.setBrFax(brFax);
		sysOrg.setBrFinCode(brFinCode);
		sysOrg.setBrLev(brLev);
		sysOrg.setBrName(brName);
		sysOrg.setBrNo(brNo);
		sysOrg.setBrPost(brPost);
		sysOrg.setBrSts(brSts);
		sysOrg.setBrTel(brTel);
		sysOrg.setBrType(brType);
		sysOrg.setUpOne(getUpBr(upOne));
		sysOrg.setUpTwo(getUpBr(upTwo));
		sysOrgFeign.update(sysOrg);
		model.addAttribute("sysOrg", sysOrg);
		return "/component/sys/SysOrgTree";
	}
	
	
	public String getUpBr(String upBr){
		String result = "";
		if("-1".equals(upBr)){
			result = "0";
		}else if ("0".equals(upBr)){
			result = "-1";
		}else{
			result = upBr;
		}
		return result;
	}
	
	/**
	 * 输入部门名称自动补全功能
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/prodAutoMenuAjax")
	@ResponseBody
	public Map<String, Object> prodAutoMenuAjax(String brNo,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		SysOrg sysOrg = new SysOrg();
		String parm = "brNo = " + brNo;
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(sysOrg, ajaxData, query, "","prodAutoMenu"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 输入部门名称自动补全功能（业务部门）
	 * @return
	 */
	@RequestMapping(value = "/appOrgAutoMenuAjax")
	@ResponseBody
	public Map<String, Object> appOrgAutoMenuAjax(String brName,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		SysOrg sysOrg = new SysOrg();
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(sysOrg, ajaxData, query, "","appOrgAutoMenu"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 输入部门名称自动补全功能
	 * @return
	 */
	@RequestMapping(value = "/orgAutoMenuAjax")
	@ResponseBody
	public Map<String, Object> orgAutoMenuAjax(String brName,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		SysOrg sysOrg = new SysOrg();
		try {
			EntityUtil entityUtil = new EntityUtil();
			sysOrg.setBrNo(User.getOrgNo(this.getHttpRequest()).substring(0,3));
			dataMap.put("data", entityUtil.prodAutoMenu(sysOrg, ajaxData, query, "","prodAutoMenu"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 根据选择的上级部门异步带出机构展示编号
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-10 上午11:00:04
	 */
	@RequestMapping(value = "/getBrAliasNoByUpNoAjax")
	@ResponseBody
	public Map<String, Object> getBrAliasNoByUpNoAjax(String upOneNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			String brAliasNo=sysOrgFeign.getBrAliasNoByUpNo(upOneNo);
			dataMap.put("brAliasNo", brAliasNo);
			dataMap.put("flag","success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 查询所有子公司
	 * @return
	 */
	@RequestMapping(value = "/getAllChildCompanyAjax" ,method = RequestMethod.GET)
	@ResponseBody
	public Map<String , Object> getAllChildCompanyAjax(Model model,String brName)throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			SysOrg sysOrg = new SysOrg();
			sysOrg.setBrName(brName);
			List<SysOrg> orgList = sysOrgFeign.getAllChildCompany(sysOrg);
			dataMap.put("items", orgList);
			dataMap.put("flag", "success");
		}catch (Exception e) {
			dataMap.put("flag", "error");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 
	 * <p>Title: getSigningSubject</p>  
	 * <p>Description:获取组织架构的第二级作为签约主体 </p>  
	 * @param pageNo
	 * @param sysOrg
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年8月7日 上午10:00:42
	 */
	@RequestMapping(value = "/getSigningSubject")
	@ResponseBody
	public Map<String,Object> getSigningSubject(int pageNo,SysOrg sysOrg)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("sysOrg", sysOrg));
			ipage = sysOrgFeign.getSigningSubject(ipage);
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
	 *
	 * 方法描述： 打开角色管理页面
	 * @return
	 * @throws Exception
	 * String
	 * @author cd
	 * @date 2019-4-13 下午14:02:04
	 */
	@RequestMapping("/sysRoleManagement")
	public String sysRoleManagement() throws Exception{
		ActionContext.initialize(request, response);
		return "/component/sys/sysRoleManagement";
	}



	/**
	 *
	 * 方法描述： 角色管理列表
	 * @return
	 * @throws Exception
	 * String
	 * @author cd
	 * @date 2019-4-13 下午14:02:04
	 */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/roleInfoList")
    @ResponseBody
    public Map<String,Object> roleInfoList(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                           String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map<String,Object> dataMap=new HashMap<>();
        SysRole sysRole=new SysRole();
        try{
            sysRole.setCustomQuery(ajaxData);//自定义查询参数赋值
            sysRole.setCustomSorts(ajaxData);
            sysRole.setCriteriaList(sysRole, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页尺寸

            ipage.setParams(this.setIpageParams("sysRole", sysRole));
            // 自定义查询Feign方法
            ipage = sysOrgFeign.findByPageList(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        }catch (Exception e){
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
	@RequestMapping(value = "/getSysOrgByBrNo")
	@ResponseBody
	public Map<String, Object> getSysOrgByBrNo(String brNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try{
			SysOrg sysOrg = new SysOrg();
			sysOrg.setBrNo(brNo);
			sysOrg = sysOrgFeign.getById(sysOrg);
			if(sysOrg != null){
				dataMap.put("flag", "success");
				dataMap.put("sysOrg",sysOrg);
				dataMap.put("brName", sysOrg.getBrName());
				dataMap.put("brNo", sysOrg.getBrNo());
				dataMap.put("upTwo", sysOrg.getUpTwo());
				dataMap.put("upOne", sysOrg.getUpOne());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormsys6002() {
		return formsys6002;
	}
	public void setFormsys6002(FormData formsys6002) {
		this.formsys6002 = formsys6002;
	}
	public FormData getFormsys6001() {
		return formsys6001;
	}
	public void setFormsys6001(FormData formsys6001) {
		this.formsys6001 = formsys6001;
	}
	public FormData getFormsyscompany0001() {
		return formsyscompany0001;
	}
	public void setFormsyscompany0001(FormData formsyscompany0001) {
		this.formsyscompany0001 = formsyscompany0001;
	}
	
	public FormData getFormsys6003() {
		return formsys6003;
	}
	public void setFormsys6003(FormData formsys6003) {
		this.formsys6003 = formsys6003;
	}
	
}
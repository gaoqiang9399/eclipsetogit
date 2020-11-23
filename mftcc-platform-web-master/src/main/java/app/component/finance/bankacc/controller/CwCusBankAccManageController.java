package  app.component.finance.bankacc.controller;
import static org.mockito.Matchers.endsWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mftcc.util.StringUtil;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import app.base.User;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.finance.bankacc.entity.CwCusBankAccManage;
import app.component.finance.bankacc.feign.CwCusBankAccManageFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.feign.SysOrgFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: CwCusBankAccManageAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 02 19:14:44 CST 2018
 **/
@Controller
@RequestMapping("/cwCusBankAccManage")
public class CwCusBankAccManageController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入业务层
	@Autowired
	private CwCusBankAccManageFeign cwCusBankAccManageFeign;
	@Autowired
	private SysOrgFeign sysOrgFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//全局变量
	private String query;
	//表单变量
	private FormService formService = new FormService();
	
	public CwCusBankAccManageController(){
		query = "";
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/bankacc/CwCusBankAccManage_List";
	}
	
	/**
	 * 列表数据查询
	 * 
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, int pageSize, String tableId, String tableType,String accProperty)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
		try {
			
			cwCusBankAccManage.setCustomQuery(ajaxData);//自定义查询参数赋值
			cwCusBankAccManage.setCriteriaList(cwCusBankAccManage, ajaxData);//我的筛选
			cwCusBankAccManage.setCustomSorts(ajaxData);// 自定义排序参数赋值
			if(StringUtil.isNotEmpty(accProperty)){
				cwCusBankAccManage.setAccProperty(accProperty);
			}
			
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("cwCusBankAccManage", cwCusBankAccManage));
			ipage = cwCusBankAccManageFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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

	@RequestMapping(value = "/getListPageForSelect")
	public String getListPageForSelect(Model model,String cusNo,String accProperty) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("accProperty", accProperty);
		return "/component/finance/bankacc/CwCusBankAccManage_ListSelect";
	}
	
	/**
	 * 新增页面
	 */
	@RequestMapping(value = "/inputPage" ,method = RequestMethod.GET)
	public String inputPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CwCusBankAccManage cusBankAccManage = new CwCusBankAccManage();
		cusBankAccManage.setLstModTime(DateUtil.getDateTime());
		cusBankAccManage.setRegTime(DateUtil.getDateTime());
		cusBankAccManage.setOpNo(User.getRegNo(request));
		cusBankAccManage.setOpName(User.getRegName(request));
		cusBankAccManage.setBrNo(User.getOrgNo(request));
		cusBankAccManage.setBrName(User.getOrgName(request));
		FormData formbankacc0002 = formService.getFormData("bankacc0002");
		getObjValue(formbankacc0002, cusBankAccManage);
		model.addAttribute("formbankacc0002", formbankacc0002);
		model.addAttribute("query", "");
		return "/component/finance/bankacc/CwCusBankAccManage_Input";
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formbankacc0002 = formService.getFormData("bankacc0002");
			getFormValue(formbankacc0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbankacc0002)){
				CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
				setObjValue(formbankacc0002, cwCusBankAccManage);
				cwCusBankAccManage.setId(WaterIdUtil.getWaterId("BAC"));
				cwCusBankAccManage.setDelFlag(BizPubParm.YES_NO_Y);
				cwCusBankAccManage.setOpNo(User.getRegNo(request));
				cwCusBankAccManage.setOpName(User.getRegName(request));
				cwCusBankAccManage.setBrName(User.getOrgName(request));
				cwCusBankAccManage.setBrNo(User.getOrgNo(request));
				cwCusBankAccManage.setRegTime(DateUtil.getDateTime());
				cwCusBankAccManage.setLstModTime(DateUtil.getDateTime());
				cwCusBankAccManage.setRegDate(DateUtil.getDate());
				cwCusBankAccManage.setLstModDate(DateUtil.getDate());
				cwCusBankAccManageFeign.insert(cwCusBankAccManage);
				dataMap.put("success", true);
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * 更新页面
	 */
	@RequestMapping(value = "/updatePage" ,method = RequestMethod.GET)
	public String updatePage(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
		cwCusBankAccManage.setId(id);
		cwCusBankAccManage = cwCusBankAccManageFeign.getById(cwCusBankAccManage);
		FormData formbankacc0001 = formService.getFormData("bankacc0001");
		getObjValue(formbankacc0001, cwCusBankAccManage);
		model.addAttribute("formbankacc0001", formbankacc0001);
		model.addAttribute("query", "");
		return "/component/finance/bankacc/CwCusBankAccManage_Update";
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
		try{
			FormData formbankacc0002 = formService.getFormData("bankacc0002");
			getFormValue(formbankacc0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbankacc0002)){
				cwCusBankAccManage = new CwCusBankAccManage();
				setObjValue(formbankacc0002, cwCusBankAccManage);
				cwCusBankAccManage.setLstModTime(DateUtil.getDateTime());
				cwCusBankAccManage.setLstModDate(DateUtil.getDate());
				cwCusBankAccManageFeign.update(cwCusBankAccManage);
				dataMap.put("success", true);
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formbankacc0002 = formService.getFormData("bankacc0002");
		CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
		cwCusBankAccManage.setId(id);
		cwCusBankAccManage = cwCusBankAccManageFeign.getById(cwCusBankAccManage);
		getObjValue(formbankacc0002, cwCusBankAccManage,formData);
		if(cwCusBankAccManage!=null){
			dataMap.put("cwCusBankAccManage",cwCusBankAccManage);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
		cwCusBankAccManage.setId(id);
		try {
			cwCusBankAccManage = cwCusBankAccManageFeign.getById(cwCusBankAccManage);
			cwCusBankAccManage.setLstModTime(DateUtil.getDateTime());
			cwCusBankAccManage.setLstModDate(DateUtil.getDate());
			cwCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
			cwCusBankAccManageFeign.update(cwCusBankAccManage);
			
			dataMap.put("success", true);
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 打开查找担保公司页面
	 * @return
	 */
	@RequestMapping(value = "/getAccNameDataSourceAjax" ,method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAccNameDataSourceAjax(Model model,String accountName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<CwCusBankAccManage> list = new ArrayList<CwCusBankAccManage>();
			SysOrg sysOrg = new SysOrg();
			sysOrg.setBrName(accountName);
			List<SysOrg> orgList = sysOrgFeign.getAllByOrgNameAndLevel(sysOrg);
			for(int i= 0;i<orgList.size();i++) {
				CwCusBankAccManage cwCusBankAccManage = new CwCusBankAccManage();
				cwCusBankAccManage.setAccountName(orgList.get(i).getBrName());
				list.add(cwCusBankAccManage);
			}
			dataMap.put("items", list);
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
	 * <p>Title: getBankCusAccMangeAjax</p>  
	 * <p>Description:养殖概况信息的时候选择银行账号 </p>  
	 * @param pageNo
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年8月1日 下午2:59:48
	 */
	@RequestMapping(value = "/getCwBankCusAccMangeAjax")
	@ResponseBody
	public Map<String,Object> getCwBankCusAccMangeAjax(int pageNo,String useType)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			CwCusBankAccManage   cwCusBankAccManage   =  new  CwCusBankAccManage();
			cwCusBankAccManage.setUseType(useType);
			ipage.setParams(this.setIpageParams("cwCusBankAccManage", cwCusBankAccManage));
			ipage = cwCusBankAccManageFeign.findByPage(ipage);
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
	 * <p>Title: getbusCwBankCusAccMangeAjax</p>  
	 * <p>Description: 获取收付款账号</p>  
	 * @param pageNo
	 * @param useType
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月25日 下午2:15:33
	 */
	@RequestMapping(value = "/getbusCwBankCusAccMangeAjax")
	@ResponseBody
	public Map<String,Object> getbusCwBankCusAccMangeAjax(int pageNo,String cusNo)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			CwCusBankAccManage   cwCusBankAccManage   =  new  CwCusBankAccManage();
			cwCusBankAccManage.setCusNo(cusNo);
			ipage.setParams(this.setIpageParams("cwCusBankAccManage", cwCusBankAccManage));
			ipage = cwCusBankAccManageFeign.busfindByPage(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
	
}

package  app.component.nmd.comtroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusAppKindFeign;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.OptionsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.nmd.entity.ParLoanuse;
import app.component.nmd.feign.ParLoanuseFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: ParLoanuseAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 02 14:12:41 CST 2018
 **/
@Controller
@RequestMapping("/parLoanuse")
public class ParLoanuseController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入ParLoanuseBo
	@Autowired
	private ParLoanuseFeign parLoanuseFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/nmd/ParLoanuse_List";
		
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ParLoanuse  parLoanuse = new ParLoanuse();
		try {
			parLoanuse.setCustomQuery(ajaxData);//自定义查询参数赋值
			parLoanuse.setCriteriaList(parLoanuse, ajaxData);//我的筛选
			//ParLoanuse.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(ParLoanuse,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("parLoanuse", parLoanuse));
			ipage = parLoanuseFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		 Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formloanuseadd = formService.getFormData("loanuseadd");
			getFormValue(formloanuseadd, getMapByJson(ajaxData));
			if(this.validateFormData(formloanuseadd)){
				ParLoanuse  parLoanuse = new ParLoanuse();
				setObjValue(formloanuseadd, parLoanuse);
				parLoanuseFeign.insert(parLoanuse);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData  formloanusedetail = formService.getFormData("loanusedetail");
		getFormValue(formloanusedetail, getMapByJson(ajaxData));
		ParLoanuse parLoanuseJsp = new ParLoanuse();
		setObjValue(formloanusedetail, parLoanuseJsp);
		ParLoanuse parLoanuse = parLoanuseFeign.getById(parLoanuseJsp);
		if(parLoanuse!=null){
			try{
				parLoanuse = (ParLoanuse)EntityUtil.reflectionSetVal(parLoanuse, parLoanuseJsp, getMapByJson(ajaxData));
				parLoanuseFeign.update(parLoanuse);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formloanusedetail = formService.getFormData("loanusedetail");
			getFormValue(formloanusedetail, getMapByJson(ajaxData));
			if(this.validateFormData(formloanusedetail)){
				ParLoanuse parLoanuse = new ParLoanuse();
				setObjValue(formloanusedetail, parLoanuse);
				parLoanuseFeign.update(parLoanuse);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String useid) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formloanusedetail = formService.getFormData("loanusedetail");
		ParLoanuse parLoanuse = new ParLoanuse();
		parLoanuse.setUseNo(useid);
		parLoanuse =parLoanuseFeign.getById(parLoanuse);
		getObjValue(formloanusedetail, parLoanuse,formData);
		if(parLoanuse!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String useid) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ParLoanuse parLoanuse = new ParLoanuse();
		parLoanuse.setUseNo(useid);
		try {
			parLoanuseFeign.delete(parLoanuse);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formloanuseadd = formService.getFormData("loanuseadd");
		model.addAttribute("formloanuseadd", formloanuseadd);
		model.addAttribute("query", "");
		return "/component/nmd/ParLoanuse_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String useid) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formloanusedetail = formService.getFormData("loanusedetail");
		 getFormValue(formloanusedetail);
		 ParLoanuse parLoanuse = new ParLoanuse();
		parLoanuse.setUseNo(useid);
		 parLoanuse = parLoanuseFeign.getById(parLoanuse);
		 getObjValue(formloanusedetail, parLoanuse);
		 model.addAttribute("formloanuseadd", formloanusedetail);
		model.addAttribute("query", "");
		return "/component/nmd/ParLoanuse_Detail";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formloanuseadd = formService.getFormData("loanuseadd");
		 getFormValue(formloanuseadd);
		 ParLoanuse parLoanuse = new ParLoanuse();
		 setObjValue(formloanuseadd, parLoanuse);
		parLoanuseFeign.insert(parLoanuse);
		 getObjValue(formloanuseadd, parLoanuse);
		 this.addActionMessage(model, "保存成功");
		 List<ParLoanuse> parLoanuseList = (List<ParLoanuse>)parLoanuseFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formloanuseadd", formloanuseadd);
		model.addAttribute("parLoanuseList", parLoanuseList);
		model.addAttribute("query", "");
		return "/component/nmd/ParLoanuse_Insert";
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
			FormService formService = new FormService();
		 FormData formloanuseadd = formService.getFormData("loanusedetail");
		 getFormValue(formloanuseadd);
		 boolean validateFlag = this.validateFormData(formloanuseadd);
	}
	/**
	 * 删除
	 * @param groupNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String useid) throws Exception {
		ActionContext.initialize(request,
				response);
		ParLoanuse parLoanuse = new ParLoanuse();
		parLoanuse.setUseNo(useid);;
		parLoanuseFeign.delete(parLoanuse);
		return getListPage(model);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
			FormService formService = new FormService();
		 FormData formloanusedetail = formService.getFormData("loanusedetail");
		 getFormValue(formloanusedetail);
		 boolean validateFlag = this.validateFormData(formloanusedetail);
	}
	/**
	 * 
	 * 方法描述： 贷款用途根据一级分类查询二级分类信息
	 * @return String
	 * @throws Exception
	 * @author jixiwang
	 * @date 2018-2-11 下午14:09:50
	 */

	public Map<String, Object>  getFincUseSmAjax(String useid) {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParLoanuse par=new ParLoanuse();
		par.setUplev(useid);
		try {	
			List<ParLoanuse> parLoanuseList=parLoanuseFeign.getFincUseSm(par);
			JSONArray parArray=JSONArray.fromObject(parLoanuseList);
			for (int i = 0; i < parArray.size(); i++) {
				if(i==0){
					parArray.getJSONObject(i).put("selected","true");
				}
				parArray.getJSONObject(i).put("id",parArray.getJSONObject(i).getString("useid"));
				parArray.getJSONObject(i).put("name",parArray.getJSONObject(i).getString("usename"));	
				}
			dataMap.put("items",parArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
		}

	/**
	 * 贷款投向细类选择组件(征信用)
	 * 
	 * @param model
	 * @param fincUse 门类, 细类根据门类筛选
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFincUseSmList")
	public String getFincUseSmList(Model model, String fincUse) throws Exception {
		ActionContext.initialize(request, response);

		ParLoanuse par = new ParLoanuse();
		par.setCategory(fincUse);
		List<ParLoanuse> parLoanuseList = parLoanuseFeign.getFincUseSm(par);

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (ParLoanuse obj : parLoanuseList) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("id", obj.getUseNo());
			data.put("name", obj.getUseName());
			data.put("pId", obj.getUplev());

			dataList.add(data);
		}
		model.addAttribute("ajaxData", new Gson().toJson(dataList));
		model.addAttribute("separator", "-");
		model.addAttribute("showAllFlag", "1");
		model.addAttribute("query", "");
		model.addAttribute("isStepLoading", "false");

		return "/component/nmd/IndustryAndArea";
	}
	/**
	 * 贷款投向细类选择组件
	 *
	 * @param model
	 * @param fincUse 门类, 细类根据门类筛选
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFincUseSmListSelect")
	@ResponseBody
	public Map<String, Object> getFincUseSmListSelect(Model model, String fincUse) throws Exception {
		ActionContext.initialize(request, response);

		ParLoanuse par = new ParLoanuse();
		par.setCategory(fincUse);
		List<ParLoanuse> parLoanuseList = parLoanuseFeign.getFincUseSm(par);
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (ParLoanuse obj : parLoanuseList) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("id", obj.getUseNo());
			data.put("name", obj.getUseName());
			data.put("pId", obj.getUplev());
			dataList.add(data);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		String NmdAreaListString = new Gson().toJson(dataList);
		dataMap.put("items", NmdAreaListString);//TODO 地区缓存需要修改
		return dataMap;
	}

	/**
	 * 贷款投向细类选择组件(全部细类投向)
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllFincUseSmList")
	public String getAllFincUseSmList(Model model,String appId) throws Exception {
		ActionContext.initialize(request, response);
		ParLoanuse par = new ParLoanuse();
		List<ParLoanuse> parLoanuseList = parLoanuseFeign.getFincUseSm(par);

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);

		if (!StringUtil.isEmpty(mfBusAppKind.getFincUse())) {
			String[] fincUses = mfBusAppKind.getFincUse().split("\\|");
			for (int i = 0; i < fincUses.length; i++) {
				for (ParLoanuse obj : parLoanuseList) {
					if(!obj.getUseNo().contains(fincUses[i]))
					{
						continue;
					}
					Map<String, String> data = new HashMap<String, String>();
					data.put("id", obj.getUseNo());
					data.put("name", obj.getUseName());
					data.put("pId", obj.getUplev());

					dataList.add(data);
				}
			}
		}

		model.addAttribute("ajaxData", new Gson().toJson(dataList));
		model.addAttribute("separator", "-");
		model.addAttribute("showAllFlag", "1");
		model.addAttribute("query", "");
		model.addAttribute("isStepLoading", "false");

		return "/component/nmd/IndustryAndArea";
	}


}

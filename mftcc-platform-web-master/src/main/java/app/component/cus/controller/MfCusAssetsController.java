package app.component.cus.controller;

import app.component.collateral.entity.EvalInfo;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusAssets;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusAssetsFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusAssetsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 15:40:17 CST 2016
 **/
@Controller
@RequestMapping("/mfCusAssets")
public class MfCusAssetsController extends BaseFormBean {
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusAssetsFeign mfCusAssetsFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		MfCusAssets mfCusAssets = new MfCusAssets();
		mfCusAssets.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
		List<MfCusAssets> mfCusAssetsList = (List<MfCusAssets>)mfCusAssetsFeign.findByPage(ipage).getResult();
		model.addAttribute("mfCusAssetsList", mfCusAssetsList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssets_List";
	}
	/**
	 * 
	 * 方法描述： 获得银行卡账号信息列表
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-6-4 下午4:37:03
	 */
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<>();
		List<MfCusAssets> mfCusAssetsList=new ArrayList<MfCusAssets>();
		FormData formcusassets00001 = formService.getFormData("cusassets00001");
		try {
			MfCusAssets mfCusAssets = new MfCusAssets();
			mfCusAssets.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
			mfCusAssetsList = (List<MfCusAssets>) mfCusAssetsFeign.findByPage(ipage).getResult();
			model.addAttribute("mfCusAssetsList", mfCusAssetsList);
			model.addAttribute("query", "");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return "/component/cus/MfCusAssets_ListBig";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusAssets mfCusAssets = new MfCusAssets();
		try {
			mfCusAssets.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusAssets.setCriteriaList(mfCusAssets, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusAssets,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
			ipage = mfCusAssetsFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType,
					(List) ipage.getResult(), ipage, true);
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

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusAssetsAction").getAddModel();
			}
			if(StringUtil.isEmpty(formId)){
//				logger.error("MfCusAssetsAction的formId找不到");
			}
			FormData formcusassets00003 = formService.getFormData(formId);
			if(formcusassets00003.getFormId() == null){
//				logger.error("form"+formId+".xml文件不存在");
			}
			getFormValue(formcusassets00003, map);
			if (this.validateFormDataAnchor(formcusassets00003)) {
				MfCusAssets mfCusAssets = new MfCusAssets();
				setObjValue(formcusassets00003, mfCusAssets);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusAssets.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusAssets.setCusName(cusName);
				mfCusAssetsFeign.insert(mfCusAssets);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusAssets.getCusNo(),mfCusAssets.getRelNo());//更新客户信息完整度

				String tableId = "tablecusFixedAssetsListBase";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusAssetsAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}


				String cusNo = mfCusAssets.getCusNo();
				String relNo = mfCusAssets.getRelNo();
				mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);
				mfCusAssets.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
				//String  tableHtml = jtu.getJsonStr("tablecusassets00001","tableTag", (List<MfCusAssets>)mfCusAssetsFeign.findByPage(ipage).getResult(), null,true);
				String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusAssets>)mfCusAssetsFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
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
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formcusassets00002 = formService.getFormData("cusassets00002");
		getFormValue(formcusassets00002, getMapByJson(ajaxData));
		MfCusAssets mfCusAssetsJsp = new MfCusAssets();
		setObjValue(formcusassets00002, mfCusAssetsJsp);
		MfCusAssets mfCusAssets = mfCusAssetsFeign.getById(mfCusAssetsJsp);
		if (mfCusAssets != null) {
			try {
				mfCusAssets = (MfCusAssets) EntityUtil.reflectionSetVal(
						mfCusAssets, mfCusAssetsJsp, getMapByJson(ajaxData));
				mfCusAssetsFeign.update(mfCusAssets);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusAssets mfCusAssets = new MfCusAssets();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusAssetsAction").getAddModel();
			}
			FormData formcusassets00003 = formService.getFormData(formId);
			if(formcusassets00003.getFormId() == null){
//				logger.error("MfCusAssetsAction表单的form"+formId+".xml文件不存在");
			}
			getFormValue(formcusassets00003, map);
			if (this.validateFormData(formcusassets00003)) {
				mfCusAssets = new MfCusAssets();
				setObjValue(formcusassets00003, mfCusAssets);
				mfCusAssetsFeign.update(mfCusAssets);
				//getTableData();

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusAssets.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusFixedAssetsListBase";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusAssetsAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}


				String cusNo = mfCusAssets.getCusNo();
				mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
				//String  tableHtml = jtu.getJsonStr("tablecusassets00001","tableTag", (List<MfCusAssets>)mfCusAssetsFeign.findByPage(ipage).getResult(), null,true);
				String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusAssets>)mfCusAssetsFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
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
	public Map<String,Object> getByIdAjax(String assetsId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusassets00002 = formService.getFormData("cusassets00002");
		MfCusAssets mfCusAssets = new MfCusAssets();
		mfCusAssets.setAssetsId(assetsId);
		mfCusAssets = mfCusAssetsFeign.getById(mfCusAssets);
		getObjValue(formcusassets00002, mfCusAssets, formData);
		if (mfCusAssets != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
	public Map<String,Object> deleteAjax(String assetsId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusAssets mfCusAssets = new MfCusAssets();
		mfCusAssets.setAssetsId(assetsId);
		try {
			//JSONObject jb = JSONObject.fromObject(ajaxData);
			//mfCusAssets = (MfCusAssets)JSONObject.toBean(jb, MfCusAssets.class);
			mfCusAssetsFeign.delete(mfCusAssets);
			//getTableData();
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
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
	public String input(Model model,String cusNo,String relNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();

		//初始值：formId为null，formcusassets00003为null
		MfCusAssets mfCusAssets = new MfCusAssets();
		String formId=null;
		mfCusAssets.setCusNo(cusNo);
		mfCusAssets.setRelNo(cusNo);
		mfCusAssets.setAssetsId(WaterIdUtil.getWaterId());
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusAssets.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusAssetsAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssetsAction表单信息没有查询到");
		} else {
			FormData formcusassets00003 = formService.getFormData(formId);
			if (formcusassets00003.getFormId() == null) {
//				logger.error("客户类型为" + mfCusCustomer.getCusType()
//						+ "的MfCusAssetsAction表单form" + formId + ".xmlinsertAjax文件不存在");
			} else {
				getFormValue(formcusassets00003);
				getObjValue(formcusassets00003, mfCusAssets);
				
			}
			model.addAttribute("formcusassets00003", formcusassets00003);
			model.addAttribute("query", "");
		}
		FormData formHouseEval = formService.getFormData("houseEvalBase");
		FormData formHouseEvalMan = formService.getFormData("houseEvalBaseMan");
		this.changeFormProperty(formHouseEval, "cusNo", "initValue", mfCusCustomer.getCusNo());
		this.changeFormProperty(formHouseEval, "cusName", "initValue", mfCusCustomer.getCusName());
		this.changeFormProperty(formHouseEvalMan, "cusNo", "initValue", mfCusCustomer.getCusNo());
		this.changeFormProperty(formHouseEvalMan, "cusName", "initValue", mfCusCustomer.getCusName());
		model.addAttribute("formHouseEval", formHouseEval);
		model.addAttribute("formHouseEvalMan", formHouseEvalMan);
		return "/component/cus/MfCusAssets_Insert";
	}
	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-9-25 下午6:01:28
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		//初始值：formId为null，formcusassets00003为null
		MfCusAssets mfCusAssets = new MfCusAssets();
		String formId=null;
		mfCusAssets.setCusNo(cusNo);
		mfCusAssets.setRelNo(relNo);
		
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusAssets.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusAssetsAction");
		if (mfCusFormConfig == null) {
			
		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssetsAction表单信息没有查询到");
		} else {
			FormData formcusassets00003 = formService.getFormData(formId);
			if (formcusassets00003.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssetsAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusassets00003);
				getObjValue(formcusassets00003, mfCusAssets);
			}
			model.addAttribute("formcusassets00003", formcusassets00003);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusAssets_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcusassets00002 = formService.getFormData("cusassets00002");
		getFormValue(formcusassets00002);
		MfCusAssets mfCusAssets = new MfCusAssets();
		setObjValue(formcusassets00002, mfCusAssets);
		mfCusAssetsFeign.insert(mfCusAssets);
		getObjValue(formcusassets00002, mfCusAssets);
		this.addActionMessage(null, "保存成功");
		Ipage ipage=new Ipage();
		ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
		List<MfCusAssets> mfCusAssetsList = (List<MfCusAssets>) mfCusAssetsFeign.findByPage(
				this.getIpage()).getResult();
		model.addAttribute("mfCusAssetsList", mfCusAssetsList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssets_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String assetsId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String formId=null;
		MfCusAssets mfCusAssets = new MfCusAssets();
		mfCusAssets.setAssetsId(assetsId);
		mfCusAssets = mfCusAssetsFeign.getById(mfCusAssets);
		
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusAssets.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusAssetsAction");
		if(mfCusFormConfig == null){
			
		}else{
			if("1".equals(mfCusFormConfig.getShowType())){
				formId = mfCusFormConfig.getShowModelDef();
			}else{
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusAssetsAction表单信息没有查询到");
		}else{
			FormData formcusassets00003 = formService.getFormData(formId);
			if(formcusassets00003.getFormId() == null){
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusAssetsAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusassets00003);
				getObjValue(formcusassets00003, mfCusAssets);
			}
			model.addAttribute("formcusassets00003", formcusassets00003);
			model.addAttribute("query", "");
		}
		
		return "/component/cus/MfCusAssets_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String assetsId) throws Exception {
		ActionContext.initialize(request,response);
		MfCusAssets mfCusAssets = new MfCusAssets();
		mfCusAssets.setAssetsId(assetsId);
		mfCusAssetsFeign.delete(mfCusAssets);
		return getListPage(null, assetsId);
	}
	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String cusNo,String assetsId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		String formId="";
		String query="";
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusAssetsAction");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getShowModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusAssetsAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}else{
			Map<String,Object> formData = new HashMap<String,Object>();
			request.setAttribute("ifBizManger","3");
			dataMap = new HashMap<String, Object>(); 
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			MfCusAssets mfCusAssets = new MfCusAssets();
			mfCusAssets.setAssetsId(assetsId);
			mfCusAssets = mfCusAssetsFeign.getById(mfCusAssets);
			FormData formcusassets00004=null;
			if ("1".equals(mfCusAssets.getFixAssType())||"2".equals(mfCusAssets.getFixAssType())||"5".equals(mfCusAssets.getFixAssType())) {
				 formcusassets00004 = formService.getFormData(formId);
			}else if ("3".equals(mfCusAssets.getFixAssType())||"4".equals(mfCusAssets.getFixAssType())) {
				 formcusassets00004 = formService.getFormData("cusassets00005");
			}else {
			}
			getObjValue(formcusassets00004, mfCusAssets,formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusassets00004,"propertySeeTag",query);
			if(mfCusAssets!=null){
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusAssets);
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String,Object> updateByOneAjax(String formId,String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusAssets mfCusAssets = new MfCusAssets();
		//这里得到的formId是带form字符串的，比如formcuscorp0001
		if(StringUtil.isEmpty(formId)){
			formId = mfCusFormConfigFeign.getByCusType("base", "MfCusAssetsAction").getShowModel();
		}else{
			if(formId.indexOf("form") == -1){
			}else{
				formId = formId.substring(4);
			}
		}			
		FormData formcusassets00004 = formService.getFormData(formId);
		getFormValue(formcusassets00004, getMapByJson(ajaxData));
		//租用年限不能以0开头
		FormActive[] activeArray = formcusassets00004.getFormActiveArray();
		for(FormActive active :activeArray)
		{
			if("hireTerm".equals(active.getFieldName()))
			{
				String term = active.getInitValue();
				if(term != null && !"".equals(term) && !"0".equals(term)) {
					char[]termArray = term.toCharArray();
					if (termArray[0] == '0') {
						dataMap.put("flag", "error");
						dataMap.put("msg", "租用年限中的内容不是整数！");
						return dataMap;
					}
			}
		}
		}
		MfCusAssets mfCusAssetsNew = new MfCusAssets();
		setObjValue(formcusassets00004, mfCusAssetsNew);
		mfCusAssets.setAssetsId(mfCusAssetsNew.getAssetsId());
		mfCusAssets = mfCusAssetsFeign.getById(mfCusAssets);
		if(mfCusAssets!=null){
			try{
				mfCusAssets = (MfCusAssets)EntityUtil.reflectionSetVal(mfCusAssets, mfCusAssetsNew, getMapByJson(ajaxData));
				mfCusAssetsFeign.update(mfCusAssets);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcusassets00002 = formService.getFormData("cusassets00002");
		getFormValue(formcusassets00002);
		boolean validateFlag = this.validateFormData(formcusassets00002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcusassets00002 = formService.getFormData("cusassets00002");
		getFormValue(formcusassets00002);
		boolean validateFlag = this.validateFormData(formcusassets00002);
	}
	/**
	 * 
	 * 方法描述： 操作完成，刷新列表
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2016-5-31 下午4:31:08
	 */
	@RequestMapping(value = "/getTableData")
	@ResponseBody
	private Map<String,Object> getTableData(String cusNo,String tableId) throws Exception {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusAssets mfCusAssets = new MfCusAssets();
		mfCusAssets.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusAssets>)mfCusAssetsFeign.findByPage(ipage).getResult(), null,true);
		dataMap.put("tableData",tableHtml);
		return dataMap;
	}
}

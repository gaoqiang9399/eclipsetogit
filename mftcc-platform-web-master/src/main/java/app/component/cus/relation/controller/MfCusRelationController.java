package app.component.cus.relation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.relation.entity.MfCusRelation;
import app.component.cus.relation.feign.MfCusRelationFeign;
import app.component.cus.relation.feign.MfCusRelationTypeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfCusRelationAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 11 15:47:03 CST 2016
 **/
@Controller
@RequestMapping("/mfCusRelation")
public class MfCusRelationController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusRelationBo
	@Autowired
	private MfCusRelationFeign mfCusRelationFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	// 全局变量
	@Autowired
	private MfCusRelationTypeFeign mfCusRelationTypeFeign;
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusRelation mfCusRelation = new MfCusRelation();

		mfCusRelation.setCusNo(cusNo);
		List<MfCusRelation> mfCusRelationList = mfCusRelationFeign.getListByCusNo(mfCusRelation);
		for (int i = 0; i < mfCusRelationList.size(); i++) {
			if (mfCusRelationList.get(i).getRemark() != null && "1".equals(mfCusRelationList.get(i).getRemark())) {
				mfCusRelationList.remove(i);
				i--;
			}
		}
		String isEmpty = "noempty";
		if (mfCusRelationList.size() == 0) {
			isEmpty = "empty";
		}
		model.addAttribute("mfCusRelationList", mfCusRelationList);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("isEmpty", isEmpty);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelation_List";
	}
	/**
	 * 查询关联关系，关联关系
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByCusAjax")
	@ResponseBody
	public Map<String, Object> getListByCusAjax(String cusNo) throws Exception {
		String flag = "";
		if(mfCusRelationFeign.getRelationByCusNo(cusNo)){
			flag="error";
		}else{
			flag="success";
		}
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("flag",flag);
		return dataMap;
	}
	/**
	 * Ajax刷新关联关系
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		String flag="";
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if ("1".equals(mfCusCustomer.getCusBaseType())) {
			mfCusRelationFeign.Sysinsert(mfCusRelation);
		} else if ("2".equals(mfCusCustomer.getCusBaseType())) {
			mfCusRelationFeign.SysinsertForPers(mfCusRelation);
		}else {
		}
		if(mfCusRelationFeign.getRelationByCusNo(cusNo)){
			flag="error";
		}else{
			flag="success";
		}
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("flag", flag);
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRelation mfCusRelation = new MfCusRelation();
		try {
			mfCusRelation.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusRelation.setCriteriaList(mfCusRelation, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusRelation,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusRelationFeign.findByPage(ipage, mfCusRelation);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusrelation0001 = formService.getFormData("cusrelation0001");

			getFormValue(formcusrelation0001, getMapByJson(ajaxData));
			String cusNo1 = getMapByJson(ajaxData).get("cusNo1").toString();
			if (this.validateFormData(formcusrelation0001)) {
				MfCusRelation mfCusRelation = new MfCusRelation();
				setObjValue(formcusrelation0001, mfCusRelation);

				mfCusRelation = mfCusRelationFeign.insert(mfCusRelation);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("cusRelation", mfCusRelation);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		getFormValue(formcusrelation0002, getMapByJson(ajaxData));
		MfCusRelation mfCusRelationJsp = new MfCusRelation();
		setObjValue(formcusrelation0002, mfCusRelationJsp);
		MfCusRelation mfCusRelation = mfCusRelationFeign.getById(mfCusRelationJsp);
		if (mfCusRelation != null) {
			try {
				mfCusRelation = (MfCusRelation) EntityUtil.reflectionSetVal(mfCusRelation, mfCusRelationJsp,
						getMapByJson(ajaxData));
				mfCusRelationFeign.update(mfCusRelation);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
			getFormValue(formcusrelation0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusrelation0002)) {
				MfCusRelation mfCusRelation = new MfCusRelation();
				setObjValue(formcusrelation0002, mfCusRelation);
				mfCusRelationFeign.update(mfCusRelation);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String relationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setRelationId(relationId);
		mfCusRelation = mfCusRelationFeign.getById(mfCusRelation);
		getObjValue(formcusrelation0002, mfCusRelation, formData);
		if (mfCusRelation != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * 
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByCusAjax")
	@ResponseBody
	public Map<String, Object> getByCusAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setCusNo(cusNo);
		mfCusRelation = mfCusRelationFeign.getById(mfCusRelation);
		getObjValue(formcusrelation0002, mfCusRelation, formData);
		if (mfCusRelation != null) {
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
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String relationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setRelationId(relationId);
		try {
			mfCusRelationFeign.delete(mfCusRelation);
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

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		model.addAttribute("formcusrelation0002", formcusrelation0002);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelation_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		getFormValue(formcusrelation0002);
		MfCusRelation mfCusRelation = new MfCusRelation();
		setObjValue(formcusrelation0002, mfCusRelation);
		mfCusRelationFeign.insert(mfCusRelation);
		getObjValue(formcusrelation0002, mfCusRelation);
		this.addActionMessage(model, "保存成功");
		List<MfCusRelation> mfCusRelationList = (List<MfCusRelation>) mfCusRelationFeign
				.findByPage(this.getIpage(), mfCusRelation).getResult();
		model.addAttribute("formcusrelation0002", formcusrelation0002);
		model.addAttribute("mfCusRelationList", mfCusRelationList);
		model.addAttribute("query", "");

		return "/component/cus/relation/MfCusRelation_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String relationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusrelation0001 = formService.getFormData("cusrelation0001");
		getFormValue(formcusrelation0001);
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setRelationId(relationId);
		mfCusRelation = mfCusRelationFeign.getById(mfCusRelation);
		getObjValue(formcusrelation0001, mfCusRelation);
		model.addAttribute("formcusrelation0001", formcusrelation0001);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelation_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param relationId
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String relationId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setRelationId(relationId);
		mfCusRelationFeign.delete(mfCusRelation);
		return getListPage(model, cusNo);
	}

	/**
	 * 跳转到图层页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/forDetail")
	public String forDetail(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		String cusNo = request.getParameter("cusNo");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		/**
		 * 进入关联关系跳转到图层页面
		 */
		MfCusRelation mfCusRelation = new MfCusRelation();
		mfCusRelation.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if ("1".equals(mfCusCustomer.getCusBaseType())) {
			mfCusRelationFeign.Sysinsert(mfCusRelation);
		} else if ("2".equals(mfCusCustomer.getCusBaseType())) {
			mfCusRelationFeign.SysinsertForPers(mfCusRelation);
		}else {
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "component/cus/relation/relationForView";
	}

	/**
	 * 跳转到图层页面(适用于渠道商、资金机构等特殊客户类型)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/forSEDetail")
	public String forSEDetail(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		String cusNo = request.getParameter("cusNo");
		String baseType = request.getParameter("baseType");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		dataMap.put("baseType", baseType);
		model.addAttribute("query", "");
		model.addAttribute("dataMap", dataMap);
		return "component/cus/relation/relationForSEView";
	}

	/**
	 * 获取关联关系图层所需数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ForView")
	public Map<String, Object> ForView(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusNo = request.getParameter("cusNo");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		// 加载所有关联关系并形成json字符串
		dataMap = mfCusRelationFeign.getJsonStrForRelation(mfCusCustomer);

		return dataMap;
	}

	/**
	 * 获取关联关系图层所需数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ForSEView")
	@ResponseBody
	public Map<String, Object> ForSEView(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusNo = request.getParameter("cusNo");
		String baseType = request.getParameter("baseType");
		Map<String, String> virMap = new HashMap<String, String>();
		dataMap = mfCusRelationFeign.getJsonStrForSERelation(cusNo, baseType);
		return dataMap;
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		getFormValue(formcusrelation0002);
		boolean validateFlag = this.validateFormData(formcusrelation0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusrelation0002 = formService.getFormData("cusrelation0002");
		getFormValue(formcusrelation0002);
		boolean validateFlag = this.validateFormData(formcusrelation0002);
	}
	
	 /**
	  * 
	  *<p>Description:判断该客户是否与其他客户存在关联关系 </p> 
	  *@param ajaxData
	  *@param cusNo
	  *@return
	  *@throws Exception
	  *@author 周凯强
	  *@date 2018年7月5日下午5:39:13
	  */
	 @RequestMapping(value = "/isCustomerRelation")
	 @ResponseBody
	 public  Map<String, Object>   isCustomerRelation(String ajaxData,String  cusNo)throws  Exception{
		 MfCusRelation    mfCusRelation  =  new  MfCusRelation();
		 mfCusRelation.setCusNo(cusNo);
		 List<MfCusRelation>  mfCusRelations  =  mfCusRelationFeign.isCustomerRelation(mfCusRelation);  
		 Map<String, Object>   dataMap  =  new  HashMap<>();
		 if(mfCusRelations != null && mfCusRelations.size() > 0){
			 dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		 return dataMap;
		   
    }
}

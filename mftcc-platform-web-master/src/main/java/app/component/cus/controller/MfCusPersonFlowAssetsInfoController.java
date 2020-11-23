package app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonFlowAssetsInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonFlowAssetsInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusPersonFlowAssetsInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 13 18:38:37 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonFlowAssetsInfo")
public class MfCusPersonFlowAssetsInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonFlowAssetsInfoBo
	@Autowired
	private MfCusPersonFlowAssetsInfoFeign mfCusPersonFlowAssetsInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	// 全局变量
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonFlowAssetsInfo_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		try {
			mfCusPersonFlowAssetsInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonFlowAssetsInfo.setCriteriaList(mfCusPersonFlowAssetsInfo, ajaxData);// 我的筛选
			// mfCusPersonFlowAssetsInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusPersonFlowAssetsInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonFlowAssetsInfo", mfCusPersonFlowAssetsInfo));
			ipage = mfCusPersonFlowAssetsInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonFlowAssetsInfoAction").getAddModel();
			}
			FormData formcusflowassets0002 = formService.getFormData(formId);
			getFormValue(formcusflowassets0002, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formcusflowassets0002)) {
				MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
				setObjValue(formcusflowassets0002, mfCusPersonFlowAssetsInfo);
				mfCusPersonFlowAssetsInfo.setAssetsId(WaterIdUtil.getWaterId());
				mfCusPersonFlowAssetsInfoFeign.insert(mfCusPersonFlowAssetsInfo);
				String cusNo = (String) map.get("cusNo");
				String relNo = mfCusPersonFlowAssetsInfo.getRelNo();
				mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
				mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
				mfCusPersonFlowAssetsInfo.setRelNo(relNo);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonFlowAssetsInfo.getCusNo(),
						mfCusPersonFlowAssetsInfo.getRelNo());// 更新资料完整度
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonFlowAssetsInfo", mfCusPersonFlowAssetsInfo));
				String tableHtml = jtu.getJsonStr("tablecusflowassetsBaseListBase", "tableTag",
						(List<MfCusPersonFlowAssetsInfo>) mfCusPersonFlowAssetsInfoFeign.findByPage(ipage).getResult(),
						null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String assetsId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonFlowAssetsInfoAction");
		String formId = "";
		// assetsType = "assetsType="+assetsType;
		// MfCusFormConfig mfCusFormConfig =
		// mfCusFormConfigFeign.getSubFormByCusType(mfCusCustomer.getCusType(),
		// "MfCusPersonFlowAssetsInfoAction",assetsType);
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonFlowAssetsInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
			mfCusPersonFlowAssetsInfo.setAssetsId(assetsId);
			mfCusPersonFlowAssetsInfo = mfCusPersonFlowAssetsInfoFeign.getById(mfCusPersonFlowAssetsInfo);
			FormData formcusflowassets0003 = formService.getFormData(formId);
			getObjValue(formcusflowassets0003, mfCusPersonFlowAssetsInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusflowassets0003, "propertySeeTag", "");
			if (mfCusPersonFlowAssetsInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusPersonFlowAssetsInfo);
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
	public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusflowassets0003 = formService.getFormData(formId);
		getFormValue(formcusflowassets0003, getMapByJson(ajaxData));
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfoJsp = new MfCusPersonFlowAssetsInfo();
		setObjValue(formcusflowassets0003, mfCusPersonFlowAssetsInfoJsp);
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = mfCusPersonFlowAssetsInfoFeign
				.getById(mfCusPersonFlowAssetsInfoJsp);
		if (mfCusPersonFlowAssetsInfo != null) {
			try {
				mfCusPersonFlowAssetsInfo = (MfCusPersonFlowAssetsInfo) EntityUtil.reflectionSetVal(
						mfCusPersonFlowAssetsInfo, mfCusPersonFlowAssetsInfoJsp, getMapByJson(ajaxData));

				if("4".equals(mfCusPersonFlowAssetsInfo.getAssetsType())){
					if(mfCusPersonFlowAssetsInfo.getQuantity()!=null&&mfCusPersonFlowAssetsInfo.getPurchasePrice()!=null){
						double nowPrice = MathExtend.multiply(mfCusPersonFlowAssetsInfo.getQuantity(),mfCusPersonFlowAssetsInfo.getPurchasePrice());
						mfCusPersonFlowAssetsInfo.setNowPrice(nowPrice);
					}
				}
				mfCusPersonFlowAssetsInfoFeign.update(mfCusPersonFlowAssetsInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		try {
			FormData formcusflowassetsBase = formService.getFormData("cusflowassetsBase");
			getFormValue(formcusflowassetsBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusflowassetsBase)) {
				setObjValue(formcusflowassetsBase, mfCusPersonFlowAssetsInfo);
				mfCusPersonFlowAssetsInfoFeign.update(mfCusPersonFlowAssetsInfo);

				String cusNo = mfCusPersonFlowAssetsInfo.getCusNo();
				mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonFlowAssetsInfo", mfCusPersonFlowAssetsInfo));
				String tableHtml = jtu.getJsonStr("tablecusflowassetsBaseListBase", "tableTag",
						(List<MfCusPersonFlowAssetsInfo>) mfCusPersonFlowAssetsInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusflowassets0002 = formService.getFormData("cusflowassets0002");
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		mfCusPersonFlowAssetsInfo.setAssetsId(assetsId);
		mfCusPersonFlowAssetsInfo = mfCusPersonFlowAssetsInfoFeign.getById(mfCusPersonFlowAssetsInfo);
		getObjValue(formcusflowassets0002, mfCusPersonFlowAssetsInfo, formData);
		if (mfCusPersonFlowAssetsInfo != null) {
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
	public Map<String, Object> deleteAjax(String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		mfCusPersonFlowAssetsInfo.setAssetsId(assetsId);
		try {
			mfCusPersonFlowAssetsInfoFeign.delete(mfCusPersonFlowAssetsInfo);
			dataMap.put("flag", "success");
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormData formcusflowassetsBase = null;
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
		mfCusPersonFlowAssetsInfo.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonFlowAssetsInfoAction");
		String formId = "";
		// assetsType = "assetsType=1";
		// MfCusFormConfig mfCusFormConfig =
		// mfCusFormConfigFeign.getSubFormByCusType(mfCusCustomer.getCusType(),
		// "MfCusPersonFlowAssetsInfoAction",assetsType);
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonFlowAssetsInfoAction表单信息没有查询到");
		} else {
			formcusflowassetsBase = formService.getFormData(formId);
			if (formcusflowassetsBase.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonFlowAssetsInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusflowassetsBase);
				mfCusPersonFlowAssetsInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusflowassetsBase, mfCusPersonFlowAssetsInfo);
			}
		}
		model.addAttribute("formcusflowassetsBase", formcusflowassetsBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonFlowAssetsInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-26 下午3:06:16
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormData formcusflowassets0002 = null;
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
		mfCusPersonFlowAssetsInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonFlowAssetsInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+kindNo+"的MfCusPersonFlowAssetsInfoAction表单信息没有查询到");
		} else {
			formcusflowassets0002 = formService.getFormData(formId);
			if (formcusflowassets0002.getFormId() == null) {
				// logger.error("产品类型为"+kindNo+"的MfCusPersonFlowAssetsInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusflowassets0002);
				mfCusPersonFlowAssetsInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusflowassets0002, mfCusPersonFlowAssetsInfo);
			}
		}
		model.addAttribute("formcusflowassets0002", formcusflowassets0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonFlowAssetsInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusflowassetsBase = formService.getFormData("cusflowassetsBase");
		getFormValue(formcusflowassetsBase);
		MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
		mfCusPersonFlowAssetsInfo.setAssetsId(assetsId);
		mfCusPersonFlowAssetsInfo = mfCusPersonFlowAssetsInfoFeign.getById(mfCusPersonFlowAssetsInfo);
		getObjValue(formcusflowassetsBase, mfCusPersonFlowAssetsInfo);
		model.addAttribute("formcusflowassetsBase", formcusflowassetsBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonFlowAssetsInfo_Detail";
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
		FormData formcusflowassets0002 = formService.getFormData("cusflowassets0002");
		getFormValue(formcusflowassets0002);
		boolean validateFlag = this.validateFormData(formcusflowassets0002);
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
		FormData formcusflowassets0002 = formService.getFormData("cusflowassets0002");
		getFormValue(formcusflowassets0002);
		boolean validateFlag = this.validateFormData(formcusflowassets0002);
	}

}

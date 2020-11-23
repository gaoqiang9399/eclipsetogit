package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.tech.oscache.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonCorp;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonCorpFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

/**
 * Title: MfCusPersonCorpAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jul 30 10:28:20 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonCorp")
public class MfCusPersonCorpController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonCorpBo
	@Autowired
	private MfCusPersonCorpFeign mfCusPersonCorpFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private YmlConfig ymlConfig;

	// 全局变量
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonCorp_List";
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
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		try {
			mfCusPersonCorp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonCorp.setCriteriaList(mfCusPersonCorp, ajaxData);// 我的筛选
			// mfCusPersonCorp.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusPersonCorp,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonCorp", mfCusPersonCorp));
			ipage = mfCusPersonCorpFeign.findByPage(ipage);
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

	/***
	 * 企业客户列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCustomerByPageAjax")
	@ResponseBody
	public Map<String, Object> findCustomerByPageAjax(String ajaxData, int pageSize, int pageNo, String cusNo)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		try {
			mfCusPersonCorp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonCorp.setCriteriaList(mfCusPersonCorp, ajaxData);// 我的筛选
			// mfCusPersonCorp.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusPersonCorp,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfCusPersonCorp.setCusNo(cusNo);
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			// 自定义查询Bo方法
			mfCusCustomer.setCustomQuery(ajaxData);
			ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
			ipage = mfCusCustomerFeign.findCorpCustomerByPage(ipage);
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonCorpAction").getAddModel();
			}
			FormData formcusPersonCorpDetail = formService.getFormData(formId);
			getFormValue(formcusPersonCorpDetail, map);
			if (this.validateFormData(formcusPersonCorpDetail)) {
				MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
				setObjValue(formcusPersonCorpDetail, mfCusPersonCorp);

				//判断该客户是否已经拥有社会信用代码
				MfCusPersonCorp temp =  new MfCusPersonCorp();
				temp.setCusNo(mfCusPersonCorp.getCusNo());
				temp.setIdNum(mfCusPersonCorp.getIdNum());
				temp.setDelFlag("0");//未删除
				List<MfCusPersonCorp> tempList =  mfCusPersonCorpFeign.getPersonCorpList(temp);
				if(tempList.size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "该社会信用代码已存在");
					return dataMap;
				}
				//判断结束

				mfCusPersonCorpFeign.insert(mfCusPersonCorp);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonCorp.getCusNo(),
						mfCusPersonCorp.getRelNo());// 更新客户信息完整度
				String cusNo = mfCusPersonCorp.getCusNo();
				String relNo = mfCusPersonCorp.getRelNo();
				mfCusPersonCorp.setCusNo(cusNo);
				mfCusPersonCorp.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonCorp", mfCusPersonCorp));
				String tableHtml = jtu.getJsonStr("tablecusPersonCorpBase", "tableTag",
						(List<MfCusPersonCorp>) mfCusPersonCorpFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusPersonCorpDetail = formService.getFormData(formId);
		getFormValue(formcusPersonCorpDetail, getMapByJson(ajaxData));
		MfCusPersonCorp mfCusPersonCorpNew = new MfCusPersonCorp();
		setObjValue(formcusPersonCorpDetail, mfCusPersonCorpNew);
		mfCusPersonCorp.setCorpId(mfCusPersonCorpNew.getCorpId());
		mfCusPersonCorp = mfCusPersonCorpFeign.getById(mfCusPersonCorp);
		if (mfCusPersonCorp != null) {
			try {
				mfCusPersonCorp = (MfCusPersonCorp) EntityUtil.reflectionSetVal(mfCusPersonCorp, mfCusPersonCorpNew,
						getMapByJson(ajaxData));
				mfCusPersonCorpFeign.update(mfCusPersonCorp);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
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
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		try {
			FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");
			getFormValue(formcusPersonCorpBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusPersonCorpBase)) {
				setObjValue(formcusPersonCorpBase, mfCusPersonCorp);
				mfCusPersonCorpFeign.update(mfCusPersonCorp);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonCorp", mfCusPersonCorp));
				String tableHtml = jtu.getJsonStr("tablecusPersonCorpBase", "tableTag",
						(List<MfCusPersonCorp>) mfCusPersonCorpFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String corpId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		mfCusPersonCorp.setCorpId(corpId);
		mfCusPersonCorp = mfCusPersonCorpFeign.getById(mfCusPersonCorp);
		getObjValue(formcusPersonCorpBase, mfCusPersonCorp, formData);
		if (mfCusPersonCorp != null) {
			dataMap.put("flag", "success");
			dataMap.put("mfCusPersonCorp", mfCusPersonCorp);
			String corpNature = mfCusPersonCorp.getCorpNature();
			Map<String, String> map = new CodeUtils().getMapByKeyName("CORP_TYPE");
			corpNature = map.get(corpNature);
			dataMap.put("corpNature", corpNature);
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX根据选择的客户号封装个人名下企业
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusPersonCorpInfoAjax")
	@ResponseBody
	public Map<String, Object> getCusPersonCorpInfoAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		mfCusPersonCorp.setCorpCusNo(cusNo);
		mfCusPersonCorp = mfCusPersonCorpFeign.getCusPersonCorpInfo(mfCusPersonCorp);
		if (mfCusPersonCorp != null) {
			dataMap.put("flag", "success");
			dataMap.put("mfCusPersonCorp", mfCusPersonCorp);
		} else {
			dataMap.put("flag", "error");
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
	public Map<String, Object> deleteAjax(String corpId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		mfCusPersonCorp.setCorpId(corpId);
		try {
			mfCusPersonCorpFeign.delete(mfCusPersonCorp);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		FormData formcusPersonCorpBase = null;
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfCusPersonCorp.setCusNo(cusNo);
		mfCusPersonCorp.setRelNo(cusNo);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonCorpAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonCorpAction表单信息没有查询到");
		} else {
			formcusPersonCorpBase = formService.getFormData(formId);
			if (formcusPersonCorpBase.getFormId() == null) {
				// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonCorpAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusPersonCorpBase);
				mfCusPersonCorp.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusPersonCorpBase, mfCusPersonCorp);
			}
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		model.addAttribute("formcusPersonCorpBase", formcusPersonCorpBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCorp_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-26 上午11:38:09
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");

		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		mfCusPersonCorp.setCusNo(cusNo);
		mfCusPersonCorp.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfCusPersonCorp.setCusName(mfCusCustomer.getCusName());
		mfCusPersonCorp.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonCorpAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("产品类型为"+kindNo+"的MfCusPersonCorpAction表单信息没有查询到");
		} else {
			formcusPersonCorpBase = formService.getFormData(formId);
			if (formcusPersonCorpBase.getFormId() == null) {
				// log.error("产品类型为"+kindNo+"的MfCusPersonCorpAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusPersonCorpBase);
				mfCusPersonCorp.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusPersonCorpBase, mfCusPersonCorp);
			}
		}
		model.addAttribute("formcusPersonCorpBase", formcusPersonCorpBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCorp_Insert";
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
		FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");
		getFormValue(formcusPersonCorpBase);
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		setObjValue(formcusPersonCorpBase, mfCusPersonCorp);
		mfCusPersonCorpFeign.insert(mfCusPersonCorp);
		getObjValue(formcusPersonCorpBase, mfCusPersonCorp);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonCorp", mfCusPersonCorp));
		List<MfCusPersonCorp> mfCusPersonCorpList = (List<MfCusPersonCorp>) mfCusPersonCorpFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusPersonCorpBase", formcusPersonCorpBase);
		model.addAttribute("formcusPersonCorpBase", formcusPersonCorpBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCorp_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String corpId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");
		getFormValue(formcusPersonCorpBase);
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		mfCusPersonCorp.setCorpId(corpId);
		mfCusPersonCorp = mfCusPersonCorpFeign.getById(mfCusPersonCorp);
		getObjValue(formcusPersonCorpBase, mfCusPersonCorp);
		model.addAttribute("formcusPersonCorpBase", formcusPersonCorpBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonCorp_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String corpId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
		mfCusPersonCorp.setCorpId(corpId);
		mfCusPersonCorpFeign.delete(mfCusPersonCorp);
		return getListPage(model);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String corpId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonCorpAction");
		String formId = "";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonCorpAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusPersonCorpDetail = formService.getFormData(formId);
			MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
			mfCusPersonCorp.setCorpId(corpId);
			mfCusPersonCorp = mfCusPersonCorpFeign.getById(mfCusPersonCorp);

			// 结束日期显示处理
			String endDate = mfCusCorpBaseInfoFeign.dealEndDate(mfCusPersonCorp.getEndDate(), "2");
			mfCusPersonCorp.setEndDate(endDate);

			getObjValue(formcusPersonCorpDetail, mfCusPersonCorp, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusPersonCorpDetail, "propertySeeTag", "");
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
			dataMap.put("formData", mfCusPersonCorp);
		}
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
		FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");
		getFormValue(formcusPersonCorpBase);
		boolean validateFlag = this.validateFormData(formcusPersonCorpBase);
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
		FormData formcusPersonCorpBase = formService.getFormData("cusPersonCorpBase");
		getFormValue(formcusPersonCorpBase);
		boolean validateFlag = this.validateFormData(formcusPersonCorpBase);
	}

}

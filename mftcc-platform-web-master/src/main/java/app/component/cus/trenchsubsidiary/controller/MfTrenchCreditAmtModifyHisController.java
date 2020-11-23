package app.component.cus.trenchsubsidiary.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.trenchsubsidiary.entity.MfTrenchCreditAmtModifyHis;
import app.component.cus.trenchsubsidiary.feign.MfTrenchCreditAmtModifyHisFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfTrenchCreditAmtModifyHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 06 15:02:30 CST 2018
 **/
@Controller
@RequestMapping("/mfTrenchCreditAmtModifyHis")
public class MfTrenchCreditAmtModifyHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private MfTrenchCreditAmtModifyHisFeign mfTrenchCreditAmtModifyHisFeign;

	@Autowired
	private MfBusTrenchFeign mfBusTrenchFeign;
	@Autowired
	private MfBusAgenciesFeign mfBusAgenciesFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);

		return "/component/cus/trenchsubsidiary/MfTrenchCreditAmtModifyHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, String tableId, String tableType, String ajaxData)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		try {
			mfTrenchCreditAmtModifyHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfTrenchCreditAmtModifyHis.setCriteriaList(mfTrenchCreditAmtModifyHis, ajaxData);// 我的筛选
			// mfTrenchCreditAmtModifyHis.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfTrenchCreditAmtModifyHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfTrenchCreditAmtModifyHis", mfTrenchCreditAmtModifyHis));
			ipage = mfTrenchCreditAmtModifyHisFeign.findByPage(ipage);
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

		try {
			FormData formTrenchAmtModify0001 = new FormService().getFormData("TrenchAmtModify0001");
			getFormValue(formTrenchAmtModify0001, getMapByJson(ajaxData));
			if (this.validateFormData(formTrenchAmtModify0001)) {
				MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
				setObjValue(formTrenchAmtModify0001, mfTrenchCreditAmtModifyHis);
				mfTrenchCreditAmtModifyHisFeign.insert(mfTrenchCreditAmtModifyHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAgenciesAjax")
	@ResponseBody
	public Map<String, Object> insertAgenciesAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			FormData formTrenchAmtModify0001 = new FormService().getFormData("AgenciesAmtModify0001");
			getFormValue(formTrenchAmtModify0001, getMapByJson(ajaxData));
			if (this.validateFormData(formTrenchAmtModify0001)) {
				MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
				setObjValue(formTrenchAmtModify0001, mfTrenchCreditAmtModifyHis);
				mfTrenchCreditAmtModifyHisFeign.insertAgencies(mfTrenchCreditAmtModifyHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formTrenchAmtModify0002 = new FormService().getFormData("TrenchAmtModify0002");
		getFormValue(formTrenchAmtModify0002, getMapByJson(ajaxData));
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHisJsp = new MfTrenchCreditAmtModifyHis();
		setObjValue(formTrenchAmtModify0002, mfTrenchCreditAmtModifyHisJsp);
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = mfTrenchCreditAmtModifyHisFeign
				.getById(mfTrenchCreditAmtModifyHisJsp);
		if (mfTrenchCreditAmtModifyHis != null) {
			try {
				mfTrenchCreditAmtModifyHis = (MfTrenchCreditAmtModifyHis) EntityUtil.reflectionSetVal(
						mfTrenchCreditAmtModifyHis, mfTrenchCreditAmtModifyHisJsp, getMapByJson(ajaxData));
				mfTrenchCreditAmtModifyHisFeign.update(mfTrenchCreditAmtModifyHis);
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		try {
			FormData formTrenchAmtModify0002 = new FormService().getFormData("TrenchAmtModify0002");
			getFormValue(formTrenchAmtModify0002, getMapByJson(ajaxData));
			if (this.validateFormData(formTrenchAmtModify0002)) {
				mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
				setObjValue(formTrenchAmtModify0002, mfTrenchCreditAmtModifyHis);
				mfTrenchCreditAmtModifyHisFeign.update(mfTrenchCreditAmtModifyHis);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formTrenchAmtModify0002 = new FormService().getFormData("TrenchAmtModify0002");
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		mfTrenchCreditAmtModifyHis.setId(id);
		mfTrenchCreditAmtModifyHis = mfTrenchCreditAmtModifyHisFeign.getById(mfTrenchCreditAmtModifyHis);
		getObjValue(formTrenchAmtModify0002, mfTrenchCreditAmtModifyHis, formData);
		if (mfTrenchCreditAmtModifyHis != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		mfTrenchCreditAmtModifyHis.setId(id);
		try {
			mfTrenchCreditAmtModifyHisFeign.delete(mfTrenchCreditAmtModifyHis);
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
	public String input(Model model, String trenchUid) throws Exception {
		ActionContext.initialize(request, response);

		FormData formTrenchAmtModify0001 = new FormService().getFormData("TrenchAmtModify0001");

		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchUid(trenchUid);
		mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrench);

		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		mfTrenchCreditAmtModifyHis.setTrenchUid(trenchUid);
		mfTrenchCreditAmtModifyHis.setTrenchName(mfBusTrench.getTrenchName());
		mfTrenchCreditAmtModifyHis.setAssureRate(mfBusTrench.getAssureRate());
		mfTrenchCreditAmtModifyHis.setCreditAmt(mfBusTrench.getCreditAmt());
		mfTrenchCreditAmtModifyHis.setCreditBal(mfBusTrench.getCreditBal());
		mfTrenchCreditAmtModifyHis.setAssureAmt(mfBusTrench.getAssureAmt());
		getObjValue(formTrenchAmtModify0001, mfTrenchCreditAmtModifyHis);

		model.addAttribute("formTrenchAmtModify0001", formTrenchAmtModify0001);
		model.addAttribute("mfTrenchCreditAmtModifyHis", mfTrenchCreditAmtModifyHis);
		model.addAttribute("query", "");

		return "/component/cus/trenchsubsidiary/MfTrenchCreditAmtModifyHis_Insert";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAgencies")
	public String inputAgencies(Model model, String trenchUid) throws Exception {
		ActionContext.initialize(request, response);

		FormData formTrenchAmtModify0001 = new FormService().getFormData("AgenciesAmtModify0001");

		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		mfBusAgencies.setAgenciesUid(trenchUid);
		mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);

		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		mfTrenchCreditAmtModifyHis.setTrenchUid(trenchUid);
		mfTrenchCreditAmtModifyHis.setTrenchName(mfBusAgencies.getAgenciesName());
		mfTrenchCreditAmtModifyHis.setCreditAmt(mfBusAgencies.getCreditAmt());
		mfTrenchCreditAmtModifyHis.setCreditBal(mfBusAgencies.getCreditBal());
		mfTrenchCreditAmtModifyHis.setCreditCircleFlag(mfBusAgencies.getCreditCircleFlag());
		getObjValue(formTrenchAmtModify0001, mfTrenchCreditAmtModifyHis);

		model.addAttribute("formTrenchAmtModify0001", formTrenchAmtModify0001);
		model.addAttribute("mfTrenchCreditAmtModifyHis", mfTrenchCreditAmtModifyHis);
		model.addAttribute("query", "");

		return "/component/cus/trenchsubsidiary/MfAgenciesCreditAmtModifyHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);

		FormData formTrenchAmtModify0001 = new FormService().getFormData("TrenchAmtModify0001");
		getFormValue(formTrenchAmtModify0001);
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		mfTrenchCreditAmtModifyHis.setId(id);
		mfTrenchCreditAmtModifyHis = mfTrenchCreditAmtModifyHisFeign.getById(mfTrenchCreditAmtModifyHis);
		getObjValue(formTrenchAmtModify0001, mfTrenchCreditAmtModifyHis);

		model.addAttribute("formTrenchAmtModify0001", formTrenchAmtModify0001);
		model.addAttribute("mfTrenchCreditAmtModifyHis", mfTrenchCreditAmtModifyHis);
		model.addAttribute("mfTrenchCreditAmtModifyHis", mfTrenchCreditAmtModifyHis);
		model.addAttribute("query", "");

		return "/component/cus/trenchsubsidiary/MfTrenchCreditAmtModifyHis_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formTrenchAmtModify0002 = new FormService().getFormData("TrenchAmtModify0002");
		getFormValue(formTrenchAmtModify0002);
		this.validateFormData(formTrenchAmtModify0002);

		throw new RuntimeException("tmp 这个有调用？？？？");
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formTrenchAmtModify0002 = new FormService().getFormData("TrenchAmtModify0002");
		getFormValue(formTrenchAmtModify0002);
		this.validateFormData(formTrenchAmtModify0002);

		throw new RuntimeException("tmp 这个有调用？？？？");
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String query = "query";
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData TrenchAmtModify0002 = formService.getFormData("TrenchAmtModify0002");
		MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis = new MfTrenchCreditAmtModifyHis();
		mfTrenchCreditAmtModifyHis.setId(id);
		mfTrenchCreditAmtModifyHis = mfTrenchCreditAmtModifyHisFeign.getById(mfTrenchCreditAmtModifyHis);
		getObjValue(TrenchAmtModify0002, mfTrenchCreditAmtModifyHis);
		String htmlStrCorp = jsonFormUtil.getJsonStr(TrenchAmtModify0002, "propertySeeTag", query);
		if (mfTrenchCreditAmtModifyHis != null) {
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfTrenchCreditAmtModifyHis);

		return dataMap;
	}
}

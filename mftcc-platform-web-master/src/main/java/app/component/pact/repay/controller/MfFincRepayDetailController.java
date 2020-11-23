package app.component.pact.repay.controller;

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
import app.component.pact.repay.entity.MfFincRepayDetail;
import app.component.pact.repay.feign.MfFincRepayDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfFincRepayDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 09:31:07 CST 2016
 **/
@Controller
@RequestMapping("/mfFincRepayDetail")
public class MfFincRepayDetailController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfFincRepayDetailBo
	@Autowired
	private MfFincRepayDetailFeign mfFincRepayDetailFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/pact/repay/MfFincRepayDetail_List";
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
		MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
		try {
			mfFincRepayDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFincRepayDetail.setCriteriaList(mfFincRepayDetail, ajaxData);// 我的筛选
			// this.getRoleConditions(mfFincRepayDetail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfFincRepayDetailFeign.findByPage(ipage, mfFincRepayDetail);
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
			FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
			getFormValue(formfincrepaydetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfincrepaydetail0002)) {
				MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
				setObjValue(formfincrepaydetail0002, mfFincRepayDetail);
				mfFincRepayDetailFeign.insert(mfFincRepayDetail);
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
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
		getFormValue(formfincrepaydetail0002, getMapByJson(ajaxData));
		MfFincRepayDetail mfFincRepayDetailJsp = new MfFincRepayDetail();
		setObjValue(formfincrepaydetail0002, mfFincRepayDetailJsp);
		MfFincRepayDetail mfFincRepayDetail = mfFincRepayDetailFeign.getById(mfFincRepayDetailJsp);
		if (mfFincRepayDetail != null) {
			try {
				mfFincRepayDetail = (MfFincRepayDetail) EntityUtil.reflectionSetVal(mfFincRepayDetail,
						mfFincRepayDetailJsp, getMapByJson(ajaxData));
				mfFincRepayDetailFeign.update(mfFincRepayDetail);
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
			FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
			getFormValue(formfincrepaydetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfincrepaydetail0002)) {
				MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
				setObjValue(formfincrepaydetail0002, mfFincRepayDetail);
				mfFincRepayDetailFeign.update(mfFincRepayDetail);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String repayDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
		MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
		mfFincRepayDetail.setRepayDetailId(repayDetailId);
		mfFincRepayDetail = mfFincRepayDetailFeign.getById(mfFincRepayDetail);
		getObjValue(formfincrepaydetail0002, mfFincRepayDetail, formData);
		if (mfFincRepayDetail != null) {
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
	public Map<String, Object> deleteAjax(String repayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
		mfFincRepayDetail.setRepayDetailId(repayDetailId);
		try {
			mfFincRepayDetailFeign.delete(mfFincRepayDetail);
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
		FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
		model.addAttribute("formfincrepaydetail0002", formfincrepaydetail0002);
		model.addAttribute("query", "");
		return "component/pact/repay/MfFincRepayDetail_Insert";
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
		FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
		getFormValue(formfincrepaydetail0002);
		MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
		setObjValue(formfincrepaydetail0002, mfFincRepayDetail);
		mfFincRepayDetailFeign.insert(mfFincRepayDetail);
		getObjValue(formfincrepaydetail0002, mfFincRepayDetail);
		this.addActionMessage(model, "保存成功");
		List<MfFincRepayDetail> mfFincRepayDetailList = (List<MfFincRepayDetail>) mfFincRepayDetailFeign
				.findByPage(this.getIpage(), mfFincRepayDetail).getResult();
		model.addAttribute("formfincrepaydetail0002", formfincrepaydetail0002);
		model.addAttribute("mfFincRepayDetail", mfFincRepayDetail);
		model.addAttribute("mfFincRepayDetailList", mfFincRepayDetailList);
		model.addAttribute("query", "");
		return "component/pact/repay/MfFincRepayDetail_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String repayDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfincrepaydetail0001 = formService.getFormData("fincrepaydetail0001");
		getFormValue(formfincrepaydetail0001);
		MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
		mfFincRepayDetail.setRepayDetailId(repayDetailId);
		mfFincRepayDetail = mfFincRepayDetailFeign.getById(mfFincRepayDetail);
		getObjValue(formfincrepaydetail0001, mfFincRepayDetail);
		model.addAttribute("formfincrepaydetail0001", formfincrepaydetail0001);
		model.addAttribute("mfFincRepayDetail", mfFincRepayDetail);
		model.addAttribute("query", "");
		return "component/pact/repay/MfFincRepayDetail_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String repayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		MfFincRepayDetail mfFincRepayDetail = new MfFincRepayDetail();
		mfFincRepayDetail.setRepayDetailId(repayDetailId);
		mfFincRepayDetailFeign.delete(mfFincRepayDetail);
		return getListPage(model);
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
		FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
		getFormValue(formfincrepaydetail0002);
		boolean validateFlag = this.validateFormData(formfincrepaydetail0002);
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
		FormData formfincrepaydetail0002 = formService.getFormData("fincrepaydetail0002");
		getFormValue(formfincrepaydetail0002);
		boolean validateFlag = this.validateFormData(formfincrepaydetail0002);
	}

}

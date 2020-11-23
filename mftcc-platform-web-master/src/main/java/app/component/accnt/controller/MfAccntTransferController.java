package app.component.accnt.controller;

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

import app.base.User;
import app.component.accnt.entity.MfAccntRepayDetail;
import app.component.accnt.entity.MfAccntTransfer;
import app.component.accnt.entity.MfAdjustRecord;
import app.component.accnt.feign.MfAccntRepayDetailFeign;
import app.component.accnt.feign.MfAccntTransferFeign;
import app.component.accnt.feign.MfAdjustRecordFeign;
import app.component.common.EntityUtil;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfAccntTransferAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 25 17:50:56 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfAccntTransfer")
public class MfAccntTransferController extends BaseFormBean {

	// 注入MfAccntTransferBo
	@Autowired
	private MfAccntTransferFeign mfAccntTransferFeign;
	@Autowired
	private MfAccntRepayDetailFeign mfAccntRepayDetailFeign;
	@Autowired
	private MfAdjustRecordFeign mfAdjustRecordFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String pactId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setPactId(pactId);
		List<MfAccntTransfer> mfAccntTransferList = mfAccntTransferFeign.getList(mfAccntTransfer);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mfAccntTransferList", mfAccntTransferList);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("formaccnttrans0002", formaccnttrans0002);
		return "MfAccntTransfer_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, int pageNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		try {
			mfAccntTransfer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAccntTransfer.setCriteriaList(mfAccntTransfer, ajaxData);// 我的筛选
			// this.getRoleConditions(mfAccntTransfer,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAccntTransferFeign.findByPage(ipage, mfAccntTransfer);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
			getFormValue(formaccnttrans0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccnttrans0002)) {
				MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
				setObjValue(formaccnttrans0002, mfAccntTransfer);
				mfAccntTransferFeign.insert(mfAccntTransfer);
				getTableData(tableId, mfAccntTransfer, dataMap);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		getFormValue(formaccnttrans0002, getMapByJson(ajaxData));
		MfAccntTransfer mfAccntTransferJsp = new MfAccntTransfer();
		setObjValue(formaccnttrans0002, mfAccntTransferJsp);
		MfAccntTransfer mfAccntTransfer = mfAccntTransferFeign.getById(mfAccntTransferJsp);
		if (mfAccntTransfer != null) {
			try {
				mfAccntTransfer = (MfAccntTransfer) EntityUtil.reflectionSetVal(mfAccntTransfer, mfAccntTransferJsp,
						getMapByJson(ajaxData));
				mfAccntTransferFeign.update(mfAccntTransfer);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		try {
			FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
			getFormValue(formaccnttrans0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccnttrans0002)) {
				mfAccntTransfer = new MfAccntTransfer();
				setObjValue(formaccnttrans0002, mfAccntTransfer);
				mfAccntTransferFeign.update(mfAccntTransfer);
				getTableData(tableId, mfAccntTransfer, dataMap);
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String transferId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		mfAccntTransfer = mfAccntTransferFeign.getById(mfAccntTransfer);
		getObjValue(formaccnttrans0002, mfAccntTransfer, formData);
		if (mfAccntTransfer != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String transferId, String tableId) throws Exception {
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		try {
			mfAccntTransferFeign.delete(mfAccntTransfer);
			getTableData(tableId, mfAccntTransfer, dataMap);
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

	@ResponseBody
	@RequestMapping(value = "/deleteAjax1")
	public Map<String, Object> deleteAjax1(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		getFormValue(formaccnttrans0002, getMapByJson(ajaxData));
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		setObjValue(formaccnttrans0002, mfAccntTransfer);
		try {
			mfAccntTransferFeign.delete(mfAccntTransfer);
			getTableData(tableId, mfAccntTransfer, dataMap);
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
	public String input(Model model, String pactId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setPactId(pactId);
		// set
		getObjValue(formaccnttrans0002, mfAccntTransfer);
		model.addAttribute("formaccnttrans0002", formaccnttrans0002);
		return "MfAccntTransfer_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		getFormValue(formaccnttrans0002);
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		setObjValue(formaccnttrans0002, mfAccntTransfer);
		mfAccntTransferFeign.insert(mfAccntTransfer);
		getObjValue(formaccnttrans0002, mfAccntTransfer);
		this.addActionMessage(model, "保存成功");
		List<MfAccntTransfer> mfAccntTransferList = (List<MfAccntTransfer>) mfAccntTransferFeign
				.findByPage(this.getIpage(), mfAccntTransfer).getResult();
		model.addAttribute("mfAccntTransferList", mfAccntTransferList);
		model.addAttribute("formaccnttrans0002", formaccnttrans0002);
		return "MfAccntTransfer_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String transferId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttrans0001 = formService.getFormData("accnttrans0001");
		getFormValue(formaccnttrans0001);
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		mfAccntTransfer = mfAccntTransferFeign.getById(mfAccntTransfer);
		getObjValue(formaccnttrans0001, mfAccntTransfer);

		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		mfAdjustRecord.setTransferId(transferId);
		List<MfAdjustRecord> mfAdjustRecordList = mfAdjustRecordFeign.getList(mfAdjustRecord);

		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setTransferId(transferId);
		List<MfAccntRepayDetail> mfAccntRepayDetailList = mfAccntRepayDetailFeign.getList(mfAccntRepayDetail);

		model.addAttribute("formaccnttrans0001", formaccnttrans0001);
		model.addAttribute("mfAdjustRecordList", mfAdjustRecordList);
		model.addAttribute("mfAccntRepayDetailList", mfAccntRepayDetailList);
		return "MfAccntTransfer_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String transferId) throws Exception {
		ActionContext.initialize(request, response);
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		mfAccntTransferFeign.delete(mfAccntTransfer);
		return getListPage(model, transferId);
	}

	@ResponseBody
	@RequestMapping(value = "/inProcess")
	public Map<String, Object> inProcess(String transferId, String pactId, String tableId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		// mfAccntTransfer.setTransferSts("2");
		try {
			mfAccntTransferFeign.doInProcess(mfAccntTransfer);
			mfAccntTransferFeign.update(mfAccntTransfer);
			mfAccntTransfer.setPactId(pactId);
			getTableData(tableId, mfAccntTransfer, dataMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String transferId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		mfAccntTransfer = mfAccntTransferFeign.getById(mfAccntTransfer);
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		getFormValue(formaccnttrans0002);
		getObjValue(formaccnttrans0002, mfAccntTransfer);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("1", "同意");
		dataMap.put("3", "退回上一环节");
		dataMap.put("4", "退回初审");
		dataMap.put("2", "否决");
		return "viewPoint";
	}

	@ResponseBody
	@RequestMapping(value = "/commitProcess")
	public Map<String, Object> commitProcess(String transferId, String ajaxData, String taskId, String opinionType,
			String approvalOpinion, String transition, String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
			getFormValue(formaccnttrans0002, getMapByJson(ajaxData));
			MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
			setObjValue(formaccnttrans0002, mfAccntTransfer);

			mfAccntTransfer.setTransferId(transferId);
			mfAccntTransfer.setCurrentSessionOrgNo(User.getOrgNo(request));
			Result result = mfAccntTransferFeign.doCommit(taskId, transferId, opinionType, approvalOpinion, transition,
					User.getRegNo(request), nextUser, mfAccntTransfer);

			if (result != null) {

				if (result.isSuccess()) {
					dataMap.put("flag", "success");
				} else {
					dataMap.put("flag", "error");
				}
			} else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	private void getTableData(String tableId, MfAccntTransfer mfAccntTransferThis, Map<String, Object> dataMap)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		// MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfAccntTransferFeign.getList(mfAccntTransferThis), null,
				true);
		dataMap.put("tableData", tableHtml);
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
		FormService formService = new FormService();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		getFormValue(formaccnttrans0002);
		boolean validateFlag = this.validateFormData(formaccnttrans0002);
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
		FormService formService = new FormService();
		FormData formaccnttrans0002 = formService.getFormData("accnttrans0002");
		getFormValue(formaccnttrans0002);
		boolean validateFlag = this.validateFormData(formaccnttrans0002);
	}

}

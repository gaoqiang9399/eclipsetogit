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

import app.component.accnt.entity.MfAccntTransfer;
import app.component.accnt.entity.MfAdjustRecord;
import app.component.accnt.feign.MfAccntTransferFeign;
import app.component.accnt.feign.MfAdjustRecordFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfAdjustRecordAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 04 17:14:31 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfAdjustRecord")
public class MfAdjustRecordController extends BaseFormBean {
	// 注入MfAdjustRecordBo
	@Autowired
	private MfAdjustRecordFeign mfAdjustRecordFeign;
	@Autowired
	private MfAccntTransferFeign mfAccntTransferFeign;

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
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/accnt/MfAdjustRecord_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		try {
			mfAdjustRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAdjustRecord.setCriteriaList(mfAdjustRecord, ajaxData);// 我的筛选
			// this.getRoleConditions(mfAdjustRecord,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAdjustRecordFeign.findByPage(ipage, mfAdjustRecord);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
			getFormValue(formaccntadjust0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccntadjust0002)) {
				MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
				setObjValue(formaccntadjust0002, mfAdjustRecord);
				mfAdjustRecordFeign.insert(mfAdjustRecord);
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
		FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
		getFormValue(formaccntadjust0002, getMapByJson(ajaxData));
		MfAdjustRecord mfAdjustRecordJsp = new MfAdjustRecord();
		setObjValue(formaccntadjust0002, mfAdjustRecordJsp);
		MfAdjustRecord mfAdjustRecord = mfAdjustRecordFeign.getById(mfAdjustRecordJsp);
		if (mfAdjustRecord != null) {
			try {
				mfAdjustRecord = (MfAdjustRecord) EntityUtil.reflectionSetVal(mfAdjustRecord, mfAdjustRecordJsp,
						getMapByJson(ajaxData));
				mfAdjustRecordFeign.update(mfAdjustRecord);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		try {
			FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
			getFormValue(formaccntadjust0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccntadjust0002)) {
				mfAdjustRecord = new MfAdjustRecord();
				setObjValue(formaccntadjust0002, mfAdjustRecord);
				mfAdjustRecordFeign.update(mfAdjustRecord);
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
	public Map<String, Object> getByIdAjax(String adjustId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		mfAdjustRecord.setAdjustId(adjustId);
		mfAdjustRecord = mfAdjustRecordFeign.getById(mfAdjustRecord);
		getObjValue(formaccntadjust0002, mfAdjustRecord, formData);
		if (mfAdjustRecord != null) {
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
	public Map<String, Object> deleteAjax(String adjustId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		mfAdjustRecord.setAdjustId(adjustId);
		try {
			mfAdjustRecordFeign.delete(mfAdjustRecord);
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
	public String input(Model model,String transferId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");

		MfAccntTransfer mfAccntTransfer = new MfAccntTransfer();
		mfAccntTransfer.setTransferId(transferId);
		mfAccntTransfer = mfAccntTransferFeign.getById(mfAccntTransfer);

		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		mfAdjustRecord.setTransferId(transferId);
		mfAdjustRecord.setBeforeAdjustAmt(mfAccntTransfer.getTransferAmt() - mfAccntTransfer.getAdjustAmt());
		getObjValue(formaccntadjust0002, mfAdjustRecord);
		
		model.addAttribute("formaccntadjust0002", formaccntadjust0002);
		return "/component/accnt/MfAdjustRecord_Insert";
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
		FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
		getFormValue(formaccntadjust0002);
		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		setObjValue(formaccntadjust0002, mfAdjustRecord);
		mfAdjustRecordFeign.insert(mfAdjustRecord);
		getObjValue(formaccntadjust0002, mfAdjustRecord);
		this.addActionMessage(model,"保存成功");
		List<MfAdjustRecord> mfAdjustRecordList = (List<MfAdjustRecord>) mfAdjustRecordFeign.findByPage(this.getIpage(), mfAdjustRecord)
				.getResult();
		
		model.addAttribute("formaccntadjust0002", formaccntadjust0002);
		model.addAttribute("mfAdjustRecordList", mfAdjustRecordList);
		return "/component/accnt/MfAdjustRecord_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String adjustId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccntadjust0001 = formService.getFormData("accntadjust0001");
		getFormValue(formaccntadjust0001);

		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		mfAdjustRecord.setAdjustId(adjustId);

		mfAdjustRecord = mfAdjustRecordFeign.getById(mfAdjustRecord);
		getObjValue(formaccntadjust0001, mfAdjustRecord);
		
		model.addAttribute("formaccntadjust0001", formaccntadjust0001);
		return "/component/accnt/MfAdjustRecord_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String adjustId) throws Exception {
		ActionContext.initialize(request, response);
		MfAdjustRecord mfAdjustRecord = new MfAdjustRecord();
		mfAdjustRecord.setAdjustId(adjustId);
		mfAdjustRecordFeign.delete(mfAdjustRecord);
		return getListPage();
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
		FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
		getFormValue(formaccntadjust0002);
		boolean validateFlag = this.validateFormData(formaccntadjust0002);
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
		FormData formaccntadjust0002 = formService.getFormData("accntadjust0002");
		getFormValue(formaccntadjust0002);
		boolean validateFlag = this.validateFormData(formaccntadjust0002);
	}

}

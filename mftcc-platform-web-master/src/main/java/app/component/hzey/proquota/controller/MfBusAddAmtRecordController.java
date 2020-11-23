package app.component.hzey.proquota.controller;

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
import app.component.hzey.proquota.entity.MfBusAddAmtRecord;
import app.component.hzey.proquota.feign.MfBusAddAmtRecordFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfBusAddAmtRecordAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 30 15:14:39 CST 2017
 **/
@Controller
@RequestMapping("/mfBusAddAmtRecord")
public class MfBusAddAmtRecordController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusAddAmtRecordBo
	@Autowired
	private MfBusAddAmtRecordFeign mfBusAddAmtRecordFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPactListPage")
	public String getPactListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray overdueStsJsonArray = JSONArray
				.fromObject("[{\"optCode\":\"0\",\"optName\":\"无逾期\"},{\"optCode\":\"1\",\"optName\":\"有逾期\"}]");
		model.addAttribute("overdueStsJsonArray", overdueStsJsonArray);
		return "/component/hzey/proquota/MfAppBatchAddAmount_List";
	}

	/**
	 * 弹出输入金额页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAmt")
	public String inputAmt(Model model, String pactNoList) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("pactNoList", pactNoList);
		return "/component/hzey/proquota/MfAppBatchAddAmount_Input";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String pactNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("pactNo", pactNo);
		return "/component/hzey/proquota/MfBusAddAmtRecord_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String pactNo, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
		try {
			mfBusAddAmtRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusAddAmtRecord.setCriteriaList(mfBusAddAmtRecord, ajaxData);// 我的筛选
			// mfBusAddAmtRecord.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfBusAddAmtRecord,"1000000001");//记录级权限控制方法
			mfBusAddAmtRecord.setPactNo(pactNo);
			mfBusAddAmtRecord.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusAddAmtRecord",mfBusAddAmtRecord));
			// 自定义查询Bo方法
			ipage = mfBusAddAmtRecordFeign.findByPage(ipage);
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
			FormData formproquota0002 = formService.getFormData("proquota0002");
			getFormValue(formproquota0002, getMapByJson(ajaxData));
			if (this.validateFormData(formproquota0002)) {
				MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
				setObjValue(formproquota0002, mfBusAddAmtRecord);
				mfBusAddAmtRecordFeign.insert(mfBusAddAmtRecord);
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
			throw new Exception(e.getMessage());
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
		FormData formproquota0002 = formService.getFormData("proquota0002");
		getFormValue(formproquota0002, getMapByJson(ajaxData));
		MfBusAddAmtRecord mfBusAddAmtRecordJsp = new MfBusAddAmtRecord();
		setObjValue(formproquota0002, mfBusAddAmtRecordJsp);
		MfBusAddAmtRecord mfBusAddAmtRecord = mfBusAddAmtRecordFeign.getById(mfBusAddAmtRecordJsp);
		if (mfBusAddAmtRecord != null) {
			try {
				mfBusAddAmtRecord = (MfBusAddAmtRecord) EntityUtil.reflectionSetVal(mfBusAddAmtRecord,
						mfBusAddAmtRecordJsp, getMapByJson(ajaxData));
				mfBusAddAmtRecordFeign.update(mfBusAddAmtRecord);
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
		try {
			FormData formproquota0002 = formService.getFormData("proquota0002");
			getFormValue(formproquota0002, getMapByJson(ajaxData));
			if (this.validateFormData(formproquota0002)) {
				MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
				setObjValue(formproquota0002, mfBusAddAmtRecord);
				mfBusAddAmtRecordFeign.update(mfBusAddAmtRecord);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formproquota0002 = formService.getFormData("proquota0002");
		MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
		mfBusAddAmtRecord.setId(id);
		mfBusAddAmtRecord = mfBusAddAmtRecordFeign.getById(mfBusAddAmtRecord);
		getObjValue(formproquota0002, mfBusAddAmtRecord, formData);
		if (mfBusAddAmtRecord != null) {
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
		MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
		mfBusAddAmtRecord.setId(id);
		try {
			mfBusAddAmtRecordFeign.delete(mfBusAddAmtRecord);
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
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formproquota0002 = formService.getFormData("proquota0002");
		model.addAttribute("formproquota0002", formproquota0002);
		model.addAttribute("query", "");
		return "/component/hzey/proquota/MfBusAddAmtRecord_Insert";
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
		FormData formproquota0002 = formService.getFormData("proquota0002");
		getFormValue(formproquota0002);
		MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
		setObjValue(formproquota0002, mfBusAddAmtRecord);
		mfBusAddAmtRecordFeign.insert(mfBusAddAmtRecord);
		getObjValue(formproquota0002, mfBusAddAmtRecord);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfBusAddAmtRecord",mfBusAddAmtRecord));
		List<MfBusAddAmtRecord> mfBusAddAmtRecordList = (List<MfBusAddAmtRecord>) mfBusAddAmtRecordFeign
				.findByPage(ipage).getResult();
		model.addAttribute("mfBusAddAmtRecordList", mfBusAddAmtRecordList);
		model.addAttribute("formproquota0002", formproquota0002);
		model.addAttribute("query", "");
		return "/component/hzey/proquota/MfBusAddAmtRecord_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formproquota0002 = formService.getFormData("proquota0001");
		getFormValue(formproquota0002);
		MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
		mfBusAddAmtRecord.setId(id);
		mfBusAddAmtRecord = mfBusAddAmtRecordFeign.getById(mfBusAddAmtRecord);
		getObjValue(formproquota0002, mfBusAddAmtRecord);
		model.addAttribute("formproquota0002", formproquota0002);
		model.addAttribute("query", "");
		return "/component/hzey/proquota/MfBusAddAmtRecord_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id, String pactNo) throws Exception {
		ActionContext.initialize(request, response);
		MfBusAddAmtRecord mfBusAddAmtRecord = new MfBusAddAmtRecord();
		mfBusAddAmtRecord.setId(id);
		mfBusAddAmtRecordFeign.delete(mfBusAddAmtRecord);
		return getListPage(model, pactNo);
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
		FormData formproquota0002 = formService.getFormData("proquota0002");
		getFormValue(formproquota0002);
		boolean validateFlag = this.validateFormData(formproquota0002);
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
		FormData formproquota0002 = formService.getFormData("proquota0002");
		getFormValue(formproquota0002);
		boolean validateFlag = this.validateFormData(formproquota0002);
	}

	/**
	 * 记录批量提额历史，并更新合同金额及借据的合同金额
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBatchAjax")
	@ResponseBody
	public Map<String, Object> insertBatchAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		String addAmt = request.getParameter("addAmt");
		String pactNoList = request.getParameter("pactNoList");
		this.getHttpRequest().setAttribute("pactNoList", pactNoList);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isEmpty(addAmt) || StringUtil.isEmpty(pactNoList)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "参数为空！");
			} else {
				// 自定义查询Bo方法
				mfBusAddAmtRecordFeign.insertBatch(addAmt, pactNoList);
				dataMap.put("flag", "success");
				dataMap.put("msg", "提升额度成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}

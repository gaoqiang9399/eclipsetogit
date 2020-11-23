package app.component.collateral.controller;

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

import app.component.collateral.entity.MfPledgeStatus;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfPledgeStatusFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfPledgeStatusController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 18:20:47 CST 2017
 **/
@Controller
@RequestMapping("/mfPledgeStatus")
public class MfPledgeStatusController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfPledgeStatusFeign mfPledgeStatusFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfPledgeStatus_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		try {
			mfPledgeStatus.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfPledgeStatus.setCriteriaList(mfPledgeStatus, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPledgeStatus,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfPledgeStatusFeign.findByPage(ipage, mfPledgeStatus);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpledgestatus0002 = formService.getFormData("pledgestatus0002");
			getFormValue(formpledgestatus0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpledgestatus0002)) {
				MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
				setObjValue(formpledgestatus0002, mfPledgeStatus);
				mfPledgeStatusFeign.insert(mfPledgeStatus);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpledgestatus0002 = formService.getFormData("pledgestatus0002");
		getFormValue(formpledgestatus0002, getMapByJson(ajaxData));
		MfPledgeStatus mfPledgeStatusJsp = new MfPledgeStatus();
		setObjValue(formpledgestatus0002, mfPledgeStatusJsp);
		MfPledgeStatus mfPledgeStatus = mfPledgeStatusFeign.getById(mfPledgeStatusJsp);
		if (mfPledgeStatus != null) {
			try {
				mfPledgeStatus = (MfPledgeStatus) EntityUtil.reflectionSetVal(mfPledgeStatus, mfPledgeStatusJsp,
						getMapByJson(ajaxData));
				mfPledgeStatusFeign.update(mfPledgeStatus);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpledgestatus0002 = formService.getFormData("pledgestatus0002");
			getFormValue(formpledgestatus0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpledgestatus0002)) {
				MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
				setObjValue(formpledgestatus0002, mfPledgeStatus);
				mfPledgeStatusFeign.update(mfPledgeStatus);
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String statuId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpledgestatus0002 = formService.getFormData("pledgestatus0002");
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		mfPledgeStatus.setStatuId(statuId);
		mfPledgeStatus = mfPledgeStatusFeign.getById(mfPledgeStatus);
		getObjValue(formpledgestatus0002, mfPledgeStatus, formData);
		if (mfPledgeStatus != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String statuId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		mfPledgeStatus.setStatuId(statuId);
		try {
			mfPledgeStatusFeign.delete(mfPledgeStatus);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
	@RequestMapping("/input")
	public String input(Model model,String cusNo,String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		pledgeBaseInfo.setPledgeNo(pledgeNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		mfPledgeStatus.setCusNo(mfCusCustomer.getCusNo());
		mfPledgeStatus.setCusName(mfCusCustomer.getCusName());
		mfPledgeStatus.setPledgeNo(pledgeBaseInfo.getPledgeNo());
		mfPledgeStatus.setPledgeName(pledgeBaseInfo.getPledgeName());
		mfPledgeStatus.setPledgeShowNo(pledgeBaseInfo.getPledgeShowNo());
//		mfPledgeStatus.setClassFirstNo(pledgeBaseInfo.getClassFirstNo());
		mfPledgeStatus.setClassSecondName(pledgeBaseInfo.getClassSecondName());
		mfPledgeStatus.setVouType(pledgeBaseInfo.getPledgeMethod());
		FormData formpledgestatus0002 = formService.getFormData("pledgestatus0002");
		getObjValue(formpledgestatus0002, mfPledgeStatus);
		model.addAttribute("formpledgestatus0002", formpledgestatus0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfPledgeStatus_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledgestatus0002 = formService.getFormData("pledgestatus0002");
		getFormValue(formpledgestatus0002);
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		setObjValue(formpledgestatus0002, mfPledgeStatus);
		mfPledgeStatusFeign.insert(mfPledgeStatus);
		getObjValue(formpledgestatus0002, mfPledgeStatus);
		this.addActionMessage(model, "保存成功");
		@SuppressWarnings("unchecked")
		List<MfPledgeStatus> mfPledgeStatusList = (List<MfPledgeStatus>) mfPledgeStatusFeign
				.findByPage(this.getIpage(), mfPledgeStatus).getResult();
		model.addAttribute("formpledgestatus0002", formpledgestatus0002);
		model.addAttribute("mfPledgeStatusList", mfPledgeStatusList);
		model.addAttribute("query", "");
		return "/component/collateral/MfPledgeStatus_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String statuId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formpledgestatus0001 = formService.getFormData("pledgestatus0001");
		getFormValue(formpledgestatus0001);
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		mfPledgeStatus.setStatuId(statuId);
		mfPledgeStatus = mfPledgeStatusFeign.getById(mfPledgeStatus);
		getObjValue(formpledgestatus0001, mfPledgeStatus);
		model.addAttribute("formpledgestatus0001", formpledgestatus0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfPledgeStatus_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String statuId) throws Exception {
		ActionContext.initialize(request, response);
		MfPledgeStatus mfPledgeStatus = new MfPledgeStatus();
		mfPledgeStatus.setStatuId(statuId);
		mfPledgeStatusFeign.delete(mfPledgeStatus);
		return getListPage();
	}

}

package app.component.oa.changemoney.controller;

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
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.oa.changemoney.entity.MfOaCounttrans;
import app.component.oa.changemoney.feign.MfOaCounttransFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * Title: MfOaCounttransAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 15:21:27 CST 2017
 **/
@Controller
@RequestMapping("/mfOaCounttrans")
public class MfOaCounttransController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaCounttransFeign mfOaCounttransFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	/**
	 * 进入到转款的操作界面
	 */
	@RequestMapping("/outChangeMoneyPage")
	public String outChangeMoneyPage(String badgeNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formchangemoney0001 = new FormService().getFormData("changemoney0001");
		getFormValue(formchangemoney0001);
		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setBadgeNo(badgeNo);
		mfOaCounttrans = mfOaCounttransFeign.getById(mfOaCounttrans);
		// 处理申请时间的显示
		mfOaCounttrans.setApplyTime(DateUtil.getShowDateTime(mfOaCounttrans.getApplyTime()));
		getObjValue(formchangemoney0001, mfOaCounttrans);
		model.addAttribute("formchangemoney0001",formchangemoney0001 );
		model.addAttribute("mfOaCounttrans", mfOaCounttrans);
		model.addAttribute("query","" );
		return "/component/oa/changemoney/MfOaCounttrans_DetailOut";
	}

	/**
	 * 处理转款Ajax操作
	 */
	@ResponseBody
	@RequestMapping("/duscussOutAjax")
	public Map<String, Object> duscussOutAjax(String ajaxData,String badgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			FormData formchangemoney0001 = new FormService().getFormData("changemoney0001");
			getFormValue(formchangemoney0001, getMapByJson(ajaxData));
			if (this.validateFormData(formchangemoney0001)) {
				MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
				mfOaCounttrans.setBadgeNo(badgeNo);
				setObjValue(formchangemoney0001, mfOaCounttrans);
				// 处理时间问题
				mfOaCounttrans.setApplyTime(DateUtil.StringToString(mfOaCounttrans.getApplyTime(),"yyyy-MM-dd HH:mm:ss", "yyyyMMdd HH:mm:ss"));
				mfOaCounttransFeign.updateOutChangeMoney(mfOaCounttrans);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 处理审批提交操作
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/discussAjax")
	public Map<String, Object> discussAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> thisObjMap = getMapByJson(ajaxData);
			String formId = (String) thisObjMap.get("formId");
			if (formId.contains("003")) {
				FormData formchangemoney0003 = new FormService().getFormData("changemoney0003");
				getFormValue(formchangemoney0003, getMapByJson(ajaxData));
				MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
				if (this.validateFormData(formchangemoney0003)) {
					setObjValue(formchangemoney0003, mfOaCounttrans);
					new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaCounttrans);
					thisObjMap.put("mfOaCounttrans", mfOaCounttrans);
					Result result = mfOaCounttransFeign.updateSubmit(thisObjMap);
					dataMap.put("flag", "success");
					dataMap.put("msg", result.getMsg());
				}
				return dataMap;
			} else {
				FormData formchangemoney0004 = new FormService().getFormData("changemoney0004");
				getFormValue(formchangemoney0004, getMapByJson(ajaxData));
				MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
				if (this.validateFormData(formchangemoney0004)) {
					setObjValue(formchangemoney0004, mfOaCounttrans);
					new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaCounttrans);
					thisObjMap.put("mfOaCounttrans", mfOaCounttrans);
					Result result = mfOaCounttransFeign.updateSubmit(thisObjMap);
					dataMap.put("flag", "success");
					dataMap.put("msg", result.getMsg());
				}
				return dataMap;
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}

	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/changemoney/MfOaCounttrans_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
			mfOaCounttrans.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaCounttrans.setCriteriaList(mfOaCounttrans, ajaxData);// 我的筛选
			// mfOaCounttrans.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfOaCounttrans,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaCounttrans", mfOaCounttrans));
			ipage = mfOaCounttransFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");
			getFormValue(formchangemoney0002, getMapByJson(ajaxData));
			if (this.validateFormData(formchangemoney0002)) {
				MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
				setObjValue(formchangemoney0002, mfOaCounttrans);
				// 处理时间问题
				mfOaCounttrans.setApplyTime(DateUtil.StringToString(mfOaCounttrans.getApplyTime(),
						"yyyy-MM-dd HH:mm:ss", "yyyyMMdd HH:mm:ss"));
				mfOaCounttrans = mfOaCounttransFeign.insert(mfOaCounttrans);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaCounttrans.getApprovalNodeName());
				paramMap.put("opNo", mfOaCounttrans.getApprovePartName());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");
		getFormValue(formchangemoney0002, getMapByJson(ajaxData));
		MfOaCounttrans mfOaCounttransJsp = new MfOaCounttrans();
		setObjValue(formchangemoney0002, mfOaCounttransJsp);
		MfOaCounttrans mfOaCounttrans = mfOaCounttransFeign.getById(mfOaCounttransJsp);
		if (mfOaCounttrans != null) {
			try {
				mfOaCounttrans = (MfOaCounttrans) EntityUtil.reflectionSetVal(mfOaCounttrans, mfOaCounttransJsp,
						getMapByJson(ajaxData));
				mfOaCounttransFeign.update(mfOaCounttrans);
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
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
			FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");
			getFormValue(formchangemoney0002, getMapByJson(ajaxData));
			if (this.validateFormData(formchangemoney0002)) {
				mfOaCounttrans = new MfOaCounttrans();
				setObjValue(formchangemoney0002, mfOaCounttrans);
				mfOaCounttransFeign.update(mfOaCounttrans);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String badgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");
		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setBadgeNo(badgeNo);
		mfOaCounttrans = mfOaCounttransFeign.getById(mfOaCounttrans);
		getObjValue(formchangemoney0002, mfOaCounttrans, formData);
		if (mfOaCounttrans != null) {
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
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String badgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setBadgeNo(badgeNo);
		try {
			mfOaCounttransFeign.delete(mfOaCounttrans);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");

		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setCust(User.getRegName(this.getHttpRequest()));
		mfOaCounttrans.setApplyTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getFormValue(formchangemoney0002);
		getObjValue(formchangemoney0002, mfOaCounttrans);
		model.addAttribute("formchangemoney0002", formchangemoney0002);
		model.addAttribute("mfOaCounttrans", mfOaCounttrans);
		model.addAttribute("query", "");
		return "/component/oa/changemoney/MfOaCounttrans_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String badgeNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formchangemoney0001 = new FormService().getFormData("changemoney0001");
		getFormValue(formchangemoney0001);
		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setBadgeNo(badgeNo);
		mfOaCounttrans = mfOaCounttransFeign.getById(mfOaCounttrans);
		mfOaCounttrans.setApplyTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getObjValue(formchangemoney0001, mfOaCounttrans);
		model.addAttribute("formchangemoney0001", formchangemoney0001);
		model.addAttribute("mfOaCounttrans", mfOaCounttrans);
		model.addAttribute("query", "");
		return "/component/oa/changemoney/MfOaCounttrans_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");
		getFormValue(formchangemoney0002);
		boolean validateFlag = this.validateFormData(formchangemoney0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formchangemoney0002 = new FormService().getFormData("changemoney0002");
		getFormValue(formchangemoney0002);
		boolean validateFlag = this.validateFormData(formchangemoney0002);
	}

	/**
	 * 一般情况的
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(String badgeNo,String[] taskId,String activityType,Model model) throws Exception {
		ActionContext.initialize(request, response);
		/*
		 * private MfOaCounttrans mfOaCounttrans; private List<MfOaCounttrans>
		 * mfOaCounttransList; private String badgeNo;
		 */
		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setBadgeNo(badgeNo);
		mfOaCounttrans = mfOaCounttransFeign.getById(mfOaCounttrans);
		mfOaCounttrans.setTaskId(taskId[0]);
		// 对申请时间进行格式化
		mfOaCounttrans.setApplyTime(DateUtil.getShowDateTime(mfOaCounttrans.getApplyTime()));
		FormData formchangemoney0003 = new FormService().getFormData("changemoney0003");
		getFormValue(formchangemoney0003);
		getObjValue(formchangemoney0003, mfOaCounttrans);

		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(badgeNo, null);

		/*
		 * //处理审批意见类型 List<OptionsList> opinionTypeList = new
		 * CodeUtils().getOpinionTypeList(activityType,
		 * taskAppro.getCouldRollback(),null); this.changeFormProperty(formapply0003,
		 * "opinionType", "optionArray", opinionTypeList);
		 */
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formchangemoney0003, "opinionType", "optionArray", opinionTypeList);
		// this.changeFormProperty(formexpense0006, "opinionType", "optionArray",
		// opinionTypeList);
		model.addAttribute("formchangemoney0003",formchangemoney0003 );
		model.addAttribute("opinionTypeList",opinionTypeList );
		model.addAttribute("mfOaCounttrans",mfOaCounttrans );
		model.addAttribute("query","" );
		return "/component/oa/changemoney/MfOaCounttrans_Discuss";
	}

	/**
	 * 总经理的审批界面
	 * 
	 * @return
	 */
	@RequestMapping("/getViewPoint1")
	public String getViewPoint1(String badgeNo,String[] taskId,String activityType,Model model) throws Exception {
		ActionContext.initialize(request, response);
		/*
		 * private MfOaCounttrans mfOaCounttrans; private List<MfOaCounttrans>
		 * mfOaCounttransList; private String badgeNo;
		 */
		MfOaCounttrans mfOaCounttrans = new MfOaCounttrans();
		mfOaCounttrans.setBadgeNo(badgeNo);
		mfOaCounttrans = mfOaCounttransFeign.getById(mfOaCounttrans);
		mfOaCounttrans.setTaskId(taskId[0]);
		// 对申请时间进行格式化
		mfOaCounttrans.setApplyTime(DateUtil.getShowDateTime(mfOaCounttrans.getApplyTime()));
		FormData formchangemoney0004 = new FormService().getFormData("changemoney0004");
		getFormValue(formchangemoney0004);
		getObjValue(formchangemoney0004, mfOaCounttrans);
		// 处理审批意见
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(badgeNo, null);
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formchangemoney0004, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formchangemoney0004",formchangemoney0004 );
		model.addAttribute("opinionTypeList",opinionTypeList );
		model.addAttribute("mfOaCounttrans",mfOaCounttrans );
		model.addAttribute("query","" );
		return "/component/oa/changemoney/MfOaCounttrans_Discuss1";
	}

}
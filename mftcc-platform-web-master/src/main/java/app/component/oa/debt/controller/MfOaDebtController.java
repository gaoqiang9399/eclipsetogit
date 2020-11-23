package app.component.oa.debt.controller;

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
import com.dhcc.workflow.api.model.Transition;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.oa.debt.entity.MfOaDebt;
import app.component.oa.debt.entity.MfOaDebtWkf;
import app.component.oa.debt.feign.MfOaDebtFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * @author Tangxj
 * @Sat Dec 17 14:00:23 CST 2016
 */
@Controller
@RequestMapping("/mfOaDebt")
public class MfOaDebtController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaDebtFeign mfOaDebtFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private TaskFeign taskFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebt_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebt mfOaDebt = new MfOaDebt();
			mfOaDebt.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaDebt.setCriteriaList(mfOaDebt, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaDebt,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaDebt", mfOaDebt));
			ipage = mfOaDebtFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping("/bigMoneyAjax")
	public Map<String, Object> bigMoneyAjax(String money) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 处理负数
		money = money.replace(",", "");
		String temp = "";
		if (money.startsWith("-")) {
			temp = "负";
			money = money.substring(1);
		}
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		if (money == null || "".equals(money) || "0.0".equals(money) || "0".equals(money) || "0.00".equals(money)) {
			money = "0";
			prefix = "零圆";
			suffix = "整";
		} else {
			double value = Double.valueOf(money);
			char[] hunit = { '拾', '佰', '仟', '万' }; // 段内位置表示
			String[] vunit = { "万", "亿", "万亿" }; // 段名表示
			char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
			long midVal = (long) (value * 1000);// 转化成整形

			String valStr = String.valueOf(midVal / 10); // 转化成字符串
			String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
			String rail = valStr.substring(valStr.length() - 2); // 取小数部分

			// 处理小数点后面的数
			if ("00".equals(rail)) {
				// 如果小数部分为0
				suffix = "整";
			} else {
				suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
			}
			// 处理小数点前面的数
			char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
			char zero = '0'; // 标志'0'表示出现过0
			byte zeroSerNum = 0; // 连续出现0的次数
			for (int i = 0; i < chDig.length; i++) {
				// 循环处理每个数字
				int idx = (chDig.length - i - 1) % 4; // 取段内位置
				int vidx = (chDig.length - i - 1) / 4; // 取段位置
				if (chDig[i] == '0') { // 如果当前字符是0
					zeroSerNum++; // 连续0次数递增
					if (zero == '0') { // 标志
						zero = digit[0];
					}
					if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
						prefix += vunit[vidx - 1];
						zero = '0';
					}
					continue;
				}
				zeroSerNum = 0; // 连续0次数清零
				if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
					prefix += zero;
					zero = '0';
				}
				prefix += digit[chDig[i] - '0']; // 转化该数字表示
				if (idx > 0) {
                    prefix += hunit[idx - 1];
                }
				if (idx == 0 && vidx > 0) {
					prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
				}
			}
			if (prefix.length() > 0) {
                prefix += '圆'; // 如果整数部分存在,则有圆的字样
            }
		}
		String bigMoney = temp + prefix + suffix;
		dataMap.put("bigMoney", bigMoney);

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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			FormData formdebt00002 = new FormService().getFormData("debt00002");
			dataMap = getMapByJson(ajaxData);
			getFormValue(formdebt00002, getMapByJson(ajaxData));
			if (this.validateFormData(formdebt00002)) {
				MfOaDebt mfOaDebt = new MfOaDebt();
				setObjValue(formdebt00002, mfOaDebt);
				mfOaDebt.setDebtId(WaterIdUtil.getWaterId());
				mfOaDebt.setOpNo(User.getRegNo(request));
				mfOaDebt.setBrNo(User.getOrgNo(request));
				String time = DateUtil.getDate();
				mfOaDebt.setApplyTime(time);
				// 设置隐藏域里面的属性，在记录表中展示
				mfOaDebt.setDebtSts("Y".equals(mfOaDebt.getDebtSts()) ? "2" : "1");
				// 模拟已放款。
				// mfOaDebt.setDebtAmt(mfOaDebt.getApplyAmt()-mfOaDebt.getReturnAmt());
				if ("1".equals(mfOaDebt.getDebtSts())) {
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				} else {
					Map<String, String> resultMap = mfOaDebtFeign.insertForSubmit(mfOaDebt);
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(resultMap));
				}
				mfOaDebtFeign.insert(mfOaDebt);
				dataMap.put("flag", "success");
				// dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 点击操作按钮跳转到审批页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throw Exception
	 */
	@RequestMapping("/applyProcess")
	public String applyProcess(String debtId, String taskId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		FormData formdebt00003 = new FormService().getFormData("debt00003");
		MfOaDebt mfOaDebt = new MfOaDebt();
		mfOaDebt.setDebtId(debtId);
		mfOaDebt = mfOaDebtFeign.getById(mfOaDebt);
		getObjValue(formdebt00003, mfOaDebt);
		if (taskId.indexOf(",") != -1) {
			taskId = taskId.substring(0, taskId.indexOf(","));
		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(null, taskId);
		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formdebt00003, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("mfOaDebt", mfOaDebt);
		model.addAttribute("formdebt00003", formdebt00003);
		model.addAttribute("opinionTypeList", opinionTypeList);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebt_ApplyDetail";
	}

	/**
	 * 借款审批处理
	 * 
	 * @return
	 * @throw Exception
	 */
	@ResponseBody
	@RequestMapping("/submitForUpdate")
	public Map<String, Object> submitForUpdate(String ajaxData, String appNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formdebt00004 = new FormService().getFormData("debt00004");
			getFormValue(formdebt00004, getMapByJson(ajaxData));
			MfOaDebtWkf mfOaDebtWkf = new MfOaDebtWkf();
			String taskId = (String) dataMap.get("taskId");
			setObjValue(formdebt00004, mfOaDebtWkf);
			MfOaDebt mfOaDebt = new MfOaDebt();
			mfOaDebt.setDebtId(appNo);
			mfOaDebt = mfOaDebtFeign.getById(mfOaDebt);
			// this.appAuthBo.updateForSubmit(appAuth);
			Result res = mfOaDebtFeign.updateForSubmit1(taskId, appNo, mfOaDebtWkf.getOpinionType(),
					mfOaDebtWkf.getApprovalOpinion(), taskFeign.getTransitionsStr(taskId),
					User.getRegNo(this.getHttpRequest()), "", mfOaDebt);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		getFormValue(formdebt00002, getMapByJson(ajaxData));
		MfOaDebt mfOaDebtJsp = new MfOaDebt();
		setObjValue(formdebt00002, mfOaDebtJsp);
		MfOaDebt mfOaDebt = mfOaDebtFeign.getById(mfOaDebtJsp);
		if (mfOaDebt != null) {
			try {
				mfOaDebt = (MfOaDebt) EntityUtil.reflectionSetVal(mfOaDebt, mfOaDebtJsp, getMapByJson(ajaxData));
				mfOaDebtFeign.update(mfOaDebt);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
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
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaDebt mfOaDebt = new MfOaDebt();
		try {
			FormData formdebt00002 = new FormService().getFormData("debt00002");
			getFormValue(formdebt00002, getMapByJson(ajaxData));
			if (this.validateFormData(formdebt00002)) {
				mfOaDebt = new MfOaDebt();
				setObjValue(formdebt00002, mfOaDebt);
				mfOaDebt.setDebtSts("Y".equals(mfOaDebt.getDebtSts()) ? "2" : "1");
				if ("1".equals(mfOaDebt.getDebtSts())) {
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				} else {
					Map<String, String> reusltMap = mfOaDebtFeign.insertForSubmit(mfOaDebt);
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(reusltMap));
				}
				mfOaDebtFeign.update(mfOaDebt);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String debtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		MfOaDebt mfOaDebt = new MfOaDebt();
		mfOaDebt.setDebtId(debtId);
		mfOaDebt = mfOaDebtFeign.getById(mfOaDebt);
		getObjValue(formdebt00002, mfOaDebt, formData);
		if (mfOaDebt != null) {
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
	public Map<String, Object> deleteAjax(String debtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebt mfOaDebt = new MfOaDebt();
			mfOaDebt.setDebtId(debtId);
			mfOaDebtFeign.delete(mfOaDebt);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaDebt mfOaDebt = new MfOaDebt();
		mfOaDebt.setOpNo(User.getRegNo(request));
		mfOaDebt.setOpName(User.getRegName(request));
		mfOaDebt.setBrNo(User.getOrgNo(request));
		mfOaDebt.setBrName(User.getOrgName(request));
		String time = DateUtil.getDateTime();
		mfOaDebt.setApplyTime(time);
		mfOaDebt.setBr(mfOaDebt.getBrName() + "" + "[" + mfOaDebt.getBrNo() + "]");
		mfOaDebt.setOp(mfOaDebt.getOpName() + "" + "[" + mfOaDebt.getOpNo() + "]");
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		getObjValue(formdebt00002, mfOaDebt);
		model.addAttribute("formdebt00002", formdebt00002);
		model.addAttribute("mfOaDebt", mfOaDebt);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebt_Insert";
	}

	/***
	 * 新增
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		getFormValue(formdebt00002);
		MfOaDebt mfOaDebt = new MfOaDebt();
		setObjValue(formdebt00002, mfOaDebt);
		mfOaDebtFeign.insert(mfOaDebt);
		getObjValue(formdebt00002, mfOaDebt);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaDebt", mfOaDebt));
		List<MfOaDebt> mfOaDebtList = (List<MfOaDebt>) mfOaDebtFeign.findByPage(ipage).getResult();
		model.addAttribute("formdebt00002", formdebt00002);
		model.addAttribute("mfOaDebtList", mfOaDebtList);
		model.addAttribute("mfOaDebt", mfOaDebt);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebt_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String debtId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		FormData formdebt00001 = new FormService().getFormData("debt00001");
		getFormValue(formdebt00002);
		MfOaDebt mfOaDebt = new MfOaDebt();
		mfOaDebt.setDebtId(debtId);
		mfOaDebt = mfOaDebtFeign.getById(mfOaDebt);
		mfOaDebt.setBr(mfOaDebt.getBrName() + "" + "[" + mfOaDebt.getBrNo() + "]");
		mfOaDebt.setOp(mfOaDebt.getOpName() + "" + "[" + mfOaDebt.getOpNo() + "]");
		model.addAttribute("formdebt00002", formdebt00002);
		model.addAttribute("formdebt00001", formdebt00001);
		model.addAttribute("mfOaDebt", mfOaDebt);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebt_Detail";
	}

	@RequestMapping("/approvalHis")
	public String approvalHis(Model model,String debtId) throws Exception {
		model.addAttribute("debtId", debtId);
		return "/component/oa/debt/MfOaDebt_ApprovalHis";
	}

	@RequestMapping("/getApplyApprovalOpinionList")
	public Map<String, Object> getApplyApprovalOpinionList(String debtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("APPROVAL_RESULT");
		List<WkfApprovalOpinion> ideaList = wkfInterfaceFeign.getWkfTaskHisList(debtId);
		JSONArray zTreeNodes = JSONArray.fromObject(ideaList);
		for (int i = 0; i < zTreeNodes.size(); i++) {
			zTreeNodes.getJSONObject(i).put("id", zTreeNodes.getJSONObject(i).getString("execution"));
			zTreeNodes.getJSONObject(i).put("name", zTreeNodes.getJSONObject(i).getString("description"));
			zTreeNodes.getJSONObject(i).put("pId", "0");
			for (ParmDic parmDic : pdList) {
				if (parmDic.getOptCode().equals(zTreeNodes.getJSONObject(i).getString("result"))) {
					zTreeNodes.getJSONObject(i).put("optName", parmDic.getOptName());
					break;
				}
			}
		}
		dataMap.put("zTreeNodes", zTreeNodes);
		return dataMap;
	}

	@RequestMapping("/getById01")
	public String getById01(String debtId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebt00001 = new FormService().getFormData("debt00001");
		getFormValue(formdebt00001);
		MfOaDebt mfOaDebt = new MfOaDebt();
		mfOaDebt.setDebtId(debtId);
		mfOaDebt = mfOaDebtFeign.getById(mfOaDebt);
		mfOaDebt.setApplyTime(DateUtil.getStr(mfOaDebt.getApplyTime()));
		mfOaDebt.setBr(mfOaDebt.getBrName() + "" + "[" + mfOaDebt.getBrNo() + "]");
		mfOaDebt.setOp(mfOaDebt.getOpName() + "" + "[" + mfOaDebt.getOpNo() + "]");
		// CodeUtils codeUtils = new CodeUtils();
		// Map<String,String> map = codeUtils.getMapByKeyName("OA_DEBT_STS");
		// mfOaDebt.setDebtSts(map.get(mfOaDebt.getDebtSts()));
		// Map<String,String> map1 = codeUtils.getMapByKeyName("APPLY_TYPE");
		// mfOaDebt.setApplyType(map1.get(mfOaDebt.getApplyType()));
		// SimpleDateFormat formatter =new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		// String s= mfOaDebt.getApplyTime();
		// Date date = formatter.parse(s);
		// SimpleDateFormat formatter1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss ");
		// String date1 = formatter1.format(date);
		// mfOaDebt.setApplyTime(date1);
		model.addAttribute("formdebt00001", formdebt00001);
		model.addAttribute("mfOaDebt", mfOaDebt);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebt_Find";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String debtId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaDebt mfOaDebt = new MfOaDebt();
		mfOaDebt.setDebtId(debtId);
		mfOaDebtFeign.delete(mfOaDebt);
		return getListPage(model);
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
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		getFormValue(formdebt00002);
		boolean validateFlag = this.validateFormData(formdebt00002);
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
		FormData formdebt00002 = new FormService().getFormData("debt00002");
		getFormValue(formdebt00002);
		boolean validateFlag = this.validateFormData(formdebt00002);
	}
}

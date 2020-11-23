package app.component.auth.controller;

import app.base.User;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApplyHis;
import app.component.auth.entity.MfCusCreditUseHis;
import app.component.auth.feign.MfCusCreditAdjustApplyFeign;
import app.component.auth.feign.MfCusCreditModelFeign;
import app.component.auth.feign.MfCusCreditApplyHisFeign;
import app.component.auth.feign.MfCusCreditUseHisFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.common.EntityUtil;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.component.cus.cusgroup.feign.MfCusGroupFeign;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.wkf.entity.Result;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusCreditAdjustApplyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 22 17:41:42 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditAdjustApply")
public class MfCusCreditAdjustApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusCreditAdjustApplyBo
	@Autowired
	private MfCusCreditAdjustApplyFeign mfCusCreditAdjustApplyFeign;
	private MfCusCreditModelFeign mfCusCreditModelFeign; // 授信配置业务控制操作
	// 全局变量
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private MfCusGroupFeign mfCusGroupFeign;
	@Autowired
	private MfCusCreditApplyHisFeign mfCusCreditApplyHisFeign;
	@Autowired
	private MfCusCreditUseHisFeign mfCusCreditUseHisFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/auth/MfCusCreditAdjustApply_List";
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		try {
			mfCusCreditAdjustApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCreditAdjustApply.setCriteriaList(mfCusCreditAdjustApply, ajaxData);// 我的筛选
			// mfCusCreditAdjustApply.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusCreditAdjustApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusCreditAdjustApplyFeign.findByPage(ipage, mfCusCreditAdjustApply);
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
	public Map<String, Object> insertAjax(String ajaxData,String agenciesArrs,String breedArrs,String baseType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formadjustapp0002 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formadjustapp0002, getMapByJson(ajaxData));
			if (this.validateFormData(formadjustapp0002)) {
				MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
				setObjValue(formadjustapp0002, mfCusCreditAdjustApply);
				dataMap.put("agenciesArrs", agenciesArrs);
				dataMap.put("breedArrs", breedArrs);
				mfCusCreditAdjustApply.setBaseType(baseType);
				//新增分公司调整授信，授信额度不能大于所属集团可用额度
				if (baseType !=null && "1".equals(baseType)){
					MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
					mfCusCorpBaseInfo.setCusNo(mfCusCreditAdjustApply.getCusNo());
					mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
					//如果是子公司，授信额度不能大于所属集团可用额度
					if (mfCusCorpBaseInfo != null && StringUtil.isNotEmpty(mfCusCorpBaseInfo.getIfGroup()) && "1".equals(mfCusCorpBaseInfo.getIfGroup())){
						MfCusGroup mfCusGroup = new MfCusGroup();
						mfCusGroup.setIdNum(mfCusCorpBaseInfo.getGroupNo());
						mfCusGroup = mfCusGroupFeign.getByIdNum(mfCusGroup);
						MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
						//获取所属集团可用额度
						MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
						mfCusCreditUseHis.setCusNo(mfCusGroup.getGroupNo());
						List<MfCusCreditUseHis> list = mfCusCreditUseHisFeign.getMfCusCreditUseHis(mfCusCreditUseHis);
						String authBal = "";
						if (list.size() > 0){
							mfCusCreditUseHis = list.get(0);
							MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
							mfCusCreditContract.setCreditAppId(mfCusCreditUseHis.getCreditAppId());
							mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
							authBal = MathExtend.moneyStr(mfCusCreditContract.getCreditSum());//所属集团可用额度
						}
						else {
							dataMap.put("msg", "所属集团暂未授信!");
							return dataMap;
						}

						String creditSum = MathExtend.moneyStr(mfCusCreditAdjustApply.getAdjCreditSum());
						if (MathExtend.comparison(creditSum.replaceAll(",",""), authBal.replaceAll(",","")) == 1){
							dataMap.put("msg", "授信额度不能大于所属集团授信额度!");
							return dataMap;
						}
					}
				}
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusCreditAdjustApply);
				dataMap.put("mfCusCreditAdjustApply", mfCusCreditAdjustApply);
				mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.insertStartProcess(dataMap);
				dataMap = new HashMap<String, Object>();
				dataMap.put("creditAppId", mfCusCreditAdjustApply.getCreditAppId());
				dataMap.put("adjustAppId", mfCusCreditAdjustApply.getAdjustAppId());
				dataMap.put("cusNo", mfCusCreditAdjustApply.getCusNo());
				dataMap.put("wkfAppId", mfCusCreditAdjustApply.getWkfAppId());
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
			throw e;
		}
		return dataMap;
	}

	/**
	 * 授信变更申请提交
	 * @param ajaxData
	 * @param kindNos
	 * @param kindNames
	 * @param creditAmts
	 * @param baseType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitInputAjax")
	@ResponseBody
	public Map<String, Object> submitInputAjax(String ajaxData, String kindNos, String kindNames, String creditAmts, String baseType, String temporaryStorage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formadjustapp0002 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formadjustapp0002, getMapByJson(ajaxData));
			if (this.validateFormData(formadjustapp0002)) {
				MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
				setObjValue(formadjustapp0002, mfCusCreditAdjustApply);
				dataMap.put("kindNos", kindNos);
				dataMap.put("kindNames", kindNames);
				dataMap.put("creditAmts", creditAmts);
				mfCusCreditAdjustApply.setBaseType(baseType);
				//新增分公司调整授信，授信额度不能大于所属集团可用额度
				if ("1".equals(baseType)) {
					MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
					mfCusCorpBaseInfo.setCusNo(mfCusCreditAdjustApply.getCusNo());
					mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
					//如果是子公司，授信额度不能大于所属集团可用额度
					if (mfCusCorpBaseInfo != null && StringUtil.isNotEmpty(mfCusCorpBaseInfo.getIfGroup()) && "1".equals(mfCusCorpBaseInfo.getIfGroup())) {
						MfCusGroup mfCusGroup = new MfCusGroup();
						mfCusGroup.setIdNum(mfCusCorpBaseInfo.getGroupNo());
						mfCusGroup = mfCusGroupFeign.getByIdNum(mfCusGroup);
						MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
						//获取所属集团可用额度
						MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
						mfCusCreditUseHis.setCusNo(mfCusGroup.getGroupNo());
						List<MfCusCreditUseHis> list = mfCusCreditUseHisFeign.getMfCusCreditUseHis(mfCusCreditUseHis);
						String authBal = "";
						if (list.size() > 0) {
							mfCusCreditUseHis = list.get(0);
							MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
							mfCusCreditContract.setCreditAppId(mfCusCreditUseHis.getCreditAppId());
							mfCusCreditContract = mfCusCreditContractFeign.getByCreditAppId(mfCusCreditContract);
							authBal = MathExtend.moneyStr(mfCusCreditContract.getCreditSum());//所属集团可用额度
						} else {
							dataMap.put("msg", "所属集团暂未授信!");
							return dataMap;
						}

						String creditSum = MathExtend.moneyStr(mfCusCreditAdjustApply.getAdjCreditSum());
						if (MathExtend.comparison(creditSum.replaceAll(",", ""), authBal.replaceAll(",", "")) == 1) {
							dataMap.put("msg", "授信额度不能大于所属集团授信额度!");
							return dataMap;
						}
					}
				}
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfCusCreditAdjustApply);
				dataMap.put("mfCusCreditAdjustApply", mfCusCreditAdjustApply);
				dataMap.put("temporaryStorage",	temporaryStorage);
				mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.submitInput(dataMap);
				dataMap = new HashMap<String, Object>();
				dataMap.put("creditAppId", mfCusCreditAdjustApply.getCreditAppId());
				dataMap.put("adjustAppId", mfCusCreditAdjustApply.getAdjustAppId());
				dataMap.put("cusNo", mfCusCreditAdjustApply.getCusNo());
				dataMap.put("wkfAppId", mfCusCreditAdjustApply.getWkfAppId());
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
		FormData formadjustapp0002 = formService.getFormData("adjustapp0002");
		getFormValue(formadjustapp0002, getMapByJson(ajaxData));
		MfCusCreditAdjustApply mfCusCreditAdjustApplyJsp = new MfCusCreditAdjustApply();
		setObjValue(formadjustapp0002, mfCusCreditAdjustApplyJsp);
		MfCusCreditAdjustApply mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApplyJsp);
		if (mfCusCreditAdjustApply != null) {
			try {
				mfCusCreditAdjustApply = (MfCusCreditAdjustApply) EntityUtil.reflectionSetVal(mfCusCreditAdjustApply,
						mfCusCreditAdjustApplyJsp, getMapByJson(ajaxData));
				mfCusCreditAdjustApplyFeign.update(mfCusCreditAdjustApply);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formadjustapp0002 = formService.getFormData("adjustapp0002");
			getFormValue(formadjustapp0002, getMapByJson(ajaxData));
			if (this.validateFormData(formadjustapp0002)) {
				MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
				setObjValue(formadjustapp0002, mfCusCreditAdjustApply);
				mfCusCreditAdjustApplyFeign.update(mfCusCreditAdjustApply);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String adjustAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formadjustapp0002 = formService.getFormData("adjustapp0002");
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		mfCusCreditAdjustApply.setAdjustAppId(adjustAppId);
		mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
		getObjValue(formadjustapp0002, mfCusCreditAdjustApply, formData);
		if (mfCusCreditAdjustApply != null) {
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
	public Map<String, Object> deleteAjax(String adjustAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		mfCusCreditAdjustApply.setAdjustAppId(adjustAppId);
		try {
			mfCusCreditAdjustApplyFeign.delete(mfCusCreditAdjustApply);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formadjustapp0002 = formService.getFormData("adjustapp0002");
		model.addAttribute("formadjustapp0002", formadjustapp0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditAdjustApply_Insert";
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
		FormData formadjustapp0002 = formService.getFormData("adjustapp0002");
		getFormValue(formadjustapp0002);
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		setObjValue(formadjustapp0002, mfCusCreditAdjustApply);
		mfCusCreditAdjustApplyFeign.insert(mfCusCreditAdjustApply);
		getObjValue(formadjustapp0002, mfCusCreditAdjustApply);
		this.addActionMessage(model, "保存成功");
		List<MfCusCreditAdjustApply> mfCusCreditAdjustApplyList = (List<MfCusCreditAdjustApply>) mfCusCreditAdjustApplyFeign
				.findByPage(this.getIpage(), mfCusCreditAdjustApply).getResult();
		model.addAttribute("mfCusCreditAdjustApplyList", mfCusCreditAdjustApplyList);
		model.addAttribute("formadjustapp0002", formadjustapp0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditAdjustApply_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String adjustAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formadjustapp0001 = formService.getFormData("adjustapp0001");
		getFormValue(formadjustapp0001);
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		mfCusCreditAdjustApply.setAdjustAppId(adjustAppId);
		mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
		getObjValue(formadjustapp0001, mfCusCreditAdjustApply);
		model.addAttribute("formadjustapp0001", formadjustapp0001);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditAdjustApply_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String adjustAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		mfCusCreditAdjustApply.setAdjustAppId(adjustAppId);
		mfCusCreditAdjustApplyFeign.delete(mfCusCreditAdjustApply);
		return getListPage(model);
	}

	@RequestMapping(value = "/submitApproveAjax")
	@ResponseBody
	public Map<String, Object> submitApproveAjax(String ajaxData, String id, String taskId, String adjustAppId,
			String nextUser, String transition) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			// 更新授信审批信息
			// updateApproveData();
			MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
			mfCusCreditApplyHis.setId(id);
			mfCusCreditApplyHis = mfCusCreditApplyHisFeign.getById(mfCusCreditApplyHis);

			dataMap = getMapByJson(ajaxData);
			FormData formadjustapp0002 = formService.getFormData("adjustapp0002");
			getFormValue(formadjustapp0002, getMapByJson(ajaxData));
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			setObjValue(formadjustapp0002, mfCusCreditAdjustApply);
			String opinionType = String.valueOf(dataMap.get("approveResult"));
			String approvalOpinion = String.valueOf(dataMap.get("approveRemark"));
			Result res = mfCusCreditAdjustApplyFeign.doCommitApprove(taskId, adjustAppId, opinionType, approvalOpinion,
					transition, User.getRegNo(this.getHttpRequest()), nextUser, mfCusCreditAdjustApply,
					mfCusCreditApplyHis);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

}

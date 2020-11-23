package app.component.lawsuit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.BizPubParm;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.entity.MfLawsuitFollow;
import app.component.lawsuit.feign.MfLawsuitFeign;
import app.component.lawsuit.entity.MfLawsuitPerformReg;
import app.component.lawsuit.feign.MfLawsuitFollowFeign;
import app.component.lawsuit.feign.MfLawsuitPerformRegFeign;
import app.component.pact.assetmanage.entity.MfLitigationExpenseApply;
import cn.mftcc.util.MathExtend;
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
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: mfLawsuitPerformRegAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Feb 22 21:21:30 CST 2017
 **/
@Controller
@RequestMapping("/mfLawsuitPerformReg")
public class MfLawsuitPerformRegController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入mfLawsuitPerformRegBo
	@Autowired
	private MfLawsuitPerformRegFeign mfLawsuitPerformRegFeign;
	@Autowired
	private MfLawsuitFollowFeign mfLawsuitFollowFeign;
	@Autowired
	private MfLawsuitFeign mfLawsuitFeign;

	// 全局变量
	// 异步参数

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/lawsuit/MfLawsuitPerformReg_List";
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
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		try {
			mfLawsuitPerformReg.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfLawsuitPerformReg.setCriteriaList(mfLawsuitPerformReg, ajaxData);// 我的筛选
			// this.getRoleConditions(mfLawsuitPerformReg,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfLawsuitPerformReg",mfLawsuitPerformReg));
			ipage = mfLawsuitPerformRegFeign.findByPage(ipage);
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
			FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
			getFormValue(formlawsuitperformregbase, getMapByJson(ajaxData));
			dataMap=getMapByJson(ajaxData);
			System.out.println(ajaxData);
			String caseId = (String)dataMap.get("caseId");
			if (this.validateFormData(formlawsuitperformregbase)) {
				MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
				setObjValue(formlawsuitperformregbase, mfLawsuitPerformReg);
				mfLawsuitPerformRegFeign.insert(mfLawsuitPerformReg);

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
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		getFormValue(formlawsuitperformregbase, getMapByJson(ajaxData));
		MfLawsuitPerformReg mfLawsuitPerformRegJsp = new MfLawsuitPerformReg();
		setObjValue(formlawsuitperformregbase, mfLawsuitPerformRegJsp);
		MfLawsuitPerformReg mfLawsuitPerformReg = mfLawsuitPerformRegFeign.getById(mfLawsuitPerformRegJsp);
		if (mfLawsuitPerformRegJsp != null) {
			try {
				mfLawsuitPerformReg = (MfLawsuitPerformReg) EntityUtil.reflectionSetVal(mfLawsuitPerformReg, mfLawsuitPerformRegJsp,
						getMapByJson(ajaxData));
                mfLawsuitPerformRegFeign.update(mfLawsuitPerformReg);
                String caseId = mfLawsuitPerformReg.getCaseId();
                mfLawsuitPerformReg.setUnallocatedAmt(calUnallocatedAmt(caseId));
                mfLawsuitPerformReg.setUnallocatedAmtStr(MathExtend.moneyStr(calUnallocatedAmt(caseId)));
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

	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String performId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<String,Object>();
		Map<String,Object> formData = new HashMap<String,Object>();
		String query = "";
		try {
			request.setAttribute("ifBizManger","3");
			MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
			mfLawsuitPerformReg.setPerformId(performId);
			mfLawsuitPerformReg = mfLawsuitPerformRegFeign.getById(mfLawsuitPerformReg);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formlawsuitperformregdetail = formService.getFormData("lawsuitperformregdetail");
			getObjValue(formlawsuitperformregdetail,mfLawsuitPerformReg,formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formlawsuitperformregdetail,"propertySeeTag",query);
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取详情失败");
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
			FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
			getFormValue(formlawsuitperformregbase, getMapByJson(ajaxData));
			if (this.validateFormData(formlawsuitperformregbase)) {
				MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
				setObjValue(formlawsuitperformregbase, mfLawsuitPerformReg);
				mfLawsuitPerformRegFeign.update(mfLawsuitPerformReg);
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
	public Map<String, Object> getByIdAjax(String performId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		String query = "query";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setPerformId(performId);
		mfLawsuitPerformReg = mfLawsuitPerformRegFeign.getById(mfLawsuitPerformReg);
		getObjValue(formlawsuitperformregbase, mfLawsuitPerformReg, formData);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formlawsuitperformregbase, "bootstarpTag", query);
		dataMap.put("htmlStr", htmlStr);
		if (mfLawsuitPerformReg != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("query", query);
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
	public Map<String, Object> deleteAjax(String performId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setPerformId(performId);
		try {
			mfLawsuitPerformRegFeign.delete(mfLawsuitPerformReg);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
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
	public String input(Model model,String caseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setCaseId(caseId);
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setCaseId(caseId);
		mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);

		mfLawsuitPerformReg.setUnallocatedAmt(calUnallocatedAmt(caseId));
		mfLawsuitPerformReg.setUnallocatedAmtStr(MathExtend.moneyStr(calUnallocatedAmt(caseId)));
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		getObjValue(formlawsuitperformregbase, mfLawsuitPerformReg);
		model.addAttribute("formlawsuitperformregbase", formlawsuitperformregbase);
		model.addAttribute("caseId", caseId);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuitPerformReg_Insert";
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
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		getFormValue(formlawsuitperformregbase);
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		setObjValue(formlawsuitperformregbase, mfLawsuitPerformReg);
		mfLawsuitPerformRegFeign.insert(mfLawsuitPerformReg);
		getObjValue(formlawsuitperformregbase, mfLawsuitPerformReg);
		this.addActionMessage(model, "保存成功");
		model.addAttribute("formlawsuitperformregbase", formlawsuitperformregbase);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuitPerformReg_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String performId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		getFormValue(formlawsuitperformregbase);
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setPerformId(performId);
		mfLawsuitPerformReg = mfLawsuitPerformRegFeign.getById(mfLawsuitPerformReg);
		getObjValue(formlawsuitperformregbase, mfLawsuitPerformReg);
		model.addAttribute("mfLawsuitPerformReg", mfLawsuitPerformReg);
		model.addAttribute("formlawsuitperformregbase", formlawsuitperformregbase);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuitPerformReg_Detail";
	}

	/**
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String performId) throws Exception {
		ActionContext.initialize(request, response);
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setPerformId(performId);
		mfLawsuitPerformRegFeign.delete(mfLawsuitPerformReg);
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
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		getFormValue(formlawsuitperformregbase);
		boolean validateFlag = this.validateFormData(formlawsuitperformregbase);
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
		FormData formlawsuitperformregbase = formService.getFormData("lawsuitperformregbase");
		getFormValue(formlawsuitperformregbase);
		boolean validateFlag = this.validateFormData(formlawsuitperformregbase);
	}
	/**
	 * 获取执行回收情况登记列表
	 */

	@RequestMapping(value = "/getPerformRegList")
	@ResponseBody
	public Map<String, Object> getPerformRegList(String caseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setCaseId(caseId);
		List<MfLawsuitPerformReg> performRegList = mfLawsuitPerformRegFeign.getPerformRegList(mfLawsuitPerformReg);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablelawsuitperformreglist", "tableTag", performRegList, null, true);
		dataMap.put("htmlStr", tableHtml);
		// 诉讼余额-金额格式化
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setCaseId(caseId);
		mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
		List<MfLawsuitPerformReg> mfLawsuitPerformRegList = mfLawsuitPerformRegFeign.getPerformRegList(mfLawsuitPerformReg);
		if(mfLawsuitPerformRegList.size()>0){//计算诉讼余额
			Double performAmt = 0.00;
			for(MfLawsuitPerformReg performRegs : mfLawsuitPerformRegList){
				performAmt = MathExtend.add(performAmt,performRegs.getPerformPrcp());
			}
			dataMap.put("loanBalance", MathExtend.moneyStr(MathExtend.subtract(mfLawsuit.getCaseAmt(),performAmt)));
			dataMap.put("actAmt",MathExtend.moneyStr(MathExtend.subtract(mfLawsuit.getCaseAmt(),MathExtend.subtract(mfLawsuit.getCaseAmt(),performAmt))));
		}else {
			dataMap.put("loanBalance", MathExtend.moneyStr(mfLawsuit.getCaseAmt()));
			dataMap.put("actAmt",MathExtend.moneyStr("0"));
		}
		return dataMap;
	}
    /**
     * 单子段编辑额回调处理
     *
     * @param
     * @return dataMap
     * @throws Exception
     */
    @RequestMapping(value = "/dealPerformAmtAjax")
    @ResponseBody
    public Map<String, Object> dealPerformAmtAjax(String caseId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();

        try {
			mfLawsuitPerformReg.setCaseId(caseId);
			// 诉讼余额-金额格式化
			MfLawsuit mfLawsuit = new MfLawsuit();
			mfLawsuit.setCaseId(caseId);
			mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
			List<MfLawsuitPerformReg> mfLawsuitPerformRegList = mfLawsuitPerformRegFeign.getPerformRegList(mfLawsuitPerformReg);
			if(mfLawsuitPerformRegList.size()>0){//计算诉讼余额
				Double performAmt = 0.00;
				for(MfLawsuitPerformReg performRegs : mfLawsuitPerformRegList){
					performAmt = MathExtend.add(performAmt,performRegs.getPerformPrcp());
				}
				dataMap.put("loanBalance", MathExtend.moneyStr(MathExtend.subtract(mfLawsuit.getCaseAmt(),performAmt)));
			}else {
				dataMap.put("loanBalance", MathExtend.moneyStr(mfLawsuit.getCaseAmt()));
			}
            List<MfLawsuitPerformReg> performRegList = mfLawsuitPerformRegFeign.getPerformRegList(mfLawsuitPerformReg);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr("tablelawsuitperformreglist", "tableTag", performRegList, null, true);
            dataMap.put("htmlStr", tableHtml);
            dataMap.put("flag", "success");
			//dataMap.put("fenpeiAmt", MathExtend.moneyStr(calUnallocatedAmt(caseId)));
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }
    public Double calUnallocatedAmt (String caseId){
        MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
        MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
        mfLawsuitPerformReg.setCaseId(caseId);
        // 诉讼余额-金额格式化
        MfLawsuit mfLawsuit = new MfLawsuit();
        mfLawsuit.setCaseId(caseId);
        mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
        Double sumAmt = 0.00;
        String performAmt = "0.00";
        Double unallocatedAmt = 0.00;
        if(mfLawsuit.getRecoverableAmount()!=null && mfLawsuit.getRecoverableAmount()>0){
            sumAmt = mfLawsuit.getRecoverableAmount();
        }else {
            mfLawsuitFollow.setCaseId(caseId);
            List<MfLawsuitFollow> followList = mfLawsuitFollowFeign.getFollowListByCaseId(mfLawsuitFollow);
            if(followList.size()>0){
                for(MfLawsuitFollow follows : followList){
                    if("4".equals(follows.getFollowType())){
                        sumAmt =MathExtend.add(sumAmt,follows.getActAmt());
                    }
                }
            }
        }
        List<MfLawsuitPerformReg> performRegList = mfLawsuitPerformRegFeign.getPerformRegList(mfLawsuitPerformReg);
        if(performRegList !=null){
            for(MfLawsuitPerformReg performRegs : performRegList){
                performAmt = MathExtend.adds(performAmt,performRegs.getPerformPrcp().toString()/*,performRegs.getPerformIntst().toString(),performRegs.getPerformCost().toString()*/);
            }
        }
        unallocatedAmt = MathExtend.subtract(sumAmt,Double.valueOf(performAmt));
        if(unallocatedAmt<0){
            unallocatedAmt = 0.00;
        }
        return  unallocatedAmt;
    }
}

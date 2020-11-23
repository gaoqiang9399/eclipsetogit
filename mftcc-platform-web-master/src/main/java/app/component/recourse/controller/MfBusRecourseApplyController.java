package  app.component.recourse.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.entity.MfBusCompensatoryDoc;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.recourse.entity.MfBusRecourseConfirm;
import app.component.recourse.feign.MfBusRecourseConfirmFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.*;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.recourse.entity.MfBusRecourseApply;
import app.component.recourse.feign.MfBusRecourseApplyFeign;
import cn.mftcc.common.MessageEnum;
import config.YmlConfig;

/**
 * Title: MfBusRecourseApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:39:17 CST 2018
 **/
@Controller
@RequestMapping("/mfBusRecourseApply")
public class MfBusRecourseApplyController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusRecourseApplyFeign mfBusRecourseApplyFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusRecourseConfirmFeign mfBusRecourseConfirmFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String fincId,String compensatoryId,String recourseId) throws Exception {//input
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrecourseApply0001 = formService.getFormData("recourseApply0001");
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		mfBusRecourseApply.setCompensatoryId(compensatoryId);
		mfBusRecourseApply.setFincId(fincId);
		mfBusRecourseApply.setRecourseId(recourseId);
		mfBusRecourseApply = mfBusRecourseApplyFeign.getById(mfBusRecourseApply);

		MfBusCompensatoryDoc mfBusCompensatoryDoc = new MfBusCompensatoryDoc();
		mfBusCompensatoryDoc.setCompensatoryId(compensatoryId);
		List<MfBusCompensatoryDoc> mfBusCompensatoryDocList = mfBusCompensatoryApplyFeign.getCompensatoryDocList(mfBusCompensatoryDoc);
		//获取代偿信息
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(compensatoryId);
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		mfBusRecourseApply.setCompensatoryDate(mfBusCompensatoryApply.getCompensatoryDate());
		getObjValue(formrecourseApply0001, mfBusFincApp);
		getObjValue(formrecourseApply0001, mfBusRecourseApply);
		model.addAttribute("formrecourseApply0001", formrecourseApply0001);
		model.addAttribute("query", "");
		model.addAttribute("recourseId", mfBusRecourseApply.getRecourseId());
		model.addAttribute("mfBusCompensatoryApply", mfBusRecourseApply);
        model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		model.addAttribute("mfBusCompensatoryDocList", mfBusCompensatoryDocList);
		return "/component/recourse/MfBusRecourseApply_Detail";
	}

	@RequestMapping("/inputRec")
	public String inputRec(Model model,String fincId,String compensatoryId) throws Exception {//input
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrecourseApply0001 = formService.getFormData("recourseApply0001");
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		mfBusRecourseApply.setRecourseId(WaterIdUtil.getWaterId());
		mfBusRecourseApply.setCompensatoryId(compensatoryId);
		mfBusRecourseApply.setFincId(fincId);
		mfBusRecourseApply = mfBusRecourseApplyFeign.getByCompensatoryId(mfBusRecourseApply);
		MfBusCompensatoryDoc mfBusCompensatoryDoc = new MfBusCompensatoryDoc();
		mfBusCompensatoryDoc.setCompensatoryId(compensatoryId);
		List<MfBusCompensatoryDoc> mfBusCompensatoryDocList = mfBusCompensatoryApplyFeign.getCompensatoryDocList(mfBusCompensatoryDoc);
		//获取代偿信息
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(compensatoryId);
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		mfBusRecourseApply.setCompensatoryDate(mfBusCompensatoryApply.getCompensatoryDate());
		Double amt = mfBusRecourseApplyFeign.getRecourseAmtSum(mfBusRecourseApply);
		mfBusRecourseApply.setExt1(String.valueOf(amt));//已追偿金额
		getObjValue(formrecourseApply0001, mfBusRecourseApply);
		getObjValue(formrecourseApply0001, mfBusFincApp);
		model.addAttribute("formrecourseApply0001", formrecourseApply0001);
		model.addAttribute("query", "");
		model.addAttribute("mfBusCompensatoryApply", mfBusRecourseApply);
		model.addAttribute("recourseId", mfBusRecourseApply.getRecourseId());
		model.addAttribute("mfBusCompensatoryDocList", mfBusCompensatoryDocList);
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		return "/component/recourse/MfBusRecourseApplyRec_Detail";
	}
	
	/**
	 * 保存追偿申请信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formrecourseApply0001 = formService.getFormData(formId);
			getFormValue(formrecourseApply0001, getMapByJson(ajaxData));
			if(this.validateFormData(formrecourseApply0001)){
				MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
				setObjValue(formrecourseApply0001, mfBusRecourseApply);
				mfBusRecourseApply = mfBusRecourseApplyFeign.insert(mfBusRecourseApply);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfBusRecourseApply.getApproveNodeName());
				paramMap.put("opNo", mfBusRecourseApply.getApprovePartName());
				if(StringUtil.isNotEmpty(mfBusRecourseApply.getApproveNodeName())){
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				}else {
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}

	/**
	 * 保存追偿确认信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjaxRec")
	@ResponseBody
	public Map<String,Object> insertAjaxRec(String ajaxData)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formrecourseConfirm0001 = formService.getFormData(formId);
			getFormValue(formrecourseConfirm0001, getMapByJson(ajaxData));
			if(this.validateFormData(formrecourseConfirm0001)){
				MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
                MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
				setObjValue(formrecourseConfirm0001, mfBusRecourseConfirm);
                setObjValue(formrecourseConfirm0001, mfBusRecourseApply);
				mfBusRecourseApply = mfBusRecourseApplyFeign.insertRec(mfBusRecourseApply);
				mfBusRecourseConfirm.setRecourseId(mfBusRecourseApply.getRecourseId());
				mfBusRecourseConfirmFeign.insert(mfBusRecourseConfirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
				return dataMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}
	
	@RequestMapping("/getRecourseType")
	@ResponseBody
	public Map<String,Object> getRecourseType(String fincId)throws Exception{
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		mfBusRecourseApply.setFincId(fincId);
		List<MfBusRecourseApply> mfBusRecourseApplyList = mfBusRecourseApplyFeign.getRecourseList(mfBusRecourseApply);
		if (mfBusRecourseApplyList != null && mfBusRecourseApplyList.size()>0) {
			mfBusRecourseApply = mfBusRecourseApplyList.get(0);
			dataMap.put("appSts", mfBusRecourseApply.getAppSts());
			dataMap.put("recourseId", mfBusRecourseApply.getRecourseId());
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag","error");
		}
		return dataMap;
	}

	/**
	 * 计算追偿总额   公式：代偿金额 * ( 追偿日期 - 代偿日期 ) * 追偿违约金费率
	 * @param recoverDate  追偿日期
	 * @param compensatoryDate  代偿日期
	 * @param recourseFee    代偿总额
	 * @param recoursePenaltyFee  追偿违约金费率
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRecourseDate")
	@ResponseBody
	public Map<String, Object> getRecourseDate (String recoverDate, String compensatoryDate, String recourseFee, String recoursePenaltyFee) throws Exception{
		Map<String,Object> dataMap = new HashMap<String, Object>();
		Integer differDate = DateUtil.getDaysBetween(compensatoryDate, recoverDate);
		String  recourseTotel = MathExtend.multiply(MathExtend.multiply(recourseFee, MathExtend.divide(recoursePenaltyFee,"100" , 6)), differDate.toString());
		recourseTotel = MathExtend.round(recourseTotel, 2);
		String  totel = MathExtend.add(recourseTotel, recourseFee);
		dataMap.put("differDate", differDate);
		dataMap.put("totel", totel);
		return dataMap;
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
		String view =request.getParameter("view");
		model.addAttribute("query", "");
		model.addAttribute("view", view);
		return "/component/recourse/MfBusRecourseApply_List";
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
        MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
        try {
			mfBusRecourseApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusRecourseApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusRecourseApply.setCriteriaList(mfBusRecourseApply, ajaxData);// 我的筛选
            String isOpen = PropertiesUtil.getCloudProperty("cloud.loanafter_filter");
            Ipage ipage = this.getIpage();
            ipage.setPageSize(pageSize);
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            // 自定义查询Bo方法
            ipage.setParams(this.setIpageParams("mfBusRecourseApply", mfBusRecourseApply));
            //ipage = mfBusFincAppFeign.getBusRecourseApplyFindByPage(ipage);
			ipage = mfBusRecourseApplyFeign.findByPage(ipage);
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
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formcusCourtInfoBase = formService.getFormData(formId);
			getFormValue(formcusCourtInfoBase, map);
			if (this.validateFormData(formcusCourtInfoBase)) {

				setObjValue(formcusCourtInfoBase, mfBusRecourseApply);
				mfBusRecourseApplyFeign.update(mfBusRecourseApply);

				String cusNo = mfBusRecourseApply.getCusNo();
				mfBusRecourseApply.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfBusRecourseApply", mfBusRecourseApply));
				String tableHtml = jtu.getJsonStr("tablebusrecourseapply", "tableTag",
						(List<MfBusRecourseApply>) mfBusRecourseApplyFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
}

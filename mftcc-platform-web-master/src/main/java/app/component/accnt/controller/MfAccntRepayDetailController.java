package app.component.accnt.controller;

import app.component.accnt.entity.MfAccntRepayDetail;
import app.component.accnt.feign.MfAccntRepayDetailFeign;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfAccntRepayDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 25 18:06:12 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfAccntRepayDetail")
public class MfAccntRepayDetailController extends BaseFormBean {
	// 注入MfAccntRepayDetailBo
	@Autowired
	private MfAccntRepayDetailFeign mfAccntRepayDetailFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;
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
		return "/component/accnt/MfAccntRepayDetail_List";
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
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		try {
			mfAccntRepayDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAccntRepayDetail.setCriteriaList(mfAccntRepayDetail, ajaxData);// 我的筛选
			// this.getRoleConditions(mfAccntRepayDetail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAccntRepayDetailFeign.findByPage(ipage);
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
	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String transferId,String repayDetailId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<String,Object>();
		String formId="";
		String query="";
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(transferId);
		pledgeBaseInfo = collateralInterfaceFeign.getById(pledgeBaseInfo);
		if (pledgeBaseInfo!=null){
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType("","MfAccntRepayDetailAction", pledgeBaseInfo.getClassFirstNo());
			if(mfCollateralFormConfig == null){

			}else{
				formId = mfCollateralFormConfig.getShowModelDef();
			}
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}else{
			Map<String,Object> formData = new HashMap<String,Object>();
			request.setAttribute("ifBizManger", "3");
			request.setAttribute("query", "query");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusbank00002 = formService.getFormData(formId);
			MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
			mfAccntRepayDetail.setRepayDetailId(repayDetailId);
			mfAccntRepayDetail = mfAccntRepayDetailFeign.getById(mfAccntRepayDetail);
			getObjValue(formcusbank00002, mfAccntRepayDetail,formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusbank00002,"propertySeeTag",query);
			if(mfAccntRepayDetail!=null){
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfAccntRepayDetail);
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
			FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
			getFormValue(formaccntrepay0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccntrepay0002)) {
				MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
				setObjValue(formaccntrepay0002, mfAccntRepayDetail);
				mfAccntRepayDetailFeign.insert(mfAccntRepayDetail);
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
		FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
		getFormValue(formaccntrepay0002, getMapByJson(ajaxData));
		MfAccntRepayDetail mfAccntRepayDetailJsp = new MfAccntRepayDetail();
		setObjValue(formaccntrepay0002, mfAccntRepayDetailJsp);
		MfAccntRepayDetail mfAccntRepayDetail = mfAccntRepayDetailFeign.getById(mfAccntRepayDetailJsp);
		if (mfAccntRepayDetail != null) {
			try {
				mfAccntRepayDetail = (MfAccntRepayDetail) EntityUtil.reflectionSetVal(mfAccntRepayDetail,
						mfAccntRepayDetailJsp, getMapByJson(ajaxData));
				mfAccntRepayDetailFeign.update(mfAccntRepayDetail);
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
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		try {
			FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
			getFormValue(formaccntrepay0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccntrepay0002)) {
				mfAccntRepayDetail = new MfAccntRepayDetail();
				setObjValue(formaccntrepay0002, mfAccntRepayDetail);
				mfAccntRepayDetailFeign.update(mfAccntRepayDetail);
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
	public Map<String, Object> getByIdAjax(String repayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setRepayDetailId(repayDetailId);
		mfAccntRepayDetail = mfAccntRepayDetailFeign.getById(mfAccntRepayDetail);
		getObjValue(formaccntrepay0002, mfAccntRepayDetail, formData);
		if (mfAccntRepayDetail != null) {
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
	public Map<String, Object> deleteAjax(String repayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setRepayDetailId(repayDetailId);
		try {
			mfAccntRepayDetailFeign.delete(mfAccntRepayDetail);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
		model.addAttribute("formaccntrepay0002", formaccntrepay0002);
		model.addAttribute("query", "");
		return "/component/accnt/MfAccntRepayDetail_Insert";
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
		FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
		getFormValue(formaccntrepay0002);
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		setObjValue(formaccntrepay0002, mfAccntRepayDetail);
		mfAccntRepayDetailFeign.insert(mfAccntRepayDetail);
		getObjValue(formaccntrepay0002, mfAccntRepayDetail);
		this.addActionMessage(model, "保存成功");
		List<MfAccntRepayDetail> mfAccntRepayDetailList = (List<MfAccntRepayDetail>) mfAccntRepayDetailFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formaccntrepay0002", formaccntrepay0002);
		model.addAttribute("mfAccntRepayDetailList", mfAccntRepayDetailList);
		return "/component/accnt/MfAccntRepayDetail_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String repayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccntrepay0001 = formService.getFormData("accntrepay0001");
		getFormValue(formaccntrepay0001);
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setRepayDetailId(repayDetailId);
		mfAccntRepayDetail = mfAccntRepayDetailFeign.getById(mfAccntRepayDetail);
		getObjValue(formaccntrepay0001, mfAccntRepayDetail);
		model.addAttribute("formaccntrepay0001", formaccntrepay0001);
		return "/component/accnt/MfAccntRepayDetail_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String repayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setRepayDetailId(repayDetailId);
		mfAccntRepayDetailFeign.delete(mfAccntRepayDetail);
		return getListPage();
	}

	@ResponseBody
	@RequestMapping(value = "/getRepayBytransferId")
	public Map<String, Object> getRepayBytransferId(String transferId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setTransferId(transferId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		getTableData(tableId, mfAccntRepayDetail, dataMap);
		dataMap.put("flag", "success");
		dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		return dataMap;
	}

	private void getTableData(String tableId, MfAccntRepayDetail mfAccntRepayDetail, Map<String, Object> dataMap)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		List<MfAccntRepayDetail> mfAccntRepayDetailList = mfAccntRepayDetailFeign.getList(mfAccntRepayDetail);
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfAccntRepayDetailFeign.getList(mfAccntRepayDetail),
				null, true);
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
		FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
		getFormValue(formaccntrepay0002);
		boolean validateFlag = this.validateFormData(formaccntrepay0002);
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
		FormData formaccntrepay0002 = formService.getFormData("accntrepay0002");
		getFormValue(formaccntrepay0002);
		boolean validateFlag = this.validateFormData(formaccntrepay0002);
	}

}

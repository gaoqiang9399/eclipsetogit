package app.component.tuning.controller;

import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditTuningDetail;
import app.component.prdct.entity.MfSysKind;
import app.component.tour.entity.MfBusTour;
import app.component.tuning.feign.MfCusCreditTuningDetailFeign;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * Title: MfBusFincAppChildAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat May 26 13:09:22 CST 2018
 **/

@Controller
@RequestMapping("/mfCusCreditTuningDetail")
public class MfCusCreditTuningDetailController extends BaseFormBean{
	@Autowired
	private MfCusCreditTuningDetailFeign mfCusCreditTuningDetailFeign;
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
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/tour/MfCusCreditTuningDetail_List";
	}

	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,String delType,String creditAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditTuningDetail mfCusCreditTuningDetail = new MfCusCreditTuningDetail();
		try {
			mfCusCreditTuningDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCreditTuningDetail.setCriteriaList(mfCusCreditTuningDetail, ajaxData);// 我的筛选
			mfCusCreditTuningDetail.setCustomSorts(ajaxData);// 自定义排序
			mfCusCreditTuningDetail.setDelType(delType);
			mfCusCreditTuningDetail.setCreditAppId(creditAppId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfCusCreditTuningDetail", mfCusCreditTuningDetail));
			ipage = mfCusCreditTuningDetailFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
			dataMap.put("tableHtml", tableHtml);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 授信时添加合作银行授信额度
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/input")
	@ResponseBody
	public Map<String,Object> input(String creditAppId,String delType)throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map dataMap = new HashMap<>();
		String formId="creditTuningBase";
		switch (delType){
			case "1":
				formId="creditTuningBase1";
				break;
			case "2":
				formId="creditTuningBase2";
				break;
			case "3":
				formId="creditTuningBase3";
				break;
			case "4":
				formId="creditTuningBase4";
				break;
			case "5":
				formId="creditTuningBase5";
				break;
		}
		try {
			FormData formcreditTuningBase = formService.getFormData(formId);
			changeFormProperty(formcreditTuningBase, "creditAppId", "initValue", creditAppId);
			changeFormProperty(formcreditTuningBase, "delType", "initValue", delType);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String formHtml = jsonFormUtil.getJsonStr(formcreditTuningBase, "bootstarpTag", "");
			dataMap.put("flag", "success");
			dataMap.put("formHtml", formHtml);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * @param ajaxData
	 * @param agenciesArrs
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map <String, Object> insertAjax(String ajaxData,String creditAppId,String delType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map <String, Object> dataMap = new HashMap <String, Object>();
		MfCusCreditTuningDetail mfCusCreditTuningDetail = new MfCusCreditTuningDetail();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formcreditapply0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditapply0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditapply0001)) {
				setObjValue(formcreditapply0001, mfCusCreditTuningDetail);
				//插入信息
				mfCusCreditTuningDetailFeign.insert(mfCusCreditTuningDetail);
				dataMap.put("creditAppId", mfCusCreditTuningDetail.getCreditAppId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 *
	 * @param creditApproveId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditTuningDetail mfCusCreditTuningDetail = new MfCusCreditTuningDetail();
		mfCusCreditTuningDetail.setId(id);
		try {
			mfCusCreditTuningDetail = mfCusCreditTuningDetailFeign.getById(mfCusCreditTuningDetail);
			dataMap.put("delType",mfCusCreditTuningDetail.getDelType());
			mfCusCreditTuningDetailFeign.delete(mfCusCreditTuningDetail);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
}

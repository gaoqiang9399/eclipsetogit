package  app.component.alliance.controller;

import app.component.alliance.entity.MfBusAlliance;
import app.component.alliance.entity.MfBusAllianceCustomer;
import app.component.alliance.entity.MfBusAllianceTakeupHis;
import app.component.alliance.feign.MfBusAllianceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusBorrowerInfo;
import app.component.cus.feign.MfCusBorrowerInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusAllianceController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Nov 24 12:29:29 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfBusAlliance")
public class MfBusAllianceController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private MfBusAllianceFeign mfBusAllianceFeign;

	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;

	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;

	@Autowired
	private MfCusBorrowerInfoFeign mfCusBorrowerInfoFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/alliance/MfBusAlliance_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		try {
			mfBusAlliance.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAlliance.setCriteriaList(mfBusAlliance, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBusAlliance", mfBusAlliance));
			//自定义查询Feign方法
			ipage = mfBusAllianceFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findCustomerByPageAjax")
	public Map<String, Object> findCustomerByPageAjax(String ajaxData,String areaCode, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		try {
			mfBusAlliance.setAreaCode(areaCode);
			mfBusAlliance.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAlliance.setCriteriaList(mfBusAlliance, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBusAlliance", mfBusAlliance));
			//自定义查询Feign方法
			ipage = mfBusAllianceFeign.findCustomerByPageAjax(ipage);
			//JsonTableUtil jtu = new JsonTableUtil();
			//String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			//ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			FormData formalliance0002 = formService.getFormData("alliance0002");
			getFormValue(formalliance0002, getMapByJson(ajaxData));
			if(this.validateFormData(formalliance0002)){
				MfBusAlliance mfBusAlliance = new MfBusAlliance();
				setObjValue(formalliance0002, mfBusAlliance);
				List<MfBusAlliance> mfBusAllianceList = mfBusAllianceFeign.getList(mfBusAlliance);
                //MfBusAlliance temp = mfBusAllianceFeign.getByName(mfBusAlliance);
                //if(temp==null){
				if(mfBusAllianceList != null && mfBusAllianceList.size() > 0)
				{
					for(MfBusAlliance mfBusAlliance1 : mfBusAllianceList)
					{
						if(mfBusAlliance1.getAllianceName().equals(mfBusAlliance.getAllianceName()))
						{
							dataMap.put("flag", "error");
							dataMap.put("msg", "联保体名称已存在！");
							return dataMap;
						}
					}
				}
                    mfBusAllianceFeign.insert(mfBusAlliance);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", "新增成功");
                /*}else{
                    dataMap.put("flag", "error");
                    dataMap.put("msg","联保体名称已存在");
                }*/

			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertMfBusAllianceCustomerAjax")
	@ResponseBody
	public Map<String, Object> insertMfBusAllianceCustomerAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
            FormData formalliancecustomer0001 = formService.getFormData("alliancecustomer0001");
            getFormValue(formalliancecustomer0001, getMapByJson(ajaxData));
            MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
            setObjValue(formalliancecustomer0001, mfBusAllianceCustomer);
			MfBusAlliance mfBusAlliance = mfBusAllianceFeign.getAllianceByCusNo(mfBusAllianceCustomer.getCusId());
			if(mfBusAlliance==null){
				mfBusAllianceFeign.insertMfBusAllianceCustomer(mfBusAllianceCustomer);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", "该客户已存在本联保体或其它联保体中");
			}

		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formalliance0002 = formService.getFormData("allianceDetail");
		getFormValue(formalliance0002, getMapByJson(ajaxData));
		MfBusAlliance mfBusAllianceJsp = new MfBusAlliance();
		setObjValue(formalliance0002, mfBusAllianceJsp);
		MfBusAlliance mfBusAlliance = mfBusAllianceFeign.getById(mfBusAllianceJsp);
		if(mfBusAlliance!=null){
			try{
				mfBusAlliance = (MfBusAlliance)EntityUtil.reflectionSetVal(mfBusAlliance, mfBusAllianceJsp, getMapByJson(ajaxData));
				mfBusAllianceFeign.update(mfBusAlliance);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCustomerAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateCustomerAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formalliancecustomer0002 = formService.getFormData("alliancecustomer0002");
		getFormValue(formalliancecustomer0002, getMapByJson(ajaxData));
		MfBusAllianceCustomer mfBusAllianceCustomerJsp = new MfBusAllianceCustomer();
		setObjValue(formalliancecustomer0002, mfBusAllianceCustomerJsp);
		MfBusAllianceCustomer mfBusAllianceCustomer = mfBusAllianceFeign.getCustomerById(mfBusAllianceCustomerJsp);
		if(mfBusAllianceCustomer!=null){
			try{
				mfBusAllianceCustomer = (MfBusAllianceCustomer)EntityUtil.reflectionSetVal(mfBusAllianceCustomer, mfBusAllianceCustomerJsp, getMapByJson(ajaxData));
				mfBusAllianceFeign.updateAllianceCustomer(mfBusAllianceCustomer);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		try{
			FormData formalliance0002 = formService.getFormData("alliance0002");
			getFormValue(formalliance0002, getMapByJson(ajaxData));
			if(this.validateFormData(formalliance0002)){
				mfBusAlliance = new MfBusAlliance();
				setObjValue(formalliance0002, mfBusAlliance);
				//mfBusAllianceFeign.update(mfBusAlliance);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAllianceCustomerAjax")
	@ResponseBody
	public Map<String, Object> updateAllianceCustomerAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
		try{
			FormData formalliancecustomer0002 = formService.getFormData("alliancecustomer0002");
			getFormValue(formalliancecustomer0002, getMapByJson(ajaxData));
			if(this.validateFormData(formalliancecustomer0002)){
				setObjValue(formalliancecustomer0002, mfBusAllianceCustomer);
				mfBusAllianceFeign.updateAllianceCustomer(mfBusAllianceCustomer);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowCustomerDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowCustomerDetailAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
		mfBusAllianceCustomer.setId(id);
		mfBusAllianceCustomer = mfBusAllianceFeign.getCustomerById(mfBusAllianceCustomer);
		String formId="alliancecustomer0002";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "3");
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formalliancecustomer0002 = formService.getFormData(formId);
		getObjValue(formalliancecustomer0002, mfBusAllianceCustomer);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formalliancecustomer0002, "propertySeeTag", "");
		dataMap.put("query", "");
		if (mfBusAllianceCustomer != null) {
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfBusAllianceCustomer);
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formalliance0002 = formService.getFormData("alliance0002");
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(id);
		//mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);
		getObjValue(formalliance0002, mfBusAlliance,formData);
		if(mfBusAlliance!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(id);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("allianceId",id);
			if(mfBusAllianceFeign.validateDelete(paramMap)){
                mfBusAllianceFeign.delete(mfBusAlliance);
                dataMap.put("flag", "success");
                dataMap.put("msg", "成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "由于该联保体有担保信息，不可删除");
            }

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax异步解散
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dismissAjax")
	@ResponseBody
	public Map<String, Object> dismissAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(id);
		mfBusAlliance.setValidFlag("0");
		try {
			mfBusAllianceFeign.update(mfBusAlliance);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formalliance0002 = formService.getFormData("alliance0002");
		model.addAttribute("formalliance0002", formalliance0002);
		model.addAttribute("query", "");
		return "/component/alliance/MfBusAlliance_Insert";
	}

    /**
     * 新增联保体成员页面
     * @return
     * @throws Exception
     */
    @RequestMapping("/alliancecustomerinput")
    public String alliancecustomerinput(Model model,String allianceId) throws Exception{
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(allianceId);
		mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);
        FormData alliancecustomer0001 = formService.getFormData("alliancecustomer0001");

        this.changeFormProperty(alliancecustomer0001, "allianceId", "initValue", allianceId);
		this.changeFormProperty(alliancecustomer0001, "areaCode", "initValue", mfBusAlliance.getAreaCode());

        model.addAttribute("formalliancecustomer0001", alliancecustomer0001);
        model.addAttribute("query", "");
        return "/component/alliance/MfBusAllianceCustomer_Insert";
    }
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formalliance0001 = formService.getFormData("alliance0001");
		getFormValue(formalliance0001);
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(id);
		//mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);
		getObjValue(formalliance0001, mfBusAlliance);
		model.addAttribute("formalliance0001", formalliance0001);
		model.addAttribute("query", "");
		return "/component/alliance/MfBusAlliance_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllianceCustomerById")
	public String getAllianceCustomerById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formalliancecustomer0002 = formService.getFormData("alliancecustomer0002");
		getFormValue(formalliancecustomer0002);
		MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
		mfBusAllianceCustomer.setId(id);
		mfBusAllianceCustomer = mfBusAllianceFeign.getCustomerById(mfBusAllianceCustomer);
		getObjValue(formalliancecustomer0002, mfBusAllianceCustomer);
		model.addAttribute("formalliancecustomer0002", formalliancecustomer0002);
		model.addAttribute("query", "");
		return "/component/alliance/MfBusAllianceCustomer_Detail";
	}

	/**
	 * 调视角
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllianceView")
	public String getAllianceView(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		try {
			// 动态业务视图参数封装
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("allianceId", id);

			MfBusAlliance mfBusAlliance = new MfBusAlliance();
			mfBusAlliance.setId(id);
			mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);
			model.addAttribute("alliance",mfBusAlliance);
			String generalClass = "bus";
			String busEntrance="alliance";
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.putAll(cusViewMap);
			model.addAttribute("dataMap", dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return "/component/alliance/MfBusAlliance_View";
	}





	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formalliance0002 = formService.getFormData("alliance0002");
		getFormValue(formalliance0002);
		boolean validateFlag = this.validateFormData(formalliance0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formalliance0002 = formService.getFormData("alliance0002");
		getFormValue(formalliance0002);
		boolean validateFlag = this.validateFormData(formalliance0002);
	}

	/**
	 * 方法描述： 获取联保体详情表单HTML
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author liumeihao
	 * @date
	 */
	@RequestMapping("/getAllianceDetailFormAjax")
	@ResponseBody
	public Map<String, Object> getAllianceDetailFormAjax(String id, String formId)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();


		// 联保体信息
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(id);
		mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);

		FormData form = formService.getFormData(formId);
		getFormValue(form);

		getObjValue(form, mfBusAlliance);
		request.setAttribute("ifBizManger", "3");
		String query ="";
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(form, "propertySeeTag", query);
		dataMap.put("htmlStr", htmlStr);
		dataMap.put("query", query);
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得成员列表html
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author liumeihao
	 * @date
	 */
	@RequestMapping("/getMemberListAjax")
	@ResponseBody
	public Map<String, Object> getPutOutHisListAjax(String allianceId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
		mfBusAllianceCustomer.setAllianceId(allianceId);
		JsonTableUtil jtu = new JsonTableUtil();
		List<Map<String,Object>> list = mfBusAllianceFeign.findAllianceCus(mfBusAllianceCustomer);
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
		return dataMap;
	}

	/**
	 *
	 * 方法描述：获取额度变更信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘美好
	 * @date
	 */
	@RequestMapping(value = "/getTakeupListAjax")
	@ResponseBody
	public Map<String, Object> getTakeupListAjax(String allianceId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusAllianceTakeupHis mfBusAllianceTakeupHis = new MfBusAllianceTakeupHis();
			mfBusAllianceTakeupHis.setAllianceId(allianceId);
			List<MfBusAllianceTakeupHis> takeupListList = mfBusAllianceFeign.getTakeupList(mfBusAllianceTakeupHis);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", takeupListList, null, true);
			dataMap.put("tableData", tableHtml);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 追加额度页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/allianceTakupHisInput")
	public String allianceTakupHisInput(Model model,String allianceId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData form = formService.getFormData("alliancetakeuphis0001");
		getFormValue(form);
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(allianceId);
		mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);
		MfBusAllianceTakeupHis mfBusAllianceTakeupHis = new MfBusAllianceTakeupHis();
		mfBusAllianceTakeupHis.setAllianceId(allianceId);
		mfBusAllianceTakeupHis.setLoanLimit(mfBusAlliance.getLoanLimit());
		mfBusAllianceTakeupHis.setAvailableBalance(mfBusAlliance.getAvailableBalance());
		getObjValue(form, mfBusAllianceTakeupHis);
		model.addAttribute("formalliancetakeuphis0001", form);
		model.addAttribute("query", "");
		return "/component/alliance/MfBusAllianceTakeupHis_Insert";
	}

	/**
	 * AJAX新增额度占用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertMfBusAllianceTakeupHisAjax")
	@ResponseBody
	public Map<String, Object> insertMfBusAllianceTakeupHisAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			FormData form = formService.getFormData("alliancetakeuphis0001");
			getFormValue(form, getMapByJson(ajaxData));
			if(this.validateFormData(form)){
				MfBusAllianceTakeupHis mfBusAllianceTakeupHis = new MfBusAllianceTakeupHis();
				setObjValue(form, mfBusAllianceTakeupHis);
				if("2".equals(mfBusAllianceTakeupHis.getType())){
					MfBusAlliance mfBusAlliance = new MfBusAlliance();
					mfBusAlliance.setId(mfBusAllianceTakeupHis.getAllianceId());
					mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);
					if(mfBusAllianceTakeupHis.getAppendLimit()>mfBusAlliance.getAvailableBalance()){
						dataMap.put("flag", "error");
						dataMap.put("msg", "减少额度不能大于可用额度");
						return dataMap;
					}
				}
				mfBusAllianceFeign.insertMfBusAllianceTakeupHis(mfBusAllianceTakeupHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}


	@RequestMapping("/getByIdAlliance")
	@ResponseBody
	public MfBusAlliance getByIdAlliance(String allianceId) throws Exception{
		ActionContext.initialize(request, response);

		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		mfBusAlliance.setId(allianceId);
		mfBusAlliance = mfBusAllianceFeign.getById(mfBusAlliance);

		return mfBusAlliance;
	}



	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAllianceCustomerAjax")
	@ResponseBody
	public Map<String, Object> deleteAllianceCustomerAjax(String id,String allianceId,String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
		mfBusAllianceCustomer.setId(id);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("allianceId",allianceId);
			paramMap.put("cusNo",cusNo);
		    if(mfBusAllianceFeign.validateDelete(paramMap)){
                mfBusAllianceFeign.deleteCustomer(mfBusAllianceCustomer);
                dataMap.put("flag", "success");
                dataMap.put("msg", "成功");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", "联保体有担保，不可删除成员");
            }

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 *获取历史数据统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCompleteBusDataAjax")
	@ResponseBody
	public Map<String, Object> getCompleteBusDataAjax(String allianceId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		    MfBusAlliance alliance = new MfBusAlliance();
		    alliance.setId(allianceId);
			dataMap = mfBusAllianceFeign.getTotalApplyDataByAllianceId(alliance);
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
	 * 历史申请查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFinshListPage")
	public String getFinshListPage(Model model,String allianceId) throws Exception{
		ActionContext.initialize(request, response);
		model.addAttribute("allianceId",allianceId);
		return "/component/alliance/MfBusAlliance_Finish_List";
	}

	/***
	 * 获取联保体使用历史记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/getFinishedListByPageAjax")
	public Map<String, Object> getFinishedListByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize,String allianceId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		try {
			mfBusAlliance.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAlliance.setExt6(ajaxData);
			mfBusAlliance.setCriteriaList(mfBusAlliance, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			mfBusAlliance.setId(allianceId);
			ipage.setParams(this.setIpageParams("mfBusAlliance", mfBusAlliance));
			//自定义查询Feign方法
			ipage = mfBusAllianceFeign.getFinishedListByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

    /***
     * 设置组长
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/setLeader")
    public Map<String,Object> setLeader(String allianceCustomerId) throws Exception {
        ActionContext.initialize(request, response);
       Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
            mfBusAllianceCustomer.setId(allianceCustomerId);
            mfBusAllianceFeign.setLeader(mfBusAllianceCustomer);
            dataMap.put("flag", "success");
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", "失败");
            throw e;
        }
        return dataMap;

    }
    /***
     * 查询联保体内除了当前客户外的所有客户
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/getOtherCus")
    public Map<String, Object> getOtherCus(int pageNo,String ajaxData,String cusNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusAlliance mfBusAlliance = new MfBusAlliance();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            mfBusAlliance.setId(cusNo);
            ipage.setParams(this.setIpageParams("mfBusAlliance", mfBusAlliance));
            ipage.setParamsStr(ajaxData);
            ipage = mfBusAllianceFeign.getOtherById(ipage);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
    /***
     * 根据客户号查询联保体剩余额度
     * 段泽宇
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getAllianceByCusNo")
    public Map<String, Object> getAllianceByCusNo(String cusNo,String assureAmt) throws Exception {
        ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();

		String ALLIANCE_AMT_CTL = new CodeUtils().getSingleValByKey("ALLIANCE_AMT_CTL");// 联保体额度控制
		if ("0".equals(ALLIANCE_AMT_CTL)) {// 不校验
			dataMap.put("flag", "success");
			return dataMap;
		}

        MfBusAlliance mfBusAlliance = new MfBusAlliance();
        try {
            mfBusAlliance = mfBusAllianceFeign.getAllianceByCusNo(cusNo);
            Double availableBalance = mfBusAlliance.getAvailableBalance();
            assureAmt = assureAmt.replaceAll(",","");
            double amt = Double.valueOf(assureAmt).doubleValue();
            double subtract = MathExtend.subtract(availableBalance, amt);
            double num = 0.0;
            if(subtract >= num){
                dataMap.put("flag","success");
            }else{
                dataMap.put("flag","error");
                dataMap.put("msg","联保体额度不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

	/***
	 * 根据客户号查询联保体相关信息
	 * 刘美好
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllianceInfoByCusNo")
	public Map<String, Object> getAllianceInfoByCusNo(String cusNo,String assureAmt, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAlliance mfBusAlliance = new MfBusAlliance();
		try {
			mfBusAlliance = mfBusAllianceFeign.getAllianceByCusNo(cusNo);
			if (mfBusAlliance == null) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "该客户未存在任何有效的联保体中");
				return dataMap;
			}

			MfBusAllianceCustomer mfBusAllianceCustomer = new MfBusAllianceCustomer();
			mfBusAllianceCustomer.setAllianceId(mfBusAlliance.getId());
			List<Map<String, Object>> list = mfBusAllianceFeign.findAllianceCus(mfBusAllianceCustomer);

			if (list.size() < 3) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "联保体成员未满3人，无法担保");
				return dataMap;
			}

			List<MfAssureInfo> mfAssureInfoList = mfCusCustomerFeign.getAssureListByAppid(appId);// 已填加的担保人
			for (MfAssureInfo assure : mfAssureInfoList) {
				for (Map<String, Object> alliance : list) {
					if (assure.getAssureNo().equals(alliance.get("cusId"))) {
						dataMap.put("flag", "error");
						dataMap.put("msg", "联保体成员已添加至担保人, 不能重复添加");
						return dataMap;
					}
				}
			}
            MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
			mfCusBorrowerInfo.setCusNo(cusNo);
            List<MfCusBorrowerInfo> mfCusBorrowerInfos = mfCusBorrowerInfoFeign.getAllBorrowerByCusNo(mfCusBorrowerInfo);
            String borrowNos = "";
            for (MfCusBorrowerInfo mfCusBorrowerInfo1: mfCusBorrowerInfos) {
                borrowNos = borrowNos + mfCusBorrowerInfo1.getBorrowNo() + ";";
            }
            List<Map<String, Object>> listNew = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < list.size(); i++) {
			    String cusId = list.get(i).get("cusId").toString();
				if (!cusNo.equals(cusId) && !borrowNos.contains(cusId)) {
                    listNew.add(list.get(i));
				}
			}
			dataMap.put("flag", "success");
			dataMap.put("list", listNew);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}

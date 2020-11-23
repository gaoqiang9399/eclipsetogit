package app.component.auth.controller;

import app.base.User;
import app.component.auth.entity.MfBusAgenciesPledgeRel;
import app.component.auth.entity.MfCusAgenciesCredit;
import app.component.auth.feign.MfBusAgenciesPledgeRelFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import org.apache.catalina.manager.util.SessionUtils;
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

@Controller
@RequestMapping("/mfBusAgenciesPledgeRel")
public class MfBusAgenciesPledgeRelController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusAgenciesPledgeRelFeign mfBusAgenciesPledgeRelFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String appId) throws Exception {
		ActionContext.initialize(request,response);
		String tableId="mfBusAgenciesPledgeRellist";
		model.addAttribute("tableId", tableId);
        model.addAttribute("appId", appId);
		// 获取展示名称
		return "component/agencies/MfBusAgenciesPledgeRel_List";
	}



	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType,String appId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
		try {
			mfBusAgenciesPledgeRel.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAgenciesPledgeRel.setCriteriaList(mfBusAgenciesPledgeRel, ajaxData);//我的筛选
			mfBusAgenciesPledgeRel.setCustomSorts(ajaxData);// 自定义排序参数赋值
            mfBusAgenciesPledgeRel.setAppId(appId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusAgenciesPledgeRel", mfBusAgenciesPledgeRel));
			ipage = mfBusAgenciesPledgeRelFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
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
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String ,Object> insertAjax(String ajaxData,String appId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfBusAgenciesPledgeRel0001 = formService.getFormData(formId);
			getFormValue(formmfBusAgenciesPledgeRel0001, getMapByJson(ajaxData));
			if(this.validateFormData(formmfBusAgenciesPledgeRel0001)){
				MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
				setObjValue(formmfBusAgenciesPledgeRel0001, mfBusAgenciesPledgeRel);
				mfBusAgenciesPledgeRel.setOpNo(User.getRegNo(request));
				mfBusAgenciesPledgeRel.setOpName(User.getRegName(request));
				mfBusAgenciesPledgeRel.setBrNo(User.getOrgNo(request));
				mfBusAgenciesPledgeRel.setBrName(User.getOrgName(request));
				mfBusAgenciesPledgeRel.setAppId(appId);
				mfBusAgenciesPledgeRelFeign.insert(mfBusAgenciesPledgeRel);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	


	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String agenciesId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formmfBusAgenciesPledgeRel0002 = formService.getFormData("mfBusAgenciesPledgeRel0002");
		MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
		mfBusAgenciesPledgeRel.setAgenciesId(agenciesId);
		mfBusAgenciesPledgeRel = mfBusAgenciesPledgeRelFeign.getById(mfBusAgenciesPledgeRel);
		getObjValue(formmfBusAgenciesPledgeRel0002, mfBusAgenciesPledgeRel,formData);
		if(mfBusAgenciesPledgeRel!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String agenciesId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
		mfBusAgenciesPledgeRel.setAgenciesId(agenciesId);
		try {
			mfBusAgenciesPledgeRelFeign.delete(mfBusAgenciesPledgeRel);
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
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjaxById")
	@ResponseBody
	public Map<String,Object> deleteAjaxById(String id) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
		mfBusAgenciesPledgeRel.setId(id);
		try {
			mfBusAgenciesPledgeRelFeign.delete(mfBusAgenciesPledgeRel);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String appId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String formId="mfBusAgenciesPledgeRel0001";
		FormData formmfBusAgenciesPledgeRel0001 = formService.getFormData(formId);
		getFormValue(formmfBusAgenciesPledgeRel0001);
		MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
		getObjValue(formmfBusAgenciesPledgeRel0001, mfBusAgenciesPledgeRel);
		model.addAttribute("baseform", formmfBusAgenciesPledgeRel0001);
		model.addAttribute("query", "");
        model.addAttribute("appId", appId);
		return "component/agencies/MfBusAgenciesPledgeRel_Insert";
	}


	/**
	 * 获取合作银行
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAgenciesListAjax")
	@ResponseBody
	public Map<String,Object> getAgenciesListAjax(String ajaxData,String creditAppId,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		try{
			MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
            mfCusAgenciesCredit.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusAgenciesCredit.setCreditAppId(creditAppId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusAgenciesCredit", mfCusAgenciesCredit));
			ipage =mfBusAgenciesPledgeRelFeign.findAgenciesListByPage(ipage);
			dataMap.put("ipage",ipage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}


    /**
     * 获取押品
     * @param kindNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPledgeListAjax")
    @ResponseBody
    public Map<String,Object> getPledgeListAjax(String ajaxData,String vouType,String creditAppId,int pageNo) throws Exception {
        ActionContext.initialize(request,response);
        Map<String,Object> dataMap=new HashMap<String,Object>();
        try{
            Map<String,String> parmMap=new HashMap<String,String>();
            parmMap.put("vouType",vouType);
            parmMap.put("collateralType","pledge");
            parmMap.put("appId",creditAppId);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            ipage =mfBusAgenciesPledgeRelFeign.getPledgeList(ipage,parmMap);
            dataMap.put("ipage",ipage);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataMap;
    }

	/**
	 * 校验押品是否关联合作银行
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkAgenciesPledgeRelAjax")
	@ResponseBody
	public Map<String,String> checkAgenciesPledgeRelAjax(String ajaxData,String creditAppId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,String> dataMap = new HashMap<String, String>();
		MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
		try {
		    if(StringUtil.isEmpty(creditAppId)){
                creditAppId = (String) getMapByJson(ajaxData).get("creditAppId");
            }
            mfBusAgenciesPledgeRel.setAppId(creditAppId);
            dataMap = mfBusAgenciesPledgeRelFeign.checkAgenciesPledgeRel(mfBusAgenciesPledgeRel);
		} catch (Exception e) {
            e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "校验押品是否关联合作银行失败");
			throw e;
		}
		return dataMap;
	}


}

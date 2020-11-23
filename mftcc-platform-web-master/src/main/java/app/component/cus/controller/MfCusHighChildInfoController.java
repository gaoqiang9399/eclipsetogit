package app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusHighChildInfo;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.feign.*;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import config.YmlConfig;
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
 * Title: MfCusHighChildInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue 09:01:54 CST 2020
 **/
@Controller
@RequestMapping("/mfCusHighChildInfo")
public class MfCusHighChildInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusHighChildInfoBo
	@Autowired
	private MfCusHighChildInfoFeign mfCusHighChildInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;

	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private YmlConfig ymlConfig;
	// 全局变量

	/**
	 * 根据类型和高管id查询子表
	 * @param highChildType
	 * @param highId
	 * @return
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Map<String,Object> getPageList(String highChildType,String highId){
		ActionContext.initialize(request, response);
      Map dataMap =  new HashMap<String,Object>();
       try {
		   MfCusHighChildInfo mfCusHighChildInfo=new MfCusHighChildInfo();
		   mfCusHighChildInfo.setHighChildType(highChildType);
		   mfCusHighChildInfo.setHighId(highId);
		   List<MfCusHighChildInfo> list = mfCusHighChildInfoFeign.findByEntity(mfCusHighChildInfo);
		   JsonTableUtil jtu = new JsonTableUtil();
		   String tableHtml="";
		   if(highChildType.equals("1")){//education
			    tableHtml = jtu.getJsonStr("tablehighChildEducationList", "tableTag", list, null, true);
		   }else if(highChildType.equals("2")){//work
			   tableHtml = jtu.getJsonStr("tablehighChildWorkList", "tableTag", list, null, true);
		   }else{//social
			   tableHtml = jtu.getJsonStr("tablehighChildSocialList", "tableTag", list, null, true);
		   }
		   dataMap.put("tableHtml", tableHtml);
		   dataMap.put("flag", "success");
	   }catch (Exception e){
       	    e.printStackTrace();
		   dataMap.put("flag", "error");
		   dataMap.put("msg", e.getMessage());
	   }
     return dataMap;
	}

	/**
	 * 打开新增页面
	 * @param highId
	 * @param highChildType
	 * @return
	 */
	@RequestMapping(value = "/highChildInput")
	@ResponseBody
	public Map<String,Object> highChildInput(String highId,String highChildType)throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map dataMap = new HashMap<>();
		String formId="";
		if(highChildType.equals("1")){//教育经验
			 formId = "cusHighChildInfoBase";
		}else if (highChildType.equals("2")){//工作经验
			formId = "cusHighChildInfoWorkBase";
		}else{//社会活动
          formId="cusHighChildInfoSocialBase";
		}
		try {
			FormData formcusHighChildInfoBase = formService.getFormData(formId);
			this.changeFormProperty(formcusHighChildInfoBase, "highId", "initValue", highId);
			this.changeFormProperty(formcusHighChildInfoBase, "highChildId", "initValue", WaterIdUtil.getWaterId());
			this.changeFormProperty(formcusHighChildInfoBase, "highChildType", "initValue", highChildType);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String formHtml = jsonFormUtil.getJsonStr(formcusHighChildInfoBase, "bootstarpTag", "");
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


	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formcusHighChildInfoBase = formService.getFormData(formId);
			getFormValue(formcusHighChildInfoBase, map);
			if (this.validateFormData(formcusHighChildInfoBase)) {
				 MfCusHighChildInfo mfCusHighChildInfo = new MfCusHighChildInfo();
				 setObjValue(formcusHighChildInfoBase, mfCusHighChildInfo);
				 mfCusHighChildInfoFeign.insert(mfCusHighChildInfo);
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
	 * 根据id查询高管信息子表
	 * @param highChildId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String highChildId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId="";
		try {
			MfCusHighChildInfo mfCusHighChildInfo = new MfCusHighChildInfo();
			mfCusHighChildInfo.setHighChildId(highChildId);
			mfCusHighChildInfo = mfCusHighChildInfoFeign.getById(mfCusHighChildInfo);
			if (mfCusHighChildInfo != null) {
				if (mfCusHighChildInfo.getHighChildType().equals("1")) {//教育
					formId = "cusHighChildInfoBase";
				} else if (mfCusHighChildInfo.getHighChildType().equals("2")) {//工作经验
					formId = "cusHighChildInfoWorkBase";
				} else {//社会活动
					formId = "cusHighChildInfoSocialBase";
				}
				FormData formcusHighChildInfoBase = formService.getFormData(formId);
				getObjValue(formcusHighChildInfoBase, mfCusHighChildInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String formHtml = jsonFormUtil.getJsonStr(formcusHighChildInfoBase, "bootstarpTag", "");
				dataMap.put("flag", "success");
				dataMap.put("formHtml", formHtml);
			} else {
				dataMap.put("flag", "error");
			}
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighChildInfo mfCusHighChildInfo = new MfCusHighChildInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formcusHighChildInfoBase = formService.getFormData(formId);
			getFormValue(formcusHighChildInfoBase, map);
			if (this.validateFormData(formcusHighChildInfoBase)) {
				mfCusHighChildInfo = new MfCusHighChildInfo();
				setObjValue(formcusHighChildInfoBase, mfCusHighChildInfo);
				mfCusHighChildInfoFeign.update(mfCusHighChildInfo);
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
	 * 删除
	 * @param highChildId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String highChildId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighChildInfo mfCusHighChildInfo = new MfCusHighChildInfo();
		mfCusHighChildInfo.setHighChildId(highChildId);
		try {
			mfCusHighChildInfoFeign.delete(mfCusHighChildInfo);
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
}

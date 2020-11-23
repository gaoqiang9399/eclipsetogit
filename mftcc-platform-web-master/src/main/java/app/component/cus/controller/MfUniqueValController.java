package  app.component.cus.controller;
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
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfUniqueVal;
import app.component.cus.feign.MfUniqueValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfUniqueValAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 04 18:01:52 CST 2016
 **/
@Controller
@RequestMapping("/mfUniqueVal")
public class MfUniqueValController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfUniqueValBo
	@Autowired
	private MfUniqueValFeign mfUniqueValFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfUniqueVal_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		try {
			mfUniqueVal.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfUniqueVal.setCriteriaList(mfUniqueVal, ajaxData);//我的筛选
			//this.getRoleConditions(mfUniqueVal,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfUniqueVal", mfUniqueVal));
			ipage = mfUniqueValFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcusunique00002 = formService.getFormData("cusunique00002");
			getFormValue(formcusunique00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusunique00002)){
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
				setObjValue(formcusunique00002, mfUniqueVal);
				mfUniqueValFeign.insert(mfUniqueVal);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusunique00002 = formService.getFormData("cusunique00002");
		getFormValue(formcusunique00002, getMapByJson(ajaxData));
		MfUniqueVal mfUniqueValJsp = new MfUniqueVal();
		setObjValue(formcusunique00002, mfUniqueValJsp);
		MfUniqueVal mfUniqueVal = mfUniqueValFeign.getById(mfUniqueValJsp);
		if(mfUniqueVal!=null){
			try{
				mfUniqueVal = (MfUniqueVal)EntityUtil.reflectionSetVal(mfUniqueVal, mfUniqueValJsp, getMapByJson(ajaxData));
				mfUniqueValFeign.update(mfUniqueVal);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		try{
		FormData 	formcusunique00002 = formService.getFormData("cusunique00002");
			getFormValue(formcusunique00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcusunique00002)){
				setObjValue(formcusunique00002, mfUniqueVal);
				mfUniqueValFeign.update(mfUniqueVal);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String uniqueId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusunique00002 = formService.getFormData("cusunique00002");
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUniqueId(uniqueId);
		mfUniqueVal = mfUniqueValFeign.getById(mfUniqueVal);
		getObjValue(formcusunique00002, mfUniqueVal,formData);
		if(mfUniqueVal!=null){
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
	public Map<String, Object> deleteAjax(String uniqueId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUniqueId(uniqueId);
		try {
			mfUniqueValFeign.delete(mfUniqueVal);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 验证录入手机号码唯一性
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-10-14 上午11:16:54
	 */
	@RequestMapping(value = "/doCheckUniqueAjax")
	@ResponseBody
	public Map<String, Object> doCheckUniqueAjax(String unVal,String relationId,String unType,String cusNoExclude,String saveType,String tabName) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUnVal(unVal);
		mfUniqueVal.setTabName(tabName);
		mfUniqueVal.setRelationId(relationId);
		mfUniqueVal.setUnType(unType);
		mfUniqueVal.setCusNo(cusNoExclude);
		try {
			String msg = mfUniqueValFeign.doCheckUniqueAjax(mfUniqueVal,saveType);
			if("0".equals(msg)){
				dataMap.put("flag", "0");
			}else{
				dataMap.put("flag", "1");
			}
			dataMap.put("msg", msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * @Description:验证身份证号码唯一性 
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-10-26 下午3:45:53
	 */
	@RequestMapping(value = "/doCheckUniqueIdNumAjax")
	@ResponseBody
	public Map<String, Object> doCheckUniqueIdNumAjax(String unVal,String unType) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUnVal(unVal);
		mfUniqueVal.setUnType(unType);
		try {
			String msg = mfUniqueValFeign.doCheckUniqueIdNum(mfUniqueVal);
			if("0".equals(msg)){
				dataMap.put("flag", "0");
			}else{
				dataMap.put("flag", "1");
			}
			dataMap.put("msg", msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 客户名称验证重复性
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-1-18 下午3:06:08
	 */
	@RequestMapping(value = "/doCheckUniqueCusNameAjax")
	@ResponseBody
	public Map<String, Object> doCheckUniqueCusNameAjax(String unVal) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUnVal(unVal);
		try {
			String msg = mfUniqueValFeign.doCheckUniqueCusName(mfUniqueVal);
			if("0".equals(msg)){
				dataMap.put("flag", "0");
			}else{
				dataMap.put("flag", "1");
			}
			dataMap.put("msg", msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
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
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcusunique00002 = formService.getFormData("cusunique00002");
		model.addAttribute("formcusunique00002", formcusunique00002);
		model.addAttribute("query", "");
		return "/component/cus/MfUniqueVal_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusunique00002 = formService.getFormData("cusunique00002");
		 getFormValue(formcusunique00002);
		MfUniqueVal  mfUniqueVal = new MfUniqueVal();
		 setObjValue(formcusunique00002, mfUniqueVal);
		 mfUniqueValFeign.insert(mfUniqueVal);
		 getObjValue(formcusunique00002, mfUniqueVal);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfUniqueVal", mfUniqueVal));
		 List<MfUniqueVal> mfUniqueValList = (List<MfUniqueVal>)mfUniqueValFeign.findByPage(ipage).getResult();
		model.addAttribute("formcusunique00002", formcusunique00002);
		model.addAttribute("mfUniqueValList", mfUniqueValList);
		model.addAttribute("query", "");
		return "/component/cus/MfUniqueVal_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uniqueId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusunique00001 = formService.getFormData("cusunique00001");
		 getFormValue(formcusunique00001);
		MfUniqueVal  mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUniqueId(uniqueId);
		 mfUniqueVal = mfUniqueValFeign.getById(mfUniqueVal);
		 getObjValue(formcusunique00001, mfUniqueVal);
		model.addAttribute("formcusunique00001", formcusunique00001);
		model.addAttribute("query", "");
		return "/component/cus/MfUniqueVal_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String uniqueId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfUniqueVal mfUniqueVal = new MfUniqueVal();
		mfUniqueVal.setUniqueId(uniqueId);
		mfUniqueValFeign.delete(mfUniqueVal);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcusunique00002 = formService.getFormData("cusunique00002");
		 getFormValue(formcusunique00002);
		 boolean validateFlag = this.validateFormData(formcusunique00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcusunique00002 = formService.getFormData("cusunique00002");
		 getFormValue(formcusunique00002);
		 boolean validateFlag = this.validateFormData(formcusunique00002);
	}
	
}

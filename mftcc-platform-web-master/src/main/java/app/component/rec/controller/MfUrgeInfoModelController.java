package  app.component.rec.controller;
import java.util.ArrayList;
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
import app.component.rec.entity.MfUrgeInfoModel;
import app.component.rec.feign.MfUrgeInfoModelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfUrgeInfoModelAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 10:44:59 CST 2016
 **/
@Controller
@RequestMapping(value="/mfUrgeInfoModel")
public class MfUrgeInfoModelController extends BaseFormBean{
	//注入MfUrgeInfoModelBo
	@Autowired
	private MfUrgeInfoModelFeign mfUrgeInfoModelFeign;
	//全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
		
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfUrgeInfoModel_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		try {
			mfUrgeInfoModel.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfUrgeInfoModel.setCriteriaList(mfUrgeInfoModel, ajaxData);//我的筛选
			//this.getRoleConditions(mfUrgeInfoModel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfUrgeInfoModelFeign.findByPage(ipage, mfUrgeInfoModel);
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
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		try{
			FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
			getFormValue(formurgemodel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formurgemodel0002)){
				MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
				setObjValue(formurgemodel0002, mfUrgeInfoModel);
				mfUrgeInfoModelFeign.insert(mfUrgeInfoModel);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_INSERT.getMessage());
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
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		getFormValue(formurgemodel0002, getMapByJson(ajaxData));
		MfUrgeInfoModel mfUrgeInfoModelJsp = new MfUrgeInfoModel();
		setObjValue(formurgemodel0002, mfUrgeInfoModelJsp);
		MfUrgeInfoModel mfUrgeInfoModel = mfUrgeInfoModelFeign.getById(mfUrgeInfoModelJsp);
		if(mfUrgeInfoModel!=null){
			try{
				mfUrgeInfoModel = (MfUrgeInfoModel)EntityUtil.reflectionSetVal(mfUrgeInfoModel, mfUrgeInfoModelJsp, getMapByJson(ajaxData));
				mfUrgeInfoModelFeign.update(mfUrgeInfoModel);
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
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String tableId,String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		try{
			FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
			getFormValue(formurgemodel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formurgemodel0002)){
				mfUrgeInfoModel = new MfUrgeInfoModel();
				setObjValue(formurgemodel0002, mfUrgeInfoModel);
				mfUrgeInfoModelFeign.update(mfUrgeInfoModel);
				getTableData(tableId,dataMap);
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String urgeModelId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		mfUrgeInfoModel.setUrgeModelId(urgeModelId);
		mfUrgeInfoModel = mfUrgeInfoModelFeign.getById(mfUrgeInfoModel);
		getObjValue(formurgemodel0002, mfUrgeInfoModel,formData);
		if(mfUrgeInfoModel!=null){
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
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String urgeModelId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		mfUrgeInfoModel.setUrgeModelId(urgeModelId);
		try {
			mfUrgeInfoModelFeign.delete(mfUrgeInfoModel);
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
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		model.addAttribute("formurgemodel0002", formurgemodel0002);
		return "MfUrgeInfoModel_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		 getFormValue(formurgemodel0002);
		 MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		 setObjValue(formurgemodel0002, mfUrgeInfoModel);
		 mfUrgeInfoModelFeign.insert(mfUrgeInfoModel);
		 getObjValue(formurgemodel0002, mfUrgeInfoModel);
		 this.addActionMessage(model,"保存成功");
		 List<MfUrgeInfoModel> mfUrgeInfoModelList = (List<MfUrgeInfoModel>)mfUrgeInfoModelFeign.findByPage(this.getIpage(), mfUrgeInfoModel).getResult();
		 model.addAttribute("formurgemodel0002", formurgemodel0002);
		 model.addAttribute("mfUrgeInfoModelList", mfUrgeInfoModelList);
		return "MfUrgeInfoModel_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String urgeModelId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		 getFormValue(formurgemodel0002);
		 MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		mfUrgeInfoModel.setUrgeModelId(urgeModelId);
		 mfUrgeInfoModel = mfUrgeInfoModelFeign.getById(mfUrgeInfoModel);
		 getObjValue(formurgemodel0002, mfUrgeInfoModel);
		 model.addAttribute("formurgemodel0002", formurgemodel0002);
		return "MfUrgeInfoModel_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String urgeModelId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		mfUrgeInfoModel.setUrgeModelId(urgeModelId);
		mfUrgeInfoModelFeign.delete(mfUrgeInfoModel);
		return getListPage();
	}
	
	private void getTableData(String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", mfUrgeInfoModelFeign.findList(mfUrgeInfoModel), null,true);
		dataMap.put("tableData",tableHtml);
	}
	
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		 getFormValue(formurgemodel0002);
		 boolean validateFlag = this.validateFormData(formurgemodel0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
		 getFormValue(formurgemodel0002);
		 boolean validateFlag = this.validateFormData(formurgemodel0002);
	}
	
	@ResponseBody
	@RequestMapping(value="/updateStsAjax")
	public Map<String,Object> updateStsAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formurgemodel0002 = formService.getFormData("urgemodel0002");
			getFormValue(formurgemodel0002, jobj);
			setObjValue(formurgemodel0002, mfUrgeInfoModel);
			int count = mfUrgeInfoModelFeign.updateSts(mfUrgeInfoModel);
			if(count>0){
				mfUrgeInfoModel = mfUrgeInfoModelFeign.getById(mfUrgeInfoModel);
				List<MfUrgeInfoModel> mfUrgeInfoModelList = new ArrayList<MfUrgeInfoModel>();
				mfUrgeInfoModelList.add(mfUrgeInfoModel);
				getTableData(mfUrgeInfoModelList,tableId,dataMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg","更新失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	private void getTableData(List<MfUrgeInfoModel> list,String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	
	public String getListChoosePage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfUrgeInfoModel_ListForChoose";
	}
	
	@ResponseBody
	@RequestMapping(value="/getUrgeInfoModelListAjax")
	public Map<String,Object> getUrgeInfoModelListAjax(String urgeType,String ajaxData,String query) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfUrgeInfoModel mfUrgeInfoModel = new MfUrgeInfoModel();
		String parm = "useFlag=1;urgeType="+urgeType+";";
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(mfUrgeInfoModel,ajaxData,query,parm,null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	

	
}

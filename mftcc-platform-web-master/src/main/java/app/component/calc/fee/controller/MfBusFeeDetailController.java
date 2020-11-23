package  app.component.calc.fee.controller;
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

import app.component.calc.fee.entity.MfBusFeeDetail;
import app.component.calc.fee.feign.MfBusFeeDetailFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusFeeDetailAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 08 14:39:47 CST 2016
 **/
@Controller
@RequestMapping("/mfBusFeeDetail")
public class MfBusFeeDetailController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusFeeDetailBo
	@Autowired
	private MfBusFeeDetailFeign mfBusFeeDetailFeign;
	//全局变量
	//异步参数
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		return "/component/calc/fee/MfBusFeeDetail_List";
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
		MfBusFeeDetail mfBusFeeDetail = new MfBusFeeDetail();
		try {
			mfBusFeeDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusFeeDetail.setCriteriaList(mfBusFeeDetail, ajaxData);//我的筛选
			//this.getRoleConditions(mfBusFeeDetail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfBusFeeDetailFeign.findByPage(ipage, mfBusFeeDetail);
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
		FormData 	formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
			getFormValue(formbusfeedetail0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusfeedetail0002)){
		MfBusFeeDetail mfBusFeeDetail = new MfBusFeeDetail();
				setObjValue(formbusfeedetail0002, mfBusFeeDetail);
				mfBusFeeDetailFeign.insert(mfBusFeeDetail);
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
		FormData formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
		getFormValue(formbusfeedetail0002, getMapByJson(ajaxData));
		MfBusFeeDetail mfBusFeeDetailJsp = new MfBusFeeDetail();
		setObjValue(formbusfeedetail0002, mfBusFeeDetailJsp);
		MfBusFeeDetail mfBusFeeDetail = mfBusFeeDetailFeign.getById(mfBusFeeDetailJsp);
		if(mfBusFeeDetail!=null){
			try{
				mfBusFeeDetail = (MfBusFeeDetail)EntityUtil.reflectionSetVal(mfBusFeeDetail, mfBusFeeDetailJsp, getMapByJson(ajaxData));
				mfBusFeeDetailFeign.update(mfBusFeeDetail);
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
		MfBusFeeDetail mfBusFeeDetail = new MfBusFeeDetail();
		try{
		FormData 	formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
			getFormValue(formbusfeedetail0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusfeedetail0002)){
		MfBusFeeDetail mfBusFeeDetail1 = new MfBusFeeDetail();
				setObjValue(formbusfeedetail0002, mfBusFeeDetail1);
				mfBusFeeDetailFeign.update(mfBusFeeDetail1);
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
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
		MfBusFeeDetail mfBusFeeDetail = new MfBusFeeDetail();
		mfBusFeeDetail.setId(id);
		mfBusFeeDetail = mfBusFeeDetailFeign.getById(mfBusFeeDetail);
		getObjValue(formbusfeedetail0002, mfBusFeeDetail,formData);
		if(mfBusFeeDetail!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusFeeDetail mfBusFeeDetail = new MfBusFeeDetail();
		mfBusFeeDetail.setId(id);
		try {
			mfBusFeeDetailFeign.delete(mfBusFeeDetail);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
		model.addAttribute("formbusfeedetail0002", formbusfeedetail0002);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusFeeDetail_Insert";
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
		FormData  formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
		 getFormValue(formbusfeedetail0002);
		MfBusFeeDetail  mfBusFeeDetail = new MfBusFeeDetail();
		 setObjValue(formbusfeedetail0002, mfBusFeeDetail);
		 mfBusFeeDetailFeign.insert(mfBusFeeDetail);
		 getObjValue(formbusfeedetail0002, mfBusFeeDetail);
		 this.addActionMessage(model, "保存成功");
		 List<MfBusFeeDetail> mfBusFeeDetailList = (List<MfBusFeeDetail>)mfBusFeeDetailFeign.findByPage(this.getIpage(), mfBusFeeDetail).getResult();
		model.addAttribute("formbusfeedetail0002", formbusfeedetail0002);
		model.addAttribute("mfBusFeeDetailList", mfBusFeeDetailList);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusFeeDetail_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formbusfeedetail0001 = formService.getFormData("busfeedetail0001");
		 getFormValue(formbusfeedetail0001);
		MfBusFeeDetail  mfBusFeeDetail = new MfBusFeeDetail();
		mfBusFeeDetail.setId(id);
		 mfBusFeeDetail = mfBusFeeDetailFeign.getById(mfBusFeeDetail);
		 getObjValue(formbusfeedetail0001, mfBusFeeDetail);
		model.addAttribute("formbusfeedetail0001", formbusfeedetail0001);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusFeeDetail_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfBusFeeDetail mfBusFeeDetail = new MfBusFeeDetail();
		mfBusFeeDetail.setId(id);
		mfBusFeeDetailFeign.delete(mfBusFeeDetail);
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
		FormData  formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
		 getFormValue(formbusfeedetail0002);
		 boolean validateFlag = this.validateFormData(formbusfeedetail0002);
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
		FormData  formbusfeedetail0002 = formService.getFormData("busfeedetail0002");
		 getFormValue(formbusfeedetail0002);
		 boolean validateFlag = this.validateFormData(formbusfeedetail0002);
	}
	
}

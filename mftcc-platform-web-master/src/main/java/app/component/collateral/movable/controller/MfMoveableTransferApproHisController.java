package  app.component.collateral.movable.controller;
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

import app.component.collateral.movable.entity.MfMoveableTransferApproHis;
import app.component.collateral.movable.feign.MfMoveableTransferApproHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfMoveableTransferApproHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 09 16:34:57 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableTransferApproHis")
public class MfMoveableTransferApproHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableTransferApproHisBo
	@Autowired
	private MfMoveableTransferApproHisFeign mfMoveableTransferApproHisFeign;
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
		 return "/component/collateral/movable/MfMoveableTransferApproHis_List";
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
		MfMoveableTransferApproHis mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
		try {
			mfMoveableTransferApproHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableTransferApproHis.setCriteriaList(mfMoveableTransferApproHis, ajaxData);//我的筛选
			//mfMoveableTransferApproHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableTransferApproHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableTransferApproHisFeign.findByPage(ipage, mfMoveableTransferApproHis);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formtransapprohis0002 = formService.getFormData("transapprohis0002");
			getFormValue(formtransapprohis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtransapprohis0002)){
		MfMoveableTransferApproHis mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
				setObjValue(formtransapprohis0002, mfMoveableTransferApproHis);
				mfMoveableTransferApproHisFeign.insert(mfMoveableTransferApproHis);
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
		FormData formtransapprohis0002 = formService.getFormData("transapprohis0002");
		getFormValue(formtransapprohis0002, getMapByJson(ajaxData));
		MfMoveableTransferApproHis mfMoveableTransferApproHisJsp = new MfMoveableTransferApproHis();
		setObjValue(formtransapprohis0002, mfMoveableTransferApproHisJsp);
		MfMoveableTransferApproHis mfMoveableTransferApproHis = mfMoveableTransferApproHisFeign.getById(mfMoveableTransferApproHisJsp);
		if(mfMoveableTransferApproHis!=null){
			try{
				mfMoveableTransferApproHis = (MfMoveableTransferApproHis)EntityUtil.reflectionSetVal(mfMoveableTransferApproHis, mfMoveableTransferApproHisJsp, getMapByJson(ajaxData));
				mfMoveableTransferApproHisFeign.update(mfMoveableTransferApproHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formtransapprohis0002 = formService.getFormData("transapprohis0002");
			getFormValue(formtransapprohis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtransapprohis0002)){
		MfMoveableTransferApproHis mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
				setObjValue(formtransapprohis0002, mfMoveableTransferApproHis);
				mfMoveableTransferApproHisFeign.update(mfMoveableTransferApproHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String transferId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formtransapprohis0002 = formService.getFormData("transapprohis0002");
		MfMoveableTransferApproHis mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
		mfMoveableTransferApproHis.setTransferId(transferId);
		mfMoveableTransferApproHis = mfMoveableTransferApproHisFeign.getById(mfMoveableTransferApproHis);
		getObjValue(formtransapprohis0002, mfMoveableTransferApproHis,formData);
		if(mfMoveableTransferApproHis!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String transferId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableTransferApproHis mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
		mfMoveableTransferApproHis.setTransferId(transferId);
		try {
			mfMoveableTransferApproHisFeign.delete(mfMoveableTransferApproHis);
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
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formtransapprohis0002 = formService.getFormData("transapprohis0002");
		model.addAttribute("formtransapprohis0002", formtransapprohis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableTransferApproHis_Insert";
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
		FormData  formtransapprohis0002 = formService.getFormData("transapprohis0002");
		 getFormValue(formtransapprohis0002);
		MfMoveableTransferApproHis  mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
		 setObjValue(formtransapprohis0002, mfMoveableTransferApproHis);
		 mfMoveableTransferApproHisFeign.insert(mfMoveableTransferApproHis);
		 getObjValue(formtransapprohis0002, mfMoveableTransferApproHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableTransferApproHis> mfMoveableTransferApproHisList = (List<MfMoveableTransferApproHis>)mfMoveableTransferApproHisFeign.findByPage(this.getIpage(), mfMoveableTransferApproHis).getResult();
		model.addAttribute("formtransapprohis0002", formtransapprohis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableTransferApproHis_Insert";
	}
	/**
	 * 查询
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String transferId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formtransapprohis0001 = formService.getFormData("transapprohis0001");
		 getFormValue(formtransapprohis0001);
		MfMoveableTransferApproHis  mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
		mfMoveableTransferApproHis.setTransferId(transferId);
		 mfMoveableTransferApproHis = mfMoveableTransferApproHisFeign.getById(mfMoveableTransferApproHis);
		 getObjValue(formtransapprohis0001, mfMoveableTransferApproHis);
		model.addAttribute("formtransapprohis0001", formtransapprohis0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableTransferApproHis_Detail";
	}
	/**
	 * 删除
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String transferId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableTransferApproHis mfMoveableTransferApproHis = new MfMoveableTransferApproHis();
		mfMoveableTransferApproHis.setTransferId(transferId);
		mfMoveableTransferApproHisFeign.delete(mfMoveableTransferApproHis);
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
		FormData  formtransapprohis0002 = formService.getFormData("transapprohis0002");
		 getFormValue(formtransapprohis0002);
		 boolean validateFlag = this.validateFormData(formtransapprohis0002);
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
		FormData  formtransapprohis0002 = formService.getFormData("transapprohis0002");
		 getFormValue(formtransapprohis0002);
		 boolean validateFlag = this.validateFormData(formtransapprohis0002);
	}
	
	
}

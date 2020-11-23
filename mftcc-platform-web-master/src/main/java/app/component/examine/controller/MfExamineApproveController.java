package  app.component.examine.controller;
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
import app.component.examine.entity.MfExamineApprove;
import app.component.examine.feign.MfExamineApproveFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfExamineApproveAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 27 18:21:50 CST 2017
 **/
@Controller
@RequestMapping(value="/MfExamineApproveController")
public class MfExamineApproveController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入MfExamineApproveBo
	@Autowired
	private MfExamineApproveFeign mfExamineApproveFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	private Map<String,Object> dataMap;
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "component/examine/MfExamineApprove_List";
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
		dataMap = new HashMap<String, Object>(); 
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		try {
			mfExamineApprove.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineApprove.setCriteriaList(mfExamineApprove, ajaxData);//我的筛选
			//mfExamineApprove.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamineApprove,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfExamineApprove", mfExamineApprove));
			ipage = mfExamineApproveFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
		dataMap = new HashMap<String, Object>(); 
		try{
			
			FormData formexamapprove0002 = formService.getFormData("examapprove0002");
			getFormValue(formexamapprove0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamapprove0002)){
				MfExamineApprove mfExamineApprove = new MfExamineApprove();
				setObjValue(formexamapprove0002, mfExamineApprove);
				mfExamineApproveFeign.insert(mfExamineApprove);
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
			throw new Exception(e.getMessage());
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
		dataMap = new HashMap<String, Object>();
		FormData formexamapprove0002 = formService.getFormData("examapprove0002");
		getFormValue(formexamapprove0002, getMapByJson(ajaxData));
		MfExamineApprove mfExamineApproveJsp = new MfExamineApprove();
		setObjValue(formexamapprove0002, mfExamineApproveJsp);
		MfExamineApprove mfExamineApprove = mfExamineApproveFeign.getById(mfExamineApproveJsp);
		if(mfExamineApprove!=null){
			try{
				mfExamineApprove = (MfExamineApprove)EntityUtil.reflectionSetVal(mfExamineApprove, mfExamineApproveJsp, getMapByJson(ajaxData));
				mfExamineApproveFeign.update(mfExamineApprove);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		try{
			FormData formexamapprove0002 = formService.getFormData("examapprove0002");
			getFormValue(formexamapprove0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamapprove0002)){
				mfExamineApprove = new MfExamineApprove();
				setObjValue(formexamapprove0002, mfExamineApprove);
				mfExamineApproveFeign.update(mfExamineApprove);
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
			throw new Exception(e.getMessage());
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
	public Map<String,Object> getByIdAjax(String approveId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamapprove0002 = formService.getFormData("examapprove0002");
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		mfExamineApprove.setApproveId(approveId);
		mfExamineApprove = mfExamineApproveFeign.getById(mfExamineApprove);
		getObjValue(formexamapprove0002, mfExamineApprove,formData);
		if(mfExamineApprove!=null){
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
	public Map<String,Object> deleteAjax(String approveId) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		mfExamineApprove.setApproveId(approveId);
		try {
			mfExamineApproveFeign.delete(mfExamineApprove);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		FormData formexamapprove0002 = formService.getFormData("examapprove0002");
		 model.addAttribute("formexamapprove0002", formexamapprove0002);
		return "component/examine/MfExamineApprove_Insert";
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
		FormData formexamapprove0002 = formService.getFormData("examapprove0002");
		 getFormValue(formexamapprove0002);
		 MfExamineApprove mfExamineApprove = new MfExamineApprove();
		 setObjValue(formexamapprove0002, mfExamineApprove);
		 mfExamineApproveFeign.insert(mfExamineApprove);
		 getObjValue(formexamapprove0002, mfExamineApprove);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfExamineApprove", mfExamineApprove));
		 List<MfExamineApprove> mfExamineApproveList = (List<MfExamineApprove>)mfExamineApproveFeign.findByPage(ipage).getResult();
		 model.addAttribute("mfExamineApproveList", mfExamineApproveList);
		 model.addAttribute("mfExamineApprove", mfExamineApprove);
		return "component/examine/MfExamineApprove_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String approveId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamapprove0001 = formService.getFormData("examapprove0001");
		 getFormValue(formexamapprove0001);
		 MfExamineApprove mfExamineApprove = new MfExamineApprove();
		mfExamineApprove.setApproveId(approveId);
		 mfExamineApprove = mfExamineApproveFeign.getById(mfExamineApprove);
		 getObjValue(formexamapprove0001, mfExamineApprove);
		 model.addAttribute("mfExamineApprove", mfExamineApprove);
		return "component/examine/MfExamineApprove_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(Model model,String approveId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineApprove mfExamineApprove = new MfExamineApprove();
		mfExamineApprove.setApproveId(approveId);
		mfExamineApproveFeign.delete(mfExamineApprove);
		return getListPage();
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
		 FormData formexamapprove0002 = formService.getFormData("examapprove0002");
		 getFormValue(formexamapprove0002);
		 boolean validateFlag = this.validateFormData(formexamapprove0002);
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
		 FormData formexamapprove0002 = formService.getFormData("examapprove0002");
		 getFormValue(formexamapprove0002);
		 boolean validateFlag = this.validateFormData(formexamapprove0002);
	}
	

	
}

package  app.component.prdct.controller;
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
import app.component.prdct.entity.MfKindFlow;
import app.component.prdct.feign.MfKindFlowFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfKindFlowAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 30 11:02:44 CST 2017
 **/

@Controller
@RequestMapping(value = "/mfKindFlow")
public class MfKindFlowController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入mfKindFlowFeign
	@Autowired
	private MfKindFlowFeign mfKindFlowFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindFlow_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindFlow mfKindFlow = new MfKindFlow();
		try {
			mfKindFlow.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfKindFlow.setCriteriaList(mfKindFlow, ajaxData);//我的筛选
			//mfKindFlow.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfKindFlow,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfKindFlowFeign.findByPage(ipage, mfKindFlow);
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
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,MfKindFlow mfKindFlow) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			FormData formkindflow0002 = formService.getFormData("kindflow0002");
			getFormValue(formkindflow0002, getMapByJson(ajaxData));
			if(this.validateFormData(formkindflow0002)){
				mfKindFlow = new MfKindFlow();
				setObjValue(formkindflow0002, mfKindFlow);
				mfKindFlowFeign.insert(mfKindFlow);
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
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formkindflow0002 = formService.getFormData("kindflow0002");
		getFormValue(formkindflow0002, getMapByJson(ajaxData));
		MfKindFlow mfKindFlowJsp = new MfKindFlow();
		setObjValue(formkindflow0002, mfKindFlowJsp);
		MfKindFlow mfKindFlow = mfKindFlowFeign.getById(mfKindFlowJsp);
		if(mfKindFlow!=null){
			try{
				mfKindFlow = (MfKindFlow)EntityUtil.reflectionSetVal(mfKindFlow, mfKindFlowJsp, getMapByJson(ajaxData));
				mfKindFlowFeign.update(mfKindFlow);
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
	 * 
	 * 方法描述： 流程的启用禁用
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-4 下午6:43:23
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUseFlagAjax")
	public Map<String, Object> updateUseFlagAjax(String kindFlowId,String kindNo,String useFlag) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setKindFlowId(kindFlowId);
		mfKindFlow.setKindNo(kindNo);
		mfKindFlow.setUseFlag(useFlag);
		try{
			mfKindFlowFeign.update(mfKindFlow);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("启用"));
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("启用"));
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
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String kindFlowId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formkindflow0002 = formService.getFormData("kindflow0002");
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setKindFlowId(kindFlowId);
		mfKindFlow = mfKindFlowFeign.getById(mfKindFlow);
		getObjValue(formkindflow0002, mfKindFlow,formData);
		if(mfKindFlow!=null){
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
	@RequestMapping(value = "/deleteAjax")
	public HashMap<String, Object> deleteAjax(String kindFlowId) throws Exception{
		ActionContext.initialize(request,response);
		HashMap<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setKindFlowId(kindFlowId);
		try {
			mfKindFlowFeign.delete(mfKindFlow);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formkindflow0002 = formService.getFormData("kindflow0002");
		model.addAttribute("query", "");
		model.addAttribute("formkindflow0002",formkindflow0002);
		return "/component/prdct/MfKindFlow_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formkindflow0002 = formService.getFormData("kindflow0002");
		 getFormValue(formkindflow0002);
		 MfKindFlow mfKindFlow = new MfKindFlow();
		 setObjValue(formkindflow0002, mfKindFlow);
		 mfKindFlowFeign.insert(mfKindFlow);
		 getObjValue(formkindflow0002, mfKindFlow);
		 this.addActionMessage(model,"保存成功");
		 List<MfKindFlow> mfKindFlowList = (List<MfKindFlow>)mfKindFlowFeign.findByPage(this.getIpage(), mfKindFlow).getResult();
		 model.addAttribute("query", "");
		 model.addAttribute("mfKindFlowList", mfKindFlowList);
		 model.addAttribute("formkindflow0002", formkindflow0002);
		 return "/component/prdct/MfKindFlow_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String kindFlowId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formkindflow0001 = formService.getFormData("kindflow0001");
		 getFormValue(formkindflow0001);
		 MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setKindFlowId(kindFlowId);
		 mfKindFlow = mfKindFlowFeign.getById(mfKindFlow);
		 getObjValue(formkindflow0001, mfKindFlow);
		 model.addAttribute("query", "");
		 model.addAttribute("formkindflow0001", formkindflow0001);
		return "/component/prdct/MfKindFlow_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String kindFlowId) throws Exception {
		ActionContext.initialize(request,response);
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setKindFlowId(kindFlowId);
		mfKindFlowFeign.delete(mfKindFlow);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formkindflow0002 = formService.getFormData("kindflow0002");
		 getFormValue(formkindflow0002);
		 boolean validateFlag = this.validateFormData(formkindflow0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formkindflow0002 = formService.getFormData("kindflow0002");
		 getFormValue(formkindflow0002);
		 boolean validateFlag = this.validateFormData(formkindflow0002);
	}
	
}

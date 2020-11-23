package  app.component.calc.penalty.controller;
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

import app.component.calc.penalty.entity.MfSysPenaltyChild;
import app.component.calc.penalty.entity.MfSysPenaltyMain;
import app.component.calc.penalty.feign.MfSysPenaltyChildFeign;
import app.component.calc.penaltyinterface.PenaltyInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfSysPenaltyChildAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 01 17:27:43 CST 2017
 **/
@Controller
@RequestMapping("/mfSysPenaltyChild")
public class MfSysPenaltyChildController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfSysPenaltyChildBo
	@Autowired
	private MfSysPenaltyChildFeign mfSysPenaltyChildFeign;
	//全局变量
	
	//异步参数
	//表单变量
	@Autowired
	private PenaltyInterfaceFeign penaltyInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
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
		return "/component/calc/penalty/MfSysPenaltyChild_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String idMain) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysPenaltyChild mfSysPenaltyChild = new MfSysPenaltyChild();
		try {
			mfSysPenaltyChild.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfSysPenaltyChild.setCriteriaList(mfSysPenaltyChild, ajaxData);//我的筛选
			//mfSysPenaltyChild.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfSysPenaltyChild,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			//ipage = mfSysPenaltyChildFeign.findByPage(ipage, mfSysPenaltyChild);
			Map<String, String> paramMap = CwPublicUtil.getMapByJson(ajaxData);
			paramMap.put("idMain",idMain);
			
			ipage = mfSysPenaltyChildFeign.getPenaltyChildListPage(ipage, paramMap);
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
	
	/***
	 * 违约金阶梯弹出
	 * @param mfSysKind 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPenaltyChildList")
	public String getPenaltyChildList(Model model,String kindNo,String penaltyType) throws Exception {
		MfSysPenaltyMain penaltyMain = new MfSysPenaltyMain();
		penaltyMain.setKindNo(kindNo);
		penaltyMain.setPenaltyType(penaltyType);
		penaltyMain = penaltyInterfaceFeign.getById(penaltyMain);
		MfSysKind mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
		request.setAttribute("idMain", penaltyMain.getId());
		request.setAttribute("kindName", mfSysKind.getKindName());
		model.addAttribute("query", "");
		return "/component/prdct/MfSysPenaltyChild_List";
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,JSONObject paramMap) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formpenaltychild0002 = formService.getFormData("penaltychild0002");
			getFormValue(formpenaltychild0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpenaltychild0002)){
		MfSysPenaltyChild mfSysPenaltyChild = new MfSysPenaltyChild();
				setObjValue(formpenaltychild0002, mfSysPenaltyChild);
				mfSysPenaltyChildFeign.insert(mfSysPenaltyChild);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			
			paramMap = JSONObject.fromObject(ajaxData);
			MfSysPenaltyChild mfSysPenaltyChild = (MfSysPenaltyChild)JSONObject.toBean(paramMap, MfSysPenaltyChild.class);
			//mfSysPenaltyChild = (MfSysPenaltyChild) CwPublicUtil.convertMap(MfSysPenaltyChild.class, paramMap);
			mfSysPenaltyChildFeign.insert(mfSysPenaltyChild);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_INSERT.getMessage());
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
		FormData formpenaltychild0002 = formService.getFormData("penaltychild0002");
		getFormValue(formpenaltychild0002, getMapByJson(ajaxData));
		MfSysPenaltyChild mfSysPenaltyChildJsp = new MfSysPenaltyChild();
		setObjValue(formpenaltychild0002, mfSysPenaltyChildJsp);
		MfSysPenaltyChild mfSysPenaltyChild = mfSysPenaltyChildFeign.getById(mfSysPenaltyChildJsp);
		if(mfSysPenaltyChild!=null){
			try{
				mfSysPenaltyChild = (MfSysPenaltyChild)EntityUtil.reflectionSetVal(mfSysPenaltyChild, mfSysPenaltyChildJsp, getMapByJson(ajaxData));
				mfSysPenaltyChildFeign.update(mfSysPenaltyChild);
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
		MfSysPenaltyChild mfSysPenaltyChild = new MfSysPenaltyChild();
		try{
			JSONObject paramMap = JSONObject.fromObject(ajaxData);
			mfSysPenaltyChild = (MfSysPenaltyChild)JSONObject.toBean(paramMap, MfSysPenaltyChild.class);
			mfSysPenaltyChildFeign.update(mfSysPenaltyChild);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE);
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
		FormData formpenaltychild0002 = formService.getFormData("penaltychild0002");
		MfSysPenaltyChild mfSysPenaltyChild = new MfSysPenaltyChild();
		mfSysPenaltyChild.setId(id);
		mfSysPenaltyChild = mfSysPenaltyChildFeign.getById(mfSysPenaltyChild);
		getObjValue(formpenaltychild0002, mfSysPenaltyChild,formData);
		if(mfSysPenaltyChild!=null){
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
		MfSysPenaltyChild mfSysPenaltyChild = new MfSysPenaltyChild();
		mfSysPenaltyChild.setId(id);
		try {
			mfSysPenaltyChildFeign.delete(mfSysPenaltyChild);
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
		FormData formpenaltychild0002 = formService.getFormData("penaltychild0002");
		model.addAttribute("formpenaltychild0002", formpenaltychild0002);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfSysPenaltyChild_Insert";
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
		FormData  formpenaltychild0001 = formService.getFormData("penaltychild0001");
		 getFormValue(formpenaltychild0001);
		MfSysPenaltyChild  mfSysPenaltyChild = new MfSysPenaltyChild();
		mfSysPenaltyChild.setId(id);
		 mfSysPenaltyChild = mfSysPenaltyChildFeign.getById(mfSysPenaltyChild);
		 getObjValue(formpenaltychild0001, mfSysPenaltyChild);
		model.addAttribute("formpenaltychild0001", formpenaltychild0001);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfSysPenaltyChild_Detail";
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
		FormData  formpenaltychild0002 = formService.getFormData("penaltychild0002");
		 getFormValue(formpenaltychild0002);
		 boolean validateFlag = this.validateFormData(formpenaltychild0002);
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
		FormData  formpenaltychild0002 = formService.getFormData("penaltychild0002");
		 getFormValue(formpenaltychild0002);
		 boolean validateFlag = this.validateFormData(formpenaltychild0002);
	}
	
}

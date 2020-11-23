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

import app.component.collateral.movable.entity.MfMoveableUnusualTailInfo;
import app.component.collateral.movable.feign.MfMoveableUnusualTailInfoFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfMoveableUnusualTailInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 16 18:04:45 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableUnusualTailInfo")
public class MfMoveableUnusualTailInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableUnusualTailInfoBo
	@Autowired
	private MfMoveableUnusualTailInfoFeign mfMoveableUnusualTailInfoFeign;
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
		 return "/component/collateral/movable/MfMoveableUnusualTailInfo_List";
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
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		try {
			mfMoveableUnusualTailInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableUnusualTailInfo.setCriteriaList(mfMoveableUnusualTailInfo, ajaxData);//我的筛选
			//mfMoveableUnusualTailInfo.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableUnusualTailInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableUnusualTailInfoFeign.findByPage(ipage, mfMoveableUnusualTailInfo);
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
		FormData 	formunusualtail0001 = formService.getFormData("unusualtail0001");
			getFormValue(formunusualtail0001, getMapByJson(ajaxData));
			if(this.validateFormData(formunusualtail0001)){
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
				setObjValue(formunusualtail0001, mfMoveableUnusualTailInfo);
				mfMoveableUnusualTailInfoFeign.insert(mfMoveableUnusualTailInfo);
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
		FormData formmovable0002 = formService.getFormData("movable0002");
		getFormValue(formmovable0002, getMapByJson(ajaxData));
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfoJsp = new MfMoveableUnusualTailInfo();
		setObjValue(formmovable0002, mfMoveableUnusualTailInfoJsp);
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = mfMoveableUnusualTailInfoFeign.getById(mfMoveableUnusualTailInfoJsp);
		if(mfMoveableUnusualTailInfo!=null){
			try{
				mfMoveableUnusualTailInfo = (MfMoveableUnusualTailInfo)EntityUtil.reflectionSetVal(mfMoveableUnusualTailInfo, mfMoveableUnusualTailInfoJsp, getMapByJson(ajaxData));
				mfMoveableUnusualTailInfoFeign.update(mfMoveableUnusualTailInfo);
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
		FormData 	formmovable0002 = formService.getFormData("movable0002");
			getFormValue(formmovable0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovable0002)){
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
				setObjValue(formmovable0002, mfMoveableUnusualTailInfo);
				mfMoveableUnusualTailInfoFeign.update(mfMoveableUnusualTailInfo);
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
	 * @param unusualTailId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String unusualTailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovable0002 = formService.getFormData("movable0002");
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		mfMoveableUnusualTailInfo.setUnusualTailId(unusualTailId);
		mfMoveableUnusualTailInfo = mfMoveableUnusualTailInfoFeign.getById(mfMoveableUnusualTailInfo);
		getObjValue(formmovable0002, mfMoveableUnusualTailInfo,formData);
		if(mfMoveableUnusualTailInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param unusualTailId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String unusualTailId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		mfMoveableUnusualTailInfo.setUnusualTailId(unusualTailId);
		try {
			mfMoveableUnusualTailInfoFeign.delete(mfMoveableUnusualTailInfo);
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
	 * @param accountCheckId 
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String accountCheckId, String busPleId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formunusualtail0001 = formService.getFormData("unusualtail0001");
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		mfMoveableUnusualTailInfo.setAccountCheckId(accountCheckId);
		mfMoveableUnusualTailInfo.setBusPleId(busPleId);
		getObjValue(formunusualtail0001, mfMoveableUnusualTailInfo);
		model.addAttribute("formunusualtail0001", formunusualtail0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableUnusualTailInfo_Insert";
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
		FormData  formmovable0002 = formService.getFormData("movable0002");
		 getFormValue(formmovable0002);
		MfMoveableUnusualTailInfo  mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		 setObjValue(formmovable0002, mfMoveableUnusualTailInfo);
		 mfMoveableUnusualTailInfoFeign.insert(mfMoveableUnusualTailInfo);
		 getObjValue(formmovable0002, mfMoveableUnusualTailInfo);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableUnusualTailInfo> mfMoveableUnusualTailInfoList = (List<MfMoveableUnusualTailInfo>)mfMoveableUnusualTailInfoFeign.findByPage(this.getIpage(), mfMoveableUnusualTailInfo).getResult();
		model.addAttribute("formmovable0002", formmovable0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableUnusualTailInfo_Insert";
	}
	/**
	 * 查询
	 * @param unusualTailId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String unusualTailId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formunusualtail0001 = formService.getFormData("unusualtail0001");
		 getFormValue(formunusualtail0001);
		MfMoveableUnusualTailInfo  mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		mfMoveableUnusualTailInfo.setUnusualTailId(unusualTailId);
		 mfMoveableUnusualTailInfo = mfMoveableUnusualTailInfoFeign.getById(mfMoveableUnusualTailInfo);
		 getObjValue(formunusualtail0001, mfMoveableUnusualTailInfo);
		model.addAttribute("formunusualtail0001", formunusualtail0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableUnusualTailInfo_Detail";
	}
	/**
	 * 删除
	 * @param unusualTailId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String unusualTailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		mfMoveableUnusualTailInfo.setUnusualTailId(unusualTailId);
		mfMoveableUnusualTailInfoFeign.delete(mfMoveableUnusualTailInfo);
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
		FormData  formmovable0002 = formService.getFormData("movable0002");
		 getFormValue(formmovable0002);
		 boolean validateFlag = this.validateFormData(formmovable0002);
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
		FormData  formmovable0002 = formService.getFormData("movable0002");
		 getFormValue(formmovable0002);
		 boolean validateFlag = this.validateFormData(formmovable0002);
	}
	
}

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

import app.component.collateral.movable.entity.MfMoveableClaimGoodsApproHis;
import app.component.collateral.movable.feign.MfMoveableClaimGoodsApproHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfMoveableClaimGoodsApproHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 10 18:35:10 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableClaimGoodsApproHis")
public class MfMoveableClaimGoodsApproHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableClaimGoodsApproHisBo
	@Autowired
	private MfMoveableClaimGoodsApproHisFeign mfMoveableClaimGoodsApproHisFeign;
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
		 return "/component/collateral/movable/MfMoveableClaimGoodsApproHis_List";
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
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
		try {
			mfMoveableClaimGoodsApproHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableClaimGoodsApproHis.setCriteriaList(mfMoveableClaimGoodsApproHis, ajaxData);//我的筛选
			//mfMoveableClaimGoodsApproHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableClaimGoodsApproHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableClaimGoodsApproHisFeign.findByPage(ipage, mfMoveableClaimGoodsApproHis);
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
		FormData 	formclaimapprohis0002 = formService.getFormData("claimapprohis0002");
			getFormValue(formclaimapprohis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formclaimapprohis0002)){
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
				setObjValue(formclaimapprohis0002, mfMoveableClaimGoodsApproHis);
				mfMoveableClaimGoodsApproHisFeign.insert(mfMoveableClaimGoodsApproHis);
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
		FormData formclaimapprohis0002 = formService.getFormData("claimapprohis0002");
		getFormValue(formclaimapprohis0002, getMapByJson(ajaxData));
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHisJsp = new MfMoveableClaimGoodsApproHis();
		setObjValue(formclaimapprohis0002, mfMoveableClaimGoodsApproHisJsp);
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = mfMoveableClaimGoodsApproHisFeign.getById(mfMoveableClaimGoodsApproHisJsp);
		if(mfMoveableClaimGoodsApproHis!=null){
			try{
				mfMoveableClaimGoodsApproHis = (MfMoveableClaimGoodsApproHis)EntityUtil.reflectionSetVal(mfMoveableClaimGoodsApproHis, mfMoveableClaimGoodsApproHisJsp, getMapByJson(ajaxData));
				mfMoveableClaimGoodsApproHisFeign.update(mfMoveableClaimGoodsApproHis);
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
		FormData 	formclaimapprohis0002 = formService.getFormData("claimapprohis0002");
			getFormValue(formclaimapprohis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formclaimapprohis0002)){
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
				setObjValue(formclaimapprohis0002, mfMoveableClaimGoodsApproHis);
				mfMoveableClaimGoodsApproHisFeign.update(mfMoveableClaimGoodsApproHis);
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
	 * @param claimApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String claimApproHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formclaimapprohis0002 = formService.getFormData("claimapprohis0002");
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
		mfMoveableClaimGoodsApproHis.setClaimApproHisId(claimApproHisId);
		mfMoveableClaimGoodsApproHis = mfMoveableClaimGoodsApproHisFeign.getById(mfMoveableClaimGoodsApproHis);
		getObjValue(formclaimapprohis0002, mfMoveableClaimGoodsApproHis,formData);
		if(mfMoveableClaimGoodsApproHis!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param claimApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String claimApproHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
		mfMoveableClaimGoodsApproHis.setClaimApproHisId(claimApproHisId);
		try {
			mfMoveableClaimGoodsApproHisFeign.delete(mfMoveableClaimGoodsApproHis);
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
		FormData formclaimapprohis0002 = formService.getFormData("claimapprohis0002");
		model.addAttribute("formclaimapprohis0002", formclaimapprohis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsApproHis_Insert";
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
		FormData  formclaimapprohis0002 = formService.getFormData("claimapprohis0002");
		 getFormValue(formclaimapprohis0002);
		MfMoveableClaimGoodsApproHis  mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
		 setObjValue(formclaimapprohis0002, mfMoveableClaimGoodsApproHis);
		 mfMoveableClaimGoodsApproHisFeign.insert(mfMoveableClaimGoodsApproHis);
		 getObjValue(formclaimapprohis0002, mfMoveableClaimGoodsApproHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableClaimGoodsApproHis> mfMoveableClaimGoodsApproHisList = (List<MfMoveableClaimGoodsApproHis>)mfMoveableClaimGoodsApproHisFeign.findByPage(this.getIpage(), mfMoveableClaimGoodsApproHis).getResult();
		model.addAttribute("mfMoveableClaimGoodsApproHisList", mfMoveableClaimGoodsApproHisList);
		model.addAttribute("formclaimapprohis0002", formclaimapprohis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsApproHis_Insert";
	}
	/**
	 * 查询
	 * @param claimApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String claimApproHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formclaimapprohis0001 = formService.getFormData("claimapprohis0001");
		 getFormValue(formclaimapprohis0001);
		MfMoveableClaimGoodsApproHis  mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
		mfMoveableClaimGoodsApproHis.setClaimApproHisId(claimApproHisId);
		 mfMoveableClaimGoodsApproHis = mfMoveableClaimGoodsApproHisFeign.getById(mfMoveableClaimGoodsApproHis);
		 getObjValue(formclaimapprohis0001, mfMoveableClaimGoodsApproHis);
		model.addAttribute("formclaimapprohis0001", formclaimapprohis0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsApproHis_Detail";
	}
	/**
	 * 删除
	 * @param claimApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String claimApproHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis = new MfMoveableClaimGoodsApproHis();
		mfMoveableClaimGoodsApproHis.setClaimApproHisId(claimApproHisId);
		mfMoveableClaimGoodsApproHisFeign.delete(mfMoveableClaimGoodsApproHis);
		return getListPage(model);
	}
	
}

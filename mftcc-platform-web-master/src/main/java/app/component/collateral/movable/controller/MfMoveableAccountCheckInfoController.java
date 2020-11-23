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

import app.component.collateral.movable.entity.MfMoveableAccountCheckInfo;
import app.component.collateral.movable.entity.MfMoveableUnusualTailInfo;
import app.component.collateral.movable.feign.MfMoveableAccountCheckInfoFeign;
import app.component.collateral.movable.feign.MfMoveableUnusualTailInfoFeign;
import app.component.common.EntityUtil;
import app.component.pactinterface.PactInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfMoveableAccountCheckInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 16 18:03:18 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableAccountCheckInfo")
public class MfMoveableAccountCheckInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableAccountCheckInfoBo
	@Autowired
	private MfMoveableAccountCheckInfoFeign mfMoveableAccountCheckInfoFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
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
		return "/component/collateral/movable/MfMoveableAccountCheckInfo_List";
	}
	/***
	 * 列表数据查询
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String busPleId,Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		try {
			mfMoveableAccountCheckInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableAccountCheckInfo.setCriteriaList(mfMoveableAccountCheckInfo, ajaxData);//我的筛选
			//mfMoveableAccountCheckInfo.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableAccountCheckInfo,"1000000001");//记录级权限控制方法
			mfMoveableAccountCheckInfo.setBusPleId(busPleId);
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfMoveableAccountCheckInfo", mfMoveableAccountCheckInfo));

			//自定义查询Bo方法
			ipage = mfMoveableAccountCheckInfoFeign.findByPage(ipage);
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
		FormData 	formaccountcheck0001 = formService.getFormData("accountcheck0001");
			getFormValue(formaccountcheck0001, getMapByJson(ajaxData));
			if(this.validateFormData(formaccountcheck0001)){
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
				setObjValue(formaccountcheck0001, mfMoveableAccountCheckInfo);
				mfMoveableAccountCheckInfoFeign.insert(mfMoveableAccountCheckInfo);
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
		FormData formaccountcheck0002 = formService.getFormData("accountcheck0002");
		getFormValue(formaccountcheck0002, getMapByJson(ajaxData));
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfoJsp = new MfMoveableAccountCheckInfo();
		setObjValue(formaccountcheck0002, mfMoveableAccountCheckInfoJsp);
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = mfMoveableAccountCheckInfoFeign.getById(mfMoveableAccountCheckInfoJsp);
		if(mfMoveableAccountCheckInfo!=null){
			try{
				mfMoveableAccountCheckInfo = (MfMoveableAccountCheckInfo)EntityUtil.reflectionSetVal(mfMoveableAccountCheckInfo, mfMoveableAccountCheckInfoJsp, getMapByJson(ajaxData));
				mfMoveableAccountCheckInfoFeign.update(mfMoveableAccountCheckInfo);
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
		FormData 	formaccountcheck0002 = formService.getFormData("accountcheck0002");
			getFormValue(formaccountcheck0002, getMapByJson(ajaxData));
			if(this.validateFormData(formaccountcheck0002)){
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
				setObjValue(formaccountcheck0002, mfMoveableAccountCheckInfo);
				mfMoveableAccountCheckInfoFeign.update(mfMoveableAccountCheckInfo);
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
	 * @param accountCheckId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String accountCheckId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formaccountcheck0002 = formService.getFormData("accountcheck0002");
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		mfMoveableAccountCheckInfo.setAccountCheckId(accountCheckId);
		mfMoveableAccountCheckInfo = mfMoveableAccountCheckInfoFeign.getById(mfMoveableAccountCheckInfo);
		getObjValue(formaccountcheck0002, mfMoveableAccountCheckInfo,formData);
		if(mfMoveableAccountCheckInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param accountCheckId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String accountCheckId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		mfMoveableAccountCheckInfo.setAccountCheckId(accountCheckId);
		try {
			mfMoveableAccountCheckInfoFeign.delete(mfMoveableAccountCheckInfo);
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
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String busPleId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formaccountcheck0001 = formService.getFormData("accountcheck0001");
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		mfMoveableAccountCheckInfo.setBusPleId(busPleId);
		mfMoveableAccountCheckInfo.setAccountCheckId(WaterIdUtil.getWaterId());
		getObjValue(formaccountcheck0001, mfMoveableAccountCheckInfo);
		model.addAttribute("formaccountcheck0001", formaccountcheck0001);
		model.addAttribute("query", "");
		return "/component/collateral/movable/MfMoveableAccountCheckInfo_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model,Ipage ipage) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formaccountcheck0002 = formService.getFormData("accountcheck0002");
		 getFormValue(formaccountcheck0002);
		MfMoveableAccountCheckInfo  mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		 setObjValue(formaccountcheck0002, mfMoveableAccountCheckInfo);
		 mfMoveableAccountCheckInfoFeign.insert(mfMoveableAccountCheckInfo);
		 getObjValue(formaccountcheck0002, mfMoveableAccountCheckInfo);
		 this.addActionMessage(model, "保存成功");
		 ipage.setParams(this.setIpageParams("mfMoveableAccountCheckInfo", mfMoveableAccountCheckInfo));
		 List<MfMoveableAccountCheckInfo> mfMoveableAccountCheckInfoList = (List<MfMoveableAccountCheckInfo>)mfMoveableAccountCheckInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("formaccountcheck0002", formaccountcheck0002);
		model.addAttribute("query", "");
		return "/component/collateral/movable/MfMoveableAccountCheckInfo_Insert";
	}
	/**
	 * 查询
	 * @param accountCheckId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String accountCheckId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formaccountcheck0003 = formService.getFormData("accountcheck0003");
		 getFormValue(formaccountcheck0003);
		MfMoveableAccountCheckInfo  mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		mfMoveableAccountCheckInfo.setAccountCheckId(accountCheckId);
		 mfMoveableAccountCheckInfo = mfMoveableAccountCheckInfoFeign.getById(mfMoveableAccountCheckInfo);
		 getObjValue(formaccountcheck0003, mfMoveableAccountCheckInfo);
		 MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo = new MfMoveableUnusualTailInfo();
		 mfMoveableUnusualTailInfo.setAccountCheckId(mfMoveableAccountCheckInfo.getAccountCheckId());
		 List<MfMoveableUnusualTailInfo> mfMoveableUnusualTailInfoList = mfMoveableUnusualTailInfoFeign.getUnusualTailInfoList(mfMoveableUnusualTailInfo);
		model.addAttribute("formaccountcheck0003", formaccountcheck0003);
		model.addAttribute("mfMoveableUnusualTailInfoList", mfMoveableUnusualTailInfoList);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableAccountCheckInfo_Detail";
	}
	/**
	 * 删除
	 * @param accountCheckId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String accountCheckId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo = new MfMoveableAccountCheckInfo();
		mfMoveableAccountCheckInfo.setAccountCheckId(accountCheckId);
		mfMoveableAccountCheckInfoFeign.delete(mfMoveableAccountCheckInfo);
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
		FormData  formaccountcheck0002 = formService.getFormData("accountcheck0002");
		 getFormValue(formaccountcheck0002);
		 boolean validateFlag = this.validateFormData(formaccountcheck0002);
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
		FormData  formaccountcheck0002 = formService.getFormData("accountcheck0002");
		 getFormValue(formaccountcheck0002);
		 boolean validateFlag = this.validateFormData(formaccountcheck0002);
	}
}

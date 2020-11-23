package  app.component.cus.custracing.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;
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
import app.component.cus.custracing.entity.MfCusTrack;
import app.component.cus.custracing.feign.MfCusTrackFeign;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusTrackAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 14 09:53:43 CST 2016
 **/
@Controller
@RequestMapping("/mfCusTrack")
public class MfCusTrackController extends BaseFormBean{
	
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusTrackBo
	@Autowired
	private MfCusTrackFeign mfCusTrackFeign;
	//全局变量

	
	/**
	 * 列表打开页面请求
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtrackBase = formService.getFormData("trackBase");
		MfCusTrack mfCusTrack = new MfCusTrack();
		mfCusTrack.setCusNo(cusNo);
        getObjValue(formtrackBase, mfCusTrack);
		JSONArray trackTypeArray = mfCusTrackFeign.getTrackTypeArray();
		dataMap.put("trackTypeArray", trackTypeArray);
		List<MfCusTrack> mfCusTrackList = mfCusTrackFeign.getList(mfCusTrack);
		for(MfCusTrack m:mfCusTrackList ){
			m.setRegTime(DateUtil.getShowDateTime(m.getRegTime()));
			List<MfCusTrack> mfCusTrackCommentList = m.getCommentList();
			for(MfCusTrack mfCusTrackComment:mfCusTrackCommentList){
				mfCusTrackComment.setRegTime(DateUtil.getShowDateTime(mfCusTrackComment.getRegTime()));
			}
		}
		String newDate = DateUtil.getDate("yyyy-MM-dd");
		String notFollowupFlag = mfCusTrackFeign.getNotFollowupFlag(cusNo);//客户未跟进标志 
		dataMap.put("notFollowupFlag", notFollowupFlag);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("newDate", newDate);
		model.addAttribute("mfCusTrackList", mfCusTrackList);
		model.addAttribute("formtrackBase", formtrackBase);
		model.addAttribute("query", "");
		return "/component/cus/custracing/MfCusTrack_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusTrack mfCusTrack = new MfCusTrack();
		try {
			mfCusTrack.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusTrack.setCriteriaList(mfCusTrack, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusTrack,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfCusTrackFeign.findByPage(ipage, mfCusTrack);
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
	 * 
	 * 方法描述：获取跟踪记录的前几条信息 
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param cusNo 
	 * @date 2017-3-28 下午1:53:09
	 */
	@RequestMapping(value = "/getTopListAjax")
	@ResponseBody
	public Map<String, Object> getTopListAjax(String ajaxData, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cusNo", cusNo);
//			paramMap.put("limit", 5);//前5条，写到bo层
			List<MfCusTrack> mfCusTrackList = mfCusTrackFeign.getTopList(paramMap);
			dataMap.put("flag", "success");
			dataMap.put("mfCusTrackList", mfCusTrackList);
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			FormData formTackBase = formService.getFormData("trackBase");
            MfCusTrack mfCusTrack = new MfCusTrack();
            getFormValue(formTackBase, map);
            if(this.validateFormData(formTackBase)) {
				setObjValue(formTackBase, mfCusTrack);
				mfCusTrack = mfCusTrackFeign.insert(mfCusTrack);
                mfCusTrack.setRegTime(DateUtil.getShowDateTime(mfCusTrack.getRegTime()));
                mfCusTrack.setTrackTypeName(new CodeUtils().getMapByKeyName("TRACK_TYPE").get(mfCusTrack.getTrackType()));
                dataMap.put("flag", "success");
				dataMap.put("cusTrack", mfCusTrack);
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

	@RequestMapping(value = "/insertTrackPlanAjax")
	@ResponseBody
	public Map<String, Object> insertTrackPlanAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formtrackplan0002 = formService.getFormData("trackplan0002");
			getFormValue(formtrackplan0002, getMapByJson(ajaxData));
			//if(this.validateFormData(formcustracing0002)){
		MfCusTrack mfCusTrack = new MfCusTrack();
				setObjValue(formtrackplan0002, mfCusTrack);
				mfCusTrackFeign.insert(mfCusTrack);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			//}else{
				//dataMap.put("flag", "error");
				//dataMap.put("msg",this.getFormulavaliErrorMsg());
			//}
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
		FormData formcustracing0002 = formService.getFormData("custracing0002");
		getFormValue(formcustracing0002, getMapByJson(ajaxData));
		MfCusTrack mfCusTrackJsp = new MfCusTrack();
		setObjValue(formcustracing0002, mfCusTrackJsp);
		MfCusTrack mfCusTrack = mfCusTrackFeign.getById(mfCusTrackJsp);
		if(mfCusTrack!=null){
			try{
				mfCusTrack = (MfCusTrack)EntityUtil.reflectionSetVal(mfCusTrack, mfCusTrackJsp, getMapByJson(ajaxData));
				mfCusTrackFeign.update(mfCusTrack);
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusTrack mfCusTrack = new MfCusTrack();
		try{
			
			mfCusTrack=(MfCusTrack)JSONObject.toBean(JSONObject.fromObject(ajaxData),MfCusTrack.class);
			mfCusTrackFeign.update(mfCusTrack);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		 	/*formcustracing0002 = formService.getFormData("custracing0002");
			getFormValue(formcustracing0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcustracing0002)){
		MfCusTrack mfCusTrack = new MfCusTrack();
				setObjValue(formcustracing0002, mfCusTrack);
				mfCusTrackFeign.update(mfCusTrack);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}*/
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
	 * @param trackId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String trackId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcustracing0002 = formService.getFormData("custracing0002");
		MfCusTrack mfCusTrack = new MfCusTrack();
		mfCusTrack.setTrackId(trackId);
		mfCusTrack = mfCusTrackFeign.getById(mfCusTrack);
		getObjValue(formcustracing0002, mfCusTrack,formData);
		if(mfCusTrack!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param trackId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String trackId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusTrack mfCusTrack = new MfCusTrack();
		mfCusTrack.setTrackId(trackId);
		try {
			mfCusTrackFeign.delete(mfCusTrack);
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
	public String input(Model model, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusTrack mfCusTrack = new MfCusTrack();
		mfCusTrack.setCusNo(cusNo);
		FormData formtrackBase = formService.getFormData("trackBase");
		getObjValue(formtrackBase,mfCusTrack);
		String newDate = DateUtil.getDate("yyyy-MM-dd");
		model.addAttribute("formtrackBase", formtrackBase);
		model.addAttribute("query", "");
		model.addAttribute("newDate", newDate);
		return "/component/cus/custracing/MfCusTrack_Insert";
	}

	/**
	 * @author czk
	 * @Description: 新增跟踪计划
	 * date 2016-6-23
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputTrackPlan")
	public String inputTrackPlan(Model model, String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formtrackplan0002 = formService.getFormData("trackplan0002");
		model.addAttribute("formtrackplan0002", formtrackplan0002);
		model.addAttribute("query", "");
		return "/component/cus/custracing/MfCusTrack_InsertTrackPlan";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcustracing0002 = formService.getFormData("custracing0002");
		 getFormValue(formcustracing0002);
		MfCusTrack  mfCusTrack = new MfCusTrack();
		 setObjValue(formcustracing0002, mfCusTrack);
		 mfCusTrackFeign.insert(mfCusTrack);
		 getObjValue(formcustracing0002, mfCusTrack);
		 this.addActionMessage(model, "保存成功");
		 List<MfCusTrack> mfCusTrackList = (List<MfCusTrack>)mfCusTrackFeign.findByPage(this.getIpage(), mfCusTrack).getResult();
		model.addAttribute("formcustracing0002", formcustracing0002);
		model.addAttribute("mfCusTrackList", mfCusTrackList);
		model.addAttribute("query", "");
		return "/component/cus/custracing/MfCusTrack_Insert";
	}
	/**
	 * 查询
	 * @param trackId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String trackId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcustracing0002 = formService.getFormData("custracing0002");
		 getFormValue(formcustracing0002);
		MfCusTrack  mfCusTrack = new MfCusTrack();
		mfCusTrack.setTrackId(trackId);
		 mfCusTrack = mfCusTrackFeign.getById(mfCusTrack);
		 getObjValue(formcustracing0002, mfCusTrack);
		model.addAttribute("formcustracing0002", formcustracing0002);
		model.addAttribute("query", "");
		return "/component/cus/custracing/MfCusTrack_Detail";
	}
	/**
	 * 删除
	 * @param trackId 
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String trackId, String cusNo) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusTrack mfCusTrack = new MfCusTrack();
		mfCusTrack.setTrackId(trackId);
		mfCusTrackFeign.delete(mfCusTrack);
		return getListPage(model,cusNo);
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
		FormData  formcustracing0002 = formService.getFormData("custracing0002");
		 getFormValue(formcustracing0002);
		 boolean validateFlag = this.validateFormData(formcustracing0002);
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
		FormData  formcustracing0002 = formService.getFormData("custracing0002");
		 getFormValue(formcustracing0002);
		 boolean validateFlag = this.validateFormData(formcustracing0002);
	}
	
}

package app.component.sys.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.PasConstant;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.MfTimeLineQueryConfig;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.feign.MfTimeLineQueryConfigFeign;
import app.component.sys.feign.SysTaskInfoFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: SysTaskInfoAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Mar 22 07:19:50 GMT 2016
 **/
@Controller
@RequestMapping("/sysTaskInfo")
public class SysTaskInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SysTaskInfoFeign sysTaskInfoFeign;
	@Autowired
	private MfTimeLineQueryConfigFeign mfTimeLineQueryConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(String pasMaxNo,Model model) throws Exception {
		model.addAttribute("pasMaxNo", pasMaxNo);
		return "/component/sys/SysB1";
	}

	/**
	 * B面任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPageToBAjax", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getListPageToBAjax(String jsonData, String ajaxData, Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("PAS_BIG_TYPE");
		List<ParmDic> minList = (List<ParmDic>) cu.getCacheByKeyName("PAS_SUB_TYPE");
		List<ParmDic> pdoList = (List<ParmDic>) cu.getCacheByKeyName("PAS_TYPE_OPERATION");
		JSONObject json = new JSONObject();
		JSONArray dicArray = JSONArray.fromObject(pdList);
		JSONArray dicMinArray = JSONArray.fromObject(minList);
		JSONArray dicOperArray = JSONArray.fromObject(pdoList);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		SysTaskInfo sysTaskInfoNew = new SysTaskInfo();
		sysTaskInfoNew.setPasSts(PasConstant.PAS_TASK_STS_0);
		sysTaskInfoNew.setPasAware(PasConstant.PAS_AWARE_NO);
		sysTaskInfoNew.setUserNo(User.getRegNo(request));
		List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMaxTypeCount(sysTaskInfoNew);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		maxCount.setUserNo(User.getRegNo(request));
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		for (int i = 0; i < dicArray.size(); i++) {
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("optCode"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("optName"));
			dicArray.getJSONObject(i).put("pId", null);
			dicArray.getJSONObject(i).put("open", true);
			dicArray.getJSONObject(i).put("count", "0");
			list.add(dicArray.getJSONObject(i).getString("optCode"));
			for (SysTaskInfo sti : stiList) {
				if (StringUtil.isNotEmpty(sti.getPasMaxNo())) {
					if (sti.getPasMaxNo().equals(dicArray.getJSONObject(i).getString("optCode"))) {
						dicArray.getJSONObject(i).put("count", sti.getPasContent());
					}
				}
			}
		}
		int pageNo = 0;
		if (jsonData != null) {
			JSONObject js = JSONObject.fromObject(jsonData);// 根据字符串转换对象
			pageNo = js.getInt("pageNo");
			sysTaskInfo = (SysTaskInfo) JSONObject.toBean(js, sysTaskInfo.getClass()); // 把值绑定成相应的值对象
		}
		if (ajaxData != null) {
			sysTaskInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
		}
		// ipage = this.getIpage();
		// Ipage ipage = new Ipage();
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		// ipage.setPageSize(15);
		sysTaskInfo.setUserNo(User.getRegNo(request));
		// 自定义查询Bo方法
		sysTaskInfo.setList(list);
		ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
		ipage = sysTaskInfoFeign.findByPage(ipage);
		MfTimeLineQueryConfig mfTimeLineQueryConfig = new MfTimeLineQueryConfig();
		mfTimeLineQueryConfig.setUseFlag(BizPubParm.YES_NO_Y);
		List<String> timeList = mfTimeLineQueryConfigFeign.getTimeLineQueryList(mfTimeLineQueryConfig);
		json.put("timeList", timeList);
		json.put("sysTaskInfoArray", JSONObject.fromObject(ipage));
		json.put("pasBigType", dicArray);
		json.put("pasSubType", dicMinArray);
		json.put("pasTypeOperation", dicOperArray);
		// json.put("maxCount", maxCount);
		json.put("pasAwareCount", pasAwareCount);
		json.put("list", list);
		json.put("SysDate", User.getSysDate(request));
		dataMap.put("ajaxData", json.toString());
		return dataMap;
	}

	@RequestMapping(value = "/getFormHtmlAjax")
	@ResponseBody
	public Map<String, Object> getFormHtmlAjax(String formId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// formId = "vou0007";
		// form = formService.getFormData(formId);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formId, "bigFormTag", "");
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		try {
			// sysTaskInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			// sysTaskInfo.setCriteriaList(sysTaskInfo, ajaxData);//我的筛选
			// this.getRoleConditions(sysTaskInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
			ipage = sysTaskInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 点击桌面消息筛选条件用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageToBAjax")
	@ResponseBody
	public Map<String, Object> findByPageToBAjax(String jsonData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setUserNo(User.getRegNo(request));
		try {
			/**
			 * 将状态、时间查询条件传入查询总数的方法。 By LiuYF 20161214 抽离成bo方法 shb
			 */
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
			ipage.setParamsStr(jsonData);
			dataMap = sysTaskInfoFeign.findByPageToBByPage(ipage);
			dataMap.put("SysDate", User.getSysDate(request));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormService formService = new FormService();
			FormData formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if (this.validateFormData(formsys0002)) {
				SysTaskInfo sysTaskInfo = new SysTaskInfo();
				setObjValue(formsys0002, sysTaskInfo);
				sysTaskInfoFeign.insert(sysTaskInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002, getMapByJson(ajaxData));
		SysTaskInfo sysTaskInfoJsp = new SysTaskInfo();
		setObjValue(formsys0002, sysTaskInfoJsp);
		SysTaskInfo sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfoJsp);
		if (sysTaskInfo != null) {
			try {
				sysTaskInfo = (SysTaskInfo) EntityUtil.reflectionSetVal(sysTaskInfo, sysTaskInfoJsp,
						getMapByJson(ajaxData));
				sysTaskInfoFeign.update(sysTaskInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		try {
			FormService formService = new FormService();
			FormData formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if (this.validateFormData(formsys0002)) {
				setObjValue(formsys0002, sysTaskInfo);
				sysTaskInfoFeign.update(sysTaskInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		SysTaskInfo sysTaskInfoNew = new SysTaskInfo();
		sysTaskInfoNew.setPasSts(PasConstant.PAS_TASK_STS_0);
		sysTaskInfoNew.setPasAware(PasConstant.PAS_AWARE_NO);
		List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMaxTypeCount(sysTaskInfoNew);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo.setUserNo(User.getRegNo(request));
		sysTaskInfo = sysTaskInfoFeign.getByIdAndUserNo(sysTaskInfo);
		if (sysTaskInfo != null) {
			String delFlag = sysTaskInfo.getDelFlag();
			if(delFlag != null && BizPubParm.YES_NO_Y.equals(delFlag)){
				dataMap.put("msg", "该任务已经被其他办理人员锁单");
				dataMap.put("flag", "error");
				return dataMap;
			}
			dataMap.put("flag", "success");
			dataMap.put("info", sysTaskInfo);
			dataMap.put("list", stiList);
			dataMap.put("pasAwareCount", pasAwareCount);
		} else {
			dataMap.put("msg", "该任务已经不存在");
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	@RequestMapping(value = "/getByIdAndUserNoAjax")
	@ResponseBody
	public Map<String, Object> getByIdAndUserNoAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMaxTypeCount(sysTaskInfo);
		sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
		int count = sysTaskInfoFeign.getCount(sysTaskInfo);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo.setUserNo(User.getRegNo(request));
		sysTaskInfo = sysTaskInfoFeign.getByIdAndUserNo(sysTaskInfo);
		if (sysTaskInfo != null) {
			dataMap.put("flag", "success");
			dataMap.put("info", sysTaskInfo);
			dataMap.put("list", stiList);
			dataMap.put("count", count);
			dataMap.put("pasAwareCount", pasAwareCount);
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	@RequestMapping(value = "/updataMsgAjax")
	@ResponseBody
	public Map<String, Object> updataMsgAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		updateCompleteMessage(sysTaskInfo, pasNo);
		dataMap.put("flag", "success");
		return dataMap;
	}

	@RequestMapping(value = "/updataPasAwareStsAjax")
	@ResponseBody
	public Map<String, Object> updataPasAwareStsAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		if(sysTaskInfo == null){
			dataMap.put("flag", "error");
			dataMap.put("msg", "该任务已经不存在");
			return dataMap;
		}else{
			String delFlag = sysTaskInfo.getDelFlag();
			if(delFlag != null && "1".equals(delFlag)){
				dataMap.put("flag", "error");
				dataMap.put("msg", "该任务已经被其他办理人员锁单");
				return dataMap;
			}
		}
		if (PasConstant.PAS_AWARE_YES.equals(sysTaskInfo.getPasAware())) {
			sysTaskInfo.setPasAware(PasConstant.PAS_AWARE_NO);
		} else {
			sysTaskInfo.setPasAware(PasConstant.PAS_AWARE_YES);
		}
		sysTaskInfoFeign.update(sysTaskInfo);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		dataMap.put("flag", "success");
		dataMap.put("sysTaskInfo", sysTaskInfo);
		dataMap.put("pasAwareCount", pasAwareCount);
		return dataMap;
	}

	/**
	 * 方法描述： 任务锁单状态修改
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-12-8 下午5:50:53
	 */
	@RequestMapping(value = "/updataPasLockStsAjax")
	@ResponseBody
	public Map<String, Object> updataPasLockStsAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			SysTaskInfo sysTaskInfo = new SysTaskInfo();
			sysTaskInfo.setPasNo(pasNo);
			sysTaskInfoFeign.updataPasLockSts(sysTaskInfo);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * @方法描述： 根据申请号，任务号，操作员获取任务信息
	 * @param pasNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年10月11日 下午9:52:01
	 */
	@RequestMapping(value = "/getSysTaskInfoAjax")
	@ResponseBody
	public Map<String, Object> getSysTaskInfoAjax(String wkfTaskNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			SysTaskInfo sysTaskInfo = new SysTaskInfo();
			sysTaskInfo.setUserNo(User.getRegNo(request));
			sysTaskInfo.setWkfTaskNo(wkfTaskNo);
			sysTaskInfo = sysTaskInfoFeign.getSysTaskInfo(sysTaskInfo);
			if(sysTaskInfo == null){
				dataMap.put("flag", "error");
				 dataMap.put("msg", "该任务已经不存在");
				return dataMap;
			}else{
				String delFlag = sysTaskInfo.getDelFlag();
				if(delFlag != null && BizPubParm.YES_NO_Y.equals(delFlag)){
					dataMap.put("flag", "error");
					dataMap.put("msg", "该任务已经被其他办理人员锁单");
					return dataMap;
				}
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateStickAjax")
	@ResponseBody
	public Map<String, Object> updateStickAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		if (PasConstant.PAS_STICK_YES.equals(sysTaskInfo.getPasStick())) {
			sysTaskInfo.setPasStick(PasConstant.PAS_STICK_NO);
		} else {
			sysTaskInfo.setPasStick(PasConstant.PAS_STICK_YES);
		}
		sysTaskInfoFeign.update(sysTaskInfo);
		dataMap.put("flag", "success");
		dataMap.put("sysTaskInfo", sysTaskInfo);
		return dataMap;
	}

	@RequestMapping(value = "/updateFilterAjax")
	@ResponseBody
	public Map<String, Object> updateFilterAjax(String jsonData, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		SysTaskInfo sti = new SysTaskInfo();
		try {
			if (jsonData != null) {
				JSONObject js = JSONObject.fromObject(jsonData);// 根据字符串转换对象
				sysTaskInfo = (SysTaskInfo) JSONObject.toBean(js, sysTaskInfo.getClass()); // 把值绑定成相应的值对象
			}
			if (ajaxData != null) {
				JSONObject js = JSONObject.fromObject(ajaxData);// 根据字符串转换对象
				sti = (SysTaskInfo) JSONObject.toBean(js, sti.getClass()); // 把值绑定成相应的值对象
			}
			sti.setUserNo(User.getRegNo(request));
			sysTaskInfo.setUserNo(User.getRegNo(request));
			List<SysTaskInfo> sysTaskInfoList = sysTaskInfoFeign.findByFilter(sysTaskInfo);
			for (SysTaskInfo sysTaskInfoTmp : sysTaskInfoList) {
				sti.setPasNo(sysTaskInfoTmp.getPasNo());
				sysTaskInfoFeign.updateByFilter(sti);
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String pasNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		try {
			sysTaskInfoFeign.delete(sysTaskInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * 暂时找不到用途，可能会导致待办任务全部丢失，因此先注释掉 YXY 20180703
	 * 
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping(value = "/deleteByFilterAjax")
	// @ResponseBody
	public Map<String, Object> deleteByFilterAjax(String jsonData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		if (jsonData != null) {
			JSONObject js = JSONObject.fromObject(jsonData);// 根据字符串转换对象
			sysTaskInfo = (SysTaskInfo) JSONObject.toBean(js, sysTaskInfo.getClass()); // 把值绑定成相应的值对象
		}
		try {
			sysTaskInfoFeign.deleteByFilter(sysTaskInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formsys0002 = formService.getFormData("sys0002");
		model.addAttribute("formsys0002", formsys0002);
		return "/component/sys/SysTaskInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		setObjValue(formsys0002, sysTaskInfo);
		sysTaskInfoFeign.insert(sysTaskInfo);
		getObjValue(formsys0002, sysTaskInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
		List<SysTaskInfo> sysTaskInfoList = (List<SysTaskInfo>) sysTaskInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys0002", formsys0002);
		model.addAttribute("sysTaskInfoList", sysTaskInfoList);
		return "/component/sys/SysTaskInfo_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String pasNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formsys0001 = formService.getFormData("sys0001");
		getFormValue(formsys0001);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		getObjValue(formsys0001, sysTaskInfo);
		model.addAttribute("formsys0001", formsys0001);
		return "/component/sys/SysTaskInfo_Detail";
	}

	/**
	 * 消息详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNewsDetail")
	public String getNewsDetail(String taskId, String ifNext, Model model) throws Exception {
		ActionContext.initialize(request, response);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_1);
		String curUserNo = User.getRegNo(getHttpRequest());
		sysTaskInfo.setPasNo(taskId);
		sysTaskInfo.setUserNo(curUserNo);
		FormService formService = new FormService();
		FormData formsysTaskInfoShow = formService.getFormData("sysTaskInfoShow");
		List<SysTaskInfo> tmpList = sysTaskInfoFeign.getNoReadNewsList(sysTaskInfo);
		if (tmpList != null) {
			if (tmpList.size() > 0) {
				sysTaskInfo.setPasNo(null);
				List<SysTaskInfo> list = sysTaskInfoFeign.getNoReadNewsList(sysTaskInfo);
				int tmpCnt = 0;
				String nextTaskId = "";
				if (list != null) {
					tmpCnt = list.size();
					if (tmpCnt > 1) {
						nextTaskId = list.get(1).getPasNo();
					}
				}
				dataMap.put("newsCnt", tmpCnt);
				dataMap.put("nextTaskId", nextTaskId);
				dataMap.put("ifNext", ifNext);
				sysTaskInfo = tmpList.get(0);
				sysTaskInfo.setPasSts("1");// 设置为已读
				sysTaskInfoFeign.update(sysTaskInfo);
				sysTaskInfo.setCreateDate(sysTaskInfo.getCreateDate() + " " + sysTaskInfo.getCreateTime());
			}
		}
		getObjValue(formsysTaskInfoShow, sysTaskInfo);
		model.addAttribute("sysTaskInfo", sysTaskInfo);
		model.addAttribute("formsysTaskInfoShow", formsysTaskInfoShow);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/sys/SysTaskInfo_newsDetail";
	}

	/**
	 * 获取消息ajax
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNextNewsDetailAjax")
	@ResponseBody
	public Map<String, Object> getNextNewsDetailAjax(String taskId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
		String curUserNo = User.getRegNo(getHttpRequest());
		sysTaskInfo.setPasNo(taskId);
		sysTaskInfo.setUserNo(curUserNo);
		List<SysTaskInfo> tmpList = sysTaskInfoFeign.getNoReadNewsList(sysTaskInfo);

		if (tmpList != null && tmpList.size() > 0) {
			// 修改当前消息为已读
			sysTaskInfo = tmpList.get(0);
			sysTaskInfo.setPasSts("1");// 设置为已读
			if(!"1".equals(sysTaskInfo.getPasMaxNo())){
				sysTaskInfoFeign.update(sysTaskInfo);
			}
			sysTaskInfo.setCreateDate(
					DateUtil.getShowDateTime(sysTaskInfo.getCreateDate()) + " " + sysTaskInfo.getCreateTime());
			// 封装下一条消息信息

			SysTaskInfo taskInfo = new SysTaskInfo();
			taskInfo.setUserNo(curUserNo);
			taskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
			List<SysTaskInfo> list = sysTaskInfoFeign.getNoReadNewsList(taskInfo);
			int tmpCnt = 0;
			String nextTaskId = "";
			if (list != null) {
				tmpCnt = list.size();
				if (tmpCnt > 1) {
					nextTaskId = list.get(1).getPasNo();

				}
			}
			dataMap.put("newsCnt", tmpCnt);
			dataMap.put("nextTaskId", nextTaskId);
			dataMap.put("newsObj", sysTaskInfo);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}

		return dataMap;
	}

	/**
	 * 消息列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNewsListPage")
	public String getNewsListPage(Model model, String taskSts) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("taskSts", taskSts);// 消息状态
		model.addAttribute("dataMap", dataMap);
		return "/component/sys/SysTaskInfo_newsList";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findNewsListPageAjax")
	@ResponseBody
	public Map<String, Object> findNewsListPageAjax(String taskSts, String ajaxData, int pageNo, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			SysTaskInfo sysTaskInfo = new SysTaskInfo();
			sysTaskInfo.setPasSts(taskSts);
			String curUserNo = User.getRegNo(getHttpRequest());

			sysTaskInfo.setUserNo(curUserNo);
			sysTaskInfo.setCustomQuery(ajaxData);
			sysTaskInfo.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
			ipage = sysTaskInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String pasNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfoFeign.delete(sysTaskInfo);
		return getListPage("1",model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002);
		boolean validateFlag = this.validateFormData(formsys0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002);
		boolean validateFlag = this.validateFormData(formsys0002);
	}

	/**
	 * 查看消息修改状态
	 * 
	 * @return
	 * @throws Exception
	 */
	public void updateCompleteMessage(SysTaskInfo sysTaskInfo, String pasNo) throws Exception {
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_1);
		sysTaskInfo.setLookTime(DateUtil.getDateTime());
		sysTaskInfoFeign.update(sysTaskInfo);
	}

	/**
	 * 未读消息-详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnReadNewsDetail")
	public String getUnReadNewsDetail(Model model, String ifNext) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_0);
		String curUserNo = User.getRegNo(getHttpRequest());
		// sysTaskInfo.setOptType("0");//纯消息
		sysTaskInfo.setUserNo(curUserNo);
		FormService formService = new FormService();
		FormData formsysTaskInfoShow = formService.getFormData("sysTaskInfoShow");
		List<SysTaskInfo> tmpList = sysTaskInfoFeign.getNoReadNewsList(sysTaskInfo);

		if (tmpList != null) {
			if (tmpList.size() > 0) {
				sysTaskInfo.setPasNo(null);
				List<SysTaskInfo> list = sysTaskInfoFeign.getNoReadNewsList(sysTaskInfo);
				int tmpCnt = 0;
				String nextTaskId = "";
				if (list != null) {
					tmpCnt = list.size();
					if (tmpCnt > 1) {
						nextTaskId = list.get(1).getPasNo();
					}
				}
				dataMap.put("newsCnt", tmpCnt);
				dataMap.put("nextTaskId", nextTaskId);
				dataMap.put("ifNext", ifNext);
				sysTaskInfo = tmpList.get(0);
				sysTaskInfo.setPasSts("1");// 设置为已读
				sysTaskInfo.setLookTime(DateUtil.getDate());//设置已读时间
				if(!"1".equals(sysTaskInfo.getPasMaxNo())){
					sysTaskInfoFeign.update(sysTaskInfo);
				}
				sysTaskInfo.setCreateDate(
						DateUtil.getShowDateTime(sysTaskInfo.getCreateDate()) + " " + sysTaskInfo.getCreateTime());
			}
		}
		getObjValue(formsysTaskInfoShow, sysTaskInfo);
		model.addAttribute("formsysTaskInfoShow", formsysTaskInfoShow);
		model.addAttribute("sysTaskInfo", sysTaskInfo);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/sys/SysTaskInfo_getUnReadNewsDetail";
	}
}
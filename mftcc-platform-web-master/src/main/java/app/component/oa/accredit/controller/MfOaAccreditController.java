package app.component.oa.accredit.controller;

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
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.base.User;
import app.component.oa.accredit.entity.MfOaAccredit;
import app.component.oa.accredit.feign.MfOaAccreditFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfOaAccreditAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Apr 25 11:16:07 CST 2017
 **/
@Controller
@RequestMapping("/mfOaAccredit")
public class MfOaAccreditController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private MfOaAccreditFeign mfOaAccreditFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String authorizerNo = User.getRegNo(this.request);
		Map<String, String> map = mfOaAccreditFeign.getSum(authorizerNo);
		model.addAttribute("authorizerNo", authorizerNo);
		model.addAttribute("map", map);
		model.addAttribute("query", "");
		return "/component/oa/accredit/MfOaAccredit_List";
	}

	@RequestMapping("/getListPageForHis")
	public String getListPageForHis(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray accreditFuncJsonArray = new CodeUtils().getJSONArrayByKeyName("ACCREDIT_FUNC");
		model.addAttribute("accreditFuncJsonArray", accreditFuncJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/accredit/MfOaAccredit_His";
	}

	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, String accreditSts,
			Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaAccredit mfOaAccredit = new MfOaAccredit();
		MfOaAccredit mfOaAccreditnew = new MfOaAccredit();
		try {
			mfOaAccreditFeign.updateAll(mfOaAccreditnew);
			mfOaAccredit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaAccredit.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfOaAccredit.setCriteriaList(mfOaAccredit, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaDebtexpense,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			mfOaAccredit.setAuthorizerNo(User.getRegNo(this.request));
			mfOaAccredit.setAccreditSts(accreditSts);
			Map<String, Object> params = new HashMap<>();
			params.put("mfOaAccredit", mfOaAccredit);
			ipage.setParams(params);
			ipage = mfOaAccreditFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// JSONArray accreditFuncJsonArray = new
		// CodeUtils().getJSONArrayByKeyName("ACCREDIT_FUNC");
		// this.request.setAttribute("accreditFuncJsonArray",
		// accreditFuncJsonArray);
		// Map<String, String> accreditFuncMap = new
		// CodeUtils().getMapByKeyName("ACCREDIT_FUNC");
		// this.request.setAttribute("accreditFuncMap", accreditFuncMap);

		MfOaAccredit mfOaAccredit = new MfOaAccredit();
		mfOaAccredit.setAuthorizerNo(User.getRegNo(this.request));
		mfOaAccredit.setAuthorizerName(User.getRegName(this.request));
		mfOaAccredit.setAuthorizerBrNo(User.getOrgNo(this.request));
		mfOaAccredit.setAuthorizerBrName(User.getOrgName(this.request));
		FormData formaccredit0002 = new FormService().getFormData("accredit0002");
		getObjValue(formaccredit0002, mfOaAccredit);
		// //选择组件需要数据
		// SysInterface
		// sysInterface=(SysInterface)SourceTemplate.getSpringContextInstance().getBean("sysInterface",
		// SysInterface.class);
		// List<SysUser> userList=sysInterface.getAllUser(null);
		// List<Map<String,String>> usersData=new
		// ArrayList<Map<String,String>>();
		// for(SysUser user:userList){
		// Map<String,String> map=new HashMap<String,String>();
		// map.put("id", user.getOpNo());
		// map.put("name", user.getOpName());
		// usersData.add(map);
		// }
		List<Map<String, Object>> funcsData = new ArrayList<Map<String, Object>>();
		JSONArray accreditFuncJsonArray = new CodeUtils().getJSONArrayByKeyName("PAS_SUB_TYPE");
		for (int i = 0; i < accreditFuncJsonArray.size(); i++) {
			String optCode = String.valueOf(accreditFuncJsonArray.getJSONObject(i).get("optCode"));
			String optName = String.valueOf(accreditFuncJsonArray.getJSONObject(i).get("optName"));
			Map<String, Object> map = new HashMap<String, Object>();
			if ("001".equals(optCode) || "002".equals(optCode) || "501".equals(optCode) || "302".equals(optCode)
					|| "601".equals(optCode)) {
				continue;
			}
			map.put("id", optCode);
			map.put("name", optName);
			funcsData.add(map);
		}
		// dataMap=new HashMap<String,Object>();
		// dataMap.put("usersData", new Gson().toJson(usersData));
		// dataMap.put("funcsData", new Gson().toJson(funcsData));
		model.addAttribute("funcsData", new Gson().toJson(funcsData));
		model.addAttribute("formaccredit0002", formaccredit0002);
		model.addAttribute("nowTime", DateUtil.getYYYYMMDD(DateUtil.getDateTime()));
		model.addAttribute("query", "");
		return "/component/oa/accredit/MfOaAccredit_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formaccredit0002 = new FormService().getFormData("accredit0002");
			getFormValue(formaccredit0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccredit0002)) {
				MfOaAccredit mfOaAccredit = new MfOaAccredit();
				setObjValue(formaccredit0002, mfOaAccredit);
				mfOaAccreditFeign.insert(mfOaAccredit);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("托管"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("托管"));
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("托管"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String accreditId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaAccredit mfOaAccredit = new MfOaAccredit();
			mfOaAccredit.setAccreditId(accreditId);
			mfOaAccredit = mfOaAccreditFeign.getById(mfOaAccredit);
			int[] count = mfOaAccreditFeign.update(mfOaAccredit);// 0:旧任务，1：新任务
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("收回"));
			dataMap.put("count", count[0] + count[1]);
			dataMap.put("countNew", count[1]);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("收回"));
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String accreditId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formaccredit0002 = new FormService().getFormData("accredit0002");
		MfOaAccredit mfOaAccredit = new MfOaAccredit();
		mfOaAccredit.setAccreditId(accreditId);
		mfOaAccredit = mfOaAccreditFeign.getById(mfOaAccredit);
		getObjValue(formaccredit0002, mfOaAccredit, formData);
		if (mfOaAccredit != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String ajaxData, String accreditId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaAccredit mfOaAccredit = new MfOaAccredit();
			mfOaAccredit.setAccreditId(accreditId);
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfOaAccredit = (MfOaAccredit) JSONObject.toBean(jb, MfOaAccredit.class);
			mfOaAccreditFeign.delete(mfOaAccredit);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 检查托管是否有冲突
	 *
	 */
	@ResponseBody
	@RequestMapping("/checkAccreditAjax")
	public Map<String, Object> checkAccreditAjax(String ajaxData) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaAccredit mfOaAccredit = new MfOaAccredit();
			FormData formaccredit0002 = new FormService().getFormData("accredit0002");
			getFormValue(formaccredit0002, getMapByJson(ajaxData));
			setObjValue(formaccredit0002, mfOaAccredit);
			Map<String, Object> resultMap = mfOaAccreditFeign.checkAccredit(mfOaAccredit);
			dataMap.put("flag", "success");
			dataMap.put("result", resultMap);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
		}
		return dataMap;
	}

}

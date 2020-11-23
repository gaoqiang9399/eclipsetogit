package app.component.oa.archive.controller;

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

import app.component.common.BizPubParm;
import app.component.oa.archive.entity.MfOaArchivesRewards;
import app.component.oa.archive.feign.MfOaArchivesRewardsFeign;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

@Controller
@RequestMapping("/mfOaArchivesRewards")
public class MfOaArchivesRewardsController extends BaseFormBean {
	@Autowired
	private MfOaArchivesRewardsFeign mfOaArchivesRewardsFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/oa/archive/MfOaArchivesRewards_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		try {
			mfOaArchivesRewards.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaArchivesRewards.setCriteriaList(mfOaArchivesRewards, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaArchivesRewards,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaArchivesRewards", mfOaArchivesRewards));
			ipage = mfOaArchivesRewardsFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formarchivereward0001 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formarchivereward0001, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formarchivereward0001)) {
				MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
				MfOaArchivesRewards mfOaArchivesRewardsOldData = new MfOaArchivesRewards();
				setObjValue(formarchivereward0001, mfOaArchivesRewards);
				mfOaArchivesRewardsOldData = mfOaArchivesRewardsFeign.getById(mfOaArchivesRewards);
				if (mfOaArchivesRewardsOldData == null) {
					mfOaArchivesRewards.setRewardId(WaterIdUtil.getWaterId());
					mfOaArchivesRewardsFeign.insert(mfOaArchivesRewards);
				} else {
					mfOaArchivesRewardsFeign.update(mfOaArchivesRewards);
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String rewardsTableHtml = jtu.getJsonStr(
						mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE3),
						"tableTag", mfOaArchivesRewardsFeign.getByBaseId(mfOaArchivesRewards), null, true);
				dataMap.put("rewardsTableHtml", rewardsTableHtml);
				dataMap.put("mfOaArchivesRewards", mfOaArchivesRewards);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String rewardId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		mfOaArchivesRewards.setRewardId(rewardId);
		mfOaArchivesRewards.setBaseId(baseId);
		try {
			mfOaArchivesRewardsFeign.delete(mfOaArchivesRewards);
			JsonTableUtil jtu = new JsonTableUtil();
			String rewardsTableHtml = jtu.getJsonStr(
					mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE3), "tableTag",
					mfOaArchivesRewardsFeign.getByBaseId(mfOaArchivesRewards), null, true);
			dataMap.put("rewardsTableHtml", rewardsTableHtml);
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
	@RequestMapping("/input")
	public String input(Model model,String rewardId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivereward0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE1));
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		mfOaArchivesRewards.setRewardId(rewardId);
		mfOaArchivesRewards = mfOaArchivesRewardsFeign.getById(mfOaArchivesRewards);
		if (mfOaArchivesRewards == null) {
			mfOaArchivesRewards = new MfOaArchivesRewards();
			mfOaArchivesRewards.setBaseId(baseId);
		}
		getObjValue(formarchivereward0001, mfOaArchivesRewards);
		model.addAttribute("formarchivereward0001", formarchivereward0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesRewards_Insert";
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String rewardId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		mfOaArchivesRewards.setRewardId(rewardId);
		mfOaArchivesRewards = mfOaArchivesRewardsFeign.getById(mfOaArchivesRewards);
		String fromId = mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE2);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formarchivereward0002 = formService.getFormData(fromId);
		getObjValue(formarchivereward0002, mfOaArchivesRewards, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formarchivereward0002, "propertySeeTag", "");
		if (mfOaArchivesRewards != null) {
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", mfOaArchivesRewards);
		return dataMap;
	}
}

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
import app.component.oa.archive.entity.MfOaArchivesBase;
import app.component.oa.archive.entity.MfOaArchivesEducation;
import app.component.oa.archive.entity.MfOaArchivesFamily;
import app.component.oa.archive.entity.MfOaArchivesRewards;
import app.component.oa.archive.entity.MfOaArchivesWork;
import app.component.oa.archive.feign.MfOaArchivesBaseFeign;
import app.component.oa.archive.feign.MfOaArchivesEducationFeign;
import app.component.oa.archive.feign.MfOaArchivesFamilyFeign;
import app.component.oa.archive.feign.MfOaArchivesRewardsFeign;
import app.component.oa.archive.feign.MfOaArchivesWorkFeign;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.component.sys.entity.SysUser;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mfOaArchivesBase")
public class MfOaArchivesBaseController extends BaseFormBean {
	@Autowired
	private MfOaArchivesBaseFeign mfOaArchivesBaseFeign;
	@Autowired
	private MfOaArchivesWorkFeign mfOaArchivesWorkFeign;
	@Autowired
	private MfOaArchivesEducationFeign mfOaArchivesEducationFeign;
	@Autowired
	private MfOaArchivesRewardsFeign mfOaArchivesRewardsFeign;
	@Autowired
	private MfOaArchivesFamilyFeign mfOaArchivesFamilyFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
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
		return "/component/oa/archive/MfOaArchivesBase_List";
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
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		try {
			mfOaArchivesBase.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaArchivesBase.setCriteriaList(mfOaArchivesBase, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaArchivesBase", mfOaArchivesBase));
			ipage = mfOaArchivesBaseFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formarchive0001 = formService
					.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesBaseAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formarchive0001, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formarchive0001)) {
				MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
				MfOaArchivesBase mfOaArchivesBase1 = new MfOaArchivesBase();
				setObjValue(formarchive0001, mfOaArchivesBase);
				mfOaArchivesBase1 = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
				if (mfOaArchivesBase1 == null) {
					mfOaArchivesBase.setEmployeeTypeUpdateDate(DateUtil.getDate());
					mfOaArchivesBase.setBaseId(WaterIdUtil.getWaterId());
					mfOaArchivesBase.setUseFlag(BizPubParm.YES_NO_Y);
					mfOaArchivesBase.setRegTime(DateUtil.getDateTime());
					mfOaArchivesBaseFeign.insert(mfOaArchivesBase);
				} else {
					mfOaArchivesBase.setLstModTime(DateUtil.getDateTime());
					mfOaArchivesBaseFeign.update(mfOaArchivesBase);
				}
				mfOaArchivesBase = getDic(mfOaArchivesBase);
				FormData formentrymanagement = formService.getFormData(
						mfOaFormConfigFeign.getFormByAction("MfOaArchivesBaseAction", BizPubParm.SHOW_TYPE2));
				getFormValue(formentrymanagement);
				getObjValue(formentrymanagement, mfOaArchivesBase);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formentrymanagement, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("mfOaArchivesBase", mfOaArchivesBase);
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
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		mfOaArchivesBase.setHireDate(DateUtil.getShowDateTime(mfOaArchivesBase.getHireDate()));
		mfOaArchivesBase.setPositionShow(new CodeUtils().getMapByKeyName("DUTIES").get(mfOaArchivesBase.getPosition()));
		mfOaArchivesBase.setSexShow(new CodeUtils().getMapByKeyName("SEX").get(mfOaArchivesBase.getSex()));
		dataMap.put("flag", "success");
		dataMap.put("mfOaArchivesBase", mfOaArchivesBase);
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchive0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesBaseAction", BizPubParm.SHOW_TYPE1));
		model.addAttribute("formarchive0001", formarchive0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesBase_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 初始化基本信息表单、工作经历表单
		FormData formentrymanagement = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesBaseAction", BizPubParm.SHOW_TYPE2));
		// 新建对象
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		// 设置baseId
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesWork.setBaseId(baseId);
		mfOaArchivesEducation.setBaseId(baseId);
		mfOaArchivesRewards.setBaseId(baseId);
		mfOaArchivesFamily.setBaseId(baseId);
		// 根据baseId获取相关数据
		List<MfOaArchivesWork> workList = mfOaArchivesWorkFeign.getByBaseId(mfOaArchivesWork);
		List<MfOaArchivesEducation> eduList = mfOaArchivesEducationFeign.getByBaseId(mfOaArchivesEducation);
		List<MfOaArchivesRewards> redList = mfOaArchivesRewardsFeign.getByBaseId(mfOaArchivesRewards);
		List<MfOaArchivesFamily> famList = mfOaArchivesFamilyFeign.getByBaseId(mfOaArchivesFamily);
		CodeUtils codeUtils = new CodeUtils();
		if (eduList != null) {
			for (int i = 0, len = eduList.size(); i < len; i++) {
				eduList.get(i).setEducation((codeUtils.getMapByKeyName("EDU")).get(eduList.get(i).getEducation()));
			}
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String familyTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE3), "tableTag",
				famList, null, true);
		String workTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE3), "tableTag",
				workList, null, true);
		String rewardTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE3), "tableTag",
				redList, null, true);
		String educationTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE3), "tableTag",
				eduList, null, true);

		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		mfOaArchivesBase = getDic(mfOaArchivesBase);
		mfOaArchivesBase.setBirthday(DateUtil.getShowDateTime(mfOaArchivesBase.getBirthday()));

		// 实体对象放到表单对象中
		getObjValue(formentrymanagement, mfOaArchivesBase);

		// 转成json串，在jsp中转成json对象，在js中使用。
		String archivesbase = JSONObject.fromObject(mfOaArchivesBase).toString();
		Map<String, String> mapNation = codeUtils.getMapByKeyName("NATION");
		Map<String, Object> parmdicMap = new HashMap<String, Object>();
		parmdicMap.put("mapNation", mapNation);
		parmdicMap.put("workTableHtml", workTableHtml);
		parmdicMap.put("educationTableHtml", educationTableHtml);
		parmdicMap.put("rewardTableHtml", rewardTableHtml);
		parmdicMap.put("familyTableHtml", familyTableHtml);
		model.addAttribute("parmdicMap", parmdicMap);
		model.addAttribute("archivesbase", archivesbase);
		model.addAttribute("baseId", baseId);
		model.addAttribute("mfOaArchivesBase", mfOaArchivesBase);
		model.addAttribute("formentrymanagement", formentrymanagement);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesBase_Detail";
	}

	// 得到数据字典
	public MfOaArchivesBase getDic(MfOaArchivesBase mfOaArchivesBase) throws Exception {
		CodeUtils codeUtils = new CodeUtils();
		mfOaArchivesBase.setNation((codeUtils.getMapByKeyName("NATION")).get(mfOaArchivesBase.getNation()));
		mfOaArchivesBase.setMarriageSts((codeUtils.getMapByKeyName("MARRIGE")).get(mfOaArchivesBase.getMarriageSts()));
		mfOaArchivesBase.setEducation((codeUtils.getMapByKeyName("EDU")).get(mfOaArchivesBase.getEducation()));
		mfOaArchivesBase.setIfParty((codeUtils.getMapByKeyName("IFPARTY")).get(mfOaArchivesBase.getIfParty()));
		mfOaArchivesBase.setSex((codeUtils.getMapByKeyName("SEX")).get(mfOaArchivesBase.getSex()));
		mfOaArchivesBase.setOpSts((codeUtils.getMapByKeyName("JOB_STS")).get(mfOaArchivesBase.getOpSts()));
		mfOaArchivesBase.setPosition((codeUtils.getMapByKeyName("DUTIES")).get(mfOaArchivesBase.getPosition()));
		return mfOaArchivesBase;
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByBaseId")
	public String getByBaseId(Model model, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 初始化基本信息表单
		FormData formarchive0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaArchivesBaseAction", BizPubParm.SHOW_TYPE1));
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		// 实体对象放到表单对象中
		getObjValue(formarchive0001, mfOaArchivesBase);
		model.addAttribute("formarchive0001", formarchive0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesBase_Find";
	}

	/**
	 * 
	 * 方法描述： 跳转选择操作员弹框
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-11 下午19:52:55
	 */
	@RequestMapping("/getSysUserSelect")
	public String getSysUserSelect() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/oa/archive/MfOaArchivesBase_SysUserSelect";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getSysUserPageAjax")
	@ResponseBody
	public Map<String, Object> getSysUserPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysUser sysUser = new SysUser();
		try {
			sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
			sysUser.setCustomSorts(ajaxData);
			sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("sysUser", sysUser));
			ipage = sysInterfaceFeign.getSysUserPage(ipage, sysUser);
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
	 * 
	 * 方法描述： 跳转选择正式职员弹框
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-11 下午19:52:55
	 */
	@RequestMapping("/getNameSelect")
	public String getNameSelect(Model model,String opSts) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("opSts", opSts);
		return "/component/oa/archive/MfOaArchivesBase_NameSelect";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getNamePageAjax")
	@ResponseBody
	public Map<String, Object> getNamePageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType, String opSts) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		try {
			mfOaArchivesBase.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaArchivesBase.setCustomSorts(ajaxData);
			mfOaArchivesBase.setCriteriaList(mfOaArchivesBase, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaArchivesBase", mfOaArchivesBase));
			ipage = mfOaArchivesBaseFeign.getNamePage(ipage, opSts);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	// 上传头像-
	@RequestMapping("/uploadHeadImg")
	public String uploadHeadImg(Model model, String baseId, String headImg, String ifUploadHead) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		headImg = mfOaArchivesBase.getHeadImg();
		ifUploadHead = mfOaArchivesBase.getIfUploadHead();
		FormData formsysuploadhead0001 = formService.getFormData("sysuploadhead0001");
		model.addAttribute("headImg", headImg);
		model.addAttribute("ifUploadHead", ifUploadHead);
		model.addAttribute("baseId", baseId);
		model.addAttribute("formsysuploadhead0001", formsysuploadhead0001);
		model.addAttribute("query", "");
		return "/component/oa/archive/MfOaArchivesBase_Upload";
	}

	@RequestMapping("/submitUploadHeadImg")
	@ResponseBody
	public Map<String, Object> submitUploadHeadImg(String baseId, String headImg) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		mfOaArchivesBase.setHeadImg(headImg);
		mfOaArchivesBase.setIfUploadHead("1");
		mfOaArchivesBase.setIfUpdateHeadImg(BizPubParm.YES_NO_Y);
		try {
			mfOaArchivesBaseFeign.update(mfOaArchivesBase);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}

	// 方法描述： 获得员工是否上传过头像
	@RequestMapping("/getIfUploadHeadImg")
	@ResponseBody
	public Map<String, Object> getIfUploadHeadImg(String baseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		mfOaArchivesBase.setBaseId(baseId);
		try {
			mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
			dataMap.put("flag", mfOaArchivesBase.getIfUploadHead());
			dataMap.put("headImg", mfOaArchivesBase.getHeadImg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
		}
		return dataMap;
	}
}

package app.component.lawsuit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.lawsuit.entity.MfLawsuitPerformReg;
import app.component.lawsuit.feign.MfLawsuitPerformRegFeign;
import app.component.pact.assetmanage.entity.MfAssetManage;
import app.component.pact.assetmanage.entity.MfLitigationExpenseApply;
import app.component.pact.assetmanage.feign.MfLitigationExpenseApplyFeign;
import app.component.sys.entity.MfSysCompanyMst;
import app.component.sys.feign.MfSysCompanyMstFeign;
import com.core.struts.taglib.JsonFormUtil;
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

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.entity.MfLawsuitFollow;
import app.component.lawsuit.feign.MfLawsuitFeign;
import app.component.lawsuit.feign.MfLawsuitFollowFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfLawsuitAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Feb 22 21:19:41 CST 2017
 **/
@Controller
@RequestMapping("/mfLawsuit")
public class MfLawsuitController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfLawsuitBo
	@Autowired
	private MfLawsuitFeign mfLawsuitFeign;
	@Autowired
	private MfSysCompanyMstFeign mfSysCompanyMstFeign;
	@Autowired
	private MfLitigationExpenseApplyFeign mfLitigationExpenseApplyFeign;
	@Autowired
	private MfLawsuitFollowFeign lawsuitFollowFegin;
	@Autowired
	private MfLawsuitPerformRegFeign mfLawsuitPerformRegFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String pactNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("pactNo", pactNo);
		return "/component/lawsuit/MfLawsuit_List";
	}
	/**
	 * @方法描述： 获取选择列表
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月26日 上午9:39:30
	 */
	@RequestMapping(value = "/getSelectInfo")
	public String getSelectInfo(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/lawsuit/MfLawsuit_SelectInfo";
	}

	/**
	 * 查询入库打开列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForQuery")
	public String getListPageForQuery(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray caseTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CASE_TYPE");
		JSONArray caseStateJsonArray = new CodeUtils().getJSONArrayByKeyName("CASE_STATE");
		model.addAttribute("caseTypeJsonArray", caseTypeJsonArray);
		model.addAttribute("caseStateJsonArray", caseStateJsonArray);
		return "/component/lawsuit/MfLawsuitQuery_List";
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
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String pactNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setPactId(pactNo);
		try {
			mfLawsuit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfLawsuit.setCriteriaList(mfLawsuit, ajaxData);// 我的筛选
			// this.getRoleConditions(mfLawsuit,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfLawsuit", mfLawsuit));
			ipage = mfLawsuitFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 查询页面展示列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageQueryAjax")
	@ResponseBody
	public Map<String, Object> findByPageQueryAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuit mfLawsuit = new MfLawsuit();
		try {
			mfLawsuit.setCustomQuery(ajaxData);// 智能搜索参数赋值
			mfLawsuit.setCustomSorts(ajaxData);// 排序数据
			mfLawsuit.setCriteriaList(mfLawsuit, ajaxData);// 我的筛选
			// this.getRoleConditions(mfLawsuit,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfLawsuit", mfLawsuit));
			ipage = mfLawsuitFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
			getFormValue(formlawsuit0002, getMapByJson(ajaxData));
			if (this.validateFormData(formlawsuit0002)) {
				MfLawsuit mfLawsuit = new MfLawsuit();
				setObjValue(formlawsuit0002, mfLawsuit);
				mfLawsuit.setCusName(mfLawsuit.getDefendantName());
				mfLawsuit.setCusNo(mfLawsuit.getDefendantId());
				MfLawsuit mfLawsuitTemp = mfLawsuitFeign.getById(mfLawsuit);
				//防止存入空值，导致类型转换错误
				if(mfLawsuit.getRecoverableAmount()==null || StringUtil.isEmpty(mfLawsuit.getRecoverableAmount().toString())){
					mfLawsuit.setRecoverableAmount(0.00);
				}
				if(mfLawsuit.getCost()==null || StringUtil.isEmpty(mfLawsuit.getCost().toString())){
					mfLawsuit.setCost(0.00);
				}
				if (mfLawsuitTemp == null) {
					mfLawsuitFeign.insert(mfLawsuit);
				} else {
					mfLawsuitFeign.update(mfLawsuit);
				}
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
     * AJAX新增  与客户关联使用
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax1")
    @ResponseBody
    public Map<String, Object> insertAjax1(String ajaxData) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formlawsuit0004 = formService.getFormData("lawsuit0004");
            getFormValue(formlawsuit0004, getMapByJson(ajaxData));
            if (this.validateFormData(formlawsuit0004)) {
                MfLawsuit mfLawsuit = new MfLawsuit();
                setObjValue(formlawsuit0004, mfLawsuit);
                MfLawsuit mfLawsuitTemp = mfLawsuitFeign.getById(mfLawsuit);
                //防止存入空值，导致类型转换错误
                if(mfLawsuit.getRecoverableAmount()==null || StringUtil.isEmpty(mfLawsuit.getRecoverableAmount().toString())){
                    mfLawsuit.setRecoverableAmount(0.00);
                }
                if(mfLawsuit.getCost()==null || StringUtil.isEmpty(mfLawsuit.getCost().toString())){
                    mfLawsuit.setCost(0.00);
                }
                if (mfLawsuitTemp == null) {
                    mfLawsuitFeign.insert(mfLawsuit);
                } else {
                    mfLawsuitFeign.update(mfLawsuit);
                }
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
		getFormValue(formlawsuit0002, getMapByJson(ajaxData));
		MfLawsuit mfLawsuitJsp = new MfLawsuit();
		setObjValue(formlawsuit0002, mfLawsuitJsp);
		MfLawsuit mfLawsuit = mfLawsuitFeign.getById(mfLawsuitJsp);
		if (mfLawsuit != null) {
			try {
				mfLawsuit = (MfLawsuit) EntityUtil.reflectionSetVal(mfLawsuit, mfLawsuitJsp, getMapByJson(ajaxData));
				mfLawsuitFeign.update(mfLawsuit);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formlawsuit0002 = formService.getFormData("lawsuit0001");
			getFormValue(formlawsuit0002, getMapByJson(ajaxData));
			if (this.validateFormData(formlawsuit0002)) {
				MfLawsuit mfLawsuit = new MfLawsuit();
				setObjValue(formlawsuit0002, mfLawsuit);
				mfLawsuitFeign.update(mfLawsuit);
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
	 * AJAX跟进
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/followAjax")
	@ResponseBody
	public Map<String, Object> followAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);
			String formId = ajaxDataMap.get("formId").toString();
			FormData formlawsuit0002 = formService.getFormData(formId);
			getFormValue(formlawsuit0002, ajaxDataMap);
			if (this.validateFormData(formlawsuit0002)) {
				MfLawsuitFollow lawsuitFollow = new MfLawsuitFollow();
				setObjValue(formlawsuit0002, lawsuitFollow);
				lawsuitFollow.setFollowId(WaterIdUtil.getWaterId());
				lawsuitFollow.setBrName(User.getOrgName(request));
				lawsuitFollow.setBrNo(User.getOrgNo(request));
				lawsuitFollow.setOpName(User.getRegName(request));
				lawsuitFollow.setOpNo(User.getRegNo(request));
				mfLawsuitFeign.follow(lawsuitFollow);
				lawsuitFollow = lawsuitFollowFegin.getById(lawsuitFollow);
				String showTime = DateUtil.getShowDateTime(lawsuitFollow.getCreatDate());//初始化时间
				lawsuitFollow.setCreatDate(showTime);
				MfLawsuit lawsuit = new MfLawsuit();
				lawsuit.setCaseId(lawsuitFollow.getCaseId());
				lawsuit = mfLawsuitFeign.getById(lawsuit);
				//由于getById中方法将字典项放入字段，update时会报错
				MfLawsuit mls = new MfLawsuit();
				mls.setCaseId(lawsuitFollow.getCaseId());
				//更新案件登记表中字段 -- start
				if(ajaxDataMap.get("caseState") != null){
					//案件状态
					mls.setCaseState(ajaxDataMap.get("caseState").toString());
				}
				if( ajaxDataMap.get("actCourt") != null){
					//受理法院
					mls.setCourt(ajaxDataMap.get("actCourt").toString());
				}
				if("2".equals(lawsuitFollow.getFollowType())||"3".equals(lawsuitFollow.getFollowType())){
					mls.setJudge(lawsuitFollow.getJudge());
					mls.setJudgeTel(lawsuitFollow.getJudgeTel());
				}else if("4".equals(lawsuitFollow.getFollowType())){
					mls.setPerformJudge(lawsuitFollow.getPerformJudge());
					mls.setPerformJudgeTel(lawsuitFollow.getPerformJudgeTel());
				}else {
				}
				mfLawsuitFeign.update(mls);
				String htmlStr = getLawsuitFollowListHtml(lawsuitFollow.getCaseId());
				//更新案件登记表中字段 -- end
				dataMap.put("flag", "success");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("lawsuitFollow", lawsuitFollow);
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
	public String getLawsuitFollowListHtml(String caseId){
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		String tableHtml = "";
		try{
			mfLawsuitFollow.setCaseId(caseId);
			List<MfLawsuitFollow> mfLawsuitFollowList=lawsuitFollowFegin.getFollowListByCaseId(mfLawsuitFollow);
			JsonTableUtil jtu = new JsonTableUtil();
			tableHtml = jtu.getJsonStr("tablelawsuitfollow0001", "tableTag", mfLawsuitFollowList, null, true);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return tableHtml;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String caseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setCaseId(caseId);
		mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
		getObjValue(formlawsuit0002, mfLawsuit, formData);
		if (mfLawsuit != null) {
			dataMap.put("flag", "success");
			dataMap.put("mfLawsuit", mfLawsuit);
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String caseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setCaseId(caseId);
		try {
			mfLawsuitFeign.delete(mfLawsuit);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
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
	public String input(Model model,String pactNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setPactId(pactNo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = mfLawsuitFeign.initLawsuit(mfLawsuit);
		mfLawsuit = (MfLawsuit)JsonStrHandling.handlingStrToBean(dataMap.get("mfLawsuit"), MfLawsuit.class);
		String query = (String) dataMap.get("query");
		getObjValue(formlawsuit0002, mfLawsuit);
		model.addAttribute("formlawsuit0002", formlawsuit0002);
		model.addAttribute("query", query);
		return "/component/lawsuit/MfLawsuit_Insert";
	}
	/**
	 * 新增页面1
	 * @auther LHB
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input1")
	public String input1(Model model,String pactNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuit0004 = formService.getFormData("lawsuit0004");
		MfLawsuit mfLawsuit = new MfLawsuit();
		MfSysCompanyMst mfSysCompanyMst=new MfSysCompanyMst();
		mfSysCompanyMst.setComNo(BizPubParm.SYS_ORG_BR_NO);
		mfSysCompanyMst=mfSysCompanyMstFeign.getById(mfSysCompanyMst);
        mfLawsuit.setRegDate(DateUtil.getDate());
        mfLawsuit.setOpName(User.getRegName(request));
		mfLawsuit.setOpNo(User.getRegNo(request));
        mfLawsuit.setCaseId(WaterIdUtil.getWaterId());
		mfLawsuit.setBrName(User.getOrgName(request));
		mfLawsuit.setBrNo(User.getOrgNo(request));
		mfLawsuit.setAccuserName(mfSysCompanyMst.getComName());


		String query = "";
		getObjValue(formlawsuit0004, mfLawsuit);
		model.addAttribute("formlawsuit0004", formlawsuit0004);
		model.addAttribute("query", query);
		return "/component/lawsuit/MfLawsuit_Insert1";
	}

	/**
	 * 根据合同查看详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByPact")
	public String getByPact(Model model, String pactNo) throws Exception {
		ActionContext.initialize(request, response);
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setPactId(pactNo);
		List<MfLawsuit> mfLawsuitList = mfLawsuitFeign.getByPact(mfLawsuit);
		model.addAttribute("mfLawsuitList", mfLawsuitList);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuit_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
		getFormValue(formlawsuit0002);
		MfLawsuit mfLawsuit = new MfLawsuit();
		setObjValue(formlawsuit0002, mfLawsuit);
		mfLawsuitFeign.insert(mfLawsuit);
		getObjValue(formlawsuit0002, mfLawsuit);
		this.addActionMessage(model, "保存成功");
		Ipage ipage= this.getIpage();
//		ipage.setParams(params);
		ipage.setParams(this.setIpageParams("mfLawsuit", mfLawsuit));
		List<MfLawsuit> mfLawsuitList = (List<MfLawsuit>) mfLawsuitFeign.findByPage(ipage).getResult();
		model.addAttribute("mfLawsuitList", mfLawsuitList);
		model.addAttribute("formlawsuit0002", formlawsuit0002);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuit_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String pactNo, String caseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setPactId(pactNo);
		mfLawsuit.setCaseId(caseId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_LAWSUIT;
		mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
		// 诉讼金额-金额格式化
		dataMap.put("caseAmt", MathExtend.moneyStr(mfLawsuit.getCaseAmt()));
		// 处理诉讼类型
		Map<String, String> caseTypeMap = new CodeUtils().getMapByKeyName("CASE_TYPE");
		dataMap.put("caseType", caseTypeMap.get(mfLawsuit.getCaseType()));
		// 诉讼余额-金额格式化
		MfLawsuitPerformReg mfLawsuitPerformReg = new MfLawsuitPerformReg();
		mfLawsuitPerformReg.setCaseId(caseId);
		List<MfLawsuitPerformReg> mfLawsuitPerformRegList = mfLawsuitPerformRegFeign.getPerformRegList(mfLawsuitPerformReg);
		if(mfLawsuitPerformRegList.size()>0){//计算诉讼余额
			Double performAmt = 0.00;
			for(MfLawsuitPerformReg performRegs : mfLawsuitPerformRegList){
				performAmt = MathExtend.add(performAmt,performRegs.getPerformPrcp());
			}
			dataMap.put("loanBalance", MathExtend.moneyStr(MathExtend.subtract(mfLawsuit.getCaseAmt(),performAmt)));
			dataMap.put("actAmt",MathExtend.moneyStr(MathExtend.subtract(mfLawsuit.getCaseAmt(),MathExtend.subtract(mfLawsuit.getCaseAmt(),performAmt))));
		}else {
			dataMap.put("loanBalance", MathExtend.moneyStr(mfLawsuit.getCaseAmt()));
			dataMap.put("actAmt",MathExtend.moneyStr("0"));
		}
		// 费用金额-金额格式化
		String cost = null;
		if(mfLawsuit.getCost()!= null){
			cost = mfLawsuit.getCost().toString();
		}else{
			MfLitigationExpenseApply mfLitigationExpenseApply = new MfLitigationExpenseApply();
			mfLitigationExpenseApply.setAssetId(caseId);
			mfLitigationExpenseApply.setCusNo(mfLawsuit.getCusNo());
			cost = mfLitigationExpenseApplyFeign.calculateFeeSum(mfLitigationExpenseApply);
		}
		dataMap.put("cost", MathExtend.moneyStr(cost));
		// 金额格式化
		dataMap.put("recoverableAmount", MathExtend.moneyStr(mfLawsuit.getRecoverableAmount()));
		String result = "/component/lawsuit/MfLawsuit_Detail";
		String formId = "lawsuit0003";
		String flag = request.getParameter("flag");
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		if ("flagShow".equals(flag)) {// 查询入口展示页面同时展示合同信息和案件信息
			model.addAttribute("mfLawsuit", mfLawsuit);
			result = "/component/lawsuit/MfLawsuit_DetailShow";
		}
		FormData formlawsuit0002 = formService.getFormData(formId);
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
		FormData formdecisionMediateDetail = formService.getFormData("decisionMediateDetail");
		getFormValue(formlawsuit0002);
		getObjValue(formlawsuit0002, mfLawsuit);
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		List<MfLawsuitFollow> mlfList = new ArrayList<MfLawsuitFollow>();
		mfLawsuitFollow.setCaseId(mfLawsuit.getCaseId());
		mlfList = lawsuitFollowFegin.getFollowListByCaseId(mfLawsuitFollow);
        double actAmt = 0.00;
		if(mlfList.size()>0 && mlfList != null){
			for(MfLawsuitFollow follows : mlfList){
				if("3".equals(follows.getFollowType()) || "8".equals(follows.getFollowType()) ||"9".equals(follows.getFollowType())){
					getObjValue(formdecisionMediateDetail, follows);
				}
				if("4".equals(follows.getFollowType())){//详情页面获取执行金额
                    actAmt = MathExtend.add(actAmt,follows.getActAmt());
                    if(actAmt<=0){
                        actAmt=0.00;
                    }
                }
			}
		}
		request.setAttribute("ifBizManger","3");
		// 格式化日期
		mfLawsuit.setCaseTime(DateUtil.getDiyDate(mfLawsuit.getCaseTime(), "yyyy-MM-dd"));
		model.addAttribute("actAmt", MathExtend.moneyStr(actAmt));
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("caseId", mfLawsuit.getCaseId());
		model.addAttribute("pactNo", pactNo);
		model.addAttribute("mfLawsuit", mfLawsuit);
		model.addAttribute("formlawsuitfollow0002", formlawsuitfollow0002);
		model.addAttribute("formlawsuit0001", formlawsuit0002);
		model.addAttribute("formdecisionMediateDetail", formdecisionMediateDetail);
		model.addAttribute("query", "");
		return result;
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String caseId, String pactNo) throws Exception {
		ActionContext.initialize(request, response);
		MfLawsuit mfLawsuit = new MfLawsuit();
		mfLawsuit.setCaseId(caseId);
		mfLawsuitFeign.delete(mfLawsuit);
		return getListPage(model, pactNo);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
		getFormValue(formlawsuit0002);
		boolean validateFlag = this.validateFormData(formlawsuit0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuit0002 = formService.getFormData("lawsuit0002");
		getFormValue(formlawsuit0002);
		boolean validateFlag = this.validateFormData(formlawsuit0002);
	}

	/**
	 * 获取已通过诉讼申请列表
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAssetManageList")
	@ResponseBody
	public Map<String, Object> getAssetManageList(Integer pageNo, Integer pageSize, String tableId, String tableType,
		 String ajaxData,String applyFlag) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetManage mfAssetManage=new MfAssetManage();
		mfAssetManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
		mfAssetManage.setCriteriaList(mfAssetManage, ajaxData);// 我的筛选
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage.setParams(this.setIpageParams("mfAssetManage",mfAssetManage));
		ipage=mfLitigationExpenseApplyFeign.getAssetManageList(ipage,applyFlag);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		dataMap.put("flag", "success");
		return dataMap;
	}
	/**
	 * 获取关联的诉讼申请相关信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getInfo")
	@ResponseBody
	public Map<String, Object> getInfo(Model model,String assetId,String applyFlag) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
        MfLawsuit mfLawsuit = new MfLawsuit();
        FormData formlawsuit0004 = formService.getFormData("lawsuit0004");

        mfLawsuit=mfLawsuitFeign.getCusInfo(mfLawsuit,assetId);
        getObjValue(formlawsuit0004, mfLawsuit,formData);
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String htmlStr = jsonFormUtil.getJsonStr(formlawsuit0004,"bootstarpTag","");
        dataMap.put("bean", mfLawsuit);
        dataMap.put("htmlStr", htmlStr);
        dataMap.put("formData", formData);
        dataMap.put("flag", "success");

		return dataMap;
	}

    /***
     * 费用登记
     * @param model
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/costReg")
	public String costReg(Model model,String caseId){
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcostreg = formService.getFormData("costreg");
        MfLitigationExpenseApply mfLitigationExpenseApply=new MfLitigationExpenseApply();
        MfLawsuit mfLawsuit=new MfLawsuit();
        mfLawsuit.setCaseId(caseId);
        mfLawsuit=mfLawsuitFeign.getById(mfLawsuit);
        mfLitigationExpenseApply.setAssetId(mfLawsuit.getCaseId());
        mfLitigationExpenseApply.setCusName(mfLawsuit.getCusName());
        mfLitigationExpenseApply.setCusNo(mfLawsuit.getCusNo());
		mfLitigationExpenseApply.setLitigationAmount(mfLawsuit.getCaseAmt());
		mfLitigationExpenseApply.setShowFlag("0");

        getObjValue(formcostreg, mfLitigationExpenseApply);
        model.addAttribute("formcostreg", formcostreg);
        model.addAttribute("query", "");
        return "/component/lawsuit/CostReg_Insert";
    }
    /***
     * 费用登记保存
     * @param ajaxData
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/costRegInsert")
	@ResponseBody
    public Map<String, Object> costRegInsert(Model model,String ajaxData){
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		MfLitigationExpenseApply mfLitigationExpenseApply=new MfLitigationExpenseApply();
		try{
			dataMap=getMapByJson(ajaxData);
			String caseId = (String)dataMap.get("assetId");
			FormData formcostreg = formService.getFormData("costreg");
			getFormValue(formcostreg, getMapByJson(ajaxData));
			if(this.validateFormData(formcostreg)){
				setObjValue(formcostreg, mfLitigationExpenseApply);
				switch (mfLitigationExpenseApply.getFeeType()){
					case "1":
						mfLitigationExpenseApply.setFilingFee(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "2":
						mfLitigationExpenseApply.setSaveCost(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "3":
						mfLitigationExpenseApply.setAnnouncementFee(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "4":
						mfLitigationExpenseApply.setAppraisalCost(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "5":
						mfLitigationExpenseApply.setPostFee(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "6":
						mfLitigationExpenseApply.setExecutionFee(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "7":
						mfLitigationExpenseApply.setSecurityPremium(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "8":
						mfLitigationExpenseApply.setBarFee(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "9":
						mfLitigationExpenseApply.setAssessmentFee(mfLitigationExpenseApply.getFeeAmt());
						break;
					case "10":
						mfLitigationExpenseApply.setOtherCharges(mfLitigationExpenseApply.getFeeAmt());
						break;
						default:
				}
				mfLitigationExpenseApplyFeign.mfLitigationExpenseApply(mfLitigationExpenseApply);
                MfLawsuit mfLawsuit = new MfLawsuit();
                mfLawsuit.setCaseId(caseId);
                mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
                if(mfLawsuit !=null && mfLawsuit.getCost()!=null){//更新法律诉讼表中费用金额
                	MfLawsuit lawsuit = new MfLawsuit();
                	lawsuit.setCaseId(caseId);
					lawsuit.setCost(MathExtend.add(mfLawsuit.getCost(),mfLitigationExpenseApply.getTotalExpenses()));
					mfLawsuitFeign.update(lawsuit);
				}
				dataMap.put("flag", "success");
				dataMap.put("assetId", mfLitigationExpenseApply.getAssetId());
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
        return dataMap;
    }

	/**
	 * 获取诉讼费用登记列表
	 */

	@RequestMapping(value = "/getMfLitigationExpenseList")
	@ResponseBody
	public Map<String, Object> getMfLitigationExpenseList(String assetId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLitigationExpenseApply mfLitigationExpenseApply=new MfLitigationExpenseApply();
		mfLitigationExpenseApply.setAssetId(assetId);
		List<MfLitigationExpenseApply> mfLitigationExpenseApplyList=mfLitigationExpenseApplyFeign.findByAssetId(mfLitigationExpenseApply);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablecostreglist", "tableTag", mfLitigationExpenseApplyList, null, true);
		String cost = mfLitigationExpenseApplyFeign.calculateFeeSum(mfLitigationExpenseApply);
        MfLawsuit mfLawsuit = new MfLawsuit();
        mfLawsuit.setCaseId(mfLitigationExpenseApply.getAssetId());
        mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
        if(mfLawsuit != null){
            dataMap.put("loanBalance",MathExtend.moneyStr(mfLawsuit.getLoanBalance()));
        }
		dataMap.put("cost", MathExtend.moneyStr(cost));
		dataMap.put("htmlStr", tableHtml);

		return dataMap;
	}
	/**
	 * 获取执行回收情况登记列表
	 */

	@RequestMapping(value = "/getExecutionRecoveryList")
	@ResponseBody
	public Map<String, Object> getExecutionRecoveryList(String caseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuitFollow mfLawsuitFollow=new MfLawsuitFollow();
		mfLawsuitFollow.setCaseId(caseId);
		List<MfLawsuitFollow> mfLawsuitFollowList=lawsuitFollowFegin.getFollowListByCaseId(mfLawsuitFollow);
		List<MfLawsuitFollow> executionRecoveryList = new ArrayList<MfLawsuitFollow>();
		if(mfLawsuitFollowList.size()>0 && mfLawsuitFollowList != null){
			for(MfLawsuitFollow mlfList:mfLawsuitFollowList){
				if("4".equals(mlfList.getFollowType())){
					executionRecoveryList.add(mlfList);
				}
			}
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tableExecutionRecoveryList", "tableTag", executionRecoveryList, null, true);
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
	
	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String litigationId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<String,Object>();
		Map<String,Object> formData = new HashMap<String,Object>();
		try {
            request.setAttribute("ifBizManger","3");
            String query = "";
			MfLitigationExpenseApply mfLitigationExpenseApply=new MfLitigationExpenseApply();
			mfLitigationExpenseApply.setLitigationId(litigationId);
			mfLitigationExpenseApply=mfLitigationExpenseApplyFeign.getById(mfLitigationExpenseApply);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcostregdetail = formService.getFormData("costregdetail");
			getObjValue(formcostregdetail, mfLitigationExpenseApply,formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcostregdetail,"propertySeeTag",query);

			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
			dataMap.put("formData", mfLitigationExpenseApply);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取详情失败");
		}
		return dataMap;
	}

//列表展示详情，单字段编辑
	@RequestMapping(value = "/followListShowDetailAjax")
	@ResponseBody
	public Map<String,Object> followListShowDetailAjax(String followId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap=new HashMap<String,Object>();
		Map<String,Object> formData = new HashMap<String,Object>();
		try {
			//request.setAttribute("ifBizManger","3");
			String formId ="";
			//String query = "";
			MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
			mfLawsuitFollow.setFollowId(followId);
			mfLawsuitFollow = lawsuitFollowFegin.getById(mfLawsuitFollow);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String followType = mfLawsuitFollow.getFollowType();
			//默认跟进类型是受理
			if("".equals(followType) || StringUtil.isEmpty(followType) ||"1".equals(followType)){
				followType = "1";
			}
			switch(followType){
			case "1":
				formId = "lawsuitfollowacceptdetail";
				break;
			case "2":
				//审理表单
				formId = "lawsuitfollowtrialdetail";
				break;
			case "3":
				//判决
				formId = "lawsuitfollowsentencedetail";
				break;
			case "4":
				//执行
				formId = "lawsuitfollowperformdetail";
				break;
			case "5":
				//撤诉
				formId = "lawsuitfollownolleprosequidetail";
				break;
			case "6":
				//终结
				formId = "lawsuitfollowenddetail";
				break;
			case "8":
				//和解
				formId = "lawsuitfollowsettlementdetail";
				break;
			case "9":
				//调解
				formId = "lawsuitfollowmediationdetail";
				break;
			case "10":
				//破产清算
				formId = "lawsuitfollowbankruptcydetail";
				break;
			case "11":
				//执行程序转破产清算
				formId = "lawsuitfollowperformbankruptcydetail";
				break;
			case "12":
				//结案
				formId = "lawsuitfollowcaseenddetail";
				break;
			default://其他
				formId = "lawsuitfollowotherdetail";
				break;
			
			}
			FormData formlawsuitfollowdetail = formService.getFormData(formId);
			getObjValue(formlawsuitfollowdetail, mfLawsuitFollow,formData);

			String htmlStrCorp = jsonFormUtil.getJsonStr(formlawsuitfollowdetail,"propertySeeTag","");

			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
			dataMap.put("formData", mfLawsuitFollow);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取详情失败");
		}
		return dataMap;
	}
}

package app.component.eval.controller;

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

import app.component.common.EntityUtil;
import app.component.eval.entity.EvalScenceConfig;
import app.component.eval.entity.MfEvalGradeCard;
import app.component.eval.feign.EvalScenceConfigFeign;
import app.component.eval.feign.MfEvalGradeCardFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfEvalGradeCardAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat May 27 17:08:34 CST 2017
 **/
@Controller
@RequestMapping("/mfEvalGradeCard")
public class MfEvalGradeCardController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfEvalGradeCardBo
	@Autowired
	private MfEvalGradeCardFeign mfEvalGradeCardFeign;
	@Autowired
	private EvalScenceConfigFeign evalScenceConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/eval/MfEvalGradeCard_List";
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		try {
			mfEvalGradeCard.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfEvalGradeCard.setCriteriaList(mfEvalGradeCard, ajaxData);// 我的筛选
			// mfEvalGradeCard.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfEvalGradeCard,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfEvalGradeCardFeign.findByPage(ipage, mfEvalGradeCard);
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
			FormData formgradecard0002 = formService.getFormData("gradecard0002");
			getFormValue(formgradecard0002, getMapByJson(ajaxData));
			if (this.validateFormData(formgradecard0002)) {
				MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
				setObjValue(formgradecard0002, mfEvalGradeCard);
				MfEvalGradeCard evalGradeCard = new MfEvalGradeCard();
				evalGradeCard.setGradeCardNameEn(mfEvalGradeCard.getGradeCardNameEn());
				evalGradeCard.setEvalScenceNo(mfEvalGradeCard.getEvalScenceNo());
				List<MfEvalGradeCard> evalGradeCardList = mfEvalGradeCardFeign.getEvalGradeCardList(mfEvalGradeCard);
				if(evalGradeCardList!=null&&evalGradeCardList.size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该评分卡别名"));
				}else{
					mfEvalGradeCard = mfEvalGradeCardFeign.insert(mfEvalGradeCard);
					dataMap.put("flag", "success");
					dataMap.put("mfEvalGradeCard", mfEvalGradeCard);
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		getFormValue(formgradecard0002, getMapByJson(ajaxData));
		MfEvalGradeCard mfEvalGradeCardJsp = new MfEvalGradeCard();
		setObjValue(formgradecard0002, mfEvalGradeCardJsp);
		MfEvalGradeCard mfEvalGradeCard = mfEvalGradeCardFeign.getById(mfEvalGradeCardJsp);
		if (mfEvalGradeCard != null) {
			try {
				mfEvalGradeCard = (MfEvalGradeCard) EntityUtil.reflectionSetVal(mfEvalGradeCard, mfEvalGradeCardJsp,
						getMapByJson(ajaxData));
				mfEvalGradeCardFeign.update(mfEvalGradeCard);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
			FormData formgradecard0003 = formService.getFormData("gradecard0003");
			getFormValue(formgradecard0003, getMapByJson(ajaxData));
			if (this.validateFormData(formgradecard0003)) {
				MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
				setObjValue(formgradecard0003, mfEvalGradeCard);
				mfEvalGradeCardFeign.update(mfEvalGradeCard);
				dataMap.put("mfEvalGradeCard", mfEvalGradeCard);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：验证评分卡权重之和不能大于100
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-6-2 下午3:06:06
	 */
	@RequestMapping(value = "/checkScorePercentAjax")
	@ResponseBody
	public Map<String, Object> checkScorePercentAjax(String evalScenceNo, String gradeCardId, Double scorePercent)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		try {
			mfEvalGradeCard.setEvalScenceNo(evalScenceNo);
			mfEvalGradeCard.setGradeCardId(gradeCardId);
			mfEvalGradeCard.setScorePercent(scorePercent);
			String flag = mfEvalGradeCardFeign.getCheckScorePercentFlag(mfEvalGradeCard);
			dataMap.put("flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String gradeCardId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		mfEvalGradeCard.setGradeCardId(gradeCardId);
		mfEvalGradeCard = mfEvalGradeCardFeign.getById(mfEvalGradeCard);
		getObjValue(formgradecard0002, mfEvalGradeCard, formData);
		if (mfEvalGradeCard != null) {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String gradeCardId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		mfEvalGradeCard.setGradeCardId(gradeCardId);
		try {
			mfEvalGradeCardFeign.delete(mfEvalGradeCard);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String evalScenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		mfEvalGradeCard.setEvalScenceNo(evalScenceNo);
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
		mfEvalGradeCard.setEvalScenceName(evalScenceConfig.getEvalScenceName());
		getObjValue(formgradecard0002, mfEvalGradeCard);
		model.addAttribute("formgradecard0002", formgradecard0002);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalGradeCard_Insert";
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
		FormData formgradecard0002 = formService.getFormData("gradecard0002");
		getFormValue(formgradecard0002);
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		setObjValue(formgradecard0002, mfEvalGradeCard);
		mfEvalGradeCardFeign.insert(mfEvalGradeCard);
		getObjValue(formgradecard0002, mfEvalGradeCard);
		this.addActionMessage(model, "保存成功");
		List<MfEvalGradeCard> mfEvalGradeCardList = (List<MfEvalGradeCard>) mfEvalGradeCardFeign
				.findByPage(this.getIpage(), mfEvalGradeCard).getResult();
		model.addAttribute("mfEvalGradeCardList", mfEvalGradeCardList);
		model.addAttribute("formgradecard0002", formgradecard0002);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalGradeCard_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String gradeCardId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formgradecard0003 = formService.getFormData("gradecard0003");
		getFormValue(formgradecard0003);
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		mfEvalGradeCard.setGradeCardId(gradeCardId);
		mfEvalGradeCard = mfEvalGradeCardFeign.getById(mfEvalGradeCard);
		getObjValue(formgradecard0003, mfEvalGradeCard);
		model.addAttribute("formgradecard0003", formgradecard0003);
		model.addAttribute("query", "");
		return "/component/eval/MfEvalGradeCard_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String gradeCardId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		mfEvalGradeCard.setGradeCardId(gradeCardId);
		mfEvalGradeCardFeign.delete(mfEvalGradeCard);
		return getListPage(model);
	}

}

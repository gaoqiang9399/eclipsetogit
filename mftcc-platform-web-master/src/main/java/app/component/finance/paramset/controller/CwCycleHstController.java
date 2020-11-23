package app.component.finance.paramset.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.common.EntityUtil;
import app.component.finance.paramset.entity.CwCycleHst;
import app.component.finance.paramset.feign.CwCycleHstFeign;
import app.component.finance.util.CwPublicUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * Title: CwCycleHstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jan 07 15:57:56 CST 2017
 **/
@Controller
@RequestMapping("/cwCycleHst")
public class CwCycleHstController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwCycleHstFeign cwCycleHstFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwCycleHstController() {
		query = "";
	}

	/**
	 * 批量插入二十年的自然年度期间
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertZiRanCwcycle")
	public void insertZiRanCwcycle() throws Exception {
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwCycleHstFeign.insertZiRanCwcycle(finBooks);
		} catch (Exception e) {
//			logger.error("insertZiRanCwcycle方法出错，执行action层失败，抛出异常，", e);
			e.printStackTrace();
		}

	}

	/***
	 * 新增(设置会计期间)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCwcycle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertCwcycle(String ajaxData, String years) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
		String result = "0000";
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			result = cwCycleHstFeign.insertCwcycle(years, formMap,finBooks);
		} catch (Exception e) {
//			logger.error("新增会计期间 出错：" + e.getMessage(), e);
			throw e;
		}
		dataMap.put("result", result);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取自然年会计期间
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 张丽
	 * @date 2017-1-16 下午2:25:57
	 */
	@RequestMapping(value = "/getPeriod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPeriod(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String ryear = formMap.get("years");
			list = cwCycleHstFeign.getPeriod(ryear);
			dataMap.put("list", list);
		} catch (Exception e) {
//			logger.error("获取自然年会计期间出错：" + e.getMessage(), e);
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "获取失败");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("数据"));
			throw e;
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述：获取非自然年会计期间
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 张丽
	 * @date 2017-1-16 下午2:25:40
	 */
	@RequestMapping(value = "/getPeriodByYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPeriodByYear(String ajaxData) throws Exception {
		List<List<String>> list = new ArrayList<List<String>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String ryear = formMap.get("years");
			list = cwCycleHstFeign.getPeriodByYear(ryear,finBooks);

			dataMap.put("list", list);
		} catch (Exception e) {
//			logger.error("获取非自然年会计期间出错：" + e.getMessage(), e);
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "获取失败");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("数据"));
			throw e;
		}

		return dataMap;

	}

	/**
	 * 
	 * 方法描述： 修改会计期间
	 * 
	 * @param val
	 * @param val1
	 * @param paramMap
	 * @return List<List<String>>
	 * @author 张丽
	 * @date 2017-1-10 上午11:01:17
	 */
	@RequestMapping(value = "/getPeriodByNum", method = RequestMethod.POST)
	@ResponseBody
	public List<List<String>> getPeriodByNum(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String rval1 = formMap.get("val1");
			String rval = formMap.get("val");
			List<String> lt = null; //
			int currVal1 = Integer.parseInt(rval1);// 修改的期间数
			String temp = "";// 临时日期(结束日期)
			temp = rval;
			String tempDay = "";// 临时天数
			int tempDay1 = Integer.parseInt(tempDay);// 修改的期间数
			for (int i = 1; i <= 12; i++) {
				lt = new ArrayList<String>();
				String start = "";
				String end = "";
				if (i >= currVal1) {
					if ("".equals(temp) || null == temp) {
						start = formMap.get("s_" + i);
						end = formMap.get("e_" + i);
						end = end.substring(8) + DateUtil.getDaysOfMonth(end.replaceAll("-", ""));
					} else {
						start = DateUtil.addDay(temp.replaceAll("-", ""), 1);// 起始日期
						end = DateUtil.addDay(temp.replaceAll("-", ""), tempDay1);
					}
					temp = end;
				} else {
					start = formMap.get("s_" + i);
					end = formMap.get("e_" + i);
				}
				lt.add(start);
				if (i == 12) {
					// end = (end.replaceAll("-", ""));
				}
				lt.add(end);
				list.add(lt);
				lt = null;
			}
		} catch (Exception e) {
//			logger.error("获取非自然年会计期间出错：" + e.getMessage(), e);
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "获取失败");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("数据"));
		}
		return list;
	}

	/**
	 * 进入会计周期设置查询页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		Map<String, String> mp = cwCycleHstFeign.getShowParam(finBooks);
		model.addAttribute("cw_show_year", mp.get("year"));
		model.addAttribute("cw_show_month", mp.get("month"));
		return "/component/finance/paramset/CwCycleHst_List";
	}

	/*
	 * public String[] isExistInitDate() { return
	 * cwCycleHstFeign.isExistInitDate(); }
	 */
	/***************************** 以下方法暂不用 ***************************************/

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwcyclehst0002 = formService.getFormData("cwcyclehst0002");
		getFormValue(formcwcyclehst0002, getMapByJson(ajaxData));
		CwCycleHst cwCycleHstJsp = new CwCycleHst();
		setObjValue(formcwcyclehst0002, cwCycleHstJsp);
		CwCycleHst cwCycleHst = cwCycleHstFeign.getById(cwCycleHstJsp);
		if (cwCycleHst != null) {
			try {
				cwCycleHst = (CwCycleHst) EntityUtil.reflectionSetVal(cwCycleHst, cwCycleHstJsp,
						getMapByJson(ajaxData));
				cwCycleHstFeign.update(cwCycleHst);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcwcyclehst0002 = formService.getFormData("cwcyclehst0002");
			getFormValue(formcwcyclehst0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwcyclehst0002)) {
				CwCycleHst cwCycleHst = new CwCycleHst();
				setObjValue(formcwcyclehst0002, cwCycleHst);
				cwCycleHstFeign.update(cwCycleHst);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String uid, String nums) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwcyclehst0002 = formService.getFormData("cwcyclehst0002");
		CwCycleHst cwCycleHst = new CwCycleHst();
		cwCycleHst.setUid(uid);
		cwCycleHst.setNums(nums);
		cwCycleHst = cwCycleHstFeign.getById(cwCycleHst);
		getObjValue(formcwcyclehst0002, cwCycleHst, formData);
		if (cwCycleHst != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String uid, String nums) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwCycleHst cwCycleHst = new CwCycleHst();
		cwCycleHst.setUid(uid);
		cwCycleHst.setNums(nums);
		try {
			cwCycleHstFeign.delete(cwCycleHst);
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

}

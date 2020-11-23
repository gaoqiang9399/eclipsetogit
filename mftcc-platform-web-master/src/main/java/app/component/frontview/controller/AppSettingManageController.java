package app.component.frontview.controller;

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

import app.component.frontview.entity.VwBannerManage;
import app.component.frontview.feign.VwBannerManageFeign;
import app.util.toolkit.Ipage;

/**
 * Title: AppSettingManageAction.java Description:移动端管理
 * 
 * @author:mh@dhcc.com.cn
 * @date:20170615 11:17
 **/
@Controller
@RequestMapping("/appSettingManage")
public class AppSettingManageController extends BaseFormBean {
	/**
		 * 
		 */
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwBannerManageBo
	@Autowired
	private VwBannerManageFeign vwBannerManageFeign;

	// 全局变量
	/**
	 * banner列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBannerListPage")
	public String getBannerListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/AppBannerManage_List";
	}

	/***
	 * banner列表数据查询 默认查询手机端banner图
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findBannerByPageAjax")
	@ResponseBody
	public Map<String, Object> findBannerByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwBannerManage vwBannerManage = new VwBannerManage();
		// 处理成手机端
		try {
			vwBannerManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwBannerManage.setCriteriaList(vwBannerManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwBannerManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("vwBannerManage",vwBannerManage));
			ipage = vwBannerManageFeign.findAppByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			// logger.error("findBannerByPageAjax方法出错，执行action层失败，抛出异常，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * appbanner新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBanner")
	public String inputBanner(Model model) throws Exception {
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);
			FormData formvwbanner0002 = formService.getFormData("vwbanner0002");
			VwBannerManage vwBannerManage = new VwBannerManage();
			vwBannerManage.setType("1");// 默认手机类型
			changeFormProperty(formvwbanner0002, "type", "readonly", "1");// 手机类型不能选择
			getObjValue(formvwbanner0002, vwBannerManage);
			model.addAttribute("formvwbanner0002", formvwbanner0002);
			model.addAttribute("query", "");
		} catch (Exception e) {
			// logger.error("异常：", e);
		}
		return "/component/frontview/AppBannerManage_Insert";
	}

	/**
	 * 查询banner
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBannerById")
	public String getBannerById(Model model, String id) throws Exception {
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);
			FormData formvwbanner0001 = formService.getFormData("vwbanner0001");
			getFormValue(formvwbanner0001);
			VwBannerManage vwBannerManage = new VwBannerManage();
			vwBannerManage.setId(id);
			vwBannerManage = vwBannerManageFeign.getById(vwBannerManage);
			changeFormProperty(formvwbanner0001, "type", "readonly", "1");// 手机类型不能选择
			getObjValue(formvwbanner0001, vwBannerManage);
			model.addAttribute("formvwbanner0001", formvwbanner0001);
			model.addAttribute("query", "");
		} catch (Exception e) {
			// logger.error("getBannerById异常，", e);
		}
		return "/component/frontview/AppBannerManage_Detail";
	}

}

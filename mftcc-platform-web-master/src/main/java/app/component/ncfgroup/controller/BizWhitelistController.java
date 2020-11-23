package app.component.ncfgroup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.ncfgroup.entity.BizWhitelist;
import app.component.ncfgroup.feign.BizWhitelistFeign;
import app.util.toolkit.Ipage;

/**
 * Title: BizWhitelistAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 14 12:28:45 CST 2017
 **/
@Controller
@RequestMapping("/bizWhitelist")
public class BizWhitelistController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private BizWhitelistFeign bizWhitelistFeign;
	

	/**
	 * 列表打开页面请求
	 * @param model 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "BizWhitelist_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			BizWhitelist bizWhitelist = new BizWhitelist();
			bizWhitelist.setCustomQuery(ajaxData);// 自定义查询参数赋值
			bizWhitelist.setCriteriaList(bizWhitelist, ajaxData);// 我的筛选
			// bizWhitelist.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(bizWhitelist,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = bizWhitelistFeign.findByPage(ipage, bizWhitelist);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 根据手机号和产品编号获取白名单客户
	 * @param bizWhitelist 
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getByCusTelAndKindNoAjax")
	public Map<String, Object> getByCusTelAndKindNoAjax(@RequestBody BizWhitelist bizWhitelist) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = bizWhitelistFeign.getByCusTelAndKindNo(bizWhitelist);
		} catch (Exception e) {
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", " 根据手机号和产品编号获取白名单客户出错");
		}
		return dataMap;
	}

}

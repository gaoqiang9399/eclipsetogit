package app.component.query.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.BizPubParm;
import app.component.sys.entity.MfSysCompanyMst;
import app.component.sys.feign.MfSysCompanyMstFeign;
import app.util.toolkit.Ipage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.nmd.entity.ParmDic;
import app.component.query.entity.MfQueryItem;
import app.component.query.feign.MfQueryItemFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.StringUtil;

@Controller
@RequestMapping("/mfQueryEntrance")
public class MfQueryEntranceController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfQueryItemBo
	@Autowired
	private MfQueryItemFeign mfQueryItemFeign;
	@Autowired
	private MfSysCompanyMstFeign mfSysCompanyMstFeign;
	// 全局变量
	// 异步参数

	@RequestMapping(value = "/queryEntrance")
	public String queryEntrance(Model model, String menuNo,String ajaxData) throws Exception {
		Gson gson = new Gson();
		ActionContext.initialize(request, response);
		CodeUtils cu = new CodeUtils();
		List<ParmDic> kindNoList = (List<ParmDic>) cu.getCacheByKeyName("KIND_NO");
		if (kindNoList != null && kindNoList.size() > 0) {
			request.setAttribute("firstKindNo", kindNoList.get(0).getOptCode());
		}

		if(StringUtil.isEmpty(menuNo)){//默认为综合页面
			menuNo="80";
		}
		// //获取基础的查询项
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setCustomQuery(ajaxData);
		mfQueryItem.setFirMenu(menuNo);
		mfQueryItem.setFuncType("0");
		mfQueryItem.setIsBase("1");
		Map<String,Object> mfQueryItemMap = mfQueryItemFeign.getQueryItemListAllByMenuNo(mfQueryItem);
		List<Object> attentionList = (List<Object>) mfQueryItemMap.get("attentionList");


		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("attentionList",gson.toJson(attentionList));

		dataMap.put("queryMap",gson.toJson(mfQueryItemMap.get("queryMap")));
		String globalSearchOpenFlag = cu.getSingleValByKey("GLOBAL_SEARCH_OPEN_FLAG");
		if(StringUtil.isNotEmpty(globalSearchOpenFlag)){
            dataMap.put("globalSearchOpenFlag",BizPubParm.YES_NO_Y);
        }else{
            dataMap.put("globalSearchOpenFlag",BizPubParm.YES_NO_N);
        }
		MfSysCompanyMst mfSysCompanyMst = mfSysCompanyMstFeign.getCompanyInfo();
		model.addAttribute("mfSysCompanyMst", mfSysCompanyMst);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/query/MfQueryEntrance";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/query/MfQueryCustomer_List";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSearchGlobalPage")
	public String getSearchGlobalPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils cu = new CodeUtils();
		MfSysCompanyMst mfSysCompanyMst = mfSysCompanyMstFeign.getCompanyInfo();
		List<ParmDic> kindNoList = (List<ParmDic>) cu.getCacheByKeyName("KIND_NO");
		if (kindNoList != null && kindNoList.size() > 0) {
			model.addAttribute("firstKindNo", kindNoList.get(0).getOptCode());
		}
		model.addAttribute("mfSysCompanyMst", mfSysCompanyMst);
		return "/component/query/MfSearchGlobalPage";
	}

}

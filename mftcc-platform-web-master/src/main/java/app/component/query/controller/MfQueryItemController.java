package app.component.query.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.query.entity.MfQueryItem;
import app.component.query.feign.MfQueryItemFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfQueryItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 10 18:03:08 CST 2017
 **/
@Controller
@RequestMapping("/mfQueryItem")
public class MfQueryItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfQueryItemBo
	@Autowired
	private MfQueryItemFeign mfQueryItemFeign;

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String itemId, String funcType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setItemId(itemId);
			mfQueryItem.setFuncType(funcType);
			List<MfQueryItem> mfQueryItemList = mfQueryItemFeign.insert(mfQueryItem);
			dataMap.put("mfQueryItemList", mfQueryItemList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getQueryList")
	@ResponseBody
	public Map<String, Object> getQueryList(String menuNo,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setCustomQuery(ajaxData);
			mfQueryItem.setFirMenu(menuNo);
			mfQueryItem.setFuncType("0");
			mfQueryItem.setIsBase("1");
			Ipage ipage = this.getIpage();
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfQueryItem", mfQueryItem));
			Map<String,Map<String,List<MfQueryItem>>> mfQueryItemMap = mfQueryItemFeign.getQueryItemListByMenuNo(ipage);
			dataMap.put("mfQueryItemMap", mfQueryItemMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取查询项失败");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getQueryListAll")
	@ResponseBody
	public Map<String, Object> getQueryListAll(String menuNo,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setCustomQuery(ajaxData);
			mfQueryItem.setFirMenu(menuNo);
			mfQueryItem.setFuncType("0");
			mfQueryItem.setIsBase("1");
			Map<String,Object> mfQueryItemMap = mfQueryItemFeign.getQueryItemListAllByMenuNo(mfQueryItem);
			dataMap.put("mfQueryItemMap", mfQueryItemMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取查询项失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新报表选中状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateReportAjax")
	@ResponseBody
	public Map<String, Object> updateReportAjax(String ajaxData, String funcType, String attentionFlag) throws Exception {
		ActionContext.initialize(request, response);
		MfQueryItem mfQueryItem = new MfQueryItem();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfQueryItem.setItemId(ajaxData);
			mfQueryItem.setFuncType(funcType);
			mfQueryItem.setAttentionFlag(attentionFlag);
			mfQueryItemFeign.updateReport(mfQueryItem);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String id, String attentionFlag) throws Exception {
		ActionContext.initialize(request, response);
		MfQueryItem mfQueryItem = new MfQueryItem();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfQueryItem.setId(id);
			mfQueryItem.setAttentionFlag(attentionFlag);
			mfQueryItemFeign.update(mfQueryItem);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：获取关注的查询基础项
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-6-26 下午2:39:24
	 */
	@RequestMapping(value = "/getBaseItemList")
	public String getBaseItemList(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 获取基础的查询项
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setAttentionFlag("0");
		mfQueryItem.setIsBase("1");
		mfQueryItem.setFuncType("0");
		List<Map<String, Object>> resList = mfQueryItemFeign.getBaseItemList(mfQueryItem);
		model.addAttribute("resList", resList);
		model.addAttribute("query", "");
		return "/component/query/MfQueryItem_List";
	}

	/**
	 * 
	 * 方法描述：获取关注的办公基础项
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-6-26 下午2:39:24
	 */
	@RequestMapping(value = "/getBaseOaItemList")
	public String getBaseOaItemList(Model model, String funcType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取基础的查询项
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setAttentionFlag("0");
		mfQueryItem.setIsBase("1");
		mfQueryItem.setFuncType(funcType);
		dataMap = mfQueryItemFeign.getBaseOaItemList(mfQueryItem);
		JSONObject object = JSONObject.fromObject(dataMap);
		String ajaxData = object.toString();
		model.addAttribute("ajaxData", ajaxData);
		return "/component/query/MfQueryItem_OaList";
	}

	/**
	 * 
	 * 方法描述：移除关注（oa使用）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-6-27 下午3:35:37
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String itemId, String funcType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setItemId(itemId);
			mfQueryItem.setFuncType(funcType);
			mfQueryItem.setOpNo(User.getRegNo(request));
			mfQueryItemFeign.delete(mfQueryItem);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage(""));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formqueryitem0002 = formService.getFormData("queryitem0002");
		getFormValue(formqueryitem0002);
		boolean validateFlag = this.validateFormData(formqueryitem0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formqueryitem0002 = formService.getFormData("queryitem0002");
		getFormValue(formqueryitem0002);
		boolean validateFlag = this.validateFormData(formqueryitem0002);
	}

}

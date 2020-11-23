package app.component.interfaces.mobileinterface.controller;

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

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.interfaces.mobileinterface.entity.MfAppCusMng;
import app.component.interfaces.mobileinterface.feign.MfAppCusMngFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusMngAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 05 10:01:02 CST 2017
 **/
@Controller
@RequestMapping("/mfAppCusMng")
public class MfAppCusMngController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusMngBo
	@Autowired
	private MfAppCusMngFeign mfAppCusMngFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray channelTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CHANNEL_TYPE");
		model.addAttribute("channelTypeJsonArray", channelTypeJsonArray);
		return "/component/interfaces/mobileinterface/MfCusMng_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppCusMng mfCusMng = new MfAppCusMng();
		try {
			mfCusMng.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusMng.setCriteriaList(mfCusMng, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusMng", mfCusMng));
			ipage = mfAppCusMngFeign.findByPage(ipage);
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String opNo, String channelType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfAppCusMng mfCusMng = new MfAppCusMng();
			mfCusMng.setCusMngNo(opNo);
			mfCusMng.setChannelType(channelType);
			dataMap = mfAppCusMngFeign.insert(mfCusMng);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String cusMngNo, String channelType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfAppCusMng mfCusMng = new MfAppCusMng();
			mfCusMng.setCusMngNo(cusMngNo);
			mfCusMng.setChannelType(channelType);
			mfAppCusMngFeign.delete(mfCusMng);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败");
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
	@RequestMapping(value = "/updateStsNewAjax")
	@ResponseBody
	public Map<String, Object> updateStsNewAjax(String ajaxData, String cusMngNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppCusMng mfCusMng = new MfAppCusMng();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			cusMngNo = (String) jobj.get("cusMngNo");
			if (StringUtil.isEmpty(cusMngNo)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}
			mfCusMng.setCusMngNo(cusMngNo);
			String opSts = (String) jobj.get("opSts");
			if (StringUtil.isNotEmpty(opSts)) {
				mfCusMng.setOpSts(opSts);
				mfAppCusMngFeign.update(mfCusMng);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

}

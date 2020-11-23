package app.component.interfaces.mobileinterface.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: ParmDicAction.java Description:
 * 
 * @author:mahao@dhcc.com.cn
 * @Thu Apr 10 09:11:38 GMT 2014
 **/
@Controller
@RequestMapping("/vmParmDic")
public class VmParmDicController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/***
	 * 列表数据查询的异步方法
	 */
	@RequestMapping(value = "/findByNameAjax")
	@ResponseBody
	public Map<String, Object> findByNameAjax(String dicName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isBlank(dicName)) {
				throw new NullPointerException("请传入查询条件");
			}
			// 前台自定义筛选组件的条件项，从数据字典缓存获取。
			JSONArray jsonArray = new CodeUtils().getJSONArrayByKeyName(dicName.toUpperCase());
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", "查询成功");
			dataMap.put("data", jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", e.getMessage());
			//logger\.error("查询字典异常", e);
		}
		return dataMap;
	}

	/***
	 * 列表数据查询的异步方法
	 */
	@RequestMapping(value = "/findParmDicListAjax")
	@ResponseBody
	public Map<String, Object> findParmDicListAjax(String dicNames) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isBlank(dicNames)) {
				throw new NullPointerException("请传入查询条件");
			}
			List<ParmDic> list = new ArrayList<ParmDic>();
			String[] dicNameList = dicNames.split(",");
			for (int i = 0; i < dicNameList.length; i++) {
				String dic = dicNameList[i];
				if (!StringUtil.isBlank(dic)) {
					List<ParmDic> cusTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName(dic.toUpperCase());
					list.addAll(cusTypeList);
				}
			}
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", "查询成功");
			dataMap.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", e.getMessage());
			//logger\.error("查询字典异常", e);
		}
		return dataMap;
	}

}

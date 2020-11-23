package  app.component.interfaces.mobileinterface.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.component.interfaces.mobileinterface.feign.AppMfSysKindFeign;
import app.component.prdct.entity.MfSysKind;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfSysKindAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 14:31:11 CST 2017
 **/
@Controller
@RequestMapping("/appMfSysKind")
public class AppMfSysKindController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfSysKindBo
	@Autowired
	private AppMfSysKindFeign appMfSysKindFeign;
	/***
	 * 产品基本信息详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appGetDetailByKindNoAjax")
	@ResponseBody
	public Map<String, Object> appGetDetailByKindNoAjax(String kindNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			if(!StringUtil.isEmpty(kindNo)){
				Map<String, Object> obj = appMfSysKindFeign.getById(kindNo);
				dataMap.put("errorCode", "00000");
				dataMap.put("data", obj);
				dataMap.put("errorMsg", "查询成功");
			}else {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询产品基本信息详情出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}
	
	/***
	 * 产品信息列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appFindByPageAjax")
	@ResponseBody
	public Map<String, Object> appFindByPageAjax(Integer pageSize, Integer pageNo, String useFlag) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			Ipage ipage = this.getIpage();
			//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			MfSysKind mfSysKind = new MfSysKind();
			mfSysKind.setUseFlag(useFlag);
			List<Map<String, Object>> list = appMfSysKindFeign.findByPage(ipage,mfSysKind);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", list);
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询产品信息列表出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}
	/***
	 * 产品基本信息详情pc交易前端使用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcGetDetailByKindNoAjax")
	@ResponseBody
	public Map<String, Object> pcGetDetailByKindNoAjax(String kindNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			if(!StringUtil.isEmpty(kindNo)){
				Map<String, Object> obj = appMfSysKindFeign.getFrontById(kindNo);
				dataMap.put("errorCode", "00000");
				dataMap.put("data", obj);
				dataMap.put("errorMsg", "查询成功");
			}else {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询产品基本信息详情出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}
	
	/***
	 * 产品信息列表pc
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcFindByPageAjax")
	@ResponseBody
	public Map<String, Object> pcFindByPageAjax(Integer pageSize, Integer pageNo, String useFlag) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			Ipage ipage = this.getIpage();
			//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			MfSysKind mfSysKind = new MfSysKind();
			mfSysKind.setUseFlag(useFlag);
			List<Map<String, Object>> list = appMfSysKindFeign.findFrontByPage(ipage,mfSysKind);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", list);
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询产品信息列表出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 获取产品准入拦截描述信息
	 * @return
	 */
	/*public String getKindInterceptorDesc(Model model, String ajaxData){
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			dataMap = appMfSysKindFeign.getKindInterceptorDesc(kindNo,nodeNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("移动端获取产品准入规则描述信息异常",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}
	*/

}

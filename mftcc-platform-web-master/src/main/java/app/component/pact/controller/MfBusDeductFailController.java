package app.component.pact.controller;

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

import app.base.ServiceException;
import app.component.pact.entity.MfBusDeductFail;
import app.component.pact.feign.MfBusDeductFailFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusDeductFailAction.java Description:批量划扣失败信息展示处理
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Sep 09 14:18:17 CST 2017
 **/
@Controller
@RequestMapping("/mfBusDeductFail")
public class MfBusDeductFailController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusDeductFailBo
	@Autowired
	private MfBusDeductFailFeign mfBusDeductFailFeign;

	// 全局变量
	// 异步参数
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/MfBusDeductFail_List";
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
		MfBusDeductFail mfBusDeductFail = new MfBusDeductFail();
		try {
			mfBusDeductFail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusDeductFail.setCriteriaList(mfBusDeductFail, ajaxData);// 我的筛选
			// mfBusDeductFail.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfBusDeductFail,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusDeductFail", mfBusDeductFail));
			// 自定义查询Bo方法
			ipage = mfBusDeductFailFeign.findWithFincInfoByPage(ipage);
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
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFakeAjax")
	@ResponseBody
	public Map<String, Object> deleteFakeAjax(String deductId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusDeductFail mfBusDeductFail = new MfBusDeductFail();
		mfBusDeductFail.setDeductId(deductId);
		try {
			mfBusDeductFailFeign.deleteFake(mfBusDeductFail);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 划扣操作
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-11 下午2:21:05
	 */
	@RequestMapping(value = "/deductAjax")
	@ResponseBody
	public Map<String, Object> deductAjax(String deductId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusDeductFail mfBusDeductFail = new MfBusDeductFail();
		mfBusDeductFail.setDeductId(deductId);
		try {
			dataMap = mfBusDeductFailFeign.doDeductByOne(mfBusDeductFail);
			// dataMap.put("flag", "success");
			// dataMap.put("msg", "成功");
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			if (e instanceof ServiceException) {
				dataMap.put("msg", e.getMessage());
			} else {
				throw e;
			}
		}
		return dataMap;
	}
}

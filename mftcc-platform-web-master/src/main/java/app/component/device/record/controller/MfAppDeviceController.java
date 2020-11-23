package app.component.device.record.controller;

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
import app.component.device.record.entity.MfAppDevice;
import app.component.device.record.feign.MfAppDeviceFeign;
import app.component.deviceinterface.DeviceInterfaceFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfAppDeviceAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jan 16 14:12:25 CST 2018
 **/
@Controller
@RequestMapping("/mfAppDevice")
public class MfAppDeviceController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAppDeviceBo
	@Autowired
	private MfAppDeviceFeign mfAppDeviceFeign;
	// 全局变量
	// 异步参数
	// 表单变量
	@Autowired
	private DeviceInterfaceFeign deviceInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/device/record/MfAppDevice_List";
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
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppDevice mfAppDevice = new MfAppDevice();
		try {
			mfAppDevice.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAppDevice.setCriteriaList(mfAppDevice, ajaxData);// 我的筛选
			// mfAppDevice.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAppDevice,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfAppDevice",mfAppDevice));
			// 自定义查询Bo方法
			ipage = mfAppDeviceFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
			FormData formappdevice0002 = formService.getFormData("appdevice0002");
			getFormValue(formappdevice0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappdevice0002)) {
				MfAppDevice mfAppDevice = new MfAppDevice();
				setObjValue(formappdevice0002, mfAppDevice);
				mfAppDeviceFeign.insert(mfAppDevice);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
		FormData formappdevice0002 = formService.getFormData("appdevice0002");
		getFormValue(formappdevice0002, getMapByJson(ajaxData));
		MfAppDevice mfAppDeviceJsp = new MfAppDevice();
		setObjValue(formappdevice0002, mfAppDeviceJsp);
		MfAppDevice mfAppDevice = mfAppDeviceFeign.getById(mfAppDeviceJsp);
		if (mfAppDevice != null) {
			try {
				mfAppDevice = (MfAppDevice) EntityUtil.reflectionSetVal(mfAppDevice, mfAppDeviceJsp,
						getMapByJson(ajaxData));
				mfAppDeviceFeign.update(mfAppDevice);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
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
			FormData formappdevice0002 = formService.getFormData("appdevice0002");
			getFormValue(formappdevice0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappdevice0002)) {
				MfAppDevice mfAppDevice = new MfAppDevice();
				setObjValue(formappdevice0002, mfAppDevice);
				mfAppDeviceFeign.update(mfAppDevice);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappdevice0002 = formService.getFormData("appdevice0002");
		MfAppDevice mfAppDevice = new MfAppDevice();
		mfAppDevice.setId(id);
		mfAppDevice = mfAppDeviceFeign.getById(mfAppDevice);
		getObjValue(formappdevice0002, mfAppDevice, formData);
		if (mfAppDevice != null) {
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppDevice mfAppDevice = new MfAppDevice();
		mfAppDevice.setId(id);
		try {
			mfAppDeviceFeign.delete(mfAppDevice);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formappdevice0002 = formService.getFormData("appdevice0002");
		model.addAttribute("formappdevice0002", formappdevice0002);
		model.addAttribute("query", "");
		return "/component/device/record/MfAppDevice_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formappdevice0001 = formService.getFormData("appdevice0001");
		getFormValue(formappdevice0001);
		MfAppDevice mfAppDevice = new MfAppDevice();
		mfAppDevice.setId(id);
		mfAppDevice = mfAppDeviceFeign.getById(mfAppDevice);
		getObjValue(formappdevice0001, mfAppDevice);
		model.addAttribute("formappdevice0001", formappdevice0001);
		model.addAttribute("query", "");
		return "/component/device/record/MfAppDevice_Detail";
	}

	@RequestMapping(value = "/getByUuidAjax")
	@ResponseBody
	public Map<String, Object> getByUuidAjax(String ajaxData, String uuid) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		boolean b = deviceInterfaceFeign.isHasUuid(uuid);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", b);
		return dataMap;
	}

}

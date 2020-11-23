package app.component.hzey.coupon.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.hzey.coupon.entity.MfDiscountManage;
import app.component.hzey.coupon.feign.MfDiscountManageFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfDiscountManageAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jul 23 10:28:46 CST 2017
 **/
@Controller
@RequestMapping("/mfDiscountManage")
public class MfDiscountManageController extends BaseFormBean {
	// 注入客户接口
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfDiscountManageBo
	@Autowired
	private MfDiscountManageFeign mfDiscountManageFeign;
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
		return "/component/hzey/MfDiscountManage_List";
	}

	/***
	 * 客户列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllCusByPageAjax")
	@ResponseBody
	public Map<String, Object> getAllCusByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// mfDiscountManage.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfDiscountManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
			// 自定义查询Bo方法
			ipage = mfDiscountManageFeign.getDiscountManageListByCus(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 跳转到查询客户优惠券
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByCusNo")
	public String getByCusNo(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
		model.addAttribute("formhzeydiscount0002", formhzeydiscount0002);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		return "/component/hzey/MfDiscountManage_Detail";
	}

	/**
	 * 获取个人客户优惠券列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfDiscountManageListByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getMfDiscountManageListByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		try {
			mfDiscountManage.setCusNo(cusNo);
			List<MfDiscountManage> mfDiscountManageList = mfDiscountManageFeign.getMfDiscountManageListByCusNo(mfDiscountManage);
			for (int i = 0; i < mfDiscountManageList.size(); i++) {
				mfDiscountManage = mfDiscountManageList.get(i);
				if ("0".equals(mfDiscountManage.getUseFlag())) {
					mfDiscountManage.setUseFlagShow("未使用");
				} else if ("1".equals(mfDiscountManage.getUseFlag())) {
					mfDiscountManage.setUseFlagShow("已使用");
				}else {
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtmlDiscountManage = jtu.getJsonStr("tablehzeydiscount0001", "tableTag", mfDiscountManageList,
					null, true);
			dataMap.put("tableHtmlDiscountManage", tableHtmlDiscountManage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 移动端获取个人客户优惠券列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfDiscountListByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getMfDiscountListByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		try {
			mfDiscountManage.setCusNo(cusNo);
			List<MfDiscountManage> mfDiscountManageList = mfDiscountManageFeign.getMfDiscountManageListByCusNo(mfDiscountManage);
			for (int i = 0; i < mfDiscountManageList.size(); i++) {
				mfDiscountManage = mfDiscountManageList.get(i);
				if ("0".equals(mfDiscountManage.getUseFlag())) {
					mfDiscountManage.setUseFlagShow("未使用");
				} else if ("1".equals(mfDiscountManage.getUseFlag())) {
					mfDiscountManage.setUseFlagShow("已使用");
				}else {
				}
			}
			dataMap.put("data", mfDiscountManageList);
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "999999");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
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
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		try {
			mfDiscountManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfDiscountManage.setCriteriaList(mfDiscountManage, ajaxData);// 我的筛选
			// mfDiscountManage.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfDiscountManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfDiscountManageFeign.findByPage(ipage, mfDiscountManage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX单个客户新增优惠券
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		try {
			FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
			getFormValue(formhzeydiscount0002, getMapByJson(ajaxData));
			if (this.validateFormData(formhzeydiscount0002)) {
				setObjValue(formhzeydiscount0002, mfDiscountManage);
				mfDiscountManage.setDiscountId(WaterIdUtil.getWaterId("discount"));
				mfDiscountManage.setCusNo(cusNo);
				mfDiscountManage.setDiscountAmt(mfDiscountManage.getDiscountAmt());
				mfDiscountManage.setStartDate(mfDiscountManage.getStartDate());
				mfDiscountManage.setEndDate(mfDiscountManage.getEndDate());
				mfDiscountManage.setUseFlag("0");
				mfDiscountManage.setLstModTime(DateUtil.getDateTime());
				mfDiscountManageFeign.insert(mfDiscountManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增批量新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertListAjax")
	@ResponseBody
	public Map<String, Object> insertListAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		List<MfCusCustomer> mfCusCustomerList = new ArrayList<MfCusCustomer>();
		List<MfDiscountManage> mfDiscountManageList = new ArrayList<MfDiscountManage>();
		try {
			FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
			getFormValue(formhzeydiscount0002, getMapByJson(ajaxData));
			mfCusCustomerList = cusInterfaceFeign.getAllCusList(mfCusCustomer);
			if (this.validateFormData(formhzeydiscount0002)) {
				MfDiscountManage mfDiscountManage = new MfDiscountManage();
				setObjValue(formhzeydiscount0002, mfDiscountManage);
				for (int i = 0; i < mfCusCustomerList.size(); i++) {
					mfCusCustomer = mfCusCustomerList.get(i);
					MfDiscountManage mfDiscountManages = new MfDiscountManage();
					mfDiscountManages.setDiscountId(WaterIdUtil.getWaterId("discount") + (int) (Math.random() * 999));
					mfDiscountManages.setCusNo(mfCusCustomer.getCusNo());
					mfDiscountManages.setDiscountAmt(mfDiscountManage.getDiscountAmt());
					mfDiscountManages.setStartDate(mfDiscountManage.getStartDate());
					mfDiscountManages.setEndDate(mfDiscountManage.getEndDate());
					mfDiscountManages.setUseFlag("0");
					mfDiscountManages.setLstModTime(DateUtil.getDateTime());
					mfDiscountManageList.add(mfDiscountManages);
				}
				mfDiscountManageFeign.insertList(mfDiscountManageList);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
		getFormValue(formhzeydiscount0002, getMapByJson(ajaxData));
		MfDiscountManage mfDiscountManageJsp = new MfDiscountManage();
		setObjValue(formhzeydiscount0002, mfDiscountManageJsp);
		MfDiscountManage mfDiscountManage = mfDiscountManageFeign.getById(mfDiscountManageJsp);
		if (mfDiscountManage != null) {
			try {
				mfDiscountManage = (MfDiscountManage) EntityUtil.reflectionSetVal(mfDiscountManage, mfDiscountManageJsp,
						getMapByJson(ajaxData));
				mfDiscountManageFeign.update(mfDiscountManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
			FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
			getFormValue(formhzeydiscount0002, getMapByJson(ajaxData));
			if (this.validateFormData(formhzeydiscount0002)) {
				MfDiscountManage mfDiscountManage = new MfDiscountManage();
				setObjValue(formhzeydiscount0002, mfDiscountManage);
				mfDiscountManageFeign.update(mfDiscountManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String discountId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		mfDiscountManage.setDiscountId(discountId);
		mfDiscountManage = mfDiscountManageFeign.getById(mfDiscountManage);
		getObjValue(formhzeydiscount0002, mfDiscountManage, formData);
		if (mfDiscountManage != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * 移动端接口:获取最大金额优惠券
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDiscountAmtAjax")
	@ResponseBody
	public Map<String, Object> getDiscountAmtAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfDiscountManageFeign.getMaxDiscountAmtByCusNo(cusNo);
		} catch (Exception e) {
//			logger.error("获取最大金额优惠券错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String discountId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		mfDiscountManage.setDiscountId(discountId);
		try {
			mfDiscountManageFeign.delete(mfDiscountManage);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
		model.addAttribute("formhzeydiscount0002", formhzeydiscount0002);
		model.addAttribute("query", "");
		return "/component/hzey/MfDiscountManage_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String discountId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0001");
		getFormValue(formhzeydiscount0002);
		MfDiscountManage mfDiscountManage = new MfDiscountManage();
		mfDiscountManage.setDiscountId(discountId);
		mfDiscountManage = mfDiscountManageFeign.getById(mfDiscountManage);
		getObjValue(formhzeydiscount0002, mfDiscountManage);
		model.addAttribute("mfDiscountManage", mfDiscountManage);
		model.addAttribute("formhzeydiscount0002", formhzeydiscount0002);
		model.addAttribute("query", "");
		return "/component/hzey/MfDiscountManage_Detail";
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
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
		getFormValue(formhzeydiscount0002);
		boolean validateFlag = this.validateFormData(formhzeydiscount0002);
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
		FormData formhzeydiscount0002 = formService.getFormData("hzeydiscount0002");
		getFormValue(formhzeydiscount0002);
		boolean validateFlag = this.validateFormData(formhzeydiscount0002);
	}

}

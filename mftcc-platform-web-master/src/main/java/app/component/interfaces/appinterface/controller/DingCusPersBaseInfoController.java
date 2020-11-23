package app.component.interfaces.appinterface.controller;

import java.util.HashMap;
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
import com.google.gson.Gson;

import app.component.common.BizPubParm;
import app.component.common.IdCardUtil;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusTable;
import app.component.cusinterface.CusInterfaceFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;

@Controller
@RequestMapping("/dingCusPersBaseInfo")
public class DingCusPersBaseInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;

	/**
	 * 进入完善信息页面，根据客户类型区分去哪里进行完善
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdateBaseInfo")
	public String toUpdateBaseInfo(Model model, String cusNo) throws Exception {
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String cusBaseFlag;
		// 客户基本信息
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人客户 获取个人客户基本信息
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = cusInterfaceFeign.getByCusNo(mfCusPersBaseInfo);
			cusBaseFlag = "0";
			if (mfCusPersBaseInfo != null) {
				cusBaseFlag = "1";
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
				mfCusCustomer.setContactsTel(mfCusCustomer.getCusTel());
			}
			model.addAttribute("mfCusPersBaseInfo", mfCusPersBaseInfo);
		} else {// 企业客户 获取企业客户基本信息
			MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
			cusBaseFlag = "0";
			if (mfCusCorpBaseInfo != null) {
				cusBaseFlag = "1";
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusCorpBaseInfo.getPostalCode());
				// 格式化金额
				String registeredCapitalFormatStr = getMoneyStrWithoutScientific(mfCusCorpBaseInfo.getRegisteredCapital());
				model.addAttribute("registeredCapitalFormatStr", registeredCapitalFormatStr);
				// 第一次没有资产总额营业收入，不需要格式化
				// assetSumFormatStr =
				// getMoneyStrWithoutScientific(mfCusCorpBaseInfo.getAssetSum());
				// bussIncomeFormatStr =
				// getMoneyStrWithoutScientific(mfCusCorpBaseInfo.getBussIncome());
			}
			model.addAttribute("mfCusCorpBaseInfo", mfCusCorpBaseInfo);
		}
		model.addAttribute("cusBaseFlag", cusBaseFlag);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人
			model.addAttribute("query", "");
			return "/component/interfaces/appinterface/DingCusPersBaseInfo";
		} else {// 企业
			model.addAttribute("query", "");
			return "/component/interfaces/appinterface/DingCusCorpBaseInfo";
		}
	}

	/**
	 * 格式化double类型的金额，不要科学计数法和"逗号,"
	 * 
	 * @param money
	 * @return
	 */
	private String getMoneyStrWithoutScientific(Double money) {
		String result = "0.00";
		if (null == money) {
			money = 0D;
		}
		result = MathExtend.moneyStr(money);
		result = MathExtend.moneyStrNum(result);
		return result;

	}

	/**
	 * 进入完善信息页面，根据客户类型区分去哪里进行完善
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdateBaseInfoDiv")
	public String toUpdateBaseInfoDiv(Model model, String cusNo) throws Exception {
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String cusBaseFlag;
		// 客户基本信息
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人客户 获取个人客户基本信息
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = cusInterfaceFeign.getByCusNo(mfCusPersBaseInfo);
			cusBaseFlag = "0";
			if (mfCusPersBaseInfo != null) {
				cusBaseFlag = "1";
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
				mfCusCustomer.setContactsTel(mfCusCustomer.getCusTel());
			}
			model.addAttribute("mfCusPersBaseInfo", mfCusPersBaseInfo);
		} else {// 企业客户 获取企业客户基本信息
			MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
			cusBaseFlag = "0";
			if (mfCusCorpBaseInfo != null) {
				cusBaseFlag = "1";
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusCorpBaseInfo.getPostalCode());
				// 格式化金额
				String registeredCapitalFormatStr = getMoneyStrWithoutScientific(mfCusCorpBaseInfo.getRegisteredCapital());
				String assetSumFormatStr = getMoneyStrWithoutScientific(mfCusCorpBaseInfo.getAssetSum());
				String bussIncomeFormatStr = getMoneyStrWithoutScientific(mfCusCorpBaseInfo.getBussIncome());
				model.addAttribute("registeredCapitalFormatStr", registeredCapitalFormatStr);
				model.addAttribute("assetSumFormatStr", assetSumFormatStr);
				model.addAttribute("bussIncomeFormatStr", bussIncomeFormatStr);

			}
			model.addAttribute("mfCusCorpBaseInfo", mfCusCorpBaseInfo);
		}
		model.addAttribute("cusBaseFlag", cusBaseFlag);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人
			model.addAttribute("query", "");
			return "/component/interfaces/appinterface/DingCusPersBaseInfoDiv";
		} else {// 企业
			model.addAttribute("query", "");
			return "/component/interfaces/appinterface/DingCusCorpBaseInfoDiv";
		}
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
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String jsonStr = gson.toJson(map);
			mfCusPersBaseInfo = gson.fromJson(jsonStr, MfCusPersBaseInfo.class);
			mfCusPersBaseInfo.setRegTime(DateUtil.getDate());
			// 跟新客户数据
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(mfCusPersBaseInfo.getCusNo());
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			// 对比客户基本数据
			// setObjValue(formcusper00002, mfCusCustomer);
			if (BizPubParm.ID_TYPE_ID_CARD.equals(mfCusPersBaseInfo.getIdType())) {// 身份证类型处理出生日期及性别
				String birthDay = IdCardUtil.getBirthdayFromIdCard(mfCusPersBaseInfo.getIdNum());
				String sex = IdCardUtil.getSexFromIdCard(mfCusPersBaseInfo.getIdNum());
				mfCusPersBaseInfo.setBrithday(birthDay);
				mfCusPersBaseInfo.setSex(sex);
			}
			mfCusCustomer.setIdType(mfCusPersBaseInfo.getIdType());
			mfCusCustomer.setIdNum(mfCusPersBaseInfo.getIdNum());
			// mfCusCustomer.setCommAddress(mfCusPersBaseInfo.getRegHomeAddre());
			mfCusCustomer.setCusTel(mfCusPersBaseInfo.getCusTel());
			mfCusCustomer.setContactsTel(mfCusPersBaseInfo.getCusTel());
			mfCusCustomer.setCusName(mfCusPersBaseInfo.getCusName());
			mfCusCustomer.setContactsName(mfCusPersBaseInfo.getCusName());
			cusInterfaceFeign.updateMfCusPersBaseInfo(mfCusPersBaseInfo);// 保存基本信息
			cusInterfaceFeign.updateMfCusCustomer(mfCusCustomer);// 保存客户信息

			MfCusTable mfCusTable = new MfCusTable();
			mfCusTable.setCusNo(mfCusPersBaseInfo.getCusNo());
			mfCusTable.setCusType(mfCusCustomer.getCusType());
			mfCusTable.setDataFullFlag("1");
			mfCusTable.setTableName("mf_cus_pers_base_info");
			cusInterfaceFeign.updateCusTable(mfCusTable);
			String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusCustomer.getCusNo());// 更新客户信息完整度
			// dataMap.put("commAddress", mfCusPersBaseInfo.getCommAddress());
			// dataMap.put("cusTel", mfCusPersBaseInfo.getCusTel());
			// dataMap.put("idNum", mfCusPersBaseInfo.getIdNum());
			// dataMap.put("cusName", mfCusPersBaseInfo.getCusName());
			// dataMap.put("htmlStrFlag","1");
			// dataMap.put("mfCusCustomer", mfCusCustomer);
			dataMap.put("flag", "success");
			// dataMap.put("infIntegrity",infIntegrity);
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存 企业客户基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCusCorpAjax")
	@ResponseBody
	public Map<String, Object> updateCusCorpAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		try {
			FormData formcuscorp00002 = formService.getFormData("cuscorp00002");
			getFormValue(formcuscorp00002, getMapByJson(ajaxData));
			mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			setObjValue(formcuscorp00002, mfCusCorpBaseInfo);
			mfCusCorpBaseInfo.setRegTime(DateUtil.getDateTime());
			cusInterfaceFeign.updateMfCusCorpBaseInfo(mfCusCorpBaseInfo);

			MfCusTable mfCusTable = new MfCusTable();
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			MfCusCustomer mfCusCustomerTmp = new MfCusCustomer();
			mfCusCustomer.setCusNo(mfCusCorpBaseInfo.getCusNo());
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			setObjValue(formcuscorp00002, mfCusCustomerTmp);
			mfCusCustomer = cusInterfaceFeign.updateCustomter(mfCusCustomerTmp, mfCusCustomer);
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			// 将邮政编码传到页面
			mfCusCustomer.setPostalCode(mfCusCustomerTmp.getPostalCode());
			mfCusTable.setCusNo(mfCusCorpBaseInfo.getCusNo());
			mfCusTable.setCusType(mfCusCustomer.getCusType());
			mfCusTable.setDataFullFlag("1");
			mfCusTable.setTableName("mf_cus_corp_base_info");
			cusInterfaceFeign.updateCusTable(mfCusTable);
			String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusCorpBaseInfo.getCusNo());
			// 获取行业分类不展示
			// Child child =
			// mfCusCorpBaseInfoFeign.getLoanUseById(mfCusCorpBaseInfo);
			// mfCusCorpBaseInfo.setWayClassName(child.getName());
			dataMap.put("mfCusCustomer", mfCusCustomer);
			dataMap.put("flag", "success");
			dataMap.put("infIntegrity", infIntegrity);
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
}

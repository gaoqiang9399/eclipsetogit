package app.component.collateral.controller;

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

import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.common.EntityUtil;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfBusCollateralDetailRelController.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Apr 12 14:38:49 CST 2017
 **/
@Controller
@RequestMapping("/mfBusCollateralDetailRel")
public class MfBusCollateralDetailRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
	private MfBusCollateralDetailRel mfBusCollateralDetailRel;

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfBusCollateralDetailRel_List";
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
											  String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		try {
			mfBusCollateralDetailRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCollateralDetailRel.setCriteriaList(mfBusCollateralDetailRel, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusCollateralDetailRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfBusCollateralDetailRelFeign.findByPage(ipage, mfBusCollateralDetailRel);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbuscollateraldetailrel0002 = formService.getFormData("buscollateraldetailrel0002");
			getFormValue(formbuscollateraldetailrel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbuscollateraldetailrel0002)) {
				mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
				setObjValue(formbuscollateraldetailrel0002, mfBusCollateralDetailRel);
				mfBusCollateralDetailRelFeign.insert(mfBusCollateralDetailRel);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbuscollateraldetailrel0002 = formService.getFormData("buscollateraldetailrel0002");
		getFormValue(formbuscollateraldetailrel0002, getMapByJson(ajaxData));
		MfBusCollateralDetailRel mfBusCollateralDetailRelJsp = new MfBusCollateralDetailRel();
		setObjValue(formbuscollateraldetailrel0002, mfBusCollateralDetailRelJsp);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRelJsp);
		if (mfBusCollateralDetailRel != null) {
			try {
				mfBusCollateralDetailRel = (MfBusCollateralDetailRel) EntityUtil.reflectionSetVal(
						mfBusCollateralDetailRel, mfBusCollateralDetailRelJsp, getMapByJson(ajaxData));
				mfBusCollateralDetailRelFeign.update(mfBusCollateralDetailRel);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		try {
			FormData formbuscollateraldetailrel0002 = formService.getFormData("buscollateraldetailrel0002");
			getFormValue(formbuscollateraldetailrel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbuscollateraldetailrel0002)) {
				mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
				setObjValue(formbuscollateraldetailrel0002, mfBusCollateralDetailRel);

				mfBusCollateralDetailRelFeign.update(mfBusCollateralDetailRel);
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
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbuscollateraldetailrel0002 = formService.getFormData("buscollateraldetailrel0002");
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
		getObjValue(formbuscollateraldetailrel0002, mfBusCollateralDetailRel, formData);
		if (mfBusCollateralDetailRel != null) {
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		try {
			mfBusCollateralDetailRelFeign.delete(mfBusCollateralDetailRel);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formbuscollateraldetailrel0002 = formService.getFormData("buscollateraldetailrel0002");
		model.addAttribute("formbuscollateraldetailrel0002", formbuscollateraldetailrel0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralDetailRel_Insert";
	}

	/***
	 * 新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formbuscollateraldetailrel0002 = formService.getFormData("buscollateraldetailrel0002");
		getFormValue(formbuscollateraldetailrel0002);
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		setObjValue(formbuscollateraldetailrel0002, mfBusCollateralDetailRel);
		mfBusCollateralDetailRelFeign.insert(mfBusCollateralDetailRel);
		getObjValue(formbuscollateraldetailrel0002, mfBusCollateralDetailRel);
		this.addActionMessage(model, "保存成功");
		@SuppressWarnings("unchecked")
		List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = (List<MfBusCollateralDetailRel>) mfBusCollateralDetailRelFeign
				.findByPage(this.getIpage(), mfBusCollateralDetailRel).getResult();
		model.addAttribute("formbuscollateraldetailrel0002", formbuscollateraldetailrel0002);
		model.addAttribute("mfBusCollateralDetailRelList", mfBusCollateralDetailRelList);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralDetailRel_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String id,String operable) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formbuscollateraldetailrel0001 = formService.getFormData("buscollateraldetailrel0001");
		getFormValue(formbuscollateraldetailrel0001);
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
		getObjValue(formbuscollateraldetailrel0001, mfBusCollateralDetailRel);
		model.addAttribute("formbuscollateraldetailrel0001", formbuscollateraldetailrel0001);
		model.addAttribute("query", "");
		model.addAttribute("operable", operable);
		return "/component/collateral/MfBusCollateralDetailRel_Detail";
	}

	/**
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request, response);
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRelFeign.delete(mfBusCollateralDetailRel);
		return getListPage();
	}

	/**
	 *
	 * 方法描述： 获得多选押品数据源
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-17 下午4:36:57
	 */
	@RequestMapping("/getPledgeDataAjax")
	@ResponseBody
	public Map<String, Object> getPledgeDataAjax(String busCollateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray dataArray = mfBusCollateralDetailRelFeign.getPledgeData(busCollateralId);
			dataMap.put("items", dataArray);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 根据应收账款获得相应的发票
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-17 下午8:13:23
	 */
	@RequestMapping("/getRelInvoiceByPledgeAjax")
	@ResponseBody
	public Map<String, Object> getRelInvoiceByPledgeAjax(String busCollateralId, String pleStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray dataArray = mfBusCollateralDetailRelFeign.getRelInvoiceData(busCollateralId, pleStr);
			dataMap.put("items", dataArray);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 根据选择押品获得关联货物明细
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-6-12 上午9:43:57
	 */
	@RequestMapping("/getRelGoodsDeailByPledgeAjax")
	@ResponseBody
	public Map<String, Object> getRelGoodsDeailByPledgeAjax(String pleStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfBusCollateralDetailRelFeign.getRelGoodsDeailData(pleStr);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：更新从合同编号
	 *
	 * @return
	 */
	@RequestMapping("/updateFollowPactNoAjax")
	@ResponseBody
	public Map<String, Object> updateFollowPactNoAjax(String followPactNo, String id) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setFollowPactNo(followPactNo);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
		Map<String, String> map = new HashMap<String, String>();
		map.put("pactNo", followPactNo);
		map.put("followType", "从");
		if (mfBusCollateralDetailRel != null) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.EXIST_PACT_NO.getMessage(map));
			return dataMap;
		}
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
		if (mfBusCollateralDetailRel == null) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			return dataMap;
		}
		mfBusCollateralDetailRel.setFollowPactNo(followPactNo);
		mfBusCollateralDetailRelFeign.update(mfBusCollateralDetailRel);
		dataMap.put("flag", "success");

		return dataMap;
	}

	/**
	 * 方法描述：更新从合同展示号
	 *
	 * @return
	 */
	@RequestMapping("/updateFollowPactNoShowAjax")
	@ResponseBody
	public Map<String, Object> updateFollowPactNoShowAjax(String followPactNoShow, String id) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
		if (mfBusCollateralDetailRel == null) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			return dataMap;
		}
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRel.setFollowPactNoShow(followPactNoShow);
		mfBusCollateralDetailRelFeign.update(mfBusCollateralDetailRel);
		dataMap.put("flag", "success");

		return dataMap;
	}

	/**
	 * 方法描述：获取模板
	 *
	 * @return
	 */
	@RequestMapping("/getTemplateBizConfigIdAjax")
	@ResponseBody
	public Map<String, Object> getTemplateBizConfigIdAjax(String id,String templateBizConfigId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
		mfBusCollateralDetailRel.setId(id);
		mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);

		MfTemplateBizConfig tbc = new MfTemplateBizConfig();
		List<MfTemplateBizConfig> tbcList = new ArrayList<MfTemplateBizConfig>();
		tbc.setTemBizNo(mfBusCollateralDetailRel.getCollateralId());
		tbc.setExt1(id);//设置押品关系明细id
		try {
			tbcList = mfTemplateBizConfigFeign.getBizConfigList(tbc);
			if(tbcList.size()==0){//对于历史数据，ext1为空。故还按照之前逻辑进行处理
				tbc = new MfTemplateBizConfig();
				tbc.setTemBizNo(mfBusCollateralDetailRel.getCollateralId());
				tbcList = mfTemplateBizConfigFeign.getBizConfigList(tbc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mfBusCollateralDetailRel == null || tbcList == null || tbcList.size() != 1) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			return dataMap;
		}
		dataMap.put("templateBizConfigId", tbcList.get(0).getTemplateBizConfigId());
		dataMap.put("repayDetailId", mfBusCollateralDetailRel.getCollateralId());
		dataMap.put("flag", "success");

		return dataMap;
	}

	@RequestMapping("/getByAppId")
	@ResponseBody
	public Map<String, Object> getByAppId(String appId,Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralDetailRel mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
			mfBusCollateralDetailRel.setAppId(appId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("mfBusCollateralDetailRel",mfBusCollateralDetailRel));
			ipage = mfBusCollateralDetailRelFeign.findByPageAppId(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}

}

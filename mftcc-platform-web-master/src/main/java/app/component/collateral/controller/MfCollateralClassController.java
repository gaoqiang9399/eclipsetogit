package app.component.collateral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.tech.oscache.CodeUtils;
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

import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.msgconf.entity.MfMsgPledge;
import app.component.msgconf.feign.MfMsgPledgeFeign;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCollateralClassController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 10:15:54 CST 2017
 **/
@Controller
@RequestMapping("/mfCollateralClass")
public class MfCollateralClassController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCollateralClassFeign mfCollateralClassFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private MfMsgPledgeFeign mfMsgPledgeFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private YmlConfig ymlConfig;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, MfCollateralClass mfCollateralClass)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCollateralClassFeign.getAll(mfCollateralClass), null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralclass0002 = formService.getFormData("dlcollateralclass0002");
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCollateralClass", mfCollateralClass));
		List<MfCollateralClass> mfCollateralClassList = (List<MfCollateralClass>) mfCollateralClassFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formdlcollateralclass0002", formdlcollateralclass0002);
		model.addAttribute("mfCollateralClassList", mfCollateralClassList);
		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralClass_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralclass0002 = formService.getFormData("dlcollateralclass0002");
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		List<MfCollateralClass> mfCollateralClassList = mfCollateralClassFeign.getAll(mfCollateralClass);
		model.addAttribute("query", "");
		model.addAttribute("mfCollateralClassList", mfCollateralClassList);
		model.addAttribute("formdlcollateralclass0002", formdlcollateralclass0002);
		return "/component/collateral/MfCollateralClass_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping("/certiInfo") @ResponseBody public Map<String, Object>
	 * insertAjax(String ajaxData) throws Exception {
	 * ActionContext.initialize(request, response);FormService formService = new
	 * FormService(); Map<String, Object> dataMap = new HashMap<String,
	 * Object>(); try{ FormData formdlcollateralclass0002 =
	 * formService.getFormData("dlcollateralclass0002");
	 * getFormValue(formdlcollateralclass0002, getMapByJson(ajaxData));
	 * if(this.validateFormData(formdlcollateralclass0002)){ mfCollateralClass =
	 * new MfCollateralClass(); setObjValue(formdlcollateralclass0002,
	 * mfCollateralClass); mfCollateralClassFeign.insert(mfCollateralClass);
	 * getTableData( dataMap, tableId, mfCollateralClass);//获取列表
	 * dataMap.put("flag", "success"); dataMap.put("msg",
	 * MessageEnum.SUCCEED_INSERT.getMessage()); }else{ dataMap.put("flag",
	 * "error"); dataMap.put("msg",this.getFormulavaliErrorMsg()); }
	 * }catch(Exception e){ e.printStackTrace(); dataMap.put("flag", "error");
	 * dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage()); throw e; }
	 * return dataMap; }
	 */
	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralclass0002 = formService.getFormData("dlcollateralclass0002");
			getFormValue(formdlcollateralclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralclass0002)) {
				MfCollateralClass mfCollateralClass = new MfCollateralClass();
				setObjValue(formdlcollateralclass0002, mfCollateralClass);
				mfCollateralClassFeign.update(mfCollateralClass);
				getTableData(dataMap, tableId, mfCollateralClass);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	@ResponseBody public Map<String, Object> getByIdAjax(String classId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralclass0002 = formService.getFormData("dlcollateralclass0002");
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		mfCollateralClass.setClassId(classId);
		mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
		getObjValue(formdlcollateralclass0002, mfCollateralClass, formData);
		if (mfCollateralClass != null) {
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData, String classId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		mfCollateralClass.setClassId(classId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCollateralClass = (MfCollateralClass) JSONObject.toBean(jb, MfCollateralClass.class);
			mfCollateralClassFeign.delete(mfCollateralClass);
			getTableData(dataMap, tableId, mfCollateralClass);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 方法描述： 押品新增时跳转到弹框选择押品类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCollateralTypeList")
	public String getCollateralTypeList() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/getCollateralTypeList";
	}

	/**
	 * 
	 * 方法描述： 业务流程中 押品新增时跳转到弹框选择押品类型
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-11 下午8:15:55
	 *  暂时废弃，目前使用{@link MfBusCollateralRelController#insertInput},
	 *             与振国、会珊、浩兵、远飞讨论 wangchao 2017-06-15
	 */

	@RequestMapping("/getListPageForSelect")
	public String getListPageForSelect() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/mfCollateralClass_select";
	}

	/**
	 * 
	 * 方法描述： 根据担保方式获得押品类别
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-11 下午8:17:59
	 */
	@RequestMapping("/getListPageByVoutypeAjax")
	@ResponseBody public Map<String, Object> getListPageByVoutypeAjax(String ajaxData, String vouType, Integer pageNo,
			Integer pageSize, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		try {
			mfCollateralClass.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCollateralClass.setCriteriaList(mfCollateralClass, ajaxData);// 我的筛选
			mfCollateralClass.setVouType(vouType);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法

			ipage = mfCollateralClassFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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

	/***
	 * Ajax抵质押品类型列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/selectCollateralTypeAjax")
	@ResponseBody public Map<String, Object> selectCollateralTypeAjax(String ajaxData, Integer pageNo, Integer pageSize,
			String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		try {
			mfCollateralClass.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCollateralClass.setCriteriaList(mfCollateralClass, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPledgeClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法

			ipage = mfCollateralClassFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
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
	 * 
	 * 方法描述： 根据选择的押品类别，查询押品类别信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author hgx
	 * @date 2017-03-09 上午10:55:43
	 */
	@RequestMapping("/getCollateralClassByClassIdAjax")
	@ResponseBody public Map<String, Object> getCollateralClassByClassIdAjax(String vouType, String classId, String cusNo,
			String cusName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			String pledgeNo = vouType + WaterIdUtil.getWaterId();
			pledgeBaseInfo.setPledgeNo(pledgeNo);
			pledgeBaseInfo.setClassId(classId);
			// pledgeBaseInfo.setClassName(className);
			pledgeBaseInfo.setCusNo(cusNo);
			pledgeBaseInfo.setCusName(cusName);
			pledgeBaseInfo.setPledgeMethod(vouType);
			pledgeBaseInfo.setKeepStatus("0");// 未入库
			pledgeBaseInfo.setDelflag("0");// 未删除
			pledgeBaseInfoFeign.insert(pledgeBaseInfo);

			dataMap.put("vouType", vouType);
			dataMap.put("classId", classId);
			// dataMap.put("pledgeImpawnNo", pledgeImpawnNo);
			dataMap.put("pledgeImpawnNo", pledgeNo);

			MfCollateralClass mfCollateralClass = new MfCollateralClass();
			mfCollateralClass.setClassId(classId);
			mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
			if (mfCollateralClass != null) {
				dataMap.put("flag", "1");
				if (!"".equals(mfCollateralClass.getAddFormId()) && mfCollateralClass.getAddFormId() != null) {
					dataMap.put("formid_new", mfCollateralClass.getAddFormId());
				} else {
					dataMap.put("formid_new", mfCollateralClass.getMotherAddFormId());
				}
				dataMap.put("formid_old", mfCollateralClass.getMotherAddFormId());
			} else {
				dataMap.put("flag", "0");
				dataMap.put("formid_new", BizPubParm.pledge_class_form_add);
				dataMap.put("formid_old", BizPubParm.pledge_class_form_add);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}

		return dataMap;
	}

	/***
	 * 抵质押品类型列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMfCollateralConfig")
	public String getMfCollateralConfig(Model model,String entranceType,String classFirstNo) throws Exception {
		ActionContext.initialize(request, response);
		try {
			MfCollateralClass mfCollateralClass = new MfCollateralClass();
			if(StringUtil.isEmpty(classFirstNo)){
				classFirstNo="A,B,C,D";
			}
			mfCollateralClass.setClassFirstNoList(Arrays.asList(classFirstNo.split(",")));
			List<MfCollateralClass> mfCollateralClassList = mfCollateralClassFeign.getAll(mfCollateralClass);
			List<MfCollateralClass> mfCollateralClassListA = new ArrayList<MfCollateralClass>();
			List<MfCollateralClass> mfCollateralClassListB = new ArrayList<MfCollateralClass>();
			List<MfCollateralClass> mfCollateralClassListC = new ArrayList<MfCollateralClass>();
			List<MfCollateralClass> mfCollateralClassListD = new ArrayList<MfCollateralClass>();
			List<MfCollateralClass> mfCollateralClassListE = new ArrayList<MfCollateralClass>();
			List<MfCollateralClass> mfCollateralClassListF = new ArrayList<MfCollateralClass>();

			String vouType = "";
			String classModel = "";
			Map<String, String> vouTypeMap = new HashMap<String, String>();
			Map<String, String> classModelMap = new HashMap<String, String>();
			List<ParmDic> vouTypeList = nmdInterfaceFeign.findByParmDicAllByKename("VOU_TYPE_PLEDGE");
			List<ParmDic> classModelList = nmdInterfaceFeign.findByParmDicAllByKename("CLASS_MODEL");
			for (ParmDic pd : vouTypeList) {
				vouTypeMap.put(pd.getOptCode(), pd.getOptName());
			}
			for (ParmDic pd : classModelList) {
				classModelMap.put(pd.getOptCode(), pd.getOptName());
			}

			for (MfCollateralClass mcc : mfCollateralClassList) {
				if (StringUtil.isEmpty(mcc.getClassFirstNo())) {
					continue;
				}
				vouType = mcc.getVouType();
				if (vouType.contains(BizPubParm.VOU_TYPE_3)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_3, vouTypeMap.get(BizPubParm.VOU_TYPE_3));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_4)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_4, vouTypeMap.get(BizPubParm.VOU_TYPE_4));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_5)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_5, vouTypeMap.get(BizPubParm.VOU_TYPE_5));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_6)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_6, vouTypeMap.get(BizPubParm.VOU_TYPE_6));
				}

				if (vouType.endsWith("|")) {
					vouType = vouType.substring(0, vouType.length() - 1);
				}
				mcc.setVouType(vouType);

				classModel = mcc.getClassModel();
				if (classModel.contains(BizPubParm.CLASS_MODEL_RECE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_RECE,classModelMap.get(BizPubParm.CLASS_MODEL_RECE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_MOVEABLE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_MOVEABLE,classModelMap.get(BizPubParm.CLASS_MODEL_MOVEABLE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_WARE_RECEIPT)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_WARE_RECEIPT,classModelMap.get(BizPubParm.CLASS_MODEL_WARE_RECEIPT));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_LEASE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_LEASE,classModelMap.get(BizPubParm.CLASS_MODEL_LEASE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_OTHER)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_OTHER,classModelMap.get(BizPubParm.CLASS_MODEL_OTHER));
				}
				if (classModel.endsWith("|")) {
					classModel = classModel.substring(0, classModel.length() - 1);
				}
				mcc.setClassModel(classModel);

				if ("A".equals(mcc.getClassFirstNo())) {
					mfCollateralClassListA.add(mcc);
				} else if ("B".equals(mcc.getClassFirstNo())) {
					mfCollateralClassListB.add(mcc);
				} else if ("C".equals(mcc.getClassFirstNo())) {
					mfCollateralClassListC.add(mcc);
				} else if ("D".equals(mcc.getClassFirstNo())) {
					mfCollateralClassListD.add(mcc);
				} else if("E".equals(mcc.getClassFirstNo())){
					mfCollateralClassListE.add(mcc);
				} else if("F".equals(mcc.getClassFirstNo())){
					mfCollateralClassListF.add(mcc);
				}else {
				}
			}
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(mfCollateralClassListA);
			json.put("mfCollateralClassListA", array);
			model.addAttribute("mfCollateralClassListA", mfCollateralClassListA);
			model.addAttribute("mfCollateralClassListB", mfCollateralClassListB);
			model.addAttribute("mfCollateralClassListC", mfCollateralClassListC);
			model.addAttribute("mfCollateralClassListD", mfCollateralClassListD);
			model.addAttribute("mfCollateralClassListE", mfCollateralClassListE);
			model.addAttribute("mfCollateralClassListF", mfCollateralClassListF);
			// 预警配置信息
			MfMsgPledge mfMsgPledge = new MfMsgPledge();
			Map<String, List<MfMsgPledge>> mmpMap = mfMsgPledgeFeign.getMfMsgPledgeMap(mfMsgPledge);
			json.put("mmpMap", JSONObject.fromObject(mmpMap));

			String ajaxData = json.toString();
			model.addAttribute("ajaxData", ajaxData);
			String projectName= ymlConfig.getSysParams().get("sys.project.name");
			model.addAttribute("entranceType", entranceType);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		model.addAttribute("query", "");
		
		return "/component/collateral/MfCollateralClass_GetMfCollateralConfig";
	}

	/**
	 * 
	 * 方法描述： 跳转押品类型新增表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author hgx
	 * @date 2017-04-24 下午15:52:42
	 */
	@RequestMapping("/collateralTypeInput")
	public String collateralTypeInput(Model model, String classFirstNo, String classFirstName) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		mfCollateralClass.setClassFirstNo(classFirstNo);
		mfCollateralClass.setClassFirstName(classFirstName);
		mfCollateralClass.setBillFlag("1");// 默认存在清单
		// 查询父类新增、详情表单
		String classSecondNo = mfCollateralClassFeign.findNextClassSecondNo(classFirstNo);
		// MfCollateralClass mfCollateralClassTemp = new MfCollateralClass();
		// mfCollateralClassTemp.setClassSecondNo(classFirstNo);
		// mfCollateralClassTemp =
		// mfCollateralClassFeign.getByClassSecondNo(mfCollateralClassTemp);
		String insClassSecondNo = StringUtil.incrementStr(classSecondNo);
		String motherAddFormId = "pledgeBase" + classFirstNo.toLowerCase() + insClassSecondNo;
		String motherDetailFormId = "pledgeDetail" + classFirstNo.toLowerCase() + insClassSecondNo;
		mfCollateralClass.setMotherAddFormId(motherAddFormId);
		mfCollateralClass.setMotherDetailFormId(motherDetailFormId);
		FormData formdlcollateralclass0001 = formService.getFormData("dlcollateralclass0001");
		getObjValue(formdlcollateralclass0001, mfCollateralClass);
		model.addAttribute("formdlcollateralclass0001", formdlcollateralclass0001);
		model.addAttribute("query", "");
		
		return "/component/collateral/CollateralTypeInput";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-21 上午10:46:14
	 */
	@RequestMapping("/collateralTypeInsertAjax")
	@ResponseBody public Map<String, Object> collateralTypeInsertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		try {
			FormData formdlcollateralclass0001 = formService.getFormData("dlcollateralclass0001");
			getFormValue(formdlcollateralclass0001, getMapByJson(ajaxData));
			setObjValue(formdlcollateralclass0001, mfCollateralClass);
			if (this.validateFormData(formdlcollateralclass0001)) {
				// 新增表单和详情表单不能一致
				if (mfCollateralClass.getMotherAddFormId().equals(mfCollateralClass.getMotherDetailFormId())) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.ERROR.getMessage("请输入不同的表单号"));
					return dataMap;
				}

				// 获取父类新增、详情表单
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType("",
						"PledgeBaseInfoAction", mfCollateralClass.getClassFirstNo());
				if (null == mfCollateralFormConfig) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_EMPTY.getMessage("父类配置表单"));
					return dataMap;
				}

				// 判断新增表单是否已存在
				FormData formAdd = formService.getFormData(mfCollateralFormConfig.getAddModel());
				formAdd.setFormId(mfCollateralClass.getMotherAddFormId());
				formAdd.setTitle(mfCollateralClass.getClassSecondName() + "信息");
				String msg = formService.saveForm(formAdd, "insert");
				if (!"".equals(msg)) {
					dataMap.put("flag", "error");
					dataMap.put("msg",
							MessageEnum.EXIST_INFORMATION_EVAL.getMessage(mfCollateralClass.getMotherAddFormId()));
					return dataMap;
				}
				FormData formDetail = formService.getFormData(mfCollateralFormConfig.getShowModel());
				formDetail.setFormId(mfCollateralClass.getMotherDetailFormId());
				formDetail.setTitle(mfCollateralClass.getClassSecondName() + "信息");
				msg = formService.saveForm(formDetail, "insert");
				if (!"".equals(msg)) {
					// 删除新增表单
					formService.saveForm(formAdd, "delete");
					dataMap.put("flag", "error");
					dataMap.put("msg",
							MessageEnum.EXIST_INFORMATION_EVAL.getMessage(mfCollateralClass.getMotherDetailFormId()));
					return dataMap;
				}
				String waterId = WaterIdUtil.getWaterId();
				mfCollateralClass.setClassId(waterId);
				mfCollateralClassFeign.insert(mfCollateralClass);
				dataMap.put("classFirstNo", mfCollateralClass.getClassFirstNo());
				dataMap.put("classFirstName", mfCollateralClass.getClassFirstName());
				dataMap.put("classSecondName", mfCollateralClass.getClassSecondName());
				dataMap.put("classId", mfCollateralClass.getClassId());

				String vouType = mfCollateralClass.getVouType();
				Map<String, String> vouTypeMap = new HashMap<String, String>();
				List<ParmDic> vouTypeList = nmdInterfaceFeign.findByParmDicAllByKename("VOU_TYPE_PLEDGE");
				for (ParmDic pd : vouTypeList) {
					vouTypeMap.put(pd.getOptCode(), pd.getOptName());
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_3)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_3, vouTypeMap.get(BizPubParm.VOU_TYPE_3));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_4)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_4, vouTypeMap.get(BizPubParm.VOU_TYPE_4));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_5)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_5, vouTypeMap.get(BizPubParm.VOU_TYPE_5));
				}
				if (vouType.endsWith("|")) {
					vouType = vouType.substring(0, vouType.length() - 1);
				}

				String classModel = mfCollateralClass.getClassModel();
				Map<String, String> classModelMap = new HashMap<String, String>();
				List<ParmDic> classModelList = nmdInterfaceFeign.findByParmDicAllByKename("CLASS_MODEL");
				for (ParmDic pd : classModelList) {
					classModelMap.put(pd.getOptCode(), pd.getOptName());
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_RECE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_RECE,
							classModelMap.get(BizPubParm.CLASS_MODEL_RECE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_MOVEABLE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_MOVEABLE,
							classModelMap.get(BizPubParm.CLASS_MODEL_MOVEABLE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_WARE_RECEIPT)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_WARE_RECEIPT,
							classModelMap.get(BizPubParm.CLASS_MODEL_WARE_RECEIPT));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_OTHER)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_OTHER,
							classModelMap.get(BizPubParm.CLASS_MODEL_OTHER));
				}
				if (classModel.endsWith("|")) {
					classModel = classModel.substring(0, classModel.length() - 1);
				}

				dataMap.put("vouType", vouType);
				dataMap.put("classModel", classModel);
				dataMap.put("useFlag", "1");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
				return dataMap;
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-21 上午10:46:14
	 */
	@RequestMapping("/collateralTypeUpdateAjax")
	@ResponseBody public Map<String, Object> collateralTypeUpdateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		try {
			FormData formdlcollateralclass0002 = formService.getFormData("dlcollateralclass0002");
			getFormValue(formdlcollateralclass0002, getMapByJson(ajaxData));
			setObjValue(formdlcollateralclass0002, mfCollateralClass);
			if (this.validateFormData(formdlcollateralclass0002)) {
				mfCollateralClassFeign.update(mfCollateralClass);
				dataMap.put("classFirstNo", mfCollateralClass.getClassFirstNo());
				dataMap.put("classFirstName", mfCollateralClass.getClassFirstName());
				dataMap.put("classSecondName", mfCollateralClass.getClassSecondName());
				dataMap.put("classId", mfCollateralClass.getClassId());

				String vouType = mfCollateralClass.getVouType();
				Map<String, String> vouTypeMap = new HashMap<String, String>();
				List<ParmDic> vouTypeList = nmdInterfaceFeign.findByParmDicAllByKename("VOU_TYPE_PLEDGE");
				for (ParmDic pd : vouTypeList) {
					vouTypeMap.put(pd.getOptCode(), pd.getOptName());
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_3)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_3, vouTypeMap.get(BizPubParm.VOU_TYPE_3));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_4)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_4, vouTypeMap.get(BizPubParm.VOU_TYPE_4));
				}
				if (vouType.contains(BizPubParm.VOU_TYPE_5)) {
					vouType = vouType.replace(BizPubParm.VOU_TYPE_5, vouTypeMap.get(BizPubParm.VOU_TYPE_5));
				}
				if (vouType.endsWith("|")) {
					vouType = vouType.substring(0, vouType.length() - 1);
				}

				String classModel = mfCollateralClass.getClassModel();
				Map<String, String> classModelMap = new HashMap<String, String>();
				List<ParmDic> classModelList = nmdInterfaceFeign.findByParmDicAllByKename("CLASS_MODEL");
				for (ParmDic pd : classModelList) {
					classModelMap.put(pd.getOptCode(), pd.getOptName());
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_RECE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_RECE,
							classModelMap.get(BizPubParm.CLASS_MODEL_RECE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_MOVEABLE)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_MOVEABLE,
							classModelMap.get(BizPubParm.CLASS_MODEL_MOVEABLE));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_WARE_RECEIPT)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_WARE_RECEIPT,
							classModelMap.get(BizPubParm.CLASS_MODEL_WARE_RECEIPT));
				}
				if (classModel.contains(BizPubParm.CLASS_MODEL_OTHER)) {
					classModel = classModel.replace(BizPubParm.CLASS_MODEL_OTHER,
							classModelMap.get(BizPubParm.CLASS_MODEL_OTHER));
				}
				if (classModel.endsWith("|")) {
					classModel = classModel.substring(0, classModel.length() - 1);
				}
				dataMap.put("vouType", vouType);
				dataMap.put("classModel", classModel);
				dataMap.put("useFlag", mfCollateralClass.getUseFlag());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
				return dataMap;
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 跳转押品类型新增表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author hgx
	 * @date 2017-06-16 下午13:52:42
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String classId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		mfCollateralClass.setClassId(classId);
		mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);

		/*
		 * String vouTypeStr = mfCollateralClass.getVouType(); String vouTypeDes
		 * = ""; List<ParmDic> vouTypeList =
		 * nmdInterfaceFeign.findByParmDicAllByKename("VOU_TYPE"); for(ParmDic
		 * pd:vouTypeList){ if(vouTypeStr.contains(pd.getOptCode())){ vouTypeDes
		 * += pd.getOptName() + "@"; } } vouTypeDes = vouTypeDes.substring(0,
		 * vouTypeDes.length()-1); mfCollateralClass.setVouTypeDes(vouTypeDes);
		 * 
		 * String classModelStr = mfCollateralClass.getClassModel(); String
		 * classModelDes = ""; List<ParmDic> classModelList =
		 * nmdInterfaceFeign.findByParmDicAllByKename("CLASS_MODEL");
		 * for(ParmDic pd:classModelList){
		 * if(classModelStr.contains(pd.getOptCode())){ classModelDes +=
		 * pd.getOptName() + "@"; } } classModelDes = classModelDes.substring(0,
		 * classModelDes.length()-1);
		 * mfCollateralClass.setClassModelDes(classModelDes);
		 */

		FormData formdlcollateralclass0002 = formService.getFormData("dlcollateralclass0002");
		getObjValue(formdlcollateralclass0002, mfCollateralClass);
		model.addAttribute("formdlcollateralclass0002", formdlcollateralclass0002);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralTypeDetail";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getVouTypeForMutiSel")
	public String getVouTypeForMutiSel(Model model) throws Exception {
		ActionContext.initialize(request, response);
		List<ParmDic> parmDiclist = nmdInterfaceFeign.findByParmDicAllByKename("VOU_TYPE");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic pd = new ParmDic();
		List<ParmDic> tempList = new ArrayList<ParmDic>();
		for (int i = 0; i < parmDiclist.size(); i++) {
			pd = parmDiclist.get(i);
			if (BizPubParm.VOU_TYPE_1.equals(pd.getOptCode()) || BizPubParm.VOU_TYPE_2.equals(pd.getOptCode())) {
				tempList.add(pd);
			}
		}
		parmDiclist.removeAll(tempList);
		dataMap.put("vouTypelist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/collateral/VouType_MutiSel";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getClassModelForMutiSel")
	public String getClassModelForMutiSel(Model model) throws Exception {
		ActionContext.initialize(request, response);
		List<ParmDic> parmDiclist = nmdInterfaceFeign.findByParmDicAllByKename("CLASS_MODEL");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("classModellist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/collateral/ClassModel_MutiSel";
	}

	@RequestMapping("/updateUserFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUserFlagAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			String classId = (String) jobj.get("classId");
			if (StringUtil.isEmpty(classId)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}
			mfCollateralClass.setClassId(classId);
			String useFlag = (String) jobj.get("useFlag");
			if (useFlag != null) {
				mfCollateralClass.setUseFlag(useFlag);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}
			
			mfCollateralClassFeign.update(mfCollateralClass);
			dataMap.put("classId", mfCollateralClass.getClassId());
			dataMap.put("useFlag", mfCollateralClass.getUseFlag());
			dataMap.put("flag", "success");
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

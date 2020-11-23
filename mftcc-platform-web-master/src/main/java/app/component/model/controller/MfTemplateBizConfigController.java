package app.component.model.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.assure.entity.MfAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.model.entity.MfSysTemplate;
import app.component.model.feign.MfNewToPageOfficeFeign;
import app.component.model.entity.MfTemplateTagBase;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFollowPact;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.stamp.entity.MfStampBaseInfo;
import app.component.stamp.entity.MfStampTemplate;
import app.component.stamp.feign.MfStampPactFeign;
import app.component.wkf.feign.WkfInterfaceFeign;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.taglib.JsonFormUtil;
import com.dhcc.workflow.api.task.Task;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.entity.MfTemplateShow;
import app.component.model.feign.MfSysTemplateFeign;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.model.feign.MfTemplateShowFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

/**
 * Title: MfTemplateBizConfigAction.java Description: 业务与模板关系
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 05 11:02:40 CST 2017
 **/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/mfTemplateBizConfig")
public class MfTemplateBizConfigController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
	@Autowired
	private MfTemplateShowFeign mfTemplateShowFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	private String functionPoint;// 功能点: wjdy(业务视角文件打印); jdbg(业务视图尽调报告)
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;
	@Autowired
	private AssureInterfaceFeign assureInterfaceFeign;
	@Autowired
	private MfNewToPageOfficeFeign mfNewToPageOfficeFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfStampPactFeign mfStampPactFeign;
	/**
	 * 查询当前业务当前节点配置的模板列表
	 *
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getBizConfigListAjax")
	@ResponseBody
	public Map<String, Object> getBizConfigListAjax(String ajaxData, String temBizNo, String nodeNo, int pageSize,
													int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);

		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemBizNo(temBizNo);// 业务主键
		templateBizConfig.setNodeNo(nodeNo);// 节点编号
		List<MfTemplateBizConfig> mfTemplateBizConfigList = mfTemplateBizConfigFeign
				.getBizConfigList(templateBizConfig);

		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage.setResult(mfTemplateBizConfigList);
		ipage.setPageSum(1);
		ipage.setPageCounts(mfTemplateBizConfigList.size());
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("ipage", ipage);

		return dataMap;
	}

	@RequestMapping(value = "/getBizConfigsAjax")
	@ResponseBody
	public Map<String, Object> getBizConfigsAjax(String nodeNo,String temBizNo) throws Exception {
		ActionContext.initialize(request, response);

		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemBizNo(temBizNo);// 业务主键
		templateBizConfig.setNodeNo(nodeNo);// 节点编号
		templateBizConfig.setTemplateType("1,7,8");// 1其他文档模板, 7主合同模板, 8尽调报告模板
		List<MfTemplateBizConfig> mfTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("bizConfigs", mfTemplateBizConfigList);

		return dataMap;
	}

	@RequestMapping(value = "/getBizConfigsForListAjax")
	@ResponseBody
	public Map<String, Object> getBizConfigsForListAjax(String nodeNo,String temBizNo)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String querySaveFlag = request.getParameter("querySaveFlag");
		String paramMap = request.getParameter("paramMap");
        MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
        JSONObject jasonObject = JSONObject.fromObject(paramMap);
        Map<String,Object> map = (Map<String,Object>)jasonObject;
        if(map != null){
            String fincId = (String)map.get("fincId");
            String ifEsignHistory = (String)map.get("ifEsignHistory");
//            if(ifEsignHistory == null || "".equals(ifEsignHistory) || BizPubParm.YES_NO_N.equals(ifEsignHistory)){
//                if(fincId == null || "".equals(fincId) ){
//                    MfBusFincApp mfBusFincApp = new MfBusFincApp();
//                    mfBusFincApp.setAppId(temBizNo);
//                    mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
//                    if(mfBusFincApp != null){
//                        fincId = mfBusFincApp.getFincId();
//                    }
//                }
                templateBizConfig.setFincId(fincId);
//            }
            templateBizConfig.setIfElectricSign(ifEsignHistory);
        }
        templateBizConfig.setTemBizNo(temBizNo);// 业务主键
        templateBizConfig.setNodeNo(nodeNo);// 节点编号
        templateBizConfig.setQuerySaveFlag(querySaveFlag);
		JsonTableUtil jtu = new JsonTableUtil();
		List<MfTemplateBizConfig> list = (List<MfTemplateBizConfig>)mfTemplateBizConfigFeign.getBizConfigListFilterRepeat(templateBizConfig);
		if(list.size()>0){
			String  tableHtml = jtu.getJsonStr("tableincludetemplatemodel","tableTag", list, null,true);
			dataMap.put("bizConfigs", tableHtml);
			//查看二维码列显隐标志
			String qrCodeShowFlag = new CodeUtils().getSingleValByKey("TEMPLATE_QR_CODE_VIEW");
			dataMap.put("qrCodeShowFlag", qrCodeShowFlag);
			dataMap.put("list", list);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * 用印文档展示
	 * @param temBizNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBizConfigsForStampListAjax")
	@ResponseBody
	public Map<String, Object> getBizConfigsForStampListAjax(String temBizNo)throws Exception{
		ActionContext.initialize(request, response);
		List<MfTemplateBizConfig> list = new ArrayList<MfTemplateBizConfig>();
		MfStampBaseInfo mfStampBaseInfo = new MfStampBaseInfo();
		mfStampBaseInfo.setStampId(temBizNo);
		mfStampBaseInfo = mfStampPactFeign.getStampBaseInfoById(mfStampBaseInfo);
		if(mfStampBaseInfo!=null){
			if("1".equals(mfStampBaseInfo.getStampType())){
				MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
				if(StringUtil.isNotEmpty(mfStampBaseInfo.getCreditAppId())){
					templateBizConfig.setTemBizNo(mfStampBaseInfo.getCreditAppId());// 业务主键
				}
				if(StringUtil.isNotEmpty(mfStampBaseInfo.getAdjustAppId())){
					templateBizConfig.setTemBizNo(mfStampBaseInfo.getAdjustAppId());// 业务主键
				}
				templateBizConfig.setIfStamp("1");
				templateBizConfig.setStampSts("0");
				List<MfTemplateBizConfig>  creditList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);
				if(creditList!=null){
					for (int i = 0; i < creditList.size(); i++) {
						if(StringUtil.isNotEmpty(creditList.get(i).getDocFileName())&&StringUtil.isNotEmpty(creditList.get(i).getDocFilePath())){
							list.add(creditList.get(i));
						}
					}
				}
			}else if ("2".equals(mfStampBaseInfo.getStampType())){
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(mfStampBaseInfo.getPactId());
				mfBusPact = mfBusPactFeign.getById(mfBusPact);
				if(mfBusPact!=null){
					MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
					if(StringUtil.isNotEmpty(mfBusPact.getAppId())){
						templateBizConfig.setTemBizNo(mfBusPact.getAppId());// 业务主键
					}
					templateBizConfig.setIfStamp("1");
					templateBizConfig.setStampSts("0");
					List<MfTemplateBizConfig>  creditList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);
					if(creditList!=null){
						for (int i = 0; i < creditList.size(); i++) {
							if(StringUtil.isNotEmpty(creditList.get(i).getDocFileName())&&StringUtil.isNotEmpty(creditList.get(i).getDocFilePath())){
								list.add(creditList.get(i));
							}
						}
					}
					//放款通知书
					//if("2".equals(mfStampBaseInfo.getStampSts())){
					MfBusFincApp mfBusFincApp = new MfBusFincApp();
					mfBusFincApp.setAppId(mfBusPact.getAppId());
					mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
					if(mfBusFincApp != null){
						MfTemplateBizConfig templateBizConfigFinc = new MfTemplateBizConfig();
						templateBizConfigFinc.setIfStamp("1");
						templateBizConfigFinc.setStampSts("0");
						templateBizConfigFinc.setTemBizNo(mfBusFincApp.getFincId());
						List<MfTemplateBizConfig>  fincList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfigFinc);
						if(fincList!=null){
							for (int i = 0; i < fincList.size(); i++) {
								if(StringUtil.isNotEmpty(fincList.get(i).getDocFileName())&&StringUtil.isNotEmpty(fincList.get(i).getDocFilePath())){
									list.add(fincList.get(i));
								}
							}
						}
					}
					//}
				}
			}else if ("3".equals(mfStampBaseInfo.getStampType())){
				MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(mfStampBaseInfo.getPactId());
				mfBusPact = mfBusPactFeign.getById(mfBusPact);
				if(mfBusPact!=null){
					templateBizConfig = new MfTemplateBizConfig();
					if(StringUtil.isNotEmpty(mfBusPact.getAppId())){
						templateBizConfig.setTemBizNo(mfBusPact.getAppId());// 业务主键
					}
					templateBizConfig.setIfStamp("1");
					templateBizConfig.setStampSts("0");
					List<MfTemplateBizConfig>  creditList2 = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);
					if(creditList2!=null){
						for (int i = 0; i < creditList2.size(); i++) {
							if(StringUtil.isNotEmpty(creditList2.get(i).getDocFileName())&&StringUtil.isNotEmpty(creditList2.get(i).getDocFilePath())){
								list.add(creditList2.get(i));
							}
						}
					}
					//放款通知书
					//if("2".equals(mfStampBaseInfo.getStampSts())){
					MfBusFincApp mfBusFincApp = new MfBusFincApp();
					mfBusFincApp.setAppId(mfBusPact.getAppId());
					mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
					if(mfBusFincApp != null){
						MfTemplateBizConfig templateBizConfigFinc = new MfTemplateBizConfig();
						templateBizConfigFinc.setIfStamp("1");
						templateBizConfigFinc.setStampSts("0");
						templateBizConfigFinc.setTemBizNo(mfBusFincApp.getFincId());
						List<MfTemplateBizConfig>  fincList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfigFinc);
						if(fincList!=null){
							for (int i = 0; i < fincList.size(); i++) {
								if(StringUtil.isNotEmpty(fincList.get(i).getDocFileName())&&StringUtil.isNotEmpty(fincList.get(i).getDocFilePath())){
									list.add(fincList.get(i));
								}
							}
						}
					}
					//}

				}

			}
		}

		for (int i = 0; i < list.size(); i++) {
			MfTemplateBizConfig templateBizConfig = list.get(i);
			templateBizConfig.setExt10("1");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		if(list.size()>0){
			String  tableHtml = jtu.getJsonStr("tabletemplatestampinsert","tableTag", list, null,true);
			dataMap.put("bizConfigs", tableHtml);
			//查看二维码列显隐标志
			String qrCodeShowFlag = new CodeUtils().getSingleValByKey("TEMPLATE_QR_CODE_VIEW");
			dataMap.put("qrCodeShowFlag", qrCodeShowFlag);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * 用印文档展示
	 * @param temBizNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBizConfigsForStampApproveListAjax")
	@ResponseBody
	public Map<String, Object> getBizConfigsForStampApproveListAjax(String temBizNo)throws Exception{
		ActionContext.initialize(request, response);
		List<MfTemplateBizConfig> list = new ArrayList<MfTemplateBizConfig>();
		MfStampTemplate mfStampTemplate = new MfStampTemplate();
		mfStampTemplate.setStampId(temBizNo);
		List<MfStampTemplate> mfStampTemplateList = mfStampPactFeign.getMfStampTemplateList(mfStampTemplate);
		if(mfStampTemplateList!=null&&mfStampTemplateList.size()>0){
			for (int i = 0; i < mfStampTemplateList.size(); i++) {
				mfStampTemplate = mfStampTemplateList.get(i);
				MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
				mfTemplateBizConfig.setTemplateBizConfigId(mfStampTemplate.getTemplateBizConfigId());
				mfTemplateBizConfig = mfTemplateBizConfigFeign.getById(mfTemplateBizConfig);
				list.add(mfTemplateBizConfig);
			}
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		if(list.size()>0){
			String  tableHtml = jtu.getJsonStr("tabletemplatestampdetail","tableTag", list, null,true);
			dataMap.put("bizConfigs", tableHtml);
			//查看二维码列显隐标志
			String qrCodeShowFlag = new CodeUtils().getSingleValByKey("TEMPLATE_QR_CODE_VIEW");
			dataMap.put("qrCodeShowFlag", qrCodeShowFlag);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * 拼接需要调用的office文档参数信息
	 *
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @param appId
	 * @param cusNo
	 * @param pactId
	 * @param fincId
	 * @param repayDetailId
	 * @date 2017-7-6 下午4:50:26
	 */
	@RequestMapping(value = "/getOfficeUrlAjax")
	@ResponseBody
	public Map<String, Object> getOfficeUrlAjax(String templateBizConfigId, String appId, String cusNo, String pactId, String fincId, String repayDetailId,String collateralId,String creditAppId,String nodeNo,String chargeId,String refundId) throws Exception {
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		Logger logger = LoggerFactory.getLogger(MfTemplateBizConfigController.class);
		HttpServletRequest request = getHttpRequest();

		String webPath = ymlConfig.getWebservice().get("webPath");
		if(StringUtil.isNotEmpty(webPath) && !webPath.startsWith("/")){
			webPath= "/"+webPath;
		}
		String contextPath =webPath;//request.getContextPath();

		// 查询当前配置
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
		templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);

		Map<String, String> poCnt = new HashMap<String, String>();
		if(!"".equals(templateBizConfig.getTemplateNo()) && templateBizConfig.getTemplateNo() != null){

			//获取模版要生成文件类型标识
			MfSysTemplate mfSysTemplate = new MfSysTemplate();
			mfSysTemplate.setTemplateNo(templateBizConfig.getTemplateNo());
			mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
			logger.error("*************************************查询时changeType="+mfSysTemplate.getChangeType());
			poCnt.put("changeType", mfSysTemplate.getChangeType());
		}
		// 权限控制
		if ("2".equals(templateBizConfig.getOptPower()) || "wjdy".equals(functionPoint)|| StringUtil.isEmpty(templateBizConfig.getOptPower())) {// 修改
			// 业务视角的文件打印永远有修改功能 20170721 wangchao
			poCnt.put("saveBtn", "1");// 保存按钮 0无1有
			poCnt.put("readOnly", "0");// 只读 1只读 0非只读
		} else {// 只读
			poCnt.put("saveBtn", "0");// 保存按钮 0无1有
			poCnt.put("readOnly", "0");// 只读 1只读 0非只读
		}


		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
		if(mfBusApply != null){
            poCnt.put("kindNo", mfBusApply.getKindNo());//获取产品编号
        }
        if(StringUtil.isNotEmpty(creditAppId)){
			poCnt.put("creditAppId", creditAppId);
		}else{
			if(mfBusApply != null&&StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
				poCnt.put("creditAppId", mfBusApply.getCreditAppId());//授信关联号
			}else {
				poCnt.put("creditAppId", appId);
			}
		}
		if (StringUtil.isNotEmpty(templateBizConfig.getDocFileName())) {// 已保存过
			poCnt.put("filePath", templateBizConfig.getDocFilePath());// 打开文件路径
			// 例子/factor/component/model/docmodel/
			poCnt.put("fileName", templateBizConfig.getDocFileName());// 打开文件名
		} else {// 未保存过
			poCnt.put("fileName", templateBizConfig.getTemplateNameEn());// 打开文件名
			// 保存文档url
			StringBuilder saveUrl = new StringBuilder();
			saveUrl.append(contextPath+"/component/model/templateInclude_toSave.jsp?1=1");
			saveUrl.append("&templateBizConfigId=").append(templateBizConfig.getTemplateBizConfigId());
			saveUrl.append("&userId=").append(User.getRegNo(request));
			poCnt.put("saveUrl", saveUrl.toString());// 保存url
		}
		poCnt.put("fileType", "0");// 打开属性 0自动判断1 doc 2excel 3ppt
		poCnt.put("printBtn", "1");// 打印按钮 0无1有
		if(StringUtil.isEmpty(cusNo) && StringUtil.isNotEmpty(appId)){
			// 主要参数赋值
			cusNo = setAttributeByAppId(appId);
		}


		// 查询当前模板对应的标签数据
		poCnt.put("templateNo", templateBizConfig.getTemplateNo());
		poCnt.put("templateSuffix", templateBizConfig.getTemplateSuffix());
		poCnt.put("templateBizConfigId", templateBizConfig.getTemplateBizConfigId());
		//获取 押品保证信息id
		/*if(StringUtil.isNotEmpty(appId)){
			//抵质押信息列表
			List<PledgeBaseInfo>  pledgeBaseInfoList = null;
			//保证信息列表
			List<MfAssureInfo> assureInfoList = null;
			List<MfBusCollateralDetailRel> collateralDetailRelList = collateralInterfaceFeign.getCollateralDetailRelList(appId);;
			if(collateralDetailRelList!=null&&collateralDetailRelList.size()>0){
				pledgeBaseInfoList = new ArrayList<PledgeBaseInfo>();
				assureInfoList = new ArrayList<MfAssureInfo>();
				StringBuffer pleId = new StringBuffer();
				for(MfBusCollateralDetailRel relbean  : collateralDetailRelList){
					String colateralId = relbean.getCollateralId();//押品编号
					//押品信息id
					pleId.append(",").append(colateralId);
					PledgeBaseInfo pledgebean = new PledgeBaseInfo();
					pledgebean.setPledgeNo(colateralId);
					PledgeBaseInfo plbean = collateralInterfaceFeign.getById(pledgebean);//获取押品实体类
					if(plbean!=null){
						//获取押品类型，这里只想要房屋的
						String classId = plbean.getClassId();
						MfCollateralClass mfCollateralClass = new MfCollateralClass();
						mfCollateralClass.setClassId(classId);
						mfCollateralClass = collateralInterfaceFeign.getCollateralClassById(mfCollateralClass);//获取类别
						if(mfCollateralClass!=null){
							String classFirstNo = mfCollateralClass.getClassFirstNo();
							if("B".equals(classFirstNo)){//是房屋类型的就存押品
								poCnt.put("pledgeNo", colateralId);
							}
						}
						pledgeBaseInfoList.add(plbean);
					}

					//保证信息查询
					MfAssureInfo assureInfo = new MfAssureInfo();
					assureInfo.setId(colateralId);
					assureInfo = assureInterfaceFeign.getById(assureInfo);
					if(assureInfo != null){
						assureInfoList.add(assureInfo);
					}

				}
				String pledgeAssureId = pleId.toString();
				if(pledgeAssureId.length()>1){
					pledgeAssureId = pledgeAssureId.substring(1);
//					poCnt.put("pledgeAssureId", pledgeAssureId);
				}
			}
		}*/
		//使用备用字段存储合作银行ID，标签取值时作为参数
		if (StringUtil.isNotEmpty(templateBizConfig.getExt1())) {
			poCnt.put("agenciesId", templateBizConfig.getExt1());
		}
		//备用字段2传递押品主键
		if (StringUtil.isNotEmpty(templateBizConfig.getExt2())) {
			poCnt.put("pledgeNo", templateBizConfig.getExt2());
		}
		//初始化节点参数
		if (BizPubParm.SCENCE_TYPE_DOC_AFTER_CHECK.equals(nodeNo)){//保后跟踪
			poCnt.put("examHisId",collateralId);
		}else if (BizPubParm.SCENCE_TYPE_DOC_RISK_AUDIT.equals(nodeNo)){//风控稽核
			poCnt.put("auditId",collateralId);
		}else if (BizPubParm.FEE_COLLECT_ALONE.equals(nodeNo)){//保后收费
			poCnt.put("chargeId", chargeId);
		}else if ("refundManage".equals(nodeNo)){//退费申请
			poCnt.put("refundId", refundId);
		}
		poCnt.put("cusNo", cusNo);
		poCnt.put("pactId", pactId);
		poCnt.put("appId", appId);
		poCnt.put("fincId", fincId);
		poCnt.put("temBizNo", templateBizConfig.getTemBizNo());
		poCnt.put("collateralId", collateralId);
		poCnt.put("opName", User.getRegName(request));
		poCnt.put("orgNo", User.getOrgNo(request));
		poCnt.put("orgName", User.getOrgName(request));
		poCnt.put("repayDetailId", repayDetailId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("poCnt", gson.toJson(poCnt));
		logger.error("************调用标签参数="+gson.toJson(poCnt));
		return dataMap;
	}

	/**
	 * 拼接需要调用的office文档参数信息(与业务无关联)
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @param appId
	 * @param cusNo
	 * @param pactId
	 * @param fincId
	 * @param repayDetailId
	 * @date 2017-7-6 下午4:50:26
	 */
	@RequestMapping(value = "/getOfficeUrlAjaxWithoutWkf")
	@ResponseBody
	public Map<String, Object> getOfficeUrlAjaxWithoutWkf(String templateNo, String appId, String cusNo, String pactId, String fincId, String repayDetailId,String collateralId,String invoiceId) throws Exception {
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		Logger logger = LoggerFactory.getLogger(MfTemplateBizConfigController.class);
		HttpServletRequest request = getHttpRequest();


		Map<String, String> poCnt = new HashMap<String, String>();

		//获取模版要生成文件类型标识
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(templateNo);
		mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
		logger.error("*************************************查询时changeType="+mfSysTemplate.getChangeType());
		poCnt.put("changeType", mfSysTemplate.getChangeType());

		poCnt.put("saveBtn", "0");// 保存按钮 0无1有
		poCnt.put("readOnly", "0");// 只读 1只读 0非只读

		poCnt.put("fileType", "0");// 打开属性 0自动判断1 doc 2excel 3ppt
		poCnt.put("printBtn", "1");// 打印按钮 0无1有
		// 查询当前模板对应的标签数据
		poCnt.put("templateNo", templateNo);
		poCnt.put("cusNo", cusNo);
		poCnt.put("pactId", pactId);
		poCnt.put("appId", appId);
		poCnt.put("fincId", fincId);
		poCnt.put("invoiceId", invoiceId);
		poCnt.put("collateralId", repayDetailId);
		poCnt.put("fileName", mfSysTemplate.getTemplateNameEn());
		poCnt.put("opName", User.getRegName(request));
		poCnt.put("orgNo", User.getOrgNo(request));
		poCnt.put("orgName", User.getOrgName(request));

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("poCnt", gson.toJson(poCnt));

		return dataMap;
	}

	@RequestMapping(value = "/updateTemplateDataAjax")
	@ResponseBody
	public Map<String, Object> updateTemplateDataAjax(String ajaxData, String templateBizConfigId, String docFilePath, String docFileName
			,String orgName,String orgNo,String opName,String userId) {
		ActionContext.initialize(request, response);
		Logger logger = LoggerFactory.getLogger(MfTemplateBizConfigController.class);

		// 查询当前配置
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
		templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);

		// 为兼容后期如果有重新新增保存需求。此处以业务编号、模板编号做更新条件
		MfTemplateBizConfig tbc = new MfTemplateBizConfig();
		tbc.setTemBizNo(templateBizConfig.getTemBizNo());
		tbc.setTemplateNo(templateBizConfig.getTemplateNo());
		tbc.setDocFilePath(docFilePath);
		tbc.setDocFileName(docFileName);
		tbc.setOpNo(userId);
		tbc.setOpName(opName);
		tbc.setBrNo(orgNo);
		tbc.setBrName(orgName);
		tbc.setLstModTime(DateUtil.getDateTime());// 最后修改时间 格式：yyyyMMdd HH:mm:ss
		tbc.setTemplateBizConfigId(templateBizConfigId);
		if (StringUtil.isEmpty(templateBizConfig.getDocFileName())) {// 之前还未上传，记录登记时间
			tbc.setRegTime(DateUtil.getDateTime());// 登记时间 格式：yyyyMMdd HH:mm:ss
		}
		mfTemplateBizConfigFeign.update(tbc);
		logger.error("************pageoffice保存templateBizConfigId"+templateBizConfigId+",DocFilePath："+docFilePath);
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("flag","success");
		return dataMap;
	}

	/**
	 * 查询当前已保存的尽调报告
	 *
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-29 下午2:45:57
	 */
	@ResponseBody
	@RequestMapping(value = "/getMfTemplateBizConfig")
	public Map<String, Object> getMfTemplateBizConfig(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);

		// 查询尽调报告展示配置
		MfTemplateShow templateShow = new MfTemplateShow();
		templateShow.setShowType("resp_investigation_doc_show");
		List<MfTemplateShow> templateShowList = mfTemplateShowFeign.getList(templateShow);

		// 要展示的文档编号
		List<String> templateTypeList = new ArrayList<String>();
		for (MfTemplateShow ts : templateShowList) {
			templateTypeList.add(ts.getTemplateNo());
		}

		List<MfTemplateBizConfig> tbcList = new ArrayList<MfTemplateBizConfig>();
		Integer uploadSize = 0;// 已保存的尽调阶段文档个数
		if (templateTypeList.size() > 0) {
			// 查询相关文档配置
			MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
			templateBizConfig.setTemBizNo(appId);
			templateBizConfig.setNodeNo(WKF_NODE.resp_investigation.getNodeNo());
			templateBizConfig.setTemplateTypeList(templateTypeList);
			tbcList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);

			// 计算已保存个数
			Iterator<MfTemplateBizConfig> iterator = tbcList.iterator();
			while (iterator.hasNext()) {
				MfTemplateBizConfig tbc = iterator.next();
				if (StringUtil.isNotEmpty(tbc.getDocFileName())) {
					uploadSize++;
				} else {
					iterator.remove();
				}
			}
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbcList", tbcList);// 所有尽调阶段的文档list
		dataMap.put("uploadSize", uploadSize);// 已保存的尽调阶段文档个数

		return dataMap;
	}

	/**
	 * 业务主视角-尽调报告按钮，查询审批以前已保存的所有文档
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-29 下午3:12:36
	 */
	@RequestMapping(value = "/printSurveyReportList")
	public String printSurveyReportList(Model model, String ajaxData,String appId) throws Exception {
		ActionContext.initialize(request, response);

		// 查询尽调报告展示配置
		MfTemplateShow templateShow = new MfTemplateShow();
		templateShow.setShowType("resp_investigation_doc_show");
		List<MfTemplateShow> templateShowList = mfTemplateShowFeign.getList(templateShow);

		// 要展示的文档编号
		List<String> templateTypeList = new ArrayList<String>();
		for (MfTemplateShow ts : templateShowList) {
			templateTypeList.add(ts.getTemplateNo());
		}

		List<MfTemplateBizConfig> bizConfigList=null;
		if (templateTypeList.size() > 0) {
			MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
			templateBizConfig.setTemBizNo(appId);
			templateBizConfig.setTemplateTypeList(templateTypeList);
			templateBizConfig.setNodeNo(WKF_NODE.resp_investigation.getNodeNo());
			bizConfigList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);
		} else {
			bizConfigList = new ArrayList<MfTemplateBizConfig>();
		}

		model.addAttribute("bizConfigList", bizConfigList);
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/model/templateBiz_printSurveyReportList";
	}

	/**
	 *
	 * 方法描述： office查询参数赋值
	 *
	 * @param appid
	 *            void
	 * @author lwq
	 * @throws Exception
	 * @date 2017-11-8 下午6:36:47
	 */
	private String setAttributeByAppId(String appid) throws Exception {
		String cusNo = null;
		if(appid != null && appid.contains("SX")){
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(appid);
			mfCusCreditApply = creditApplyInterfaceFeign.getCusCreditApply(mfCusCreditApply);
			cusNo = mfCusCreditApply.getCusNo();
		}else{
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appid);
			cusNo = mfBusApply.getCusNo();
		}
		return cusNo;
	}

	@ResponseBody
	@RequestMapping(value = "/validateTemplateIsInput")
	public Map<String,Object> validateTemplateIsInput(String templateBizConfigIds) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			if(StringUtil.isNotEmpty(templateBizConfigIds)){
				MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
				mfTemplateBizConfig.setTemplateBizConfigIdList(Arrays.asList(templateBizConfigIds.split(",")));
				dataMap = mfTemplateBizConfigFeign.validateTemplateIsInput(mfTemplateBizConfig);
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 查询当前已保存的风险审查
	 *
	 * @return
	 * @throws Exception
	 * @author 吕春杰
	 * @date 2018-9-6 下午14:37:57
	 */
	@ResponseBody
	@RequestMapping(value = "/getRiskReviewMfTemplateBizConfig")
	public Map<String, Object> getRiskReviewMfTemplateBizConfig(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);

		// 查询风险审查展示配置
		MfTemplateShow templateShow = new MfTemplateShow();
		templateShow.setShowType("risk-review-doc-show");
		List<MfTemplateShow> mfTemplateShowList = mfTemplateShowFeign.getList(templateShow);

		// 要展示的文档编号
		List<String> templateTypeList = new ArrayList<String>();
		for (MfTemplateShow mfTemplateShow : mfTemplateShowList) {
			templateTypeList.add(mfTemplateShow.getTemplateNo());
		}

		List<MfTemplateBizConfig> mfTemplateBizConfigList = new ArrayList<MfTemplateBizConfig>();
		Integer uploadSize = 0;// 已保存的风险审查阶段文档个数
		if (templateTypeList.size() > 0) {
			// 查询相关文档配置
			MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
			templateBizConfig.setTemBizNo(appId);
			templateBizConfig.setNodeNo(WKF_NODE.risk_review.getNodeNo());
			templateBizConfig.setTemplateTypeList(templateTypeList);
			mfTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);

			// 计算已保存个数
			Iterator<MfTemplateBizConfig> iterator = mfTemplateBizConfigList.iterator();
			while (iterator.hasNext()) {
				MfTemplateBizConfig mfTemplateBizConfig = iterator.next();
				if (StringUtil.isNotEmpty(mfTemplateBizConfig.getDocFileName())) {
					uploadSize++;
				} else {
					iterator.remove();
				}
			}
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mfTemplateBizConfigList", mfTemplateBizConfigList);// 所有风险审查阶段的文档list
		dataMap.put("uploadSize", uploadSize);// 已保存的风险审查阶段文档个数

		return dataMap;
	}
	/**
	 * 业务主视角-风险审查按钮，查询审批以前已保存的所有文档
	 * @return
	 * @throws Exception
	 * @author 吕春杰
	 * @date 2018-9-6 下午14:43:36
	 */
	@RequestMapping(value = "/printRiskReviewList")
	public String printRiskReviewList(Model model, String ajaxData,String appId) throws Exception {
		ActionContext.initialize(request, response);

		// 查询风险审查展示配置
		MfTemplateShow templateShow = new MfTemplateShow();
		templateShow.setShowType("risk-review-doc-show");
		List<MfTemplateShow> templateShowList = mfTemplateShowFeign.getList(templateShow);

		// 要展示的文档编号
		List<String> templateTypeList = new ArrayList<String>();
		for (MfTemplateShow mfTemplateShow : templateShowList) {
			templateTypeList.add(mfTemplateShow.getTemplateNo());
		}

		List<MfTemplateBizConfig> bizConfigList=null;
		if (templateTypeList.size() > 0) {
			MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
			templateBizConfig.setTemBizNo(appId);
			templateBizConfig.setTemplateTypeList(templateTypeList);
			templateBizConfig.setNodeNo(WKF_NODE.risk_review.getNodeNo());
			bizConfigList = mfTemplateBizConfigFeign.getBizConfigList(templateBizConfig);
		} else {
			bizConfigList = new ArrayList<MfTemplateBizConfig>();
		}

		model.addAttribute("bizConfigList", bizConfigList);
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/model/templateBiz_printSurveyReportList";
	}

	@ResponseBody
	@RequestMapping(value = "/deleteTemplateSaveInfo")
	public Map<String, Object> deleteTemplateSaveInfo(String templateBizConfigId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
			mfTemplateBizConfig.setTemplateBizConfigId(templateBizConfigId);
			mfTemplateBizConfigFeign.deleteTemplateSaveInfo(mfTemplateBizConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 预览标签值功能
	 *
	 * @return
	 * @throws Exception
	 * @author lvchunjie
	 * @param appId
	 * @param cusNo
	 * @param pactId
	 * @param fincId
	 * @date 2019-2-28 上午10:00:30
	 */
	@RequestMapping(value = "/previewTagValue")
	public String previewTagValue(Model model,String templateBizConfigId, String appId, String cusNo, String pactId, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		Logger logger = LoggerFactory.getLogger(MfTemplateBizConfigController.class);
		FormService formService = new FormService();

		// 查询当前配置
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
		templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);

		Map<String, String> bookParmMap = new HashMap<String, String>();

		// 查询当前模板对应的标签数据
		bookParmMap.put("templateNo", templateBizConfig.getTemplateNo());
		bookParmMap.put("fileName", templateBizConfig.getTemplateNameEn());
		//获取 押品保证信息id
		if(StringUtil.isNotEmpty(appId)){
			//抵质押信息列表
			List<PledgeBaseInfo>  pledgeBaseInfoList = null;
			//保证信息列表
			List<MfAssureInfo> assureInfoList = null;
			List<MfBusCollateralDetailRel> collateralDetailRelList = collateralInterfaceFeign.getCollateralDetailRelList(appId);;
			if(collateralDetailRelList!=null&&collateralDetailRelList.size()>0){
				pledgeBaseInfoList = new ArrayList<PledgeBaseInfo>();
				assureInfoList = new ArrayList<MfAssureInfo>();
				StringBuffer pleId = new StringBuffer();
				for(MfBusCollateralDetailRel relbean  : collateralDetailRelList){
					String colateralId = relbean.getCollateralId();//押品编号
					//押品信息id
					pleId.append(",").append(colateralId);
					PledgeBaseInfo pledgebean = new PledgeBaseInfo();
					pledgebean.setPledgeNo(colateralId);
					PledgeBaseInfo plbean = collateralInterfaceFeign.getById(pledgebean);//获取押品实体类
					if(plbean!=null){
						//获取押品类型，这里只想要房屋的
						String classId = plbean.getClassId();
						MfCollateralClass mfCollateralClass = new MfCollateralClass();
						mfCollateralClass.setClassId(classId);
						mfCollateralClass = collateralInterfaceFeign.getCollateralClassById(mfCollateralClass);//获取类别
						if(mfCollateralClass!=null){
							String classFirstNo = mfCollateralClass.getClassFirstNo();
							if("B".equals(classFirstNo)){//是房屋类型的就存押品
								bookParmMap.put("pledgeNo", colateralId);
							}
						}
						pledgeBaseInfoList.add(plbean);
					}

					//保证信息查询
					MfAssureInfo assureInfo = new MfAssureInfo();
					assureInfo.setId(colateralId);
					assureInfo = assureInterfaceFeign.getById(assureInfo);
					if(assureInfo != null){
						assureInfoList.add(assureInfo);
					}

				}
				String pledgeAssureId = pleId.toString();
				if(pledgeAssureId.length()>1){
					pledgeAssureId = pledgeAssureId.substring(1);
					bookParmMap.put("pledgeAssureId", pledgeAssureId);
				}
			}
		}
		bookParmMap.put("cusNo", cusNo);
		bookParmMap.put("pactId", pactId);
		bookParmMap.put("appId", appId);
		bookParmMap.put("fincId", fincId);

		/*  访问标签项目 查询标签值信息 */
		Map<String,Object> resultMap = mfNewToPageOfficeFeign.getDataMapComm(bookParmMap);
		logger.error("*************************************预览查询标签值+"+resultMap);
		/*  获取数据库字段标签 */
		Map<String,String> columnMap = (Map<String,String>)resultMap.get("column");

		Map<String,String>	map =  new HashMap<String,String>();
		List<String> list = new ArrayList<String>();// html 填充 list
		List<String> strList = new ArrayList<String>();// 标签名称填充list
		//获取 标签名称
		for (String key : columnMap.keySet()) {
			strList.add(key);
		}
		//判断标签个数奇偶
		int size = columnMap.size();
		if(size%2==0){
			size = columnMap.size()-1;
		}else {
			size = (columnMap.size()+1)-2;
		}
		//获取表单list
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		for (int a=0;a<=size;a+=2) {
			map.put("tagName1",strList.get(a).replaceAll("\\$",""));
			map.put("tagValue1",columnMap.get(strList.get(a)));
			if(a>=size){
				map.put("tagName2","");
				map.put("tagValue2","");
			}else {
				map.put("tagName2",strList.get(a+1).replaceAll("\\$",""));
				map.put("tagValue2",columnMap.get(strList.get(a+1)));
			}
			FormData formTagShow = formService.getFormData("previewTagValue");
			getObjValue(formTagShow, map);
			FormActive[] faArr = formTagShow.getFormActives();
			for(int n = 0;n<faArr.length;n++){
				if(a+1-n>size){
					faArr[n].setLabelName("");
					faArr[n+1].setLabelName(strList.get(a).replaceAll("\\$",""));
					break;
				}else{
					faArr[n].setLabelName(strList.get(a+1-n).replaceAll("\\$",""));
				}
			}
			formTagShow.setFormActiveArray(faArr);
			String htmlStr = jsonFormUtil.getJsonStr(formTagShow,"bootstarpTag","");
			list.add(htmlStr);
		}
		//拼接表单
		StringBuffer strBuf=new StringBuffer();
		for(int b = 0;b<list.size();b++){
			String s = list.get(b);
			strBuf.append(list.get(b));
		}
		String html = strBuf.toString();

		/*  获取表格字段标签 */
		Map<String,Object> tableMap = (Map<String,Object>)resultMap.get("table");
		Map<String,Object> tableTagMap = new HashMap<String,Object>();

		String showTabFlag = "0";// 1:有表格数据 0：无表格数据
		if(tableMap.size()!=0){
			showTabFlag = "1";
		}
		if("1".equals(showTabFlag)){
			/*  获取该模板的所配置的标签 */
			List<MfTemplateTagBase> mfTemplateTagBaseList = mfNewToPageOfficeFeign.getTemplateTagBaseValue(bookParmMap);
			logger.error("*************************************获取该模板的所配置的标签+"+mfTemplateTagBaseList);
			/* 遍历list替换map中的key值 */
			for(String key : tableMap.keySet()){
				for(MfTemplateTagBase mfTemplateTagBase : mfTemplateTagBaseList){
					if(mfTemplateTagBase.getPoName()!=null && mfTemplateTagBase.getPoName().equals(key)){
						tableTagMap.put(mfTemplateTagBase.getTagKeyName().replaceAll("\\$",""),tableMap.get(key));
						break;
					}
				}
			}
		}
		boolean tipFlag = false;
		if(columnMap==null || columnMap.size()==0){
			tipFlag =true;
		}

		model.addAttribute("html", html.replaceAll("\\\n",""));
		model.addAttribute("templateName", templateBizConfig.getTemplateNameZh());
		model.addAttribute("showTabFlag",showTabFlag);
		model.addAttribute("tableTagMap", tableTagMap);
		model.addAttribute("tipFlag", tipFlag);
		model.addAttribute("query", "");

		return "/component/model/PreviewTagValueJsp";
	}

	// ---------- set get ----------

	/**
	 *
	 * 方法描述： 获取某笔业务特定场景下需要打印的文件列表
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param appId
	 * @date 2016-11-21 上午11:41:24
	 */
	@RequestMapping(value = "/getPrintFileList")
	public String getPrintFileList(Model model, String nodeNo, String appId, String cusNo, String fincId, String relNo,
								   String pactId,String generatePhase,String modelNo,String pleId,String templateBizConfigIds) throws Exception {
		ActionContext.initialize(request, response);

		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemBizNo(appId);
		templateBizConfig.setNodeNo(nodeNo);// 不传nodeNo时展示此业务所有文档文件
		List<MfTemplateBizConfig> bizConfigList = mfTemplateBizConfigFeign
				.getBizConfigListFilterRepeat(templateBizConfig);
		String charResult="";
		if(bizConfigList.size()==0 ){
            charResult="0";
		}
		model.addAttribute("charResult", charResult);
		model.addAttribute("bizConfigList", bizConfigList);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("fincId", fincId);
		model.addAttribute("relNo", relNo);
		model.addAttribute("pactId", pactId);
		model.addAttribute("generatePhase", generatePhase);
		model.addAttribute("modelNo", modelNo);
		model.addAttribute("pleId", pleId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("templateBizConfigIds", templateBizConfigIds);
		return "/component/model/MfBusTemplate_fileList";
	}

	/**
	 *
	 * 方法描述： 文件打印合同模板列表打开页面请求
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018年5月30日 下午2:58:19
	 */
	@RequestMapping(value = "/getTemplateListPage")
	public String getTemplateListPage(Model model, String nodeNo, String appId, String cusNo, String fincId, String relNo,
									  String pactId,String generatePhase,String modelNo,String pleId,String templateBizConfigIds) throws Exception {
		ActionContext.initialize(request, response);
		String qrCodeShowFlag = new CodeUtils().getSingleValByKey("TEMPLATE_QR_CODE_VIEW");
		// 获取从合同列表
		List<MfBusFollowPact> mfBusFollowPactList = new ArrayList<MfBusFollowPact>();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		Double pactAmt = 0.00;
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		if(mfBusPact != null){
			pactAmt = mfBusPact.getPactAmt();
			appId = mfBusPact.getAppId();
		}
		mfBusFollowPactList = mfBusCollateralDetailRelFeign.getMfBusFollowPactList(appId, pactAmt);
		if (mfBusFollowPactList.size() == 0) {
			mfBusFollowPactList = null;
		}
		Task task = wkfInterfaceFeign.getTask(mfBusPact.getWkfAppId(), null);
		String formId="";
		if (task == null) {
		} else if (WKF_NODE.contract_sign.getNodeNo().equals(task.getActivityName())){
			nodeNo = WKF_NODE.contract_sign.getNodeNo();// 功能节点编号
		}else if(WKF_NODE.contract_confirm.getNodeNo().equals(task.getActivityName())){
			nodeNo = WKF_NODE.contract_confirm.getNodeNo();// 功能节点编号
		}else {
		}
		// 从合同展示号 是否启用  1:启用
		String followPactNoShowSts = new CodeUtils().getSingleValByKey("FOLLOW_PACTNO_SHOW_STS");
		model.addAttribute("followPactNoShowSts", followPactNoShowSts);
		model.addAttribute("mfBusFollowPactList", mfBusFollowPactList);
		model.addAttribute("qrCodeShowFlag", qrCodeShowFlag);
		model.addAttribute("appId", appId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("fincId", fincId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("templateBizConfigIds", templateBizConfigIds);
		model.addAttribute("query","");
		return "/component/model/MfBusTemplate_fileListByPage";
	}

	@RequestMapping(value = "/getApprovalBizConfigsForListAjax")
	@ResponseBody
	public Map<String, Object> getApprovalBizConfigsForListAjax(String nodeNo,String temBizNo)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String querySaveFlag = request.getParameter("querySaveFlag");
		String paramMap = request.getParameter("paramMap");
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		JSONObject jasonObject = JSONObject.fromObject(paramMap);
		Map<String,Object> map = (Map<String,Object>)jasonObject;
		if(map != null){
			String fincId = (String)map.get("fincId");
			String ifEsignHistory = (String)map.get("ifEsignHistory");
			if(ifEsignHistory == null || "".equals(ifEsignHistory) || BizPubParm.YES_NO_N.equals(ifEsignHistory)){
				if(fincId == null || "".equals(fincId) ){
					MfBusFincApp mfBusFincApp = new MfBusFincApp();
					mfBusFincApp.setAppId(temBizNo);
					mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
					if(mfBusFincApp != null){
						fincId = mfBusFincApp.getFincId();
					}
				}
				templateBizConfig.setFincId(fincId);
			}
			templateBizConfig.setIfElectricSign(ifEsignHistory);
		}
		templateBizConfig.setTemBizNo(temBizNo);// 业务主键
		templateBizConfig.setNodeNo(nodeNo);// 节点编号
		templateBizConfig.setQuerySaveFlag(querySaveFlag);
		if(map != null){
			String fincId = (String)map.get("fincId");
			String ifEsignHistory = (String)map.get("ifEsignHistory");
			if(ifEsignHistory == null || "".equals(ifEsignHistory) || BizPubParm.YES_NO_N.equals(ifEsignHistory)){
				if(fincId == null || "".equals(fincId) ){
					MfBusFincApp mfBusFincApp = new MfBusFincApp();
					mfBusFincApp.setAppId(temBizNo);
					mfBusFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
					if(mfBusFincApp != null){
						fincId = mfBusFincApp.getFincId();
					}
				}
				templateBizConfig.setFincId(fincId);
			}
			templateBizConfig.setIfElectricSign(ifEsignHistory);
		}
		templateBizConfig.setTemBizNo(temBizNo);// 业务主键
		templateBizConfig.setNodeNo(nodeNo);// 节点编号
		templateBizConfig.setQuerySaveFlag(querySaveFlag);
		JsonTableUtil jtu = new JsonTableUtil();
		List<MfTemplateBizConfig> list = (List<MfTemplateBizConfig>)mfTemplateBizConfigFeign.getBizConfigListFilterRepeat(templateBizConfig);
		if(list.size()>0){
			String  tableHtml = jtu.getJsonStr("tableapprovalincludetemplatemodel","tableTag", list, null,true);
			dataMap.put("bizConfigs", tableHtml);
			//查看二维码列显隐标志
			String qrCodeShowFlag = new CodeUtils().getSingleValByKey("TEMPLATE_QR_CODE_VIEW");
			dataMap.put("qrCodeShowFlag", qrCodeShowFlag);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * 判断是否为符合pdf.js打开条件
	 * @param templateBizConfigId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIsPdfFlag")
	@ResponseBody
	public String getIsPdfFlag(String templateBizConfigId) throws Exception{
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		//是否是pdf文件标识 默认为否(0) 0否 1是
		String isPdfFlag = BizPubParm.YES_NO_N;
		try{
			//查询当前配置
			templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
			templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);
			//获取模板文档实际名称
			String modelName_en = templateBizConfig.getDocFileName();
			if(StringUtil.isNotEmpty(modelName_en)&&modelName_en.endsWith(".pdf")&&BizPubParm.YES_NO_Y.equals(templateBizConfig.getIfInitTag())&&StringUtil.isNotEmpty(templateBizConfig.getDocFileName())){
				isPdfFlag = BizPubParm.YES_NO_Y;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return isPdfFlag;
	}
	/**
	 * pdf.js打开方法(拼接路径并进行流转换写入Response中)
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value = "/pdfStreamHandler",method = RequestMethod.GET)
	@ResponseBody
	public void pdfStreamHandler(String name) throws Exception {
		String templateBizConfigId = name;
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		String filePath = "";
		//获取当前配置信息
		templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
		templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);

		if (StringUtil.isNotEmpty(templateBizConfig.getDocFileName())) {
			// 已保存的文档
			// 示例/factor/component/model/docmodel/
			String sqlFilePath = templateBizConfig.getDocFilePath();
			// 示例 20190924111953039.pdf
			String sqlFileName = templateBizConfig.getDocFileName();
			//拼接全量路径
			filePath = sqlFilePath+sqlFileName;
		} else {
			// 未保存过的文档暂不处理，如有需要可单独处理
		}

		//因为"/" File无法读取 所以做转义为"\\"
		String replaceStr = filePath.replaceAll("/",Matcher.quoteReplacement(File.separator));
		File file = new File(replaceStr);
		if (file.exists()){
			byte[] data = null;
			FileInputStream input = null;
			ServletOutputStream out = null;
			try {
				input= new FileInputStream(file);
				/*data = new byte[input.available()];
				input.read(data);*/
				out = response.getOutputStream();
				int index = 0;
				byte buffer[] = new byte[1024];
				while ((index = input.read(buffer)) != -1) {
					out.write(buffer , 0, index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (input != null) {
					input.close();
				}
			}
		}else{
		}
		return;
	}
}

package app.component.thirdservice.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import app.tech.oscache.CodeUtils;

public class FormBeanUtil extends BaseFormBean {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(FormBeanUtil.class);
	private FormService formService;
	private FormData formData;
	private JsonFormUtil jsonFormUtil;
	@SuppressWarnings("unused")
	private Gson gson = new Gson();

	/**
	 * 根据返回结果和表单编号生成相应的表单html字符串
	 * 
	 * @param report
	 *            :表单对应的数据源
	 * @param formId
	 *            :表单编号
	 * @return
	 * @author LiuAo
	 */
	public String getFormStr(Object report, String formId) {
		String formStr = "";
		formService = new FormService();
		formData = formService.getFormData(formId);
		getObjValue(formData, report);
		jsonFormUtil = new JsonFormUtil();
		try {
			formStr = jsonFormUtil.getJsonStr(formData, "bootstarpTag", "query");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formStr;
	}

	/**
	 * 根据数据源(list)和列表编号生成列表html
	 * 
	 * @param list
	 *            :数据源
	 * @param tableId
	 *            :列表编号
	 * @param showHead
	 *            :是否显示表头
	 * @return
	 * @author LiuAo
	 */
	@SuppressWarnings("rawtypes")
	public String getTableStr(List list, String tableId, boolean showHead) {
		String tableStr = "";
		JsonTableUtil jtu = new JsonTableUtil();
		try {
			tableStr = jtu.getJsonStr(tableId, "tableTag", list, null, showHead);
		} catch (Exception e) {
			logger.error("列表表单解析出错_tableId:" + tableId, e);
		}
		return tableStr;
	}

	/**
	 * 用于 在表单或列表外添加样式之用
	 * 
	 * @param html
	 *            表单或列表html
	 * @param className
	 *            表单:servFormList,列表:servTableList
	 * @return
	 */
	public static String formatHtml(String html, String className, String title) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='" + className + "'>");
		if (StringUtils.isNotEmpty(title)) {
			sb.append("	<h4 class='servListTitle'>");
			sb.append(title);
			sb.append("	</h4>");
		}
		sb.append("<div class='servListCont'>");
		if (StringUtils.isEmpty(html)) {
			sb.append("<div class='servNodata'>暂无数据</div>");
		} else {
			sb.append(html);
		}
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}

	/**
	 * 企业工商数据查询
	 * 
	 * @param sourceMap
	 *            数据源
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getEnterpriseQueryFormStr(Map<String, Object> sourceMap) {
		StringBuffer sb = new StringBuffer();
		if (sourceMap != null) {
			// 基本信息
			Map<String, Object> basicMap = (Map<String, Object>) sourceMap.get("basic");
			String basicHtml = null;
			if (basicMap != null) {

				basicHtml = this.getFormStr(basicMap, "enterprisebasic001");
			}
			sb.append(FormBeanUtil.formatHtml(basicHtml, "servFormList", "基本信息"));
			// 股东信息shareholder
			List<Map<String, Object>> shareholders = (List<Map<String, Object>>) sourceMap.get("shareholder");
			StringBuffer ssb = new StringBuffer();
			if (shareholders != null && shareholders.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : shareholders) {
					if (i != 0) {
						ssb.append("<div class='separatorLine'><hr></div>");
					}
					String sHtml = this.getFormStr(mm, "enterpriseshareholder001");
					ssb.append(sHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "股东信息"));
			// 高管信息
			List<Map<String, Object>> shareholderPersons = (List<Map<String, Object>>) sourceMap.get("shareholderPersons");
			StringBuffer hsb = new StringBuffer();
			if (shareholderPersons != null && shareholderPersons.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : shareholderPersons) {
					if (i != 0) {
						hsb.append("<div class='separatorLine'><hr></div>");
					}
					String sHtml = this.getFormStr(mm, "enterpriseshareholderpersons001");
					hsb.append(sHtml);
					i++;
				}

			}
			sb.append(FormBeanUtil.formatHtml(hsb.toString(), "servFormList", "高管信息"));
			// 法人对外投资信息
			List<Map<String, Object>> legalPersonInvests = (List<Map<String, Object>>) sourceMap.get("legalPersonInvests");
			StringBuffer lsb = new StringBuffer();
			if (legalPersonInvests != null && legalPersonInvests.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : legalPersonInvests) {
					if (i != 0) {
						lsb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterpriselegalpersoninvests001");
					lsb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(lsb.toString(), "servFormList", "法人对外投资信息"));

			// 法人其他任职信息
			List<Map<String, Object>> legalPersonPostions = (List<Map<String, Object>>) sourceMap.get("legalPersonPostions");
			StringBuffer lpsb = new StringBuffer();
			if (legalPersonPostions != null && legalPersonPostions.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : legalPersonPostions) {
					if (i != 0) {
						lpsb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterpriselegalpersonpostions001");
					lpsb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(lpsb.toString(), "servFormList", "法人其他任职信息"));

			// 企业对外投资信息
			List<Map<String, Object>> enterpriseInvests = (List<Map<String, Object>>) sourceMap.get("enterpriseInvests");
			StringBuffer esb = new StringBuffer();
			if (enterpriseInvests != null && enterpriseInvests.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : enterpriseInvests) {
					if (i != 0) {
						esb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterpriseinvests001");
					esb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(esb.toString(), "servFormList", "企业对外投资信息"));

			// 变更信息
			List<Map<String, Object>> alterInfos = (List<Map<String, Object>>) sourceMap.get("alterInfos");
			StringBuffer aisb = new StringBuffer();
			if (alterInfos != null && alterInfos.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : alterInfos) {
					if (i != 0) {
						aisb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisealterinfos001");
					aisb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(aisb.toString(), "servFormList", "变更信息"));

			// 分支机构信息
			List<Map<String, Object>> filiations = (List<Map<String, Object>>) sourceMap.get("filiations");
			StringBuffer fssb = new StringBuffer();
			if (filiations != null && filiations.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : filiations) {
					if (i != 0) {
						fssb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisefiliations001");
					fssb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(fssb.toString(), "servFormList", "分支机构信息"));

			// 股权出质历史信息
			List<Map<String, Object>> shareSimpaWns = (List<Map<String, Object>>) sourceMap.get("shareSimpaWns");
			StringBuffer sssb = new StringBuffer();
			if (shareSimpaWns != null && shareSimpaWns.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : shareSimpaWns) {
					if (i != 0) {
						sssb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisesharesimpawns001");
					sssb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(sssb.toString(), "servFormList", "股权出质历史信息"));

			// 动产抵押信息
			List<Map<String, Object>> morDetails = (List<Map<String, Object>>) sourceMap.get("morDetails");
			StringBuffer mdsb = new StringBuffer();
			if (morDetails != null && morDetails.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : morDetails) {
					if (i != 0) {
						mdsb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisemordetails001");
					mdsb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(mdsb.toString(), "servFormList", "动产抵押信息"));

			// 动产抵押物信息
			List<Map<String, Object>> morguaInfos = (List<Map<String, Object>>) sourceMap.get("morguaInfos");
			StringBuffer misb = new StringBuffer();
			if (morguaInfos != null && morguaInfos.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : morguaInfos) {
					if (i != 0) {
						misb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisemorguainfos001");
					misb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(misb.toString(), "servFormList", "动产抵押物信息"));

			// 失信被执行人信息
			List<Map<String, Object>> punishBreaks = (List<Map<String, Object>>) sourceMap.get("punishBreaks");
			StringBuffer pbsb = new StringBuffer();
			if (punishBreaks != null && punishBreaks.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : punishBreaks) {
					if (i != 0) {
						pbsb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisepunishbreaks001");
					pbsb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(pbsb.toString(), "servFormList", "失信被执行人信息"));

			// 被执行人信息
			List<Map<String, Object>> punished = (List<Map<String, Object>>) sourceMap.get("punished");
			StringBuffer pdsb = new StringBuffer();
			if (punished != null && punished.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : punished) {
					if (i != 0) {
						pdsb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisepunished001");
					pdsb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(pdsb.toString(), "servFormList", "被执行人信息"));

			// 股权冻结历史信息
			List<Map<String, Object>> sharesFrosts = (List<Map<String, Object>>) sourceMap.get("sharesFrosts");
			StringBuffer sfsb = new StringBuffer();
			if (sharesFrosts != null && sharesFrosts.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : sharesFrosts) {
					if (i != 0) {
						sfsb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisesharesfrosts001");
					sfsb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(sfsb.toString(), "servFormList", "股权冻结历史信息"));

			// 清算信息
			List<Map<String, Object>> liquidations = (List<Map<String, Object>>) sourceMap.get("liquidations");
			StringBuffer lssb = new StringBuffer();
			if (liquidations != null && liquidations.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : liquidations) {
					if (i != 0) {
						lssb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterpriseliquidations001");
					lssb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(lssb.toString(), "servFormList", "清算信息"));

			// 行政处罚历史信息
			List<Map<String, Object>> caseInfos = (List<Map<String, Object>>) sourceMap.get("caseInfos");
			StringBuffer cisb = new StringBuffer();
			if (caseInfos != null && caseInfos.size() > 0) {
				int i = 0;
				for (Map<String, Object> mm : caseInfos) {
					if (i != 0) {
						cisb.append("<div class='separatorLine'><hr></div>");
					}
					String lHtml = this.getFormStr(mm, "enterprisecaseinfos001");
					cisb.append(lHtml);
					i++;
				}
			}
			sb.append(FormBeanUtil.formatHtml(cisb.toString(), "servFormList", "行政处罚历史信息"));
		}

		return sb.toString();
	}

	/**
	 * 方法描述： 处理错误报告
	 * 
	 * @param errormessage
	 * @return String
	 * @author 仇招
	 * @date 2018-1-3 下午3:17:49
	 */
	public String errorHtml(String errormessage) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(errormessage)) {
			sb.append("<div class='servError'>" + errormessage + "</div>");
		} else {
			sb.append("<div class='servNodata'>暂无数据</div>");
		}
		return sb.toString();
	}

	/**
	 * 方法描述： 个人身份认证
	 * 
	 * @param dataMap
	 * @return String
	 * @author 仇招
	 * @date 2018-1-3 下午4:31:38
	 */
	public String authidIdentityVerifyHtml(Map<String, Object> dataMap) {
		if (dataMap != null && !dataMap.isEmpty()) {
			String str = this.getFormStr(dataMap, "idrz_output");
			return formatHtml(str, "servFormList", null);
		}
		return formatHtml(null, "servFormList", null);
	}

	/**
	 * 方法描述： 手机三要素
	 * 
	 * @param dataMap
	 * @return String
	 * @author 仇招
	 * @date 2018-1-4 下午3:01:04
	 */
	public String getQueryVerifyThreeHtml(Map<String, Object> dataMap) {
		if (dataMap != null && !dataMap.isEmpty()) {
			String str = this.getFormStr(dataMap, "verifyThree001");
			return formatHtml(str, "servFormList", null);
		}
		return formatHtml(null, "servFormList", null);
	}



	/**
	 * 上边框加线
	 * 
	 * @return
	 */
	public static String getSeparatorLine() {
		return "<div class=\"separatorLine\"><hr/></div>";
	}

	/**
	 * 个人工商综合信息
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public String getPersonalEnterpriseHtml(Map<String, Object> dataMap) throws Exception {
		String htmlStr = "";
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormService formService = new FormService();
		// 企业法人
		List<Map<String, String>> listData = null;
		listData = (List<Map<String, String>>) dataMap.get("corporate");
		String corporateStr = "";
		if (listData != null) {
			int len = listData.size();
			for (int i = 0; i < len; i++) {
				Map<String, String> mp = listData.get(i);
				FormData formcorporate001 = formService.getFormData("corporate001");
				getObjValue(formcorporate001, mp);
				corporateStr += jsonFormUtil.getJsonStr(formcorporate001, "formTag", "query");
				if (i != len - 1) {
					corporateStr += getSeparatorLine();
				}
			}
		}

		htmlStr += formatHtml(corporateStr, "servFormList", "企业法人信息");
		// 管理人员
		listData = (List<Map<String, String>>) dataMap.get("manager");
		String managerStr = "";
		if (listData != null) {
			int len = listData.size();
			for (int i = 0; i < len; i++) {
				Map<String, String> mp = listData.get(i);
				FormData formmanager001 = formService.getFormData("manager001");
				getObjValue(formmanager001, mp);
				managerStr += jsonFormUtil.getJsonStr(formmanager001, "formTag", "query");
				if (i != len - 1) {
					managerStr += getSeparatorLine();
				}
			}
		}
		htmlStr += formatHtml(managerStr, "servFormList", "企业主要管理人员信息");
		// 股东信息
		listData = (List<Map<String, String>>) dataMap.get("shareholder");
		String shareholderStr = "";
		if (listData != null) {
			int len = listData.size();
			for (int i = 0; i < len; i++) {
				Map<String, String> mp = listData.get(i);
				FormData formshareholder001 = formService.getFormData("shareholder002");
				getObjValue(formshareholder001, mp);
				shareholderStr += jsonFormUtil.getJsonStr(formshareholder001, "formTag", "query");
				if (i != len - 1) {
					shareholderStr += getSeparatorLine();
				}
			}
		}
		htmlStr += formatHtml(shareholderStr, "servFormList", "企业股东信息");
		return htmlStr;
	}

	/**
	 * 企业裁判文书解析摘要
	 * 
	 * @param dataMap
	 * @return
	 */
	public String getEntJudgAbstractApiHzHtml(Map<String, Object> dataMap) throws Exception {
		String htmlStr = "";
		List<Map<String, String>> listData = null;
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormService formService = new FormService();
		// 裁判文书
		String htmlStrCpws = "";
		listData = (List<Map<String, String>>) dataMap.get("listData");
		String status = (String) dataMap.get("status");
		if ("EXIST".equals(status) && listData != null && listData.size() > 0) {
			int len = listData.size();
			for (int i = 0; i < len; i++) {
				Map<String, String> mp = listData.get(i);
				FormData formentcpws = formService.getFormData("entcpws");
				getObjValue(formentcpws, mp);
				htmlStrCpws += jsonFormUtil.getJsonStr(formentcpws, "formTag", "query");

				if (i != len - 1) {
					htmlStrCpws += FormBeanUtil.getSeparatorLine();
				}
			}

		}
		htmlStr += formatHtml(htmlStrCpws, "", "");
		return htmlStr;

	}

	/**
	 * 个人裁判文书解析摘要
	 * 
	 * @param report
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getqueryPersonJudgAbstractHtml(Map<String, Object> dataMap) throws Exception {
		String htmlStr = "";
		List<Map<String, String>> listData = null;
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormService formService = new FormService();
		// 裁判文书
		String htmlStrCpws = "";
		listData = (List<Map<String, String>>) dataMap.get("listData");
		String status = (String) dataMap.get("status");
		if ("EXIST".equals(status) && listData != null && listData.size() > 0) {
			int len = listData.size();
			for (int i = 0; i < len; i++) {
				Map<String, String> mp = listData.get(i);
				FormData formcpws001 = formService.getFormData("cpws001");
				getObjValue(formcpws001, mp);
				htmlStrCpws += jsonFormUtil.getJsonStr(formcpws001, "formTag", "query");

				if (i != len - 1) {
					htmlStrCpws += FormBeanUtil.getSeparatorLine();
				}
			}

		}
		htmlStr += formatHtml(htmlStrCpws, "", "");
		return htmlStr;
	}

	/**
	 * 企业风险信息
	 * 
	 * @param sourceMap
	 *            数据源
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getEnterpriseRiskInfoHtml(Map<String,Object> sourceMap) throws Exception{
		StringBuffer sb=new StringBuffer();
		List<Map<String,Object>> entData= (List<Map<String, Object>>) sourceMap.get("listData");
		StringBuffer ssb=new StringBuffer();
		if(entData!=null&&entData.size()>0){
			List<Map<String,Object>> allList =(List<Map<String, Object>>)entData.get(0).get("allList");
			if(allList!=null&&allList.size()>0){
				//裁判文书
				StringBuffer cpwssb=new StringBuffer();

				int i = 0; 
				for(Map<String,Object> mm:allList){
					if("cpws".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						if(i != 0){
							cpwssb.append(FormBeanUtil.getSeparatorLine());
						}

						String sHtml = this.getFormStr(mm, "cpws");
						cpwssb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(cpwssb.toString(), "servFormList", "裁判文书"));
			}	
			if (allList != null && allList.size() > 0) {
				StringBuffer ktggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("ktgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						if (i != 0) {
							ktggsb.append("<div class='separatorLine'><hr></div>");
						}

						String sHtml = this.getFormStr(mm, "ktgg");
						ktggsb.append(sHtml);

						i++;
					}
				}
				sb.append(FormBeanUtil.formatHtml(ktggsb.toString(), "servFormList", "开庭公告"));
			}
			// 执行公告
			if (allList != null && allList.size() > 0) {
				StringBuffer zzggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("zxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						if (i != 0) {
							zzggsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml = this.getFormStr(mm, "entzxgg");
						zzggsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(zzggsb.toString(), "servFormList", "执行公告"));
			}
			// 失信公告
			if (allList != null && allList.size() > 0) {
				StringBuffer sxggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("sxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						if (i != 0) {
							sxggsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml = this.getFormStr(mm, "entsxgg");
						sxggsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(sxggsb.toString(), "servFormList", "失信公告"));
			}
			// 法院公告
			if (allList != null && allList.size() > 0) {
				StringBuffer fyggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("fygg".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						if (i != 0) {
							fyggsb.append("<div class='separatorLine'><hr></div>");
						}

						String sHtml = this.getFormStr(mm, "entfygg");
						fyggsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(fyggsb.toString(), "servFormList", "法院公告"));
			}
			// 网贷黑名单
			if (allList != null && allList.size() > 0) {
				StringBuffer wdhmdsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("wdhmd".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						if (i != 0) {
							wdhmdsb.append("<div class='separatorLine'><hr></div>");
						}

						String sHtml = this.getFormStr(mm, "entwdhmd");
						wdhmdsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(wdhmdsb.toString(), "servFormList", "网贷黑名单"));
			}
			// 案件流程信息
			if (allList != null && allList.size() > 0) {
				StringBuffer ajlcsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("ajlc".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						if (i != 0) {
							ajlcsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml = this.getFormStr(mm, "entajlc");
						ajlcsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(ajlcsb.toString(), "servFormList", "案件流程信息"));
			}
			// 曝光台
			if (allList != null && allList.size() > 0) {
				StringBuffer bgtsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("bgt".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						if (i != 0) {
							bgtsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml = this.getFormStr(mm, "entbgt");
						bgtsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(bgtsb.toString(), "servFormList", "曝光台"));
			}
			}
		else{
			
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "裁判文书"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "开庭公告"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "执行公告"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "失信公告"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "法院公告"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "网贷黑名单"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "案件流程信息"));
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "曝光台"));
		}	
		return sb.toString();
}
	
	/**
	 * 	个人风险信息
	 * @param sourceMap 数据源
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getUnifyPersonRiskInfoHtml(Map<String,Object> sourceMap) throws Exception{
		StringBuffer sb=new StringBuffer();
		StringBuffer ssb=new StringBuffer();
		List<Map<String,Object>> perDate= (List<Map<String, Object>>) sourceMap.get("listData");
		if(perDate!=null&&perDate.size()>0){
			List<Map<String,Object>> allList =(List<Map<String, Object>>)perDate.get(0).get("allList");
			if(allList!=null&&allList.size()>0){
				//裁判文书
				StringBuffer cpwssb=new StringBuffer();
				
				int i = 0; 
				for(Map<String,Object> mm:allList){
					if("cpws".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						if(i != 0){
							cpwssb.append(FormBeanUtil.getSeparatorLine());
						}
						
						String sHtml=this.getFormStr(mm, "percpws");
						cpwssb.append(sHtml);
						i++;
					}
					
					
				}
				sb.append(FormBeanUtil.formatHtml(cpwssb.toString(), "servFormList", "裁判文书"));
			}
			//开庭公告
			if(allList!=null&&allList.size()>0){
				StringBuffer ktggsb=new StringBuffer();
				int i=0;
				for(Map<String,Object> mm:allList){
					if("ktgg".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						if(i != 0){
							ktggsb.append("<div class='separatorLine'><hr></div>");
						}
						
						String sHtml=this.getFormStr(mm, "perktgg");
						ktggsb.append(sHtml);
						
						i++;
					}
				}
				sb.append(FormBeanUtil.formatHtml(ktggsb.toString(), "servFormList", "开庭公告"));
			}
				//执行公告
			if(allList!=null&&allList.size()>0){
				StringBuffer zzggsb=new StringBuffer();
				int i=0;
				for(Map<String,Object> mm:allList){
					if("zxgg".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						if(i != 0){
							zzggsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml=this.getFormStr(mm, "perzxgg");
						zzggsb.append(sHtml);
						i++;
					}
					
				}
				sb.append(FormBeanUtil.formatHtml(zzggsb.toString(), "servFormList", "执行公告"));
			}
				//失信公告
			if(allList!=null&&allList.size()>0){
				StringBuffer sxggsb=new StringBuffer();
				int i=0;
				for(Map<String,Object> mm:allList){
					if("sxgg".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						
						if(i != 0){
							sxggsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml=this.getFormStr(mm, "persxgg");
						sxggsb.append(sHtml);
						i++;
					}
					
				}
				sb.append(FormBeanUtil.formatHtml(sxggsb.toString(), "servFormList", "失信公告"));
			}
				//法院公告
			if(allList!=null&&allList.size()>0){
				StringBuffer fyggsb=new StringBuffer();
				int i =0;
				for(Map<String,Object> mm:allList){
					if("fygg".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						if(i != 0){
							fyggsb.append("<div class='separatorLine'><hr></div>");
						}
						
						String sHtml=this.getFormStr(mm, "perfygg");
						fyggsb.append(sHtml);
						i++;
					}
					
				}
				sb.append(FormBeanUtil.formatHtml(fyggsb.toString(), "servFormList", "法院公告"));
			}
				//网贷黑名单
			if(allList!=null&&allList.size()>0){
				StringBuffer wdhmdsb=new StringBuffer();
				int i =0;
				for(Map<String,Object> mm:allList){
					if("wdhmd".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						if(i != 0){
							wdhmdsb.append("<div class='separatorLine'><hr></div>");
						}
						
						String sHtml=this.getFormStr(mm, "perwdhmd");
						wdhmdsb.append(sHtml);
						i++;
					}
					
				}
				sb.append(FormBeanUtil.formatHtml(wdhmdsb.toString(), "servFormList", "网贷黑名单"));
			}
				//案件流程信息
			if(allList!=null&&allList.size()>0){
				StringBuffer ajlcsb=new StringBuffer();
				int i=0;
				for(Map<String,Object> mm:allList){
					if("ajlc".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						
						if(i != 0){
							ajlcsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml=this.getFormStr(mm, "perajlc");
						ajlcsb.append(sHtml);
						i++;
					}
					
					
					
				}
				sb.append(FormBeanUtil.formatHtml(ajlcsb.toString(), "servFormList", "案件流程信息"));
			}
				//曝光台
			if(allList!=null&&allList.size()>0){
				StringBuffer bgtsb=new StringBuffer();
				int i =0;
				for(Map<String,Object> mm:allList){
					if("bgt".equals(mm.get("dataType"))&&mm.get("dataType")!=null){
						
						if(i != 0){
							bgtsb.append("<div class='separatorLine'><hr></div>");
						}
						String sHtml=this.getFormStr(mm, "perbgt");
						bgtsb.append(sHtml);
						i++;
					}
					
				}
				sb.append(FormBeanUtil.formatHtml(bgtsb.toString(), "servFormList", "曝光台"));
			}
			}
			else{
			
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "裁判文书"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "开庭公告"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "执行公告"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "失信公告"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "法院公告"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "网贷黑名单"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "案件流程信息"));
					sb.append(FormBeanUtil.formatHtml(ssb.toString(), "servFormList", "曝光台"));
			}
		
		return sb.toString();
	}

	/**
	 * 方法描述： 用户银行画像
	 * @param dataMap
	 * @return
	 * String
	 * @author 仇招
	 * @date 2018-1-6 下午7:34:51
	 */
	@SuppressWarnings("unchecked")
	public String getBankCardPortrayalHtml(Map<String, Object> dataMap) {
		StringBuilder htmlStr = new StringBuilder();
		htmlStr.append("<div class='servFormList'><div class='servListCont'><table id='bankCardPortrayal' class='from_w' title='formidrz_output' align='center' width='100%' cellspacing='0' cellpadding='0' border='0'>");
		List<Map<String, String>> listData = null;
		listData = (List<Map<String, String>>) dataMap.get("listData");
		String status = (String) dataMap.get("status");
		if ("EXIST".equals(status) && listData != null && listData.size() > 0) {
			CodeUtils cu = new CodeUtils();
			try {
				Map<String, String> map = cu.getMapByKeyName("SEARCH_NO");
				Map<String, String> listMap = (Map<String, String>) listData.get(0);
				Iterator<Map.Entry<String, String>> it = listMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = it.next();
					if(map.get(entry.getKey()) != null && !"".equals(map.get(entry.getKey()))){
						htmlStr.append("<tr>");
						htmlStr.append("<td class='tdlable' align='right'>" + map.get(entry.getKey()) + "&nbsp;</td>");
						htmlStr.append("<td class='tdvalue' align='left' style='word-wrap:break-word;'>" + entry.getValue() + "</td>");
						htmlStr.append("</tr>");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		htmlStr.append("</table></div></div>");
		return htmlStr.toString();
	}
	/**
	 * 方法描述： 贷款轨迹
	 * @param sourceMap
	 * @return
	 * String
	 * @author 仇招
	 * @date 2018-1-6 下午7:33:44
	 */
	@SuppressWarnings("unchecked")
	public String loanTrackHtml(Map<String, Object> sourceMap) {
		StringBuffer sb = new StringBuffer();
		if (sourceMap != null) {
			List<Map<String, Object>> ktgg = (List<Map<String, Object>>) sourceMap.get("listData");
			List<Map<String, Object>> allList = (List<Map<String, Object>>) ktgg.get(0).get("allList");
			if (allList != null && allList.size() > 0) {
				// 贷款申请详请查询
				StringBuffer cpwssb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("EMR004".equals(mm.get("type")) && mm.get("type") != null) {
						if (i != 0) {
							cpwssb.append(FormBeanUtil.getSeparatorLine());
						}
						Map<String ,String> map = (Map<String ,String>)mm.get("data");
						mm.put("emr004Type", "1".equals(map.get("type")) ? "银行":"非银行");
						mm.put("platformCode", map.get("platformCode"));
						mm.put("applicationTime", map.get("applicationTime"));
						mm.put("applicationAmount", map.get("applicationAmount"));
						String sHtml = this.getFormStr(mm, "loantrackemr004");
						cpwssb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(cpwssb.toString(), "servFormList", "贷款申请详请"));
			}
			//贷款放款详情
			if (allList != null && allList.size() > 0) {
				StringBuffer ktggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("EMR009".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						if (i != 0) {
							ktggsb.append("<div class='separatorLine'><hr></div>");
						}
						Map<String ,String> map = (Map<String ,String>)mm.get("data");
						mm.put("emr007Type", "1".equals(map.get("type")) ? "银行":"非银行");
						mm.put("platformCode", map.get("platformCode"));
						mm.put("loanLendersTime", map.get("loanLendersTime"));
						mm.put("loanLendersAmount", map.get("loanLendersAmount"));
						String sHtml = this.getFormStr(mm, "loantrackemr007");
						ktggsb.append(sHtml);

						i++;
					}
				}
				sb.append(FormBeanUtil.formatHtml(ktggsb.toString(), "servFormList", "贷款放款详情"));
			}
			// 贷款驳回详情
			if (allList != null && allList.size() > 0) {
				StringBuffer zzggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("zxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						if (i != 0) {
							zzggsb.append("<div class='separatorLine'><hr></div>");
						}
						Map<String ,String> map = (Map<String ,String>)mm.get("data");
						mm.put("emr009Type", "1".equals(map.get("type")) ? "银行":"非银行");
						mm.put("platformCode", map.get("platformCode"));
						mm.put("rejectionTime", map.get("rejectionTime"));
						String sHtml = this.getFormStr(mm, "loantrackemr009");
						zzggsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(zzggsb.toString(), "servFormList", "贷款驳回详情"));
			}
			// 逾期平台详情查询
			if (allList != null && allList.size() > 0) {
				StringBuffer sxggsb = new StringBuffer();
				int i = 0;
				for (Map<String, Object> mm : allList) {
					if ("sxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						if (i != 0) {
							sxggsb.append("<div class='separatorLine'><hr></div>");
						}
						Map<String ,String> map = (Map<String ,String>)mm.get("data");
						mm.put("emr012Type", "1".equals(map.get("type")) ? "银行":"非银行");
						mm.put("platformCode", map.get("platformCode"));
						mm.put("counts", map.get("counts"));
						mm.put("money ", map.get("money "));
						mm.put("time ", map.get("time "));
						String sHtml = this.getFormStr(mm, "loantrackemr012");
						sxggsb.append(sHtml);
						i++;
					}

				}
				sb.append(FormBeanUtil.formatHtml(sxggsb.toString(), "servFormList", "逾期平台详情"));
			}
		}
		return sb.toString();
	}
	/**
	 * 方法描述： 企业涉诉案件详情
	 * @param sourceMap
	 * @return
	 * String
	 * @author 刘东迎
	 * @date 2018年1月8日14:47:20
	 */
	@SuppressWarnings("unchecked")
	public String highCourtCaseEParmsHtml(Map<String,Object> sourceMap) throws Exception{
		StringBuffer sb=new StringBuffer();
		List<Map<String,Object>> entData= (List<Map<String, Object>>) sourceMap.get("listData");
		StringBuffer ssb=new StringBuffer();
		if(entData!=null&&entData.size()>0){
			List<Map<String,Object>> allList =(List<Map<String, Object>>)entData.get(0).get("data");
			if(allList!=null&&allList.size()>0){
				//裁判文书
				StringBuffer cpwssb=new StringBuffer();
				for(Map<String,Object> mm:allList){
					if("cpws".equals(mm.get("dataType"))&&mm.get("dataType")!=null){

						String sHtml = this.getFormStr(mm, "entivnocpws");
						cpwssb.append(sHtml);
					}
					if("parytsTitle".equals(mm.get("parytsTitle"))&&mm.get("parytsTitle")!=null){
						List<Map<String,Object>> parytsTitle =(List<Map<String, Object>>)mm.get("parytsTitle");
						parytsTitle.get(0).put("parytsTitle", parytsTitle.get(0).get("title"));
						for(Map<String,Object> m1:parytsTitle){
							String sHtml = this.getFormStr(m1, "entivnocpws");
							cpwssb.append(sHtml);
						}
					}

				}
				sb.append(FormBeanUtil.formatHtml(cpwssb.toString(), "servFormList", "裁判文书"));
				
			}
			//开庭公告
			if (allList != null && allList.size() > 0) {
				StringBuffer ktggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("ktgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "entivnoktgg");
						ktggsb.append(sHtml);

					}
				}
				sb.append(FormBeanUtil.formatHtml(ktggsb.toString(), "servFormList", "开庭公告"));
			}
			// 执行公告
			if (allList != null && allList.size() > 0) {
				StringBuffer zzggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("zxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						String sHtml = this.getFormStr(mm, "entivnozxgg");
						zzggsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(zzggsb.toString(), "servFormList", "执行公告"));
			}
			// 失信公告
			if (allList != null && allList.size() > 0) {
				StringBuffer sxggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("sxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "entinvosxgg");
						sxggsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(sxggsb.toString(), "servFormList", "失信公告"));
			}
			// 法院公告
			if (allList != null && allList.size() > 0) {
				StringBuffer fyggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("fygg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "entivnofygg");
						fyggsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(fyggsb.toString(), "servFormList", "法院公告"));
			}
			// 案件流程信息
			if (allList != null && allList.size() > 0) {
				StringBuffer ajlcsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("ajlc".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "entivnoajlc");
						ajlcsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(ajlcsb.toString(), "servFormList", "案件流程信息"));
			}
			// 曝光台
			if (allList != null && allList.size() > 0) {
				StringBuffer bgtsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("bgt".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "entinvobgt");
						bgtsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(bgtsb.toString(), "servFormList", "曝光台"));
			}
			}
		else{
			
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "", ""));
			
		}	
		return sb.toString();
	}
	/**
	 * 方法描述：手机二要素 
	 * @param dataMap
	 * @return
	 * String
	 * @author 仇招
	 * @date 2018-1-8 下午5:25:23
	 */
	public String getMobileVerifyTwoHtml(Map<String, Object> dataMap) {
		if (dataMap != null && !dataMap.isEmpty()) {
			String str = this.getFormStr(dataMap, "verifyTwo001");
			return formatHtml(str, "servFormList", null);
		}
		return formatHtml(null, "servFormList", null);
	}

	/**
	 * 方法描述： 个人涉诉案件详情
	 * @param sourceMap
	 * @return
	 * String
	 * @author 刘东迎
	 * @date 2018年1月8日14:47:20
	 */
	@SuppressWarnings("unchecked")
	public String highCourtCasePParmsHtml(Map<String,Object> sourceMap) throws Exception{
		StringBuffer sb=new StringBuffer();
		List<Map<String,Object>> entData= (List<Map<String, Object>>) sourceMap.get("listData");
		StringBuffer ssb=new StringBuffer();
		if(entData!=null&&entData.size()>0){
			List<Map<String,Object>> allList =(List<Map<String, Object>>)entData.get(0).get("data");
			if(allList!=null&&allList.size()>0){
				//裁判文书
				StringBuffer cpwssb=new StringBuffer();
				for(Map<String,Object> mm:allList){
					if("cpws".equals(mm.get("dataType"))&&mm.get("dataType")!=null){

						String sHtml = this.getFormStr(mm, "perivnocpws");
						cpwssb.append(sHtml);
					}
					if("parytsTitle".equals(mm.get("parytsTitle"))&&mm.get("parytsTitle")!=null){
						List<Map<String,Object>> parytsTitle =(List<Map<String, Object>>)mm.get("parytsTitle");
						parytsTitle.get(0).put("parytsTitle", parytsTitle.get(0).get("title"));
						for(Map<String,Object> m1:parytsTitle){
							String sHtml = this.getFormStr(m1, "perivnocpws");
							cpwssb.append(sHtml);
						}
					}

				}
				sb.append(FormBeanUtil.formatHtml(cpwssb.toString(), "servFormList", "裁判文书"));
				
			}
			//开庭公告
			if (allList != null && allList.size() > 0) {
				StringBuffer ktggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("ktgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "perivnoktgg");
						ktggsb.append(sHtml);

					}
				}
				sb.append(FormBeanUtil.formatHtml(ktggsb.toString(), "servFormList", "开庭公告"));
			}
			// 执行公告
			if (allList != null && allList.size() > 0) {
				StringBuffer zzggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("zxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {
						String sHtml = this.getFormStr(mm, "perivnozxgg");
						zzggsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(zzggsb.toString(), "servFormList", "执行公告"));
			}
			// 失信公告
			if (allList != null && allList.size() > 0) {
				StringBuffer sxggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("sxgg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "perinvosxgg");
						sxggsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(sxggsb.toString(), "servFormList", "失信公告"));
			}
			// 法院公告
			if (allList != null && allList.size() > 0) {
				StringBuffer fyggsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("fygg".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "perivnofygg");
						fyggsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(fyggsb.toString(), "servFormList", "法院公告"));
			}
			// 案件流程信息
			if (allList != null && allList.size() > 0) {
				StringBuffer ajlcsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("ajlc".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "perivnoajlc");
						ajlcsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(ajlcsb.toString(), "servFormList", "案件流程信息"));
			}
			// 曝光台
			if (allList != null && allList.size() > 0) {
				StringBuffer bgtsb = new StringBuffer();
				for (Map<String, Object> mm : allList) {
					if ("bgt".equals(mm.get("dataType")) && mm.get("dataType") != null) {

						String sHtml = this.getFormStr(mm, "perinvobgt");
						bgtsb.append(sHtml);
					}

				}
				sb.append(FormBeanUtil.formatHtml(bgtsb.toString(), "servFormList", "曝光台"));
			}
			}
		else{
			
			sb.append(FormBeanUtil.formatHtml(ssb.toString(), "", ""));
			
		}	
		return sb.toString();
}
	/**
	 * 方法描述： GPS查询
	 * 
	 * @param dataMap
	 * @return String
	 * @author 刘东迎
	 * @date 2018年1月12日12:00:29
	 */
	public String getsimpleObjectTracksHtml(Map<String, Object> dataMap) {
		if (dataMap != null && !dataMap.isEmpty()) {
			String str = this.getFormStr(dataMap, "gpsquery");
			return formatHtml(str, "servFormList", null);
		}
		return formatHtml(null, "servFormList", null);
	}
	/*
	 * 方法描述： 个人名下企业(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getPersonalCorpToHtml(Map<String, Object> dataMap) throws  Exception{

		StringBuffer sb=new StringBuffer();
		if(dataMap!=null){
			List<Map<String,Object>> corporate = (List<Map<String,Object>>)dataMap.get("corporate");//法人信息
			List<Map<String,Object>> shareholder = (List<Map<String,Object>>)dataMap.get("shareholder");//股东信息
			List<Map<String,Object>> manager = (List<Map<String,Object>>)dataMap.get("manager");//企业管理人员
			formService = new FormService();
			FormData formapply0007_query = null;
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			//法人信息
			if(corporate!=null&&corporate.size()>0){
				int i = 1;
				for(Map<String,Object> corporate_one:corporate){
					formapply0007_query = formService.getFormData("mfcuspersonalcorp02");
					getObjValue(formapply0007_query, corporate_one);
					sb.append("<div class='doc-upload-little-title'>法人信息</div>");
					sb.append("<form method=\"post\" id=\"PersonalCorpCorporateForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
					sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
					sb.append("</form>");
					i++;
				}
			}else{
				sb.append("<div class='doc-upload-little-title'>法人信息</div>");
				sb.append("<form method=\"post\" id=\"personalCorpCorporateErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append("<div class='court-upload-record'>暂无数据</div>");
				sb.append("</form>");
			}
			//股东信息
			if(shareholder!=null&&shareholder.size()>0){
				int i = 1;
				for(Map<String,Object> shareholder_one:shareholder){
					formapply0007_query = formService.getFormData("mfcuspersonalcorp02");
					getObjValue(formapply0007_query, shareholder_one);
					sb.append("<div class='doc-upload-little-title'>股东信息</div>");
					sb.append("<form method=\"post\" id=\"PersonalCorpShareholderForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
					sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
					sb.append("</form>");
					i++;
				}

			}else{
				sb.append("<div class='doc-upload-little-title'>股东信息</div>");
				sb.append("<form method=\"post\" id=\"personalCorpShareholderErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append("<div class='court-upload-record'>暂无数据</div>");
				sb.append("</form>");
			}
			//企业管理人员
			if(manager!=null&&manager.size()>0){
				int i = 1;
				for(Map<String,Object> manager_one:manager){
					formapply0007_query = formService.getFormData("mfcuspersonalcorp02");
					getObjValue(formapply0007_query, manager_one);
					sb.append("<div class='doc-upload-little-title'>企业管理人员</div>");
					sb.append("<form method=\"post\" id=\"PersonalCorpManageForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
					sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
					sb.append("</form>");
					i++;
				}
			}else{
				sb.append("<div class='doc-upload-little-title'>企业管理人员</div>");
				sb.append("<form method=\"post\" id=\"personalCorpManageErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append("<div class='court-upload-record'>暂无数据</div>");
				sb.append("</form>");
			}

		}else{
			sb.append("<div class='doc-upload-little-title'>个人名下企业</div>");
			sb.append("<form method=\"post\" id=\"personalCorpForm01\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		return sb.toString();
	}

/*
 * 方法描述： 身份认证返照片(信息页面)
 * @param dataMap
 * @return
 * String
 * @author 吕春杰
 * @date 2018年8月30日16:50:20
 */
public String getIdAttestationHtml(Map<String, Object> dataMap) throws Exception{
	StringBuffer sb=new StringBuffer();
	formService = new FormService();
	FormData formapply0007_query = null;
	JsonFormUtil jsonFormUtil = new JsonFormUtil();
	if (dataMap != null && !dataMap.isEmpty()) {
		formapply0007_query = formService.getFormData("mfcusidattestation");
		getObjValue(formapply0007_query, dataMap);
		sb.append("<div class='doc-upload-little-title'>身份认证</div>");
		sb.append("<form method=\"post\" id=\"idAttestationForm\" theme=\"simple\" name=\"operform\" action=\"\">");
		sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
		sb.append("</form>");

	}else{
		sb.append("<div class='doc-upload-little-title'>身份认证</div>");
		sb.append("<form method=\"post\" id=\"idAttestationErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
		sb.append("<div class='court-upload-record'>暂无数据</div>");
		sb.append("</form>");
	}
	return sb.toString();
}
	/*
	 * 方法描述： 户籍信息查询(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getCensusDataQueryHtml(Map<String, Object> dataMap) throws  Exception{
		StringBuffer sb=new StringBuffer();
		formService = new FormService();
		FormData formapply0007_query = null;
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		if (dataMap != null && !dataMap.isEmpty()) {
			formapply0007_query = formService.getFormData("mfcuscensusdata");
			getObjValue(formapply0007_query, dataMap);
			sb.append("<div class='doc-upload-little-title'>户籍信息查询</div>");
			sb.append("<form method=\"post\" id=\"censusDataQueryForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
			sb.append("</form>");
		}else{
			sb.append("<div class='doc-upload-little-title'>户籍信息查询</div>");
			sb.append("<form method=\"post\" id=\"censusDataQueryErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		return sb.toString();
	}
	/*
	 * 方法描述： 不良信息查询(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getBadInfoQueryHtml(Map<String, Object> dataMap) throws  Exception{
		StringBuffer sb=new StringBuffer();
		formService = new FormService();
        FormData formapply0007_query = null;
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
		if (dataMap != null && !dataMap.isEmpty()) {
            formapply0007_query = formService.getFormData("mfcusbadinfo");
            getObjValue(formapply0007_query, dataMap);
			sb.append("<div class='doc-upload-little-title'>不良信息</div>");
			sb.append("<form method=\"post\" id=\"badInfoQueryForm\" theme=\"simple\" name=\"operform\" action=\"\">");
            sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
			sb.append("</form>");
		}else{
            sb.append("<div class='doc-upload-little-title'>不良信息</div>");
            sb.append("<form method=\"post\" id=\"badInfoQueryErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
            sb.append("<div class='court-upload-record'>暂无数据</div>");
            sb.append("</form>");
        }
		return sb.toString();
	}
	/*
	 * 方法描述： 企业涉诉高法全类(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getCorpLawsuitsAllClassHtml(Map<String, Object> dataMap) throws  Exception{
		StringBuffer sb=new StringBuffer();

		Map<String,Object> cpws = (Map<String,Object>)dataMap.get("cpws");//裁判文书
		List<Map<String,Object>> cpwslist = (List<Map<String,Object>>)cpws.get("listData");
		//裁判文书
		formService = new FormService();
		FormData formapply0007_query = null;
        JsonFormUtil jsonFormUtil = new JsonFormUtil();
		StringBuilder stringBuilder = new StringBuilder();
		if(cpwslist!=null&&cpwslist.size()>0){
			StringBuffer cpwssb=new StringBuffer();
			int i = 1;
			for(Map<String,Object> cpwslist_one:cpwslist){

				formapply0007_query = null;
				formapply0007_query = formService.getFormData("mfcuscorplawsuitsallclass02");
				getObjValue(formapply0007_query, cpwslist_one);
				sb.append("<div class='doc-upload-little-title'>裁判文书</div>");
				sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassCpwsForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
				sb.append("</form>");
				i++;
			}
		}else{
			sb.append("<div class='doc-upload-little-title'>裁判文书</div>");
			sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassCpwsErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		Map<String,Object> zxgg = (Map<String,Object>)dataMap.get("zxgg");//执行公告
		List<Map<String,Object>> zxgglist = (List<Map<String,Object>>)zxgg.get("listData");
		//执行公告
		if(zxgglist!=null&&zxgglist.size()>0){
			StringBuffer zxggsb=new StringBuffer();
			int i = 1;
			for(Map<String,Object> zxgglist_one:zxgglist){

				formapply0007_query = formService.getFormData("mfcuscorplawsuitsallclass02");
				getObjValue(formapply0007_query, zxgglist_one);
				sb.append("<div class='doc-upload-little-title'>执行公告</div>");
				sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassZxggForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
				sb.append("</form>");
				i++;
			}

		}else{
			sb.append("<div class='doc-upload-little-title'>执行公告</div>");
			sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassZxggErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		Map<String,Object> sxgg = (Map<String,Object>)dataMap.get("sxgg");//失信公告
		List<Map<String,Object>> sxgglist = (List<Map<String,Object>>)sxgg.get("listData");
		//失信公告
		if(sxgglist!=null&&sxgglist.size()>0){
			StringBuffer sxggsb=new StringBuffer();
			int i = 1;
			for(Map<String,Object> sxgglist_one:sxgglist){

				formapply0007_query = formService.getFormData("mfcuscorplawsuitsallclass02");
				getObjValue(formapply0007_query, sxgglist_one);
				sb.append("<div class='doc-upload-little-title'>失信公告</div>");
				sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassSxggForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
				sb.append("</form>");
				i++;
			}
		}else{
			sb.append("<div class='doc-upload-little-title'>失信公告</div>");
			sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassSxggErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		Map<String,Object> fygg = (Map<String,Object>)dataMap.get("fygg");//法院公告
		List<Map<String,Object>> fygglist = (List<Map<String,Object>>)fygg.get("listData");
		//法院公告
		if(fygglist!=null&&fygglist.size()>0){
			StringBuffer fyggsb=new StringBuffer();
			int i = 1;
			for(Map<String,Object> fygglist_one:fygglist){

				formapply0007_query = formService.getFormData("mfcuscorplawsuitsallclass02");
				getObjValue(formapply0007_query, fygglist_one);
				sb.append("<div class='doc-upload-little-title'>法院公告</div>");
				sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassFyggForm\""+i+" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
				sb.append("</form>");
				i++;

			}

		}else{
			sb.append("<div class='doc-upload-little-title'>法院公告</div>");
			sb.append("<form method=\"post\" id=\"corpLawsuitsAllClassFyggErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}

		return sb.toString();
	}
	/*
	 * 方法描述： 多重借贷查询(暂无服务)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getMultipleLoansQueryHtml(Map<String, Object> dataMap){
		StringBuffer sb=new StringBuffer();
		if (dataMap != null && !dataMap.isEmpty()) {
			List<Map<String,Object>> listData= (List<Map<String, Object>>) dataMap.get("listData");
			if(listData != null && listData.size() > 0){
				StringBuffer sb1=new StringBuffer();
				for (Map<String, Object> jdxx : listData) {
					String sHtml = this.getFormStr(jdxx, "mfcusmultipleloans");
					sb1.append(sHtml);
				}
				sb.append( FormBeanUtil.formatHtml(sb1.toString(), "servFormList", "多重借贷"));
			}


		}
		return sb.toString();
	}
	/*
	 * 方法描述： 企业工商数据查询(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getCorpICDataHtml(Map<String, Object> dataMap) throws Exception{
		StringBuffer sb=new StringBuffer();
		formService = new FormService();
		FormData formapply0007_query = null;
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		Map<String, Object> resultMap = (Map<String, Object>)dataMap.get("result");
		if (dataMap != null && !dataMap.isEmpty()) {
			formapply0007_query = formService.getFormData("mfcuscorpicdata");
			getObjValue(formapply0007_query, resultMap);
			sb.append("<div class='doc-upload-little-title'>企业工商数据</div>");
			sb.append("<form method=\"post\" id=\"corpICDataForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
			sb.append("</form>");

		}else{
			sb.append("<div class='doc-upload-little-title'>企业工商数据</div>");
			sb.append("<form method=\"post\" id=\"corpICDataErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		return sb.toString();
	}
	/*
	 * 方法描述： 失信黑名单查询(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getLoseCreditBlacklistHtml(Map<String, Object> dataMap){
		StringBuffer sb=new StringBuffer();
		if (dataMap != null && !dataMap.isEmpty()) {
			List<Map<String,Object>> listData= (List<Map<String, Object>>) dataMap.get("listData");
			if(listData != null && listData.size() > 0){
				StringBuffer sb1=new StringBuffer();
				for (Map<String, Object> sxxx : listData) {
					String sHtml = this.getFormStr(sxxx, "mfcuslosecreditblacklist");
					sb1.append(sHtml);
				}
				sb.append( FormBeanUtil.formatHtml(sb1.toString(), "servFormList", "失信黑名单"));
			}

		}
		return sb.toString();
	}

	/*
	 * 方法描述： 失信被执行人信息查询(信息页面)
	 * @param dataMap
	 * @return
	 * String
	 * @author 吕春杰
	 * @date 2018年8月30日16:50:20
	 */
	public String getLoseCerditBZXRInfoHtml(Map<String, Object> dataMap) throws Exception{
		StringBuffer sb=new StringBuffer();
		formService = new FormService();
		FormData formapply0007_query = null;
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		if (dataMap != null && !dataMap.isEmpty()) {
			List<Map<String,Object>> listData= (List<Map<String, Object>>) dataMap.get("listData");
			if(listData != null && listData.size() > 0){
				StringBuffer sb1=new StringBuffer();
				for (Map<String, Object> sxxx : listData) {
					formapply0007_query = formService.getFormData("mfcuslosecerditbzxrinfo");
					getObjValue(formapply0007_query, sxxx);
					sb.append("<div class='doc-upload-little-title'>失信被执行人信息</div>");
					sb.append("<form method=\"post\" id=\"loseCerditBZXRInfoForm\" theme=\"simple\" name=\"operform\" action=\"\">");
					sb.append(jsonFormUtil.getJsonStr(formapply0007_query,"bootstarpTag",""));
					sb.append("</form>");

				}
			}else{
				sb.append("<div class='doc-upload-little-title'>失信被执行人信息</div>");
				sb.append("<form method=\"post\" id=\"loseCerditBZXRInfoErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
				sb.append("<div class='court-upload-record'>暂无数据</div>");
				sb.append("</form>");
			}

		}else{
			sb.append("<div class='doc-upload-little-title'>失信被执行人信息查询</div>");
			sb.append("<form method=\"post\" id=\"loseCerditBZXRInfoErrorForm\" theme=\"simple\" name=\"operform\" action=\"\">");
			sb.append("<div class='court-upload-record'>暂无数据</div>");
			sb.append("</form>");
		}
		return sb.toString();
	}

}

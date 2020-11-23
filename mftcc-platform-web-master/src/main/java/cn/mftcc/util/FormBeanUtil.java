package cn.mftcc.util;

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
			formStr = jsonFormUtil.getJsonStr(formData, "formTag", "query");
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
	public static String errorHtml(String errormessage) {
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

}

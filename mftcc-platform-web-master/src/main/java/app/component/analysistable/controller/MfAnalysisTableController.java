package app.component.analysistable.controller;

import app.component.analysistable.entity.MfAnalysisTable;
import app.component.analysistable.feign.MfAnalysisTableFeign;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusPersonLiabilities;
import app.component.cus.feign.MfCusPersonLiabilitiesFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfAnalysisTableController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Oct 10 20:43:12 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfAnalysisTable")
public class MfAnalysisTableController extends BaseFormBean {

	@Autowired
	private MfAnalysisTableFeign mfAnalysisTablefeign;
	@Autowired
	private MfCusPersonLiabilitiesFeign mfCusPersonLiabilitiesFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * @方法描述： 小微贷分析表对比查看
	 * 
	 * @return String
	 * @author 仇招
	 * @throws Exception
	 * @date 2018年10月12日 下午9:40:23
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("analysisTable")
	public String analysisTable(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		List<Map<String, Object>> listDataMap = new ArrayList<Map<String, Object>>();
		List<MfAnalysisTable> tableNameList = mfAnalysisTablefeign.getTableName();
		if (tableNameList != null && tableNameList.size() > 0) {
			// 循环遍历对比表
			for (MfAnalysisTable tableObj : tableNameList) {
				String tableName = tableObj.getTableName();
				String tableShowName = tableObj.getTableShowName();
				if (tableName != null && !"".endsWith(tableName)) {
					MfAnalysisTable mfAnalysisTable = new MfAnalysisTable();
					mfAnalysisTable.setTableName(tableName);
					Gson gson = new Gson();
					List<MfAnalysisTable> mfAnalysisTableList = mfAnalysisTablefeign
							.getListByTableName(mfAnalysisTable);
					switch (tableName) {
					case "mf_bus_apply":// 申请表
						MfBusApply mfBusApply = new MfBusApply();
						mfBusApply.setAppId(appId);
						mfBusApply = mfBusApplyFeign.getById(mfBusApply);
						MfBusApply mfBusApplyOld = new MfBusApply();
						mfBusApplyOld.setAppId(mfBusApply.getAppIdOld());
						mfBusApplyOld = mfBusApplyFeign.getById(mfBusApplyOld);
						Map<String, Object> liabilitiesMap = analysisData(
								gson.fromJson(gson.toJson(mfBusApplyOld), Map.class),
								gson.fromJson(gson.toJson(mfBusApply), Map.class),
								mfAnalysisTableList);
						liabilitiesMap.put("tableName", tableName);
						liabilitiesMap.put("tableShowName", tableShowName);
						liabilitiesMap.put("className", "MfBusApply");
						listDataMap.add(liabilitiesMap);
						break;
					default:
						break;
					}
				}
			}
		}
		model.addAttribute("listDataMap", listDataMap);
		return "/component/analysistable/MfAnalysisTable_AnalysisTable";

	}
	/**
	 * @方法描述： 判断是否存在数据小微贷分析表对比查看
	 *
	 * @return String
	 * @author 仇招
	 * @throws Exception
	 * @date 2018年10月12日 下午9:40:23
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getAnalysisTable")
	@ResponseBody
	public Map<String,Object> getAnalysisTable(Model model, String appId) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String flag = "0";
		List<Map<String, Object>> listDataMap = new ArrayList<Map<String, Object>>();
		List<MfAnalysisTable> tableNameList = mfAnalysisTablefeign.getTableName();
		if (tableNameList != null && tableNameList.size() > 0) {
			// 循环遍历对比表
			for (MfAnalysisTable tableObj : tableNameList) {
				String tableName = tableObj.getTableName();
				String tableShowName = tableObj.getTableShowName();
				if (tableName != null && !"".endsWith(tableName)) {
					MfAnalysisTable mfAnalysisTable = new MfAnalysisTable();
					mfAnalysisTable.setTableName(tableName);
					Gson gson = new Gson();
					List<MfAnalysisTable> mfAnalysisTableList = mfAnalysisTablefeign
							.getListByTableName(mfAnalysisTable);
					switch (tableName) {
						case "mf_cus_person_liabilities":// 资产负债表
							MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
							mfCusPersonLiabilities.setAppId(appId);
							List<MfCusPersonLiabilities> mfCusPersonLiabilitiesList = mfCusPersonLiabilitiesFeign
									.getByAppId(mfCusPersonLiabilities);
							if (mfCusPersonLiabilitiesList != null && mfCusPersonLiabilitiesList.size() == 2) {
								Map<String, Object> liabilitiesMap = analysisData(
										gson.fromJson(gson.toJson(mfCusPersonLiabilitiesList.get(0)), Map.class),
										gson.fromJson(gson.toJson(mfCusPersonLiabilitiesList.get(1)), Map.class),
										mfAnalysisTableList);
								liabilitiesMap.put("tableName", tableName);
								liabilitiesMap.put("tableShowName", tableShowName);
								liabilitiesMap.put("className", "MfCusPersonLiabilities");
								listDataMap.add(liabilitiesMap);
							}
							break;
						default:
							break;
					}
				}
			}
		}
		if (listDataMap!=null&&listDataMap.size()>0){
			flag = "1";
		}
		returnMap.put("flag",flag);
		returnMap.put("errorCode","success");
		return returnMap;

	}
	/**
	 * @方法描述： 分析数据
	 * 
	 * @param firstMap
	 * @param secondMap
	 * @param mfAnalysisTableList
	 * @return Map<String,Object>
	 * @author 仇招
	 * @throws Exception
	 * @date 2018年10月13日 上午10:22:01
	 */
	private Map<String, Object> analysisData(Map<String, Object> firstMap, Map<String, Object> secondMap,
			List<MfAnalysisTable> mfAnalysisTableList) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		for (MfAnalysisTable mfAnalysisTable : mfAnalysisTableList) {
			String field = mfAnalysisTable.getField();
			String fieldType = mfAnalysisTable.getFieldType();
			String firstValue = String.valueOf(firstMap.get(field));
			if ("null".equals(firstValue)) {
				firstValue = "";
			}
			String secondValue = String.valueOf(secondMap.get(field));
			if ("null".equals(secondValue)) {
				secondValue = "";
			}
			if (fieldType == null || "".equals(fieldType)) {
				fieldType = BizPubParm.FIELD_TYPE_1;
			}
			switch (fieldType) {
			case BizPubParm.FIELD_TYPE_2:// 字典项
				String selectName = mfAnalysisTable.getSelectName();
				Map<String, String> codeMap = cu.getMapByKeyName(selectName);
				firstValue = codeMap.get(firstValue);
				secondValue = codeMap.get(secondValue);
				break;
			case BizPubParm.FIELD_TYPE_3:// 日期
				firstValue = DateUtil.getShowDateTime(firstValue);
				secondValue = DateUtil.getShowDateTime(secondValue);
				break;
			case BizPubParm.FIELD_TYPE_4:// 金额
				firstValue = MathExtend.moneyStr(firstValue);
				secondValue = MathExtend.moneyStr(secondValue);
				break;
			case BizPubParm.FIELD_TYPE_5:// 百分比
				firstValue =  MathExtend.multiply(firstValue, "100");
				secondValue = MathExtend.multiply(secondValue, "100");
				break;
			case BizPubParm.FIELD_TYPE_6:// 整型
				firstValue =  firstValue.substring(0,firstValue.indexOf("."));
				secondValue =  secondValue.substring(0,secondValue.indexOf("."));
				break;
			default:
				break;
			}
			mfAnalysisTable.setFirstVal(firstValue);
			mfAnalysisTable.setSecondVal(secondValue);
			if(secondValue.equals(firstValue)){
				mfAnalysisTable.setIfEqual(BizPubParm.YES_NO_Y);
			}else{
				mfAnalysisTable.setIfEqual(BizPubParm.YES_NO_N);
			}
		}
		resultMap.put("list", mfAnalysisTableList);
		return resultMap;
	}
}

package  app.component.archives.controller;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.biz.feign.BizInfoChangeSubFeign;
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

import app.component.archives.entity.ArchiveInfoDetailLog;
import app.component.archives.entity.ArchiveInfoDetailLogIncludeDetailAndLend;
import app.component.archives.entity.Constant;
import app.component.archives.feign.ArchiveInfoDetailLogFeign;
import app.component.biz.entity.BizInfoChange;
import app.component.biz.entity.BizInfoChangeSub;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONObject;

/**
 * Title: ArchiveInfoDetailLogAction.java
 * Description:归档文件操作日志
 * @author:yudongwei@mftcc.cn
 * @Sat Apr 08 15:07:35 CST 2017
 **/
@Controller
@RequestMapping("/archiveInfoDetailLog")
public class ArchiveInfoDetailLogController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveInfoDetailLogFeign archiveInfoDetailLogFeign;
	@Autowired
	private BizInfoChangeSubFeign bizInfoChangeSubFeign;
	/** 归档明细信息Feign */
	//全局变量
	/** 归档编号 */
	//表单变量
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,ArchiveInfoDetailLog archiveInfoDetailLog) throws Exception {
		Map<String,String> dataMap = new HashMap<String,String>();
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveInfoDetailLogFeign.getAll(archiveInfoDetailLog), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdl_archive_detail_log02 = formService.getFormData("dl_archive_detail_log02");
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		Ipage ipage = this.getIpage();
		//List<ArchiveInfoDetailLog>archiveInfoDetailLogList = (List<ArchiveInfoDetailLog>)archiveInfoDetailLogFeign.findByPage(ipage, archiveInfoDetailLog).getResult();
		model.addAttribute("formdl_archive_detail_log02", formdl_archive_detail_log02);
		//model.addAttribute("ArchiveInfoDetailLogList", archiveInfoDetailLogList);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetailLog_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		try {
			archiveInfoDetailLog.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoDetailLog.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoDetailLog.setCriteriaList(archiveInfoDetailLog, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoDetailLog", archiveInfoDetailLog));
			//自定义查询Bo方法
			ipage = archiveInfoDetailLogFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formdl_archive_detail_log02 = formService.getFormData("dl_archive_detail_log02");
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		List<ArchiveInfoDetailLog> archiveInfoDetailLogList = archiveInfoDetailLogFeign.getAll(archiveInfoDetailLog);
		model.addAttribute("formdl_archive_detail_log02", formdl_archive_detail_log02);
		model.addAttribute("ArchiveInfoDetailLogList", archiveInfoDetailLogList);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetailLog_List";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tabelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formdl_archive_detail_log02 = formService.getFormData("dl_archive_detail_log02");
			getFormValue(formdl_archive_detail_log02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_detail_log02)){
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
				setObjValue(formdl_archive_detail_log02, archiveInfoDetailLog);
				archiveInfoDetailLogFeign.insert(archiveInfoDetailLog);
				getTableData(tabelId,archiveInfoDetailLog);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tabelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		try{
		FormData 	formdl_archive_detail_log02 = formService.getFormData("dl_archive_detail_log02");
			getFormValue(formdl_archive_detail_log02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_detail_log02)){
				setObjValue(formdl_archive_detail_log02, archiveInfoDetailLog);
				archiveInfoDetailLogFeign.update(archiveInfoDetailLog);
				getTableData(tabelId,archiveInfoDetailLog);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formdl_archive_detail_log02 = formService.getFormData("dl_archive_detail_log02");
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		archiveInfoDetailLog.setId(id);
		archiveInfoDetailLog = archiveInfoDetailLogFeign.getById(archiveInfoDetailLog);
		getObjValue(formdl_archive_detail_log02, archiveInfoDetailLog,formData);
		if(archiveInfoDetailLog!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String id,String tabelId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		archiveInfoDetailLog.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveInfoDetailLog = (ArchiveInfoDetailLog)JSONObject.toBean(jb, ArchiveInfoDetailLog.class);
			archiveInfoDetailLogFeign.delete(archiveInfoDetailLog);
			getTableData(tabelId,archiveInfoDetailLog);
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
	
	@RequestMapping(value = "/getAllForMainAjax")
	@ResponseBody
	public Map<String, Object> getAllForMainAjax(String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception("归档编号不能为空！");
		}
		
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		archiveInfoDetailLog.setArchiveMainNo(archiveMainNo);
		try {
			List<ArchiveInfoDetailLogIncludeDetailAndLend> logList = new ArrayList<ArchiveInfoDetailLogIncludeDetailAndLend>();
			List<ArchiveInfoDetailLogIncludeDetailAndLend> logIncludeDetailAndLendList = archiveInfoDetailLogFeign
					.getAllIncludeDetailAndLend(archiveInfoDetailLog);
			if (logIncludeDetailAndLendList != null && logIncludeDetailAndLendList.size() > 0) {
				CodeUtils codeUtils = new CodeUtils();
				Map<String, String> archiveOperationTypeMap = codeUtils.getMapByKeyName("ARCHIVE_OPERATION_TYPE");
				int count = 0;
				for (ArchiveInfoDetailLogIncludeDetailAndLend logIncludeDetailAndLend : logIncludeDetailAndLendList) {
					String archiveOperationType = logIncludeDetailAndLend.getArchiveOperationType();
					if (Constant.ARCHIVE_OPERATION_TYPE_ARCHIVE.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_MERGE.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_BACKUP.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_SEAL.equals(archiveOperationType)) {
						int lastIndex = logList.size() - 1;
						if (lastIndex >= 0 && archiveOperationType.equals(logList.get(lastIndex).getArchiveOperationType())) {
							count++;
							String description = archiveOperationTypeMap.get(archiveOperationType) + count + "个文件";
							logList.get(lastIndex).setDescription(description);
							continue;
						} else {
							count = 1;
							String description = archiveOperationTypeMap.get(archiveOperationType) + count + "个文件";
							logIncludeDetailAndLend.setDescription(description);
							logIncludeDetailAndLend.setOpDate(DateUtil.getShowDateTime(logIncludeDetailAndLend.getOpDate()));
							logList.add(logIncludeDetailAndLend);
						}
					} else if (Constant.ARCHIVE_OPERATION_TYPE_LEND.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_RETURN.equals(archiveOperationType)) {
						String docName = logIncludeDetailAndLend.getDocName();
						String description = archiveOperationTypeMap.get(archiveOperationType)
								+ "《" + docName + "》，借阅人：" + logIncludeDetailAndLend.getBorrower()
								+ "，计划归还日期：" + DateUtil.getShowDateTime(logIncludeDetailAndLend.getReturnPlanDate());
						logIncludeDetailAndLend.setDescription(description);
						logIncludeDetailAndLend.setOpDate(DateUtil.getShowDateTime(logIncludeDetailAndLend.getOpDate()));
						logList.add(logIncludeDetailAndLend);
					} else {
						String docName = logIncludeDetailAndLend.getDocName();
						String description = archiveOperationTypeMap.get(archiveOperationType) + "《" + docName + "》";
						logIncludeDetailAndLend.setDescription(description);
						logIncludeDetailAndLend.setOpDate(DateUtil.getShowDateTime(logIncludeDetailAndLend.getOpDate()));
						logList.add(logIncludeDetailAndLend);
					}
				}
			}
			
			List<ArchiveInfoDetailLogIncludeDetailAndLend> top5LogList = new ArrayList<ArchiveInfoDetailLogIncludeDetailAndLend>();
			if (logList != null && logList.size() > 0) {
				for (int i = 0; i < logList.size(); i++) {
					if (i == 5) {
						break;
					}
					ArchiveInfoDetailLogIncludeDetailAndLend log = logList.get(i);
					top5LogList.add(log);
				}
			}
			dataMap.put("logList", top5LogList);
			dataMap.put("success", true);
		} catch (Exception e) {
			dataMap.put("success", false);
			dataMap.put("msg", "获取文件操作记录出错：" + e.getMessage());
			e.printStackTrace();
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getListPageForMain")
	public String getListPageForMain(Model model, String archiveMainNo) throws Exception {
		ArrayList<BizInfoChange> bizInfoChangeList = new ArrayList<BizInfoChange>();
		ActionContext.initialize(request,response);
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception("归档编号不能为空！");
		}
		
		ArchiveInfoDetailLog archiveInfoDetailLog = new ArchiveInfoDetailLog();
		archiveInfoDetailLog.setArchiveMainNo(archiveMainNo);
		try {
			List<ArchiveInfoDetailLogIncludeDetailAndLend> logList = new ArrayList<ArchiveInfoDetailLogIncludeDetailAndLend>();
			List<ArchiveInfoDetailLogIncludeDetailAndLend> logIncludeDetailAndLendList = archiveInfoDetailLogFeign
					.getAllIncludeDetailAndLend(archiveInfoDetailLog);
			if (logIncludeDetailAndLendList != null && logIncludeDetailAndLendList.size() > 0) {
				CodeUtils codeUtils = new CodeUtils();
				Map<String, String> archiveOperationTypeMap = codeUtils.getMapByKeyName("ARCHIVE_OPERATION_TYPE");
				int count = 0;
				for (ArchiveInfoDetailLogIncludeDetailAndLend logIncludeDetailAndLend : logIncludeDetailAndLendList) {
					String archiveOperationType = logIncludeDetailAndLend.getArchiveOperationType();
					if (Constant.ARCHIVE_OPERATION_TYPE_ARCHIVE.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_MERGE.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_BACKUP.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_SEAL.equals(archiveOperationType)) {
						int lastIndex = logList.size() - 1;
						if (lastIndex >= 0 && archiveOperationType.equals(logList.get(lastIndex).getArchiveOperationType())) {
							count++;
							String description = archiveOperationTypeMap.get(archiveOperationType) + count + "个文件";
							logList.get(lastIndex).setDescription(description);
							continue;
						} else {
							count = 1;
							String description = archiveOperationTypeMap.get(archiveOperationType) + count + "个文件";
							logIncludeDetailAndLend.setDescription(description);
							logIncludeDetailAndLend.setOpDate(DateUtil.getShowDateTime(logIncludeDetailAndLend.getOpDate()));
							logList.add(logIncludeDetailAndLend);
						}
					} else if (Constant.ARCHIVE_OPERATION_TYPE_LEND.equals(archiveOperationType)
							|| Constant.ARCHIVE_OPERATION_TYPE_RETURN.equals(archiveOperationType)) {
						String docName = logIncludeDetailAndLend.getDocName();
						String description = archiveOperationTypeMap.get(archiveOperationType)
								+ "《" + docName + "》，借阅人：" + logIncludeDetailAndLend.getBorrower()
								+ "，计划归还日期：" + DateUtil.getShowDateTime(logIncludeDetailAndLend.getReturnPlanDate());
						logIncludeDetailAndLend.setDescription(description);
						logIncludeDetailAndLend.setOpDate(DateUtil.getShowDateTime(logIncludeDetailAndLend.getOpDate()));
						logList.add(logIncludeDetailAndLend);
					} else {
						String docName = logIncludeDetailAndLend.getDocName();
						String description = archiveOperationTypeMap.get(archiveOperationType) + "《" + docName + "》";
						logIncludeDetailAndLend.setDescription(description);
						logIncludeDetailAndLend.setOpDate(DateUtil.getShowDateTime(logIncludeDetailAndLend.getOpDate()));
						logList.add(logIncludeDetailAndLend);
					}
				}
			}
			
			if (logList != null && logList.size() > 0) {
				for (ArchiveInfoDetailLogIncludeDetailAndLend log : logList) {
					BizInfoChange bizInfoChange = new BizInfoChange();
					bizInfoChange.setBizType("2");
					bizInfoChange.setChangeNo(log.getId());
					bizInfoChange.setChangeType("3");
					bizInfoChange.setCont(log.getDescription());
					bizInfoChange.setDate(log.getOpDate());
					bizInfoChange.setTime(log.getOpTime());
					bizInfoChange.setOpNo(log.getOpNo());
					bizInfoChange.setOpName(log.getOpName());
					bizInfoChangeList.add(bizInfoChange);
				}
			}
			model.addAttribute("ArchiveInfoDetailLogList", logList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		this.getBizInfo(model,bizInfoChangeList);
		model.addAttribute("query", "");

		return "/component/archives/ArchiveInfoDetailLog_List";
	}
	
	@RequestMapping(value = "/getBizInfo")
	public void getBizInfo(Model model,List<BizInfoChange> bizInfoChangeList){
		//将年、月、日、分别放进一个map
		TreeMap<String, String> yearMap = new TreeMap<String, String>(
				new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						Integer a = Integer.parseInt((String) o1);
						Integer b = Integer.parseInt((String) o2);
						return b - a;
					}
				});
		TreeMap<String, String> monthMap = new TreeMap<String, String>(
				new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						Integer a = Integer.parseInt((String) o1);
						Integer b = Integer.parseInt((String) o2);
						return b - a;
					}
				});
		TreeMap<String, String> dayMap = new TreeMap<String, String>(
				new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						Integer a = Integer.parseInt((String) o1);
						Integer b = Integer.parseInt((String) o2);
						return b - a;
					}
				});
		for (BizInfoChange change : bizInfoChangeList) {
			String dateTmp = change.getDate().replaceAll("-", "");
			String year = dateTmp.substring(0, 4);
			String month = dateTmp.substring(0, 6);
			String day = dateTmp;
			String desc = change.getCont();
			if (!yearMap.containsKey(year)) {
				yearMap.put(year, year);
			}
			if (!monthMap.containsKey(month)) {
				monthMap.put(month, month);
			}
			if (!dayMap.containsKey(day)) {
				dayMap.put(day, change.getChangeNo() + "@" + desc + "@"
						+ change.getChangeType() + "@" + change.getTime());
			} else {
				dayMap.put(day, dayMap.get(day) + ";" + change.getChangeNo()
						+ "@" + desc + "@" + change.getChangeType() + "@"
						+ change.getTime());
			}
		}
		// 循环map 将日放进月的子集 将月放进年的子集
		List<BizInfoChange> yearList = new ArrayList<BizInfoChange>();
		List<BizInfoChange> monthList = new ArrayList<BizInfoChange>();
		List<BizInfoChange> dayList = new ArrayList<BizInfoChange>();
		String dateStr = "";
		for (String yearKey : yearMap.keySet()) {
			BizInfoChange yearChange = new BizInfoChange();
			yearChange.setDate(yearKey);
			dateStr += yearKey + "@";
			for (String monthkey : monthMap.keySet()) {
				if (monthkey.substring(0, 4).equals(yearKey)) {
					BizInfoChange monthChange = new BizInfoChange();
					monthChange.setDate(monthkey);
					dateStr += monthkey + "@";
					for (String dayKey : dayMap.keySet()) {
						if (dayKey.subSequence(0, 6).equals(monthkey)) {
							String[] values = dayMap.get(dayKey).split(";");
							for (int i = 0; i < values.length; i++) {
								BizInfoChange dayChange = new BizInfoChange();
								if (i == 0) {
									dayChange.setDate(dayKey);
								}
								dayChange.setChangeNo(values[i].split("@")[0]);
								dayChange.setCont(values[i].split("@")[1]);
								dayChange.setChangeType(values[i].split("@")[2]);
								dayChange.setTime(values[i].split("@")[3]);
								dayList.add(dayChange);
							}
						}
					}
					monthChange.setCont(dayList.get(0).getDate() + "@"
							+ dayList.get(0).getCont() + "@"
							+ dayList.get(0).getChangeNo() + "@"
							+ dayList.get(0).getChangeType() + "@"
							+ dayList.get(0).getTime());
					dayList.remove(0);
					monthChange.setSubList(dayList);
					monthList.add(monthChange);
				}
			}
			yearChange.setSubList(monthList);
			yearList.add(yearChange);
		}
		if (!"".equals(dateStr)) {
			dateStr = dateStr.substring(0, dateStr.lastIndexOf("@"));
		}
		model.addAttribute("dateStr", dateStr);
		model.addAttribute("yearList", yearList);
		if (null != bizInfoChangeList && bizInfoChangeList.size() != 0) {
			BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
			bizInfoChangeSub.setChangeNo(bizInfoChangeList.get(0).getChangeNo());
			List<BizInfoChangeSub> bizInfoChangeSubList = bizInfoChangeSubFeign.getAll(bizInfoChangeSub);
			model.addAttribute("bizInfoChangeSubList",bizInfoChangeSubList);
		}
	}

	
}

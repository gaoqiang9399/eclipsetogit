package app.component.collateral.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyHis;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfCollateralTableFeign;
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.wkf.AppConstant;
import com.core.report.ExpExclUtil;
import config.YmlConfig;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.MfCollateralTable;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.finance.util.CustomException;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: PledgeBaseInfoBillController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 19 13:49:06 CST 2017
 **/
@Controller
@RequestMapping("/pledgeBaseInfoBill")
public class PledgeBaseInfoBillController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
	@Autowired
	private MfCollateralTableFeign mfCollateralTableFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void getTableData(Map<String, Object> dataMap, String tableId, PledgeBaseInfoBill pledgeBaseInfoBill)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pledgeBaseInfoBillFeign.getAll(pledgeBaseInfoBill), null,
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
		FormData formdlpledgebaseinfobill0002 = formService.getFormData("dlpledgebaseinfobill0002");
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
		List<PledgeBaseInfoBill> pledgeBaseInfoBillList = (List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign
				.findByPage(ipage).getResult();
		model.addAttribute("pledgeBaseInfoBillList", pledgeBaseInfoBillList);
		model.addAttribute("formdlpledgebaseinfobill0002", formdlpledgebaseinfobill0002);
		model.addAttribute("query", "");
		return "/component/collateral/PledgeBaseInfoBill_List";
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
		FormData formdlpledgebaseinfobill0002 = formService.getFormData("dlpledgebaseinfobill0002");
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign.getAll(pledgeBaseInfoBill);
		model.addAttribute("pledgeBaseInfoBillList", pledgeBaseInfoBillList);
		model.addAttribute("formdlpledgebaseinfobill0002", formdlpledgebaseinfobill0002);
		model.addAttribute("query", "");
		return "/component/collateral/PledgeBaseInfoBill_List";
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
			FormData formdlpledgebaseinfobill0002 = formService.getFormData("dlpledgebaseinfobill0002");
			getFormValue(formdlpledgebaseinfobill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlpledgebaseinfobill0002)) {
				PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
				setObjValue(formdlpledgebaseinfobill0002, pledgeBaseInfoBill);
				String collateralId = pledgeBaseInfoBill.getPledgeNo();
				int billOrder = pledgeBaseInfoBillFeign.findNetBillOrder(pledgeBaseInfoBill);
				pledgeBaseInfoBill.setBillOrder(billOrder);
				pledgeBaseInfoBill.setIsHis("0");
				pledgeBaseInfoBillFeign.insert(pledgeBaseInfoBill);
				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(pledgeBaseInfoBill.getClassId(), "PledgeBaseInfoBillAction", "");
				String tableId = "";
				if (mfCollateralFormConfig == null) {
					// Log.error("押品评估配置信息没有找到。");
				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的PledgeBaseInfoBillController列表table"
					// + tableId + ".xml文件不存在");
				}

				pledgeBaseInfoBill = new PledgeBaseInfoBill();
				pledgeBaseInfoBill.setPledgeNo(collateralId);
				pledgeBaseInfoBill.setIsHis("0");
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("pledgeNo", collateralId);

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
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
		try {
			FormData formdlpledgebaseinfobill0002 = formService.getFormData("dlpledgebaseinfobill0002");
			getFormValue(formdlpledgebaseinfobill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlpledgebaseinfobill0002)) {
				PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
				setObjValue(formdlpledgebaseinfobill0002, pledgeBaseInfoBill);
				pledgeBaseInfoBillFeign.update(pledgeBaseInfoBill);

				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(pledgeBaseInfoBill.getClassId(), "PledgeBaseInfoBillAction", "");
				String tableId = "";
				if (mfCollateralFormConfig == null) {

				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的PledgeBaseInfoBillController列表table"
					// + tableId + ".xml文件不存在");
				}

				String collateralNo = pledgeBaseInfoBill.getPledgeNo();
				pledgeBaseInfoBill = new PledgeBaseInfoBill();
				pledgeBaseInfoBill.setPledgeNo(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<EvalInfo>) pledgeBaseInfoBillFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String ajaxData, String pledgeBillNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlpledgebaseinfobill0002 = formService.getFormData("dlpledgebaseinfobill0002");
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);
		getObjValue(formdlpledgebaseinfobill0002, pledgeBaseInfoBill, formData);
		if (pledgeBaseInfoBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String pledgeBillNo, String pledgeNo, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill.setPledgeNo(pledgeNo);
		pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);
		try {
			pledgeBaseInfoBillFeign.delete(pledgeBaseInfoBill);
			List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign
					.getBillListByPledgeNo(pledgeBaseInfoBill);

			if (pledgeBaseInfoBillList == null || pledgeBaseInfoBillList.size() == 0) {
				dataMap.put("dataFullFlag", BizPubParm.YES_NO_N);
				MfCollateralTable mfCollateralTable = new MfCollateralTable();
				mfCollateralTable.setCollateralNo(pledgeNo);
				mfCollateralTable.setTableName("pledge_base_info_bill");
				mfCollateralTable.setDataFullFlag("0");
				mfCollateralTableFeign.update(mfCollateralTable);
				mfCollateralTable = new MfCollateralTable();
				mfCollateralTable.setCollateralNo(pledgeNo);
				List<MfCollateralTable> mfCollateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
				dataMap.put("mfCollateralTableList", mfCollateralTableList);
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				pledgeBaseInfo.setPledgeNo(pledgeNo);
				pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
				dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
			} else {
				dataMap.put("dataFullFlag", BizPubParm.YES_NO_Y);
				pledgeBaseInfoBill = new PledgeBaseInfoBill();
				pledgeBaseInfoBill.setPledgeNo(pledgeNo);
				pledgeBaseInfoBill.setIsHis("0");
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign.findByPageNotRegister(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("pledgeNo", pledgeNo);
			}

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
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjaxNew")
	@ResponseBody
	public Map<String, Object> deleteAjaxNew(String ajaxData, String pledgeBillNo, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);
		try {
			pledgeBaseInfoBillFeign.delete(pledgeBaseInfoBill);
			List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign
					.findNewByPledgeNo(pledgeBaseInfoBill.getPledgeNo());
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("unchecked")
			String tableHtml = jtu.getJsonStr( tableId, "tableTag",
					pledgeBaseInfoBillList, null, true);
			dataMap.put("htmlStr", tableHtml);
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
	public String input(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"PledgeBaseInfoBillAction", "");
		String formId = null;
		if (mfCollateralFormConfig == null) {
		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的PledgeBaseInfoBillController表单信息没有查询到");
		} else {
			PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
			pledgeBaseInfoBill.setPledgeNo(collateralNo);
			String pledgeBillNo = WaterIdUtil.getWaterId();
			pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
			pledgeBaseInfoBill.setClassId(classId);
			FormData formdlpledgebaseinfobill0002 = formService.getFormData(formId);
			if (formdlpledgebaseinfobill0002.getFormId() == null) {
				// Log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的PledgeBaseInfoBillController表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formdlpledgebaseinfobill0002);
				getObjValue(formdlpledgebaseinfobill0002, pledgeBaseInfoBill);
				model.addAttribute("formdlpledgebaseinfobill0002", formdlpledgebaseinfobill0002);
			}
		}

		model.addAttribute("query", "");
		model.addAttribute("collateralNo", collateralNo);
		return "/component/collateral/PledgeBaseInfoBill_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String pledgeBillNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(pledgeBaseInfoBill.getPledgeNo());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		pledgeBaseInfoBill.setClassId(classId);// 修改时候用
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"PledgeBaseInfoBillAction", "");
		String formId = "";
		if (mfCollateralFormConfig == null) {
		} else {
			if ("1".equals(mfCollateralFormConfig.getShowType())) {
				formId = mfCollateralFormConfig.getShowModelDef();
			} else {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
		}

		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的PledgeBaseInfoBillController表单信息没有查询到");
		} else {
			FormData formdlpledgebaseinfobill0003 = formService.getFormData(formId);
			if (formdlpledgebaseinfobill0003.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的PledgeBaseInfoBillController表单form"
				// + formId + ".xml文件不存在");
			} else {
				getFormValue(formdlpledgebaseinfobill0003);
				getObjValue(formdlpledgebaseinfobill0003, pledgeBaseInfoBill);
				model.addAttribute("formdlpledgebaseinfobill0003", formdlpledgebaseinfobill0003);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/PledgeBaseInfoBill_Detail";
	}

	@RequestMapping("/export")
	public String export(Model model, String pledgeNo) throws Exception {
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeNo(pledgeNo);
		model.addAttribute("pledgeBaseInfoBill", pledgeBaseInfoBill);
		model.addAttribute("pledgeNo", pledgeNo);
		return "/component/collateral/PledgeBaseInfoBill_Export";
	}

	/**
	 * 方法描述： 下载清单导入模版 void
	 * 
	 * @author HGX
	 * @date 2017-5-23 下午3:46:23
	 */
	@RequestMapping("/printPledgeBillTemplate")
	public void printPledgeBillTemplate() throws Exception {
		Logger logger = LoggerFactory.getLogger(PledgeBaseInfoBillController.class);
		InputStream inputStream = null;
		ServletOutputStream servletOutputStream = null;
		try {
			String filename = "押品明细清单导入模板.xls";
			String path = "static/template/pledgeBillTemplate.xls";
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource("classpath:" + path);

			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.addHeader("charset", "utf-8");
			response.addHeader("Pragma", "no-cache");
			String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);
			inputStream = resource.getInputStream();
			servletOutputStream = response.getOutputStream();
			IOUtils.copy(inputStream, servletOutputStream);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (servletOutputStream != null) {
					servletOutputStream.close();
					servletOutputStream = null;
				}
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				// 召唤jvm的垃圾回收器
				System.gc();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 方法描述： 清单模版上传
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-17 上午9:56:31
	 */
	@SuppressWarnings({ "unchecked", "resource", "deprecation" })
	@RequestMapping("/importPledgeBillPlateAjax")
	@ResponseBody
	public Map<String, Object> importPledgeBillPlateAjax(String pledgeNo, @RequestParam(value="vch")  MultipartFile vch) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HSSFWorkbook  wb = null;
		try {
			wb = new HSSFWorkbook(new POIFSFileSystem(vch.getInputStream()));
			DecimalFormat df = new DecimalFormat("##0");
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = null;
			HSSFCell cell = null;
			int lastrow = sheet.getLastRowNum();
			int lastcell;
 			List<String[]> dataList = new ArrayList<String[]>();
			for (int i = 1; i <= lastrow; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				lastcell = row.getLastCellNum();
				String[] temp = new String[lastcell];
				for (int j = 0; j < lastcell; j++) {
					cell = row.getCell(j);
					if (cell == null) {
						temp[j] = "";
						continue;
					}
					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
			            	temp[j] = df.format(cell.getNumericCellValue());  
							break;
						case HSSFCell.CELL_TYPE_STRING:
							temp[j] = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							temp[j] = "";
							break;
						default:
							break;
					}
					
				
				}
				dataList.add(temp);
			}
			String dataListStr = new Gson().toJson(dataList);
			pledgeBaseInfoBillFeign.dealImportPledgeBillPlate(dataListStr, pledgeNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
			pledgeBaseInfoBill.setPledgeNo(pledgeNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
			JsonTableUtil jtu = new JsonTableUtil();
			String tableId = "dlpledgebaseinfobill0004";
			String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
					(List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign.findByPage(ipage).getResult(), null, true);
			dataMap.put("htmlStr", tableHtml);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "清单导入失败");
			if (e instanceof CustomException) {
				dataMap.put("msg", e.getMessage());
			} else {
				// Log.error("清单导入出错", e);
				e.printStackTrace();
				throw e;
			}
		}
		finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 根据押品编号分页展示货物明细
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-6-13 下午4:46:07
	 */
	@RequestMapping("/getListByPage")
	public String getListByPage(Model model, String pledgeNo ,String insId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("pledgeNo", pledgeNo);
		model.addAttribute("insId", insId);
		return "/component/collateral/PledgeBaseInfoBillByPage_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType, String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		try {
			pledgeBaseInfoBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pledgeBaseInfoBill.setCriteriaList(pledgeBaseInfoBill, ajaxData);// 我的筛选
			pledgeBaseInfoBill.setPledgeNo(pledgeNo);
			// mfMoveableTransferApply.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfMoveableTransferApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
			ipage = pledgeBaseInfoBillFeign.findByPage(ipage);
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
	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjaxHistory")
	@ResponseBody
	public Map<String, Object> findByPageAjaxHistory(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType, String pledgeNo,String insId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		try {
			pledgeBaseInfoBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pledgeBaseInfoBill.setCriteriaList(pledgeBaseInfoBill, ajaxData);// 我的筛选
			pledgeBaseInfoBill.setPledgeNo(pledgeNo);
			pledgeBaseInfoBill.setRegisterId(insId);
			// mfMoveableTransferApply.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfMoveableTransferApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
			ipage = pledgeBaseInfoBillFeign.findByPage(ipage);
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
	 * 
	 * 方法描述： 根据押品编号获得关联的货物明细
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-6-14 下午8:31:50
	 */
	@RequestMapping("/getTableDataByPledgeNoAjax")
	@ResponseBody
	public Map<String, Object> getTableDataByPledgeNoAjax(String ajaxData, String pledgeNo,
														  String appId, String tableId,
														  String pledgeBillSts,String isDealing)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
			pledgeBaseInfoBill.setPledgeNo(pledgeNo);
			pledgeBaseInfoBill.setPledgeBillSts(pledgeBillSts);
			pledgeBaseInfoBill.setIsDealing(isDealing);
			pledgeBaseInfoBill.setAppId(appId);
			JsonTableUtil jtu = new JsonTableUtil();
			List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign
					.getBillListByPledgeNo(pledgeBaseInfoBill);
			if (pledgeBaseInfoBillList!=null&&pledgeBaseInfoBillList.size()>0){
				double goodsValus = pledgeBaseInfoBillFeign.getGoodsValue(pledgeBaseInfoBillList);
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", pledgeBaseInfoBillList, null, true);
				dataMap.put("tableData", tableHtml);
				dataMap.put("goodsValus", goodsValus);
				dataMap.put("flag", "success");
			}else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String ajaxData, String pledgeBillNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "2");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(pledgeBaseInfoBill.getPledgeNo());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"PledgeBaseInfoBillAction", "");
		String formId = "";
		String query = "";
		String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(pledgeBaseInfoBill.getPledgeNo());
		if(StringUtil.isNotEmpty(appId)){
			if(!appId.contains("SX")){
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
				//判断押品表单信息是否允许编辑
				query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(),mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_PLE,User.getRegNo(request));
			}
			if(query==null) {
				query="";
			}
		}
		if (mfCollateralFormConfig == null) {
		} else {
			formId = mfCollateralFormConfig.getListModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的PledgeBaseInfoBillController列表详情表单信息没有查询到");
		}
		FormData formdlpledgebaseinfobill0003 = formService.getFormData(formId);
		if (formdlpledgebaseinfobill0003.getFormId() == null) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的PledgeBaseInfoBillController列表详情表单信息没有查询到");
			dataMap.put("msg", "form" + formId + "获取失败");
			dataMap.put("flag", "error");
			return dataMap;
		}
		getObjValue(formdlpledgebaseinfobill0003, pledgeBaseInfoBill, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formdlpledgebaseinfobill0003, "propertySeeTag", query);
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", pledgeBaseInfoBill);
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 根据业务上次审批意见类型获得query
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-9-2 下午5:32:17
	 */
	@RequestMapping(value = "/getQueryBylastApproveType")
	public String getQueryBylastApproveType(String appId) throws Exception {
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setAppId(appId);
		/**
		 * 上次审批意见类型。
		 * 如果上次审批的审批意见类型为发回补充资料，设置query为"",表单可编辑,要件可上传
		 */
		List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
		list  = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);
		String query="query";
		if(list!=null&&list.size()>0){
			mfBusApplyHis = list.get(0);
			String lastApproveType=mfBusApplyHis.getApproveResult();
			if(StringUtil.isNotEmpty(lastApproveType)&&AppConstant.OPINION_TYPE_DEALER.equals(lastApproveType)){
				query = "";
			}
		}
		return query;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxData);
		String pledgeBillNo = (String) dataMap.get("pledgeBillNo");
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setPledgeBillNo(pledgeBillNo);
		pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);

		if (StringUtil.isEmpty(formId)) {
			String collateralNo = pledgeBaseInfoBill.getPledgeNo();
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			String classId = pledgeBaseInfo.getClassId();
			formId = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "PledgeBaseInfoBillAction", "")
					.getListModelDef();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}

		// PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		FormData formdlpledgebaseinfobill0003 = formService.getFormData(formId);
		getFormValue(formdlpledgebaseinfobill0003, getMapByJson(ajaxData));
		PledgeBaseInfoBill pledgeBaseInfoBillNew = new PledgeBaseInfoBill();
		setObjValue(formdlpledgebaseinfobill0003, pledgeBaseInfoBillNew);
		// pledgeBaseInfoBill.setPledgeBillNo(pledgeBaseInfoBillNew.getPledgeBillNo());
		// PledgeBaseInfoBill pledgeBaseInfoBill =
		// pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);
		if (pledgeBaseInfoBill != null) {
			try {
				pledgeBaseInfoBill = (PledgeBaseInfoBill) EntityUtil.reflectionSetVal(pledgeBaseInfoBill,
						pledgeBaseInfoBillNew, getMapByJson(ajaxData));
				pledgeBaseInfoBillFeign.update(pledgeBaseInfoBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}


	@RequestMapping("/getBillPageByPledgeNos")
	@ResponseBody
	public Map<String, Object> getBillPageByPledgeNos(String ajaxData, Integer pageNo,
													  Integer pageSize,String pledgeNos
			,String pledgeBillSts,String refFlag) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		try {
			pledgeBaseInfoBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pledgeBaseInfoBill.setCriteriaList(pledgeBaseInfoBill, ajaxData);// 我的筛选
			pledgeBaseInfoBill.setPledgeNo(pledgeNos);
			pledgeBaseInfoBill.setRefFlag(refFlag);
			pledgeBaseInfoBill.setPledgeBillSts(pledgeBillSts);
			// mfMoveableTransferApply.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfMoveableTransferApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("pledgeBaseInfoBill", pledgeBaseInfoBill));
			if (pledgeNos.indexOf("@")!=-1){
				ipage = pledgeBaseInfoBillFeign.getBillPageByPledgeNos(ipage);
			} else {
				ipage = pledgeBaseInfoBillFeign.findByPage(ipage);
			}
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
	 * 计算出库数量、调用规则计算货值
	 * @param billListData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calcPledgeValueByBillAjax")
	@ResponseBody
	public Map<String, Object> calcPledgeValueByBillAjax(String billListData,String appId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = pledgeBaseInfoBillFeign.calcPledgeValueByBill(billListData,appId);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 导出借据信息
	 * @param ajaxData
	 * @param pactId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcelAjax")
	@ResponseBody
	public void exportExcelAjax(String ajaxData,String tableId,String collateralNo,String insId) throws Exception{
		try {
			PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
			pledgeBaseInfoBill.setPledgeNo(collateralNo);
			pledgeBaseInfoBill.setRegisterId(insId);
			List<PledgeBaseInfoBill> listExport =pledgeBaseInfoBillFeign.getAll(pledgeBaseInfoBill);
			if(listExport.size()>0){
				for (int i = 0; i < listExport.size(); i++) {
					pledgeBaseInfoBill = listExport.get(i);
					pledgeBaseInfoBill.setSingleNum(i+1);
				}
			}
			if(listExport.size()>0){
				String fileName = "合同清单"+System.currentTimeMillis()+".xls";//目标文件
				ExpExclUtil eu = new ExpExclUtil();
				HSSFWorkbook wb = eu.expExclTableForList(tableId, listExport,null,false,"");
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/x-download; charset=utf-8");
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				OutputStream stream = response.getOutputStream();
				wb.write(stream);
				wb.close();// HSSFWorkbook关闭
				stream.flush();
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}

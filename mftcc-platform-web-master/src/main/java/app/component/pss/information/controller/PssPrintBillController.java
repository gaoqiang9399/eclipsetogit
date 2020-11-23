package app.component.pss.information.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.base.User;
import app.component.model.entity.PageContent;
import app.component.model.entity.PageOfficePage;
import app.component.model.feign.MfToPageOfficeFeign;
import app.component.pss.fund.entity.PssOtherPayBill;
import app.component.pss.fund.entity.PssOtherRecBill;
import app.component.pss.fund.entity.PssPaymentBill;
import app.component.pss.fund.entity.PssReceiptBill;
import app.component.pss.fund.feign.PssOtherPayBillFeign;
import app.component.pss.fund.feign.PssOtherRecBillFeign;
import app.component.pss.fund.feign.PssPaymentBillFeign;
import app.component.pss.fund.feign.PssReceiptBillFeign;
import app.component.pss.information.feign.PssPrintBillFeign;
import app.component.pss.purchases.entity.PssBuyBill;
import app.component.pss.purchases.entity.PssBuyOrder;
import app.component.pss.purchases.entity.PssBuyReturnBill;
import app.component.pss.purchases.feign.PssBuyBillFeign;
import app.component.pss.purchases.feign.PssBuyOrderFeign;
import app.component.pss.purchases.feign.PssBuyReturnBillFeign;
import app.component.pss.sales.entity.PssSaleBill;
import app.component.pss.sales.entity.PssSaleOrder;
import app.component.pss.sales.entity.PssSaleReturnBill;
import app.component.pss.sales.feign.PssSaleBillFeign;
import app.component.pss.sales.feign.PssSaleOrderFeign;
import app.component.pss.sales.feign.PssSaleReturnBillFeign;
import app.component.pss.stock.entity.PssAlloTransBill;
import app.component.pss.stock.entity.PssOtherStockInBill;
import app.component.pss.stock.entity.PssOtherStockOutBill;
import app.component.pss.stock.feign.PssAlloTransBillFeign;
import app.component.pss.stock.feign.PssOtherStockInBillFeign;
import app.component.pss.stock.feign.PssOtherStockOutBillFeign;
import app.util.JsonStrHandling;
import config.YmlConfig;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pssPrintBill")
public class PssPrintBillController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssPrintBillFeign pssPrintBillFeign;
	@Autowired
	private PssBuyBillFeign pssBuyBillFeign;
	@Autowired
	private PssBuyOrderFeign pssBuyOrderFeign;
	private PssBuyReturnBillFeign pssBuyReturnBillFeign ;
	@Autowired
	private PssSaleBillFeign pssSaleBillFeign;
	@Autowired
	private PssSaleOrderFeign pssSaleOrderFeign;
	private PssSaleReturnBillFeign pssSaleReturnBillFeign ;
	@Autowired
	private PssAlloTransBillFeign pssAlloTransBillFeign;
	@Autowired
	private PssOtherStockInBillFeign pssOtherStockInBillFeign;
	@Autowired
	private PssOtherStockOutBillFeign pssOtherStockOutBillFeign;
	@Autowired
	private PssReceiptBillFeign pssReceiptBillFeign;
	@Autowired
	private PssPaymentBillFeign pssPaymentBillFeign;
	@Autowired
	private PssOtherRecBillFeign pssOtherRecBillFeign;
	@Autowired
	private PssOtherPayBillFeign pssOtherPayBillFeign;
	
	@Autowired
	private MfToPageOfficeFeign mfToPageOfficeFeign;
	@Autowired
	private YmlConfig ymlConfig;
	
	/**
	 * 下载PageOffice插件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downLoadPrintPlugin")
	public InputStream downLoadPrintPlugin(Model model, String ajaxData) throws Exception{
		BufferedInputStream inputStream = null;
		try{
			String path = request.getServletContext().getRealPath("WEB-INF/lib/posetup.exe");
			inputStream = new BufferedInputStream(new FileInputStream(path));
			File tempFile =new File(path.trim());  
	        String tempFileName = tempFile.getName();  
		String 	fileName = new String(tempFileName.getBytes(),"ISO8859-1");
		}catch(Exception ex){
			throw ex;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return inputStream;
	}
	
	/**
	 * 进销存-批量打印公共函数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBatchPrintAjax")
	@ResponseBody
	public Map<String, Object> pssBatchPrintAjax(String printBizType,String billId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageContent poCnt = null;
		PageOfficePage poPage = null;
		JSONObject jsonObject = new JSONObject();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String webPath = ymlConfig.getWebservice().get("webPath");
		if(webPath.startsWith("/")){
			basePath+=webPath;
		}else{
			basePath= basePath+"/"+webPath;
		}
		try{
			switch(printBizType){
				case "GHDD" : {
					PssBuyOrder query = new PssBuyOrder();
					query.setBuyOrderId(billId);
					query = pssBuyOrderFeign.getById(query);
					Map<String, String> pssBuyOrderMap = new HashMap<String, String>();
					pssBuyOrderMap.put("fileType", "0");
					pssBuyOrderMap.put("readOnly", "1");
					pssBuyOrderMap.put("saveBtn", "0");
					pssBuyOrderMap.put("fileName", fileName.replace("_batch", ""));
					pssBuyOrderMap.put("printBtn", "1");
					pssBuyOrderMap.put("supplierId", query.getSupplierId());
					pssBuyOrderMap.put("buyOrderId", query.getBuyOrderId());
					pssBuyOrderMap.put("buyOrderNo", query.getBuyOrderNo());
					pssBuyOrderMap.put("regNo", User.getRegNo(request));
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssBuyOrderMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "GHD" : {
					PssBuyBill query = new PssBuyBill();
					query.setBuyBillId(billId);
					query = pssBuyBillFeign.getById(query);
					Map<String, String> pssBuyBillMap = new HashMap<String, String>();
					pssBuyBillMap.put("fileType", "0");
					pssBuyBillMap.put("readOnly", "1");
					pssBuyBillMap.put("saveBtn", "0");
					pssBuyBillMap.put("fileName", fileName.replace("_batch", ""));
					pssBuyBillMap.put("printBtn", "1");
					pssBuyBillMap.put("supplierId", query.getSupplierId());
					pssBuyBillMap.put("buyBillId", query.getBuyBillId());
					pssBuyBillMap.put("buyBillNo", query.getBuyBillNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssBuyBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "GHTHD" : {
					PssBuyReturnBill query = new PssBuyReturnBill();
					query.setBuyReturnBillId(billId);
					query = pssBuyReturnBillFeign.getById(query);				
					Map<String, String> pssBuyReturnBillMap = new HashMap<String, String>();
					pssBuyReturnBillMap.put("fileType", "0");
					pssBuyReturnBillMap.put("readOnly", "1");
					pssBuyReturnBillMap.put("saveBtn", "0");
					pssBuyReturnBillMap.put("fileName", fileName.replace("_batch", ""));
					pssBuyReturnBillMap.put("printBtn", "1");
					pssBuyReturnBillMap.put("supplierId", query.getSupplierId());
					pssBuyReturnBillMap.put("buyReturnBillId", query.getBuyReturnBillId());
					pssBuyReturnBillMap.put("buyReturnBillNo", query.getBuyReturnBillNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssBuyReturnBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "XHDD" : {
					PssSaleOrder query = new PssSaleOrder();
					query.setSaleOrderId(billId);
					query = pssSaleOrderFeign.getById(query);
					Map<String, String> pssSaleOrderMap = new HashMap<String, String>();
					pssSaleOrderMap.put("fileType", "0");
					pssSaleOrderMap.put("readOnly", "1");
					pssSaleOrderMap.put("saveBtn", "0");
					pssSaleOrderMap.put("fileName", fileName.replace("_batch", ""));
					pssSaleOrderMap.put("printBtn", "1");
					pssSaleOrderMap.put("cusNo", query.getCusNo());
					pssSaleOrderMap.put("saleOrderId", query.getSaleOrderId());
					pssSaleOrderMap.put("saleOrderNo", query.getSaleOrderNo());
					pssSaleOrderMap.put("opNo", query.getSalerNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssSaleOrderMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}case "XHD" : {
					PssSaleBill query = new PssSaleBill();
					query.setSaleBillId(billId);
					query = pssSaleBillFeign.getById(query);
					Map<String, String> pssSaleBillMap = new HashMap<String, String>();
					pssSaleBillMap.put("fileType", "0");
					pssSaleBillMap.put("readOnly", "1");
					pssSaleBillMap.put("saveBtn", "0");
					pssSaleBillMap.put("fileName", fileName.replace("_batch", ""));
					pssSaleBillMap.put("printBtn", "1");
					pssSaleBillMap.put("cusNo", query.getCusNo());
					pssSaleBillMap.put("saleBillId", query.getSaleBillId());
					pssSaleBillMap.put("saleBillNo", query.getSaleBillNo());
					pssSaleBillMap.put("opNo", query.getSalerNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssSaleBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "XHTHD" : {
					PssSaleReturnBill query = new PssSaleReturnBill();
					query.setSaleReturnBillId(billId);
					query = pssSaleReturnBillFeign.getById(query);
					Map<String, String> pssSaleReturnBillMap = new HashMap<String, String>();
					pssSaleReturnBillMap.put("fileType", "0");
					pssSaleReturnBillMap.put("readOnly", "1");
					pssSaleReturnBillMap.put("saveBtn", "0");
					pssSaleReturnBillMap.put("fileName", fileName.replace("_batch", ""));
					pssSaleReturnBillMap.put("printBtn", "1");
					pssSaleReturnBillMap.put("cusNo", query.getCusNo());
					pssSaleReturnBillMap.put("saleReturnBillId", query.getSaleReturnBillId());
					pssSaleReturnBillMap.put("saleReturnBillNo", query.getSaleReturnBillNo());
					pssSaleReturnBillMap.put("opNo", query.getSalerNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssSaleReturnBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "DBD" : {
					PssAlloTransBill query = new PssAlloTransBill();
					query.setAlloTransId(billId);
					query = pssAlloTransBillFeign.getById(query);
					Map<String, String> pssAlloTransBillMap = new HashMap<String, String>();
					pssAlloTransBillMap.put("fileType", "0");
					pssAlloTransBillMap.put("readOnly", "1");
					pssAlloTransBillMap.put("saveBtn", "0");
					pssAlloTransBillMap.put("fileName", fileName.replace("_batch", ""));
					pssAlloTransBillMap.put("printBtn", "1");
					pssAlloTransBillMap.put("alloTransId", query.getAlloTransId());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssAlloTransBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "QTRKD" : {
					PssOtherStockInBill query = new PssOtherStockInBill();
					query.setOtherStockInId(billId);
					query = pssOtherStockInBillFeign.getById(query);
					Map<String, String> pssOtherStockInBillMap = new HashMap<String, String>();		
					pssOtherStockInBillMap.put("fileType", "0");
					pssOtherStockInBillMap.put("readOnly", "1");
					pssOtherStockInBillMap.put("saveBtn", "0");
					pssOtherStockInBillMap.put("fileName", fileName.replace("_batch", ""));
					pssOtherStockInBillMap.put("printBtn", "1");
					pssOtherStockInBillMap.put("supplierId", query.getSupplierId());
					pssOtherStockInBillMap.put("otherStockInId", query.getOtherStockInId());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssOtherStockInBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "QTCKD" : {
					PssOtherStockOutBill query = new PssOtherStockOutBill();
					query.setOtherStockOutId(billId);
					query = pssOtherStockOutBillFeign.getById(query);
					Map<String, String> pssOtherStockOutBillMap = new HashMap<String, String>();
					pssOtherStockOutBillMap.put("fileType", "0");
					pssOtherStockOutBillMap.put("readOnly", "1");
					pssOtherStockOutBillMap.put("saveBtn", "0");
					pssOtherStockOutBillMap.put("fileName", fileName.replace("_batch", ""));
					pssOtherStockOutBillMap.put("printBtn", "1");
					pssOtherStockOutBillMap.put("cusNo", query.getCusNo());
					pssOtherStockOutBillMap.put("otherStockOutId", query.getOtherStockOutId());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssOtherStockOutBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "SKD" : {
					PssReceiptBill query = new PssReceiptBill();
					query.setReceiptId(billId);
					query = pssReceiptBillFeign.getById(query);
					Map<String, String> pssReceiptBillMap = new HashMap<String, String>();
					pssReceiptBillMap.put("fileType", "0");
					pssReceiptBillMap.put("readOnly", "1");	
					pssReceiptBillMap.put("saveBtn", "0");
					pssReceiptBillMap.put("fileName", fileName.replace("_batch", ""));
					pssReceiptBillMap.put("printBtn", "1");
					pssReceiptBillMap.put("cusNo", query.getCusNo());
					pssReceiptBillMap.put("receiptId", query.getReceiptId());
					pssReceiptBillMap.put("opNo", query.getPayeeId());
					pssReceiptBillMap.put("receiptNo", query.getReceiptNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssReceiptBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "FKD" : {
					PssPaymentBill query = new PssPaymentBill();
					query.setPaymentId(billId);
					query = pssPaymentBillFeign.getById(query);
					Map<String, String> pssPaymentBillMap = new HashMap<String, String>();					
					pssPaymentBillMap.put("fileType", "0");
					pssPaymentBillMap.put("readOnly", "1");					
					pssPaymentBillMap.put("saveBtn", "0");
					pssPaymentBillMap.put("fileName", fileName.replace("_batch", ""));
					pssPaymentBillMap.put("printBtn", "1");
					pssPaymentBillMap.put("supplierId", query.getSupplierId());
					pssPaymentBillMap.put("opNo", query.getPayerId());
					pssPaymentBillMap.put("paymentId", query.getPaymentId());
					pssPaymentBillMap.put("paymentNo", query.getPaymentNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssPaymentBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					
					break;
				}
				case "QTSRD" : {
					PssOtherRecBill query = new PssOtherRecBill();
					query.setOtherRecId(billId);
					query = pssOtherRecBillFeign.getById(query);
					Map<String, String> pssOtherRecBillMap = new HashMap<String, String>();
					pssOtherRecBillMap.put("fileType", "0");
					pssOtherRecBillMap.put("readOnly", "1");
					pssOtherRecBillMap.put("saveBtn", "0");
					pssOtherRecBillMap.put("fileName", fileName.replace("_batch", ""));
					pssOtherRecBillMap.put("printBtn", "1");
					pssOtherRecBillMap.put("cusNo", query.getCusNo());
					pssOtherRecBillMap.put("otherRecId", query.getOtherRecId());
					pssOtherRecBillMap.put("accountId", query.getClearanceAccountId());
					pssOtherRecBillMap.put("otherRecNo", query.getOtherRecNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssOtherRecBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				case "QTZCD" : {
					PssOtherPayBill query = new PssOtherPayBill();
					query.setOtherPayId(billId);
					query = pssOtherPayBillFeign.getById(query);
					Map<String, String> pssOtherPayBillMap = new HashMap<String, String>();
					pssOtherPayBillMap.put("fileType", "0");
					pssOtherPayBillMap.put("readOnly", "1");
					pssOtherPayBillMap.put("saveBtn", "0");
					pssOtherPayBillMap.put("fileName", fileName.replace("_batch", ""));
					pssOtherPayBillMap.put("printBtn", "1");
					pssOtherPayBillMap.put("supplierId", query.getSupplierId());
					pssOtherPayBillMap.put("otherPayId", query.getOtherPayId());
					pssOtherPayBillMap.put("accountId", query.getClearanceAccountId());
					pssOtherPayBillMap.put("otherPayNo", query.getOtherPayNo());
					String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, pssOtherPayBillMap);
					poCnt = JSON.parseObject(poCntStr, PageContent.class);
					poPage=PageOfficePage.getAddPO(poCnt, request);
					break;
				}
				default:
				
			}
			
			jsonObject = new JSONObject();
			jsonObject.put("pageFieldMap", poPage.getPoCnt().getPageFieldMap());
			jsonObject.put("pageTableMap", poPage.getPoCnt().getPageTableMapList());

			dataMap.put("jsonObject", jsonObject);
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");

		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 购货单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssBuyBillAjax")
	@ResponseBody
	public Map<String, Object> getPssBuyBillAjax(String buyBillId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssBuyBill pssBuyBill = new PssBuyBill();
			pssBuyBill.setBuyBillId(buyBillId);
			pssBuyBill = pssBuyBillFeign.getById(pssBuyBill);
			
			Map<String, Object> pssBuyBillMap = new HashMap<String, Object>();

			pssBuyBillMap.put("fileType", "0");
			pssBuyBillMap.put("readOnly", "1");
			pssBuyBillMap.put("saveBtn", "0");
			pssBuyBillMap.put("fileName", fileName);
			pssBuyBillMap.put("printBtn", "1");
			pssBuyBillMap.put("supplierId", pssBuyBill.getSupplierId());
			pssBuyBillMap.put("buyBillId", pssBuyBill.getBuyBillId());
			pssBuyBillMap.put("buyBillNo", pssBuyBill.getBuyBillNo());
			
			dataMap.put("pssBuyBill", gson.toJson(pssBuyBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 购货订单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> getPssBuyOrderAjax(String buyOrderId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssBuyOrder pssBuyOrder = new PssBuyOrder();
			pssBuyOrder.setBuyOrderId(buyOrderId);
			pssBuyOrder = pssBuyOrderFeign.getById(pssBuyOrder);
			
			Map<String, Object> pssBuyOrderMap = new HashMap<String, Object>();
			
			pssBuyOrderMap.put("fileType", "0");
			pssBuyOrderMap.put("readOnly", "1");
			pssBuyOrderMap.put("saveBtn", "0");
			pssBuyOrderMap.put("fileName", fileName);
			pssBuyOrderMap.put("printBtn", "1");
			pssBuyOrderMap.put("supplierId", pssBuyOrder.getSupplierId());
			pssBuyOrderMap.put("buyOrderId", pssBuyOrder.getBuyOrderId());
			pssBuyOrderMap.put("buyOrderNo", pssBuyOrder.getBuyOrderNo());
			
			dataMap.put("pssOrderBill", gson.toJson(pssBuyOrderMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 购货退货单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> getPssBuyReturnBillAjax(String buyReturnBillId,String fileName) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
			pssBuyReturnBill.setBuyReturnBillId(buyReturnBillId);
			pssBuyReturnBill = pssBuyReturnBillFeign.getById(pssBuyReturnBill);
			
			Map<String, Object> pssBuyReturnBillMap = new HashMap<String, Object>();

			pssBuyReturnBillMap.put("fileType", "0");
			pssBuyReturnBillMap.put("readOnly", "1");
			pssBuyReturnBillMap.put("saveBtn", "0");
			pssBuyReturnBillMap.put("fileName", fileName);
			pssBuyReturnBillMap.put("printBtn", "1");
			pssBuyReturnBillMap.put("supplierId", pssBuyReturnBill.getSupplierId());
			pssBuyReturnBillMap.put("buyReturnBillId", pssBuyReturnBill.getBuyReturnBillId());
			pssBuyReturnBillMap.put("buyReturnBillNo", pssBuyReturnBill.getBuyReturnBillNo());
			
			dataMap.put("pssBuyReturnBill", gson.toJson(pssBuyReturnBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 销货单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssSaleBillAjax")
	@ResponseBody
	public Map<String, Object> getPssSaleBillAjax(String saleBillId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssSaleBill pssSaleBill = new PssSaleBill();
			pssSaleBill.setSaleBillId(saleBillId);
			pssSaleBill = pssSaleBillFeign.getById(pssSaleBill);
			
			Map<String, Object> pssSaleBillMap = new HashMap<String, Object>();

			pssSaleBillMap.put("fileType", "0");
			pssSaleBillMap.put("readOnly", "1");
			pssSaleBillMap.put("saveBtn", "0");
			pssSaleBillMap.put("fileName", fileName);
			pssSaleBillMap.put("printBtn", "1");
			pssSaleBillMap.put("cusNo", pssSaleBill.getCusNo());
			pssSaleBillMap.put("saleBillId", pssSaleBill.getSaleBillId());
			pssSaleBillMap.put("saleBillNo", pssSaleBill.getSaleBillNo());
			pssSaleBillMap.put("opNo", pssSaleBill.getSalerNo());
			
			dataMap.put("pssSaleBill", gson.toJson(pssSaleBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 销货订单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> getPssSaleOrderAjax(String saleOrderId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssSaleOrder pssSaleOrder = new PssSaleOrder();
			pssSaleOrder.setSaleOrderId(saleOrderId);
			pssSaleOrder = pssSaleOrderFeign.getById(pssSaleOrder);
			
			Map<String, Object> pssSaleOrderMap = new HashMap<String, Object>();
			
			pssSaleOrderMap.put("fileType", "0");
			pssSaleOrderMap.put("readOnly", "1");
			pssSaleOrderMap.put("saveBtn", "0");
			pssSaleOrderMap.put("fileName", fileName);
			pssSaleOrderMap.put("printBtn", "1");
			pssSaleOrderMap.put("cusNo", pssSaleOrder.getCusNo());
			pssSaleOrderMap.put("saleOrderId", pssSaleOrder.getSaleOrderId());
			pssSaleOrderMap.put("saleOrderNo", pssSaleOrder.getSaleOrderNo());
			pssSaleOrderMap.put("opNo", pssSaleOrder.getSalerNo());
			
			dataMap.put("pssSaleOrder", gson.toJson(pssSaleOrderMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 销货退货单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> getPssSaleReturnBillAjax(String saleReturnBillId,String fileName) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
			pssSaleReturnBill.setSaleReturnBillId(saleReturnBillId);
			pssSaleReturnBill = pssSaleReturnBillFeign.getById(pssSaleReturnBill);
			
			Map<String, Object> pssSaleReturnBillMap = new HashMap<String, Object>();

			pssSaleReturnBillMap.put("fileType", "0");
			pssSaleReturnBillMap.put("readOnly", "1");
			pssSaleReturnBillMap.put("saveBtn", "0");
			pssSaleReturnBillMap.put("fileName", fileName);
			pssSaleReturnBillMap.put("printBtn", "1");
			pssSaleReturnBillMap.put("cusNo", pssSaleReturnBill.getCusNo());
			pssSaleReturnBillMap.put("saleReturnBillId", pssSaleReturnBill.getSaleReturnBillId());
			pssSaleReturnBillMap.put("saleReturnBillNo", pssSaleReturnBill.getSaleReturnBillNo());
			
			dataMap.put("pssSaleReturnBill", gson.toJson(pssSaleReturnBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 调拨单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssAlloTransBillAjax")
	@ResponseBody
	public Map<String, Object> getPssAlloTransBillAjax(String alloTransId,String fileName) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
			pssAlloTransBill.setAlloTransId(alloTransId);
			pssAlloTransBill = pssAlloTransBillFeign.getById(pssAlloTransBill);
			
			Map<String, Object> pssAlloTransBillMap = new HashMap<String, Object>();
			
			pssAlloTransBillMap.put("fileType", "0");
			pssAlloTransBillMap.put("readOnly", "1");
			pssAlloTransBillMap.put("saveBtn", "0");
			pssAlloTransBillMap.put("fileName", fileName);
			pssAlloTransBillMap.put("printBtn", "1");
			pssAlloTransBillMap.put("alloTransId", pssAlloTransBill.getAlloTransId());
			
			dataMap.put("pssAlloTransBill", gson.toJson(pssAlloTransBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 其它入库单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssOtherStockInBillAjax")
	@ResponseBody
	public Map<String, Object> getPssOtherStockInBillAjax(String otherStockInId,String fileName) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
			pssOtherStockInBill.setOtherStockInId(otherStockInId);
			pssOtherStockInBill = pssOtherStockInBillFeign.getById(pssOtherStockInBill);
			
			Map<String, Object> pssOtherStockInBillMap = new HashMap<String, Object>();
			
			pssOtherStockInBillMap.put("fileType", "0");
			pssOtherStockInBillMap.put("readOnly", "1");
			pssOtherStockInBillMap.put("saveBtn", "0");
			pssOtherStockInBillMap.put("fileName", fileName);
			pssOtherStockInBillMap.put("printBtn", "1");
			pssOtherStockInBillMap.put("supplierId", pssOtherStockInBill.getSupplierId());
			pssOtherStockInBillMap.put("otherStockInId", pssOtherStockInBill.getOtherStockInId());
			
			dataMap.put("pssOtherStockInBill", gson.toJson(pssOtherStockInBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 其他出库单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssOtherStockOutBillAjax")
	@ResponseBody
	public Map<String, Object> getPssOtherStockOutBillAjax(String otherStockOutId,String fileName) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
			pssOtherStockOutBill.setOtherStockOutId(otherStockOutId);
			pssOtherStockOutBill = pssOtherStockOutBillFeign.getById(pssOtherStockOutBill);
			
			Map<String, Object> pssOtherStockOutBillMap = new HashMap<String, Object>();
			
			pssOtherStockOutBillMap.put("fileType", "0");
			pssOtherStockOutBillMap.put("readOnly", "1");
			
			pssOtherStockOutBillMap.put("saveBtn", "0");
			pssOtherStockOutBillMap.put("fileName", fileName);
			pssOtherStockOutBillMap.put("printBtn", "1");
			pssOtherStockOutBillMap.put("cusNo", pssOtherStockOutBill.getCusNo());
			pssOtherStockOutBillMap.put("otherStockOutId", pssOtherStockOutBill.getOtherStockOutId());
			
			dataMap.put("pssOtherStockOutBill", gson.toJson(pssOtherStockOutBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		
		return dataMap;
	}
	
	/**
	 * 收款单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssReceiptBillAjax")
	@ResponseBody
	public Map<String, Object> getPssReceiptBillAjax(String receiptId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssReceiptBill pssReceiptBill = new PssReceiptBill();
			pssReceiptBill.setReceiptId(receiptId);
			pssReceiptBill = pssReceiptBillFeign.getById(pssReceiptBill);
			
			Map<String, Object> pssReceiptBillMap = new HashMap<String, Object>();
			
			pssReceiptBillMap.put("fileType", "0");
			pssReceiptBillMap.put("readOnly", "1");
			
			pssReceiptBillMap.put("saveBtn", "0");
			pssReceiptBillMap.put("fileName", fileName);
			pssReceiptBillMap.put("printBtn", "1");
			pssReceiptBillMap.put("cusNo", pssReceiptBill.getCusNo());
			pssReceiptBillMap.put("receiptId", pssReceiptBill.getReceiptId());
			pssReceiptBillMap.put("opNo", pssReceiptBill.getPayeeId());
			pssReceiptBillMap.put("receiptNo", pssReceiptBill.getReceiptNo());
			
			dataMap.put("pssReceiptBill", gson.toJson(pssReceiptBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		
		return dataMap;
	}
	
	/**
	 * 付款单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssPaymentBillAjax")
	@ResponseBody
	public Map<String, Object> getPssPaymentBillAjax(String paymentId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssPaymentBill pssPaymentBill = new PssPaymentBill();
			pssPaymentBill.setPaymentId(paymentId);
			pssPaymentBill = pssPaymentBillFeign.getById(pssPaymentBill);
			
			Map<String, Object> pssPaymentBillMap = new HashMap<String, Object>();
			
			pssPaymentBillMap.put("fileType", "0");
			pssPaymentBillMap.put("readOnly", "1");
			
			pssPaymentBillMap.put("saveBtn", "0");
			pssPaymentBillMap.put("fileName", fileName);
			pssPaymentBillMap.put("printBtn", "1");
			pssPaymentBillMap.put("supplierId", pssPaymentBill.getSupplierId());
			pssPaymentBillMap.put("opNo", pssPaymentBill.getPayerId());
			pssPaymentBillMap.put("paymentId", pssPaymentBill.getPaymentId());
			pssPaymentBillMap.put("paymentNo", pssPaymentBill.getPaymentNo());
			
			dataMap.put("pssPaymentBill", gson.toJson(pssPaymentBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 其他收入单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> getPssOtherRecBillAjax(String otherRecId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
			pssOtherRecBill.setOtherRecId(otherRecId);
			pssOtherRecBill = pssOtherRecBillFeign.getById(pssOtherRecBill);
			
			Map<String, Object> pssOtherRecBillMap = new HashMap<String, Object>();
			
			pssOtherRecBillMap.put("fileType", "0");
			pssOtherRecBillMap.put("readOnly", "1");
			
			pssOtherRecBillMap.put("saveBtn", "0");
			pssOtherRecBillMap.put("fileName", fileName);
			pssOtherRecBillMap.put("printBtn", "1");
			pssOtherRecBillMap.put("cusNo", pssOtherRecBill.getCusNo());
			pssOtherRecBillMap.put("otherRecId", pssOtherRecBill.getOtherRecId());
			pssOtherRecBillMap.put("accountId", pssOtherRecBill.getClearanceAccountId());
			pssOtherRecBillMap.put("otherRecNo", pssOtherRecBill.getOtherRecNo());
			
			dataMap.put("pssOtherRecBill", gson.toJson(pssOtherRecBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		
		return dataMap;
	}
	
	/**
	 * 其他支出单打印(单表)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPssOtherPayBillAjax")
	@ResponseBody
	public Map<String, Object> getPssOtherPayBillAjax(String otherPayId,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Gson gson = new Gson();
		try{
			PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
			pssOtherPayBill.setOtherPayId(otherPayId);
			pssOtherPayBill = pssOtherPayBillFeign.getById(pssOtherPayBill);
			
			Map<String, Object> pssOtherPayBillMap = new HashMap<String, Object>();
			
			pssOtherPayBillMap.put("fileType", "0");
			pssOtherPayBillMap.put("readOnly", "1");
			
			pssOtherPayBillMap.put("saveBtn", "0");
			pssOtherPayBillMap.put("fileName", fileName);
			pssOtherPayBillMap.put("printBtn", "1");
			pssOtherPayBillMap.put("supplierId", pssOtherPayBill.getSupplierId());
			pssOtherPayBillMap.put("otherPayId", pssOtherPayBill.getOtherPayId());
			pssOtherPayBillMap.put("accountId", pssOtherPayBill.getClearanceAccountId());
			pssOtherPayBillMap.put("otherPayNo", pssOtherPayBill.getOtherPayNo());
			
			dataMap.put("pssOtherPayBill", gson.toJson(pssOtherPayBillMap));
			
			dataMap.put("flag", true);
			dataMap.put("msg", "成功");
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		
		return dataMap;
	}
	
}

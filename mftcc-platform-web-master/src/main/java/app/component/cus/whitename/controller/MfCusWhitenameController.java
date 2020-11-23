package  app.component.cus.whitename.controller;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.BizPubParm;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.whitename.entity.MfCusWhitename;
import app.component.cus.whitename.feign.MfCusWhitenameFeign;
import app.component.cus.whitename.feign.MfCusWhitenameInterfaceFeign;
import app.component.cus.whitename.util.WhitenameExcelUtil;
import app.component.pact.util.RementPayPoiExcelUtil;
import app.component.pfs.entity.AssetsReport;
import app.component.pfs.entity.CashflowsReport;
import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.ProfitsReport;
import app.component.pfs.util.PoiExportExcelUtil;
import app.component.pfs.util.PoiReadExcelUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: MfCusWhitenameAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 02 14:12:41 CST 2018
 **/
@Controller
@RequestMapping("/mfCusWhitename")
public class MfCusWhitenameController extends BaseFormBean{

	private Logger log = LoggerFactory.getLogger(MfCusWhitenameController.class);

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusWhitenameBo
	@Autowired
	private MfCusWhitenameFeign mfCusWhitenameFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfCusWhitenameInterfaceFeign mfCusWhitenameInterfaceFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/cus/whitename/MfCusWhitename_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusWhitename  mfCusWhitename = new MfCusWhitename();
		try {
			mfCusWhitename.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusWhitename.setCriteriaList(mfCusWhitename, ajaxData);//我的筛选
			mfCusWhitename.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(MfCusWhitename,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusWhitename", mfCusWhitename));
			ipage = mfCusWhitenameFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	//列表展示详情，单字段编辑
		@RequestMapping(value = "/listShowDetailAjax")
		@ResponseBody
		public Map<String,Object> listShowDetailAjax(String id) throws Exception {
			ActionContext.initialize(request,response);
			FormService formService = new FormService();
			Map<String,Object> dataMap=new HashMap<String,Object>();
			String cusType="";
			String formId="";
			String query="";
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(id);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			cusType=mfCusCustomer.getCusType();
			MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusWhitenameAction");
			if(mfCusFormConfig == null){
				
			}else{
				formId = mfCusFormConfig.getShowModelDef();
			}
			if(StringUtil.isEmpty(formId)){
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}else{
				Map<String,Object> formData = new HashMap<String,Object>();
				request.setAttribute("ifBizManger","3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				FormData formwhitenamedetail = formService.getFormData(formId);
				MfCusWhitename mfCusWhitename = new MfCusWhitename();
				mfCusWhitename.setId(id);
				mfCusWhitename = mfCusWhitenameFeign.getById(mfCusWhitename);
				getObjValue(formwhitenamedetail, mfCusWhitename,formData);
				String htmlStrCorp = jsonFormUtil.getJsonStr(formwhitenamedetail,"propertySeeTag",query);
				if(mfCusWhitename!=null){
					dataMap.put("formHtml", htmlStrCorp);
					dataMap.put("flag", "success");
				}else{
					dataMap.put("msg", "获取详情失败");
					dataMap.put("flag", "error");
				}
				dataMap.put("formData", mfCusWhitename);
			}
			return dataMap;
		}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		 Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formwhitenamebase = formService.getFormData("whitenamebase");
			getFormValue(formwhitenamebase, getMapByJson(ajaxData));
			if(this.validateFormData(formwhitenamebase)){
				MfCusWhitename  mfCusWhitename = new MfCusWhitename();
				setObjValue(formwhitenamebase, mfCusWhitename);
				//校验相同客户类型下证件号码需要唯一 20200212 xzp
				MfCusWhitename tempWhitename = mfCusWhitenameFeign.getByIdNumAndCusType(mfCusWhitename);
				if(null != tempWhitename){
					dataMap.put("flag", "error");
					dataMap.put("msg","白名单客户信息重复！");
				}else{
					mfCusWhitenameFeign.insert(mfCusWhitename);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData  formwhitenamedetail = formService.getFormData("whitenamedetail");
		getFormValue(formwhitenamedetail, getMapByJson(ajaxData));
		MfCusWhitename mfCusWhitenameJsp = new MfCusWhitename();
		setObjValue(formwhitenamedetail, mfCusWhitenameJsp);
		MfCusWhitename mfCusWhitename = mfCusWhitenameFeign.getById(mfCusWhitenameJsp);
		if(mfCusWhitename!=null){
			try{
				mfCusWhitename = (MfCusWhitename)EntityUtil.reflectionSetVal(mfCusWhitename, mfCusWhitenameJsp, getMapByJson(ajaxData));
				mfCusWhitenameFeign.update(mfCusWhitename);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formwhitenamedetail = formService.getFormData("whitenamedetail");
			getFormValue(formwhitenamedetail, getMapByJson(ajaxData));
			if(this.validateFormData(formwhitenamedetail)){
				MfCusWhitename mfCusWhitename = new MfCusWhitename();
				setObjValue(formwhitenamedetail, mfCusWhitename);
				//校验相同客户类型下证件号码需要唯一 20200212 xzp
				MfCusWhitename tempWhitename = mfCusWhitenameFeign.getByIdNumAndCusTypeExistsId(mfCusWhitename);
				if(null != tempWhitename){
					dataMap.put("flag", "error");
					dataMap.put("msg","白名单客户信息重复！");
				}else{
					mfCusWhitenameFeign.update(mfCusWhitename);
					dataMap.put("flag", "success");
					dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formwhitenamedetail = formService.getFormData("whitenamedetail");
		MfCusWhitename mfCusWhitename = new MfCusWhitename();
		mfCusWhitename.setId(id);
		mfCusWhitename = mfCusWhitenameFeign.getById(mfCusWhitename);
		getObjValue(formwhitenamedetail, mfCusWhitename,formData);
		if(mfCusWhitename!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusWhitename mfCusWhitename = new MfCusWhitename();
		mfCusWhitename.setId(id);
		try {
			mfCusWhitenameFeign.delete(mfCusWhitename);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		List<ParmDic> parmList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
		for (Iterator iterator = parmList.iterator(); iterator.hasNext();) {
			ParmDic parmDic = (ParmDic) iterator.next();
			// 保留个人和企业,remark 中存放的是客户基础类型（base_type）
			if ( ! (BizPubParm.CUS_BASE_TYPE_CORP.equals(parmDic.getRemark()) || BizPubParm.CUS_BASE_TYPE_PERSON.equals(parmDic.getRemark()))) {
				iterator.remove();
			}
		}
		FormData formwhitenamebase = formService.getFormData("whitenamebase");
		model.addAttribute("formwhitenamebase", formwhitenamebase);
		model.addAttribute("query", "");
		return "/component/cus/whitename/MfCusWhitename_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formwhitenamedetail = formService.getFormData("whitenamedetail");
		 getFormValue(formwhitenamedetail);
		 MfCusWhitename mfCusWhitename = new MfCusWhitename();
		mfCusWhitename.setId(id);
		mfCusWhitename = mfCusWhitenameFeign.getById(mfCusWhitename);
		 getObjValue(formwhitenamedetail, mfCusWhitename);
		 model.addAttribute("formwhitenamedetail", formwhitenamedetail);
		model.addAttribute("query", "");
		return "/component/cus/whitename/MfCusWhitename_Detail";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formwhitenamebase = formService.getFormData("whitenamebase");
		 getFormValue(formwhitenamebase);
		 MfCusWhitename mfCusWhitename = new MfCusWhitename();
		 setObjValue(formwhitenamebase, mfCusWhitename);
		 mfCusWhitenameFeign.insert(mfCusWhitename);
		 getObjValue(formwhitenamebase, mfCusWhitename);
		 this.addActionMessage(model, "保存成功");
		 List<MfCusWhitename> mfCusWhitenameList = (List<MfCusWhitename>)mfCusWhitenameFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formwhitenamebase", formwhitenamebase);
		model.addAttribute("mfCusWhitenameList", mfCusWhitenameList);
		model.addAttribute("query", "");
		return "/component/cus/whitename/MfCusWhitename_Insert";
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
			FormService formService = new FormService();
		 FormData formwhitenamebase = formService.getFormData("whitenamebase");
		 getFormValue(formwhitenamebase);
		 boolean validateFlag = this.validateFormData(formwhitenamebase);
	}
	/**
	 * 删除
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusWhitename mfCusWhitename = new MfCusWhitename();
		mfCusWhitename.setId(id);;
		mfCusWhitenameFeign.delete(mfCusWhitename);
		return getListPage(model);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
			FormService formService = new FormService();
		 FormData formwhitenamedetail = formService.getFormData("whitenamedetail");
		 getFormValue(formwhitenamedetail);
		 boolean validateFlag = this.validateFormData(formwhitenamedetail);
	}

	//白名单模板导入
	@SuppressWarnings("unused")
	@RequestMapping(value = "/uploadAjax")
	@ResponseBody
	public Map<String, Object> uploadAjax(String ajaxData, String whitenameUploadFileName,@RequestParam(value="cusFinUpload")  MultipartFile cusFinUpload) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		String whitename = "白名单";
		boolean flag = true;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		whitenameUploadFileName=cusFinUpload.getOriginalFilename();
		//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
		if((!whitenameUploadFileName.endsWith(".xlsx")) && (!whitenameUploadFileName.endsWith(".xls"))){
			dataMap.put("flag", "error");
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage(whitename));
			return dataMap;
		}
		String uploadFinPath = ymlConfig.getUpload().get("uploadFinFilePath");
		//根据属性名获取upload.properties中对应属性值
		//上传后的excel文件名字
		String newFileName = UUID.randomUUID().toString() + whitenameUploadFileName.substring(whitenameUploadFileName.lastIndexOf("."));
		//上传后excel文件的所在文件夹
		String allPath = uploadFinPath + File.separator + newFileName;
		File f = new File(allPath);
		cusFinUpload.transferTo(f);
		try {
			boolean sign = true;
			MfCusWhitename tempWhitename = new MfCusWhitename();
			StringBuffer dataMsg = new StringBuffer();
			String prompt = null;
			//解析excel;
			WhitenameExcelUtil whitenameExcelUtil = new WhitenameExcelUtil();
			List<MfCusWhitename> mfCusWhitenameList = whitenameExcelUtil.resolveExcel(allPath);
			if(mfCusWhitenameList.size()>0 && mfCusWhitenameList != null){
				int nullCount = 0;
				int insertCount = 0;
				for(MfCusWhitename mfCusWhitename : mfCusWhitenameList){
					if(StringUtil.isEmpty(mfCusWhitename.getCusName()) || StringUtil.isEmpty(mfCusWhitename.getCusType()) ){
						sign = false;
						nullCount ++;
					}else {
						tempWhitename = mfCusWhitenameFeign.getByIdNumAndCusType(mfCusWhitename);
						if(tempWhitename == null){
							mfCusWhitenameFeign.insert(mfCusWhitename);
							insertCount ++;
						}
					}
				}
				if(sign){
					dataMap.put("flag", "success");
					dataMap.put("sign", sign);
					dataMap.put("mfCusWhitename", mfCusWhitenameList);
					dataMap.put("dataMsg", MessageEnum.SUCCEED_UPLOAD.getMessage(whitename));
				}else {
					dataMap.put("flag", "success");
					dataMap.put("sign", sign);
					dataMap.put("dataMsg","成功导入"+insertCount+"条，"+nullCount+"条数据不完整，导入失败，请检查模板中数据！");
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("dataMsg", "导入的文件没有数据！");
				return dataMap;
			}
		} catch (Exception e) {
			log.error("白名单管理模板上传失败",e);
			dataMap.put("flag", "error");
			dataMap.put("dataMsg", MessageEnum.FAILED_UPLOAD_FILE.getMessage(whitename));
		
		}
		return dataMap;
	}

	/**
	 *	白名单导入模板下载
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadTemplate")
	@ResponseBody
	public Map<String, Object> downloadTemplate() throws Exception{
		Map<String, Object> dataMap = new HashMap();
		String path = PropertiesUtil.getUploadProperty("uploadFinModelFilePath");
		String filePath = path+File.separator+"whiteNameTemplate.xlsx";
		File file = new File(filePath);
		// 如果文件存在，则进行下载
		if (file.exists()) {
			// 配置文件下载
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			// 下载文件能正常显示中文
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("白名单管理模板.xlsx", "UTF-8"));
			// 实现文件下载
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
				return null;
			} catch (Exception e) {
				log.error("白名单模板下载失败",e);
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "模板文件不存在！");
			return dataMap;
		}
		return dataMap;
	}
}

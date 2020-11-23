package app.component.pact.repayplan.controller;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.struts.ActionContext;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.pact.repayplan.feign.MfRementpayPlanFeign;
import app.component.pact.util.RementPayPoiExcelUtil;
import app.tech.upload.UploadUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;


@Controller
@RequestMapping("/mfRementpayPlan")
public class MfRementpayPlanController{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfRementpayPlanFeign mfRementpayPlanFeign;	// 注入还款计划
	
	
	
		/**
		 * 下载还款计划表模板
		 * @author 段泽宇
		 * @date2018.4.12
		 */
		@RequestMapping(value = "/exportExcelAjax")
		@ResponseBody
		public Map<String, Object> exportExcelAjax(String ajaxData, String cusNo) throws Exception {
			ActionContext.initialize(request, response);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			try {
				//找到模板
				String path = mfRementpayPlanFeign.doExportExcelModel(cusNo);
				dataMap.put("flag", "success");
				dataMap.put("exportFlag", "success");
				dataMap.put("path", path);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("导出"));
				if (path == null) {
					dataMap.put("exportFlag", "error");
					dataMap.put("msg", MessageEnum.NO_FILE.getMessage("模板导出路径"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", e.getMessage());
			}
			return dataMap;
		}
		
		//还款计划表上传控制
		@RequestMapping(value = "/uploadAjax")
		@ResponseBody
		public Map<String, Object> uploadAjax(String cusFinUploadFileName,@RequestParam(value="cusFinUpload")  MultipartFile cusFinUpload) throws Exception{
			ActionContext.initialize(request,response);
			String cusNo = request.getParameter("cusNo");
			boolean flag = true;
			Map<String, Object> dataMap = new HashMap<String, Object>();
			cusFinUploadFileName=cusFinUpload.getOriginalFilename();
			//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
			if(cusFinUploadFileName.endsWith(".exe")||cusFinUploadFileName.endsWith(".jsp")||cusFinUploadFileName.endsWith(".bat")
					||cusFinUploadFileName.endsWith(".dll")||cusFinUploadFileName.endsWith(".com")||cusFinUploadFileName.endsWith(".sh")||cusFinUploadFileName.endsWith(".py")){
				dataMap.put("flag", "error");
				dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
				return dataMap;
			}
			//根据属性名获取upload.properties中对应属性值
			String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
			//上传后的excel文件名字
			String newFileName = cusNo + UUID.randomUUID().toString() + cusFinUploadFileName.substring(cusFinUploadFileName.lastIndexOf("."));
			//上传后excel文件的所在文件夹
			String allPath = uploadFinPath + File.separator + newFileName;
			File f = new File(allPath);
			cusFinUpload.transferTo(f);
			try {
				//解析excel;
				RementPayPoiExcelUtil rementPayPoiExcelUtil = new RementPayPoiExcelUtil();
				List<MfRepayPlan> mfRepayPlans = rementPayPoiExcelUtil.resolveExcel(allPath);
				dataMap.put("mfRepayPlans", mfRepayPlans);
				dataMap.put("dataMsg", "文件上传成功");
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				dataMap.put("flag", "error");	
				dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
			} finally {
				if(flag){
					dataMap.put("flag", "success");
				}
			}
			return dataMap;
		}
		
		
}

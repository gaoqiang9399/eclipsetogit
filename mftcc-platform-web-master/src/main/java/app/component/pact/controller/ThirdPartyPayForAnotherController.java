package app.component.pact.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.pact.entity.ThirdPartyPayForAnother;
import app.component.pact.feign.ThirdPartyPayForAnotherFeign;
import app.component.pfs.util.PoiExportExcelUtil;
import app.util.toolkit.Ipage;

/**
 * Title: ThirdPartyPayForAnotherAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 15:16:07 CST 2017
 **/
@Controller
@RequestMapping("/thirdPartyPayForAnother")
public class ThirdPartyPayForAnotherController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入ThirdPartyPayForAnotherBo
	@Autowired
	private ThirdPartyPayForAnotherFeign thirdPartyPayForAnotherFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/ThirdPartyPayForAnother_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ThirdPartyPayForAnother thirdPartyPayForAnother = new ThirdPartyPayForAnother();
		try {
			thirdPartyPayForAnother.setCustomQuery(ajaxData);// 自定义查询参数赋值
			thirdPartyPayForAnother.setCriteriaList(thirdPartyPayForAnother, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = thirdPartyPayForAnotherFeign.findByPage(ipage, thirdPartyPayForAnother);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/exportListAjax")
	@ResponseBody
	public Map<String, Object> exportListAjax(String exportFincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ThirdPartyPayForAnother thirdPartyPayForAnother = new ThirdPartyPayForAnother();
		FileOutputStream os = null;
		FileInputStream fileInputStream = null;
		try {
			dataMap = new HashMap<String, Object>();
			// 模板原始文件
			PoiExportExcelUtil excelUtil = new PoiExportExcelUtil();
			String filePath = excelUtil.getFilePath("");
			// 源文件
			String templateFileName = filePath + "fuiouPayExport.xls";
			// 目标文件名
			String destFileName = filePath + "fuiouPayCollect_" + System.currentTimeMillis() + ".xls";
			String destFileName2 = filePath + "fuiou" + ".xls";
			thirdPartyPayForAnother.setFincId(exportFincId);
			thirdPartyPayForAnotherFeign.exportForExcel(thirdPartyPayForAnother, templateFileName, destFileName);
			File file = new File(destFileName);
			fileInputStream = new FileInputStream(file);
			os = new FileOutputStream(destFileName2);
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode(file.getName(), "utf-8"));
			int fileLen = fileInputStream.available();
			response.setContentLength(fileLen);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = fileInputStream.read(buf)) != -1) {
				os.write(len);
			}
			dataMap.put("flag", "success");
			dataMap.put("filePath", destFileName);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (null != os)
				{os.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (null != fileInputStream)
				{fileInputStream.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataMap;
	}

}
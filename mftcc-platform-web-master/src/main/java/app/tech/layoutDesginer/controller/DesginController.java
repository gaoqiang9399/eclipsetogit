package app.tech.layoutDesginer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.struts.BaseFormBean;

import app.tech.layoutDesginer.util.FreemarkerUtil;
import app.tech.layoutDesginer.util.LayoutDesginerUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/desgin")
public class DesginController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

//	private File upload;
//	private String uploadFileName;
//	private String uploadContentType;
//	private String query;
//	private String editSts;
//	private String data;
//	private String blockDatas;
//	private String fileName;
//	private String msg;
//	private String jspPath;
//	private String ftlPath;
//	private String formId;
//	private String formType;
	private JSONObject json;

//	{
//		query = "";
		// request = ServletActionContext.getRequest();
		// response = ServletActionContext.getResponse();
		
//	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(String editSts, String data, String blockDatas, String fileName) {
		JSONArray arr = JSONArray.fromObject(data);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blockDatas", blockDatas);
		map.put("datas", arr);
		map.put("cellDatas", data);
		FreemarkerUtil freemarkerUtil = new FreemarkerUtil();
		long temp = System.currentTimeMillis();
		String jspPath = request.getSession().getServletContext().getRealPath("/tech/layoutDesginer/jsp");
		String fn = jspPath + "\\" + temp + ".jsp";
		String msg;
		if ("update".equals(editSts)) {
			String filePath = jspPath + "\\" + fileName;
			fn = filePath;
			msg = fileName;
		} else {
			msg = temp + ".jsp";
		}
		String ftlPath = request.getServletContext().getRealPath("/tech/layoutDesginer/ftl");
		freemarkerUtil.fprint("desgin.ftl", map, ftlPath, fn);
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "/openDesginer"/*,headers = MediaType.MULTIPART_FORM_DATA_VALUE*/)// consumes="multipart/form-data", produces="multipart/form-data")
	public JSONObject openDesginer(@RequestParam("upload") MultipartFile /*File */upload/*, @RequestParam String uploadFileName*/) throws IOException {
		json = new JSONObject();
		List<String> list = FreemarkerUtil.readFile(upload.getInputStream());//((CommonsMultipartFile)upload).getFileItem().get); 
		if (list.size() > 0) {
			String str = list.get(0);
			str = str.substring(str.indexOf("["), str.lastIndexOf("]") + 1);
			json.put("cellDatas", str);
			str = list.get(1);
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			json.put("blockDatas", str);
			json.put("fileName", upload.getOriginalFilename());//uploadFileName
		}
		return json;
	}

	@RequestMapping(value = "/desginModeler")
	public String desginModeler(String fileName) {
		if (fileName == null) {
			return "/tech/layoutDesginer/layoutDesginer";
		}
		String jspPath = request.getSession().getServletContext().getRealPath("/tech/layoutDesginer/jsp");
		String filePath = jspPath + "\\" + fileName;
		File jspFile = new File(filePath);
		if (!jspFile.exists()) {
			return "/tech/layoutDesginer/layoutDesginer";
		} else {
			List<String> list = FreemarkerUtil.readFile(jspFile);
			String str = list.get(0);
			str = str.substring(str.indexOf("["), str.lastIndexOf("]") + 1);
			request.setAttribute("cellDatas", str);
			str = list.get(1);
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			request.setAttribute("blockDatas", str);
			request.setAttribute("fileName", fileName);
			return "/tech/layoutDesginer/layoutDesginer";
		}
	}
	@ResponseBody
	@RequestMapping(value = "/downLoad")
	public void downLoad(String fileName) {
		String jspPath = request.getSession().getServletContext().getRealPath("/tech/layoutDesginer/jsp");
		String filePath = jspPath + "\\" + fileName;
		LayoutDesginerUtil.doDownLoad(filePath, fileName, response);
		new File(filePath).delete();
	}

	
}

package app.component.interfaces.mobileinterface.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

@Controller
@RequestMapping("/appAPKDownload")
public class AppAPKDownloadController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//FIXME 
	@RequestMapping("/getInputStream")
	@ResponseBody
	public InputStream getInputStream(String fileName) throws Exception {
		ActionContext.initialize(request, response);
		byte[] bytes = fileName.getBytes("ISO8859-1");
		fileName = new String(bytes, "utf-8");
		// tomcat路径
		String path = AppAPKDownloadController.class.getResource("/").toURI().getPath();
		String tomcatPath = path.substring(0, path.indexOf("webapps"));
		File file = new File(tomcatPath + "appapk" + File.separator + fileName);
		InputStream inputStream = new FileInputStream(file);
		return inputStream;
	}
}

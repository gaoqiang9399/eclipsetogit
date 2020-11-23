package app.component.doc.controller;

import app.component.doc.entity.DocManage;
import app.component.doc.feign.DocFeign;
import cn.mftcc.util.PropertiesUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: DocManageController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Jan 26 11:18:01 GMT 2016
 **/
@Controller
@RequestMapping("/docTest")
public class DocTestController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocFeign docFeign;
	@RequestMapping("/fileExistence")
	@ResponseBody
	public Map<String, String> fileExistence() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, String> dataMap = new HashMap<String, String>();
		List<DocManage> docManageList = docFeign.getListForPackDownDocManage(new DocManage());
		if (docManageList!=null){
			for (int i = 0; i < docManageList.size(); i++) {
				DocManage docManage = docManageList.get(i);
				String filePath = PropertiesUtil.getUploadProperty("uploadFilePath")+docManage.getDocAddr();
				File file = new File(filePath);
				if (!file.exists()){
					dataMap.put(docManage.getDocNo(),filePath);
				}
			}
		}

		return dataMap;
	}
}
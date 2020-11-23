package app.component.ncfgroup.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.ncfgroup.feign.NcfLinkFaceFeign;

/**
 * Created by dark on 2017/9/6.
 */
@Controller
@RequestMapping("/ncfLinkFace")
public class NcfLinkFaceController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private NcfLinkFaceFeign ncfLinkFaceFeign;

	@ResponseBody
	@RequestMapping(value = "/livenessAjax")
	public Map<String, Object> livenessAjax(String liveness_data_fileFileName, String cusNo, File liveness_data_file,
			String liveness_data_fileContentType) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		try {
			// 上传的文件类型 必须为视频格式时avi、wmv、mpeg、mp4、mov、mkv、flv、f4v、m4v、rmvb、rm、3gp
			String contentType = liveness_data_fileFileName;
			if (contentType.endsWith(".avi") || contentType.endsWith(".wmv") || contentType.endsWith(".mpeg")
					|| contentType.endsWith(".mp4") || contentType.endsWith(".mov") || contentType.endsWith(".mkv")
					|| contentType.endsWith(".flv") || contentType.endsWith(".f4v") || contentType.endsWith(".m4v")
					|| contentType.endsWith(".rmvb") || contentType.endsWith(".rm") || contentType.endsWith(".3gp")) {
				boolean flag = ncfLinkFaceFeign.isRealInPerson(cusNo, liveness_data_file, liveness_data_fileFileName,
						liveness_data_fileContentType);
				if (flag) {
					dataMap.put("errorCode", "000000");
					dataMap.put("errorMsg", "活体识别通过");
				} else {
					dataMap.put("errorCode", "000002");
					dataMap.put("errorMsg", "活体识别未通过，请重试");
				}
			} else {
				dataMap.put("errorCode", "000002");
				dataMap.put("errorMsg", "视频格式不支持，请重试");
			}
		} catch (Exception e) {
			// logger.error("活体识别出错", e);
			dataMap.put("errorCode", "000001");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/livenessStatusAjax")
	public Map<String, Object> livenessStatusAjax(String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		try {
			boolean flag = ncfLinkFaceFeign.hasRealInPerson(cusNo);
			dataMap.put("errorCode", "000000");
			dataMap.put("data", flag);
		} catch (Exception e) {
			// logger.error("活体识别出错", e);
			dataMap.put("errorCode", "000001");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

}

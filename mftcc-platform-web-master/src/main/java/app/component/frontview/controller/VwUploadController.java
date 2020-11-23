package app.component.frontview.controller;

import app.component.frontview.util.UploadUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Title: VwBannerManageAction.java Description:banner管理
 * 
 * @author:yht@dhcc.com.cn
 * @Wed Apr 26 11:06:06 CST 2017
 **/
@Controller
@RequestMapping("/vwUpload")
public class VwUploadController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/**
	 * 前端设置上传文件(前端交易共用)
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload(MultipartFile upload,String folder) throws Exception {
		String originalFilename=upload.getOriginalFilename();
		String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") );
		String date=DateUtil.getDate();
		folder="vw/"+folder+"/"+date;
		String filename=WaterIdUtil.getWaterId()+fileType;
		Map<String,Object> resultMap=UploadUtils.uploadVwImg(upload,folder,filename);
		return new Gson().toJson(resultMap);
	}

}

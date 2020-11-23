package app.component.forewarning.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.forewarning.entity.MfSysMsgInfo;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysMsgInfoBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 01 17:15:38 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfSysMsgInfoFeign {

	@RequestMapping("/mfSysMsgInfo/insert")
	public void insert(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/delete")
	public void delete(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/update")
	public void update(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/getById")
	public MfSysMsgInfo getById(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfSysMsgInfo") MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/getUnReadMsgCount")
	public Long getUnReadMsgCount(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/setMsgRead")
	public void setMsgRead(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	@RequestMapping("/MfExamIndex/insertMsgInfo")
	public void insertMsgInfo(@RequestBody cn.mftcc.function.bean.MfSysMsgInfo mfSysMsgInfo) throws Exception;

}

package  app.component.sys.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.sys.entity.MfApplyInsertConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfApplyInsertConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 22 14:33:04 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfApplyInsertConfigFeign {
	@RequestMapping(value = "/mfApplyInsertConfig/insert", method = RequestMethod.POST)
	public void insert(@RequestBody MfApplyInsertConfig mfApplyInsertConfig) throws Exception;
	@RequestMapping(value = "/mfApplyInsertConfig/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfApplyInsertConfig mfApplyInsertConfig) throws Exception;
	@RequestMapping(value = "/mfApplyInsertConfig/update", method = RequestMethod.POST)
	public void update(@RequestBody MfApplyInsertConfig mfApplyInsertConfig) throws Exception;
	@RequestMapping(value = "/mfApplyInsertConfig/getById", method = RequestMethod.POST)
	public MfApplyInsertConfig getById(@RequestBody MfApplyInsertConfig mfApplyInsertConfig) throws Exception;
	@RequestMapping(value = "/mfApplyInsertConfig/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(Ipage ipage) throws Exception;
	/**
	 * 根据登陆用户获取进件跳转地址
	 * @return
	 */
	@RequestMapping(value = "/mfApplyInsertConfig/getReqUrlByUser", method = RequestMethod.POST)
	public Map<String, Object> getReqUrlByUser(@RequestParam("opNo") String opNo) throws Exception;
}

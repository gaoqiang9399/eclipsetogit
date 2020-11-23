package app.component.pact.protect.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.protect.entity.MfAssetHandleInfo;
import app.util.toolkit.Ipage;

/**
 * Title: MfAssetHandleInfoBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAssetHandleInfoFeign {

	@RequestMapping(value = "/mfAssetHandleInfo/insert")
	public MfAssetHandleInfo insert(@RequestBody MfAssetHandleInfo mfAssetHandleInfo) throws Exception;

	@RequestMapping(value = "/mfAssetHandleInfo/delete")
	public void delete(@RequestBody MfAssetHandleInfo mfAssetHandleInfo) throws Exception;

	@RequestMapping(value = "/mfAssetHandleInfo/update")
	public void update(@RequestBody MfAssetHandleInfo mfAssetHandleInfo) throws Exception;

	@RequestMapping(value = "/mfAssetHandleInfo/getById")
	public MfAssetHandleInfo getById(@RequestBody MfAssetHandleInfo mfAssetHandleInfo) throws Exception;

	@RequestMapping(value = "/mfAssetHandleInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfAssetHandleInfo") MfAssetHandleInfo mfAssetHandleInfo) throws Exception;

	@RequestMapping(value = "/mfAssetHandleInfo/initInputData")
	public Map<String, Object> initInputData(@RequestBody MfAssetHandleInfo mfAssetHandleInfo) throws Exception;

}

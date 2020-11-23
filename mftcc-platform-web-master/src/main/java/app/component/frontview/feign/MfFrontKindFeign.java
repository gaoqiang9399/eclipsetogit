package app.component.frontview.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.frontview.entity.MfFrontKind;
import app.util.toolkit.Ipage;

/**
 * Title: MfFrontKindBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 23 17:08:22 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfFrontKindFeign {

	@RequestMapping("/mfFrontKind/insert")
	public void insert(@RequestBody MfFrontKind mfFrontKind) throws Exception;

	@RequestMapping("/mfFrontKind/delete")
	public void delete(@RequestBody MfFrontKind mfFrontKind) throws Exception;

	@RequestMapping("/mfFrontKind/update")
	public MfFrontKind update(@RequestBody MfFrontKind mfFrontKind) throws Exception;

	@RequestMapping("/mfFrontKind/getById")
	public MfFrontKind getById(@RequestBody MfFrontKind mfFrontKind) throws Exception;

	@RequestMapping("/mfFrontKind/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

//	@RequestMapping("/mfFrontKind/getAllListByPage")
//	public Ipage getAllListByPage(@RequestBody Ipage ipage);

	@RequestMapping("/mfFrontKind/insert1")
	public Map<String, Object> insert1(@RequestBody String kindNo) throws Exception;
	@RequestMapping("/mfFrontKind/getAllListByPage")
	public Ipage getAllListByPage(@RequestBody Ipage ipage);


}

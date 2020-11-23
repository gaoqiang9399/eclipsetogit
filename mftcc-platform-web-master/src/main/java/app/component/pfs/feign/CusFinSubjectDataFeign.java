package app.component.pfs.feign;

import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.CusFinSubjectBalInfo;
import app.component.pfs.entity.CusFinSubjectData;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title:CusFinSubjectDataBoImplImpl.java Description:科目余额表业务操作
 * 
 * @author:@LJW
 * @Mon May 09 05:30:24 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinSubjectDataFeign {
	@RequestMapping(value = "/cusFinSubjectData/update")
	public void update(@RequestBody Map<String,Object> parmMap) throws Exception ;

	@RequestMapping(value = "/cusFinSubjectData/getByCusNoDateTypeForList")
	public Map<String,Object> getByCusNoDateTypeForList(@RequestBody CusFinSubjectData cusFinSubjectData) throws Exception ;

	@RequestMapping(value = "/cusFinSubjectData/getAttentionList")
	public List<CusFinSubjectBalInfo> getAttentionList(@RequestBody CusFinSubjectData cusFinSubjectData) throws Exception;

	@RequestMapping(value = "/cusFinSubjectData/getLastLevList")
	public List<CusFinSubjectData> getLastLevList(@RequestBody CusFinSubjectData cusFinSubjectData) throws Exception;
}

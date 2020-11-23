package  app.component.cus.feign;

import java.util.List;

import app.component.paph.entity.ApiReturnRecord;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusHighInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusHighInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue May 31 09:01:54 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusHighInfoFeign {
	@RequestMapping("/mfCusHighInfo/insert")
	public void insert(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	
	@RequestMapping("/mfCusHighInfo/insertForApp")
	public MfCusHighInfo insertForApp(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	
	@RequestMapping("/mfCusHighInfo/delete")
	public void delete(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	
	@RequestMapping("/mfCusHighInfo/update")
	public void update(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	
	@RequestMapping("/mfCusHighInfo/getById")
	public MfCusHighInfo getById(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	
	@RequestMapping("/mfCusHighInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusHighInfo/findByEntity")
	public List<MfCusHighInfo> findByEntity(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;;
	/**
	 * @方法描述： 查询关系人列表
	 * @param mfCusHighInfo
	 * @return
	 * @throws Exception
	 * @author 陈迪
	 * @date 2020年01月04日 下午5:20:00
	 */
	@RequestMapping("/mfCusHighInfo/getCorpPartyList")
	public List<MfCusHighInfo> getCorpPartyList(@RequestBody MfCusHighInfo mfCusHighInfo)throws Exception;
}

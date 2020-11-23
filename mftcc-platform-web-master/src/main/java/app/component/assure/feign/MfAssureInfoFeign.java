package  app.component.assure.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfAssureInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 20 15:11:16 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfAssureInfoFeign {
	
	@RequestMapping(value = "/mfAssureInfo/insert")
	public MfAssureInfo insert (@RequestBody Map<String, Object> dataMap) throws Exception;
	@RequestMapping(value = "/mfAssureInfo/insertForApp")
	public Map<String, Object> insertForApp (@RequestBody Map<String, Object> dataMap,@RequestParam("mfAssureInfo")  MfAssureInfo mfAssureInfo) throws Exception;
	/**
	 * 
	 * 方法描述： pad端单独添加担保不提交申请
	 * @param dataMap
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * MfAssureInfo
	 * @author YaoWenHao
	 * @date 2017-12-16 下午3:35:26
	 */
	@RequestMapping(value = "/mfAssureInfo/insertOnlyForApp")
	public MfAssureInfo insertOnlyForApp (@RequestBody Map<String, Object> dataMap,@RequestParam("mfAssureInfo")  MfAssureInfo mfAssureInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 新增保证信息，只新增不关联
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * MfAssureInfo
	 * @author 沈浩兵
	 * @date 2017-11-8 下午9:10:48
	 */
	@RequestMapping(value = "/mfAssureInfo/insertAssureInfo")
	public MfAssureInfo insertAssureInfo(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
	
	@RequestMapping(value = "/mfAssureInfo/delete")
	public void delete(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
	
	@RequestMapping(value = "/mfAssureInfo/update")
	public void update(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
	/***
	 * 
	 * 方法描述： pad端修改修改保证人关联修改其他信息
	 * @param mfAssureInfo
	 * @throws Exception
	 * void
	 * @author YaoWenHao
	 * @date 2017-12-17 上午11:44:48
	 */
	@RequestMapping(value = "/mfAssureInfo/updateForPad")
	public void updateForPad(@RequestBody MfAssureInfo mfAssureInfo,@RequestParam("appId") String appId) throws Exception;
	@RequestMapping(value = "/mfAssureInfo/getById")
	public MfAssureInfo getById(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
	
	@RequestMapping(value = "/mfAssureInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfAssureInfo") MfAssureInfo mfAssureInfo) throws Exception;
	
	@RequestMapping(value = "/mfAssureInfo/getMfAssureInfoList")
	public List<MfAssureInfo>  getMfAssureInfoList(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
	
	@RequestMapping(value = "/mfAssureInfo/getMultiBusList")
	public List<MfBusApplyAssureInfo> getMultiBusList(@RequestBody MfAssureInfo mfAssureInfo)throws Exception;
	
	@RequestMapping(value = "/mfAssureInfo/findAssureInfoById")
	public List<MfAssureInfo>  findAssureInfoById(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
}

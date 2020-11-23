package  app.component.collateral.movable.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableUnusualTailInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableUnusualTailInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 16 18:04:45 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableUnusualTailInfoFeign {
	
	@RequestMapping(value = "/mfMoveableUnusualTailInfo/insert")
	public void insert(@RequestBody MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableUnusualTailInfo/delete")
	public void delete(@RequestBody MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableUnusualTailInfo/update")
	public void update(@RequestBody MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableUnusualTailInfo/getById")
	public MfMoveableUnusualTailInfo getById(@RequestBody MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableUnusualTailInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableUnusualTailInfo") MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 
	 * @param mfMoveableUnusualTailInfo
	 * @return
	 * @throws Exception
	 * List<MfMoveableUnusualTailInfo>
	 * @author 沈浩兵
	 * @date 2017-6-19 下午6:13:01
	 */
	@RequestMapping(value = "/mfMoveableUnusualTailInfo/getUnusualTailInfoList")
	public List<MfMoveableUnusualTailInfo> getUnusualTailInfoList(@RequestBody MfMoveableUnusualTailInfo mfMoveableUnusualTailInfo) throws Exception;
	
}

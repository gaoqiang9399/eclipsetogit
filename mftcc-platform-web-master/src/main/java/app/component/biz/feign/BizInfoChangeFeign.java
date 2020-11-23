package  app.component.biz.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.biz.entity.BizInfoChange;
import app.util.toolkit.Ipage;

/**
* Title: BizInfoChangeBo.java
* Description:
* @author:@dhcc.com.cn
* @Wed Apr 20 06:08:57 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface BizInfoChangeFeign {
	
	@RequestMapping(value = "/bizInfoChange/insert")
	public void insert(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/delete")
	public void delete(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/update")
	public void update(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/getById")
	public BizInfoChange getById(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("bizInfoChange") BizInfoChange bizInfoChange) throws ServiceException;

	@RequestMapping(value = "/bizInfoChange/getAll")
	public List<BizInfoChange> getAll(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/insertInfo")
	public void insertInfo(@RequestBody BizInfoChange bizInfoChange,@RequestParam("subList")List<String> subList) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/updateInfo")
	public void updateInfo(@RequestBody BizInfoChange bizInfoChange,@RequestParam("subList")List<String> subList) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/getSubCnt")
	public int getSubCnt(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChange/getTopFive")
	public List<BizInfoChange> getTopFive(@RequestBody BizInfoChange bizInfoChange) throws ServiceException;

	/**
	 * 
	 * 方法描述：获取客户历史变更信息的前几条 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 * List<BizInfoChange>
	 * @author zhs
	 * @date 2017-3-28 下午3:01:09
	 */
	@RequestMapping(value = "/bizInfoChange/getTopList")
	public List<BizInfoChange> getTopList(@RequestBody Map<String, Object> paramMap) throws ServiceException;
}

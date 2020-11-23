package app.component.nmd.feign;

import java.util.List;
import java.util.Map;

import app.component.nmd.entity.NmdAreaProvinces;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.NmdArea;
import app.util.toolkit.Ipage;

/**
 * Title: NmdAreaBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Apr 05 03:30:59 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface NmdAreaFeign {

	@RequestMapping("/nmdArea/insert")
	public void insert(@RequestBody NmdArea nmdArea) throws ServiceException;

	@RequestMapping("/nmdArea/delete")
	public void delete(@RequestBody NmdArea nmdArea) throws ServiceException;

	@RequestMapping("/nmdArea/update")
	public void update(@RequestBody NmdArea nmdArea) throws ServiceException;

	@RequestMapping("/nmdArea/getById")
	public NmdArea getById(@RequestBody NmdArea nmdArea) throws ServiceException;

	@RequestMapping("/nmdArea/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/nmdArea/getAll")
	public List<NmdArea> getAll(@RequestBody NmdArea nmdArea) throws ServiceException;
	
	@RequestMapping("/nmdArea/getByLev")
	public List<NmdArea> getByLev(@RequestBody NmdArea nmdArea) throws ServiceException;

	/**
	 * 查询所有(@RequestBody 过滤直辖市下的县区)
	 */
	@RequestMapping("/nmdArea/getAllExceptDirectCity")
	public List<NmdArea> getAllExceptDirectCity(@RequestBody NmdArea nmdArea) throws ServiceException;
	
	@RequestMapping("/nmdArea/getLv")
	public List<NmdArea> getLv(@RequestBody NmdArea nmdArea) throws Exception;
	@RequestMapping("/nmdArea/getChild")
	public List<NmdArea> getChild(@RequestBody NmdArea nmdArea) throws Exception;
	/**
	 * 方法描述： 根据区域编号获取所有上级名称集合
	 * @param nmdArea
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年8月1日 下午10:25:35
	 */
	@RequestMapping(value = "/nmdArea/getAllLevById",produces="text/html;charset=utf-8")
	public String getAllLevById(@RequestBody NmdArea nmdArea) throws Exception;


	@RequestMapping("/nmdArea/getLvProvinces")
	public List<NmdAreaProvinces> getLvProvinces(@RequestBody NmdAreaProvinces nmdAreaProvinces) throws Exception;

	@RequestMapping("/nmdArea/getChildProvinces")
	public List<NmdAreaProvinces> getChildProvinces(@RequestBody NmdAreaProvinces nmdAreaProvinces) throws Exception;

	@RequestMapping("/nmdArea/getIfLocal")
    public Map<String,Object> getIfLocal(@RequestBody NmdArea nmdArea) throws Exception;
}

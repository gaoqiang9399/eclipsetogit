package  app.component.model.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.MfTemplateModelRel;
import app.util.toolkit.Ipage;

/**
 * Title: MfTemplateModelRelBo.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 22 11:17:10 CST 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface MfTemplateModelRelFeign {

	@RequestMapping(value = "/mfTemplateModelRel/insert")
	public void insert(@RequestBody MfTemplateModelRel mfTemplateModelRel) throws ServiceException;

	@RequestMapping(value = "/mfTemplateModelRel/delete")
	public void delete(@RequestBody MfTemplateModelRel mfTemplateModelRel) throws ServiceException;

	@RequestMapping(value = "/mfTemplateModelRel/update")
	public void update(@RequestBody MfTemplateModelRel mfTemplateModelRel) throws ServiceException;

	@RequestMapping(value = "/mfTemplateModelRel/getById")
	public MfTemplateModelRel getById(@RequestBody MfTemplateModelRel mfTemplateModelRel) throws ServiceException;

	@RequestMapping(value = "/mfTemplateModelRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfTemplateModelRel/insertTemplateModelRel")
	public void insertTemplateModelRel(@RequestBody Map<String, Object> dataMap)throws ServiceException;

	@RequestMapping(value = "/mfTemplateModelRel/getTemplateModelRelList")
	public List<MfTemplateModelRel> getTemplateModelRelList(@RequestBody MfTemplateModelRel mfTemplateModelRel)throws ServiceException;
	/**
	 *
	 * 方法描述： 获得是否保存过文档及保存的文档信息
	 * @param mfTemplateModelRel
	 * @param realFilePath 真实webapps的路径
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-8-7 上午10:00:55
	 */
	@RequestMapping(value = "/mfTemplateModelRel/getIfSaveTemplateInfo")
	public Map<String,Object> getIfSaveTemplateInfo(@RequestBody MfTemplateModelRel mfTemplateModelRel,@RequestParam("realFilePath") String realFilePath)throws Exception;


	/**
	 *@desc 查询空白模板打印个数
	 *@author lwq
	 *@date 2018/9/28 11:18
	 *@parm []
	 *@return int
	 **/
	@RequestMapping(value = "/mfTemplateModelRel/getMfTemplateModelRelCnt")
	public int getMfTemplateModelRelCnt(@RequestBody MfTemplateModelRel mfTemplateModelRel)throws ServiceException;
}

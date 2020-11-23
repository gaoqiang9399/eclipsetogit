package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.entity.MfUniqueVal;
import app.util.toolkit.Ipage;

/**
* Title: MfUniqueValBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 04 18:01:52 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfUniqueValFeign {
	@RequestMapping(value = "/mfUniqueVal/insert")
	public void insert(@RequestBody MfUniqueVal mfUniqueVal) throws Exception;
	
	@RequestMapping(value = "/mfUniqueVal/delete")
	public void delete(@RequestBody MfUniqueVal mfUniqueVal) throws Exception;
	
	@RequestMapping(value = "/mfUniqueVal/update")
	public void update(@RequestBody MfUniqueVal mfUniqueVal) throws Exception;
	
	@RequestMapping(value = "/mfUniqueVal/getById")
	public MfUniqueVal getById(@RequestBody MfUniqueVal mfUniqueVal) throws Exception;
	
	@RequestMapping(value = "/mfUniqueVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述：获取唯一值的list数据
	 * @param mfUniqueVal
	 * @return
	 * @throws ServiceException
	 * List<MfUniqueVal>
	 * @author 沈浩兵
	 * @date 2016-10-14 上午10:41:11
	 */
	@RequestMapping(value = "/mfUniqueVal/doCheckUniqueAjax",produces="text/html;charset=utf-8")
	public String doCheckUniqueAjax(@RequestBody MfUniqueVal mfUniqueVal,@RequestParam("saveType")String saveType) throws Exception;
	/**
	 * @Description:身份证唯一性检查 
	 * @param mfUniqueVal
	 * @return
	 * @throws ServiceException
	 * @author: 李伟
	 * @date: 2017-10-26 下午3:19:53
	 */
	@RequestMapping(value = "/mfUniqueVal/doCheckUniqueIdNum",produces="text/html;charset=utf-8")
	public String doCheckUniqueIdNum(@RequestBody MfUniqueVal mfUniqueVal) throws Exception;

	/**
	 * 
	 * 方法描述： 客户名称重复性检测
	 * @param mfUniqueVal
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-1-18 下午3:08:55
	 */
	@RequestMapping(value = "/mfUniqueVal/doCheckUniqueCusName",produces="text/html;charset=utf-8")
	public String doCheckUniqueCusName(@RequestBody MfUniqueVal mfUniqueVal)throws Exception;
	
}

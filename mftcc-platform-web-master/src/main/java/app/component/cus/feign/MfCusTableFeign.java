package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.entity.MfCusTable;
import app.util.toolkit.Ipage;

/**
* Title: MfCusTableBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 21 14:17:01 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusTableFeign {
	
	@RequestMapping(value = "/mfCusTable/insert")
	public void insert(@RequestBody MfCusTable mfCusTable) throws Exception;
	
	@RequestMapping(value = "/mfCusTable/delete")
	public void delete(@RequestBody MfCusTable mfCusTable) throws Exception;
	
	@RequestMapping(value = "/mfCusTable/update")
	public void update(@RequestBody MfCusTable mfCusTable) throws Exception;
	
	@RequestMapping(value = "/mfCusTable/getById")
	public MfCusTable getById(@RequestBody MfCusTable mfCusTable) throws Exception;
	
	@RequestMapping(value = "/mfCusTable/getByCusNo2")
	public MfCusTable getByCusNo2(@RequestParam("cusNo") String cusNo ,@RequestParam("tableName")String tableName) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得客户信息完善情况
	 * @param cusNo
	 * @return
	 * @throws ServiceException
	 * MfCusTable
	 * @author 沈浩兵
	 * @date 2016-12-29 下午8:48:46
	 */
	@RequestMapping(value = "/mfCusTable/getByCusNo")
	public List<MfCusTable> getByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
	
	@RequestMapping(value = "/mfCusTable/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusTable/getList")
	public List<MfCusTable> getList(@RequestBody MfCusTable mfCusTable) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 获得除了基本信息外的其他表单信息
	 * date 2016-9-30
	 * @param mfCusTable
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfCusTable/getListExceptBase")
	public List<MfCusTable> getListExceptBase(@RequestBody MfCusTable mfCusTable) throws Exception;

	/**
	 * 
	 * 方法描述： 插入与业务相关的客户表单配置信息
	 * @param kindNo
	 * @param appId
	 * @throws Exception
	 * void
	 * @author zhs
	 * @param cusNo 
	 * @date 2017-9-22 下午3:34:20
	 */
	@RequestMapping(value = "/mfCusTable/insertBizCusTable")
	public void insertBizCusTable(@RequestParam("kindNo") String kindNo,@RequestParam("appId") String appId,@RequestParam("cusNo") String cusNo) throws Exception;
	
	
}

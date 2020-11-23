package  app.component.calc.fee.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfBusAppFeeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 28 10:07:52 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusAppFeeFeign {
	
	@RequestMapping(value = "/mfBusAppFee/insert")
	public void insert(@RequestBody MfBusAppFee mfBusAppFee) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFee/delete")
	public void delete(@RequestBody MfBusAppFee mfBusAppFee) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFee/update")
	public void update(@RequestBody MfBusAppFee mfBusAppFee) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFee/getById")
	public MfBusAppFee getById(@RequestBody MfBusAppFee mfBusAppFee) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFee/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFee/getList")
	public List<MfBusAppFee> getList(@RequestBody MfBusAppFee mfBusAppFee) throws ServiceException;
	
	/**
	 * @author czk
	 * @Description: 在生成合同时，根据appId，更新mf_bus_app_fee表中的pactId
	 * mf_bus_app_fee是业务-费用关联表，业务刚申请时只和appId关联了，当生成合同时，需要将pactId更新上
	 * date 2016-11-19
	 */
	@RequestMapping(value = "/mfBusAppFee/updateBusAppFeePactId")
	public void updateBusAppFeePactId(@RequestBody String appId,@RequestParam("pactId") String pactId) throws Exception;

	/**
	 * 方法描述：根据申请号，kindno itemno更新费用信息  
	 * @param mfBusAppFee
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-6-12 下午6:08:50
	 */
	@RequestMapping(value = "/mfBusAppFee/updateAppByappId")
	public void updateAppByappId(@RequestBody MfBusAppFee mfBusAppFee) throws Exception;
    /**
     * 
     * 方法描述：通过appId获取业务与费用关联表信息 按照收取顺序获取 先收取的在上面
     * @param busAppFee
     * @return
     * @throws Exception 
     * List<MfBusAppFee>
     * @author WD
     * @date 2018-1-5 下午5:27:30
     */
	@RequestMapping(value = "/mfBusAppFee/getMfBusAppFeeList")
	public List<MfBusAppFee> getMfBusAppFeeList(@RequestBody MfBusAppFee busAppFee)throws Exception;
	/**
	 * 
	 * 方法描述： 获得借据费用标准列表
	 * @param mfBusAppFee
	 * @return
	 * @throws ServiceException
	 * List<MfBusAppFee>
	 * @author 沈浩兵
	 * @date 2018年5月15日 上午11:53:11
	 */
	@RequestMapping(value = "/mfBusAppFee/getFincBusAppFeeList")
	public List<MfBusAppFee> getFincBusAppFeeList(@RequestBody MfBusAppFee mfBusAppFee,@RequestParam("fincId") String fincId) throws Exception;

	@RequestMapping(value = "/mfBusAppFee/doCommit")
	public Result doCommit(@RequestBody MfBusAppFee mfBusAppFee)throws Exception;

	
}

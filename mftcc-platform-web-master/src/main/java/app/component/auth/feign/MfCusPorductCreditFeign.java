package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.auth.entity.MfCusPorductCredit;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPorductCreditBo.java
* Description:授信-产品业务控制
* @author:LJW
* @Mon Feb 27 10:45:47 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusPorductCreditFeign {
	
	@RequestMapping(value = "/mfCusPorductCredit/insert")
	public void insert(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/delete")
	public void delete(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	/**
	 * 根据授信申请id删除授信产品信息
	 * @author LJW
	 * date 2017-3-20
	 */
	@RequestMapping(value = "/mfCusPorductCredit/deleteByCreditId")
	public void deleteByCreditId(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws Exception;
	
	@RequestMapping(value = "/mfCusPorductCredit/update")
	public void update(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/getById")
	public MfCusPorductCredit getById(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusPorductCredit") MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	/**
	 * 根据授信申请查询
	 * @author LJW
	 * date 2017-2-28
	 */
	@RequestMapping(value = "/mfCusPorductCredit/getByCreditAppId")
	public List<MfCusPorductCredit> getByCreditAppId(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/getByCreditAppIdDesc")
	public List<MfCusPorductCredit> getByCreditAppIdDesc(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得产品额度信息，新增授信和授信调整
	 * @param mfCusPorductCredit
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-1 下午4:49:21
	 */
	@RequestMapping(value = "/mfCusPorductCredit/getPorductCreditApplyAndAdj")
	public Map<String,Object> getPorductCreditApplyAndAdj(@RequestBody String creditAppId,@RequestParam("adjustAppId") String adjustAppId) throws ServiceException;
	
	/**
	 * 批量插入
	 * @author LJW
	 * date 2017-3-18
	 */
	@RequestMapping(value = "/mfCusPorductCredit/insertByBatch")
	public void insertByBatch(@RequestBody List<MfCusPorductCredit> mfCusPorductCredits) throws ServiceException;
	
	
	@RequestMapping(value = "/mfCusPorductCredit/getMfCusPorductCredit")
	public MfCusPorductCredit getMfCusPorductCredit(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws Exception;
	
	@RequestMapping(value = "/mfCusPorductCredit/getMfCusPorductCreditList")
	public List<MfCusPorductCredit> getMfCusPorductCreditList(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws Exception;
	
	@RequestMapping(value = "/mfCusPorductCredit/updateById")
	public void updateById(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/updateByKindNoAppId")
	public void updateByKindNoAppId(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/getMfCusPorductCreditListByCusNo")
	public List<MfCusPorductCredit> getMfCusPorductCreditListByCusNo(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;
	
	@RequestMapping(value = "/mfCusPorductCredit/deleteKindInsert")
	public void deleteKindInsert(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws ServiceException;

	@RequestMapping(value = "/mfCusPorductCredit/getListByCreditAppIdAndKindNo")
	public List<MfCusPorductCredit> getListByCreditAppIdAndKindNo(@RequestBody MfCusPorductCredit mfCusPorductCredit) throws Exception;
}

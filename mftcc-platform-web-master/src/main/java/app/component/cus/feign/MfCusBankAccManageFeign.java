package  app.component.cus.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.entity.MfCusBankAccManage;
import app.util.toolkit.Ipage;

/**
* Title: MfCusBankAccManageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 01 09:39:43 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusBankAccManageFeign {
	
	@RequestMapping("/mfCusBankAccManage/insert")
	public MfCusBankAccManage insert(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/insertForApp")
	public MfCusBankAccManage insertForApp(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/delete")
	public void delete(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/update")
	public void update(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/getById")
	public MfCusBankAccManage getById(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/getMfCusBankAccListByCusNo")
	public List<MfCusBankAccManage> getMfCusBankAccListByCusNo(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	/**
	 * 方法描述： 直接删除银行卡信息  不留记录
	 * @param mfCusBankAccManage
	 * @throws ServiceException
	 * void
	 * @author YuShuai
	 * @date 2017-12-19 下午7:21:34
	 */
	@RequestMapping("/mfCusBankAccManage/deleteMfCusBankAccManage")
	public void deleteMfCusBankAccManage(@RequestBody MfCusBankAccManage mfCusBankAccManage)throws Exception;
	
	@RequestMapping("/mfCusBankAccManage/getMfCusBankAccListByCusNoPage")
	public Ipage getMfCusBankAccListByCusNoPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * <p>Title: signReq</p>  
	 * <p>Description:预签约接口 </p>  
	 * @param parmMap
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月6日 上午9:52:15
	 */
	@RequestMapping("/thirdPayGateWayInterfaceConT/signReq")
	public Map<String,String> signReq(@RequestBody Map<String, Object> parmMap)throws Exception;
	
	/**
	 * 
	 * <p>Title: signConfirmReq</p>  
	 * <p>Description: 签约确认接口</p>  
	 * @param parmMap
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月6日 上午9:57:21
	 */
	@RequestMapping("/thirdPayGateWayInterfaceConT/signConfirmReq")
	public Map<String,String> signConfirmReq(@RequestBody Map<String, Object> parmMap)throws Exception;
	
	
	
}

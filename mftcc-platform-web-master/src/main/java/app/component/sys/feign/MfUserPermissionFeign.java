package app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.MfUserPermission;
import app.util.toolkit.Ipage;

/**
* Title: MfUserPermissionBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Aug 21 15:13:44 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfUserPermissionFeign {
	@RequestMapping("/mfUserPermission/insert")
	public void insert(@RequestBody MfUserPermission mfUserPermission) throws ServiceException;
	@RequestMapping("/mfUserPermission/delete")
	public void delete(@RequestBody MfUserPermission mfUserPermission) throws ServiceException;
	@RequestMapping("/mfUserPermission/update")
	public void update(@RequestBody MfUserPermission mfUserPermission) throws ServiceException;
	@RequestMapping("/mfUserPermission/getById")
	public MfUserPermission getById(@RequestBody MfUserPermission mfUserPermission) throws ServiceException;
	@RequestMapping("/mfUserPermission/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 方法描述： 获取操作员的数据源
	 * @param ipage
	 * @param mfUserPermission
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YuShuai
	 * @param ajaxData 
	 * @param ifFilterFlag 是否过滤当前操作员1-是 0-否(parm_dic.key_name = 'IF_FILTER_FLAG')。此值如果为null或为空则以数据库字典项值为准
	 * @date 2017-8-21 下午3:49:48
	 */
	@RequestMapping("/mfUserPermission/getOpDataSource")
	public Ipage getOpDataSource(@RequestBody Ipage ipage)throws Exception;
	
	/**
	 * 
	 * 方法描述： 
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 曲植
	 * @date 2018年8月17日 下午5:24:10
	 */
	@RequestMapping("/mfUserPermission/getAmountSource")
	public Ipage getAmountSource(@RequestBody Ipage ipage)throws Exception;

	/**
	 *
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfUserPermission/getContractLoan")
	public Ipage getContractLoan(@RequestBody Ipage ipage)throws Exception;



	/**
	 * 方法描述： 获取操作员的数据源
	 * @param ipage,brNo
	 * @param mfUserPermission
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YuShuai
	 * @param ajaxData 
	 * @param ifFilterFlag 是否过滤当前操作员1-是 0-否(parm_dic.key_name = 'IF_FILTER_FLAG')。此值如果为null或为空则以数据库字典项值为准
	 * @date 2017-8-21 下午3:49:48
	 */
	@RequestMapping("/mfUserPermission/getSiblingOpDataSource")
	public Ipage getSiblingOpDataSource(@RequestBody Ipage ipage, @RequestParam("brNo") String brNo)throws Exception;

	/**
	 * 方法描述： 获取共同借款人数据源
	 * @param ipage
	 * @param mfUserPermission
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YuShuai
	 * @param ajaxData 
	 * @date 2017-8-21 下午7:22:55
	 */
	@RequestMapping("/mfUserPermission/getPerDataSource")
	public Ipage getPerDataSource(@RequestBody Ipage ipage)throws Exception;

	/**
	 * 非选择组件共同借款人
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfUserPermission/getCoborrDataAjax")
	public Ipage getCoborrDataAjax(@RequestBody Ipage ipage)throws Exception;
	
	/**
	 * @Description:  获取保证人数据源
	 * @param ipage
	 * @param mfUserPermission
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-11-8 下午5:06:24
	 */
	@RequestMapping("/mfUserPermission/getAssureDataSource")
	public Ipage getAssureDataSource(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 获取渠道商数据源
	 * @param ipage
	 * @param mfUserPermission
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfUserPermission/getChannelSource")
	public Ipage getChannelSource(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 获取核心企业
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年7月23日 下午9:42:22
	 */
	@RequestMapping("/mfUserPermission/getCoreCompanySource")
	public Ipage getCoreCompanySource(@RequestBody Ipage ipage)throws Exception;
	
	/**
	 * @Description:选择组件获取企业客户和个人客户的方法 
	 * @return
	 * @throws Exception
	 * @author: 刘东迎
	 * @date: 2018-9-14 下午5:05:50
	 */
	@RequestMapping("/mfUserPermission/getCustomerDataSource")
	public Ipage getCustomerDataSource(@RequestBody Ipage ipage)throws Exception;

	/**
	 * @Description:选择组件获取企业客户和个人客户的方法（逾期客户）
	 * @return
	 * @throws Exception
	 * @author: 刘东迎
	 * @date: 2018-9-14 下午5:05:50
	 */
	@RequestMapping("/mfUserPermission/getCustomerDataSourceByOver")
	public Ipage getCustomerDataSourceByOver(@RequestBody Ipage ipage)throws Exception;
	@RequestMapping("/mfUserPermission/getCustomerDataSourceAjaxByCompensatory")
	public Ipage getCustomerDataSourceAjaxByCompensatory(@RequestBody Ipage ipage)throws Exception;
	/**
	 * @方法描述： 根据客户类型，查找客户信息
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年10月22日 上午8:48:21
	 */
	@RequestMapping("/mfUserPermission/getCustomerInfo")
	public Ipage getCustomerInfo(@RequestBody Ipage ipage)throws Exception;

	@RequestMapping("/mfUserPermission/getRelCorpInfo")
	Ipage getRelCorpInfo(Ipage ipage)throws Exception;
}

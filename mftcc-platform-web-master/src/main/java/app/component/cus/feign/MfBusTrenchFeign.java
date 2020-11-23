package  app.component.cus.feign;

import java.util.List;
import java.util.Map;

import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.entity.MfBusTrench;
import app.util.toolkit.Ipage;

/**
* Title: MfBusTrenchBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 29 17:36:57 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfBusTrenchFeign {
	
	@RequestMapping(value = "/mfBusTrench/insert")
	public void insert(@RequestBody MfBusTrench mfBusTrench) throws Exception;
	
	@RequestMapping(value = "/mfBusTrench/delete")
	public void delete(@RequestBody MfBusTrench mfBusTrench) throws Exception;
	
	@RequestMapping(value = "/mfBusTrench/update")
	public void update(@RequestBody MfBusTrench mfBusTrench) throws Exception;
	
	@RequestMapping(value = "/mfBusTrench/getById")
	public MfBusTrench getById(@RequestBody MfBusTrench mfBusTrench) throws Exception;
	
	@RequestMapping(value = "/mfBusTrench/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 获取当前操作员所有的渠道来源
	 * @param ipage
	 * @param mfBusTrench
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author YuShuai
	 * @date 2017-7-29 下午5:45:14
	 */
	@RequestMapping(value = "/mfBusTrench/getChannelListPage")
	public Ipage getChannelListPage(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 根据uid获取信息
	 * @param busTrence
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusTrench/getByUId")
	public MfBusTrench getByUId(@RequestBody MfBusTrench busTrence) throws Exception;
	/**
	 * 资料完整度更新
	 * @param mfBusTrench
	 * @return 
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusTrench/updateInfIntegrity")
	public String updateInfIntegrity(@RequestBody MfBusTrench mfBusTrench) throws Exception;
	/**
	 * 完整一个表格之后的资料完整度计算
	 * @param mfBusTrench
	 * @return 
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusTrench/doPubInfIntegrity")
	public String doPubInfIntegrity(@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 获取历史业务统计
	 * @param trenchUid
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusTrench/getTrenchBusHisAjax")
	public Map<String, String> getTrenchBusHisAjax(@RequestParam("trenchUid") String trenchUid)  throws Exception;
	/**
	 * 新增特殊客户身份的客户时需要初始化的事情
	 * @param cusType：客户类型好
	 * @param cusNo:客户参与系统的唯一编号 一般为uId
	 * @param completeTableName:此次新增已经完善的表
	 * @param agencyName:业务身份，主要为了在信息变更记录里展现新增了什么
	 * @param cusName:客户名称
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusTrench/initCusSysOper")
	public void initCusSysOper(@RequestParam("cusType") String cusType,@RequestParam("cusNo") String cusNo,@RequestParam("completeTableName") String completeTableName,
			@RequestParam("agencyName") String agencyName,@RequestParam("cusName") String cusName,@RequestParam("contactsName") String contactsName,
			@RequestParam("contactsTel") String contactsTel) throws Exception;
	/**
	 * 
	 * 方法描述： 获取所有的渠道信息
	 * @return
	 * @throws Exception
	 * List<MfBusTrench>
	 * @author lwq
	 * @date 2018-1-19 下午4:24:37
	 */
	@RequestMapping(value = "/mfBusTrench/getMfBusTrenchList")
	public List<MfBusTrench> getMfBusTrenchList()throws Exception;
	@RequestMapping(value = "/mfBusTrench/findTrenchWarnByPage")
	public Ipage findTrenchWarnByPage(@RequestBody Ipage ipage)throws Exception;

	/**
	 * 方法描述： 获得渠道及其子渠道编号字符串
	 * 
	 * @param mfBusTrench
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2018-3-8 下午2:43:25
	 */
	@RequestMapping(value = "/mfBusTrench/getTrenchChildStr")
	public String getTrenchChildStr(@RequestBody MfBusTrench mfBusTrench) throws Exception;

	/**
	 * 方法描述： 获得子渠道列表数据
	 * 
	 * @param mfBusTrench
	 * @return
	 * @throws Exception List<MfBusTrench>
	 * @author 沈浩兵
	 * @date 2018-3-18 下午4:27:40
	 */
	@RequestMapping(value = "/mfBusTrench/getChildTrenchList")
	public List<MfBusTrench> getChildTrenchList(@RequestBody MfBusTrench mfBusTrench) throws Exception;

	@RequestMapping(value = "/mfBusTrench/getBusTrenchList")
	public List<MfBusTrench> getBusTrenchList(@RequestBody MfBusTrench mfBusTrench) throws Exception;
	
	/**
	 * 方法描述： 根据操作员编号获取渠道商
	 * @param opNo
	 * @return
	 * @throws Exception
	 * MfBusTrench
	 * @author 仇招
	 * @date 2018年8月27日 上午11:43:32
	 */
	@RequestMapping(value = "/mfBusTrench/getByOpNo")
	public MfBusTrench getByOpNo(@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 通过信用村的地址（行政区域）查找该行政区域下的客户
	 * @param mfBusTrench
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusTrench/getCustomerList")
    public List<MfCusPersBaseInfo> getCustomerListAjax(MfBusTrench mfBusTrench)throws Exception;
}

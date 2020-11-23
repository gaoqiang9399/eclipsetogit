package  app.component.collateral.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.pact.entity.MfBusFollowPact;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfBusCollateralDetailRelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Apr 12 14:38:49 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCollateralDetailRelFeign {
	
	@RequestMapping(value = "/mfBusCollateralDetailRel/insert")
	public void insert(@RequestBody MfBusCollateralDetailRel mfBusCollateralDetailRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralDetailRel/delete")
	public void delete(@RequestBody MfBusCollateralDetailRel mfBusCollateralDetailRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralDetailRel/update")
	public void update(@RequestBody MfBusCollateralDetailRel mfBusCollateralDetailRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralDetailRel/getById")
	public MfBusCollateralDetailRel getById(@RequestBody MfBusCollateralDetailRel mfBusCollateralDetailRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralDetailRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusCollateralDetailRel") MfBusCollateralDetailRel mfBusCollateralDetailRel) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据押品业务关联编号，获得关联押品
	 * @param mfBusCollateralDetailRel
	 * @return
	 * @throws ServiceException
	 * List<MfBusCollateralDetailRel>
	 * @author 沈浩兵
	 * @date 2017-4-13 下午2:22:16
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getCollateralDetailRel")
	public List<MfBusCollateralDetailRel> getCollateralDetailRel(@RequestBody MfBusCollateralDetailRel mfBusCollateralDetailRel) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根绝业务编号获得关联的押品信息
	 * @param relId
	 * @return
	 * @throws ServiceException
	 * List<MfBusCollateralDetailRel>
	 * @author 沈浩兵
	 * @date 2017-4-28 下午3:56:24
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getCollateralDetailRelList", method=RequestMethod.POST)
	public List<MfBusCollateralDetailRel> getCollateralDetailRelList(@RequestParam("relId") String relId,@RequestParam("collateralType") String collateralType) throws ServiceException;
	/**
	 * 
	 * 方法描述： 修改押品状态
	 * @param appId
	 * @param pleSts
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-8 上午9:56:40
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/updatePledgeInfoSts")
	public void updatePledgeInfoSts(@RequestParam("appId") String appId,@RequestParam("pleSts") String pleSts) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得多选押品数据源
	 * @param busCollateralId
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-5-17 下午4:40:11
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getPledgeData")
	public JSONArray getPledgeData(@RequestParam("busCollateralId") String busCollateralId) throws ServiceException;
	/**
	 * 
	 * 方法描述：  根据应收账款获得相应的发票
	 * @param busCollateralId
	 * @return
	 * @throws ServiceException
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-5-17 下午8:14:41
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getRelInvoiceData")
	public JSONArray getRelInvoiceData(@RequestBody String busCollateralId,@RequestParam("pleStr") String pleStr) throws ServiceException;


	/**
	 * 方法描述： 根据押品编号获取对应关系列表
	 * @param mfBusCollateralDetailRel
	 * @return
	 * @throws ServiceException
	 * List<MfBusCollateralDetailRel>
	 * @author YuShuai
	 * @date 2017-6-2 下午8:27:27
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getCollateralDetailRelById")
	public List<MfBusCollateralDetailRel> getCollateralDetailRelById(
			MfBusCollateralDetailRel mfBusCollateralDetailRel)throws ServiceException;;
	/**
	 * 
	 * 方法描述： 根据选择押品获得关联货物明细
	 * @param pleStr
	 * @return
	 * @throws ServiceException
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-6-12 上午9:46:31
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getRelGoodsDeailData")
	public Map<String,Object> getRelGoodsDeailData(@RequestBody String pleStr) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据业务编号获得回购押品编号及名称
	 * @param pleStr
	 * @return
	 * @throws ServiceException
	 * @author 姚文豪
	 * @date 2017-6-19 上午9:46:31
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getPledgeListData")
	public Map<String,Object> getPledgeListData(@RequestBody String busCollateralId) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据押品编号更新押品状态，
	 * @param pledgeIdArr，可能多个押品编号拼接的字符串
	 * @param pleSts
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-7-2 下午5:35:02
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/updatePledgeInfoStsById")
	public void updatePledgeInfoStsById(@RequestBody String pledgeIdArr,@RequestParam("pleSts") String pleSts) throws ServiceException;
	/**
	 * 方法描述： 根据appId查找从合同列表
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * @author 仇招
	 * @date 2017-8-1 上午09:09:02
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getMfBusFollowPactList")
	public List<MfBusFollowPact> getMfBusFollowPactList(@RequestParam("appId") String appId ,@RequestParam("pactAmt")  Double pactAmt)throws ServiceException;
	/**
	 * 
	 * 方法描述： 删除业务与押品关联的关联关系
	 * @param relId
	 * @param bussType 正常业务business、授信业务credit、展期业务extension
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-22 下午5:31:20
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/updatePledgeStsByRelId")
	public void updatePledgeStsByRelId(@RequestBody String relId,@RequestParam("bussType") String bussType) throws Exception;
	/**
	 * 
	 * 方法描述： 放款审批和入库审批合并，放款审批完成根据选择是否同意入库标识业务关联押品入库
	 * 并添加保管信息
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-25 下午6:40:22
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/updatePledgeInstockByBuss")
	public void updatePledgeInstockByBuss(@RequestBody String relId) throws Exception;

	@RequestMapping(value = "/mfBusCollateralDetailRel/findByPageAppId")
	public Ipage findByPageAppId(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 *
	 * 方法描述： 根绝业务编号获得未入库的押品信息
	 *
	 * @param appId
	 * @return
	 * @throws ServiceException
	 *             List<MfBusCollateralDetailRel>
	 * @author ldy
	 * @date 2018-12-17 下午3:56:24
	 */
	@RequestMapping(value = "/mfBusCollateralDetailRel/getCollateralListByAppId")
	public List<MfBusCollateralDetailRel> getCollateralListByAppId(@RequestParam("appId") String appId) throws Exception;
}

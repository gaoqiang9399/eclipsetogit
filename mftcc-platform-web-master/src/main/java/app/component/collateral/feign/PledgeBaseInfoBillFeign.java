package  app.component.collateral.feign;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import app.base.ServiceException;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.util.toolkit.Ipage;

/**
* Title: PledgeBaseInfoBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 19 13:49:06 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PledgeBaseInfoBillFeign {
	
	@RequestMapping(value = "/pledgeBaseInfoBill/insert")
	public void insert(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;
	@RequestMapping(value = "/pledgeBaseInfoBill/insertList")
	public void insertList(@RequestBody List<PledgeBaseInfoBill> pledgeBaseInfoBillList) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfoBill/delete")
	public void delete(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfoBill/update")
	public void update(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfoBill/getById")
	public PledgeBaseInfoBill getById(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfoBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfoBill/getAll")
	public List<PledgeBaseInfoBill> getAll(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfoBill/findNetBillOrder")
	public int findNetBillOrder(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfoBill/findListByPledgeNo")
	public List<PledgeBaseInfoBill> findListByPledgeNo(@RequestBody String pledgeNo) throws ServiceException;
	@RequestMapping(value = "/pledgeBaseInfoBill/findByPageNotRegister")
	public Ipage findByPageNotRegister(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/pledgeBaseInfoBill/findNewByPledgeNo")
	public List<PledgeBaseInfoBill> findNewByPledgeNo(@RequestBody String pledgeNo) throws ServiceException;
	/**
	 * 方法描述： 获取押品清单模版Excel对象
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author HGX
	 * @date 2017-5-23 下午3:51:24
	 */
//	@RequestMapping(value = "/pledgeBaseInfoBill/getPledgeBillTemplateWB")
	//public HSSFWorkbook getPledgeBillTemplateWB() throws Exception;
	
	/**
	 * 方法描述： 押品清单模版导入
	 * @param dataListStr
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2017-5-24 上午11:33:56
	 */
	@RequestMapping(value = "/pledgeBaseInfoBill/dealImportPledgeBillPlate")
	public void dealImportPledgeBillPlate(@RequestParam("dataListStr") String dataListStr,@RequestParam("pledgeNo")  String pledgeNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据押品编号获得关联的货物明细
	 * @param pledgeBaseInfoBill
	 * @return
	 * @throws ServiceException
	 * List<PledgeBaseInfoBill>
	 * @author 沈浩兵
	 * @date 2017-6-14 下午8:31:40
	 */
	@RequestMapping(value = "/pledgeBaseInfoBill/getBillListByPledgeNo")
	public List<PledgeBaseInfoBill> getBillListByPledgeNo(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfoBill/getGoodsValue")
	public double getGoodsValue(@RequestBody  List<PledgeBaseInfoBill> list) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfoBill/getBillPageByPledgeNos")
	public Ipage getBillPageByPledgeNos(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 获得本次提货关联的押品明细信息
	 * @param pledgeBaseInfoBill
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/pledgeBaseInfoBill/getBillListForClaim")
	public List<PledgeBaseInfoBill> getBillListForClaim(@RequestBody PledgeBaseInfoBill pledgeBaseInfoBill) throws Exception;

	/**
	 * 计算货值
	 * @param pledgeBillValue
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pledgeBaseInfoBill/calcPledgeValueByBill")
	public Map<String, Object> calcPledgeValueByBill(@RequestParam("billListData") String billListData, @RequestParam("appId") String appId) throws Exception;
}

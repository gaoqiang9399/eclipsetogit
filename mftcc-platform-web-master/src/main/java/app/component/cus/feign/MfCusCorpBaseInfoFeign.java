package  app.component.cus.feign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.relation.entity.Child;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfCusCorpBaseInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 21 15:25:30 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCorpBaseInfoFeign {
	
	@RequestMapping("/mfCusCorpBaseInfo/insert")
	public MfCusCorpBaseInfo insert(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws ServiceException;
	
	@RequestMapping("/mfCusCorpBaseInfo/insertForApp")
	public MfCusCorpBaseInfo insertForApp(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws ServiceException;
	
	@RequestMapping("/mfCusCorpBaseInfo/delete")
	public void delete(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws ServiceException;
	
	@RequestMapping("/mfCusCorpBaseInfo/update")
	public MfCusCorpBaseInfo update(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws ServiceException;
	
	@RequestMapping("/mfCusCorpBaseInfo/getById")
	public MfCusCorpBaseInfo getById(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws ServiceException;
	
	@RequestMapping("/mfCusCorpBaseInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/mfCusCorpBaseInfo/findPageWithEntZoned")
	public Ipage findPageWithEntZoned(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping("/mfCusCorpBaseInfo/getCusCorpBaseInfoList")
	public List<MfCusCorpBaseInfo> getCusCorpBaseInfoList(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws ServiceException;
	/**
	 * 获取行业分类的层级关系
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCorpBaseInfo/getLoanUse")
	public JSONArray  getLoanUse() throws Exception;
	/**
	 * 获取行业分类
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCorpBaseInfo/getLoanUseById")
	public Child getLoanUseById(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;
	/**
	 * 根据行业分类，资产总额，营业收入，从业人员匹配企业规模    1-大型企业 2-中型企业 3-小型企业 4-微型企业 5-其他
	 * @param wayNo  行业分类
	 * @param assetSum 资产总额
	 * @param bussIncome 营业收入
	 * @param empCnt 从业人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCorpBaseInfo/getMatchProjectSize")
	public Map<String,Object> getMatchProjectSize(@RequestParam("wayNo") String wayNo,@RequestParam("assetSum") String assetSum,@RequestParam("bussIncome") String bussIncome,@RequestParam("empCnt") String empCnt)throws Exception;
	/**
	 * @param cusNo 客户号
	 * @param cusName 客户名称
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping("/mfCusCorpBaseInfo/checkinfo")
	public Map<String, String> checkinfo(@RequestParam("cusNo") String cusNo,@RequestParam("cusName") String cusName,@RequestParam("nodeNo") String nodeNo)throws Exception;
	/**
	 * @Description:处理结束日期  
	 * @param endDate
	 * @param dealType 处理类型: 1-插入数据库 (去掉日期中的横杠)  2-界面显示(加上横杠)
	 * @return
	 * @author: 李伟
	 * @date: 2017-11-25 下午2:18:26
	 */
	@RequestMapping("/mfCusCorpBaseInfo/dealEndDate")
	public String dealEndDate(@RequestParam("endDate") String endDate,@RequestParam("dealType") String dealType);
	/**
	 * 
	 * 方法描述： 根据客户号cusNo获取客户法人信息
	 * @param ipage
	 * @param mfCusCorpBaseInfo
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author zhs
	 * @date 2018-1-9 上午9:53:14
	 */
	@RequestMapping("/mfCusCorpBaseInfo/getLegalPerson")
	public Ipage getLegalPerson(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * 根据全称或简称查询
	 * @author yudongwei@mftcc.cn 2018-05-11
	 * @param mfCusCorpBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCorpBaseInfo/getListByCusNameOrShort")
	public List<MfCusCorpBaseInfo> getListByCusNameOrShort(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;

	@RequestMapping("/mfCusCorpBaseInfo/findByPageForShort")
	public Ipage findByPageForShort(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCorpBaseInfo/updateForShort")
	public void updateForShort(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;
	@RequestMapping("/mfCusCorpBaseInfo/updateForOpen")
	public void updateForOpen(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;
	@RequestMapping("/mfCusCorpBaseInfo/getCusListAjax")
	public JSONArray getCusListAjax(@RequestBody Map<String,Object> dataMap) throws Exception;
}

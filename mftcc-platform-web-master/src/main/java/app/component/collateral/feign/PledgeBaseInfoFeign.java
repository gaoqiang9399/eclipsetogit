package  app.component.collateral.feign;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javafx.scene.media.Media;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import app.base.ServiceException;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.web.multipart.MultipartFile;

/**
* Title: PledgeBaseInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Apr 21 16:24:12 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PledgeBaseInfoFeign {
	
	@RequestMapping(value = "/pledgeBaseInfo/insert")
	public void insert(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/delete")
	public void delete(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/update")
	public void update(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	@RequestMapping(value = "/pledgeBaseInfo/updateAjax")
	public void updateAjax(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfo/getById")
	public PledgeBaseInfo getById(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	

	@RequestMapping(value = "/pledgeBaseInfo/getAll")
	public List<PledgeBaseInfo> getAll(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/getByCondition")
	public List<PledgeBaseInfo> getByCondition(@RequestBody Map<String, Object> paraMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得选择押品分页列表
	 * @param ipage
	 * @param pledgeBaseInfo
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-5-2 下午5:35:34
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getPledgeInfoListByCusNoPage")
	public Ipage getPledgeInfoListByCusNoPage(@RequestBody Ipage ipage,@RequestParam("appId") String appId) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfo/getEvalCnt")
	public String getEvalCnt(@RequestBody Map<String, String> parMap)throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/validateDupExtOstr04")
	public String validateDupExtOstr04(@RequestBody String extOstr04) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/validateDupExtOstr07")
	public String validateDupExtOstr07(@RequestBody String extOstr07) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/getByCusNo")
	public PledgeBaseInfo getByCusNo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	
	@RequestMapping(value = "/pledgeBaseInfo/getSelectPledge")
	public List<PledgeBaseInfo> getSelectPledge(@RequestBody List<PledgeBaseInfo> list,@RequestParam("collaDetailList")List<MfBusCollateralDetailRel> collaDetailList) throws ServiceException;

	@RequestMapping(value = "/pledgeBaseInfo/getPledgeInfoListByAppIdPage")
	public Ipage getPledgeInfoListByAppIdPage(Ipage ipage)throws Exception;


	@RequestMapping(value = "/pledgeBaseInfo/getPledgeListByAppInfo")
	public Ipage getPledgeListByAppInfo(Ipage ipage,
@RequestParam("pledgeBaseInfo") 			PledgeBaseInfo pledgeBaseInfo,@RequestParam("appId")  String appId,@RequestParam("tableName") String tableName)throws ServiceException;

	/**
	 * 
	 * 方法描述： 根据客户编号和押品展示号模糊匹配查询押品
	 * @param pledgeBaseInfo
	 * @return
	 * @throws ServiceException
	 * List<PledgeBaseInfo>
	 * @author 沈浩兵
	 * @date 2017-6-14 上午10:42:32
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getBypledgeShowNo")
	public List<PledgeBaseInfo> getBypledgeShowNo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	

	@RequestMapping(value = "/pledgeBaseInfo/getPledgeListByAppInfo")
	public List<PledgeBaseInfo> getPledgeListByAppInfo(@RequestParam("pledgeBaseInfo") PledgeBaseInfo pledgeBaseInfo,@RequestParam("appId")  String appId,@RequestParam("tableName") String tableName)throws ServiceException;

	

	@RequestMapping(value = "/pledgeBaseInfo/getPledgeBaseInfo")
	public PledgeBaseInfo getPledgeBaseInfo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	/**
	 * 方法描述：押品到期查询
	 * @param ipage
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getPledgeToDateByPage")
	public Ipage getPledgeToDateByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得业务关联的押品
	 * @param ipage
	 * @param pledgeBaseInfo
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-8-12 上午10:54:55
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getPledgeInfoListByBussPage")
	public Ipage getPledgeInfoListByBussPage(@RequestBody Ipage ipage,@RequestParam("pledgeBaseInfo") PledgeBaseInfo pledgeBaseInfo,@RequestParam("appId") String appId,@RequestParam("pledgeNoStr") String pledgeNoStr) throws ServiceException;

	/**
	 * 
	 * 方法描述： 预约登记功能，更新押品基本信息并提交一下业务流程
	 * @param pledgeBaseInfo
	 * @param appId
	 * @param wkfAppId 
	 * @return
	 * @throws Exception
	 * Result
	 * @author zhs
	 * @date 2017-12-22 上午10:42:06
	 */
	@RequestMapping(value = "/pledgeBaseInfo/reservationReg")
	public Result reservationReg(@RequestBody PledgeBaseInfo pledgeBaseInfo,@RequestParam("appId")  String appId,@RequestParam("wkfAppId")  String wkfAppId) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据申请号，获取对应的押品信息
	 * @param pledgeBaseInfo
	 * @param appId
	 * @return
	 * @throws Exception
	 * @author LiJuntao
	 * @date 2018-6-2  上午10:42:06
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getByAppId")
	public List<PledgeBaseInfo> getByAppId(@RequestParam("appId") String appId,@RequestParam("collateralType") String collateralType) throws Exception;

	/**
	 * 
	 * 方法描述： 根据申请号，提交车管验车流程节点
	 * @param pledgeBaseInfo
	 * @param appId
	 * @return
	 * @throws Exception
	 * @author 段泽宇
	 * @date 2018-7-10  下午17:42:06
	 */
	@RequestMapping(value = "/pledgeBaseInfo/doCommit")
	public Result doCommit(@RequestParam("appId") String appId,@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 方法描述： 车管验车关联押品后   更新与押品相关的信息表
	 * @param pledgeBaseInfo
	 * @param pledgeBaseInfo
	 * @return
			 * @throws Exception
	 * @author 段泽宇
	 * @date 2018-8-6  下午17:42:06
			*/
	@RequestMapping(value = "/pledgeBaseInfo/updateRelCusName")
	public void updateRelCusName(@RequestBody PledgeBaseInfo pledgeBaseInfo)throws Exception;

	/**
	 * 方法描述： 车管验车换押品后   解除与之前押品关联信息
	 * @param pledgeBaseInfo
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * @author 段泽宇
	 * @date 2018-8-6  下午17:42:06
	 */
	@RequestMapping(value = "/pledgeBaseInfo/updateRelCusNameRelieve")
	public void updateRelCusNameRelieve(@RequestBody PledgeBaseInfo pledgeBaseInfo)throws Exception;
	/**
	 * @方法描述： 通过案件id获取押品
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * List<PledgeBaseInfo>
	 * @author 仇招
	 * @date 2018年9月26日 上午10:55:14
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getListByCaseId")
	public List<PledgeBaseInfo> getListByCaseId(@RequestBody PledgeBaseInfo pledgeBaseInfo)throws Exception;
	/**
	 * @方法描述： 根据客户号和资产属性查找押品列表
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * List<PledgeBaseInfo>
	 * @author 仇招
	 * @date 2018年9月27日 下午3:38:52
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getListByCusNoAndAssetProperty")
	public List<PledgeBaseInfo> getListByCusNoAndAssetProperty(@RequestBody PledgeBaseInfo pledgeBaseInfo)throws Exception;

	/**
	 *根据客户号查询押品信息
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pledgeBaseInfo/getPledgePageByCusNo")
	public Ipage getPledgePageByCusNo(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/pledgeBaseInfo/getAccountByAppId")
	public List<PledgeBaseInfo> getAccountByAppId(@RequestParam("appId") String appId,@RequestParam("classFirstNo") String classFirstNo) throws Exception;

	@RequestMapping(value = "/pledgeBaseInfo/importExcel", method = RequestMethod.POST )
	public String  importExcel(@RequestPart List<PledgeBaseInfo> list,@RequestParam("appId") String appId) throws Exception;
}

package  app.component.collateral.movable.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.movable.entity.MfMoveableClaimGoodsApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfMoveableClaimGoodsApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 10 18:33:41 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableClaimGoodsApplyFeign {
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/insert")
	public MfMoveableClaimGoodsApply insert(@RequestBody Map<String, Object> dataMap) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/delete")
	public void delete(@RequestBody MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/update")
	public void update(@RequestBody MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/getById")
	public MfMoveableClaimGoodsApply getById(@RequestBody MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableClaimGoodsApply") MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化提货新增实体
	 * @param busPleId
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * MfMoveableClaimGoodsApply
	 * @author 沈浩兵
	 * @date 2017-6-12 上午9:16:03
	 */
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/initClaimGoodsApply")
	public MfMoveableClaimGoodsApply initClaimGoodsApply(@RequestBody String busPleId,@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 审批意见保存提交
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-6-12 下午6:40:38
	 */
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述：  根据已选择的押品
	 * @param busCollateralId
	 * @return
	 * @throws ServiceException
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-6-12 下午9:35:42
	 */
	@RequestMapping(value = "/mfMoveableClaimGoodsApply/getPledgeDataBySelected")
	public JSONArray getPledgeDataBySelected(@RequestParam("claimId") String claimId) throws ServiceException;
	
}

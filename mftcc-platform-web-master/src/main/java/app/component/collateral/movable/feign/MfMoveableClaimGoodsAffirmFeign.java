package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableClaimGoodsAffirm;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableClaimGoodsAffirmBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 12 20:12:27 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableClaimGoodsAffirmFeign {
	
	@RequestMapping(value = "/mfMoveableClaimGoodsAffirm/insert")
	public void insert(@RequestBody MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsAffirm/delete")
	public void delete(@RequestBody MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsAffirm/update")
	public void update(@RequestBody MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsAffirm/getById")
	public MfMoveableClaimGoodsAffirm getById(@RequestBody MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsAffirm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableClaimGoodsAffirm") MfMoveableClaimGoodsAffirm mfMoveableClaimGoodsAffirm) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化提货确认登记
	 * @param busPleId
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * MfMoveableClaimGoodsAffirm
	 * @author 沈浩兵
	 * @date 2017-6-12 下午8:37:23
	 */
	@RequestMapping(value = "/mfMoveableClaimGoodsAffirm/getInitClaimGoodsAffirm")
	public MfMoveableClaimGoodsAffirm getInitClaimGoodsAffirm(@RequestParam("busPleId") String busPleId,@RequestParam("cusNo") String cusNo) throws Exception;
}

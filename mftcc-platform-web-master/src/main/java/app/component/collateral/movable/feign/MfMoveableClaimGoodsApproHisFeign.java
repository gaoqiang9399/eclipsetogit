package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableClaimGoodsApproHis;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableClaimGoodsApproHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 10 18:35:10 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableClaimGoodsApproHisFeign {
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApproHis/insert")
	public void insert(@RequestBody MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApproHis/delete")
	public void delete(@RequestBody MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApproHis/update")
	public void update(@RequestBody MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApproHis/getById")
	public MfMoveableClaimGoodsApproHis getById(@RequestBody MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableClaimGoodsApproHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableClaimGoodsApproHis") MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis) throws Exception;
	
}

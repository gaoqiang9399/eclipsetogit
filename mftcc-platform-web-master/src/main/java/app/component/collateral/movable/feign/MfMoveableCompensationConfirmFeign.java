package  app.component.collateral.movable.feign;

import java.io.File;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableCompensationConfirm;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableCompensationConfirmBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 15 18:43:41 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableCompensationConfirmFeign {
	
	@RequestMapping(value = "/mfMoveableCompensationConfirm/insert")
	public void insert(@RequestBody MfMoveableCompensationConfirm mfMoveableCompensationConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensationConfirm/delete")
	public void delete(@RequestBody MfMoveableCompensationConfirm mfMoveableCompensationConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensationConfirm/update")
	public void update(@RequestBody MfMoveableCompensationConfirm mfMoveableCompensationConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensationConfirm/getById")
	public MfMoveableCompensationConfirm getById(@RequestBody MfMoveableCompensationConfirm mfMoveableCompensationConfirm) throws Exception;
	@RequestMapping(value = "/mfMoveableCompensationConfirm/input")
	public MfMoveableCompensationConfirm input(@RequestBody MfMoveableCompensationConfirm mfMoveableCompensationConfirm) throws Exception;
	@RequestMapping(value = "/mfMoveableCompensationConfirm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableCompensationConfirm") MfMoveableCompensationConfirm mfMoveableCompensationConfirm) throws Exception;
	/**
	 * 方法描述： 押品清单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2017-5-24 上午11:33:56
	 */
	@RequestMapping(value = "/mfMoveableCompensationConfirm/importPledgeBillPlate")
	public String importPledgeBillPlate(@RequestBody File file,@RequestParam("pledgeNo")  String pledgeNo) throws Exception;	
}

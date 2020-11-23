package app.component.nmd.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.nmd.entity.MfSysRateBase;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysRateBaseBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 09 10:15:21 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfSysRateBaseFeign {

	@RequestMapping("/mfSysRateBase/insert")
	public void insert(@RequestBody MfSysRateBase mfSysRateBase) throws ServiceException;

	@RequestMapping("/mfSysRateBase/delete")
	public void delete(@RequestBody MfSysRateBase mfSysRateBase) throws ServiceException;

	@RequestMapping("/mfSysRateBase/update")
	public void update(@RequestBody MfSysRateBase mfSysRateBase) throws ServiceException;

	@RequestMapping("/mfSysRateBase/getById")
	public MfSysRateBase getById(@RequestBody MfSysRateBase mfSysRateBase) throws ServiceException;

	@RequestMapping("/mfSysRateBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage/*, @RequestParam("mfSysRateBase") MfSysRateBase mfSysRateBase*/)
			throws ServiceException;

	/**
	 * 
	 * 方法描述：通过申请 期限 获取基准利率类型 和 基准利率值，基准利率日期
	 * 
	 * @param mfBusApply
	 * @param mfBusAppKind
	 * @return baseMap.put(@RequestBody "baseRate",baseRate );//基准利率
	 *         baseMap.put(@RequestBody "baseRateDate",baseRateDate);//基准利率开始日期
	 *         baseMap.put(@RequestBody "baseRateType",baseRateType);//基准利率类型
	 *         1-贷款基准利率 2-公积金贷款率 3-贴现基准利率
	 * @throws Exception
	 *             Map<String,String>
	 * @author WD
	 * @date 2017-8-29 上午10:38:42
	 */
	@RequestMapping("/mfSysRateBase/getMfSysRateTypeMap")
	public Map<String, String> getMfSysRateTypeMap(@RequestBody MfBusApply mfBusApply,@RequestParam("mfBusAppKind") MfBusAppKind mfBusAppKind)
			throws Exception;

}

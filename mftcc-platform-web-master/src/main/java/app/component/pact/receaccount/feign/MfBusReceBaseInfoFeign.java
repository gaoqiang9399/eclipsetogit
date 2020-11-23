package app.component.pact.receaccount.feign;

import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: MfAssetHandleInfoBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusReceBaseInfoFeign {

	/**
	 *
	 * 方法描述 账款登记保存
	 * @param [mfBusReceBaseInfo]
	 * @return app.component.pact.receaccount.entity.MfBusReceBaseInfo
	 * @author zhs
	 * @date 2018/9/1 11:38
	 */
	@RequestMapping(value = "/mfBusReceBaseInfo/insert")
	public MfBusReceBaseInfo insert(@RequestBody MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;
	/**
	 *  
	 * 方法描述 业务流程中进行账款登记保存
	 * @param [mfBusReceBaseInfo]
	 * @return app.component.pact.receaccount.entity.MfBusReceBaseInfo
	 * @author zhs
	 * @date 2018/9/1 11:38
	 */
	@RequestMapping(value = "/mfBusReceBaseInfo/insertForBus")
	public Map<String,Object> insertForBus(@RequestBody MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;

	@RequestMapping(value = "/mfBusReceBaseInfo/delete")
	public void delete(@RequestBody MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;

	@RequestMapping(value = "/mfBusReceBaseInfo/update")
	public void update(@RequestBody MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;

	@RequestMapping(value = "/mfBusReceBaseInfo/getById")
	public MfBusReceBaseInfo getById(@RequestBody MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;
	@RequestMapping(value = "/mfBusReceBaseInfo/findReceByPage")
	public Ipage findReceByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 *
	 * 方法描述 获取业务下关联的应收账款列表
	 * @param [mfBusReceBaseInfo]
	 * @return java.util.List<app.component.pact.receaccount.entity.MfBusReceBaseInfo>
	 * @author zhs
	 * @date 2018/8/16 11:16
	 */
	@RequestMapping(value = "/mfBusReceBaseInfo/getMfBusReceBaseInfoList")
	public List<MfBusReceBaseInfo> getMfBusReceBaseInfoList(@RequestBody MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;
	@RequestMapping(value = "/mfBusReceBaseInfo/getUnTransReceList")
	public List<MfBusReceBaseInfo> getUnTransReceList(MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;

	/**
	 *
	 * 方法描述 业务下应收账款的概要信息
	 * @param [mfBusReceBaseInfo]
	 * @return app.component.pact.receaccount.entity.MfBusReceBaseInfo
	 * @author zhs
	 * @date 2018/8/29 16:09
	 */
	@RequestMapping(value = "/mfBusReceBaseInfo/getReceAbstractInfo")
	public MfBusReceBaseInfo getReceAbstractInfo(MfBusReceBaseInfo mfBusReceBaseInfo) throws Exception;

   
}

package app.component.cus.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusAssureDetail;
import app.component.cus.entity.MfCusAssureCompany;
import app.util.toolkit.Ipage;

/**
 * Title: MfCusAssureCompanyBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Oct 23 10:57:47 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfCusAssureCompanyFeign {

	@RequestMapping(value = "/mfCusAssureCompany/insert")
	public void insert(@RequestBody MfCusAssureCompany mfCusAssureCompany) throws Exception;

	@RequestMapping(value = "/mfCusAssureCompany/delete")
	public void delete(@RequestBody MfCusAssureCompany mfCusAssureCompany) throws Exception;

	@RequestMapping(value = "/mfCusAssureCompany/update")
	public void update(@RequestBody MfCusAssureCompany mfCusAssureCompany) throws Exception;

	@RequestMapping(value = "/mfCusAssureCompany/getById")
	public MfCusAssureCompany getById(@RequestBody MfCusAssureCompany mfCusAssureCompany) throws Exception;

	@RequestMapping(value = "/mfCusAssureCompany/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 根据担保额度占用明细更新担保公司额度
	 * 
	 * @param mfBusAssureDetail
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午2:05:36
	 */
	@RequestMapping(value = "/mfCusAssureCompany/occupyUsableAmt")
	public void occupyUsableAmt(@RequestBody  MfBusAssureDetail mfBusAssureDetail) throws Exception;

	/**
	 * 审批否决、还款结清等恢复额度
	 * 
	 * @param mfBusAssureDetail
	 * @param recoveryAmt
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午3:26:24
	 */
	@RequestMapping(value = "/mfCusAssureCompany/recoveryUsableAmt")
	public void recoveryUsableAmt(@RequestBody MfBusAssureDetail mfBusAssureDetail,@RequestParam("recoveryAmt") Double recoveryAmt) throws Exception;
	/**
	 * 
	 * 方法描述： 获得担保公司信息
	 * @param mfCusAssureCompany
	 * @return
	 * @throws Exception
	 * List<MfCusAssureCompany>
	 * @author 沈浩兵
	 * @date 2018年4月21日 下午3:42:58
	 */
	@RequestMapping(value = "/mfCusAssureCompany/getMfCusAssureCompanyList")
	public List<MfCusAssureCompany> getMfCusAssureCompanyList(@RequestBody MfCusAssureCompany mfCusAssureCompany) throws Exception;

	/**
	 * 
	 * 方法描述：根据idNum 获得担保公司信息
	 * @param mfCusAssureCompany
	 * @return
	 * @throws Exception
	 * List<MfCusAssureCompany>
	 * @author 刘东迎
	 * @date 2018年4月21日 下午3:42:58
	 */
	@RequestMapping(value = "/mfCusAssureCompany/getAssureInfoByIdNum")
	public List<MfCusAssureCompany> getAssureInfoByIdNum(@RequestBody MfCusAssureCompany mfCusAssureCompany) throws Exception;

}

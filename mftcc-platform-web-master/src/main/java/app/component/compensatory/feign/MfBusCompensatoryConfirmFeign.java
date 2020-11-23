package  app.component.compensatory.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.compensatory.entity.MfBusCompensatoryConfirm;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusCompensatoryConfirmFeign.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 19:02:24 CST 2018
 **/

@FeignClient("mftcc-platform-factor")
public interface MfBusCompensatoryConfirmFeign{

	@RequestMapping(value = "/mfBusCompensatoryConfirm/getCompensatoryConfirmData")
	public Map<String, Object> getCompensatoryConfirmData(@RequestBody MfBusCompensatoryConfirm mfBusCompensatoryConfirm) throws Exception;;
	
	@RequestMapping(value = "/mfBusCompensatoryConfirm/insertCompensatoryConfirm")
	public void insertCompensatoryConfirm(@RequestBody MfBusCompensatoryConfirm mfBusCompensatoryConfirm) throws Exception;

	@RequestMapping(value = "/mfBusCompensatoryConfirm/getByFincId")
    List<MfBusCompensatoryConfirm> getByFincId(@RequestBody MfBusCompensatoryConfirm mfBusCompensatoryConfirm)throws Exception;

	@RequestMapping(value = "/mfBusCompensatoryConfirm/getCompensatoryExpiresPage")
	public Ipage getCompensatoryExpiresPage(Ipage ipage);
	@RequestMapping(value = "/mfBusCompensatoryConfirm/findRegisterByPageAjax")
	public Ipage findRegisterByPageAjax(Ipage ipage);
	/**
	 * 
	 * <p>Title: getByPactId</p>  
	 * <p>Description:根据合同id去查询担保公司的代偿记录 </p>  
	 * @param mfBusCompensatoryConfirm
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月13日 下午5:16:45
	 */
	@RequestMapping(value = "/mfBusCompensatoryConfirm/getByPactId")
    List<MfBusCompensatoryConfirm> getByPactId(@RequestBody MfBusCompensatoryConfirm mfBusCompensatoryConfirm)throws Exception;
}

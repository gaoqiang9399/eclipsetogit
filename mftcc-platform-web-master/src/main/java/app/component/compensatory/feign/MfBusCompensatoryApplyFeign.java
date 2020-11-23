package  app.component.compensatory.feign;

import java.util.List;

import app.base.ServiceException;
import app.component.compensatory.entity.MfBusCompensatoryDoc;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.compensatory.entity.MfBusCompensatoryApply;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Title: MfBusCompensatoryApplyFeign.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 18:57:02 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusCompensatoryApplyFeign{

	@RequestMapping(value = "/mfBusCompensatoryApply/getByFincId")
	public MfBusCompensatoryApply getByFincId(@RequestBody MfBusCompensatoryApply mfBusCompensatoryApply) throws Exception;

	@RequestMapping(value = "/mfBusCompensatoryApply/insert")
	public void insert(@RequestBody MfBusCompensatoryApply mfBusCompensatoryApply);
	
	@RequestMapping(value = "/mfBusCompensatoryApply/insertCompensatoryApply")
	public MfBusCompensatoryApply insertCompensatoryApply(@RequestBody MfBusCompensatoryApply mfBusCompensatoryApply);

	@RequestMapping(value = "/mfBusCompensatoryApply/getCompensatoryList")
	public List<MfBusCompensatoryApply> getCompensatoryList(@RequestBody MfBusCompensatoryApply mfBusCompensatoryApply);

	@RequestMapping(value = "/mfBusCompensatoryApply/getById")
    MfBusCompensatoryApply getById(@RequestBody MfBusCompensatoryApply mfBusCompensatoryApply)throws Exception;

	@RequestMapping(value = "/mfBusCompensatoryApply/getFincId")
	public MfBusCompensatoryApply getFincId(@RequestBody MfBusCompensatoryApply mfBusCompensatoryApply) throws Exception;

	@RequestMapping(value = "/mfBusCompensatoryApply/getByPactId")
	List<MfBusCompensatoryApply> getByPactId(@RequestParam("pactId") String pactId) throws Exception;
	@RequestMapping(value = "/mfBusCompensatoryApply/getCompensatoryDocList")
	List<MfBusCompensatoryDoc> getCompensatoryDocList(@RequestBody MfBusCompensatoryDoc mfBusCompensatoryDoc ) throws Exception;
	/**
	 * @Description 获取代偿申请
	 * @Author fuchen
	 * @DateTime 2020/03/14 10:03
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/mfBusCompensatoryApply/findConfirmByPageAjax")
	public Ipage findConfirmByPageAjax(@RequestBody Ipage ipage) throws Exception;

	/**
	 * @Description 获取代偿申请
	 * @Author fuchen
	 * @DateTime 2020/03/14 10:03
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/mfBusCompensatoryApply/getLawsuitInitAjax")
	public Ipage getLawsuitInitAjax(@RequestBody Ipage ipage) throws Exception;
}
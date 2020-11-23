package app.component.pact.fiveclass.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pact.fiveclass.entity.MfFiveclassModel;
import app.component.pact.fiveclass.entity.MfPactFiveclass;
import app.util.toolkit.Ipage;

/**
 * Title: MfFiveclassModelBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 12:26:58 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfFiveclassModelFeign {

	@RequestMapping(value = "/mfFiveclassModel/insert")
	public void insert(@RequestBody MfFiveclassModel mfFiveclassModel) throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/delete")
	public void delete(@RequestBody MfFiveclassModel mfFiveclassModel) throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/update")
	public void update(@RequestBody MfFiveclassModel mfFiveclassModel) throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/getById")
	public MfFiveclassModel getById(@RequestBody MfFiveclassModel mfFiveclassModel) throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/listByMfFiveclassModel")
	public List<MfFiveclassModel> listByMfFiveclassModel(@RequestBody MfFiveclassModel mfFiveclassModel)
			throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/getByMfPactFiveclass")
	public List<MfFiveclassModel> getByMfPactFiveclass(@RequestBody MfPactFiveclass mfPactFiveclass)
			throws Exception;

	@RequestMapping(value = "/mfFiveclassModel/getMfFiveclassModel")
	public MfFiveclassModel getMfFiveclassModel() throws Exception;

}

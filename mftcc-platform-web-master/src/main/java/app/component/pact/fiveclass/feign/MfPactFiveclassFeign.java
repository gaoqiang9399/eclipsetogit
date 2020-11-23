package app.component.pact.fiveclass.feign;

import app.component.pact.entity.MfBusPact;
import app.component.pact.fiveclass.entity.FiveClassAndPact;
import app.component.pact.fiveclass.entity.MfPactFiveclass;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: MfPactFiveclassBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 13:38:56 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfPactFiveclassFeign {

	@RequestMapping(value = "/mfPactFiveclass/insert")
	public MfPactFiveclass insert(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/insertByBatch")
	public void insertByBatch(@RequestBody List<MfBusPact> list) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/delete")
	public void delete(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/update")
	public MfPactFiveclass update(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/confirmAjax")
	public void confirmAjax(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/getById")
	public MfPactFiveclass getById(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/getByFincId")
	public MfPactFiveclass getByFincId(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfPactFiveclass") MfPactFiveclass mfPactFiveclass)
			throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/findFiveClassAndPactByPage")
	public Ipage findFiveClassAndPactByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/mfPactFiveclass/findFiveClassAndPactByPageForBatch")
	public Ipage findFiveClassAndPactByPageForBatch(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfPactFiveclass/findFiveClassAndPactByPageForApply")
	public List<FiveClassAndPact> findFiveClassAndPactByPageForApply(@RequestBody FiveClassAndPact fiveClassAndPact) throws Exception;
	@RequestMapping(value = "/mfPactFiveclass/updateByBatch")
	public void updateByBatch(@RequestBody List<MfBusPact> list) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/doCommit")
	public Result doCommit(@RequestBody Map<String, Object> dataMap)
			throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/listHisByFincId")
	public Ipage listHisByFincId(@RequestBody Ipage ipg,
			@RequestParam("mfPactFiveclass") MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/listHisByFincId1")
	public List<MfPactFiveclass> listHisByFincId1(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/getHisById")
	public MfPactFiveclass getHisById(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/getFincIdByPactId")
	public List<MfPactFiveclass> getFincIdByPactId(@RequestBody String pactId) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/getEndDateByFincId")
	public String getEndDateByFincId(@RequestBody String fincId) throws Exception;
	
	@RequestMapping(value = "/mfPactFiveclass/fiveclassStorage")
	public void fiveclassStorage(@RequestBody List<MfPactFiveclass> list) throws Exception;

	@RequestMapping(value = "/mfPactFiveclass/getFiveAndHisList")
	public List<MfPactFiveclass> getFiveAndHisList(@RequestBody MfPactFiveclass mfPactFiveclass) throws Exception;

	/**
	 * @Description 根据客户号获取其下面贷款的最差的五级分类
	 * @Author zhaomingguang
	 * @DateTime 2019/9/20 15:19
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfPactFiveclass/getWorstMfPactFiveclassByCusNo")
	String getWorstMfPactFiveclassByCusNo(String cusNo);
}

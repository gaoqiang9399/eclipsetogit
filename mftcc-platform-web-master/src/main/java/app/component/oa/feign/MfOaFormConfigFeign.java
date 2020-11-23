package app.component.oa.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.entity.MfOaFormConfig;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface MfOaFormConfigFeign{

	@RequestMapping(value="/mfOaFormConfig/insert")
	public void insert(@RequestBody MfOaFormConfig mfOaFormConfig) throws Exception ;

	@RequestMapping(value="/mfOaFormConfig/delete")
	public void delete(@RequestBody MfOaFormConfig mfOaFormConfig) throws Exception ;

	@RequestMapping(value="/mfOaFormConfig/update")
	public void update(@RequestBody MfOaFormConfig mfOaFormConfig) throws Exception ;

	@RequestMapping(value="/mfOaFormConfig/getById")
	public MfOaFormConfig getById(@RequestBody MfOaFormConfig mfOaFormConfig) throws Exception ;

	@RequestMapping(value="/mfOaFormConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;

	/**
	 * 
	 * @Title: getFormByAction   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param parmMap :action,formType
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/mfOaFormConfig/getFormByAction")
	public String getFormByAction(@RequestParam("action") String action,@RequestParam("formType") String formType) throws Exception ;

}

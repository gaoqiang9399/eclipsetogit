package  app.component.cus.infochange.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.infochange.entity.MfCusKeyInfoFields;
import app.util.toolkit.Ipage;

/**
 * 类名： MfCusKeyInfoFieldsFeign
 * 描述：客户关键信息字段
 * @author 仇招
 * @date 2018年5月29日 上午11:18:18
 */
@FeignClient("mftcc-platform-factor")
public interface MfCusKeyInfoFieldsFeign {
	
	@RequestMapping("/mfCusKeyInfoFields/insert")
	public void insert(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception;
	
	@RequestMapping("/mfCusKeyInfoFields/delete")
	public void delete(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception;
	
	@RequestMapping("/mfCusKeyInfoFields/update")
	public void update(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception;
	
	@RequestMapping("/mfCusKeyInfoFields/getById")
	public MfCusKeyInfoFields getById(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception;
	
	@RequestMapping("/mfCusKeyInfoFields/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusKeyInfoFields/ifKeyField")
	public MfCusKeyInfoFields ifKeyField(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception;
	
	@RequestMapping("/mfCusKeyInfoFields/getByField")
	public List<MfCusKeyInfoFields> getByField(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields) throws Exception;

	@RequestMapping("/mfCusKeyInfoFields/getPrimaryValue")
	public Map<String, Object> getPrimaryValue(@RequestBody MfCusKeyInfoFields mfCusKeyInfoFields)throws Exception;
}

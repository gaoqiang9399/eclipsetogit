package app.component.financingoptions.feign;

import app.util.toolkit.Ipage;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.financingoptions.entity.MfFinancingOptions;

/**
 * Title: MfFinancingOptionsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 10 09:40:36 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfFinancingOptionsFeign {
	@RequestMapping("/mfFinancingOptions/insert")
	public void insert(@RequestBody MfFinancingOptions mfFinancingOptions) throws Exception;

	@RequestMapping("/mfFinancingOptions/delete")
	public void delete(@RequestBody MfFinancingOptions mfFinancingOptions) throws Exception;

	@RequestMapping("/mfFinancingOptions/update")
	public void update(@RequestBody MfFinancingOptions mfFinancingOptions) throws Exception;

	@RequestMapping("/mfFinancingOptions/getById")
	public MfFinancingOptions getById(@RequestBody MfFinancingOptions mfFinancingOptions) throws Exception;

	@RequestMapping("/mfFinancingOptions/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfFinancingOptions/getAll")
	public List<MfFinancingOptions> getAll() throws Exception;

}

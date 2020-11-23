package app.component.rules.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rules.entity.MfRulesProRelation;

/**
 * Title: MfRulesProRelationBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Oct 16 17:28:37 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfRulesProRelationFeign {
	@RequestMapping(value = "/mfRulesProRelation/getMfRulesProRelationList")
	public List<MfRulesProRelation> getMfRulesProRelationList(@RequestBody MfRulesProRelation mfRulesProRelation) throws Exception;

	@RequestMapping(value = "/mfRulesProRelation/getMfRulesProRelation")
	public MfRulesProRelation getMfRulesProRelation(@RequestBody MfRulesProRelation mfRulesProRelation)throws Exception;
}

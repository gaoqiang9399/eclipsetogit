package  app.component.eval.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.eval.entity.MfEvalGradeCard;
import app.util.toolkit.Ipage;

/**
* Title: MfEvalGradeCardBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfEvalGradeCardFeign {
	
	@RequestMapping(value = "/mfEvalGradeCard/insert")
	public MfEvalGradeCard insert(@RequestBody MfEvalGradeCard mfEvalGradeCard) throws Exception;
	
	@RequestMapping(value = "/mfEvalGradeCard/delete")
	public void delete(@RequestBody MfEvalGradeCard mfEvalGradeCard) throws Exception;
	
	@RequestMapping(value = "/mfEvalGradeCard/update")
	public void update(@RequestBody MfEvalGradeCard mfEvalGradeCard) throws Exception;
	
	@RequestMapping(value = "/mfEvalGradeCard/getById")
	public MfEvalGradeCard getById(@RequestBody MfEvalGradeCard mfEvalGradeCard) throws Exception;
	
	@RequestMapping(value = "/mfEvalGradeCard/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfEvalGradeCard") MfEvalGradeCard mfEvalGradeCard) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评级模型下配置的评级评分卡信息
	 * @param mfEvalGradeCard
	 * @return
	 * @throws Exception
	 * List<MfEvalGradeCard>
	 * @author 沈浩兵
	 * @date 2017-5-27 下午5:44:23
	 */
	@RequestMapping(value = "/mfEvalGradeCard/getEvalGradeCardList")
	public List<MfEvalGradeCard> getEvalGradeCardList(@RequestBody MfEvalGradeCard mfEvalGradeCard) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评分卡
	 * @param mfEvalGradeCard
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-6-2 下午3:08:32
	 */
	@RequestMapping(value = "/mfEvalGradeCard/getCheckScorePercentFlag")
	public String getCheckScorePercentFlag(@RequestBody MfEvalGradeCard mfEvalGradeCard) throws Exception;
	
}

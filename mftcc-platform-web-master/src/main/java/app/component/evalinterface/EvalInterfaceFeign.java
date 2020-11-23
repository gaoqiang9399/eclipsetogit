package app.component.evalinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.eval.entity.AppEval;
import app.component.eval.entity.AppProperty;
import app.component.eval.entity.EvalScenceAdjRel;
import app.component.eval.entity.EvalScenceAdjVal;
import app.component.eval.entity.EvalScenceConfig;
import app.component.eval.entity.EvalScenceDxRel;
import app.component.eval.entity.EvalScoreGradeConfig;
import app.component.eval.entity.MfEvalGradeCard;
import app.component.eval.entity.MfFormItemConfig;
import app.component.eval.entity.EvalScenceDxVal;

@FeignClient("mftcc-platform-factor")
public interface EvalInterfaceFeign {
	/**
	 * 获取项目评级级别
	 * @param obj 项目评级对象
	 * @param scenceNo 评级场景编号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/evalInterface/getProjectRatingLevel")
	public Map<String,String> getProjectRatingLevel(@RequestBody Object obj,@RequestParam("scenceNo") String scenceNo)  throws Exception;
	
	/**
	 * 获取评级配置场景编号
	 * @param evalType
	 * @param formal
	 * @param prodNo
	 * @return
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceNo")
	public String getEvalScenceNo(@RequestBody String evalType ,@RequestParam("formal")  String formal,@RequestParam("prodNo")  String prodNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得可用的评级场景信息
	 * @return
	 * @throws Exception
	 * List<EvalScenceConfig>
	 * @author 沈浩兵
	 * @date 2016-11-3 上午8:49:57
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceConfigDataMap")
	public Map<String,Object> getEvalScenceConfigDataMap(@RequestBody Map<String,String> map) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评级模型配置信息
	 * @param evalScenceConfig
	 * @return
	 * @throws Exception
	 * List<EvalScenceConfig>
	 * @author 沈浩兵
	 * @date 2017-3-2 上午9:03:01
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceConfigList")
	public List<EvalScenceConfig> getEvalScenceConfigList(@RequestBody EvalScenceConfig evalScenceConfig) throws Exception;
	/**
	 * 
	 * 方法描述： 获得可用的表单项配置信息
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2016-11-5 下午3:58:31
	 */
	@RequestMapping(value = "/evalInterface/getEvalFormItemDataList")
	public List<MfFormItemConfig> getEvalFormItemDataList() throws Exception;
	/**
	 * 
	 * 方法描述： 根据场景编号获得场景信息
	 * @param scenceNo
	 * @return
	 * @throws Exception
	 * EvalScenceConfig
	 * @author 沈浩兵
	 * @date 2016-11-3 下午3:19:00
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceConfigById")
	public EvalScenceConfig getEvalScenceConfigById(@RequestParam("scenceNo") String scenceNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得指标项信息list
	 * @return
	 * @throws Exception
	 * List<AppProperty>
	 * @author 沈浩兵
	 * @date 2016-11-7 下午5:36:52
	 */
	@RequestMapping(value = "/evalInterface/getAppPropertyList")
	public List<AppProperty> getAppPropertyList() throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得客户财务报表数据并检查是否有连续两年以上财报数据
	 * @return
	 * @throws Exception
	 * String 0 没有 1 有
	 * @author 沈浩兵
	 * @date 2017-1-5 上午11:21:13
	 */
	@RequestMapping(value = "/evalInterface/checkFinMainByCusNo")
	public String checkFinMainByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得最近的保存的评级信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * AppEval
	 * @author 沈浩兵
	 * @date 2017-1-22 上午10:12:01
	 */
	@RequestMapping(value = "/evalInterface/getAppEval")
	public AppEval getAppEval(@RequestBody AppEval appEval) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号和状态查询评级列表
	 * @param cusNo,evalSts
	 * @return List<AppEval>
	 * @throws Exception
	 * EvalScenceDxRel
	 * @author LiuAo
	 * @date 2017-3-27 下午15:22:19
	 */
	@RequestMapping(value = "/evalInterface/getListByCusNoAndSts")
	public List<AppEval> getListByCusNoAndSts(@RequestBody AppEval appEval) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评级定性指标关联信息
	 * @param evalAppNo
	 * @return
	 * @throws Exception
	 * EvalScenceDxRel
	 * @author 沈浩兵
	 * @date 2017-2-3 上午9:25:19
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceDxRel")
	public EvalScenceDxRel getEvalScenceDxRel(@RequestBody EvalScenceDxRel evalScenceDxRel) throws Exception;
	/**
	 * 
	 * 方法描述： 获得客户定性指标项得分情况
	 * @param evalScenceDxVal
	 * @return
	 * @throws Exception
	 * EvalScenceDxVal
	 * @author 沈浩兵
	 * @date 2017-2-3 上午9:26:03
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceDxVal")
	public EvalScenceDxVal getEvalScenceDxVal(@RequestBody EvalScenceDxVal evalScenceDxVal) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评级调整指标关联信息
	 * @param evalScenceAdjRel
	 * @return
	 * @throws Exception
	 * EvalScenceAdjRel
	 * @author 沈浩兵
	 * @date 2017-2-3 上午10:21:45
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceAdjRel")
	public EvalScenceAdjRel getEvalScenceAdjRel(@RequestBody EvalScenceAdjRel evalScenceAdjRel) throws Exception;
	/**
	 * 
	 * 方法描述： 获得客户调整指标项得分情况
	 * @param evalScenceAdjVal
	 * @return
	 * @throws Exception
	 * EvalScenceAdjVal
	 * @author 沈浩兵
	 * @date 2017-2-3 上午10:22:36
	 */
	@RequestMapping(value = "/evalInterface/getEvalScenceAdjVal")
	public EvalScenceAdjVal getEvalScenceAdjVal(@RequestBody EvalScenceAdjVal evalScenceAdjVal) throws Exception;
	/**
	 * 
	 * 方法描述： 获得分数等级配置信息
	 * @return
	 * @throws Exception
	 * List<EvalScoreGradeConfig>
	 * @author 沈浩兵
	 * @date 2017-3-4 上午10:59:07
	 */
	@RequestMapping(value = "/evalInterface/getGradeConfigsList")
	public List<EvalScoreGradeConfig> getGradeConfigsList() throws Exception;
	
	/**
	 * 根据客户编号查询是否填写了财务报表
	 * @author admin
	 * date 2017-3-10
	 */
	@RequestMapping(value = "/evalInterface/checkFinanceStatement")
	public int checkFinanceStatement(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述：根据客户号、评级申请号获得评级模型评分卡 
	 * @param cusNo
	 * @param evalAppNo
	 * @return
	 * @throws Exception
	 * List<MfEvalGradeCard>
	 * @author 沈浩兵
	 * @date 2017-8-23 下午8:09:40
	 */
	@RequestMapping(value = "/evalInterface/getEvalGradeCardByNo")
	public List<MfEvalGradeCard> getEvalGradeCardByNo(@RequestParam("cusNo") String cusNo,@RequestParam("evalAppNo") String evalAppNo) throws Exception;
 }

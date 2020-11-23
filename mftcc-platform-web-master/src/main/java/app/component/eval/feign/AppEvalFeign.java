package  app.component.eval.feign;

import app.base.ServiceException;
import app.component.eval.entity.AppEval;
import app.component.eval.entity.EvalCompreVal;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: AppEvalBo.java
* Description:
* @author:@dhcc.com.cn
* @Wed Mar 23 03:42:29 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface AppEvalFeign {
	
	@RequestMapping(value = "/appEval/insert")
	public AppEval insert(@RequestBody Map<String,Object> appMap) throws ServiceException;
	
	@RequestMapping(value = "/appEval/insertPers")
	public void insertPers(@RequestBody AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/delete")
	public void delete(@RequestBody AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/update")
	public void update(@RequestBody AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/updateSts")
	public void updateSts(@RequestBody AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/updateSubmit")
	public AppEval updateSubmit(@RequestBody  Map<String,Object> appMap) throws ServiceException;
	
	@RequestMapping(value = "/appEval/updateSave")
	public void updateSave(@RequestBody AppEval appEval) throws ServiceException;
	/**
	 * 
	 * 方法描述：更新保存评级申请信息
	 * @param appEval
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-12-26 下午8:29:16
	 */
	@RequestMapping(value = "/appEval/updateAppEvalInfo")
	public void updateAppEvalInfo(@RequestBody AppEval appEval,@RequestParam("evalCompreVal") EvalCompreVal evalCompreVal) throws ServiceException;
	
	@RequestMapping(value = "/appEval/getById")
	public AppEval getById(@RequestBody AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("appEval") AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/appEvalGnList")
	public Ipage appEvalGnList(@RequestBody Ipage ipage,@RequestParam("appEval") AppEval appEval) throws ServiceException;
	
	@RequestMapping(value = "/appEval/getListForCusNo")
	public List<AppEval> getListForCusNo(@RequestBody String cusNo) throws ServiceException;
	
	/**
	 * @author czk
	 * @Description: 根据客户号和评级状态查询
	 * date 2017-1-23
	 */
	@RequestMapping(value = "/appEval/getListForCusNoAndSts")
	public List<AppEval> getListForCusNoAndSts(@RequestBody AppEval appEval) throws ServiceException;

	@RequestMapping(value = "/appEval/getUnfinishedListUseType")
	public List<AppEval> getUnfinishedListUseType(@RequestBody Map<String,Object> paramMap) throws ServiceException;
	/**
	 *
	 * 方法描述： 获得未提交的评级申请
	 * @param cusNo
	 * @return
	 * @throws ServiceException
	 * List<AppEval>
	 * @author 沈浩兵
	 * @date 2016-11-23 下午1:43:55
	 */
	@RequestMapping(value = "/appEval/getUnfinishedList")
	public List<AppEval> getUnfinishedList(@RequestBody String cusNo) throws ServiceException;
	
	@RequestMapping(value = "/appEval/getAllScoreForEchart")
	public Map<String,Object> getAllScoreForEchart(@RequestBody Map<String,String> map)throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得评级评分卡详情
	 * @param evalScenceNo
	 * evalAppNo
	 * @return
	 * @author 沈浩兵
	 * @date 2017-5-31 下午5:28:35
	 * @throws Exception
	 */
	@RequestMapping(value = "/appEval/getListData")
	public Map<String,Object> getListData(@RequestParam("evalAppNo") String evalAppNo,@RequestParam("evalScenceNo") String evalScenceNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得不同评分卡类型的评级结果
	 * @param evalAppNo
	 * @param evalScenceNo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-1 上午11:25:07
	 */
	@RequestMapping(value = "/appEval/getDlFinScore")
	public Map<String,Object> getDlFinScore(@RequestParam("evalAppNo") String evalAppNo,@RequestParam("evalScenceNo") String evalScenceNo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 更新定性和调整评级指标
	 * @param dataMap
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-6-2 下午12:56:05
	 */
	@RequestMapping(value = "/appEval/updateDxAdj")
	public AppEval updateDxAdj(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得评级总分
	 * @param evalAppNo
	 * @param evalScenceNo
	 * @return
	 * @throws ServiceException
	 * Double
	 * @author 沈浩兵
	 * @date 2017-6-3 下午6:09:54
	 */
	@RequestMapping(value = "/appEval/getEvalTotalScore")
	public Double getEvalTotalScore(@RequestParam("evalAppNo") String evalAppNo,@RequestParam("evalScenceNo") String evalScenceNo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获取评级机评级别
	 * @param scenceNo
	 * @param totalScore
	 * @param grade
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-6-3 下午8:08:42
	 */
	@RequestMapping(value = "/appEval/getEvalLevel")
	public String getEvalLevel(@RequestParam("scenceNo") String scenceNo,@RequestParam("totalScore") double totalScore,@RequestParam("grade") String grade) throws ServiceException;
	/**
	 * 
	 * 方法描述： 处理评级申请信息
	 * @param appMap
	 * @return
	 * @throws ServiceException
	 * AppEval
	 * @author 沈浩兵
	 * @date 2018年6月7日 下午3:38:25
	 */
	@RequestMapping(value = "/appEval/insertEvalApply")
	public AppEval insertEvalApply(@RequestBody Map<String,Object> appMap) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评级评分卡及指标信息
	 * @param evalAppNo
	 * @param evalScenceNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年6月7日 下午5:34:44
	 */
	@RequestMapping(value = "/appEval/getEvalListData")
	public Map<String,Object> getEvalListData(@RequestParam("evalAppNo") String evalAppNo,@RequestParam("evalScenceNo") String evalScenceNo) throws Exception;
	/**
	 *
	 * 方法描述： 获得评级评分卡及指标信息
	 * @param evalAppNo
	 * @param evalScenceNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年6月7日 下午5:34:44
	 */
	@RequestMapping(value = "/appEval/getEvalListDataForResult")
	public Map<String,Object> getEvalListDataForResult(@RequestParam("evalAppNo") String evalAppNo,@RequestParam("evalScenceNo") String evalScenceNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得客户当前评级信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年6月13日 下午3:58:40
	 */
	@RequestMapping(value = "/appEval/getCurrAppEvalData")
	public Map<String,Object> getCurrAppEvalData(@RequestBody Map <String, Object> paramMap) throws Exception;
	
	
	/**
	 * 
	 * 方法描述： 人为申请外部评级
	 * @param appMap
	 * @return
	 * AppEval
	 * @author lyb
	 * @date 2018年6月13日 下午9:12:38
	 */
	@RequestMapping(value = "/appEval/insertMan")
	public AppEval insertMan(@RequestBody AppEval appEval);
	
	/**
	 * 
	 * 方法描述： 人为提交外部评级
	 * @param appMap
	 * @return
	 * AppEval
	 * @author lyb
	 * @date 2018年6月13日 下午9:12:38
	 */
	@RequestMapping(value = "/appEval/updateManSubmit")
	public AppEval updateManSubmit(@RequestBody AppEval appEval);

	@RequestMapping(value = "/appEval/getEvalCreditAmtByCusNo")
	public Double getEvalCreditAmtByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/appEval/getListByCusNoAndRptDateAndRptType")
	public List <AppEval> getListByCusNoAndRptDateAndRptType(@RequestBody AppEval appEval) throws Exception ;
}

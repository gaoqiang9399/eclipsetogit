package  app.component.report.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.report.entity.MfReportQueryConditionUser;
import app.util.toolkit.Ipage;

/**
* Title: MfReportQueryConditionUserBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 24 16:53:50 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReportQueryConditionUserFeign {
	
	@RequestMapping(value = "/mfReportQueryConditionUser/insert")
	public void insert(@RequestBody MfReportQueryConditionUser mfReportQueryConditionUser) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/delete")
	public void delete(@RequestBody MfReportQueryConditionUser mfReportQueryConditionUser) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/update")
	public void update(@RequestBody MfReportQueryConditionUser mfReportQueryConditionUser) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/getById")
	public MfReportQueryConditionUser getById(@RequestBody MfReportQueryConditionUser mfReportQueryConditionUser) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReportQueryConditionUser") MfReportQueryConditionUser mfReportQueryConditionUser) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/reportQuery")
	public String reportQuery(@RequestBody MfReportQueryConditionUser mfReportQueryConditionUser)  throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/saveReoprtSqlCondition")
	public String saveReoprtSqlCondition(@RequestBody MfReportQueryConditionUser mfReportQueryConditionUser)   throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionUser/getReportSqlListBean")
	public Ipage getReportSqlListBean(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * @Description:查询条件显示 
	 * @param user
	 * @return
	 * @author: 李伟
	 * @date: 2017-8-27 下午5:49:29
	 */
	@RequestMapping(value = "/mfReportQueryConditionUser/showFormConditionVal",produces="application/json;charset=utf-8")
	public String showFormConditionVal(@RequestBody Map<String, String> formMap) throws Exception;
	@RequestMapping(value = "/mfReportQueryConditionUser/pieceJavaBeanSql")
	public  Map<String,Object> pieceJavaBeanSql(@RequestBody String opNo,@RequestParam("reportId") String reportId) throws Exception;
	@RequestMapping(value = "/mfReportQueryConditionUser/pieceOpenJavaBeanSql")
	public  Map<String,Object> pieceOpenJavaBeanSql(@RequestBody Map<String,String> map) throws Exception;
	/**
	 * 
	 * 方法描述： 报表页面查询条件转换成储存的sql
	 * @param reportId
	 * @param sqlCondition
	 * @return
	 * String
	 * @author YaoWenHao
	 * @date 2018-1-4 下午2:11:58
	 */
	@RequestMapping(value = "/mfReportQueryConditionUser/reportOpenQuery",produces="application/json; charset=utf-8")
	public String  reportOpenQuery (@RequestBody Map<String,String> map)  throws Exception;

    @RequestMapping(value = "/mfReportQueryConditionUser/updateSqlCondition",produces="application/json; charset=utf-8")
    public void  updateSqlCondition (@RequestBody Map<String,String> map)  throws Exception;
}

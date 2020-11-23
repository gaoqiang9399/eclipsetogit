package app.component.report.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.report.entity.MfBalAnalysis;
import app.component.report.entity.MfInfoSource;

@FeignClient("mftcc-platform-factor")
public interface MfBalAnalysisFeign {

	/**
	 * 
	 * 方法描述： 获取余额汇总表数据
	 * @return
	 * @throws ServiceException
	 * List<MfBalAnalysis>
	 * @author lwq
	 * @date 2017-7-18 下午3:18:55
	 */
	@RequestMapping(value = "/mfBalAnalysis/getMfBalAnalysisList")
	public List<MfBalAnalysis> getMfBalAnalysisList(@RequestBody Map<String,String> map) throws Exception ;
	
	/**
	 * 
	 * 方法描述： 项目信息来源
	 * @return
	 * @throws ServiceException
	 * List<MfInfoSource>
	 * @author lwq
	 * @date 2017-7-25 上午9:21:35
	 */
	@RequestMapping(value = "/mfBalAnalysis/getSourceList")
	public List<MfInfoSource> getSourceList(@RequestBody String opNo,@RequestParam("reportId") String reportId,@RequestParam("sqlMap") String sqlMap) throws ServiceException;
	
}

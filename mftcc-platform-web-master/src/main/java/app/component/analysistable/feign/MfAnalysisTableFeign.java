package  app.component.analysistable.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.analysistable.entity.MfAnalysisTable;

/**
* Title: MfAnalysisTableBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Oct 10 20:43:12 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfAnalysisTableFeign {
	/**
	 * @方法描述： 根据表名获取配置项
	 * @param mfAnalysisTable
	 * @return
	 * @throws Exception
	 * List<MfAnalysisTable>
	 * @author 仇招
	 * @date 2018年10月11日 上午9:25:57
	 */
	@RequestMapping(value = "/mfAnalysisTable/getListByTableName")
	public List<MfAnalysisTable> getListByTableName(@RequestBody MfAnalysisTable mfAnalysisTable) throws Exception;
	
	/**
	 * @方法描述： 获取表名
	 * @return
	 * @throws Exception
	 * List<String>
	 * @author 仇招
	 * @date 2018年10月13日 上午9:43:59
	 */
	@RequestMapping(value = "/mfAnalysisTable/getTableName")
	public List<MfAnalysisTable> getTableName() throws Exception;
	
}

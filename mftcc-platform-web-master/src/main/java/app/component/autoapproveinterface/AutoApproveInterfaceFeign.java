/**
 * Copyright (C) DXHM 版权所有
 * 文件名： AutoApproveInterface.java
 * 包名： app.component.autoapproveinterface
 * 说明： 
 * @author 仇招
 * @date 2018年9月12日 上午9:13:33
 * @version V1.0
 */ 
package app.component.autoapproveinterface;

import app.component.paph.entity.ApiReturnRecord;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 类名： AutoApproveInterface
 * 描述：自动审批
 * @author 仇招
 * @date 2018年9月12日 上午9:13:33
 */
@FeignClient("mftcc-platform-factor")
public interface AutoApproveInterfaceFeign {
	
	/**
	 * @方法描述： 百融-获取百融贷前管理数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月30日 下午7:10:25
	 */
	@RequestMapping(value = "/autoApproveInterface/getBRPreLoanData")
	public Map<String, Object> getBRPreLoanData(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 百融-手机号在网时长
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月30日 下午7:11:18
	 */
	@RequestMapping(value = "/autoApproveInterface/getBRDurationData")
	public Map<String, Object> getBRDurationData(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 获取百融解析数据token
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月30日 下午8:18:31
	 */
	@RequestMapping(value = "/autoApproveInterface/getBrReportToken")
	public Map<String, Object> getBrReportToken() throws Exception;
	/**
	 * @方法描述： 获取聚信立-蜜罐数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/getJxlMgData")
	public  Map<String, Object> getJxlMgData(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 获取聚信立-蜜蜂数据数据源
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/getMFDataSource")
	public  Map<String, Object> getMFDataSource() throws Exception;
	/**
	 * @方法描述： 蜜蜂数据提交申请表单获取回执信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/honeybeeApplications")
	public  Map<String, Object>  honeybeeApplications(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 蜜蜂数据跳过信息采集
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/skip")
	public  Map<String, Object>  skip(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 蜜蜂数据信息采集
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/collect")
	public  Map<String, Object>  collect(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 根据token获取蜜蜂数据信息采集
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/getApiReturnRecordByToken")
	public ApiReturnRecord getApiReturnRecordByToken(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;
	/**
	 * @方法描述： 蜜蜂数据查询报告状态
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/getReportSts")
	public  Map<String, Object>  getReportSts(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 蜜蜂数据查询报告
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2018年10月10日 下午3:12:24
	 */
	@RequestMapping(value = "/autoApproveInterface/getReport")
	public  Map<String, Object>  getReport(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * @方法描述： 获取天天有余
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 段泽宇
	 * @date 2018年10月18日 下午14:44:24
	 */
	@RequestMapping(value = "/autoApproveInterface/getTTYYData")
	public  Map<String, Object> getTTYYData(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * @方法描述： 同盾风控核查
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 陈迪
	 * @date 2020年01月04日 下午5:20:00
	 */
	@RequestMapping(value =  "/autoApproveInterface/tongdunCheck")
	Map<String, Object> tongdunCheck(@RequestBody Map<String, String> map);
}

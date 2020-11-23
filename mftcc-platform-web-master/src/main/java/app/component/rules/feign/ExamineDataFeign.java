/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ExamineDataBo.java
 * 包名： app.component.rules.bo
 * 说明：
 * @author 沈浩兵
 * @date 2017-2-17 下午1:42:06
 * @version V1.0
 */ 
package app.component.rules.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rules.entity.ExamineBigBean;

/**
 * 类名： ExamineDataBo
 * 描述：
 * @author 沈浩兵
 * @date 2017-2-17 下午1:42:06
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface ExamineDataFeign {
	/**
	 * 
	 * 方法描述： 调用规则引擎获得检查大实体
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * ExamineBigBean
	 * @author 沈浩兵
	 * @date 2017-2-17 下午1:44:43
	 */
	@RequestMapping(value = "/examineData/getExamineDataByRules")
	public ExamineBigBean getExamineDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;
}

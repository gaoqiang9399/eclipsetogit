/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FinancingOptionsInterfaceController.java
 * 包名： app.component.financingoptionsinterface
 * 说明： 
 * @author 仇招
 * @date 2018年7月11日 上午11:36:22
 * @version V1.0
 */ 
package app.component.financingoptionsinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.component.financingoptions.entity.MfFinancingOptions;

/**
 * 类名： FinancingOptionsInterfaceController
 * 描述：
 * @author 仇招
 * @date 2018年7月11日 上午11:36:22
 */
@Controller
@RequestMapping("/financingOptionsInterface")
public class FinancingOptionsInterfaceController {
	@Autowired
	private FinancingOptionsInterfaceFeign financingOptionsInterfaceFeign;
	
	/**
	 * 方法描述： 根据选择匹配融资方案
	 * @param salesArea
	 * @return
	 * @throws Exception
	 * List<MfFinancingOptions>
	 * @author 仇招
	 * @date 2018年7月11日 上午11:50:33
	 */
	@RequestMapping("/getBySelectCondition")
	@ResponseBody
	public List<MfFinancingOptions> getBySelectCondition() throws Exception{
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		return financingOptionsInterfaceFeign.getBySelectCondition(mfFinancingOptions);
	}
}

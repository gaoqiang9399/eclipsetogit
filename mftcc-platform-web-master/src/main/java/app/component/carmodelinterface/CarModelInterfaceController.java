/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CarModelInterface.java
 * 包名： app.component.carmodelinterface
 * 说明： 
 * @author 仇招
 * @date 2018年7月6日 下午7:49:24
 * @version V1.0
 */
package app.component.carmodelinterface;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类名： CarModelInterface 描述：车型库接口
 * 
 * @author 仇招
 * @date 2018年7月6日 下午7:49:24
 */
@Controller
@RequestMapping("/carModelInterface")
public class CarModelInterfaceController {
	@Autowired
	private CarModelInterfaceFeign carModelInterfaceFeign;


	/**
	 * 方法描述： 更新车型库
	 * 
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 仇招
	 * @date 2018年7月9日 上午10:45:22
	 */
	@RequestMapping("/updateCarModelLibrary")
	@ResponseBody
	public Map<String, Object> updateCarModelLibrary() throws Exception {
		return carModelInterfaceFeign.updateCarModelLibrary();
	}

}

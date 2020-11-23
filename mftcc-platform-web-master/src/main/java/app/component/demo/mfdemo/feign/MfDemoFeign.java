package app.component.demo.mfdemo.feign;

import app.component.demo.mfdemo.entity.MfDemo;
import app.component.desk.entity.MfDeskMsgItem;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfDeskMsgItemBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Aug 27 10:57:59 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfDemoFeign {
	
	@RequestMapping(value = "/mfDemo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)throws Exception;

	@RequestMapping(value = "/mfDemo/findByPage")
	public void insert(@RequestBody MfDemo mfDemo) throws Exception;
}

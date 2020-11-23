package  app.component.message.feign;

import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.message.entity.MfInnerMessageTemplete;

/**
* Title: MfInnerMessageTempleteBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 30 11:05:19 CST 2018
**/


@FeignClient("mftcc-platform-factor")
public interface MfInnerMessageTempleteFeign {
	
	@RequestMapping(value = "/mfInnerMessageTemplete/insert")
	public void insert(MfInnerMessageTemplete mfInnerMessageTemplete) throws Exception;
	
	@RequestMapping(value = "/mfInnerMessageTemplete/delete")
	public void delete(MfInnerMessageTemplete mfInnerMessageTemplete) throws Exception;
	
	@RequestMapping(value = "/mfInnerMessageTemplete/update")
	public void update(MfInnerMessageTemplete mfInnerMessageTemplete) throws Exception;
	
	@RequestMapping(value = "/mfInnerMessageTemplete/getById")
	public MfInnerMessageTemplete getById(MfInnerMessageTemplete mfInnerMessageTemplete) throws Exception;
	
	@RequestMapping(value = "/mfInnerMessageTemplete/findByPage")
	public Ipage findByPage(Ipage ipage) throws Exception;	
}
	
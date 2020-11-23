package  app.component.bizinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.biz.entity.BizInfoChange;


/**
* Title: cusinterface.java
* Description:
* @author:张波@dhcc.com.cn
* @Wed Jan 13 05:52:56 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface BizInterfaceFeign {
	/**
	 * 插入信息变更
	 * @param changeType-信息类型（1开户、2预警、3普通消息、4注销）,cont-变更内容，bizType-业务类型（1客户、2业务）
	 * map-关联号（map.put('cusNo','111'),map.put('appNo','222'),map.put('conNo','333')）,subList-变更内容详情（字符串数组：['张三地址变更为11','张三电话变更为13555555555']）
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/bizInterface/insertInfo")
	public void insertInfo(@RequestBody String changeType,@RequestParam("cont") String cont,@RequestParam("map") Map<String,String> map,@RequestParam("bizType") String bizType,@RequestParam("subList")List<String> subList) throws Exception;
	/**
	 * APP插入信息变更
	 * @param changeType-信息类型（1开户、2预警、3普通消息、4注销）,cont-变更内容，bizType-业务类型（1客户、2业务）
	 * map-关联号（map.put('cusNo','111'),map.put('appNo','222'),map.put('conNo','333')）,subList-变更内容详情（字符串数组：['张三地址变更为11','张三电话变更为13555555555']）
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/bizInterface/insertInfoForApp")
	public void insertInfoForApp(@RequestBody String changeType,@RequestParam("cont") String cont,@RequestParam("map") Map<String,String> map,@RequestParam("bizType") String bizType,@RequestParam("subList")List<String> subList,@RequestParam("opNo") String opNo,@RequestParam("opName") String opName) throws Exception;
	/**
	 * 更新信息变更
	 * @param date-变更日期（格式：20140401）,changeType-信息类型（1开户、2预警、3普通消息、4注销）,cont-变更内容，bizType-业务类型（1客户、2业务）
	 * map-关联号（map.put('cusNo','111'),map.put('appNo','222'),map.put('conNo','333')）,subList-变更内容详情（字符串数组：['张三地址变更为11','张三电话变更为13555555555']）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bizInterface/updateInfo")
	public void updateInfo(@RequestBody String date,@RequestParam("changeType") String changeType,@RequestParam("cont") String cont,@RequestParam("map") Map<String,String> map,@RequestParam("bizType") String bizType,@RequestParam("subList")List<String> subList) throws Exception;
	
	/**
	 * 查询当天的消息条数
	 * @param date-变更日期（格式：20140401），relNo-关联号（格式：cusNo-20145221;appNo-2364323;contNo-235243）,bizType-业务类型（1客户、2业务）
	 * changeType-信息类型（1开户、2预警、3普通消息、4注销）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bizInterface/getSubCnt")
	public int getSubCnt(@RequestBody String date,@RequestParam("relNo") String relNo,@RequestParam("bizType") String bizType,@RequestParam("changeType") String changeType) throws Exception;
	
	
	@RequestMapping(value = "/bizInterface/getAll")
	public List<BizInfoChange> getAll(@RequestBody String relNo) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 获取前十条历史消息
	 * date 2016-9-2
	 * @param relNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bizInterface/getTopFive")
	public List<BizInfoChange> getTopFive(@RequestBody String relNo) throws Exception;
}



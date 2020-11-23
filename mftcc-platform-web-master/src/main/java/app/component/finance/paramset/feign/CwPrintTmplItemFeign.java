package app.component.finance.paramset.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwPrintTmplItem;
import app.util.toolkit.Ipage;

/**
 * Title: CwPrintTmplItemBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 15 14:30:33 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwPrintTmplItemFeign {

	@RequestMapping(value = "/cwPrintTmplItem/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwPrintTmplItem cwPrintTmplItem) throws Exception;

	@RequestMapping(value = "/cwPrintTmplItem/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwPrintTmplItem cwPrintTmplItem) throws Exception;

	@RequestMapping(value = "/cwPrintTmplItem/update", method = RequestMethod.POST)
	public void update(@RequestBody CwPrintTmplItem cwPrintTmplItem) throws Exception;

	@RequestMapping(value = "/cwPrintTmplItem/getById", method = RequestMethod.POST)
	public CwPrintTmplItem getById(@RequestBody CwPrintTmplItem cwPrintTmplItem) throws Exception;

	@RequestMapping(value = "/cwPrintTmplItem/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 
	 * 方法描述： 根据类型获取模版内容
	 * 
	 * @param cwPrintTmplItem
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-15 下午3:36:54
	 */
	@RequestMapping(value = "/cwPrintTmplItem/getTmplObjByType", method = RequestMethod.POST)
	public Map<String, Object> getTmplObjByType(@RequestBody CwPrintTmplItem cwPrintTmplItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取从模板中保存的内容
	 * 
	 * @param cwPrintTmplItem
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-15 下午4:20:03
	 */
	@RequestMapping(value = "/cwPrintTmplItem/getTmplCodeByType", method = RequestMethod.POST)
	public Map<String, Object> getTmplCodeByType(@RequestBody CwPrintTmplItem cwPrintTmplItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 保存模版标签
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-16 上午9:58:27
	 */
	@RequestMapping(value = "/cwPrintTmplItem/doSaveTmplCode", method = RequestMethod.POST)
	public Map<String, Object> doSaveTmplCode(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取还款或者放款信息
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-19 下午4:02:48
	 */
	@RequestMapping(value = "/cwPrintTmplItem/getPrintMessageById", method = RequestMethod.POST)
	public Map<String, Object> getPrintMessageById(@RequestBody Map<String, Object> dataMap) throws Exception;

}

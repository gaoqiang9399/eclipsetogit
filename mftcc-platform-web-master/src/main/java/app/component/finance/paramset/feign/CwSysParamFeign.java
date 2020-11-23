package app.component.finance.paramset.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwSysParam;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwSysParamBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 30 09:47:39 CST 2016
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwSysParamFeign {

	@RequestMapping(value = "/cwSysParam/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwSysParam/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwSysParam/update", method = RequestMethod.POST)
	public void update(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwSysParam/getById", method = RequestMethod.POST)
	public CwSysParam getById(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwSysParam/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改pvalue的值
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-20 下午3:41:46
	 */
	@RequestMapping(value = "/cwSysParam/doChangePvaluebyPcode", method = RequestMethod.POST)
	public String doChangePvaluebyPcode(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取参数的beanList
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             List<CwSysParam>
	 * @author lzshuai
	 * @date 2017-2-20 下午5:26:06
	 */
	@RequestMapping(value = "/cwSysParam/findListData", method = RequestMethod.POST)
	public List<CwSysParam> findListData(@RequestBody CwSysParam cwSysParam) throws Exception;

	/**
	 * 方法描述： 获取参数设置页面数据
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             List<Map<String,Object>>
	 * @author Javelin
	 * @date 2017-3-21 上午9:44:26
	 */
	@RequestMapping(value = "/cwSysParam/getSysParamSetData", method = RequestMethod.POST)
	public List<Map<String, Object>> getSysParamSetData(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 新增计提
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-21 下午5:31:06
	 */
	@RequestMapping(value = "/cwSysParam/insertJitiType", method = RequestMethod.POST)
	public R insertJitiType(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取计提的值
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             CwSysParam
	 * @author lzshuai
	 * @date 2017-8-21 下午7:01:48
	 */
	@RequestMapping(value = "/cwSysParam/getJiTiValue", method = RequestMethod.POST)
	public CwSysParam getJiTiValue(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;
}

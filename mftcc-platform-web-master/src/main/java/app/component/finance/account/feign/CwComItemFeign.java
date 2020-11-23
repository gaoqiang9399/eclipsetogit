package app.component.finance.account.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.account.entity.CwComItem;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.paramset.entity.CwSysParam;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-fiscal")
public interface CwComItemFeign {
	
	@RequestMapping(value = "/cwComItem/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;
	
	@RequestMapping(value = "/cwComItem/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;
	
	@RequestMapping(value = "/cwComItem/update", method = RequestMethod.POST)
	public R update(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;
	
	@RequestMapping(value = "/cwComItem/getById", method = RequestMethod.POST)
	public CwComItem getById(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;
	
	@RequestMapping(value = "/cwComItem/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获取科目bean实体类
	 * @param cwComItem
	 * @return
	 * @throws Exception
	 * CwComItem
	 * @author 刘争帅
	 * @date 2016-12-28 下午2:12:14
	 */
	@RequestMapping(value = "/cwComItem/getcwComItemById", method = RequestMethod.POST)
	public CwComItem getcwComItemById(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 新增二级科目及以上
	 * @param cwComItem
	 * @throws Exception
	 * void
	 * @author 刘争帅
	 * @date 2016-12-30 下午3:56:52
	 */
	@RequestMapping(value = "/cwComItem/insertComItem2Up", method = RequestMethod.POST)
	public R  insertComItem2Up(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;
	
	/**
	 * 方法描述： 获取所有科目
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * List<Map<String,String>>
	 * @author Javelin
	 * @date 2017-1-24 下午2:20:28
	 */
	@RequestMapping(value = "/cwComItem/getAllComItemListForTree", method = RequestMethod.POST)
	public R getAllComItemListForTree(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 科目检查，获取数据
	 * @param temap
	 * @return
	 * @throws Exception
	 * List<Map<String,Object>>
	 * @author 刘争帅
	 * @date 2017-2-4 下午3:09:54
	 */
	@RequestMapping(value = "/cwComItem/doAccountCheck", method = RequestMethod.POST)
	public R doAccountCheck(@RequestBody Map<String, String> temap,@RequestParam("finBooks") String finBooks)throws Exception;
	
	/**
	 * 方法描述： 获取科目编号设置列表数据
	 * @param ipage
	 * @param parmMap
	 * @return
	 * Ipage
	 * @author 张丽
	 * @date 2017-2-9 上午11:12:10
	 */
	@RequestMapping(value = "/cwComItem/getAccLengthNData", method = RequestMethod.POST)
	public Ipage getAccLengthNData(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks);
	/**
	 * 
	 * 方法描述： 修改科目长度设置格式
	 * @param cwComItem
	 * @return
	 * String
	 * @author 张丽
	 * @date 2017-2-9 下午5:53:55
	 */
	@RequestMapping(value = "/cwComItem/doInputAccnoLength", method = RequestMethod.POST)
	public R doInputAccnoLength(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks);

	/**
	 * 
	 * 方法描述： 获取辅助核算的信息
	 * @param cwComItem
	 * @return
	 * @throws Exception
	 * List<CwRelation>
	 * @author lzshuai
	 * @date 2017-3-2 下午4:25:15
	 */
	@RequestMapping(value = "/cwComItem/getCwRelationData", method = RequestMethod.POST)
	public List<CwRelation> getCwRelationData(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;
	/**
	 * 
	 * 方法描述： 获取上级科目控制字的数量
	 * @param upaccHrt
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @date 2017-3-4 上午10:39:01
	 */
	@RequestMapping(value = "/cwComItem/getUpaccHrtCnt", method = RequestMethod.POST)
	public String getUpaccHrtCnt(@RequestBody String upaccHrt) throws Exception;
}

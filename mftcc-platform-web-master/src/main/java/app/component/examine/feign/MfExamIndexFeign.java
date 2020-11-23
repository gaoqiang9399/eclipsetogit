package  app.component.examine.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.examine.entity.MfExamIndex;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfExamIndexBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Feb 16 14:59:05 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamIndexFeign {
	
	@RequestMapping("/mfExamIndex/insert")
	public void insert(@RequestBody MfExamIndex mfExamIndex) throws ServiceException;
	
	@RequestMapping("/mfExamIndex/delete")
	public void delete(@RequestBody MfExamIndex mfExamIndex) throws ServiceException;
	
	@RequestMapping("/mfExamIndex/update")
	public void update(@RequestBody MfExamIndex mfExamIndex,@RequestParam("indexSubJsonArray") JSONArray indexSubJsonArray) throws ServiceException;
	/**
	 * 
	 * 方法描述： 更新检查指标状态
	 * @param mfExamIndex
	 * @return
	 * @throws ServiceException
	 * int
	 * @author 沈浩兵
	 * @date 2017-2-17 下午8:53:36
	 */
	@RequestMapping("/mfExamIndex/updateSts")
	public int updateSts(@RequestBody MfExamIndex mfExamIndex) throws ServiceException;
	
	@RequestMapping("/mfExamIndex/getById")
	public MfExamIndex getById(@RequestBody MfExamIndex mfExamIndex) throws ServiceException;
	
	@RequestMapping("/mfExamIndex/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamIndex") MfExamIndex mfExamIndex) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得检查指标
	 * @param mfExamIndex
	 * @return
	 * @throws ServiceException
	 * List<MfExamIndex>
	 * @author 沈浩兵
	 * @date 2017-2-16 下午8:19:56
	 */
	@RequestMapping("/mfExamIndex/getMfExamIndexList")
	public List<MfExamIndex> getMfExamIndexList(@RequestBody MfExamIndex mfExamIndex) throws ServiceException;
	/**
	 * 
	 * 方法描述： 检查项及检查项子项保存
	 * @param mfExamIndex
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-7-25 上午9:58:40
	 */
	@RequestMapping("/mfExamIndex/insertExamIndexAndSub")
	public void insertExamIndexAndSub(@RequestBody Map<String,Object> paramMap) throws ServiceException;
}

package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusFamilyInfo;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfCusFamilyInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 01 16:17:46 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusFamilyInfoFeign {
	
	@RequestMapping("/mfCusFamilyInfo/insert")
	public void insert(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	
	@RequestMapping("/mfCusFamilyInfo/insertForApp")
	public MfCusFamilyInfo insertForApp(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	
	@RequestMapping("/mfCusFamilyInfo/delete")
	public void delete(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	
	@RequestMapping("/mfCusFamilyInfo/update")
	public void update(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	
	@RequestMapping("/mfCusFamilyInfo/getById")
	public MfCusFamilyInfo getById(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	
	@RequestMapping("/mfCusFamilyInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusFamilyInfo/getFamilyList")
	public List<MfCusFamilyInfo> getFamilyList(@RequestBody MfCusFamilyInfo mfCusFamilyInfo)throws Exception;

	@RequestMapping("/mfCusFamilyInfo/findByCusNo")
	public List<MfCusFamilyInfo> findByCusNo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo)throws Exception;
	
	/**
	 * @Description:客户关系下拉框内容，如果已经登记配偶信息，隐藏配偶选项
	 * @param mfCusFamilyInfo
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-10-30 上午11:10:26
	 */
	@RequestMapping("/mfCusFamilyInfo/getCusPersRelStr")
	public JSONArray getCusPersRelStr(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	
	@RequestMapping("/mfCusFamilyInfo/deleteFamilyInfo")
	public void deleteFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo)throws Exception;
	/**
	 * 
	 * <p>Title: findListByPage</p>  
	 * <p>Description: 查询出社会关系表里面是配偶的与该客户的名称</p>  
	 * @param ipage
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年8月22日 下午4:14:08
	 */
	@RequestMapping("/mfCusFamilyInfo/findListByPage")
	public Ipage findListByPage(@RequestBody Ipage ipage) throws Exception;



	/**
	 *
	 * 方法描述：  常用联系人新增
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 陈迪
	 * @date 2019-5-13 下午11:45:48
	 */
	@RequestMapping("/mfCusFamilyInfo/insertContacts")
	public void insertContacts(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
}

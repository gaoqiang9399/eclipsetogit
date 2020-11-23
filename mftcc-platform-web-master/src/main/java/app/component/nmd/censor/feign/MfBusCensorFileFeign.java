package app.component.nmd.censor.feign;



import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.nmd.censor.entity.MfBusCensorBase;
import app.component.nmd.censor.entity.MfBusCensorBiz;
import app.component.nmd.censor.entity.MfBusCensorFile;
import app.component.nmd.censor.entity.MfBusCensorType;
import app.util.toolkit.Ipage;



/**
* Title: MfBusCensorFileBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 15 14:47:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCensorFileFeign {
	
	@RequestMapping(value = "/mfBusCensorFile/findByPageBase")
	public Ipage findByPageBase(@RequestBody Ipage ipage/*,@RequestParam("MfBusCensorBase") MfBusCensorBase MfBusCensorBase*/) throws Exception;
	
	@RequestMapping(value = "/mfBusCensorFile/insert")
	public void insert(@RequestBody MfBusCensorFile mfBusCensorFile) throws Exception;
	
	@RequestMapping(value = "/mfBusCensorFile/delete")
	public void delete(@RequestBody MfBusCensorFile mfBusCensorFile) throws Exception;
	
	@RequestMapping(value = "/mfBusCensorFile/update")
	public void update(@RequestBody MfBusCensorFile mfBusCensorFile) throws Exception;
	
	@RequestMapping(value = "/mfBusCensorFile/getById")
	public MfBusCensorFile getById(@RequestBody MfBusCensorFile mfBusCensorFile) throws Exception;
	
	@RequestMapping(value = "/mfBusCensorFile/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage/*,@RequestParam("mfBusCensorFile") MfBusCensorFile mfBusCensorFile*/) throws Exception;
	
	/**
	 * 方法描述： 根据产品与节点获取对应审查表文件列表数据
	 * @param mfBusCensorBiz
	 * @return
	 * @throws Exception
	 * List<MfBusCensorBase>
	 * @author Javelin
	 * @date 2017-7-15 下午3:22:22
	 */
	@RequestMapping(value = "/mfBusCensorFile/getBusCensorBaseList")
	public List<MfBusCensorBase> getBusCensorBaseList(@RequestBody MfBusCensorBiz mfBusCensorBiz) throws Exception;
	@RequestMapping(value = "/mfBusCensorFile/getIsSime")
	public Boolean getIsSime(@RequestBody MfBusCensorFile mfBusCensorFile) throws Exception;
	@RequestMapping(value = "/mfBusCensorFile/insert")
	public void insert(@RequestBody MfBusCensorFile mfBusCensorFile,@RequestParam("fromJson")  String[] fromJson) throws Exception;
	@RequestMapping(value = "/mfBusCensorFile/findAllType")
	public List<MfBusCensorType> findAllType();
	/**
	 * 查询查项分类
	 * @param ipage
	 * @param mfBusCensorType
	 * @return
	 */
	@RequestMapping(value = "/mfBusCensorFile/findByPageBaseType")
	public Ipage findByPageBaseType(@RequestBody Ipage ipage/*,@RequestParam("mfBusCensorType")  MfBusCensorType mfBusCensorType*/);

	@RequestMapping(value = "/mfBusCensorFile/isInstanceTypeByName")
	public boolean isInstanceTypeByName(@RequestBody MfBusCensorBase bizz);

	@RequestMapping(value = "/mfBusCensorFile/insertBaseType")
	public void insertBaseType(@RequestBody MfBusCensorBase bbiz) throws Exception;

	@RequestMapping(value = "/mfBusCensorFile/findCensorBaseListByCensorFileNo")
	public List<MfBusCensorBase> findCensorBaseListByCensorFileNo(@RequestBody MfBusCensorFile mfBusCensorFile) throws Exception;

	@RequestMapping(value = "/mfBusCensorFile/getCensorFileNo")
	public MfBusCensorFile getCensorFileNo(@RequestBody MfBusCensorFile mfBusCensorFile);

	@RequestMapping(value = "/mfBusCensorFile/update")
	public void update(@RequestBody MfBusCensorFile mfBusCensorFile,@RequestParam("fromJson")  String[] fromJson) throws Exception;
	
}

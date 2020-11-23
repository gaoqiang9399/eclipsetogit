package app.component.frontview.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.FormActive;

import app.component.frontview.entity.MfFrontAppform;
import app.util.toolkit.Ipage;

/**
 * Title: MfFrontAppformBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 22 10:18:20 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfFrontAppformFeign {

	@RequestMapping("/mfFrontAppform/insert")
	public void insert(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;

	@RequestMapping("/mfFrontAppform/delete")
	public void delete(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;

	@RequestMapping("/mfFrontAppform/update")
	public void update(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;

	@RequestMapping("/mfFrontAppform/getById")
	public MfFrontAppform getById(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;

	@RequestMapping("/mfFrontAppform/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfFrontAppform") MfFrontAppform mfFrontAppform) throws Exception;

	/**
	 * 根据联合主键产品编号和字段名得到一个要素参数
	 * 
	 * @param mfFrontAppform
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfFrontAppform/getByKindNoAndFieldName")
	public MfFrontAppform getByKindNoAndFieldName(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;

	/**
	 * 根据表单所有属性获取进件要填写的参数，并初始化已设置过的参数
	 * 
	 * @param kindNo
	 * @param formActives
	 *            所有表单属性
	 * @return List&lt;MfFrontAppformVO&gt; list = new
	 *         ArrayList&lt;MfFrontAppformVO&gt;(@RequestBody );<br>
	 *         resultMap.put(@RequestBody "saved", savedList);//保存过的属性<br>
	 *         resultMap.put(@RequestBody "unsaved", list);//未保存过的属性
	 * @throws Exception
	 */
	@RequestMapping("/mfFrontAppform/getFormDataActives")
	public Map<String, Object> getFormDataActives(@RequestParam("kindNo") String kindNo, @RequestBody FormActive[] formActives) throws Exception;

	@RequestMapping("/mfFrontAppform/deleteByKindNoAndFieldName")
	public void deleteByKindNoAndFieldName(@RequestBody MfFrontAppform mfFrontAppform);

	@RequestMapping("/mfFrontAppform/getBy")
	public List<MfFrontAppform> getBy(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;
}

package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pss.information.entity.PssStorageWarning;
import app.util.toolkit.Ipage;

/**
 * Title: PssStorageWarningBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 30 10:07:13 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssStorageWarningFeign {

	@RequestMapping("/pssStorageWarning/insert")
	public void insert(@RequestBody PssStorageWarning pssStorageWarning) throws Exception ;

	@RequestMapping("/pssStorageWarning/delete")
	public void delete(@RequestBody PssStorageWarning pssStorageWarning) throws Exception ;

	@RequestMapping("/pssStorageWarning/deleteByGoodsId")
	public void deleteByGoodsId(@RequestBody PssStorageWarning pssStorageWarning) throws Exception ;

	@RequestMapping("/pssStorageWarning/update")
	public void update(@RequestBody PssStorageWarning pssStorageWarning) throws Exception ;

	@RequestMapping("/pssStorageWarning/getById")
	public PssStorageWarning getById(@RequestBody PssStorageWarning pssStorageWarning) throws Exception ;

	@RequestMapping("/pssStorageWarning/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;

	@RequestMapping("/pssStorageWarning/getAll")
	public List<PssStorageWarning> getAll(@RequestBody PssStorageWarning pssStorageWarning) throws Exception ;
}

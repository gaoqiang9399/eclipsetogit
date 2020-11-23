package app.component.lawsuitinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.lawsuit.entity.MfLawsuit;

/**
 * 
 * @author LiuAo
 * 
 */
@FeignClient("mftcc-platform-factor")
public interface LawsuitInterfaceFeign {
	/**
	 * 查询指定合同相关的法律诉讼
	 * @return List<MfLawsuit>
	 * @throws Exception
	 */
	@RequestMapping(value = "/lawsuitInterface/findByPact")
	public List<MfLawsuit> findByPact() throws Exception;
	
	/**
	 * 判断指定合同是否有法律诉讼
	 * @return 0:无法律诉讼 1:存在法律诉讼
	 * @throws Exception
	 */
	@RequestMapping(value = "/lawsuitInterface/hasLawsuit")
	public String hasLawsuit(@RequestBody String pactId) throws Exception;
}

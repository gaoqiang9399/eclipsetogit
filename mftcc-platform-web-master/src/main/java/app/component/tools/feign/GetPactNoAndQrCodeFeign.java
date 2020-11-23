package app.component.tools.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Title:
 * @Description: 调用监管获取合同号 生成二维码
 * @Auther: Yuan Xiaolong
 * @Version: 1.0
 * @create:2019/6/188:51
 */
@FeignClient("mftcc-platform-factor")
public interface GetPactNoAndQrCodeFeign {
	@RequestMapping(value="/getPactNoAndQrCode",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = "application/json;charset=UTF-8")
	public String getPactNoAndQrCode(@RequestParam("pactNo") String pactNo) throws Exception;
}

package app.component.rules.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rules.entity.FiveclassBean;

/**
 * <p>FivecalssDataBo</p>
 * <p>Description:五级分类-规则引擎返回结果处理</p>
 * <p>Company:</p>
 * @author GuoXY
 * @date 2017-3-18 下午14:37:02
 */
@FeignClient("mftcc-platform-factor")
public interface FivecalssDataFeign {

    /**
     * 获取规则引擎返回的结果数据
     * @author GuoXY
     * date 2017-3-18
     */
	@RequestMapping(value = "/fivecalssData/getFiveclassDataByRules")
    public FiveclassBean getFiveclassDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;
}

package app.component.thirdservice.esign.feign;

import app.component.app.entity.MfBusApply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface EsignInterfaceFeign {
    /**
     * 初始化pdf标签值
     * @author      仇招
     *
     * @return
     * @exception
     * @date        2019/4/12 9:42
     */
    @RequestMapping(value="/esignInterface/initReplacePdf")
    public void initReplacePdf(@RequestBody MfBusApply mfBusApply) throws Exception;

    @RequestMapping(value="/esignInterface/getTemplateIfEsigned")
    public Map<String, Object> getTemplateIfEsigned(@RequestBody  Map<String, Object> paramMap) throws Exception;

    @RequestMapping("/esignInterface/getPdf")
    public Map <String, Object> getPdf(Map <String, Object> paramMap) throws Exception ;

    @RequestMapping("/esignInterface/DZQY")
    public Map <String, Object> DZQY(Map <String, Object> paramMap) throws Exception ;
}

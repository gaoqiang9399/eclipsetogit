package app.component.encrypt.feign;

import app.component.encrypt.entity.MfEncryptFields;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfEncryptFieldsFegin {

    @RequestMapping(value = "/mfEncryptFields/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;


    @RequestMapping("/mfEncryptFields/getById")
    public MfEncryptFields getById(@RequestBody MfEncryptFields mfEncryptFields) throws Exception;

    @RequestMapping("/mfEncryptFields/insert")
    public void insert(@RequestBody MfEncryptFields mfEncryptFields) throws Exception;

    @RequestMapping("/mfEncryptFields/update")
    public void update(@RequestBody MfEncryptFields mfEncryptFields) throws Exception;

    @RequestMapping("/mfEncryptFields/getCount")
    public int getCount(@RequestBody MfEncryptFields mfEncryptFields) throws Exception;


    @RequestMapping("/mfEncryptFields/getList")
    public List<MfEncryptFields> getList(@RequestBody MfEncryptFields mfEncryptFields) throws Exception;

    @RequestMapping("/mfEncryptFields/encryptHistoryData")
    public Map<String,String> encryptHistoryData(@RequestParam("fieldId") String fieldId) throws Exception;

    @RequestMapping("/mfEncryptFields/dencryptHistoryData")
    public Map<String,String> dencryptHistoryData(@RequestParam("fieldId") String fieldId) throws Exception;

}

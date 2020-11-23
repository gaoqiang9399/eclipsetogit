package app.component.cus.feign;

import app.component.cus.entity.MfCusDesignatedRecipient;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title: MfCusDesignatedRecipientFeign.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Thu Apr 11 14:24:30 CST 2019
 **/
@FeignClient("mftcc-platform-factor")
public interface MfCusDesignatedRecipientFeign {
    @RequestMapping("/mfCusDesignatedRecipient/insert")
    public void insert(@RequestBody MfCusDesignatedRecipient mfCusDesignatedRecipient) throws Exception;

    @RequestMapping("/mfCusDesignatedRecipient/delete")
    public void delete(@RequestBody MfCusDesignatedRecipient mfCusDesignatedRecipient) throws Exception;

    @RequestMapping("/mfCusDesignatedRecipient/update")
    public void update(@RequestBody MfCusDesignatedRecipient mfCusDesignatedRecipient) throws Exception;

    @RequestMapping("/mfCusDesignatedRecipient/getBean")
    public MfCusDesignatedRecipient getBean(@RequestBody MfCusDesignatedRecipient mfCusDesignatedRecipient) throws Exception;

    @RequestMapping("/mfCusDesignatedRecipient/getList")
    public List<MfCusDesignatedRecipient> getList(@RequestBody MfCusDesignatedRecipient mfCusDesignatedRecipient) throws Exception;

    @RequestMapping("/mfCusDesignatedRecipient/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}

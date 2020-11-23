package app.component.sys.controller;

import app.component.sys.entity.SysOrgInfoChange;
import app.component.sys.feign.SysOrgInfoChangeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Description:
 * @Author: 仇招
 * @CreateDate: 2019/1/19$ 14:41$
 * @Version: v1.0.0
 */
@Controller
@RequestMapping("/sysOrgInfoChange")
public class SysOrgInfoChangeController {
    @Autowired
    private SysOrgInfoChangeFeign sysOrgInfoChangeFeign;

    @RequestMapping(value = "/refreshBusData")
    @ResponseBody
    public Map<String,Object> refreshBusData() throws Exception {
        SysOrgInfoChange sysOrgInfoChange = new SysOrgInfoChange();
        return sysOrgInfoChangeFeign.refreshBusData(sysOrgInfoChange);
    }
}

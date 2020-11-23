package app.component.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.SourceTemplate;
import app.base.shiro.SessionUserCheckService;
import app.component.common.BizPubParm;
import app.component.secinterface.SecinterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.common.SysGlobalParams;
import app.component.sys.entity.MfSysCompanyMst;

/**
 * Title: SysUserAction.java Description:
 *
 * @author:@dhcc.com.cn
 * @Mon Jan 11 07:02:47 GMT 2016
 **/
@Controller
@RequestMapping("/sysDevView")
public class SysDevViewController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private SysUserFeign sysUserFeign;

    @Autowired
    private SecinterfaceFeign secinterfaceFeign;

    @Autowired
    private SessionUserCheckService sessionUserCheckService;

    @RequestMapping("/devView")
    public String devView(Model model) throws Exception {
        ActionContext.initialize(request, response);
        String orgName = User.getOrgName(request);
        String regName = User.getRegName(request);
        String opNo = User.getRegNo(request);
        MfSysCompanyMst mfSysCompanyMst = (MfSysCompanyMst) SysGlobalParams.get("COMPANY");
        String tlrno = User.getRegNo(request);
        if (StringUtils.isNotBlank(tlrno) && !"null".equals(tlrno)) {
            // 从数据库取出build_in_flag=1的允许访问。
            SysUser sysUser = new SysUser();
            sysUser.setOpNo(tlrno);
            sysUser = sysUserFeign.getById(sysUser);
            if (BizPubParm.YES_NO_Y.equals(sysUser.getBuildInFlag())) {
                //开发者平台入口添加第一次登录强制修改密码限制。20190325陈迪
                String remeg = secinterfaceFeign.SecurityPwd(opNo);
                if ("must".equals(remeg.substring(0, 4))) {
                    request.getSession().setAttribute("opNo", opNo);
                    request.getSession().setAttribute("opName", regName);
//                    this.sessionUserCheckService.deleteSession(request.getSession(), opNo);
                    return "/component/sys/SysUser_PwdForceUpdate";//除值为0时不修改密码，其他都强制修改密码
                }
            } else {
                model.addAttribute("msg", "您没有操作权限！");
                return "/component/pub/noAuthorityException";
            }
        } else {
            model.addAttribute("msg", "您没有操作权限！");
            return "/component/pub/noAuthorityException";
        }
        model.addAttribute("orgName", orgName);
        model.addAttribute("regName", regName);
        model.addAttribute("opNo", opNo);
        model.addAttribute("mfSysCompanyMst", mfSysCompanyMst);
        return "/component/sys/devView";
    }

    @RequestMapping("/setC")
    public String setC(Model model, String setType) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("setType", setType);
        return "/component/sys/setC";
    }

}

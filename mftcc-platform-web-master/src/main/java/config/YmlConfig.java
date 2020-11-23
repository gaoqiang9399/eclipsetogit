package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 加载yaml配置文件的方法
 * Created by sun on 2017-1-15.
 * spring-boot更新到1.5.2版本后locations属性无法使用
 * @PropertySource注解只可以加载proprties文件,无法加载yaml文件 故现在把数据放到application.yml文件中, spring-boot启动时会加载
 */
@Component
//@ConfigurationProperties(locations = {"classpath:config/myProps.yml"},prefix = "myProps")
@ConfigurationProperties(prefix = "mftcc")
public class YmlConfig {
    private Map<String, String> cloud;
    private Map<String, String> pageoffice;
    private Map<String, String> upload;
    private Map<String, String> webservice;
    private Map<String, String> sysParams;
    private Map<String, String> officeConfig;

    /** 征信核心查询, creditQuery.yml */
    private Map<String, String> creditQuery;
    private Map<String, String> cloudnew;
    public Map<String, String> getCloudnew() {
        return cloudnew;
    }

    public void setCloudnew(Map<String, String> cloudnew) {
        this.cloudnew = cloudnew;
    }

    public Map<String, String> getCloud() {
        return cloud;
    }

    public void setCloud(Map<String, String> cloud) {
        this.cloud = cloud;
    }

    public Map<String, String> getPageoffice() {
        return pageoffice;
    }

    public void setPageoffice(Map<String, String> pageoffice) {
        this.pageoffice = pageoffice;
    }

    public Map<String, String> getUpload() {
        return upload;
    }

    public void setUpload(Map<String, String> upload) {
        this.upload = upload;
    }

    public Map<String, String> getWebservice() {
        return webservice;
    }

    public void setWebservice(Map<String, String> webservice) {
        this.webservice = webservice;
    }

    public Map<String, String> getSysParams() {
        return sysParams;
    }

    public void setSysParams(Map<String, String> sysParams) {
        this.sysParams = sysParams;
    }

    public Map<String, String> getOfficeConfig() {
        return officeConfig;
    }

    public void setOfficeConfig(Map<String, String> officeConfig) {
        this.officeConfig = officeConfig;
    }

    public Map<String, String> getCreditQuery() {
        return creditQuery;
    }

    public void setCreditQuery(Map<String, String> creditQuery) {
        this.creditQuery = creditQuery;
    }
}
package app.component.rules.entity;

import java.util.Map;

/**
 * <p>FiveclassBean</p>
 * <p>Description:五级分类前置条件检查</p>
 * @author GuoXY
 * @date 2017-3-18 14:38:12
 */
public class FiveclassBean {

    private String vouType;    //担保类型
    private String repayType;   //还款方式
    private Integer overdueDay;    //逾期天数
    
    //获得结果
    private Map<String,Object> resultMap;

    public String getVouType() {
        return vouType;
    }

    public void setVouType(String vouType) {
        this.vouType = vouType;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public Integer getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(Integer overdueDay) {
        this.overdueDay = overdueDay;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}

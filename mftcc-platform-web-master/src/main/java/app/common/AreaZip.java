package app.common;

import org.apache.commons.lang3.StringUtils;

/**
 *  行政区划邮编映射
 */
public class AreaZip {


    public static String getZip(String addr) {
        if (StringUtils.isEmpty(addr)) {
            return null;
        }
        if(addr.startsWith("北京市")){
            if(addr.startsWith("北京市海淀区")){
                return "100089";
            }
            if(addr.startsWith("北京市朝阳区")){
                return "100020";
            }
            if(addr.startsWith("北京市东城区")){
                return "100010";
            }
            if(addr.startsWith("北京市西城区")){
                return "100032";
            }
            if(addr.startsWith("北京市丰台区")){
                return "100071";
            }
            if(addr.startsWith("北京市大兴区")){
                return "102600";
            }
            if(addr.startsWith("北京市昌平区")){
                return "102200";
            }
            if(addr.startsWith("北京市通州区")){
                return "101149";
            }
            if(addr.startsWith("北京市石景山区")){
                return "100043";
            }
            if(addr.startsWith("北京市门头沟区")){
                return "102300";
            }
            if(addr.startsWith("北京市房山区")){
                return "102488";
            }
            if(addr.startsWith("北京市顺义区")){
                return "101300";
            }
            if(addr.startsWith("北京市怀柔区")){
                return "101400";
            }
            if(addr.startsWith("北京市平谷区")){
                return "101200";
            }
            if(addr.startsWith("北京市密云区")){
                return "101500";
            }
            if(addr.startsWith("北京市延庆区")){
                return "102100";
            }
        }
        return null;
    }
}

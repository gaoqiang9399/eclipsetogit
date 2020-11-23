package app.component.thirdpay.util;

import cn.mftcc.util.StringUtil;

public class BankUtil {
	
	public static String getBankCodeByCardNum(String cardNum){
		String bankId = "";
		String banknumbei = cardNum;
		if(StringUtil.isNotBlank(banknumbei)){//根据开户行号获取到银行编码
			if(banknumbei.startsWith("0102")){//中国工商银行
				bankId="ICBC";
			}else if(banknumbei.startsWith("0103")){//中国农业银行
				bankId = "ABC";
			}else if(banknumbei.startsWith("0105")){//中国建设银行
				bankId = "CCB";
			}else if(banknumbei.startsWith("0104")){//中国银行
				bankId = "BOC";
			}else if(banknumbei.startsWith("0100")){//中国邮政储蓄银行
				bankId = "PSBC";
			}else if(banknumbei.startsWith("0308")){//招商银行
				bankId = "CMB";
			}else if(banknumbei.startsWith("0309")){//兴业银行
				bankId = "CIB";
			}else if(banknumbei.startsWith("6303")||banknumbei.startsWith("0303")){//中国光大银行
				bankId = "CEB";
			}else if(banknumbei.startsWith("0306")){//广发银行
				bankId = "GDB";
			}else if(banknumbei.startsWith("0307")||banknumbei.startsWith("0318")||banknumbei.startsWith("0410")||banknumbei.startsWith("0610")){//平安银行
				bankId = "PAB";
			}else if(banknumbei.startsWith("0305")){//中国民生银行
				bankId = "CMBC";
			}else if(banknumbei.startsWith("0310")){//上海浦东发展银行
				bankId = "SPDB";
			}else if(banknumbei.startsWith("0302")){//中信银行
				bankId = "CNCB";
			}else if(banknumbei.startsWith("0301")){//交通银行
				bankId = "BOCOM";
			}else if(banknumbei.startsWith("0403")){//北京银行
				bankId = "BCCB";
			}else if(banknumbei.startsWith("0401")){//上海银行
				bankId = "BOS";
			}else if(banknumbei.startsWith("0304")){//华夏银行
				bankId = "HXB";
			}else {
			}
		}
		return bankId;
	}
}

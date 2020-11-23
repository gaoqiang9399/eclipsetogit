package app.component.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参考网上资料，15位身份证转化18位或者18位转化成15位，还有校验身份证是否合法，也加一个自己写的方法 
 * 由一种身份证号码求到另外一个身份证号码
 * @author fuww
 *
 */
public class IdCardUtil {

	private static Pattern pattern = Pattern.compile("\\d{15}|\\d{17}[x,X,0-9]");
	private static Pattern pat = Pattern.compile("^[0-9A-Z]{8}-[0-9X]$");
	public IdCardUtil(){
        
    }
	
	/** *//**
     * 15位身份证号码转化为18位的身份证。如果是18位的身份证则直接返回，不作任何变化。
     * @param idCard,15位的有效身份证号码
     * @return idCard18 返回18位的有效身份证
     */
    public String IdCard15to18(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        //加权因子
        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //校验码值
        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        //15位的身份证
        if(idCard != null && idCard.length()==15){
            idCard18.insert(6, "19");
            for(int index=0;index<idCard18.length();index++){
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
//                logger.debug(new Integer(ai));
                //sum = sum+ai*weight[index];
                //加权因子的算法
                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
                sum = sum+ai*Wi;
            }
            int indexOfCheckBit = sum%11; //取模
            idCard18.append(checkBit[indexOfCheckBit]);
        }
//        logger.debug(idCard18);
        return idCard18.toString();
    }
    
    /**
     * 此方法为20后的人校验身份证服务的,不知道20后18身份证生成算法，暂和20前的算法一样。0........                              
     * @param idCard
     * @return
     */
    public String IdCard15to18for20hou(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        //加权因子
        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //校验码值
        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        //15位的身份证
        if(idCard != null && idCard.length()==15){
            idCard18.insert(6, "20");
            for(int index=0;index<idCard18.length();index++){
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
//                logger.debug(new Integer(ai));
                //sum = sum+ai*weight[index];
                //加权因子的算法
                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
                sum = sum+ai*Wi;
            }
            int indexOfCheckBit = sum%11; //取模
            idCard18.append(checkBit[indexOfCheckBit]);
        }
//        logger.debug(idCard18);
        return idCard18.toString();
    }
    
    /** *//**
     * 转化18位身份证位15位身份证。如果输入的是15位的身份证则不做任何转化，直接返回。
     * @param idCard 18位身份证号码
     * @return idCard15
     */
    public String IdCard18to15(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard15 = new StringBuffer(idCard);
        if(idCard!=null && idCard.length()==18){
            idCard15.delete(17, 18);
            idCard15.delete(6, 8);
        }
//        logger.debug(idCard15);
        return idCard15.toString();

    }
    /** *//**
     * 校验是否是一个有效的身份证。如果是18的身份证，则校验18位的身份证。15位的身份证不校验，也无法校验
     * @param idCard
     * @return
     */
    public boolean checkIDCard(String idCard){
        boolean isIDCard = false;
        Matcher matcher = pattern.matcher(idCard);
        if(matcher.matches()){//可能是一个身份证
            isIDCard = true;
            if(idCard.length()==18){//如果是18的身份证，则校验18位的身份证。15位的身份证暂不校验
                String IdCard15 = IdCard18to15(idCard);
                String IdCard18 =null;
                if("20".equals(idCard.substring(6, 8))){
                IdCard18 = IdCard15to18for20hou(IdCard15);
                }else{
                IdCard18 = IdCard15to18(IdCard15);
                }
         //       System.out.println(IdCard18+"——--校验后产生的");
                if(!idCard.equals(IdCard18)){
                    isIDCard = false;
                }
            }else if(idCard.length()==15){
               isIDCard = true;
            }else{
                isIDCard = false;
            }
        }
        return isIDCard;
    } 
    /**此方法是取一个身份证另外一个号码
     * @param args
     */
    public String getOtherIdNum(String idCard){
    	String otherNum ="";
    	if(null != idCard && (!"".equals(idCard)) && idCard.length() == 15){
      		otherNum = IdCard15to18(idCard);
    	}
    	else if(null != idCard && (!"".equals(idCard)) && idCard.length() == 18){
    		otherNum = IdCard18to15(idCard);
    	}else {
          //System.out.println(idCard+"--此身份证不符合条件");
    	}
    	return otherNum;
    }
    
    /**
     * 功能描述：从身份证中获取出生年月
     * date 2016-01-14
     */
    public static String getBirthdayFromIdCard(String idCard){
    	if(idCard != null){
    		if(idCard.length() == 18){
    			return idCard.substring(6, 14);
    		}else{
    			return "19" + idCard.substring(6,12);
    		}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 功能描述：从身份证中判断性别 true 男 false 女
     * date 2016-01-14
     */
    public static String getSexFromIdCard(String idCard){
    	char temp;
    	int temp_int;
    	if(idCard.length() == 18){
    		temp = idCard.charAt(16);
    	}else{
    		temp = idCard.charAt(14);
    	}
    	temp_int = Integer.valueOf(temp) % 2;
    	if(temp_int == 0){
    		return "1";
    	}else{
    		return "0";
    	}
    }
    
    /**
	 * 检验组织结构代码是否合法 标准:GB11714-1995 返回值false true
	 */
	public static boolean cheakOrgCode(String str) {
		final String[] codeNo = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
				"L", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		final String[] staVal = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
				"19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "31", "32", "33", "34", "35" };
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < codeNo.length; i++) {
			map.put(codeNo[i], staVal[i]);
		}
		final int[] wi = { 3, 7, 9, 10, 5, 8, 4, 2 };
		Matcher matcher = pat.matcher(str);
		if (!matcher.matches()) {
			// System.out.println("你的表达式非法");
			return false;
		}
		String[] all = str.split("-");
		final char[] values = all[0].toCharArray();
		int parity = 0;
		for (int i = 0; i < values.length; i++) {
			final String val = Character.toString(values[i]);
			parity += wi[i] * Integer.parseInt(map.get(val).toString());
		}

		int check = 11 - parity % 11;
		String cheak = "";

		if (check == 10) {
			cheak = "X";
		} else if (check == 11) {
			cheak = "0";
		} else {
			cheak = Integer.toString(check);
		}

		return cheak.equals(all[1]);
	}
	/**
	 * 信用代码证校验
	 * @param s
	 * @return
	 */
	 public static boolean cheakCreditCode(String businessCode){
		 if( ("".equals(businessCode))||businessCode.length() != 18){
				return false;
			}
			String baseCode = "0123456789ABCDEFGHJKLMNPQRTUWXY";
			char[] baseCodeArray = baseCode.toCharArray();
			Map<Character, Integer> codes = new HashMap<Character, Integer>();
			for(int i = 0; i < baseCode.length(); i++) {
				codes.put(baseCodeArray[i], i);
			}
			char [] businessCodeArray = businessCode.toCharArray();
		 	Character check = businessCodeArray[17];
			if (baseCode.indexOf(check)==-1) {
				return false;
			}
		 	int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
		 	int sum = 0;
		 	for (int i = 0; i < 17; i++) {
		 		Character key = businessCodeArray[i];
		 		if (baseCode.indexOf(key)==-1) {
		 			return false;
		 		}
		 		sum += (codes.get(key) * wi[i]);
		 	}
		 	int value = 31 - sum % 31;
		 	if(value==31){
		 		value = 0;
		 	}
			return value == codes.get(check);
    }
	 /**
	  * 校验营业执照号
	  * @param businesslicense
	  * @return
	  */
	 public static boolean isBusinesslicense(String businesslicense){
		 if(businesslicense!=null&&!"".equals(businesslicense.trim())&&businesslicense.length()==15){
			 String lic14 = businesslicense.substring(0,14);// 获取营业执照注册号前14位数字用来计算校验码  
		        String lic15 = businesslicense.substring(14,businesslicense.length());// 获取营业执照号的校验码  
		        char[] chars = lic14.toCharArray();  
		        int[] ints = new int[chars.length];  
		        for(int i=0; i<chars.length;i++){  
		            ints[i] = Integer.parseInt(String.valueOf(chars[i]));  
		        }  
		        int check = -1;
		        if (null != ints && ints.length > 1) {  
		            int ti = 0;  
		            int si = 0;
		            int cj = 0;
		            int pj = 10; 
		            for (int i=0;i<ints.length;i++) {  
		                ti = ints[i];  
		                pj = (cj % 11) == 0 ? 10 : (cj % 11);  
		                si = pj + ti;  
		                cj = (0 == si % 10 ? 10 : si % 10) * 2;  
		                if (i == ints.length-1) {  
		                    pj = (cj % 11) == 0 ? 10 : (cj % 11);  
		                    check = pj == 1 ? 1 : 11 - pj;  
		                }  
		            }  
		        }  
		        if(lic15.equals(check+"")){// 比较 填写的营业执照注册号的校验码和计算的校验码是否一致  
		            return true;
		        }  
		 }
		 return false;
	 }
	 
	 /**
	  * 更具身份证号计算年龄
	  * @param IdNO
	  * @return
	  */
	 public static int idNoToAge(String idNo){
		 int leh = idNo.length();
		 if(idNo!=null&&!"".equals(idNo)){
			 String dates="";
			 if (leh == 18) {
				 dates = idNo.substring(6, 10);
				 SimpleDateFormat df = new SimpleDateFormat("yyyy");
				 String year=df.format(new Date());
				 int u=Integer.parseInt(year)-Integer.parseInt(dates);
				 return u;
			 }else{
				 dates = idNo.substring(6, 8);
				 return Integer.parseInt(dates);
			 }
		 }else{
			 return 0;
		 }
    }
}

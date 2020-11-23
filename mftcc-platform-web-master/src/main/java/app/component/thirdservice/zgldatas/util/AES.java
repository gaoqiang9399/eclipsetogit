package app.component.thirdservice.zgldatas.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public  class AES {

	// 说明 key 需要大家自己去设定加密解密的key，key牵涉到安全信息,所以这里无法公布

	private static final String transform = "AES/ECB/PKCS5Padding";

	private static final String algorithm = "AES";
	//解密
	public static String decrypt(String content, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(transform);
		byte[] encryptedBytes = Base64.decodeBase64(content);
		SecretKeySpec keySpec = new SecretKeySpec(getKey(key),algorithm);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes,"UTF-8");
	}
	//加密
	public static String encrypt(String content, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(transform);
		SecretKeySpec keySpec = new SecretKeySpec(getKey(key), algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);

		byte[] output = cipher.doFinal(content.getBytes("UTF-8"));
		return Base64.encodeBase64String(output);
	}
	
	private static byte[] getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes("UTF-8");
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
       
        return arrB;
    }

	
	
//	public static void main(String[] args) throws Exception {
//		String str2 = AES.encrypt("000000","4CB85BBA862A4234B226897A1ED92E70");
//		System.out.println(str2);
//	}
	
	
}
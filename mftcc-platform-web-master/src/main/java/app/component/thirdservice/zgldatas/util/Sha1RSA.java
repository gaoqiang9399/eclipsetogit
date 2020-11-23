package app.component.thirdservice.zgldatas.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Sha1RSA {

	public static String sign(String content, String privateKey) {
		try {		
			PrivateKey priKey = decodePrivateKeyFromXml(privateKey);
			java.security.Signature signature = java.security.Signature
					.getInstance("SHA1WithRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes("UTF-8"));
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean doCheck(String content, String sign, String publicKey) {
		try {			
			PublicKey pubKey = decodePublicKeyFromXml(publicKey);
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes("UTF-8"));
			return signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static String encodePublicKeyToXml(PublicKey key) {
		if (!RSAPublicKey.class.isInstance(key)) {
			return null;
		}
		RSAPublicKey pubKey = (RSAPublicKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>")
				.append(Base64.encodeBase64String(pubKey.getModulus()
						.toByteArray())).append("</Modulus>");
		sb.append("<Exponent>")
				.append(Base64.encodeBase64String(pubKey.getPublicExponent()
						.toByteArray())).append("</Exponent>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}

	public static PublicKey decodePublicKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64.decodeBase64(StringUtil
				.GetMiddleString(xml, "<Modulus>", "</Modulus>")));
		BigInteger publicExponent = new BigInteger(1,
				Base64.decodeBase64(StringUtil.GetMiddleString(xml,
						"<Exponent>", "</Exponent>")));

		RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus,
				publicExponent);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePublic(rsaPubKey);
		} catch (Exception e) {
			return null;
		}
	}

	public static PrivateKey decodePrivateKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64.decodeBase64(StringUtil
				.GetMiddleString(xml, "<Modulus>", "</Modulus>")));
		BigInteger publicExponent = new BigInteger(1,
				Base64.decodeBase64(StringUtil.GetMiddleString(xml,
						"<Exponent>", "</Exponent>")));
		BigInteger privateExponent = new BigInteger(1,
				Base64.decodeBase64(StringUtil.GetMiddleString(xml, "<D>",
						"</D>")));
		BigInteger primeP = new BigInteger(1, Base64.decodeBase64(StringUtil
				.GetMiddleString(xml, "<P>", "</P>")));
		BigInteger primeQ = new BigInteger(1, Base64.decodeBase64(StringUtil
				.GetMiddleString(xml, "<Q>", "</Q>")));
		BigInteger primeExponentP = new BigInteger(1,
				Base64.decodeBase64(StringUtil.GetMiddleString(xml, "<DP>",
						"</DP>")));
		BigInteger primeExponentQ = new BigInteger(1,
				Base64.decodeBase64(StringUtil.GetMiddleString(xml, "<DQ>",
						"</DQ>")));
		BigInteger crtCoefficient = new BigInteger(1,
				Base64.decodeBase64(StringUtil.GetMiddleString(xml,
						"<InverseQ>", "</InverseQ>")));

		RSAPrivateCrtKeySpec rsaPriKey = new RSAPrivateCrtKeySpec(modulus,
				publicExponent, privateExponent, primeP, primeQ,
				primeExponentP, primeExponentQ, crtCoefficient);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePrivate(rsaPriKey);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encodePrivateKeyToXml(PrivateKey key) {
		if (!RSAPrivateCrtKey.class.isInstance(key)) {
			return null;
		}
		RSAPrivateCrtKey priKey = (RSAPrivateCrtKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>")
				.append(Base64.encodeBase64String(priKey.getModulus()
						.toByteArray())).append("</Modulus>");
		sb.append("<Exponent>")
				.append(Base64.encodeBase64String(priKey.getPublicExponent()
						.toByteArray())).append("</Exponent>");
		sb.append("<P>")
				.append(Base64.encodeBase64String(priKey.getPrimeP()
						.toByteArray())).append("</P>");
		sb.append("<Q>")
				.append(Base64.encodeBase64String(priKey.getPrimeQ()
						.toByteArray())).append("</Q>");
		sb.append("<DP>")
				.append(Base64.encodeBase64String(priKey.getPrimeExponentP()
						.toByteArray())).append("</DP>");
		sb.append("<DQ>")
				.append(Base64.encodeBase64String(priKey.getPrimeExponentQ()
						.toByteArray())).append("</DQ>");
		sb.append("<InverseQ>")
				.append(Base64.encodeBase64String(priKey.getCrtCoefficient()
						.toByteArray())).append("</InverseQ>");
		sb.append("<D>")
				.append(Base64.encodeBase64String(priKey.getPrivateExponent()
						.toByteArray())).append("</D>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}
}

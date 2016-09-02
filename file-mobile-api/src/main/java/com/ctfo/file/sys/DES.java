package com.ctfo.file.sys;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {

	// 解密数据
	protected final static String PUBLICKEY = "CTFOTRV1";// 公共秘钥

	/**
	 * 输入解密的信息与解密的编码规则，返回解密后的字符串
	 * @param message 待解密消息
	 * @param key 编码
	 * @return 解密后字符串
	 * @throws Exception
	 */
	private static String decrypt(String message, String key) throws Exception {

		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte,"UTF-8");
	}
	public static byte[] decryptByte(byte[] b) throws Exception{
		byte[] bytesrc = b;
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(PUBLICKEY.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(PUBLICKEY.getBytes("gb2312"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return retByte;
	}

	/**
	 * 指定编码对字符串进行加密
	 * @param message 待加密消息
	 * @param key 编码
	 * @return 加密后数据
	 * @throws Exception
	 */
	protected static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}
	
	/**
	 * 指定编码对byte进行加密
	 * @param message 待加密消息
	 * @param key 编码
	 * @return 加密后数据
	 * @throws Exception
	 */
	protected static byte[] encryptByte(byte[] message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message);
	}
	
	/**
	 * 把待解密字符串转换为字节数组
	 * @param ss 待解密信息
	 * @return 字节数组
	 */
	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	/**
	 * 对外提供加密方法，默认为 UTF-8编码格式
	 * @param message 待加密信息
	 * @return 加密后的字符串
	 */
	public static String encrypt(String message) {
		if (message == null || message.trim().length() == 0) {
			System.err.println("message is  empty");
			return null;
		}
		try {
			String mw = toHexString(encrypt(message, PUBLICKEY)).toUpperCase();
			return mw;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 对外提供的解密方法，默认为先Base64解密再UTF-8 解密
	 * @param ciphertext 待解密字符串
	 * @return 解密后信息
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext){
		try {
			String clearText = decrypt(ciphertext, PUBLICKEY);
			return clearText;
		} catch (Exception e) {
			System.err.println("ciphertext is  empty");
			return null;
		}
	}
	 
	/**
	 * 数据加密方法
	 * @param b 待加密的字节数组
	 * @return 加密后的字符串
	 */
	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	public static void main(String[] args) throws Exception {
          String value = "abcaaaaaaa";
		 String ttt = encrypt(value);
		 System.out.println("加密后的数据为:" + ttt);
	 
		System.out.println("解密后的数据 :" + decrypt(ttt));
	}
}

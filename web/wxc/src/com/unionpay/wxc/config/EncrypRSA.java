package com.unionpay.wxc.config;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncrypRSA {
	
	/**
	 * 加密
	 * @param publicKey
	 * @param srcBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	protected byte[] encrypt(RSAPublicKey publicKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		if(publicKey!=null){
			//Cipher负责完成加密或解密工作，基于RSA
			Cipher cipher = Cipher.getInstance("RSA");
			//根据公钥，对Cipher对象进行初始化
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] resultBytes = cipher.doFinal(srcBytes);
			return resultBytes;
		}
		return null;
	}
	
	/**
	 * 解密 
	 * @param privateKey
	 * @param srcBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	protected byte[] decrypt(RSAPrivateKey privateKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		if(privateKey!=null){
			//Cipher负责完成加密或解密工作，基于RSA
			Cipher cipher = Cipher.getInstance("RSA");
			//根据公钥，对Cipher对象进行初始化
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] resultBytes = cipher.doFinal(srcBytes);
			return resultBytes;
		}
		return null;
	}

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		System.out.println(new String("中文".getBytes("utf8")));
		System.out.println(new String("中文".getBytes("GBK")));
	}

}

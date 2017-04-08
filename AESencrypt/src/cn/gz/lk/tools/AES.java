package cn.gz.lk.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES Coder<br/>
 * secret key length:	128bit, default:	128 bit<br/>
 * mode:	ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding:	Nopadding/PKCS5Padding/ISO10126Padding/
 * @author NTG
 * 
 */
public class AES{
	
	/**
	 * ��Կ�㷨
	*/
	private static final String KEY_ALGORITHM = "AES";
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**
	 * ��ʼ����Կ
	 * 
	 * @return byte[] ��Կ 
	 * @throws Exception
	 */
	public static byte[] initSecretKey() {
		//��������ָ���㷨��������Կ�� KeyGenerator ����
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
		//��ʼ������Կ��������ʹ�����ȷ������Կ��С
		//AES Ҫ����Կ����Ϊ 128
		kg.init(128);
		//����һ����Կ
		SecretKey  secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * ת����Կ
	 * 
	 * @param key	��������Կ
	 * @return ��Կ
	 */
	public Key toKey(byte[] key){
		//������Կ
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data,byte[] key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//��ԭ��Կ
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//ʵ����
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//ʹ����Կ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//ִ�в���
		return cipher.doFinal(data);
	}
	
	
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data,byte[] key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//��ԭ��Կ
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//ʵ����
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//ʹ����Կ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.DECRYPT_MODE, key);
		//ִ�в���
		return cipher.doFinal(data);
	}
	
	private static String  showByteArray(byte[] data){
		if(null == data){
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for(byte b:data){
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		AES aes=new AES();
		byte[] key = initSecretKey();//ͨ���������㷨���ɸ��ڲ����㷨ʵ����Կ�ĳ�ʼ��
		Key k = aes.toKey(key);//��byte���͵���Կת����Key����
		Tool tool=new Tool();
		File img=new File("C:\\Users\\LK\\Desktop\\watermarked.jpg");
		String imgString=img.toString();
		FileInputStream inputStream=new FileInputStream(img);
		FileOutputStream fil=new FileOutputStream("C:\\Users\\LK\\Desktop\\���ܺ�.jpg");
		FileOutputStream fil1=new FileOutputStream("C:\\Users\\LK\\Desktop\\���ܺ�.txt");
		byte [] content = tool.getImageOrFile(imgString);
		byte[] encryptData1 = encrypt(content, k);// 
		fil1.write(encryptData1,0,encryptData1.length);
		byte[] decryptData = decrypt(encryptData1, k);
		fil.write(decryptData,0,decryptData.length);
	}
	public String byteToString(byte[] b) {
		String result = "";
		String temp;
		for (int i = 0; i < b.length; i++) {
			temp = Byte.toString(b[i]);
			result = result + temp + ",";
		}
		return result;
	}
}

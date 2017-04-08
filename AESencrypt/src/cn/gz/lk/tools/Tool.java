package cn.gz.lk.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Administrator 工具类
 */
public class Tool {
	//用来记录收到的信息的String形式
		public static String resultStr = "";
		//用来记录信息的格式
		public static String mark = "";
		//用来记录弹出框的message
		public static String message = "";
	// 形成量子加密的密钥，随机的形成0-255就可以了
	public Tool(){
	}

	public void initKeySaveToFile() {
		File file = new File("E:\\QuantumKey.txt");
		String content = "";
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			int count = 0;
			while (count < 100000) {
				// 形成随机的0-255
				content = String.valueOf(new Double(
						Math.ceil(Math.random() * 127)).intValue());
				fw.write(content + ",");
				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 将String转换为byte数组
	 * */
	public byte[] StringToByte(String str) {
		String strArr[] = str.split(",");
		byte b[] = new byte[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			System.out.println("strArr[i] is " + strArr[i]);
			b[i] = Byte.decode(strArr[i]);
		}
		return b;
	}

	// 截取图片和文件的路径和名称
	public Map<String, String> getPathAndName(String str) {
		Map<String, String> map = new HashMap<String, String>();
		String reg = ".*?path:(.*?);name:(.*)";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		while (m.find()) {
			String path = m.group(1);
			System.out.println(m.group(0));
			String name = m.group(2);
			map.put("path", path);
			map.put("name", name);
		}
		return map;
	}

	// 从文件中读取密钥
	public String readKeyFromFile(String fileName) {
		File file = new File(fileName);
		String key = "";
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			// 将inputstream转换成为reader
			br = new BufferedReader(new InputStreamReader(fis));
			String content = br.readLine();
			while (content != null) {
				key += content;
				content = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return key;
	}

	/**
	 * 
	 * 获得文件或图片的内容
	 * */
	@SuppressWarnings("resource")
	public byte[] getImageOrFile(String path) {
		File file = new File(path);
		FileInputStream fis;
		String result = "";
		try {
			fis = new FileInputStream(file);
			byte[] temp = new byte[1024];
			int count = 0;
			do{
				count = fis.read(temp);
				if(count != 1024)
					temp = removeZero(temp, count);
				//System.out.println(temp.length);
				result += byteToString(temp);
			}while (count == 1024); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte [] b =  StringToByte(result);
		b = fixToSixteenTime(b);
		return b;
	}

	/**
	 * 将byte数组转化为String,
	 * */
	public String byteToString(byte[] b) {
		String result = "";
		String temp;
		for (int i = 0; i < b.length; i++) {
			temp = Byte.toString(b[i]);
			result = result + temp + ",";
		}
		return result;
	}
	
	/**
	 * 将要发送的信息进行编码
	 * 编码格式：
	 * 第一个字节：0表示字符串，1代表图片，2代表文件
	 * 如果第一个字节不是0的话，第2-101 字节，表示的就是文件名或者是图像名称,
	 * 如果名称这一部分不够100字节的话，就以0来填充
	 * */
	public byte [] EncodeSendData(byte [] data,byte sign,String name){
		byte [] finalData;
		int count = 0 ;
		if(sign == 0){//如果是字符串的话
			 finalData = new byte[data.length + 1 ];
			finalData [0] = sign;
			count = 1;
		}
		else{//如果是图片或者是文件的话
			finalData = new byte[data.length + 1 + 100];
			finalData [0] = sign; 
			byte [] nameByte = name.getBytes();
			for(int n = 0 ;n < nameByte.length ;n++)
				finalData[n+1] = nameByte[n];
			for(int m = nameByte.length ;m < 100 ;m++ ){
				finalData[m + 1]= 0;
			}
			count = 101;
		}
		for(int j = 0 ; j < data.length ;j++)
			finalData[count + j] = data[j];
		return finalData;
	}
	//将文件存储在本地中
	public void saveFileToLocal(String fileName,byte [] data){
		File file = new File("E:\\"+fileName);
		FileOutputStream fos  = null;
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {
			 fos = new FileOutputStream(file);
			 fos.write(data);//将数据写到文件中去
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * 
	 * 转换为最近的16的倍数
	 */
	public byte[] fixToSixteenTime(byte [] b){
		int count = b.length;
		int base = count % 16;
		byte [] fix = new byte[b.length + 16 - base];
		for(int i = 0 ;i < fix.length ;i++){
			if(i < b.length )
				fix[i] = b[i];
			else
				fix[i] = 0;
		}
		return fix;	
		
	}
	
	
	/**
	 *去除byte中多余的0
	 * */
	public byte[] removeZero(byte b [] ,int leftCount){
		byte [] left = new byte[leftCount];
		for(int i = 0 ;i < leftCount;i++)
			left[i] = b[i];
		return left;
	}
}

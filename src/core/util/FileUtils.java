package core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import core.log.LogService;


public class FileUtils {
	//�����ļ���
	  public static boolean CreateDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (dir.exists()) {  
	            System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѿ�����");  
	            return false;  
	        }  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	        //����Ŀ¼  
	        if (dir.mkdirs()) {  
	            System.out.println("����Ŀ¼" + destDirName + "�ɹ���");  
	            return true;  
	        } else {  
	            System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�");  
	            return false;  
	        }  
	    } 
	//��ȡ�ı��ļ�
	public static String LoadTextFile(String path) {

		 StringBuilder result = new StringBuilder();
	        try{
	        	File file=new File(path);
	        	InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
	            BufferedReader br = new BufferedReader(read);//����һ��BufferedReader������ȡ�ļ�
	            String s = null;
	            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
	                result.append(s+"\r\n");
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return result.toString();
	}
	//��ȡ�ı��ļ�
	public static String[] LoadLineTextFile(String path) {
		  /* ����TXT�ļ� */  
       // String pathname = "D:\\twitter\\13_9_6\\dataset\\en\\input.txt"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��  
        File file = new File(path); // Ҫ��ȡ����·����input��txt�ļ� 
        ArrayList<String> list = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            	list.add(s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        String[] arr=new String[list.size()];
        return  list.toArray(arr);
	}
	
	
	public static void WriteTextFile(String path,String data) {
		
		 /* д��Txt�ļ� */  
        File file = new File(path); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  

		try {
			if(file.exists()) {
				file.delete();
			}else {
				CreateDir(file.getParentFile().getPath());
			}
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(data);
			 out.flush(); // �ѻ���������ѹ���ļ�  
		        out.close(); // ���ǵùر��ļ� 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}
	
    /** 
     * B����׷���ļ���ʹ��FileWriter 
     */  
    public static void AppendTextFile(String path, String content) {  
        try {  
        	 File file = new File(path);
        	if(!file.exists()) {
        		file.createNewFile();
			}
			
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�  
            FileWriter writer = new FileWriter(path, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  

}

package com.frameWork.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.frameWork.service.LogService;

public class DataManager
{
	static String s_modelName = "";
	static String path = "./Data/";
	
	static HashMap<String, DataTable> s_datas = new HashMap<>();
	
	public static DataTable GetData(String dataName) throws Exception
	{
		if(s_datas.containsKey(dataName))
		{
			return s_datas.get(dataName);
		}
		
		return ReadData(dataName);
	}
	
	static DataTable ReadData(String dataName) throws Exception
	{
		String conetnt = ReadText(dataName);
		
		DataTable dataTable = DataTable.Analysis(conetnt);
		
		s_datas.put(dataName, dataTable);
		
		return dataTable;
	}
	
	static String ReadText(String dataName)
	{
		String conetnt = "";
		try {
			
			String filePath = path + dataName + ".txt";
			
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
            	
                InputStreamReader read = 
                		new InputStreamReader(new FileInputStream(file),"UTF-8");//���ǵ������ʽ
                
                @SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(read);
                
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                {
                	conetnt += line + "\r\n";
                }

//                read.close();
		    }
            else
		    {
		    	LogService.Error(s_modelName, "�Ҳ���ָ�����ļ� " + dataName);
		    }
            
	    } catch (Exception e) {
	    	
	        LogService.Exception(s_modelName, "��ȡ�ļ����ݳ��� " + dataName, e);
	    }
		return conetnt;
	}
}

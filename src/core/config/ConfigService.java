package core.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import core.log.LogService;

public class ConfigService 
{
	public final static String configPath = "./Resources/Config/"; 
	
	static HashMap<String, Properties> s_configs = new HashMap<>();
	
	public static Properties GetProperties(String configName)
	{
		if(!s_configs.containsKey(configName))
		{
			Properties propertys = new Properties();  

			FileInputStream fis;
			try {
				fis = new FileInputStream( ConfigService.configPath + configName + ".txt" );
				propertys.load(fis);
				
				fis.close();
				
			} catch (Exception e) {
				LogService.Warn("", "�Ҳ��������ļ� " + configName);
			}

			s_configs.put(configName, propertys);
		}
		
		return s_configs.get(configName);
	}
	
	public static String GetString(String configName , String key,String defaultValue)
	{
		Properties properties =GetProperties(configName);
		
		if(properties != null)
		{
			return properties.getProperty(key);
		}
		else 
		{
			return defaultValue;
		}
	}
	
	public static int GetInt(String configName , String key,int defaultValue) 
	{
		Properties properties =GetProperties(configName);
		
		if(properties != null)
		{
			try
			{
				return Integer.parseInt(  properties.getProperty(key));
			}
			catch (Exception e) 
			{
				return defaultValue;
			}
		}
		else 
		{
			return defaultValue;
		}
	}
	
	public static float GetFloat(String configName , String key,Float defaultValue) throws Exception
	{
		Properties properties =GetProperties(configName);
		
		if(properties != null)
		{	
			try
			{
				return Float.parseFloat( properties.getProperty(key));
			}
			catch (Exception e) 
			{
				return defaultValue;
			}
		}
		else 
		{
			return defaultValue;
		}
	}
	
	public static boolean GetBool(String configName , String key,boolean defaultValue)
	{
		Properties properties =GetProperties(configName);
		if(properties != null)
		{			
			try
			{
				return Boolean.parseBoolean(properties.getProperty(key));
			}
			catch (Exception e) 
			{
				return defaultValue;
			}
		}
		else 
		{
			return defaultValue;
		}
	}
	public static String[] GetStringArray(String configName , String key,String[] defaultValue)
	{
		Properties properties =GetProperties(configName);
		if(properties != null)
		{			
			try
			{
				String content =properties.getProperty(key);
				if (content.contains("|")) {
					return content.split("\\|");
				}
				else {
					return new String[] {content};
				}
			}
			catch (Exception e) 
			{
				return defaultValue;
			}
		}
		else 
		{
			return defaultValue;
		}
	}
}

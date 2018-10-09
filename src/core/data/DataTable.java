package core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.log.LogService;

public class DataTable extends HashMap<String,SingleData >
{
	private static final long serialVersionUID = 1L;

	static String s_modelName = "DataTable";
	
	  /// <summary>
    /// ������¼��ӵ�е��ֶ���
    /// </summary>
	public List<String> TableKeys;
	  /// <summary>
    /// �������е�Key
    /// </summary>
	public List<String> TableIDs;

	public HashMap<String, String> defaultValue= new HashMap<String, String>();
	
    public HashMap<String, String> m_noteValue= new HashMap<String, String>();

    /// <summary>
    /// ����ÿ���ֶ���ʲô����
    /// </summary>
    public HashMap<String, FieldType> m_tableTypes = new HashMap<String, FieldType>();
    
    public HashMap<String, String> m_tableEnumTypes= new HashMap<String, String>();
	
	 public static DataTable Analysis(String stringData) throws Exception
	    {
	        try
	        {
//	        	LogService.Log("", "���: stringData :"+stringData);
	            int lineIndex = 0;
	            DataTable data = new DataTable();
	            String[] line = stringData.split("\r\n");

	            //��һ����ΪKey
	            data.TableKeys = new ArrayList<>();
	            String[] rowKeys = ConvertStringArray(line[lineIndex]);
	            for (int i = 0; i < rowKeys.length; i++)
	            {
	            	String r =rowKeys[i];
	                if (!rowKeys[i].equals(""))
	                {
//	                	LogService.Log("", "���key ��"+ r);
	                    data.TableKeys.add(r);
	                }
	            }

	            String[] LineData;
	            for (lineIndex = 1;lineIndex <line.length;lineIndex++)
	            {
	                LineData = ConvertStringArray(line[lineIndex]);

	                //ע��
	                if (LineData[0].equals("note"))
	                {
                        AnalysisNoteValue(data, LineData);
	                }
	                //Ĭ��ֵ
	                else if (LineData[0].equals("default"))
	                {
	                    AnalysisDefaultValue(data, LineData);
	                }
                    //��������
                    else if (LineData[0].equals("type"))
                    {
                        AnalysisFieldType(data, LineData);
                    }
	                //��������
	                else
	                {
	                    break;
	                }
	            }    
	            
//	            lineIndex = 3;

	            data.TableIDs = new ArrayList<String>();

	            //��ʼ��������
	            for (int i = lineIndex; i < line.length; i++)
	            {
	                SingleData dataTmp = new SingleData();
	                dataTmp.data = data;
	                String[] row = ConvertStringArray(line[i]);

	                for (int j = 0; j < row.length; j++)
	                {
	                    if (!row[j].equals(""))
	                    {
	                        dataTmp.put(data.TableKeys.get(j), row[j]);
	                    }
	                }

	                //��һ��������Ϊ��һ����¼��Key
	                data.put(row[0], dataTmp);
	                data.TableIDs.add(row[0]);
	            }

	            return data;
	        }
	        catch (Exception e)
	        {
	        	LogService.Exception(s_modelName, e.toString(), e);
	        	
	            throw new Exception("Analysis: Don't convert value to DataTable:" + "\n" + e.toString()); // throw  
	        }
	    }
	 
	 
	 private static void AnalysisNoteValue(DataTable data, String[] lineData) {
		 data.m_noteValue = new HashMap<String, String>();

        for (int i = 0; i < lineData.length && i < data.TableKeys.size(); i++)
        {
            if (!lineData[i].equals(""))
            {
            	data.m_noteValue.put(data.TableKeys.get(i), lineData[i]);
            }
        }
	}


	private static void AnalysisFieldType(DataTable data, String[] lineData) {
		 
		 data.m_tableTypes = new HashMap<String,FieldType>();
		 data.m_tableEnumTypes = new HashMap<String, String>();

	        for (int i = 1; i < lineData.length; i++)
	        {
	            if (!lineData[i].equals(""))
	            {
	                String[] content = lineData[i].split("\\|");
	                String type = content[0];
	                String[] typeTmp = type.split("&");
	                
	                try
	                {
	                	data.m_tableTypes.put(data.TableKeys.get(i), (FieldType)Enum.valueOf( FieldType.class, typeTmp[0]) );

	                    if (content.length > 1)
	                    {
	                    	data.m_tableEnumTypes.put(data.TableKeys.get(i), content[1]);
	                    }
	                }
	                catch(Exception e)
	                { 
	                	e.printStackTrace();
	                }
	            }
	        }
	}


	public static String[] ConvertStringArray(String lineContent)
	    {
	        List<String> result = new ArrayList<String>();
	        int startIndex = 0;
	        boolean state = true; //����״̬������״̬
//LogService.Log("", "���: lineContent :"+lineContent);
	        for (int i = 0; i < lineContent.length(); i++)
	        {
	            if (state)
	            {
	                if (lineContent.charAt(i) == '\t')
	                {
	                	String tmp = lineContent.substring(startIndex, i);
	                    result.add(tmp);
	                    startIndex = i + 1;
	                }
	                else if (lineContent.charAt(i) == '\"')
	                {
	                    //תΪ����״̬
	                    state = false;
	                }
	                else if (i == lineContent.length() - 1)
	                {
	                	String tmp = lineContent.substring(startIndex, i + 1);
	                	
	                    result.add(tmp);
	                    startIndex = i + 1;
	                }
	            }
	            else
	            {
	                if (lineContent.charAt(i) == '\"')
	                {
	                    //תΪ����״̬
	                    state = true;
	                }
	            }
	        }
	        
	        String[] reString = new String[result.size()];
	        for (int i = 0; i < result.size(); i++) 
	        {
	        	reString[i] = result.get(i);
			}
	        return reString;
	    }
	 
	    public static void AnalysisDefaultValue(DataTable l_data,String[] l_lineData)
	    {
	        l_data.defaultValue = new HashMap<String, String>();

	        for (int i = 0; i < l_lineData.length; i++)
	        {
	            if (!l_lineData[i].equals(""))
	            {
	                l_data.defaultValue.put(l_data.TableKeys.get(i), l_lineData[i]);
	            }
	        }
	    }
}

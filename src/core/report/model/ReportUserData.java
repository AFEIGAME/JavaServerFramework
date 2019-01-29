package core.report.model;

import java.util.Date;
import java.util.Map;

import core.localizedLanguage.SystemLanguage;

public class ReportUserData extends ReportDataBase
{
	public String uuid;      //�û�ID 
	public String version;   //��Ϸ�汾
	public String channel;   //����
	
	public String brand;     //Ʒ��
	public String device;    //�豸
	public int resolution_w; //�ֱ���
	public int resolution_h; 
	
	public String processorType; //�������ܹ�
	public String processorCount;//������������
	
	public int memorySize;        //�ڴ��С
	public int graphicMemorySize; //�Դ��Сpublic 
	public String graphicDeviceType; //OpenGL ����
	public int shaderLevel; //��ɫ���ȼ�
	
	public String os;        //����ϵͳ
	public String ov;        //����ϵͳ�汾
	public String net;       //��������
	public String ping;      //�����ӳ�
	public SystemLanguage language; //�û�����
	
	public Map<String, String> properties; //�Զ�������
	public Date lastLoginTime;   //����½ʱ��
	public String lastLoginIp;   //����¼IP
}

package core.report.model;

import java.util.Date;
import java.util.Map;

public class ReportEventData  extends ReportDataBase
{
	public String uuid;      //�û�ID 
	public String version;   //��Ϸ�汾
	public String name;   //����
	
	public String os;        //����ϵͳ
	public String ov;        //����ϵͳ�汾
	public String net;       //��������
	public String ping;      //�����ӳ�
	
	public Map<String, String> properties; //�Զ�������
	public Date time;         //�¼�ʱ��
}

package core.player;

import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.mina.core.session.IoSession;

import core.login.RuntimePlatform;

public class PlayerBase 
{
	public IoSession m_session = null;
	public String m_ID = "";
	public String nickName = "";

	public Date RegistrationDate; // ע������
	public Date LastLoginDate; // ����½����

	public RuntimePlatform platform;
	public String deviceUniqueIdentifier;
	
	public String vsrsion; //�汾��
	public String channel; //����
	
	public void setPlatform(RuntimePlatform platform) {
		this.platform = platform;
	}

	public RuntimePlatform getPlatform() {
		return platform;
	}

	public void setDeviceUniqueIdentifier(String deviceUniqueIdentifier) {
		this.deviceUniqueIdentifier = deviceUniqueIdentifier;
	}

	public String getDeviceUniqueIdentifier() {
		return deviceUniqueIdentifier;
	}

	public String GetIP() {
		if (m_session != null) {
			return ((InetSocketAddress) m_session.getRemoteAddress()).getAddress().getHostAddress();
		} else {
			return "null";
		}
	}
}

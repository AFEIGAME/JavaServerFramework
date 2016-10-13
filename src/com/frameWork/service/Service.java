package com.frameWork.service;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.frameWork.service.config.configs.ServiceConfig;

///import com.handler.ServerHandler;

public class Service 
{
	public static NioSocketAcceptor socketAcceptor;
	ServiceHandler handler;
	
	String m_modelName = "NioService";

	public static Service instance;

	static public Service getInstance()
	{
		if (instance == null)
		{
			instance = new Service();
			instance.startService();
		}
		return instance;
	}
	
	public Service() 
	{
		// ����һ����������server�˵�socket��
		socketAcceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		socketAcceptor.setReuseAddress(true);
		socketAcceptor.getSessionConfig().setKeepAlive(true);
		

		// ���߼�����������Ҫ���߼�����handler����
		handler = new ServiceHandler();
		socketAcceptor.setHandler(handler);
		handler.socketAcceptor = socketAcceptor;
		
//		socketAcceptor.setBacklog(ConfigService.MAX_CONNECTOR);

	//	socketAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
//		socketAcceptor.getFilterChain().addLast("json",
//				new ProtocolCodecFilter(new JSONCodecFactory()));
		TextLineCodecFactory lineCode = new TextLineCodecFactory(Charset.forName("utf8"),"&","&");
		lineCode.setDecoderMaxLineLength(1024*512 * 1);
		lineCode.setEncoderMaxLineLength(1024*512 * 1);
		
		socketAcceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(lineCode));
		
		socketAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);

		socketAcceptor.getSessionConfig().setReadBufferSize(1024*10); // ���ͻ�����1kb//
																	    // 1M=1024*1024
		socketAcceptor.getSessionConfig().setReceiveBufferSize(1024*10);// ���ջ�����1kb
		
		ShowInfo();
	}
	
	public void startService()
	{
		socketAcceptor.getManagedSessions();
		try {
			// �˿ڰ󶨡�
			socketAcceptor.bind(new InetSocketAddress(ServiceConfig.s_Port));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void ShowInfo()
	{
		String Info = "����Socket����  �˿ںţ�" + ServiceConfig.s_Port + "\n"
					+ "�������汾 �� 2016/10/13 \n" 
					+ "����������� " + Runtime.getRuntime().availableProcessors() + "\n";
		try 
		{
			String hostName = InetAddress.getLocalHost().getHostName();
			for (InetAddress it : InetAddress.getAllByName( hostName)) 
			{
				Info += ipv4OrIpv6(it) + "\n";
			}
		}
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		
		LogService.Log(m_modelName, Info);
	}
	
	String ipv4OrIpv6(InetAddress ita) 
	{
			String[] itn = ita.toString().split("/");
			String str = itn[1];
			if (str.length() > 16) 
			{
					return "IPv6\t" + ita.toString();
			}
			return "IPv4\t" + ita.toString();
	}
}

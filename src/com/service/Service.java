package com.service;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.service.config.ConfigService;


///import com.handler.ServerHandler;

public class Service 
{
	public static NioSocketAcceptor socketAcceptor;
	private Logger log = null;
	ServiceHandler handler;

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
		log = LogManager.getLogger(Service.class.getName());

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
		
		log.error("����������~~~\n�������汾 �� 2016/9/25");
		log.error( "����������� " + Runtime.getRuntime().availableProcessors());
	}
	
	public void startService()
	{
		socketAcceptor.getManagedSessions();
		try {
			// �˿ڰ󶨡�
//			socketAcceptor.bind(new InetSocketAddress(ConfigService.Port));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

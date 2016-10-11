package com.frameWork.service;

import org.apache.mina.core.session.IoSession;
import com.frameWork.service.encryption.EncryptionService;
import net.sf.json.JSONObject;

public class MessageService 
{
	public static MessageService instance;

	public static MessageService getInstance() 
	{
		if(instance == null)
		{
			instance = new MessageService();
		}
		return instance;
	}
	
	//�����ܷ���
	public void sendMessageNoSafe(IoSession session,JSONObject jsonMes) 
	{
		send(session,jsonMes.toString());
	}

	//���ܷ���
	void send(IoSession session,String message)
	{

		
		if(session != null)
		{
			session.write(message);
		}
	}
	
	//���ܱ���
	void encryptionSend(IoSession session,String message)
	{
		try 
		{

			
			JSONObject encryption = EncryptionService.Encryption(message);
			

			
			if(session != null)
			{
				session.write(encryption.toString());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
}
package com.service;

import java.util.Base64;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.tapedeck.PlayCommand;

import com.service.encryption.EncryptionService;

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
		if(ConfigService.isDebug)
		{
			System.out.println("��������: " + message);
		}
		
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
			if(ConfigService.isDebug)
			{
				System.out.println("��������: " + message);
			}
			
			JSONObject encryption = EncryptionService.Encryption(message);
			
			if(ConfigService.isDebug)
			{
				System.out.println("�������� ����: " + encryption.toString());
			}
			
			if(session != null)
			{
				session.write(encryption.toString());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
}
package com.service.encryption;

import java.util.Base64;

import com.service.ConfigService;

import net.sf.json.JSONObject;

public class EncryptionService 
{
	//����
	public static JSONObject Encryption(String messageString) 
	{
		JSONObject encryption = new JSONObject();
		try {
			
			//���������DESkey
			String desKey;
			
			desKey = DESCoder.initKey();
	
			//�����Ӽ��ܱ���
			byte[] data = messageString.getBytes();  
			String securityKey = Base64.getEncoder().encodeToString(DESCoder.encrypt(data, desKey));
			encryption.put("securityData", securityKey);
			
			
			//��������
			byte[] key = desKey.getBytes();  
			byte[] encodedData = RSACoder.encryptByPrivateKey(key, ConfigService.RSA_private_key);
			String encryptionMsg = Base64.getEncoder().encodeToString(encodedData);
			
			encryption.put("securityKey", encryptionMsg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryption;
	}
	
	//����
	public static JSONObject decryption(JSONObject EncryptionJson) 
	{
		JSONObject result  = null;
		
		try {
			String securityKey = EncryptionJson.getString("securityKey");
			String securityData = EncryptionJson.getString("securityData");
			
			String desKey ="";
			String Msg ="";
			
			//����DESkey
			byte[] data =  securityKey.getBytes();
			
			desKey = new String(RSACoder.decryptByPrivateKey(data, ConfigService.RSA_private_key));
			
			//��DESkey���ܱ���
			byte[] key = securityData.getBytes();
			Msg = new String(DESCoder.decrypt(key, desKey));
			
			//����json����
			result =JSONObject.fromObject(Msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}

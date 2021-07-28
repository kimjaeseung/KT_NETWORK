package com.kt.controller;

import static java.util.Base64.getEncoder;

import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class createOpenAPI_sample {
	static String $URL = "https://api.ucloudbiz.olleh.com/server/v2/client/api?";
	static String $APIKEY = "bjatL1TklBIHV9DYCb_W42jTv4sEW8qAOLbjy1GW4ulIrWJ0zsudzkH478pMpn2OSrWIdXrbDnrwV-JdRBtVUQ";
	static String $SECRETKEY = "bIP_4fV2snLb1zlUIori5-xtuVZ5tbEDxh1YFFPqCZb1_YrbK-OBdzy3Ouq8o1RvdPPAFDBkVOV7dNALK7HRpA";
	static String $COMMAND = "listVirtualMachines&response=json";
	
	public static String createSignature(String data, String key) throws java.security.SignatureException
	{
		String result;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			
			Base64.Encoder encoder = getEncoder();
	        String signature = encoder.encodeToString(rawHmac);			
			result = URLEncoder.encode(signature, "UTF-8");
		} 
		catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}	
	
	public static void main(String[] args) {
		String request = "command=" + $COMMAND + "&apikey=" + $APIKEY;
		
		request = request.replace(" ", "%20");
		
		String[] s = request.split("&");
		
		Arrays.sort(s);
		
		request = s[0];
		for(int i=1;i<s.length;i++){
			request = request + "&" + s[i];
		}
		
		String request_e = "";
		request_e=request.toLowerCase();
		
		String signature = "";
		try {
			signature = createSignature(request_e, $SECRETKEY);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		
		String result = $URL + request + "&signature=" + signature;
		
		System.out.println(result);
	
	}
}

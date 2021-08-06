package com.kt.controller;

import static java.util.Base64.getEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.service.Header;

@Api("Controller V1")
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class SensorController {
	static String $URL = "https://api.ucloudbiz.olleh.com/d1/server";
	static String $APIKEY = "bjatL1TklBIHV9DYCb_W42jTv4sEW8qAOLbjy1GW4ulIrWJ0zsudzkH478pMpn2OSrWIdXrbDnrwV-JdRBtVUQ";
	static String $SECRETKEY = "bIP_4fV2snLb1zlUIori5-xtuVZ5tbEDxh1YFFPqCZb1_YrbK-OBdzy3Ouq8o1RvdPPAFDBkVOV7dNALK7HRpA";
	static String $COMMAND = "listVirtualMachines&response=json";
	static String servername, pubp, ddip;

	@ApiOperation(value = "KT Cloud Port Forwarding 조회", notes = "포트포워딩 조회", response = Map.class)
	@GetMapping(value = "/ktportforwarding")
	public JSONObject ktportforwarding(@RequestParam String name, @RequestParam String key_name,
			@RequestParam String flavorRef, @RequestParam String nuuid, @RequestParam String vuuid)
			throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		return null;
	}

	@ApiOperation(value = "KT Cloud network", notes = "네트워크 조회", response = Map.class)
	@GetMapping(value = "/ktnetwork")
	public ResponseEntity<String> ktnetwork() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Network", HttpMethod.GET,
				entity, String.class);
		System.out.println(response);
		return response;
	}

	@ApiOperation(value = "KT Cloud ktportforwarding id", notes = "네트워크 조회", response = Map.class)
	@GetMapping(value = "/ktportforwardingid")
	public String ktportforwardingid() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Portforwarding",
				HttpMethod.GET, entity, String.class);

		JsonParser jp = new JsonParser();
		JsonObject jo = (JsonObject) jp.parse(response.getBody().toString());
		JsonParser jp2 = new JsonParser();
		JsonObject temp = (JsonObject) jo.get("nc_listportforwardingrulesresponse");
		String temp2 = temp.get("portforwardingrule").toString();
		JsonArray jo2 = (JsonArray) jp2.parse(temp2);
		String portforwardingid = "";
		for (int i = 0; i < jo2.size(); i++) {			
			JsonObject job2 = (JsonObject) jo2.get(i);
			JsonElement id = job2.get("publicendport");
			if (strm(id.toString()).equals(pubp)) {
				JsonElement id2 = job2.get("id");
				portforwardingid = strm(id2.toString());
			}
		}
		System.out.println(portforwardingid);
		return portforwardingid;
	}

	@ApiOperation(value = "KT Cloud D1 server list", notes = "D1 서버 리스트 조회", response = Map.class)
	@GetMapping(value = "/ktd1serverlist")
	public JSONObject ktd1serverlist() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/server/servers",
				HttpMethod.GET, entity, String.class);
		
		JSONParser jp = new JSONParser();
		JSONObject jo = (JSONObject) jp.parse(response.getBody().toString());

		return jo;
	}

	public String strm(String str) {
		str = str.substring(1, str.length() - 1);
		return str;
	}

	@ApiOperation(value = "KT Cloud D1 server ip", notes = "D1 서버 리스트 조회", response = Map.class)
	@GetMapping(value = "/ktd1serverid")
	public String ktd1serverid() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		JSONObject response = ktd1serverlist();
		String serverid = "";
		JsonParser jp = new JsonParser();
		JsonObject jo = (JsonObject) jp.parse(response.toString());
		
		String temp = jo.get("servers").toString(); 
		JsonArray jo2 = (JsonArray) jp.parse(temp);

		for (int i = 0; i < jo2.size(); i++) {
			JsonObject job = (JsonObject) jo2.get(i);
			String str = strm(job.get("name").toString());
			if (str.equals(servername)) {
				serverid = job.get("id").toString();
			}
		}
		serverid = strm(serverid); 
		return serverid;
	}

	@ApiOperation(value = "KT Cloud D1 server ip", notes = "D1 서버 리스트 조회", response = Map.class)
	@GetMapping(value = "/ktd1serverip")
	public String ktd1serverip() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		String serverid = ktd1serverid();
		try {
			Thread.sleep(20000);
		} catch (Exception e) {
			System.out.println(e);
		}
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/server/servers/" + serverid,
				HttpMethod.GET, entity, String.class);

		JsonParser jp2 = new JsonParser();
		JsonObject jo3 = (JsonObject) jp2.parse(response.getBody().toString());

		JsonParser jp6 = new JsonParser();

		JsonObject temp2 = (JsonObject) jo3.get("server");


		JsonObject temp3 = (JsonObject) temp2.get("addresses");

				
		String temp4 = temp3.get("DMZ").toString();
		
		
		JsonArray jo4 = (JsonArray) jp6.parse(temp4);
		JsonObject job2 = (JsonObject) jo4.get(0);
		JsonElement id = job2.get("addr");
		String serverip = id.toString();
		
		return serverip;
	}
	
	@ApiOperation(value = "KT Cloud ktd1tier", notes = "D1 서버 리스트 조회", response = Map.class)
	@GetMapping(value = "/ktd1tier")
	public List<Map<String, String>> ktd1tier() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		List<Map<Integer, String>> list = ktd1serveridlist();
		Map<Integer, String> serverid = list.get(0);
		Map<Integer, String> id = list.get(1);
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		for (int i = 0; i < serverid.size(); i++) {
			ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/server/servers/" + serverid.get(i),
					HttpMethod.GET, entity, String.class);
			JsonParser jp2 = new JsonParser();
			JsonObject jo3 = (JsonObject) jp2.parse(response.getBody().toString());
			JsonObject temp2 = (JsonObject) jo3.get("server");

			JsonObject temp3 = (JsonObject) temp2.get("addresses");
			
			if(temp3.toString().substring(2,3).equals("D")) {
				String temp4 = temp3.get("DMZ").toString();
				
				JsonParser jp6 = new JsonParser();
				JsonArray jo4 = (JsonArray) jp6.parse(temp4);
				JsonObject job2 = (JsonObject) jo4.get(0);
				JsonElement id2 = job2.get("addr");
				String serverip = strm(id2.toString());
				
				Map<String, String> tierlist = new HashMap<String, String>();
				tierlist.put("id",id.get(i));
				tierlist.put("tier", "DMZ");
				tierlist.put("ip", serverip);
				result.add(tierlist);
				
			}
			else {
				String temp4 = temp3.get("Private").toString();

				JsonParser jp6 = new JsonParser();
				JsonArray jo4 = (JsonArray) jp6.parse(temp4);
				JsonObject job2 = (JsonObject) jo4.get(0);
				JsonElement id2 = job2.get("addr");
				String serverip = strm(id2.toString());
				Map<String, String> tierlist = new HashMap<String, String>();
				tierlist.put("id",id.get(i));
				tierlist.put(id.get(i), "Private");
				tierlist.put("ip", serverip);
				result.add(tierlist);
			}
		}
		System.out.println(result);
		return result;
	}
	
	@GetMapping(value = "/ktd1serveridlist")
	public List<Map<Integer,String>> ktd1serveridlist() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		JSONObject response = ktd1serverlist();
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, String> map2 = new HashMap<Integer, String>();
		List<Map<Integer,String>> result = new ArrayList<Map<Integer,String>>();
		JsonParser jp = new JsonParser();
		JsonObject jo = (JsonObject) jp.parse(response.toString());
		
		String temp = jo.get("servers").toString();
		JsonArray jo2 = (JsonArray) jp.parse(temp);
		
		for (int i = 0; i < jo2.size(); i++) {
			JsonObject job = (JsonObject) jo2.get(i);
			String str = strm(job.get("name").toString());
			String serverid = null;
			serverid = job.get("id").toString();
			serverid = strm(serverid);
			map.put(i, serverid);
			map2.put(i, str);
		}
		result.add(map);
		result.add(map2);
		
		return result;
	}
	
	
	@ApiOperation(value = "KT Cloud ip", notes = "네트워크 조회", response = Map.class)
	@GetMapping(value = "/ktip")
	public ResponseEntity<String> ktip() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/IpAddress", HttpMethod.GET,
				entity, String.class);
		return response;
	}
	
	
	@ApiOperation(value = "KT Cloud ktkeypair", notes = "D1 서버 리스트 조회", response = Map.class)
	@GetMapping(value = "/ktkeypair")
	public JSONObject ktkeypair() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/server/os-keypairs",
				HttpMethod.GET, entity, String.class);
		
		JSONParser jp = new JSONParser();
		JSONObject jo = (JSONObject) jp.parse(response.getBody().toString());

		return jo;
	}
	
	@ApiOperation(value = "KT Cloud ktd1firewall", notes = "D1 서버 리스트 조회", response = Map.class)
	@GetMapping(value = "/ktd1firewall")
	public JSONObject ktd1firewall() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Firewall",
				HttpMethod.GET, entity, String.class);
		
		JSONParser jp = new JSONParser();
		JSONObject jo = (JSONObject) jp.parse(response.getBody().toString());
		
		return jo;
	}
	
	
	
	@ApiOperation(value = "KT Cloud authtoken", notes = "인증토큰", response = Map.class)
	@GetMapping(value = "/ktauthtoken")
	public String ktauthtoken() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject3 = new JSONObject();
		JSONObject jsonObject4 = new JSONObject();
		JSONObject jsonObject5 = new JSONObject();
		JSONObject jsonObject6 = new JSONObject();
		JSONObject jsonObject8 = new JSONObject();
		JSONObject jsonObject9 = new JSONObject();
		JSONObject jsonObject10 = new JSONObject();
		String[] test2 = new String[1];
		test2[0] = "password";
		jsonObject6.put("id", "default");
		jsonObject5.put("password", "gkdlQM2020!@");
		jsonObject5.put("name", "cse2.hybrid@gmail.com");
		jsonObject5.put("domain", jsonObject6);
		jsonObject4.put("user", jsonObject5);
		jsonObject3.put("methods", test2);
		jsonObject3.put("password", jsonObject4);
		jsonObject8.put("id", "default");
		jsonObject9.put("domain", jsonObject8);
		jsonObject9.put("name", "cse2.hybrid@gmail.com");
		jsonObject10.put("project", jsonObject9);
		jsonObject1.put("identity", jsonObject3);
		jsonObject1.put("scope", jsonObject10);
		jsonObject.put("auth", jsonObject1);

		RestTemplate rt = new RestTemplate();
		HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(jsonObject);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/identity/auth/tokens",
				HttpMethod.POST, entity, String.class);
		HttpHeaders test = response.getHeaders();
		List<String> temp = test.get("x-subject-token");
		String auth = temp.get(0);
		return auth;
	}

	@ApiOperation(value = "KT Cloud D1 create server", notes = "서버 생성", response = Map.class)
	@PostMapping(value = "/ktd1createserver")
	public ResponseEntity<String> ktd1createserver(@RequestParam String name, @RequestParam String key_name,
			@RequestParam String flavorRef, @RequestParam String nuuid, @RequestParam String vuuid)
			throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String authtoke = ktauthtoken();
		JsonObject jsonObject5 = new JsonObject();
		JsonObject jsonObject7 = new JsonObject();
		JsonObject jsonObject9 = new JsonObject();
		JsonObject jsonObject10 = new JsonObject();

		jsonObject10.addProperty("destination_type", "volume");
		jsonObject10.addProperty("boot_index", "0");
		jsonObject10.addProperty("source_type", "image");
		jsonObject10.addProperty("volume_size", 50);
		jsonObject10.addProperty("uuid", vuuid);

		JsonArray jArray = new JsonArray(); 
		jArray.add(jsonObject10);

		jsonObject9.add("block_device_mapping_v2", jArray);

		JsonArray jArray2 = new JsonArray();
		jsonObject7.addProperty("uuid", nuuid);
		jArray2.add(jsonObject7);
		jsonObject9.add("networks", jArray2);

		jsonObject9.addProperty("availability_zone", "DX-M1");
		jsonObject9.addProperty("flavorRef", flavorRef);
		jsonObject9.addProperty("key_name", key_name);
		jsonObject9.addProperty("name", name);
		servername = name;
		jsonObject5.add("server", jsonObject9);
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", authtoke);
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject5.toString(), headers);

		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/server/servers",
				HttpMethod.POST, entity, String.class);
		return response;
	}
	
	@ApiOperation(value = "KT Cloud d1 방화벽 설정", notes = "서버 생성", response = Map.class)
	@PostMapping(value = "/ktd1createfirewall")
	public ResponseEntity<String> ktd1createfirewall() throws ParseException, IOException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		String authtoke = ktauthtoken();
		String id = ktportforwardingid();
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("action", "allow");
		jsonObject.addProperty("srcnetworkid", "6a108730-8285-47e8-b768-9b8f7825dbca");
		jsonObject.addProperty("dstnetworkid", "09817cff-74dd-4902-9cf6-3f3cfb6fa2c9");
		jsonObject.addProperty("protocol", "ALL");
		jsonObject.addProperty("virtualipid", id);
		jsonObject.addProperty("vpcid", "77288336-c593-4aca-9444-b62d16b01a0a");
		jsonObject.addProperty("zoneid", "DX-M1");
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", authtoke);
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Firewall", HttpMethod.POST,
				entity, String.class);
		return response;
	}
	
	@ApiOperation(value = "KT Cloud D1 create portforwarding", notes = "서버 생성", response = Map.class)
	@PostMapping(value = "/ktd1createportforwarding")
	public String ktd1createportforwarding(@RequestParam String privateport,
			@RequestParam String publicport, @RequestParam String protocol, @RequestParam String ip) throws ParseException, IOException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		String authtoke = ktauthtoken();
		JsonObject jsonObject1 = new JsonObject();
		String serverip = strm(ktd1serverip());
		pubp = publicport;
		ddip = ip;
		System.out.println("포트포워딩 생성중");
		System.out.println("serverip: " + serverip);
		System.out.println("privateport: " + privateport);
		System.out.println("publicport: " + publicport);
		System.out.println("protocol: " + protocol);
		jsonObject1.addProperty("vmguestip", serverip);
		jsonObject1.addProperty("entpublicipid", "02f42fe4-f961-4d5e-badb-555230978971");
		jsonObject1.addProperty("privateport", privateport);
		jsonObject1.addProperty("publicport", publicport);
		jsonObject1.addProperty("protocol", protocol);
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", authtoke);
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject1.toString(), headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Portforwarding",
				HttpMethod.POST, entity, String.class);

		ResponseEntity<String> t = ktd1createfirewall();
		
		String last = "";
		try {
			System.out.println("방화벽 정책 생성 + ping test 진행중.. 약 1분정도 소요됩니다.");
			for (int i = 0; i < 30; i++) {
				Thread.sleep(1000);
				System.out.println(i*5+"초 경과..");
				last = cmdstr(ip, publicport);
				if(last.contains("Port is open")) {
					System.out.println("ping 연결이 확인되었습니다.");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(last);
		return last;
	}

	@GetMapping(value = "/sid")
	public String sid() throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		String auth = ktauthtoken();
		String sname = ktd1serverid();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", auth);
		headers.add("content-type", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Header> entity = new HttpEntity<Header>(headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/server/servers/" + sname,
				HttpMethod.GET, entity, String.class);

		JsonParser jp2 = new JsonParser();
		JsonObject jo3 = (JsonObject) jp2.parse(response.getBody().toString());

		JsonParser jp6 = new JsonParser();

		JsonObject temp2 = (JsonObject) jo3.get("server");


		JsonObject temp3 = (JsonObject) temp2.get("addresses");

				
		String temp4 = temp3.get("DMZ").toString();
		
		
		JsonArray jo4 = (JsonArray) jp6.parse(temp4);
		JsonObject job2 = (JsonObject) jo4.get(0);
		JsonElement id = job2.get("addr");
		String serverip = id.toString();
		
		return serverip;
	}
	
	@PostMapping(value = "/portfire")
	public String portfire(@RequestParam String sname) throws ParseException, IOException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		String authtoke = ktauthtoken();
		servername = sname;
		JsonObject jsonObject1 = new JsonObject();	
		String serverip = strm(sid());
		int min = 10000;
		int max = 10999;
		int random = (int) ((Math.random() * (max - min)) + min);
		String publicport = Integer.toString(random);
		pubp = publicport;
		ddip = serverip;
		System.out.println("포트포워딩 생성중");
		System.out.println("serverip: " + serverip);
		System.out.println("privateport: " + "22");
		System.out.println("publicport: " + publicport);
		System.out.println("protocol: " + "TCP");
		jsonObject1.addProperty("vmguestip", serverip);
		jsonObject1.addProperty("entpublicipid", "02f42fe4-f961-4d5e-badb-555230978971");
		jsonObject1.addProperty("privateport", "22");
		jsonObject1.addProperty("publicport", publicport);
		jsonObject1.addProperty("protocol", "TCP");
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", authtoke);
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject1.toString(), headers);
		ResponseEntity<String> response = rt.exchange("https://api.ucloudbiz.olleh.com/d1/nc/Portforwarding",
				HttpMethod.POST, entity, String.class);
		ResponseEntity<String> t = ktd1createfirewall();
		String last = "";
		String tsip = "211.34.247.18"; 
		try {
			System.out.println("방화벽 정책 생성 + ping test 진행중.. 약 1분정도 소요됩니다.");
			for (int i = 0; i < 30; i++) {
				Thread.sleep(1000);
				System.out.println(i*5+"초 경과..");
				last = cmdstr(tsip, publicport);
				System.out.println(last);
				if(last.contains("Port is open")) {
					System.out.println("ping 연결 확인됨 !!");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(last);
		return last;
	}
	
	
	
	@PostMapping(value = "/pingck")
	public String pingck(@RequestParam String publicport, @RequestParam String ip) throws ParseException, IOException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		String last = "";
		try {
			System.out.println("ping test 진행중.. 약 30초 정도 소요됩니다.");
			for (int i = 0; i < 4; i++) {
				Thread.sleep(1000);
				System.out.println(i*5+"초 경과..");
				last = cmdstr(ip, publicport);
				if(last.contains("Port is open")) {
					System.out.println("ping 연결 확인됨 !!");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(last);
		return last;
	}
	
	@ApiOperation(value = "KT Cloud Create Server", notes = "서버 생성", response = Map.class)
	@PostMapping(value = "/ktcreateserver")
	public String ktcreateserver(
			@ApiParam(value = "createserver", required = true) @RequestBody List<Map<String, String>> list)
			throws ParseException, IOException, org.json.simple.parser.ParseException,
			org.apache.tomcat.util.json.ParseException {
		JSONObject real = resulturl("listPortForwardingRules&response=json");

		String URI = "http://localhost:8080/api/test";

		return URI;
	}
	
	/*
	public boolean ktpingcheck() throws IOException {
		InetAddress pingcheck = InetAddress.getByName(serverip);
		boolean isAlive = pingcheck.isReachable(1000);
		return isAlive;
	}
	*/

	@ApiOperation(value = "KT Cloud Server 조회", notes = "조회", response = Map.class)
	@GetMapping(value = "/ktserver")
	public JSONObject ktserver() throws NoSuchAlgorithmException, InvalidKeyException, IOException, ParseException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		JSONObject real = resulturl("listVirtualMachines&response=json");
		return real;
	}

	@ApiOperation(value = "KT Cloud Firewall 조회", notes = "조회", response = Map.class)
	@GetMapping(value = "/ktfirewall")
	public JSONObject ktfirewall() throws NoSuchAlgorithmException, InvalidKeyException, IOException, ParseException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		JSONObject real = resulturl("listFirewallRules&response=json");
		System.out.println(real.keySet());
		
		return real;
	}

	@ApiOperation(value = "test data list", notes = "성공/실패 여부에 따라 http 상태코드 출력", response = Map.class)
	@GetMapping(value = "/test")
	public ResponseEntity<List<Map<String, String>>> test() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		HttpStatus status = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", "kimjaeseung zz zz zz");
		list.add(map);
		status = HttpStatus.ACCEPTED;
		return new ResponseEntity<List<Map<String, String>>>(list, status);
	}

	public static String createSignature(String data, String key) throws java.security.SignatureException {
		String result;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());

			Base64.Encoder encoder = getEncoder();
			String signature = encoder.encodeToString(rawHmac);
			result = URLEncoder.encode(signature, "UTF-8");
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	public static JSONObject readJsonFromUrl(String req_message) throws ParseException, IOException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		URL url = new URL(req_message);
		JSONParser parser = new JSONParser();
		JSONObject obj = null;

		// HTTPcache사용을 위해 HttpURLConnection 생성
		HttpURLConnection myURLConnection = (HttpURLConnection) url.openConnection();
		myURLConnection.setUseCaches(false);

		BufferedReader bf;
		String line = "";
		String result = "";
		int responseCode = myURLConnection.getResponseCode();
		if (responseCode == 200) {
			InputStream stream = myURLConnection.getErrorStream();
			if (stream == null) {
				bf = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
				while ((line = bf.readLine()) != null) {
					result = result.concat(line);
				}
				obj = (JSONObject) parser.parse(result);
			}
		} else {
			readJsonFromUrl(req_message);
		}
		System.out.println("받아온값: " + obj);
		return obj;
	}

	public JSONObject resulturl(String command) throws ParseException, IOException,
			org.json.simple.parser.ParseException, org.apache.tomcat.util.json.ParseException {
		String request = "command=" + command + "&apikey=" + $APIKEY;
		request = request.replace(" ", "%20");
		String[] s = request.split("&");
		Arrays.sort(s);
		request = s[0];
		for (int i = 1; i < s.length; i++) {
			request = request + "&" + s[i];
		}
		String request_e = "";
		request_e = request.toLowerCase();
		String signature = "";
		try {
			signature = createSignature(request_e, $SECRETKEY);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		String result = $URL + request + "&signature=" + signature;
		System.out.println(result);
		JSONObject real = readJsonFromUrl(result);
		return real;
	}

	
	private StringBuffer buffer;
	private Process process;
	private BufferedReader bufferedReader;
	private StringBuffer readBuffer;
	
	public String inputCommand(String cmd) {
		
		buffer = new StringBuffer();
		
		buffer.append("cmd.exe ");	
		buffer.append("/c ");
		buffer.append(cmd);

		return buffer.toString();
	}
	
	public String execCommand(String cmd) {
		try {
			process = Runtime.getRuntime().exec(cmd);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			readBuffer = new StringBuffer();
			
			while((line = bufferedReader.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			
			return readBuffer.toString();
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	@GetMapping(value = "/cmdstr")
	public String cmdstr(String ip, String pub) {
		String tmp = "tcping "+ip+" "+pub;
		System.out.println(tmp);
		String comd = inputCommand(tmp);
		String result = execCommand(comd);
		return result;
	}
	
}

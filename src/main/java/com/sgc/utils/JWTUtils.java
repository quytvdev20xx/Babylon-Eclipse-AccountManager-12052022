package com.sgc.utils;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.DatatypeConverter;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JWTUtils {
	private static String key = "linhvuongmbsecrest";

//	@SuppressWarnings("serial")
	public static String generateJWT(String userName, String userId) {
		String result = "";
		try {
			Gson gson = new Gson();    
		   // String json = gson.toJson(object);
			result = Jwts.builder().setClaims(new HashMap<String, Object>() {
				{
					put("userName", userName);
					put("userId", userId);
				}
			}).signWith(SignatureAlgorithm.HS256, key).compact();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("==========KET QUA MA HOA: "+result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> validateJWT(String jwt) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)).parseClaimsJws(jwt).getBody();
					
			 HashMap<String, String> map = new HashMap<String, String>();
			
			 String userName =  String.valueOf(claims.get("userName"));
			 map.put("userName", userName);
			 String userId =  String.valueOf(claims.get("userId"));
			 map.put("userId", userId);
			 return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public static JSONObject decodeTokenApple(String token) {
//		String[] parts = token.split("\\.");
//		String tokenPayload = parts[1];
//		String payload = new String(Base64.getUrlDecoder().decode(tokenPayload));
//		JSONObject json = new JSONObject();
//		try {
//			json = new JSONObject(payload);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		return json;
//	}
}
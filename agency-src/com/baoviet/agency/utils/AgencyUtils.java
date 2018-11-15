package com.baoviet.agency.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class AgencyUtils {

	private final Logger log = LoggerFactory.getLogger(AgencyUtils.class);

	public static String generateRandomPassword() {
		return "12345678";
	}

	public static String signatureHmacSHA256(String secret, String message) {
		String hash = "";
		try {
			// Get an algorithm instance.
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

			// Create secret key.
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");

			// Assign secret key algorithm.
			sha256_HMAC.init(secret_key);

			hash = Base64.encode(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return hash.trim();
	}
	
	public static Map<String, String> convertMultiToRegularMap(MultiValueMap<String, String> m) {
		Map<String, String> map = new HashMap<>();
		
		Set<String> keys = m.keySet();

		for (String key : keys) {
		    List<String> list = (List<String>) m.get(key);

		    map.put(key, String.join(",", list));
		} 
		
		return map;
	}
	
	public static String getRandomOTP() {
        int rand = rand(100000, 999999);
		return String.valueOf(rand);
    }
	
	private static int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
	
}

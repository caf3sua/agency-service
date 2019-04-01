package com.baoviet.agency.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class AgencyUtils {

	private final Logger log = LoggerFactory.getLogger(AgencyUtils.class);

	public static final String KHONG = "không";
	public static final String MOT = "một";
	public static final String HAI = "hai";
	public static final String BA = "ba";
	public static final String BON = "bốn";
	public static final String NAM = "năm";
	public static final String SAU = "sáu";
	public static final String BAY = "bảy";
	public static final String TAM = "tám";
	public static final String CHIN = "chín";
	public static final String LAM = "lăm";
	public static final String LE = "lẻ";
	public static final String MUOI = "mươi";
	public static final String MUOIF = "mười";
	public static final String MOTS = "mốt";
	public static final String TRAM = "trăm";
	public static final String NGHIN = "nghìn";
	public static final String TRIEU = "triệu";
	public static final String TY = "tỷ";

	public static final String[] mangso = { KHONG, MOT, HAI, BA, BON, NAM, SAU, BAY, TAM, CHIN };

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

	public static String toKhongDau(String str) {
		try {
			String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	// Đọc số
	public static String docso(double so) {
		if (so == 0)
			return mangso[0];
		String chuoi = "", hauto = "";
		do {
			double ty = so % 1000000000;
			so = Math.floor(so / 1000000000);
			if (so > 0) {
				chuoi = dochangtrieu(ty, true) + hauto + chuoi;
			} else {
				chuoi = dochangtrieu(ty, false) + hauto + chuoi;
			}
			hauto = " tỷ ";
		} while (so > 0);
		
		if (StringUtils.isNotEmpty(chuoi)) {
			chuoi = chuoi.trim().substring(0, chuoi.trim().length() - 1);
		}
		return chuoi.trim() + " đồng";
	}

	// Đọc số hàng chục
	private static String dochangchuc(double so, Boolean daydu) {
		String chuoi = "";
		int chuc = (int) Math.floor(so / 10);
		int donvi = (int) so % 10;
		if (chuc > 1) {
			chuoi = " " + mangso[chuc] + " mươi";
			if (donvi == 1) {
				chuoi += " mốt";
			}
		} else if (chuc == 1) {
			chuoi = " mười";
			if (donvi == 1) {
				chuoi += " một";
			}
		} else if (daydu && donvi > 0) {
			chuoi = " lẻ";
		}
		if (donvi == 5 && chuc >= 1) {
			chuoi += " lăm";
		} else if (donvi > 1 || (donvi == 1 && chuc == 0)) {
			chuoi += " " + mangso[donvi];
		}
		return chuoi;
	}

	// Đọc block 3 số
	private static String docblock(double so, Boolean daydu) {
		String chuoi = "";
		int tram = (int) Math.floor(so / 100);
		so = so % 100;
		if (daydu || tram > 0) {
			chuoi = " " + mangso[tram] + " trăm";
			chuoi += dochangchuc(so, true);
		} else {
			chuoi = dochangchuc(so, false);
		}
		return chuoi;
	}

	// Đọc số hàng triệu
	private static String dochangtrieu(double so, Boolean daydu) {
		String chuoi = "";
		int trieu = (int) Math.floor(so / 1000000);
		so = so % 1000000;
		if (trieu > 0) {
			chuoi = docblock(trieu, daydu) + " triệu ";
			daydu = true;
		}
		double nghin = Math.floor(so / 1000);
		so = so % 1000;
		if (nghin > 0) {
			chuoi += docblock(nghin, daydu) + " nghìn, ";
			daydu = true;
		}
		if (so > 0) {
			chuoi += docblock(so, daydu);
		}
		return chuoi;
	}
	
}

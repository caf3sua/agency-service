package com.baoviet.agency.utils;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * Implementation of PasswordEncoder that uses the BCrypt strong hashing function. Clients
 * can optionally supply a "strength" (a.k.a. log rounds in BCrypt) and a SecureRandom
 * instance. The larger the strength parameter the more work will have to be done
 * (exponentially) to hash the passwords. The default value is 10.
 *
 * @author Dave Syer
 *
 */
public class BaovietPasswordEncoder implements PasswordEncoder {
	
	private Pattern BCRYPT_PATTERN = Pattern
			.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
	private final Log logger = LogFactory.getLog(getClass());

	private final int strength;

	private final SecureRandom random;
	
	private final int MIN_LOG_ROUNDS = 4;
	
	private final int MAX_LOG_ROUNDS = 31;

	public BaovietPasswordEncoder() {
		this(-1);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 */
	public BaovietPasswordEncoder(int strength) {
		this(strength, null);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 * @param random the secure random instance to use
	 *
	 */
	public BaovietPasswordEncoder(int strength, SecureRandom random) {
		if (strength != -1 && (strength < MIN_LOG_ROUNDS || strength > MAX_LOG_ROUNDS)) {
			throw new IllegalArgumentException("Bad strength");
		}
		this.strength = strength;
		this.random = random;
	}

	public String encode(CharSequence rawPassword) {
		String salt;
		if (strength > 0) {
			if (random != null) {
				salt = BCrypt.gensalt(strength, random);
			}
			else {
				salt = BCrypt.gensalt(strength);
			}
		}
		else {
			salt = BCrypt.gensalt();
		}
		return BCrypt.hashpw(rawPassword.toString(), salt);
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String md5Password = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
		
		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}

		// compare password
		if (!StringUtils.equals(md5Password, encodedPassword)) {
			logger.warn("Wrong password");
			return false;
		}
		
		return true;
	}
}

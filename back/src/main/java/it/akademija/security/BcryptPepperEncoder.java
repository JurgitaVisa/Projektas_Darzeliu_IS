package it.akademija.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPepperEncoder extends BCryptPasswordEncoder {

	private String pepper;

	/**
	 * Standard constructor of BCryptPasswordEncoder. Set pepper value
	 */
	public BcryptPepperEncoder() {
		super(12);
		this.pepper = "Red$1&Hot*5^Chilly&VI1CyDbqRrlA3dUu282";
	}

	/**
	 * Add the pepper before calling BCryptPasswordEncoder.
	 *
	 * @param rawPassword The original password.
	 * @return Encoded password.
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return super.encode(rawPassword + pepper);
	}

	/**
	 * Add the pepper to the raw password and call BCryptPasswordEncoder.
	 *
	 * @param rawPassword     The original password.
	 * @param encodedPassword The encoded password.
	 * @return Whether passwords match.
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
				
		return super.matches(rawPassword + pepper, encodedPassword);
	}
}

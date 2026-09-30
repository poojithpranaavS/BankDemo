package com.ducat;

import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class MGSamples {

	private static final String API_URL = "https://api.mailgun.net/v3/sandbox2f226f255cbc45c394429d6664138f82.mailgun.org/messages";
	private static final String FROM_SENDER = "Mailgun Sandbox <postmaster@sandbox2f226f255cbc45c394429d6664138f82.mailgun.org>";

	private static String getApiKey() {
		String apiKey = System.getenv("API_KEY");
		return apiKey;
	}

	public static JsonNode sendOTP(String username, String email, String otp) {
		return Unirest.post(API_URL).basicAuth("api", getApiKey()).field("from", FROM_SENDER).field("to", email)
				.field("subject", "Your Security Code").field("text", "Hello " + username + ", your OTP is: " + otp)
				.asJson().getBody();
	}

	public static JsonNode welcomeMail(String username, String email) {
		return Unirest.post(API_URL).basicAuth("api", getApiKey()).field("from", FROM_SENDER).field("to", email)
				.field("subject", "Welcome to The Banking System")
				.field("text", "Welcome " + username + "! Your account has been successfully created.").asJson()
				.getBody();
	}
}

package com.api.utils;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;
import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private AuthTokenProvider() {}
	public static String getToken(Role role) throws IOException {
		//I want to make the request for login api and we want to extract the token and 
		//print it on the console
		UserCredentials userCred= null;
		
		if(role== FD) {
			userCred = new UserCredentials("iamfd","password");
		}
		
		else if(role== SUP) {
			userCred = new UserCredentials("iamsup","password");
		}
		
		else if(role== ENG) {
			userCred = new UserCredentials("iameng","password");
		}
		
		else if(role== QC) {
			userCred = new UserCredentials("iamqc","password");
		}
		
		
		String token = given().baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.body(new UserCredentials("iamfd","password"))
		.when()
		.post("login")
		.then()
		.log()
		.ifValidationFails()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.and()
		.extract()
		.body()
		.jsonPath()
		.getString("data.token");
		

		return token;
	}

}

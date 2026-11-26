package com.api.tests;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
public class MasterAPITest {

	@Test
	
	public void masterAPITest() throws IOException {
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.headers("Authorization", getToken(SUP))
		.and()
		.contentType("")
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(4000L))
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body("data",hasKey("mst_oem"))
		.body("data",hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", Matchers.equalTo(2))
		.body("data.mst_oem.id", Matchers.everyItem(notNullValue()))
		.body("data.mst_oem.name", Matchers.everyItem(notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
	}
	
	
	@Test
	public void invalidTokenMasterAPI() throws IOException {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.headers("Authorization", "")
		.contentType("")
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(401);
	}
}

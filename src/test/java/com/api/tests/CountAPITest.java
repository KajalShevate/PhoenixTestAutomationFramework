package com.api.tests;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.ConfigManager;

import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() throws IOException {
		given()
		.baseUri(getProperty("BASE_URI"))
		.header("Authorization", getToken(Role.FD))
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()	
		.statusCode(200)
		.body("message", equalTo("Success"))
		.time(lessThan(1000L))
		.body("data", notNullValue())
		.body("data.size()", equalTo(3))
		.body("data.count", everyItem(greaterThanOrEqualTo(0)))
		.body("data.label", everyItem(not(blankOrNullString())))
		.body("data.key", containsInAnyOrder("pending_fst_assignment","pending_for_delivery","created_today"))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	
	
	public void countAPITest_MissingAuthToken() throws IOException {
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(200);
		
	}
	
}

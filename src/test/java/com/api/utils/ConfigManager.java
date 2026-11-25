package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	// write a program to read the property file from
	// src/test/resources/config/config.properties
	private static Properties prop = new Properties(); // create object of Properties class

	private static String path = "config/config.properties";

	private static String env;

	private ConfigManager() {
	} // private constructor so that we can not create object of this class outside it

	static {
		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();

		System.out.println("Running tests in Env: " + env);
		switch (env) {

		case "dev" -> path = "condifg/config.dev.properties";

		case "qa" -> path = "condifg/config.qa.properties";

		case "uat" -> path = "condifg/config.uat.properties";

		default -> path = "condifg/config.qa.properties";

		}
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config/config.properties");
		if (input == null) {
			throw new RuntimeException("Can not find the file at the path" + path);
		}

		try {
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) throws IOException {
		return prop.getProperty(key);

	}

}

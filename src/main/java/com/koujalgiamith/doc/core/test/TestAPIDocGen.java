package com.koujalgiamith.doc.core.test;

import com.koujalgiamith.doc.core.annotations.RZTRESTService;
import com.koujalgiamith.doc.core.annotations.RZTRESTServiceMethod;
import com.koujalgiamith.doc.core.annotations.RZTRESTServiceMethod.RESTMethodType;
import com.koujalgiamith.doc.core.annotations.RZTRESTServiceResponseCode;
import com.koujalgiamith.doc.core.entry.RZTRestAPIScanner;

public class TestAPIDocGen {
	public static void main(String[] args) throws Exception {
		RZTRestAPIScanner s = new RZTRestAPIScanner("Test Webapp");
		s.scanPackage("com.koujalgiamith");
	}
}

@RZTRESTService(serviceDescription = "Test service desc", serviceName = "Test service", path = "test")

class TestJerseyServiceClass {

	@RZTRESTServiceMethod(path = "fetch-city-info", methodType = RESTMethodType.GET, verbs = { "country",
			"city" }, methodDescription = "Fetch city info", methodResponseClass = Message.class, responseCodes = {
					@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
					@RZTRESTServiceResponseCode(httpCode = "400", description = "Bad request", statusCode = "ERR_BAD_CITY_NAME") })

	public Object fetchCityInfo() throws Exception {
		System.out.println("Hit the service");
		Message msg = new Message();
		msg.setMessage("Test message");
		msg.setMessageCode("200_ok");
		return msg;
	}

	@RZTRESTServiceMethod(path = "add-new-city", methodType = RESTMethodType.POST, methodDescription = "Add new city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
			@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
			@RZTRESTServiceResponseCode(httpCode = "400", description = "Bad request", statusCode = "ERR_NO_CITY_NAME") })
	public Object addNewCity(Message m) throws Exception {
		System.out.println("Hit the service");
		Message msg = new Message();
		msg.setMessage("Test message");
		msg.setMessageCode("200_ok");
		return msg;
	}

	class Message {
		private String message, messageCode;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getMessageCode() {
			return messageCode;
		}

		public void setMessageCode(String messageCode) {
			this.messageCode = messageCode;
		}
	}
}
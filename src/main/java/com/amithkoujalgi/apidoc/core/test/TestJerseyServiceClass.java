package com.amithkoujalgi.apidoc.core.test;
//package com.koujalgiamith.apidoc.core.test;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import com.koujalgiamith.apidoc.core.annotations.RZTRESTService;
//import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceHeader;
//import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceMethod;
//import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceResponseCode;
//import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceMethod.RESTMethodType;
//
//@RZTRESTService(serviceDescription = "Test service desc", serviceName = "Test service", path = "test", deprecated = false, visible = true, version = "1.2", headers = {
//		@RZTRESTServiceHeader(description = "Test service header", name = "X-TEST-HEADER", valuesAllowed = { "123",
//				"abc" }) })
//@Path("/test")
//public class TestJerseyServiceClass {
//
//	@RZTRESTServiceMethod(path = "fetch-city-info", methodType = RESTMethodType.GET, verbs = { "country",
//			"city" }, methodDescription = "Fetch city info", methodResponseClass = Message.class, responseCodes = {
//					@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
//					@RZTRESTServiceResponseCode(httpCode = "400", description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME") }, headers = {
//							@RZTRESTServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
//									"ogow1onnovnlk3" }) })
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/fetch-city-info")
//	public Object fetchCityInfo() throws Exception {
//		System.out.println("Hit the service");
//		Message msg = new Message();
//		msg.setMessage("Test message");
//		msg.setMessageCode("200_ok");
//		return msg;
//	}
//
//	@RZTRESTServiceMethod(path = "add-new-city", methodType = RESTMethodType.POST, methodDescription = "Add new city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
//			@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
//			@RZTRESTServiceResponseCode(httpCode = "400", description = "City name missing", statusCode = "ERR_NO_CITY_NAME") }, headers = {
//					@RZTRESTServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
//							"ogow1onnovnlk3" }) })
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/add-new-city")
//	public Object addNewCity(Message m) throws Exception {
//		System.out.println("Hit the service");
//		Message msg = new Message();
//		msg.setMessage("Test message");
//		msg.setMessageCode("200_ok");
//		return msg;
//	}
//
//}
package com.razorthink.utils.apidoc.test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.razorthink.utils.apidoc.annotations.WebService;
import com.razorthink.utils.apidoc.annotations.WebServiceHeader;
import com.razorthink.utils.apidoc.annotations.WebServiceMethod;
import com.razorthink.utils.apidoc.annotations.WebServiceMethod.RESTMethodType;
import com.razorthink.utils.apidoc.annotations.WebServiceMethodURLParam;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode.HTTPStatus;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode.ResponseType;

@WebService(serviceDescription = "Test service desc", serviceName = "Testservice", path = "test", deprecated = false, visible = true, version = "1.2", headers = {
		@WebServiceHeader(description = "Test service header", name = "X-TEST-HEADER", valuesAllowed = { "123",
				"abc" }) })
@Path("/test")
public class TestJerseyServiceClass {

	@WebServiceMethod(path = "fetch-city-info", type = RESTMethodType.GET, description = "Fetch city info", responseClass = Message.class, responseCodes = {
			@WebServiceResponseCode(httpStatus = HTTPStatus.SC_OK, description = "Fetch city info successfully", statusCode = "OK", type = ResponseType.SUCCESS),
			@WebServiceResponseCode(httpStatus = HTTPStatus.SC_BAD_REQUEST, description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME", type = ResponseType.FAILURE) }, headers = {
					@WebServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" }) }, urlParameters = {
									@WebServiceMethodURLParam(desc = "city spec", name = "city", possibleValues = {
											"bengaluru" }, order = 0) })

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fetch-city-info")
	public Object fetchCityInfo() throws Exception {
		System.out.println("Hit the service");
		Message msg = new Message();
		msg.setMessage("Test message");
		msg.setMessageCode("200_ok");

		return msg;
	}

	@WebServiceMethod(path = "add-new-city", type = RESTMethodType.POST, description = "Add new city info", requestClass = Message.class, responseClass = Message.class, responseCodes = {
			@WebServiceResponseCode(httpStatus = HTTPStatus.SC_OK, description = "Fetch city info successfully", statusCode = "OK", type = ResponseType.SUCCESS),
			@WebServiceResponseCode(httpStatus = HTTPStatus.SC_BAD_REQUEST, description = "City name missing", statusCode = "ERR_NO_CITY_NAME", type = ResponseType.FAILURE) }, headers = {
					@WebServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" })

	})

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add-new-city")
	public Object addNewCity(Message m) throws Exception {
		System.out.println("Hit the service");
		Message msg = new Message();
		msg.setMessage("Test message");
		msg.setMessageCode("200_ok");
		return msg;
	}

}
package com.amithkoujalgi.apidoc.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amithkoujalgi.apidoc.annotations.RZTRESTService;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceHeader;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceMethod;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceResponseCode;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceMethod.RESTMethodType;

@RZTRESTService( serviceDescription = "Test service desc", serviceName = "Test service", path = "/", headers = {
		@RZTRESTServiceHeader( description = "Test service header", name = "X-TEST-HEADER", valuesAllowed = { "123",
				"abc" } ) })

public class TestHTTPServletClass extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@RZTRESTServiceMethod( path = "fetch-city-info", methodType = RESTMethodType.GET, methodDescription = "Fetch city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
			@RZTRESTServiceResponseCode( httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK" ),
			@RZTRESTServiceResponseCode( httpCode = "400", description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME" ) }, headers = {
					@RZTRESTServiceHeader( description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" } ) })
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}

	@RZTRESTServiceMethod( path = "add-new-city", methodType = RESTMethodType.POST, methodDescription = "Add new city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
			@RZTRESTServiceResponseCode( httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK" ),
			@RZTRESTServiceResponseCode( httpCode = "400", description = "City name missing", statusCode = "ERR_NO_CITY_NAME" ) }, headers = {
					@RZTRESTServiceHeader( description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" } ) })
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		super.doPost(req, resp);
	}
}
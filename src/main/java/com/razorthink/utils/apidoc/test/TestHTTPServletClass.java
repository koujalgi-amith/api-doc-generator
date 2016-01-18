package com.razorthink.utils.apidoc.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.razorthink.utils.apidoc.annotations.WebService;
import com.razorthink.utils.apidoc.annotations.WebServiceHeader;
import com.razorthink.utils.apidoc.annotations.WebServiceMethod;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode;
import com.razorthink.utils.apidoc.annotations.WebServiceMethod.RESTMethodType;
import com.razorthink.utils.apidoc.annotations.WebServiceMethodURLParam;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode.HTTPStatus;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode.ResponseType;

@WebService(serviceDescription = "Test service desc", serviceName = "Test service", path = "/", headers = {
		@WebServiceHeader(description = "Test service header", name = "X-TEST-HEADER", valuesAllowed = { "123",
				"abc" }) }, tags = { "test", "servlet" })

public class TestHTTPServletClass extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@WebServiceMethod(path = "fetch-city-info", type = RESTMethodType.GET, urlParameters = {
			@WebServiceMethodURLParam(desc = "city spec", name = "city") }, description = "Fetch city info", responseClass = Message.class, responseCodes = {
					@WebServiceResponseCode(httpStatus = HTTPStatus.SC_OK, description = "Fetch city info successfully", statusCode = "OK", type = ResponseType.SUCCESS),
					@WebServiceResponseCode(httpStatus = HTTPStatus.SC_BAD_REQUEST, description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME", type = ResponseType.FAILURE) }, headers = {
							@WebServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
									"ogow1onnovnlk3" }) }, tags = { "fetch", "city" })
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@WebServiceMethod(path = "add-new-city", type = RESTMethodType.POST, description = "Add new city info", requestClass = Message.class, responseClass = Message.class, responseCodes = {
			@WebServiceResponseCode(httpStatus = HTTPStatus.SC_OK, description = "Fetch city info successfully", statusCode = "OK", type = ResponseType.SUCCESS),
			@WebServiceResponseCode(httpStatus = HTTPStatus.SC_BAD_REQUEST, description = "City name missing", statusCode = "ERR_NO_CITY_NAME", type = ResponseType.FAILURE) }, headers = {
					@WebServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" }) })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
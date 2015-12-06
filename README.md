# api-doc-generator

[![Build Status](https://travis-ci.org/koujalgi-amith/api-doc-generator.svg)](https://travis-ci.org/koujalgi-amith/api-doc-generator)

Generate API document/playground for any kind of Java webapp.

 - Annotate your Java application classes that are used as webservice implementations.
 - Run the API generator over the application packages.
 - Find the API document of your webservices in JSON form.

Supports:
 - Jersey JAX-RS
 - Servlets
 - support for more frameworks to be added (Spring, Spark Java, etc)
 
The API generator:

```java
RZTRestAPIScanner scanner = new RZTRestAPIScanner("Test Webapp");
scanner.scanPackage("com.koujalgiamith");
```

A sample Jersey service class:

```java
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.koujalgiamith.apidoc.core.annotations.RZTRESTService;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceHeader;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceMethod;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceResponseCode;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceMethod.RESTMethodType;

@RZTRESTService(serviceDescription = "Test service desc", serviceName = "Test service", path = "test", deprecated = false, visible = true, version = "1.2", headers = {
		@RZTRESTServiceHeader(description = "Test service header", name = "X-TEST-HEADER", valuesAllowed = { "123",
				"abc" }) })
@Path("/test")
public class TestJerseyServiceClass {

	@RZTRESTServiceMethod(path = "fetch-city-info", methodType = RESTMethodType.GET, verbs = { "country",
			"city" }, methodDescription = "Fetch city info", methodResponseClass = Message.class, responseCodes = {
					@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
					@RZTRESTServiceResponseCode(httpCode = "400", description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME") }, headers = {
							@RZTRESTServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
									"ogow1onnovnlk3" }) })
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

	@RZTRESTServiceMethod(path = "add-new-city", methodType = RESTMethodType.POST, methodDescription = "Add new city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
			@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
			@RZTRESTServiceResponseCode(httpCode = "400", description = "City name missing", statusCode = "ERR_NO_CITY_NAME") }, headers = {
					@RZTRESTServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" }) })
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
```

Sample JSON model of the above REST service:

```javascript
{
  "appName": "Test Webapp",
  "services": [
    {
      "serviceName": "Test service",
      "serviceClassName": "com.koujalgiamith.apidoc.core.test.TestJerseyServiceClass",
      "serviceDescription": "Test service desc",
      "path": "/test",
      "headers": [
        {
          "name": "X-TEST-HEADER",
          "description": "Test service header",
          "allowedValues": [
            "123",
            "abc"
          ]
        }
      ],
      "methods": [
        {
          "methodName": "fetchCityInfo",
          "methodDescription": "Fetch city info",
          "methodResponseClass": "com.koujalgiamith.apidoc.core.test.Message",
          "methodRequestClass": "javax.validation.constraints.Null",
          "path": "/fetch-city-info",
          "endpoint": "/test/fetch-city-info",
          "responseJSONStructure": "{\n  \"message\": \"J0hWSCPAz3\",\n  \"messageCode\": \"MziMzAQjZo\"\n}",
          "version": "1.0",
          "responseCodes": [
            {
              "statusCode": "200_OK",
              "httpCode": "200",
              "description": "Fetch city info successfully"
            },
            {
              "statusCode": "ERR_BAD_CITY_NAME",
              "httpCode": "400",
              "description": "City name isn't right"
            }
          ],
          "headers": [
            {
              "name": "X-RZT-API-TOKEN",
              "description": "API Auth token",
              "allowedValues": [
                "ogow1onnovnlk3"
              ]
            }
          ],
          "methodType": "GET",
          "verbs": [
            "country",
            "city"
          ]
        },
        {
          "methodName": "addNewCity",
          "methodDescription": "Add new city info",
          "methodResponseClass": "com.koujalgiamith.apidoc.core.test.Message",
          "methodRequestClass": "com.koujalgiamith.apidoc.core.test.Message",
          "path": "/add-new-city",
          "endpoint": "/test/add-new-city",
          "requestJSONStructure": "{\n  \"message\": \"EpKnRU57RN\",\n  \"messageCode\": \"8Q1pDGmFcC\"\n}",
          "responseJSONStructure": "{\n  \"message\": \"gB8q3qO8tT\",\n  \"messageCode\": \"gyvIvb6wlC\"\n}",
          "version": "1.0",
          "responseCodes": [
            {
              "statusCode": "200_OK",
              "httpCode": "200",
              "description": "Fetch city info successfully"
            },
            {
              "statusCode": "ERR_NO_CITY_NAME",
              "httpCode": "400",
              "description": "City name missing"
            }
          ],
          "headers": [
            {
              "name": "X-RZT-API-TOKEN",
              "description": "API Auth token",
              "allowedValues": [
                "ogow1onnovnlk3"
              ]
            }
          ],
          "methodType": "POST"
        }
      ]
    }
  ]
}
```

A sample HTTPServlet class:

```java
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koujalgiamith.apidoc.core.annotations.RZTRESTService;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceHeader;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceMethod;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceResponseCode;
import com.koujalgiamith.apidoc.core.annotations.RZTRESTServiceMethod.RESTMethodType;

@RZTRESTService(serviceDescription = "Test service desc", serviceName = "Test service", path = "/", headers = {
		@RZTRESTServiceHeader(description = "Test service header", name = "X-TEST-HEADER", valuesAllowed = { "123",
				"abc" }) })

public class TestHTTPServletClass extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RZTRESTServiceMethod(path = "fetch-city-info", methodType = RESTMethodType.GET, methodDescription = "Fetch city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
			@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
			@RZTRESTServiceResponseCode(httpCode = "400", description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME") }, headers = {
					@RZTRESTServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" }) })
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@RZTRESTServiceMethod(path = "add-new-city", methodType = RESTMethodType.POST, methodDescription = "Add new city info", methodRequestClass = Message.class, methodResponseClass = Message.class, responseCodes = {
			@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
			@RZTRESTServiceResponseCode(httpCode = "400", description = "City name missing", statusCode = "ERR_NO_CITY_NAME") }, headers = {
					@RZTRESTServiceHeader(description = "API Auth token", name = "X-RZT-API-TOKEN", valuesAllowed = {
							"ogow1onnovnlk3" }) })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
```

Sample JSON model of the above servlet:

```javascript
{
  "appName": "Test Webapp",
  "services": [
    {
      "serviceName": "Test service",
      "serviceClassName": "com.koujalgiamith.apidoc.core.test.TestHTTPServletClass",
      "serviceDescription": "Test service desc",
      "path": "/",
      "headers": [
        {
          "name": "X-TEST-HEADER",
          "description": "Test service header",
          "allowedValues": [
            "123",
            "abc"
          ]
        }
      ],
      "methods": [
        {
          "methodName": "doGet",
          "methodDescription": "Fetch city info",
          "methodResponseClass": "com.koujalgiamith.apidoc.core.test.Message",
          "methodRequestClass": "com.koujalgiamith.apidoc.core.test.Message",
          "path": "/fetch-city-info",
          "endpoint": "/fetch-city-info",
          "requestJSONStructure": "{\n  \"message\": \"4ebDOx5hiz\",\n  \"messageCode\": \"LNwnDkep_o\"\n}",
          "responseJSONStructure": "{\n  \"message\": \"arJzjituty\",\n  \"messageCode\": \"icyOD7q3Ko\"\n}",
          "version": "1.0",
          "responseCodes": [
            {
              "statusCode": "200_OK",
              "httpCode": "200",
              "description": "Fetch city info successfully"
            },
            {
              "statusCode": "ERR_BAD_CITY_NAME",
              "httpCode": "400",
              "description": "City name isn't right"
            }
          ],
          "headers": [
            {
              "name": "X-RZT-API-TOKEN",
              "description": "API Auth token",
              "allowedValues": [
                "ogow1onnovnlk3"
              ]
            }
          ],
          "methodType": "GET"
        },
        {
          "methodName": "doPost",
          "methodDescription": "Add new city info",
          "methodResponseClass": "com.koujalgiamith.apidoc.core.test.Message",
          "methodRequestClass": "com.koujalgiamith.apidoc.core.test.Message",
          "path": "/add-new-city",
          "endpoint": "/add-new-city",
          "requestJSONStructure": "{\n  \"message\": \"PKdz9IUXyW\",\n  \"messageCode\": \"9F0bO4UN54\"\n}",
          "responseJSONStructure": "{\n  \"message\": \"kpRqLyOd41\",\n  \"messageCode\": \"iumHNoekRr\"\n}",
          "version": "1.0",
          "responseCodes": [
            {
              "statusCode": "200_OK",
              "httpCode": "200",
              "description": "Fetch city info successfully"
            },
            {
              "statusCode": "ERR_NO_CITY_NAME",
              "httpCode": "400",
              "description": "City name missing"
            }
          ],
          "headers": [
            {
              "name": "X-RZT-API-TOKEN",
              "description": "API Auth token",
              "allowedValues": [
                "ogow1onnovnlk3"
              ]
            }
          ],
          "methodType": "POST"
        }
      ]
    }
  ]
}
```

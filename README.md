# api-doc-generator

[![Build Status](https://travis-ci.org/koujalgi-amith/api-doc-generator.svg)](https://travis-ci.org/koujalgi-amith/api-doc-generator)

Generate API document/playground for any kind of Java webapp.

The API generator:

```java
RZTRestAPIScanner scanner = new RZTRestAPIScanner("Test Webapp");
scanner.scanPackage("com.koujalgiamith");
```

A sample Jersey service class:

```java

@RZTRESTService(serviceDescription = "Test service desc", serviceName = "Test service", path = "test")

@Path("/test")
@Component
public class TestJerseyServiceClass {

	@RZTRESTServiceMethod(path = "fetch-city-info", methodType = RESTMethodType.GET, verbs = { "country",
			"city" }, methodDescription = "Fetch city info", methodResponseClass = Message.class, responseCodes = {
					@RZTRESTServiceResponseCode(httpCode = "200", description = "Fetch city info successfully", statusCode = "200_OK"),
					@RZTRESTServiceResponseCode(httpCode = "400", description = "City name isn't right", statusCode = "ERR_BAD_CITY_NAME") })
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
			@RZTRESTServiceResponseCode(httpCode = "400", description = "City name missing", statusCode = "ERR_NO_CITY_NAME") })
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
  "appType": "JERSEY_2X",
  "services": [
    {
      "serviceName": "Test service",
      "serviceClassName": "com.koujalgiamith.doc.core.test.TestJerseyServiceClass",
      "serviceDescription": "Test service desc",
      "path": "/test",
      "headers": [
        ""
      ],
      "methods": [
        {
          "methodName": "fetchCityInfo",
          "methodDescription": "Fetch city info",
          "methodResponseClass": "com.koujalgiamith.doc.core.test.TestJerseyServiceClass$Message",
          "methodRequestClass": "javax.validation.constraints.Null",
          "path": "/fetch-city-info",
          "endpoint": "/test/fetch-city-info",
          "responseJSONStructure": "{\n  \"message\": \"YtzEX5acVq\",\n  \"messageCode\": \"F6IMdS4uYR\"\n}",
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
          "methodType": "GET",
          "verbs": [
            "country",
            "city"
          ]
        },
        {
          "methodName": "addNewCity",
          "methodDescription": "Add new city info",
          "methodResponseClass": "com.koujalgiamith.doc.core.test.TestJerseyServiceClass$Message",
          "methodRequestClass": "com.koujalgiamith.doc.core.test.TestJerseyServiceClass$Message",
          "path": "/add-new-city",
          "endpoint": "/test/add-new-city",
          "requestJSONStructure": "{\n  \"message\": \"CO6ckYcEaE\",\n  \"messageCode\": \"EOxvDmSGvb\"\n}",
          "responseJSONStructure": "{\n  \"message\": \"QPv7x2HFH7\",\n  \"messageCode\": \"PGZxJFrBqE\"\n}",
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
          "methodType": "POST"
        }
      ]
    }
  ]
}
```

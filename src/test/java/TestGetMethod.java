import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestGetMethod {
    private final String uri = "http://localhost:8080/test";

    @Test
    void testGetStatusCode(){
        Response response = RestAssured.get(this.uri);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }
    @Test
    void testBodyHeader(){
        Response response = RestAssured.get(this.uri);
        Assert.assertTrue(response.getHeaders().asList().size() > 0);
        response.then().assertThat().header("content-type", Matchers.containsString("application/json"));
        response.then().assertThat().body("txtMessage", Matchers.containsString("GET"));
    }

    @Test
    void testGetHeader(){
        Response response = RestAssured.get(this.uri);
        response.then().assertThat().header("content-type", Matchers.containsString("application/json"));
    }

    @Test
    void testMatchBody(){
        Response response = RestAssured.get(this.uri);
        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("GET"));
    }

    @Test
    void testAsMethod(){
        Response response = RestAssured.get(this.uri);
        response.getBody().prettyPrint();
        Assert.assertEquals(response.getBody().as(TestResponse.class).getTxtMessage(), "Response received via GET! Param: Default");
    }

    @Test
    void testGet(){
        Response response = RestAssured.get(this.uri);
        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("GET"));
    }

    @Test
    void testPost(){
        Response response = RestAssured.post(this.uri);
        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("POST"));
    }

    @Test
    void testDelete(){
        Response response = RestAssured.delete(this.uri);
        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("DELETE"));
    }

    @Test
    void testPatch(){
        Response response = RestAssured.patch(this.uri);
        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("PATCH"));
    }

    @Test
    void testPut(){
        Response response = RestAssured.put(this.uri);
        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("PUT"));
    }

    @Test
    void testHead(){
        Response response = RestAssured.head(this.uri);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    void testSendBody(){
        Response response = RestAssured.given()
                .body("{txt: 'request param'}")
                .post(this.uri);

        response.getBody().prettyPrint();
        response.then().assertThat().header("content-type", Matchers.containsString("application/json"));
    }

    @Test
    void testSendQueryParam(){
        Response response = RestAssured
                .given()
                .queryParam("name", "Query Param")
                .get(this.uri);

        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("Query Param"));
    }

    @Test
    void testSendPathParam(){
        Response response = RestAssured
                .get(this.uri+"/{msg}", "Path Param");

        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("Path Param"));
    }

    @Test
    void testSendFormParam(){
        Response response = RestAssured
                .given()
                .formParam("name", "Form Param")
                .post(this.uri);

        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("Form Param"));
    }

    @Test
    void testSendCookies(){
        Response response = RestAssured
                .given()
                .cookie("cookie", "cookie123")
                .get(this.uri);

        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("GET"));
    }

    @Test
    void testSendHeaders(){
        Response response = RestAssured
                .given()
                .header("content-type", "application/json")
                .get(this.uri);

        response.getBody().prettyPrint();
        response.then().assertThat().body("txtMessage", Matchers.containsString("GET"));
    }

    @Test
    void testGetCookies(){
        Response response = RestAssured
                .post(this.uri);
        response.then().assertThat().cookies("cookie", Matchers.containsString("test"));
    }

    @Test
    void testResponseTime(){
        long time = RestAssured
                .get(this.uri).time();
        Assert.assertNotNull(time);
    }

    @Test
    void testResponseContentType(){
        String contentType = RestAssured
                .get(this.uri).contentType();
        Assert.assertEquals(contentType, "application/json");
    }

}

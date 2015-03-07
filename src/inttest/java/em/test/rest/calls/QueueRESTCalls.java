package em.test.rest.calls;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import em.model.Queue;
import em.test.rest.config.RESTTestConfig;

public class QueueRESTCalls {
    
    public static Queue createQueue() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/queue"));
        
        return toQueue(r.getBody().asString());
    }
    
    public static void deleteQueue(int id) throws IOException {
        deleteQueue(id, 204);
    }
    
    public static void deleteQueue(int id, int expectStatusCode) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        
        req.delete(RESTTestConfig.getInstance().getFullURL("/queue/{id}"), id); // JOE uc
    }
    
    public static Queue toQueue(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<Queue>() {
        });
    }
}

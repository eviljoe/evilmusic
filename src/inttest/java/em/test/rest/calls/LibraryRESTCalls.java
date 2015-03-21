package em.test.rest.calls;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import em.model.Library;
import em.test.rest.config.RESTTestConfig;

/**
 * @since v0.1
 * @author eviljoe
 */
public class LibraryRESTCalls {
    
    public static void rebuildLibrary() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        
        res = req.then();
        res.expect().statusCode(204);
        
        res.post(RESTTestConfig.getInstance().getFullURL("/library"));
    }
    
    public static Library getLibrary() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/library"));
        
        return toLibrary(r.getBody().asString());
    }
    
    public static void clearLibrary() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        
        res = req.then();
        res.expect().statusCode(204);
        
        res.delete(RESTTestConfig.getInstance().getFullURL("/library"));
    }
    
    public static Library toLibrary(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<Library>() {
        });
    }
}

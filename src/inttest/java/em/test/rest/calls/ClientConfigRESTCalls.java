package em.test.rest.calls;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import em.test.rest.config.RESTTestConfig;

/**
 * @since v0.1
 * @author eviljoe
 */
public class ClientConfigRESTCalls {
    
    public static double getVolume() {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        req.when().get(RESTTestConfig.getInstance().getFullURL("/config/volume"));
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get();
        
        return Double.parseDouble(r.getBody().asString());
    }
}

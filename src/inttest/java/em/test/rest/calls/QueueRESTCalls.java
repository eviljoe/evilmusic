/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
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
        
        req.delete(RESTTestConfig.getInstance().getFullURL("/queue/{id}"), id);
    }
    
    public static Queue getQueue(int id) throws IOException {
        return getQueue(200, id);
    }
    
    public static Queue getQueue(int expectStatusCode, int id) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/queue/{id}"), id);
        
        return expectStatusCode == 200 ? toQueue(r.getBody().asString()) : null;
    }
    
    public static Queue addLast(int expectStatusCode, int qID, int... songIDs) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r =
                res.put(RESTTestConfig.getInstance().getFullURL("/queue/{qID}/last?songIDs={songIDs}"), qID,
                        EMUtils.toCSV(songIDs, ","));
        
        return expectStatusCode == 200 ? toQueue(r.getBody().asString()) : null;
    }
    
    public static Queue clear(int qID) throws IOException {
        return clear(200, qID);
    }
    
    public static Queue clear(int expectStatusCode, int qID) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.delete(RESTTestConfig.getInstance().getFullURL("/queue/{qID}/elements"), qID);
        
        return expectStatusCode == 200 ? toQueue(r.getBody().asString()) : null;
    }
    
    public static Queue removeElement(int qID, int index) throws IOException {
        return removeElement(200, qID, index);
    }
    
    public static Queue removeElement(int expectStatusCode, int qID, int index) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.delete(RESTTestConfig.getInstance().getFullURL("/queue/{qID}/queueindex/{index}"), qID, index);
        
        return expectStatusCode == 200 ? toQueue(r.getBody().asString()) : null;
    }
    
    public static Queue setPlayIndex(int qID, int playIndex) throws IOException {
        return setPlayIndex(200, qID, playIndex);
    }
    
    public static Queue setPlayIndex(int expectStatusCode, int qID, int playIndex) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.put(RESTTestConfig.getInstance().getFullURL("/queue/{qID}/playindex/{playIndex}"), qID, playIndex);
        
        return expectStatusCode == 200 ? toQueue(r.getBody().asString()) : null;
    }
    
    public static Queue toQueue(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<Queue>() {
        });
    }
}

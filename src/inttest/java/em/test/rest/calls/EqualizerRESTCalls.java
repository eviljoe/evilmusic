/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
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
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import em.model.Equalizer;
import em.test.rest.config.RESTTestConfig;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EqualizerRESTCalls {
    
    public static Equalizer getCurrentEQ() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/eq/default"));
        
        return toEQ(r.getBody().asString());
    }
    
    public static Equalizer getEQ(int id) throws IOException {
        return getEQ(id, 200);
    }
    
    public static Equalizer getEQ(int id, int expectStatusCode) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/eq/{id}"), id);
        
        return expectStatusCode == 200 ? toEQ(r.getBody().asString()) : null;
    }
    
    public static List<Equalizer> getAllEQs() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/eq"));
        
        return toEQs(r.getBody().asString());
    }
    
    public static void deleteAllEQs() {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        
        res = req.then();
        res.expect().statusCode(204);
        
        res.delete(RESTTestConfig.getInstance().getFullURL("/eq"));
    }
    
    public static void deleteEQ(int id) {
        deleteEQ(id, 204);
    }
    
    public static void deleteEQ(int id, int expectStatusCode) {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        
        res.delete(RESTTestConfig.getInstance().getFullURL("/eq/{id}"), id);
    }
    
    public static Equalizer updateEQ(Equalizer eq) throws IOException {
        return updateEQ(eq, 200);
    }
    
    public static Equalizer updateEQ(Equalizer eq, int expectStatusCode) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        req.body(eq);
        req.contentType(ContentType.JSON);
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.put(RESTTestConfig.getInstance().getFullURL("/eq/{id}"), eq.getID());
        
        return expectStatusCode == 200 ? toEQ(r.getBody().asString()) : null;
    }
    
    public static Equalizer toEQ(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<Equalizer>() {
        });
    }
    
    public static List<Equalizer> toEQs(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<List<Equalizer>>() {
        });
    }
}

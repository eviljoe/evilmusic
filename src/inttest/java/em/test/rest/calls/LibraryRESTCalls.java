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
    
    public static Library rebuildLibrary() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.post(RESTTestConfig.getInstance().getFullURL("/library"));
        
        return toLibrary(r.getBody().asString());
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
    
    public static Library clearLibrary() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.delete(RESTTestConfig.getInstance().getFullURL("/library"));
        
        return toLibrary(r.getBody().asString());
    }
    
    public static Library toLibrary(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<Library>() {
        });
    }
}

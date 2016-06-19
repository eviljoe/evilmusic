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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import em.model.Playlist;
import em.test.rest.config.RESTTestConfig;

/**
 * @since v0.1
 * @author eviljoe
 */
public class PlaylistRESTCalls {
    
    public static Playlist createPlaylist() throws IOException {
        return createPlaylist(200, "rest test playlist " + Long.toString(System.currentTimeMillis(), 16));
    }
    
    public static Playlist createPlaylist(String name) throws IOException {
        return createPlaylist(200, name);
    }
    
    public static Playlist createPlaylist(int expectStatusCode, String name) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.post(RESTTestConfig.getInstance().getFullURL("/playlists?name={name}"), name);
        
        return expectStatusCode == 200 ? toPlaylist(r.getBody().asString()) : null;
    }
    
    public static Playlist getPlaylist(int pID) throws IOException {
        return getPlaylist(200, pID);
    }
    
    public static Playlist getPlaylist(int expectStatusCode, int pID) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/playlists/{pID}"), pID);
        
        return expectStatusCode == 200 ? toPlaylist(r.getBody().asString()) : null;
    }
    
    public static List<Playlist> getPlaylists() throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(200);
        res.expect().contentType(ContentType.JSON);
        
        r = res.get(RESTTestConfig.getInstance().getFullURL("/playlists"));
        
        return toPlaylists(r.getBody().asString());
    }
    
    public static Playlist addLast(int expectStatusCode, int pID, int... songIDs) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.put( //
                RESTTestConfig.getInstance().getFullURL("/playlists/{pID}/last?{songIDs}"), //
                pID, //
                Arrays.stream(songIDs).mapToObj(id -> "songID=" + id).collect(Collectors.joining("&")));
        
        return expectStatusCode == 200 ? toPlaylist(r.getBody().asString()) : null;
    }
    
    public static Playlist setPlaylistName(int pID, String name) throws IOException {
        return setPlaylistName(200, pID, name);
    }
    
    public static Playlist setPlaylistName(int expectStatusCode, int pID, String name) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.put(RESTTestConfig.getInstance().getFullURL("/playlists/{pID}/name/{name}"), pID, name);
        
        return expectStatusCode == 200 ? toPlaylist(r.getBody().asString()) : null;
    }
    
    public static Playlist clearPlaylist(int pID) throws IOException {
        return clearPlaylist(200, pID);
    }
    
    public static Playlist clearPlaylist(int expectStatusCode, int pID) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.delete(RESTTestConfig.getInstance().getFullURL("/playlists/{pID}/elements"), pID);
        
        return expectStatusCode == 200 ? toPlaylist(r.getBody().asString()) : null;
    }
    
    public static Playlist removeElement(int pID, int elemID) throws IOException {
        return removeElement(200, pID, elemID);
    }
    
    public static Playlist removeElement(int expectStatusCode, int pID, int elemID) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        final Response r;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        res.expect().contentType(ContentType.JSON);
        
        r = res.delete(RESTTestConfig.getInstance().getFullURL("/playlists/{pID}/elements/{elemID}"), pID, elemID);
        
        return expectStatusCode == 200 ? toPlaylist(r.getBody().asString()) : null;
    }
    
    public static void deletePlaylist(int id) throws IOException {
        deletePlaylist(204, id);
    }
    
    public static void deletePlaylist(int expectStatusCode, int id) throws IOException {
        final RequestSpecification req = given();
        final ResponseSpecification res;
        
        res = req.then();
        res.expect().statusCode(expectStatusCode);
        
        res.delete(RESTTestConfig.getInstance().getFullURL("/playlists/{id}"), id);
    }
    
    public static Playlist toPlaylist(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<Playlist>() {
        });
    }
    
    public static List<Playlist> toPlaylists(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<List<Playlist>>() {
        });
    }
}

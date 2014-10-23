package em.controllers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

//	@GET
//	@RequestMapping("/music")
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
//	public Response getSong(@Context HttpServletResponse response) {
//		final File file = new File("/home/joe/Desktop/song.flac");
//		
//		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
//				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
//				.build();
//	}
	
	@GET
	@RequestMapping("/music")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getSong(@Context HttpServletResponse response) {
		try {
			final File file = new File("/home/joe/Desktop/song.flac");
			byte[] buf = new byte[1024];
	        int length = 0;
	        ServletOutputStream outStream;
	        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	        
	        response.setContentLength((int) file.length());
	        response.setHeader("Content-Disposition", "attachment; filename="
	                + file.getName());
	        outStream = response.getOutputStream();
	        
	        while (((length = in.read(buf)) != -1)) {
	            outStream.write(buf, 0, length);
	        }
	        
	        in.close();
	        outStream.flush();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
	    return Response.ok().build();
		
	}
	
}

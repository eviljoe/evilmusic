package em.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.SongInfoDAO;
import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class MusicController {
    
    @Autowired
    private SongInfoDAO songInfoDAO;
    
    @RequestMapping(value = "/music", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public Response getSong(@Context HttpServletResponse response) {
        try {
            final File file = new File("/home/joe/Desktop/song.flac");
            byte[] buf = new byte[1024];
            int length = 0;
            ServletOutputStream outStream;
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            
            response.setContentLength((int)file.length());
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            outStream = response.getOutputStream();
            
            while(((length = in.read(buf)) != -1)) {
                outStream.write(buf, 0, length);
            }
            
            in.close();
            outStream.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return Response.ok().build();
    }
    
    @RequestMapping(value = "/addsongandlist", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<SongInfo> addSongAndList() {
        final SongInfo song = new SongInfo();
        final Date now = new Date();
        
        song.setArtist("test artist, " + now);
        song.setAlbum("test album, " + now);
        song.setYear((int)(now.getTime() % 2014L));
        song.setSeconds((int)(now.getTime() % 360L));
        
        return songInfoDAO.addAndList(song);
    }
    
    @RequestMapping(value = "/songs", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void removeAllSongs() {
        songInfoDAO.removeAllSongs();
    }
}

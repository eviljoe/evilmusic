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

package em.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import em.model.SongInfo;
import em.utils.metadatareaders.MetaDataReadException;
import em.utils.metadatareaders.MetaDataReader;

/**
 * @since v0.1
 * @author eviljoe
 */
public class LibraryUtils {
    
    private static final String HOME_DIR_KEYWORD = "$home";
    private static final String CLASSPATH_KEYWORD = "$classpath";
    private static final String TIMESTAMP_KEYWORD = "$timestamp";
    private static final String HOME_DIR_PROP_NAME = "user.home";
    static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH-mm-ss-S";
    
    private static final Logger LOG = Logger.getLogger(LibraryUtils.class.getName());
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Private constructor so this class cannot be instantiated or extended. */
    private LibraryUtils() {
        super();
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    public static List<SongInfo> scanDirectories(String[] dirNames) throws IOException, URISyntaxException {
        final ArrayList<SongInfo> musicFiles = new ArrayList<SongInfo>();
        
        if(dirNames != null) {
            for(String dirName : dirNames) {
                scanFile(convertToFile(dirName), musicFiles);
            }
        }
        
        return musicFiles;
    }
    
    private static void scanFile(File f, List<SongInfo> musicFiles) {
        if(f != null) {
            String logMsg;
            
            if(f.isDirectory()) {
                final File[] children = f.listFiles();
                
                logMsg = "Scanning Directory: " + f.getPath();
                
                if(children != null) {
                    for(File child : children) {
                        scanFile(child, musicFiles);
                    }
                }
            } else {
                final SongInfo info = getSongInfoForFile(f);
                
                if(info != null) {
                    logMsg = "Adding File: " + f.getName();
                    musicFiles.add(info);
                } else {
                    logMsg = "Skipping File: " + f.getName();
                }
            }
            
            LOG.info(logMsg);
        }
    }
    
    private static SongInfo getSongInfoForFile(File f) {
        SongInfo info = null;
        
        try {
            info = MetaDataReader.getMetaDataFor(f);
        } catch(MetaDataReadException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        
        return info;
    }
    
    public static void streamSongToResponse(HttpServletResponse response, SongInfo info, boolean updateHeadersOnly)
            throws IOException, NullPointerException, FileNotFoundException, SecurityException {
        
        if(response != null && info != null) {
            final File file = info.getFile();
            
            if(file == null) {
                throw new NullPointerException("Cannot stream from a null file");
            } else if(!file.exists()) {
                throw new FileNotFoundException("File does not exist: " + file);
            } else if(!file.canRead()) {
                throw new SecurityException("Cannot read file: " + file);
            }
            
            response.setContentLength((int)file.length());
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            
            if(!updateHeadersOnly) {
                final ServletOutputStream outStream = response.getOutputStream();
                final BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                final byte[] buf = new byte[1024];
                int length = 0;
                
                try {
                    while(((length = in.read(buf)) != -1)) {
                        outStream.write(buf, 0, length);
                    }
                } finally {
                    in.close();
                    outStream.flush();
                }
            }
        }
    }
    
    public static List<SongInfo> sortSongs(Collection<Integer> orderedSongIDs, Collection<SongInfo> songs) {
        final ArrayList<SongInfo> sortedSongs = new ArrayList<>(0);
        
        if(EMUtils.hasValues(orderedSongIDs) && EMUtils.hasValues(songs)) {
            final IDSet<SongInfo> unsortedSongs = new IDSet<>(songs);
            
            sortedSongs.ensureCapacity(unsortedSongs.size());
            
            for(Integer songID : orderedSongIDs) {
                SongInfo song = unsortedSongs.get(songID);
                
                if(song != null) {
                    sortedSongs.add(song);
                }
            }
        }
        
        return sortedSongs;
    }
    
    /* *********************************** */
    /* Song Directory Conversion Functions */
    /* *********************************** */
    
    public static File convertToFile(String dir) throws URISyntaxException {
        File f = null;
        
        if(EMUtils.hasValues(dir)) {
            dir = processHomeKeyword(dir);
            dir = processTimestampKeyword(dir);
            f = processClasspathKeyword(dir, LibraryUtils.class.getClassLoader());
            
            if(f == null) {
                f = new File(dir);
            }
        }
        
        return f;
    }
    
    static String processHomeKeyword(String dir) {
        if(dir != null && dir.startsWith(HOME_DIR_KEYWORD)) {
            dir = System.getProperty(HOME_DIR_PROP_NAME) + dir.substring(HOME_DIR_KEYWORD.length());
        }
        
        return dir;
    }
    
    static String processTimestampKeyword(String dir) {
        if(dir != null && dir.contains(TIMESTAMP_KEYWORD)) {
            final int timestampIndex = dir.indexOf(TIMESTAMP_KEYWORD);
            final String timestamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
            
            dir =
                    dir.substring(0, timestampIndex) + timestamp
                            + dir.substring(timestampIndex + TIMESTAMP_KEYWORD.length());
        }
        
        return dir;
    }
    
    static File processClasspathKeyword(String dir, ClassLoader loader) throws URISyntaxException {
        File f = null;
        
        if(dir != null && dir.startsWith(CLASSPATH_KEYWORD)) {
            final String resourcePath = dir.substring(CLASSPATH_KEYWORD.length());
            f = new File(loader.getResource(resourcePath).toURI());
        }
        
        return f;
    }
}

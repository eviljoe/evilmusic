package em.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import em.model.SongInfo;

public class LibraryUtils {
    
    private static final String HOME_DIR_KEYWORD = "$home";
    private static final String HOME_DIR_PROP_NAME = "user.home";
    private static final String FLAC_EXTENSION = "flac";
    
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
    
    public static List<SongInfo> scanDirectories(Collection<String> dirNames) throws IOException {
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
        
        if(isMusicFile(f)) {
            info = new SongInfo();
            info.setName(f.getName());
            info.setFile(f);
        }
        
        return info;
    }
    
    public static boolean isMusicFile(File f) {
        boolean musicFile = false;
        
        if(f != null && !f.isDirectory()) {
            final String ext = EMUtils.getExtension(f);
            
            musicFile = ext == null ? false : FLAC_EXTENSION.equals(ext);
        }
        
        return musicFile;
    }
    
    public static File convertToFile(String dir) {
        File f = null;
        
        if(EMUtils.hasValues(dir)) {
            if(dir.startsWith(HOME_DIR_KEYWORD)) {
                dir = System.getProperty(HOME_DIR_PROP_NAME) + dir.substring(HOME_DIR_KEYWORD.length());
            }
            
            f = new File(dir);
        }
        
        return f;
    }
    
    public static SongInfo sanitizeForClient(SongInfo info) {
        if(info != null) {
            info.setFile(null);
        }
        
        return info;
    }
    
    public static <S extends SongInfo, C extends Collection<S>> C sanitizeForClient(C infos) {
        if(infos != null) {
            for(SongInfo info : infos) {
                sanitizeForClient(info);
            }
        }
        
        return infos;
    }
    
    public static void streamSongToResponse(HttpServletResponse response, SongInfo info) throws IOException {
        if(response != null && info != null) {
            final File file = info.getFile();
            
            if(file != null) {
                final ServletOutputStream outStream = response.getOutputStream();
                final BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                final byte[] buf = new byte[1024];
                int length = 0;
                
                try {
                    response.setContentLength((int)file.length());
                    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                    
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
}

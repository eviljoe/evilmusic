package em.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

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
}

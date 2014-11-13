package em.utils.metadatareaders;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import em.model.SongInfo;
import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
public class FLACMetaDataReader extends MetaDataReader {
    
    private static final String FLAC_FILE_EXT = "flac";
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public FLACMetaDataReader() {
        super();
    }
    
    /* ************************** */
    /* Meta Data Reader Functions */
    /* ************************** */
    
    /** {@inheritDoc} */
    @Override
    public boolean canReadMetaData(File f) {
        return EMUtils.equalsIgnoreCase(FLAC_FILE_EXT, EMUtils.getExtension(f).trim());
    }
    
    /** {@inheritDoc} */
    @Override
    public SongInfo readMetaData(File f) throws MetaDataReadException {
        SongInfo info = null;
        
        try {
            info = executeMetaDataCommand(f);
        } catch(IOException | InterruptedException e) {
            throw new MetaDataReadException(e);
        }
        
        return info;
    }
    
    private SongInfo executeMetaDataCommand(File f) throws IOException, InterruptedException {
        final ProcessBuilder pb = new ProcessBuilder(new String[] {"metaflac", "--list", f.getAbsolutePath()});
        final Process p = pb.start();
        final InputStream stdOut = p.getInputStream();
        final InputStream stdErr = p.getErrorStream();
        final SongInfoStreamGobbler stdOutGobbler = new SongInfoStreamGobbler(stdOut);
        final IgnoreStreamGobbler stdErrGobbler = new IgnoreStreamGobbler(stdErr);
        final int exitCode;
        SongInfo info = null;
        
        stdOutGobbler.start();
        stdErrGobbler.start();
        exitCode = p.waitFor();
        
        if(exitCode == 0) {
            stdOutGobbler.join();
            info = stdOutGobbler.getSongInfo();
        }
        
        return info;
    }
    
    static SongInfo parseMetaData(InputStream in) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int blockType = -1;
        int sampleRate = -1;
        int sampleCount = -1;
        String line = null;
        SongInfo info = new SongInfo();
        
        while((line = br.readLine()) != null) {
            line = line.trim();
            
            if(line.startsWith("metadata block #")) {
                blockType = -1;
            } else if(EMUtils.equalsIgnoreCase(parseField(line, "type"), "0 (STREAMINFO)")) {
                blockType = 0;
            } else if(EMUtils.equalsIgnoreCase(parseField(line, "type"), "4 (VORBIS_COMMENT)")) {
                blockType = 4;
            } else if(blockType == 0) {
                String value;
                
                if((value = parseField(line, "sample_rate")) != null && value.endsWith("Hz")) {
                    sampleRate = Integer.parseInt(value.substring(0, value.length() - 2).trim());
                    info.setSampleRate(sampleRate);
                } else if((value = parseField(line, "total samples")) != null) {
                    sampleCount = Integer.parseInt(value);
                    info.setSampleCount(sampleCount);
                }
            } else if(blockType == 4) {
                String commentValue;
                
                if((commentValue = parseComment(line, "artist")) != null) {
                    info.setArtist(commentValue);
                } else if((commentValue = parseComment(line, "album")) != null) {
                    info.setAlbum(commentValue);
                } else if((commentValue = parseComment(line, "date")) != null) {
                    info.setYear(Integer.parseInt(commentValue));
                } else if((commentValue = parseComment(line, "title")) != null) {
                    info.setTitle(commentValue);
                } else if((commentValue = parseComment(line, "genre")) != null) {
                    info.setGenre(commentValue);
                } else if((commentValue = parseComment(line, "tracknumber")) != null) {
                    info.setTrackNumber(Integer.parseInt(commentValue));
                }
            }
        }
        
        if(sampleRate > -1 && sampleCount > -1) {
            info.setSeconds((int)Math.ceil((double)sampleCount / (double)sampleRate));
        }
        
        return info;
    }
    
    static String parseField(String line, String field) {
        String value = null;
        
        if(line != null && field != null) {
            final String[] parts = line.split(":");
            
            if(parts != null && parts.length == 2 && EMUtils.equalsIgnoreCase(field, parts[0].trim().toLowerCase())) {
                value = parts[1].trim();
            }
        }
        
        return value;
    }
    
    static String parseComment(String line, String field) {
        String value = null;
        
        if(line != null && field != null) {
            final String[] parts = line.split(":");
            
            if(parts != null && parts.length == 2 && parts[0].trim().toLowerCase().startsWith("comment[")) {
                final String[] subparts = parts[1].trim().split("=");
                
                if(subparts != null && subparts.length == 2 && EMUtils.equalsIgnoreCase(subparts[0].trim(), field)) {
                    value = subparts[1].trim();
                }
            }
        }
        
        return value;
    }
    
    /* ************** */
    /* Stream Gobbler */
    /* ************** */
    
    private static class SongInfoStreamGobbler extends Thread {
        
        private final InputStream in;
        private volatile SongInfo info;
        
        public SongInfoStreamGobbler(InputStream in) {
            super();
            this.in = in;
            this.info = null;
        }
        
        public void run() {
            try {
                info = parseMetaData(in);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        public SongInfo getSongInfo() {
            return info;
        }
    }
    
    private static class IgnoreStreamGobbler extends Thread {
        
        private final BufferedInputStream in;
        
        public IgnoreStreamGobbler(InputStream in) {
            super();
            this.in = new BufferedInputStream(in);
        }
        
        public void run() {
            final byte[] buf = new byte[1024];
            
            try {
                while(in.read(buf) > -1) {
                    // Do nothing
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}

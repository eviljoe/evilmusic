package em.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMCommandLine {
    
    private static final List<EMOption> OPTIONS;
    
    /* ************ */
    /* Static Block */
    /* ************ */
    
    static {
        final ArrayList<EMOption> options = new ArrayList<>();
        
        options.add(new EMOption(null, "add-music-dir", true, "Add a directory that will be scanned for audio files",
                new AddMusicDirCallback()));
        options.add(new EMOption(null, "remove-music-dir", true,
                "Remove a directory from those that are scanned for audio files", new RemoveMusicDirCallback()));
        options.add(new EMOption(null, "list-music-dirs", false,
                "List the directories that will be scanned for audio files", new ListMusicDirsCallback()));
        options.add(new EMOption(null, "scan-music-dirs", false, "Scan the music directories for audio files",
                new ScanMusicDirsCallback()));
        
        OPTIONS = Collections.unmodifiableList(options);
    }
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Private constructor so this class cannot be instantiated or extended. */
    private EMCommandLine() {
        super();
    }
    
    /* ************* */
    /* CLI Functions */
    /* ************* */
    
    public static void parseAndAct(String[] args) throws ParseException {
        final Options opts = new Options();
        final CommandLineParser parser = new GnuParser();
        final CommandLine cmd;
        
        for(EMOption opt : OPTIONS) {
            opts.addOption(opt.getOption());
        }
        
        cmd = parser.parse(opts, args);
        
        for(EMOption opt : OPTIONS) {
            final String longOpt = opt.getLongOption();
            
            if(cmd.hasOption(longOpt)) {
                opt.getCallback().performAction(cmd.getOptionValues(longOpt));
            }
        }
    }
    
    /* **************** */
    /* Option Callbacks */
    /* **************** */
    
    private static class AddMusicDirCallback implements EMOptionCallback {
        
        public void performAction(String[] args) {
            
        }
    }
    
    private static class RemoveMusicDirCallback implements EMOptionCallback {
        
        public void performAction(String[] args) {
            
        }
    }
    
    private static class ListMusicDirsCallback implements EMOptionCallback {
        
        public void performAction(String[] args) {
            
        }
    }
    
    private static class ScanMusicDirsCallback implements EMOptionCallback {
        
        public void performAction(String[] args) {
            
        }
    }
}

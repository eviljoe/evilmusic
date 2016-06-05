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

package em.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import em.prefs.EMPreferencesManager;
import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMCommandLine {
    
    private static final Logger LOG = Logger.getLogger(EMCommandLine.class.getName());
    private static final List<EMOption> OPTIONS;
    
    /* ************ */
    /* Static Block */
    /* ************ */
    
    static {
        final ArrayList<EMOption> options = new ArrayList<>();
        
        options.add(new EMOption(null, "emproperties", true, "Specify the properties file", new EMPropertiesCallback()));
        
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
                final String[] optVals = cmd.getOptionValues(longOpt);
                opt.getCallback().performAction(optVals == null ? new String[0] : optVals);
            }
        }
    }
    
    /* **************** */
    /* Option Callbacks */
    /* **************** */
    
    private static class EMPropertiesCallback implements EMOptionCallback {
        
        @Override
        public void performAction(String[] args) {
            final String fullPath = EMUtils.toCSV(args, " ");
            
            LOG.info("Command line specified properties file: " + fullPath);
            
            if(EMUtils.hasValues(fullPath)) {
                EMPreferencesManager.getInstance().setCommandLinePreferencesFile(new File(fullPath));
            }
        }
    }
}

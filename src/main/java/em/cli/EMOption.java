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

import org.apache.commons.cli.Option;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMOption {
    
    private Option option;
    private EMOptionCallback callback;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public EMOption(String opt, String longOpt, boolean hasArg, String desc, EMOptionCallback callback) {
        super();
        
        setOption(new Option(opt, longOpt, hasArg, desc));
        setCallback(callback);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public Option getOption() {
        return option;
    }
    
    public void setOption(Option option) {
        this.option = option;
    }
    
    public String getLongOption() {
        final Option opt = getOption();
        return opt == null ? null : option.getLongOpt();
    }
    
    public EMOptionCallback getCallback() {
        return callback;
    }
    
    public void setCallback(EMOptionCallback callback) {
        this.callback = callback;
    }
}

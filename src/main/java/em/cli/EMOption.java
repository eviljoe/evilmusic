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

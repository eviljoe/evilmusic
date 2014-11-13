package em.model;

/**
 * @since v0.1
 * @author eviljoe
 */
public class QueuedSongInfo extends SongInfo {
    
    private int queueIndex;
    private int playIndex;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public QueuedSongInfo() {
        super();
    }
    
    public QueuedSongInfo(Integer id) {
        super(id);
    }
    
    public QueuedSongInfo(SongInfo info) {
        super();
        
        if(info != null) {
            info.update(this);
        }
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public int getQueueIndex() {
        return queueIndex;
    }
    
    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    public int getPlayIndex() {
        return playIndex;
    }
    
    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }
}

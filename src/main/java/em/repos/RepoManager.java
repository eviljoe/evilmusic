package em.repos;


/**
 * @since v0.1
 * @author eviljoe
 */
public class RepoManager {
    
    private static RepoManager instance;
    
    private volatile SongInfoRepository songInfo;
    private volatile QueueRepository queue;
    private volatile EqualizerRepository equalizer;
    private volatile ClientConfigurationRepository clientConfig;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepoManager() {
        super();
    }
    
    /* ******************* */
    /* Singleton Functions */
    /* ******************* */
    
    public static synchronized RepoManager getInstance() {
        if(instance == null) {
            instance = new RepoManager();
        }
        return instance;
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public SongInfoRepository getSongInfo() {
        return songInfo;
    }
    
    public void setSongInfo(SongInfoRepository songInfo) {
        this.songInfo = songInfo;
    }
    
    public QueueRepository getQueue() {
        return queue;
    }
    
    public void setQueue(QueueRepository queue) {
        this.queue = queue;
    }
    
    public EqualizerRepository getEqualizer() {
        return equalizer;
    }
    
    public void setEqualizer(EqualizerRepository equalizer) {
        this.equalizer = equalizer;
    }
    
    public ClientConfigurationRepository getClientConfiguration() {
        return clientConfig;
    }
    
    public void setClientConfiguration(ClientConfigurationRepository clientConfig) {
        this.clientConfig = clientConfig;
    }
}

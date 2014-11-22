package em.repos;

/**
 * @since v0.1
 * @author eviljoe
 */
public class RepoManager {
    
    private static RepoManager instance;
    
    private volatile SongInfoRepository songInfo;
    private volatile MusicDirectoryRepository musicDirectory;
    private volatile QueueRepository queue;
    
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
    
    public MusicDirectoryRepository getMusicDirectory() {
        return musicDirectory;
    }
    
    public void setMusicDirectory(MusicDirectoryRepository musicDirectory) {
        this.musicDirectory = musicDirectory;
    }

    public QueueRepository getQueue() {
        return queue;
    }

    public void setQueue(QueueRepository queue) {
        this.queue = queue;
    }
}

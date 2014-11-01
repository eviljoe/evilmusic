package em.repos;

public class RepoManager {
    
    private static RepoManager instance;
    
    private volatile SongInfoRepository songInfo;
    
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
}

package em.dao;

import java.util.List;

import em.model.SongInfo;

public interface SongInfoDAO {
    
    public List<SongInfo> addAndList(SongInfo song);
    
    public void removeAllSongs();
}

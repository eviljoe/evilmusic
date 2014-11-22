package em.dao;

import java.util.Collection;
import java.util.List;

import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface SongInfoDAO {
    
    public List<SongInfo> addAndList(SongInfo song);
    
    public void removeAllSongs();
    
    public void replaceAllSongs(Collection<SongInfo> infos);
    
    public List<SongInfo> findAll();
    
    public SongInfo findByID(Integer id);
    
    public List<SongInfo> findByID(Collection<Integer> ids);
}

package em.dao.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import em.dao.SongInfoDAO;
import em.model.SongInfo;
import em.repos.SongInfoRepository;
import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@Service
public class RepositorySongInfoDAO extends AbstractRepositoryDAO implements SongInfoDAO {
    
    private SongInfoRepository songInfoRepo;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepositorySongInfoDAO() {
        super();
    }
    
    /* ************* */
    /* DAO Functions */
    /* ************* */
    
    @Override
    public List<SongInfo> addAndList(SongInfo newSong) {
        final SongInfoRepository repo = getSongInfoRepo();
        final ArrayList<SongInfo> songs = new ArrayList<SongInfo>();
        
        repo.save(newSong);
        
        for(SongInfo song : repo.findAll()) {
            songs.add(song);
        }
        
        return songs;
    }
    
    @Override
    public void removeAllSongs() {
        getSongInfoRepo().deleteAll();
    }
    
    @Override
    public void replaceAllSongs(Collection<SongInfo> infos) {
        final SongInfoRepository repo = getSongInfoRepo();
        
        repo.deleteAll();
        
        if(EMUtils.hasValues(infos)) {
            repo.save(infos);
        }
    }
    
    @Override
    public List<SongInfo> findAll() {
        final SongInfoRepository repo = getSongInfoRepo();
        final ArrayList<SongInfo> infos = new ArrayList<SongInfo>();
        
        for(SongInfo info : repo.findAll()) {
            infos.add(info);
        }
        
        return infos;
    }
    
    @Override
    public SongInfo findByID(Integer id) {
        final SongInfoRepository repo = getSongInfoRepo();
        return repo.findOne(id);
    }
    
    @Override
    public List<SongInfo> findByID(Collection<Integer> ids) {
        final SongInfoRepository repo = getSongInfoRepo();
        final List<SongInfo> infos = new ArrayList<>();
        
        for(SongInfo info : repo.findAll(ids)) {
            infos.add(info);
        }
        
        return infos;
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    private SongInfoRepository getSongInfoRepo() {
        if(songInfoRepo == null) {
            songInfoRepo = this.getRepoManager().getSongInfo();
        }
        return songInfoRepo;
    }
}

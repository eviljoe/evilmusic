package em.dao.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import em.dao.SongInfoDAO;
import em.model.SongInfo;
import em.repos.SongInfoRepository;
import em.utils.EMUtils;
import em.utils.IDSet;

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
    @Transactional
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
    @Transactional
    public void removeAllSongs() {
        getSongInfoRepo().deleteAll();
    }
    
    @Override
    @Transactional
    public void replaceAllSongs(Collection<SongInfo> infos) {
        final SongInfoRepository repo = getSongInfoRepo();
        
        repo.deleteAll();
        
        if(EMUtils.hasValues(infos)) {
            repo.save(infos);
        }
    }
    
    @Override
    @Transactional
    public List<SongInfo> findAll() {
        final SongInfoRepository repo = getSongInfoRepo();
        final ArrayList<SongInfo> infos = new ArrayList<SongInfo>();
        
        for(SongInfo info : repo.findAll()) {
            infos.add(info);
        }
        
        return infos;
    }
    
    @Override
    @Transactional
    public SongInfo findByID(Integer id) {
        final SongInfoRepository repo = getSongInfoRepo();
        return repo.findOne(id);
    }
    
    @Override
    @Transactional
    public Set<SongInfo> findByID(Collection<Integer> ids) {
        final SongInfoRepository repo = getSongInfoRepo();
        final IDSet<SongInfo> infos = new IDSet<>();
        
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

package em.dao.repo;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import em.dao.SongInfoDAO;
import em.model.SongInfo;
import em.repos.SongInfoRepository;

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

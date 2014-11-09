package em.dao.repo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import em.dao.MusicDirectoryDAO;
import em.model.MusicDirectory;
import em.repos.MusicDirectoryRepository;
import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
public class RepositoryMusicDirectoryDAO extends AbstractRepositoryDAO implements MusicDirectoryDAO {
    
    private MusicDirectoryRepository musicDirectoryRepo;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepositoryMusicDirectoryDAO() {
        super();
    }
    
    /* ************* */
    /* DAO Functions */
    /* ************* */
    
    @Override
    @Transactional
    public boolean addMusicDirectory(File dir) {
        final MusicDirectoryRepository repo = getMusicDirectoryRepository();
        boolean added = false;
        
        if(dir != null) {
            repo.save(new MusicDirectory(dir));
            added = true;
            // JOE todo? catch unique constraint violation and return false?
        }
        
        return added;
    }
    
    @Override
    @Transactional
    public boolean removeMusicDirectory(File dir) {
        final MusicDirectoryRepository repo = getMusicDirectoryRepository();
        boolean removed = false;
        
        if(dir != null && repo.findByDirectory(dir) == null) {
            repo.delete(new MusicDirectory(dir));
            removed = true;
        }
        
        return removed;
    }
    
    @Override
    @Transactional
    public List<MusicDirectory> getMusicDirectories() {
        final MusicDirectoryRepository repo = getMusicDirectoryRepository();
        final ArrayList<MusicDirectory> mdirs = new ArrayList<>();
        
        for(MusicDirectory mdir : repo.findAll()) {
            mdirs.add(mdir);
        }
        
        return mdirs;
    }
    
    @Override
    @Transactional
    public void replaceAll(Collection<String> mdirs) {
        final MusicDirectoryRepository repo = getMusicDirectoryRepository();
        
        repo.deleteAll();
        
        if(EMUtils.hasValues(mdirs)) {
            final ArrayList<MusicDirectory> musicDirs = new ArrayList<>();
            
            for(String mdir : mdirs) {
                musicDirs.add(new MusicDirectory(mdir));
            }
            
            repo.save(musicDirs);
        }
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    private MusicDirectoryRepository getMusicDirectoryRepository() {
        if(musicDirectoryRepo == null) {
            musicDirectoryRepo = this.getRepoManager().getMusicDirectory();
        }
        return musicDirectoryRepo;
    }
}

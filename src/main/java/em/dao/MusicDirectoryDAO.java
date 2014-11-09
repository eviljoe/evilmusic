package em.dao;

import java.io.File;
import java.util.Collection;
import java.util.List;

import em.model.MusicDirectory;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface MusicDirectoryDAO {
    
    public boolean addMusicDirectory(File dir);
    
    public boolean removeMusicDirectory(File dir);
    
    public List<MusicDirectory> getMusicDirectories();
    
    public void replaceAll(Collection<String> mdirs);
}

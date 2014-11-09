package em.repos;

import java.io.File;

import org.springframework.data.repository.CrudRepository;

import em.model.MusicDirectory;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface MusicDirectoryRepository extends CrudRepository<MusicDirectory, Integer> {
    
    public MusicDirectory findByDirectory(File directory);
}

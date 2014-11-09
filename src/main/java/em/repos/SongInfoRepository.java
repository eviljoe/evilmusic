package em.repos;

import org.springframework.data.repository.CrudRepository;

import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface SongInfoRepository extends CrudRepository<SongInfo, Integer> {
}

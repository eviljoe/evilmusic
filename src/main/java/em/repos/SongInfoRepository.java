package em.repos;

import org.springframework.data.repository.CrudRepository;

import em.model.SongInfo;

public interface SongInfoRepository extends CrudRepository<SongInfo, Long> {
}

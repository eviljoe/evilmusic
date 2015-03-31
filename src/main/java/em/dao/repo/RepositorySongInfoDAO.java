/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

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

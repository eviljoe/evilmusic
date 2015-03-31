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

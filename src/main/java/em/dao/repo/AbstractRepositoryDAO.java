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

import em.repos.RepoManager;

/**
 * @since v0.1
 * @author eviljoe
 */
public class AbstractRepositoryDAO {
    
    private RepoManager rmgr;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public AbstractRepositoryDAO() {
        super();
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public RepoManager getRepoManager() {
        if(rmgr == null) {
            rmgr = RepoManager.getInstance();
        }
        return rmgr;
    }
}

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

package em.repos;

/**
 * @since v0.1
 * @author eviljoe
 */
public class RepoManager {
    
    private static RepoManager instance;
    
    private volatile SongInfoRepository songInfo;
    private volatile QueueRepository queue;
    private volatile ClientConfigurationRepository clientConfig;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepoManager() {
        super();
    }
    
    /* ******************* */
    /* Singleton Functions */
    /* ******************* */
    
    public static synchronized RepoManager getInstance() {
        if(instance == null) {
            instance = new RepoManager();
        }
        return instance;
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public SongInfoRepository getSongInfo() {
        return songInfo;
    }
    
    public void setSongInfo(SongInfoRepository songInfo) {
        this.songInfo = songInfo;
    }
    
    public QueueRepository getQueue() {
        return queue;
    }
    
    public void setQueue(QueueRepository queue) {
        this.queue = queue;
    }
    
    public ClientConfigurationRepository getClientConfiguration() {
        return clientConfig;
    }
    
    public void setClientConfiguration(ClientConfigurationRepository clientConfig) {
        this.clientConfig = clientConfig;
    }
}

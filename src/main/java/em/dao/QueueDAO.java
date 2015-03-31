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

import java.util.Set;

import em.model.Queue;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface QueueDAO {
    
    public Set<Queue> findAll();
    
    public Queue findById(int id);
    
    public Queue save(Queue queue);
    
    public void removeAll();
    
    public void remove(int id);
}

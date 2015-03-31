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

import java.util.Set;

import org.springframework.stereotype.Service;

import em.dao.EqualizerDAO;
import em.model.Equalizer;
import em.repos.EqualizerRepository;
import em.utils.IDSet;

/**
 * @since v0.1
 * @author eviljoe
 */
@Service
public class RepositoryEqualizerDAO extends AbstractRepositoryDAO implements EqualizerDAO {
    
    private EqualizerRepository eqRepo;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepositoryEqualizerDAO() {
        super();
    }
    
    /* ************* */
    /* DAO Functions */
    /* ************* */
    
    @Override
    public Set<Equalizer> findAll() {
        final IDSet<Equalizer> eqs = new IDSet<>();
        
        for(Equalizer eq : getEqualizerRepo().findAll()) {
            eqs.add(eq);
        }
        
        return eqs;
    }
    
    @Override
    public Equalizer findByID(int id) {
        return getEqualizerRepo().findOne(id);
    }
    
    @Override
    public Equalizer save(Equalizer eq) {
        return getEqualizerRepo().save(eq);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    private EqualizerRepository getEqualizerRepo() {
        if(eqRepo == null) {
            eqRepo = this.getRepoManager().getEqualizer();
        }
        return eqRepo;
    }
}

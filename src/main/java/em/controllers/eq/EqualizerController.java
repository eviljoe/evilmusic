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

package em.controllers.eq;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.model.Equalizer;
import em.model.EqualizerNode;
import em.utils.EqualizerUtils;
import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class EqualizerController {
    
    private static final Logger LOG = Logger.getLogger(EqualizerController.class.getName());
    private static final String SELECT_ALL_EQS_JPQL = String.format("SELECT e FROM %s e ", Equalizer.class.getName());
    private static final String DELETE_ALL_EQS_JPQL = String.format("DELETE FROM %s", Equalizer.class.getName());
    
    @PersistenceContext
    private EntityManager em;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public EqualizerController() {
        super();
    }
    
    /* ************** */
    /* REST Functions */
    /* ************** */
    
    @Transactional
    @RequestMapping(value = "/rest/eq/current", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer maybeCreateEqualizer() {
        Equalizer eq;
        
        LogUtils.restCall(LOG, "/rest/eq/current", RequestMethod.GET, "Requesting equalizer, maybe create");
        
        try {
            eq = em.createQuery(SELECT_ALL_EQS_JPQL, Equalizer.class).getSingleResult();
        } catch(NoResultException e) {
            eq = EqualizerUtils.createDefaultEqualizer();
            em.persist(eq);
        }
        
        return eq;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equalizer> getAllEqualizers() {
        final List<Equalizer> eqs;
        
        LogUtils.restCall(LOG, "/rest/eq/current", RequestMethod.GET, "Requesting all equalizers");
        eqs = em.createQuery(SELECT_ALL_EQS_JPQL, Equalizer.class).getResultList();
        
        return eqs;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer getEqualizer(@PathVariable("id") int id) {
        final Equalizer eq;
        
        LogUtils.restCall(LOG, "/rest/eq/{id}", RequestMethod.GET, "Requesting equalizer: " + id);
        eq = em.find(Equalizer.class, id);
        
        if(eq == null) {
            throw new EqualizerNotFoundException(id);
        }
        
        return eq;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllEqualizers() {
        LogUtils.restCall(LOG, "/rest/eq", RequestMethod.GET, "Deleting all equalizers");
        em.createQuery(DELETE_ALL_EQS_JPQL).executeUpdate();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEqualizer(@PathVariable("id") int id) {
        final StringBuilder jpql = new StringBuilder();
        final Query q;
        
        LogUtils.restCall(LOG, "/rest/eq/{id}", RequestMethod.GET, "Deleting equalizer: " + id);
        
        jpql.append("DELETE FROM ").append(Equalizer.class.getName()).append(" e ");
        jpql.append("WHERE e.id = :ID ");
        
        q = em.createQuery(jpql.toString());
        q.setParameter("ID", id);
        
        if(q.executeUpdate() == 0) {
            throw new EqualizerNotFoundException(id);
        }
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{id}", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer updateEqualizer(@PathVariable("id") int id, @RequestBody Equalizer eq) {
        LogUtils.restCall(LOG, "/rest/eq/{id}", RequestMethod.PUT, "Updating equalizer: " + eq);
        validateEqualizer(eq);
        
        eq.setID(id);
        if(em.find(Equalizer.class, id) == null) {
            throw new EqualizerNotFoundException(id);
        }
        
        em.merge(eq);
        
        return eq;
    }
    
    private void validateEqualizer(Equalizer eq) throws InvalidEqualizerException {
        final Collection<EqualizerNode> nodes;
        final int nodeCount;
        
        if(eq == null) {
            throw new InvalidEqualizerException("An equalizer must be specified.");
        }
        
        nodes = eq.getNodes();
        nodeCount = nodes == null ? 0 : nodes.size();
        if(nodeCount != 16) {
            throw new InvalidEqualizerException(String.format("An equalizer must have 16 nodes, not %s.", nodeCount));
        }
    }
}

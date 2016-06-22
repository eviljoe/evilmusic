/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
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

package em.controllers;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.eq.EqualizerDAO;
import em.dao.eq.EqualizerNotFoundException;
import em.dao.eq.InvalidEqualizerException;
import em.model.Equalizer;
import em.model.EqualizerNode;
import em.utils.EqualizerUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class EqualizerController {
    
    @Autowired
    EqualizerDAO eqDAO;
    
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
    @RequestMapping(value = "/rest/eq/default", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer getDefaultEqualizer() {
        Equalizer eq;
        
        try {
            eq = eqDAO.getFirst();
        } catch(EqualizerNotFoundException e) {
            eq = eqDAO.add(EqualizerUtils.createDefaultEqualizer());
        }
        
        return eq;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equalizer> getAllEqualizers() {
        return eqDAO.getAll();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer getEqualizer(@PathVariable("id") int id) {
        return eqDAO.get(id);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllEqualizers() {
        eqDAO.removeAll();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEqualizer(@PathVariable("id") int id) {
        eqDAO.remove(id);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{id}", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer updateEqualizer(@PathVariable("id") int id, @RequestBody Equalizer eq) {
        validateEqualizer(eq);
        
        eq.setID(id);
        eqDAO.exists(id, true); // Make sure the EQ exists
        eqDAO.save(eq);
        
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

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

package em.controllers;

import java.util.Set;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import em.dao.EqualizerDAO;
import em.model.Equalizer;
import em.utils.EMUtils;
import em.utils.EqualizerUtils;
import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class EqualizerController {
    
    private static final Logger LOG = Logger.getLogger(EqualizerController.class.getName());
    
    @Autowired
    private EqualizerDAO eqDAO;
    
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
        final Set<Equalizer> allEQs;
        final Equalizer eq;
        
        LogUtils.restCall(LOG, "/rest/eq/current", RequestMethod.GET, "Requesting equalizer, maybe create");
        
        allEQs = eqDAO.findAll();
        
        if(EMUtils.hasValues(allEQs)) {
            eq = allEQs.iterator().next();
        } else {
            eq = eqDAO.save(EqualizerUtils.createDefaultEqualizer());
        }
        
        return eq;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/eq/{eqID}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Equalizer getEqualizer(@PathVariable("eqID") int eqID) {
        LogUtils.restCall(LOG, "/rest/eq/{eqID}", RequestMethod.GET, "Requesting equalizer: " + eqID);
        return eqDAO.findByID(eqID);
    }
}

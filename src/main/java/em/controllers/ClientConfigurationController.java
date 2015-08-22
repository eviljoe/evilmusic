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

import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import em.dao.client.ClientConfigurationDAO;
import em.dao.client.ClientConfigurationNotFoundException;
import em.model.ClientConfiguration;
import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class ClientConfigurationController {
    
    private static final Logger LOG = Logger.getLogger(ClientConfigurationController.class.getName());
    private static final double DEFAULT_VOLUME = 100.0;
    
    @Autowired
    private ClientConfigurationDAO clientConfigDAO;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public ClientConfigurationController() {
        super();
    }
    
    /* ************** */
    /* REST Functions */
    /* ************** */
    
    @Transactional
    @RequestMapping(value = "/rest/config/volume", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public double getVolume() {
        LogUtils.restCall(LOG, "/rest/config/volume", RequestMethod.GET, "Requesting volume");
        return getConfig().getVolume();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/config/volume/{volume}", method = RequestMethod.PUT)
    @Produces(MediaType.APPLICATION_JSON)
    public double setVolume(@PathVariable("volume") double volume) {
        ClientConfiguration config;
        
        LogUtils.restCall(LOG, "/rest/config/volume/{volume}", RequestMethod.PUT, "Setting volume: " + volume);
        
        config = getConfig();
        config.setVolume(volume);
        clientConfigDAO.save(config);
        
        return volume;
    }
    
    private ClientConfiguration getConfig() {
        ClientConfiguration config;
        
        try {
            config = clientConfigDAO.getFirst();
        } catch(ClientConfigurationNotFoundException e) {
            config = createDefaultConfig();
        }
        
        return config;
    }
    
    private ClientConfiguration createDefaultConfig() {
        final ClientConfiguration config = new ClientConfiguration();
        
        config.setVolume(DEFAULT_VOLUME);
        return clientConfigDAO.save(config);
    }
}

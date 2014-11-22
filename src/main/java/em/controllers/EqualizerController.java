package em.controllers;

import java.util.Set;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import em.dao.EqualizerDAO;
import em.model.Equalizer;
import em.utils.EMUtils;
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
        
        LogUtils.createRESTCallEntry(LOG, "/rest/eq/current", RequestMethod.GET, "Requesting equalizer, maybe create");
        
        allEQs = eqDAO.findAll();
        
        if(EMUtils.hasValues(allEQs)) {
            eq = allEQs.iterator().next();
        } else {
            eq = eqDAO.save(new Equalizer());
        }
        
        return eq;
    }
}
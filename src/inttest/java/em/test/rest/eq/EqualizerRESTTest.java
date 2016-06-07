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
package em.test.rest.eq;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import em.controllers.EqualizerController;
import em.model.Equalizer;
import em.model.EqualizerNode;
import em.test.rest.calls.EqualizerRESTCalls;

/**
 * A class containing REST tests for {@link EqualizerController}.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EqualizerRESTTest {
    
    /* **************************** */
    /* Set Up / Tear Down Functions */
    /* **************************** */
    
    @After
    public void tearDown() {
        EqualizerRESTCalls.deleteAllEQs();
    }
    
    /* ***************** */
    /* Get Current Tests */
    /* ***************** */
    
    /** Ensures that an EQ will be created if one does not exist. */
    @Test
    public void testGetDefault_NoExisting() throws IOException {
        Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        
        assertThat(eq, is(not(nullValue())));
        assertThat(eq.getID(), is(greaterThan(0)));
    }
    
    /** Ensures that an existing EQ will be returned if one already exists. */
    @Test
    public void testGetDefault_OneExists() throws IOException {
        final Equalizer eq;
        
        EqualizerRESTCalls.getCurrentEQ();
        eq = EqualizerRESTCalls.getCurrentEQ();
        
        assertThat(eq, is(not(nullValue())));
        assertThat(eq.getID(), is(greaterThan(0)));
    }
    
    /* ************* */
    /* Get All Tests */
    /* ************* */
    
    /** Ensures that an empty list is returned when there are no equalizers. */
    @Test
    public void testGetAll_NoneExist() throws IOException {
        assertThat(EqualizerRESTCalls.getAllEQs(), is(empty()));
    }
    
    /** Ensures that a list containing the only EQ is returned when there is one EQ. */
    @Test
    public void testGetAll_OneExists() throws IOException {
        final List<Equalizer> eqs;
        
        EqualizerRESTCalls.getCurrentEQ();
        
        eqs = EqualizerRESTCalls.getAllEQs();
        assertThat(eqs, is(not(nullValue())));
        assertThat(eqs.size(), is(equalTo(1)));
    }
    
    /* ********* */
    /* Get Tests */
    /* ********* */
    
    /** Ensures that an existing EQ can be requested. */
    @Test
    public void testGet_Existing() throws IOException {
        final Equalizer eq1 = EqualizerRESTCalls.getCurrentEQ();
        final Equalizer eq2 = EqualizerRESTCalls.getEQ(eq1.getID());
        
        assertThat(eq2, is(not(nullValue())));
        assertThat(eq2.getID(), is(equalTo(eq1.getID())));
    }
    
    /** Ensures that HTTP 404 (not found) is returned when requesting an EQ that does not exist. */
    @Test
    public void testGet_DNE() throws IOException {
        EqualizerRESTCalls.getEQ(-17, 404);
    }
    
    /* **************** */
    /* Delete All Tests */
    /* **************** */
    
    /** Ensures that no error is thrown when deleting all EQs when none exist. */
    @Test
    public void testDeletAll_NoneExist() {
        EqualizerRESTCalls.deleteAllEQs();
    }
    
    /** Ensures that the only EQ will be deleted when deleting all EQs. */
    @Test
    public void testDeletAll_OneExists() throws IOException {
        final int id = EqualizerRESTCalls.getCurrentEQ().getID();
        
        EqualizerRESTCalls.deleteAllEQs();
        EqualizerRESTCalls.getEQ(id, 404);
    }
    
    /* ************ */
    /* Delete Tests */
    /* ************ */
    
    /** Ensures that an existing EQ can be deleted. */
    @Test
    public void testDelete_Existing() throws IOException {
        final int id = EqualizerRESTCalls.getCurrentEQ().getID();
        
        EqualizerRESTCalls.deleteEQ(id);
        EqualizerRESTCalls.getEQ(id, 404);
    }
    
    /** Ensures that HTTP 404 (not found) is returned when deleting an EQ that does not exist. */
    @Test
    public void testDelete_DNE() throws IOException {
        EqualizerRESTCalls.deleteEQ(-17, 404);
    }
    
    /* ************ */
    /* Update Tests */
    /* ************ */
    
    /** Ensures an EQ can be updated. */
    @Test
    public void testUpdate_Existing() throws IOException {
        Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        final int id = eq.getID();
        
        getEQNode(eq, 55).setGain(7.0);
        getEQNode(eq, 20000).setGain(-5.0);
        
        eq = EqualizerRESTCalls.updateEQ(eq);
        assertThat(getEQNode(eq, 55).getGain(), is(equalTo(7.0)));
        assertThat(getEQNode(eq, 20000).getGain(), is(equalTo(-5.0)));
        
        eq = EqualizerRESTCalls.getEQ(id);
        assertThat(getEQNode(eq, 55).getGain(), is(equalTo(7.0)));
        assertThat(getEQNode(eq, 20000).getGain(), is(equalTo(-5.0)));
    }
    
    /** Ensures that HTTP 404 (not found) is returned when updating an EQ that does not exist. */
    @Test
    public void testUpdate_DNE() throws IOException {
        final Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        
        eq.setID(-17);
        EqualizerRESTCalls.updateEQ(eq, 404);
    }
    
    /** Ensures that HTTP 400 (bad request) is returned when updating an EQ to have a <code>null</code> list of nodes. */
    @Test
    public void testUpdate_NullNodes() throws IOException {
        final Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        
        eq.setNodes(null);
        EqualizerRESTCalls.updateEQ(eq, 400);
    }
    
    /** Ensures that HTTP 400 (bad request) is returned when updating an EQ to have an empty list of nodes. */
    @Test
    public void testUpdate_NoNodes() throws IOException {
        final Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        
        eq.setNodes(new HashSet<EqualizerNode>());
        EqualizerRESTCalls.updateEQ(eq, 400);
    }
    
    /** Ensures that HTTP 400 (bad request) is returned when updating an EQ to have less than 16 nodes. */
    @Test
    public void testUpdate_NotEnoughNodes() throws IOException {
        final Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        final Iterator<EqualizerNode> it = eq.getNodes().iterator();
        
        it.next();
        it.remove();
        
        EqualizerRESTCalls.updateEQ(eq, 400);
    }
    
    /** Ensures that HTTP 400 (bad request) is returned when updating an EQ to have more than 16 nodes. */
    @Test
    public void testUpdate_TooManyNodes() throws IOException {
        final Equalizer eq = EqualizerRESTCalls.getCurrentEQ();
        
        eq.getNodes().add(new EqualizerNode());
        EqualizerRESTCalls.updateEQ(eq, 400);
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    private EqualizerNode getEQNode(Equalizer eq, int freq) {
        final Iterator<EqualizerNode> it = eq.getNodes().iterator();
        EqualizerNode node = null;
        
        while(it.hasNext() && node == null) {
            final EqualizerNode n = it.next();
            
            if(n.getFrequency() == freq) {
                node = n;
            }
        }
        
        return node;
    }
}

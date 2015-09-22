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

import angular from 'angular';
import emAlert from './components/alert';
import eq from './components/eq';
import evilmusic from './components/evilmusic';
import library from './components/library';
import player from './components/player';
import progressBar from './components/progress-bar';
import queue from './components/queue';
import resources from './components/resources';
import utils from './components/utils';
import volumeControl from './components/volume-control';

const NG_RESOURCE_MODULE_INJECT_ID = 'ngResource';
const UI_BOOTSTRAP_MODULE_INJECT_ID = 'ui.bootstrap';

class EvilMusicApp {
    constructor() {
        this.init();
    }
    
    static get injectID() {
        return 'EvilMusicApp';
    }
    
    init() {
        emAlert(this);
        eq(this);
        evilmusic(this);
        library(this);
        player(this);
        progressBar(this);
        queue(this);
        resources(this);
        utils(this);
        volumeControl(this);
    }

    createAngularModule() {
        return angular.module(EvilMusicApp.injectID, [
            NG_RESOURCE_MODULE_INJECT_ID,
            UI_BOOTSTRAP_MODULE_INJECT_ID
        ]);
    }

    getAngularModule() {
        if(!this.angularModule) {
            this.angularModule = this.createAngularModule();
        }
        
        return this.angularModule;
    }
    
    getInjectID(type, obj) {
        let injectID;
        
        if(obj.injectID) {
            let injectIDType = typeof obj.injectID;
            
            if(injectIDType === 'function') {
                injectID = obj.injectID();
            } else if(injectIDType === 'string') {
                injectID = obj.injectID;
            } else {
                throw new Error(type + ' injectID field is of an unsupported type: ' + injectIDType);
            }
        } else {
            throw new Error(type + ' has no injectID field');
        }
        
        return injectID;
    }
    
    /**
     * Registers an Angular item.
     *
     * @param  {string} type The name of the type of Angular item that is being registered (e.g. controller, directive)
     * @param  {Object} angularable The actual item that will be passed to Angular.
     * @param  {string} functionName The name of the Angular function that should be used to register the Angular item.
     *                            (Default: "type" param)
     *
     * @return {EvilMusicApp} Returns the EvilMusicApp
     */
    registerAngularItem(type, angularable, functionName) {
        let injectID;
        let functName = functionName || type;
        
        injectID = this.getInjectID(type, angularable);
        this.getAngularModule()[functName](injectID, angularable);
        
        return this;
    }

    /**
     * Creates a new Angular controller using the given controller class.
     *
     * @param {Object} Controller The controller class (not instance!) to be used to create the Angular controller.
     *
     * @return {EvilMusicApp} Returns the EvilMusicApp
     */
    controller(Controller) {
        return this.registerAngularItem('controller', Controller);
    }
    
    /**
     * Creates a new Angular directive using the given factory class.
     *
     * @param {DirectiveFactory} DirectiveFactory The factory class (not instance!) to be used to create the Angular
     *                                            directive.
     *
     * @return {EvilMusicApp} Returns the EvilMusicApp
     */
    directive(DirectiveFactory) {
        return this.registerAngularItem('directive', new DirectiveFactory().directive);
    }
    
    /**
     * Creates a new Angular filter using the given factory class.
     *
     * @param {FilterFactory} FilterFactory The factory class (not instance!) to be used to create the Angular filter.
     *
     * @return {EvilMusicApp} Returns the EvilMusicApp
     */
    filter(FilterFactory) {
        return this.registerAngularItem('filter', new FilterFactory().filter);
    }
    
    /**
     * Creates a new Angular resource using the given factory class.
     *
     * @param {ResourceFactory} ResourceFactory The factory class (not instance!) to be used to create the Angular
     *                                          resource.
     *
     * @return {EvilMusicApp} Returns the EvilMusicApp
     */
    resource(ResourceFactory) {
        return this.registerAngularItem('resource', new ResourceFactory().resource, 'factory');
    }
    
    /**
     * Creates a new Angular service using the given service class.
     *
     * @param {Object} Service The service class (not instance!) to be used to create the Angular service.
     *
     * @return {EvilMusicApp} Returns the EvilMusicApp
     */
    service(Service) {
        return this.registerAngularItem('service', Service);
    }
}

export default new EvilMusicApp();

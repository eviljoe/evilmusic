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
    
    registerAngularItem(type, functName, item) {
        let injectID;
        
        console.log('registering ' + type + '...');
        injectID = this.getInjectID(type, item);
        console.log('  > ' + injectID);
        
        this.getAngularModule()[functName](injectID, item);
        console.log('  > done!');
        
        return this;
    }

    controller(clazz) {
        return this.registerAngularItem('controller', 'controller', clazz);
    }
    
    directive(factory) {
        return this.registerAngularItem('directive', 'directive', factory);
    }
    
    filter(factory) {
        return this.registerAngularItem('filter', 'filter', factory);
    }
    
    resource(factory) {
        return this.registerAngularItem('resource', 'factory', factory);
    }
    
    service(clazz) {
        return this.registerAngularItem('service', 'service', clazz);
    }
}

export default new EvilMusicApp();

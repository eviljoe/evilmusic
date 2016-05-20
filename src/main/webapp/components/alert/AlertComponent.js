/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2015 Joe Falascino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import {Component} from '@angular/core';
import {Alerts} from 'services/Alerts';

export const ALERT_DIALOG_ELEMENT_ID = 'em-alert-dialog';

export class AlertComponent {
    constructor(alerts) {
        this.alerts = alerts;
        this.elemID = ALERT_DIALOG_ELEMENT_ID;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-alert',
            templateUrl: 'components/alert/alert.html'
        })];
    }
    
    static get parameters() {
        return [Alerts];
    }
    
    get title() {
        let info = this.alerts.getAlertInfo();
        
        return info ? info.title : null;
    }
    
    get message() {
        let info = this.alerts.getAlertInfo();
        
        return info ? info.message : null;
    }
    
    get debugInfo() {
        let info = this.alerts.getAlertInfo();
        
        return info ? info.debugInfo : null;
    }
    
    get buttons() {
        let info = this.alerts.getAlertInfo();
        
        return info ? info.buttons : null;
    }
    
    close() {
        this.modals.hide(ALERT_DIALOG_ELEMENT_ID);
    }
}

export default AlertComponent;

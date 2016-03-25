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

import AlertButton from './AlertButton';
import AlertController from './AlertController';

export default class Alerts {
    constructor($modal) {
        this.$modal = $modal;
    }
    
    static get $inject() {
        return ['$modal'];
    }
    
    static get injectID() {
        return 'alerts';
    }
    
    open(msg, title, debugInfo, size, buttons) {
        return this.$modal.open({
            templateUrl: 'components/alert/alert.html',
            controller: AlertController.injectID,
            controllerAs: 'ctrl',
            size,
            resolve: {
                buttons: () => buttons,
                debugInfo: () => debugInfo,
                message: () => msg,
                title: () => title
            }
        });
    }
    
    error(msg, data) {
        return this.open(msg, 'Error', data, 'md', [this.okButton()]);
    }
    
    okButton(primary=true, resolve=true) {
        return new AlertButton('ok', 'OK', null, primary, resolve);
    }
}

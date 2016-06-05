/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2016 Joe Falascino
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

import {Injectable} from '@angular/core';

export class Alerts {
    constructor() {
        this.modalElement = null;
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [];
    }
    
    getAlertInfo() {
        return this.alertInfo;
    }
    
    _show() {
        this.modalElement.show();
    }
    
    hide() {
        this.modalElement.hide();
    }
    
    error(msg, data) {
        this.alertInfo = new AlertInfo('Error', msg, data, [this.okButton()]);
        this._show();
    }
    
    okButton(primary=true, resolve=true) {
        return new AlertButton('ok', 'OK', null, primary, resolve);
    }
}

export class AlertInfo {
    constructor(title, message, debugInfo, buttons) {
        this.title = title;
        this.message = message;
        this.debugInfo = debugInfo;
        this.buttons = buttons;
    }
}

export class AlertButton {
    
    /**
     * Creates a new alert button.
     *
     * @param {string} id If this button is clicked, this string will be returned as the promise data when the modal
     *        promise is reslved.
     * @param {string} text The text to be displayed within the buttons
     * @param {string} icon The CSS classes necessary to add an icon to the button.  This should be a single string
     *        containing each CSS class delimited by a space (just how you would specify them in an HTML class
     *        attribute).
     * @param {boolean} primary Whether or not the button is the primary button on the modal.
     * @param {string} resolve If true, clicking the button will resolve the modal's result promise.  If false,
     *        clicking the button will reject the modal's result promise.
     */
    constructor(id, text, icon, primary, resolve) {
        this.id = id;
        this.text = text;
        this.icon = icon;
        this.primary = primary;
        this.resolve = resolve;
    }
}

export default Alerts;

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

export default class AlertController {
    constructor($modalInstance, buttons, debugInfo, message, title) {
        this.$modalInstance = $modalInstance;
        this.title = title;
        this.message = message;
        this.buttons = buttons;
        this.debugInfo = debugInfo;
    }
    
    static get $inject() {
        return ['$modalInstance', 'buttons', 'debugInfo', 'message', 'title'];
    }
    
    static get injectID() {
        return 'AlertController';
    }
    
    buttonClicked(button) {
        if(button.resolve) {
            this.$modalInstance.close(button.id);
        } else {
            this.$modalInstance.dismiss(button.id);
        }
    }
}

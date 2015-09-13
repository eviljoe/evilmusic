/*
 * Copyright (C) 2015 Joe Falascino
 * EvilMusic - Web-Based Music Player
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

export default class EQDialogController {
    constructor($modalInstance, equalizers) {
        this.$modalInstance = $modalInstance;
        this.equalizers = equalizers;
    }
    
    static get $inject() {
        return ['$modalInstance', 'equalizers'];
    }
    
    static get injectID() {
        return EQDialogController.name;
    }
    
    save() {
        this.equalizers.save().then(() => {
            this.$modalInstance.close('save');
        });
    }
    
    reset() {
        this.equalizers.reset();
    }
    
    close() {
        this.$modalInstance.dismiss('close');
    }
}

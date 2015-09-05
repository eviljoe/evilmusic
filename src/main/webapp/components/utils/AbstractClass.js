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

export default class Interface {
    constructor(interfaceFunctionNames) {
        if(Array.isArray(interfaceFunctionNames)) {
            this._checkForFunctions(interfaceFunctionNames);
        } else if(typeof interfaceFunctionNames === 'string') {
            this._checkForFunction(interfaceFunctionNames);
        }
    }
    
    _checkForFunction(name) {
        if(typeof this[name] !== 'function') {
            throw new Error(this.constructor.name + ' must define a "' + name + '()" function');
        }
    }
    
    _checkForFunctions(names) {
        for(let x = 0; x < names.length; x++) {
            this._checkForFunction(names[x]);
        }
    }
}

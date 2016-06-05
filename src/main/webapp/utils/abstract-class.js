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

import _ from 'lodash';

export class AbstractClass {
    constructor(abstractFunctionNames) {
        this._initAbstractClass(abstractFunctionNames);
    }
    
    _initAbstractClass(abstractFunctionNames) {
        if(Array.isArray(abstractFunctionNames)) {
            this._checkForAbstractFunctions(abstractFunctionNames);
        } else if(typeof abstractFunctionNames === 'string') {
            this._checkForAbstractFunction(abstractFunctionNames);
        }
    }
    
    _checkForAbstractFunction(abstractFunctionName) {
        if(typeof this[abstractFunctionName] !== 'function') {
            throw new Error('${this.constructor.name} must define a "${abstractFunctionName}()" function');
        }
    }
    
    _checkForAbstractFunctions(abstractFunctionNames) {
        _.forEach(abstractFunctionNames, (name) => this._checkForAbstractFunction(name));
    }
}

export default AbstractClass;

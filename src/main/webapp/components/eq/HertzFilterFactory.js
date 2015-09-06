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

import FilterFactory from 'components/utils/FilterFactory';

export default class HertzFilterFactory extends FilterFactory {
    constructor() {
        super();
        this.filter.injectID = 'hertz';
        this.filter.$inject = ['emUtils'];
    }
    
    filter(emUtils) {
        return (hertz) => HertzFilterFactory.hertzToString(emUtils, hertz);
    }
    
    static hertzToString(emUtils, hertz) {
        return emUtils.hertzToString(hertz);
    }
}
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

import EQButtonController from './eq-button/EQButtonController';
import EQButtonDirectiveFactory from './eq-button/EQButtonDirectiveFactory';
import EQController from './EQController';
import EQDialogController from './eq-dialog/EQDialogController';
import EQDirectiveFactory from './EQDirectiveFactory';
import Equalizers from './Equalizers';
import HertzFilterFactory from './HertzFilterFactory';

export default (emApp) => { // eslint-disable-line arrow-body-style
    return emApp
    .controller(EQButtonController)
    .directive(EQButtonDirectiveFactory)
    .controller(EQController)
    .controller(EQDialogController)
    .directive(EQDirectiveFactory)
    .service(Equalizers)
    .filter(HertzFilterFactory);
};

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

let emModule = angular.module('EvilMusicApp', ['ngResource', 'ui.bootstrap']);

eq(emModule);
evilmusic(emModule);
library(emModule);
player(emModule);
progressBar(emModule);
queue(emModule);
resources(emModule);
utils(emModule);
volumeControl(emModule);

export default emModule;

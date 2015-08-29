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

angular.module('EvilMusicApp')
.factory('player', ['$http', '$rootScope', 'emUtils', 'queues', 'equalizers',
function($http, $rootScope, emUtils, queues, equalizers) {

    'use strict';

    var that = this;

    that.playerProgressChangedEventName = 'emPlayerProgressChanged';
    that.avPlayer = null;
    that.currentSong = null;
    that.playerProgress = null;
    that.volume = 100;

    that.play = function(queueIndex) {
        if(that.avPlayer) {
            that.avPlayer.stop();
        }

        if(queues.q && queues.q.id) {
            var song = queues.getSong(queueIndex);

            if(song) {
                that.avPlayer = AV.Player.fromURL(
                    '/rest/queue/' + queues.q.id + '/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');

                that.updateAVPlayerDefaults(that.avPlayer, song);

                that.currentSong = song;
                that.avPlayer.play();
                queues.load();
            }
        }
    };

    that.updateAVPlayerDefaults = function(avPlayer, song) {
        if(avPlayer && song) {
            avPlayer.nodeCreationCallback = equalizers.createEQNodes;
            avPlayer.volume = that.volume;

            avPlayer.on('progress', function(progress) {
                that.playerProgress = progress / song.millis * 100;
                $rootScope.$broadcast(that.playerProgressChangedEventName);
            });

            avPlayer.on('end', function() {
                avPlayer = null;
                that.currentSong = null;
            });
        }
    };

    that.togglePlayback = function() {
        var toggled = false;

        if(that.avPlayer) {
            that.avPlayer.togglePlayback();
            toggled = true;
        }

        return toggled;
    };

    that.seekToMillis = function(millis) {
        var newMillis = null;

        if(emUtils.isNumber(millis) && that.avPlayer) {
            newMillis = that.avPlayer.seek(Math.max(0, millis));
        }

        return newMillis;
    };

    that.seekToPercent = function(percent) {
        var newMillis = null;

        if(emUtils.isNumber(percent) && that.avPlayer) {
            percent = Math.max(0, percent);
            percent = Math.min(1, percent);

            newMillis = that.seekToMillis(Math.floor(that.avPlayer.duration * percent));
        }

        return newMillis;
    };

    that.setVolume = function(volume) {
        if(emUtils.isNumber(volume)) {
            volume = Math.max(0, volume);
            volume = Math.min(100, volume);

            if(that.avPlayer) {
                that.avPlayer.volume = volume;
            }

            that.volume = volume;

            $http.put('/rest/config/volume/' + volume)
            .error(function (data, status, headers, config) {
                alert('Could not update volume.\n\n' + JSON.stringify(data));
            });
        }
    };

    that.loadVolume = function() {
        $http.get('/rest/config/volume')
        .success(function (data, status, headers, config) {
            that.volume = data;
        })
        .error(function (data, status, headers, config) {
            alert('Could not load volume.\n\n' + JSON.stringify(data));
        });
    };

    that.loadVolume();

    return that;
}]);

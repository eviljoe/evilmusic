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

export default class Players {
    constructor($http, $rootScope, emUtils, queues, equalizers) {
        this.$http = $http;
        this.$rootScope = $rootScope;
        this.emUtils = emUtils;
        this.queues = queues;
        this.equalizers = equalizers;
        
        this.playerProgressChangedEventName = 'emPlayerProgressChanged';
        this.avPlayer = null;
        this.currentSong = null;
        this.playerProgress = null;
        this.volume = 100;
        
        this.loadVolume();
    }
    
    static get $inject() {
        return ['$http', '$rootScope', 'emUtils', 'queues', 'equalizers'];
    }
    
    static get injectID() {
        return 'player'; // JOE TODO change to "players"
    }
    
    play(queueIndex) {
        if(this.avPlayer) {
            this.avPlayer.stop();
        }

        if(this.queues.q && this.queues.q.id) {
            let song = this.queues.getSong(queueIndex);

            if(song) {
                this.avPlayer = AV.Player.fromURL(
                    '/rest/queue/' + this.queues.q.id + '/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');

                this.updateAVPlayerDefaults(this.avPlayer, song);

                this.currentSong = song;
                this.avPlayer.play();
                this.queues.load();
            }
        }
    }

    updateAVPlayerDefaults(avPlayer, song) {
        if(avPlayer && song) {
            avPlayer.nodeCreationCallback = this.equalizers.createEQNodes;
            avPlayer.volume = this.volume;

            avPlayer.on('progress', (progress) => {
                this.playerProgress = progress / song.millis * 100;
                this.$rootScope.$broadcast(this.playerProgressChangedEventName);
            });

            avPlayer.on('end', () => {
                avPlayer = null;
                this.currentSong = null;
            });
        }
    }

    togglePlayback () {
        let toggled = false;

        if(this.avPlayer) {
            this.avPlayer.togglePlayback();
            toggled = true;
        }

        return toggled;
    }

    seekToMillis(millis) {
        let newMillis = null;

        if(this.emUtils.isNumber(millis) && this.avPlayer) {
            newMillis = this.avPlayer.seek(Math.max(0, millis));
        }

        return newMillis;
    }

    seekToPercent(percent) {
        let newMillis = null;

        if(this.emUtils.isNumber(percent) && this.avPlayer) {
            percent = Math.max(0, percent);
            percent = Math.min(1, percent);

            newMillis = this.seekToMillis(Math.floor(this.avPlayer.duration * percent));
        }

        return newMillis;
    }

    setVolume(volume) {
        if(this.emUtils.isNumber(volume)) {
            volume = Math.max(0, volume);
            volume = Math.min(100, volume);

            if(this.avPlayer) {
                this.avPlayer.volume = volume;
            }

            this.volume = volume;

            this.$http.put('/rest/config/volume/' + volume)
            .error((data, status, headers, config) => {
                alert('Could not update volume.\n\n' + JSON.stringify(data));
            });
        }
    }
    
    loadVolume() {
        this.$http.get('/rest/config/volume')
        .success((data, status, headers, config) => {
            this.volume = data;
        }).error((data, status, headers, config) => {
            alert('Could not load volume.\n\n' + JSON.stringify(data));
        });
    }
}

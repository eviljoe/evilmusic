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

import AV from 'av';

export default class Players {
    constructor($http, $rootScope, alerts, emUtils, queues, equalizers) {
        this.$http = $http;
        this.$rootScope = $rootScope;
        this.alerts = alerts;
        this.emUtils = emUtils;
        this.queues = queues;
        this.equalizers = equalizers;
        this.AV = AV;
        
        this.playerProgressChangedEventName = 'emPlayerProgressChanged';
        this.avPlayer = null;
        this.currentSong = null;
        this.playerProgress = null;
        this.volume = 100;
        
        this.loadVolume();
    }
    
    static get $inject() {
        return ['$http', '$rootScope', 'alerts', 'emUtils', 'queues', 'equalizers'];
    }
    
    static get injectID() {
        return 'players';
    }
    
    play(qIndex) {
        this.stop();
        
        if(this.queues.q && this.queues.q.id) {
            this.playSong(qIndex, this.queues.getSong(qIndex));
        }
    }
    
    playSong(qIndex, song) {
        this.stop();
        
        if(song && this.emUtils.isNumber(qIndex)) {
            this.avPlayer = this.createPlayer(qIndex);
            this.updateAVPlayerDefaults(this.avPlayer, song);
            this.currentSong = song;
            this.avPlayer.play();
            this.queues.load();
        }
    }
    
    createPlayer(qIndex) {
        return this.AV.Player.fromURL(
            `/rest/queue/${this.queues.q.id}/stream/queueindex/${qIndex}?updatePlayIndex=true`);
    }
    
    stop() {
        if(this.avPlayer) {
            this.avPlayer.stop();
        }
    }

    updateAVPlayerDefaults(avPlayer, song) {
        if(avPlayer && song) {
            avPlayer.nodeCreationCallback = this.equalizers.createEQNodes;
            avPlayer.volume = this.volume;
            avPlayer.on('progress', (progress) => this.playerProgressChanged(song, progress));
            avPlayer.on('end', () => this.playerSongEnded());
        }
    }
    
    playerProgressChanged(song, progress) {
        this.playerProgress = progress / song.millis * 100;
        this.$rootScope.$broadcast(this.playerProgressChangedEventName);
    }
    
    playerSongEnded() {
        this.avPlayer = null;
        this.currentSong = null;
    }

    togglePlayback() {
        let toggled = false;

        if(this.avPlayer) {
            this.avPlayer.togglePlayback();
            toggled = true;
        }

        return toggled;
    }

    seekToMillis(millis) {
        let newMillis = null;

        if(this.avPlayer && this.emUtils.isNumber(millis)) {
            newMillis = this.avPlayer.seek(Math.max(0, millis));
        }

        return newMillis;
    }

    seekToPercent(percent) {
        let newMillis = null;

        if(this.avPlayer && this.emUtils.isNumber(percent)) {
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
            this.putVolume();
        }
    }
    
    putVolume(volume) {
        this.$http.put('/rest/config/volume/' + volume).catch((response) => {
            this.alerts.error('Could not update volume.', response);
        });
    }
    
    loadVolume() {
        this.$http.get('/rest/config/volume').then((response) => {
            this.volume = response.data;
        }, (response) => {
            this.alerts.error('Could not load volume.', response);
        });
    }
}

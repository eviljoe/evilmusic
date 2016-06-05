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

import AV from 'av';
import {Injectable, EventEmitter} from '@angular/core';

import {Alerts} from './alerts';
import {EMUtils} from './emutils';
import {Equalizers} from './equalizers';
import {Queues} from './queues';
import {VolumeCalls} from './server-calls/volume-calls';

const MIN_VOLUME = 0;
const MAX_VOLUME = 100;

export class Players {
    constructor(alerts, emUtils, equalizers, queues, volumeCalls) {
        this.alerts = alerts;
        this.emUtils = emUtils;
        this.equalizers = equalizers;
        this.queues = queues;
        this.volumeCalls = volumeCalls;
        this.AV = AV;
        
        this.avPlayer = null;
        this.currentSong = null;
        this.playerProgress = null;
        this.playerProgressChanges = new EventEmitter();
        this.volume = MAX_VOLUME;
        
        this.loadVolume();
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Alerts], [EMUtils], [Equalizers], [Queues], [VolumeCalls]];
    }
    
    play(qIndex) {
        this.stop();
        
        if(this.queues.q && this.queues.q.id) {
            this.playSong(qIndex, this.queues.getSong(qIndex));
        }
    }
    
    playNext() {
        let nextQIndex = this.queues.getNextSongQueueIndex();
        
        if(nextQIndex > -1) {
            this.play(nextQIndex);
        }
    }
    
    playPrevious() {
        let prevQIndex = this.queues.getPreviousSongQueueIndex();
        
        if(prevQIndex > -1) {
            this.play(prevQIndex);
        }
    }
    
    playSong(qIndex, song) {
        this.stop();
        
        if(song && this.emUtils.isNumber(qIndex)) {
            this.avPlayer = this.createPlayer(qIndex);
            this.updateAVPlayerDefaults(this.avPlayer, song);
            this.currentSong = song;
            this.avPlayer.play();
            this.queues.setPlayIndex(qIndex);
        }
    }
    
    createPlayer(qIndex) {
        return this.AV.Player.fromURL(`/rest/queue/${this.queues.q.id}/queueindex/${qIndex}/stream`);
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
        this.playerProgress = progress / song.millis * 100;  // eslint-disable-line no-magic-numbers
        this.playerProgressChanges.emit(this.playerProgress);
    }
    
    playerSongEnded() {
        this.avPlayer = null;
        this.currentSong = null;
        this.playNext();
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
            volume = Math.max(MIN_VOLUME, volume);
            volume = Math.min(MAX_VOLUME, volume);

            if(this.avPlayer) {
                this.avPlayer.volume = volume;
            }

            this.volume = volume;
            this.putVolume(volume);
        }
    }
    
    putVolume(volume) {
        this.volumeCalls.save(volume).subscribe(
            null,
            (err) => this.alerts.error('Could not update volume.', err)
        );
    }
    
    loadVolume() {
        this.volumeCalls.get().subscribe(
            (volume) => this.volume = volume,
            (err) => this.alerts.error('Could not load volume.', err)
        );
    }
}

export default Players;

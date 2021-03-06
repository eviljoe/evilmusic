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

import {Observable} from 'rxjs';
import {Players} from 'services/players';

describe(Players.name, () => {
    let players;
    let _alerts;
    let _emUtils;
    let _equalizers;
    let _queues;
    let _volumeCalls;
    let _AV;
    let _avPlayer;
    
    beforeEach(() => {
        _alerts = {
            error() {}
        };
        
        _emUtils = {
            isNumber() {}
        };
        
        _equalizers = {
            createEQNodes() {}
        };
        
        _queues = {
            q: {},
            load() {},

            getNextSongQueueIndex() {},

            getPreviousSongQueueIndex() {},

            getSong() {},

            setPlayIndex() {}
        };
        
        _volumeCalls = {
            get() {
                return Observable.create((observer) => {
                    observer.next();
                    observer.complete();
                });
            },

            save() {}
        };
        
        _AV = {
            Player: {
                fromURL() {}
            }
        };
        
        _avPlayer = {
            on() {},

            play() {},

            seek() {},

            stop() {},

            togglePlayback() {}
        };
        
        players = new Players(_alerts, _emUtils, _equalizers, _queues, _volumeCalls);
        players.AV = _AV;
        players.avPlayer = _avPlayer;
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(Players.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(Players.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('play', () => {
        let songAtQIndex = null;
        
        beforeEach(() => {
            songAtQIndex = {a: 'A'};
            spyOn(players, 'playSong').and.stub();
            spyOn(players, 'stop').and.stub();
            spyOn(_queues, 'getSong').and.returnValue(songAtQIndex);
        });
        
        it('stops the currently playing song', () => {
            players.play(7);
            expect(players.stop).toHaveBeenCalled();
        });
        
        it('does not play a song if there is no queue', () => {
            players.queues = {};
            players.play(7);
            expect(players.playSong).not.toHaveBeenCalled();
        });
        
        it('does not play a song if the current queue has no ID', () => {
            players.queues.q = {};
            players.play(7);
            expect(players.playSong).not.toHaveBeenCalled();
        });
        
        it('plays the song at the given queue index when the current queue has an ID', () => {
            players.queues.q.id = 13;
            players.play(7);
            expect(_queues.getSong).toHaveBeenCalledWith(7);
            expect(players.playSong).toHaveBeenCalledWith(7, songAtQIndex);
        });
    });
    
    describe('playSong', () => {
        beforeEach(() => {
            players.avPlayer = null;
            spyOn(players, 'stop').and.stub();
            spyOn(_emUtils, 'isNumber').and.returnValue(true);
            spyOn(players, 'createPlayer').and.returnValue(_avPlayer);
            spyOn(players, 'updateAVPlayerDefaults').and.stub();
            spyOn(_avPlayer, 'play').and.stub();
            spyOn(_queues, 'setPlayIndex').and.stub();
        });
        
        it('stops the currently playing song', () => {
            players.playSong(7, {});
            expect(players.stop).toHaveBeenCalled();
        });
        
        it('does not create a player the given queue index is not a number', () => {
            _emUtils.isNumber.and.returnValue(false);
            players.playSong(null, {});
            expect(players.createPlayer).not.toHaveBeenCalled();
        });
        
        it('does not create a player if no song is given', () => {
            players.playSong(7, null);
            expect(players.createPlayer).not.toHaveBeenCalled();
        });
        
        it('creates a player from the given queue index', () => {
            players.playSong(7, {});
            expect(players.createPlayer).toHaveBeenCalledWith(7);
        });
        
        it('updates the player\'s defaults', () => {
            players.playSong(7, {});
            expect(players.updateAVPlayerDefaults).toHaveBeenCalled();
        });
        
        it('updates the currently playing song', () => {
            let song = {a: 'A'};
            
            players.playSong(7, song);
            expect(players.currentSong).toEqual(song);
        });
        
        it('plays the song', () => {
            players.playSong(7, {});
            expect(_avPlayer.play).toHaveBeenCalled();
        });
        
        it('sets the play index on the queue', () => {
            players.playSong(7, {});
            expect(_queues.setPlayIndex).toHaveBeenCalled();
        });
    });
    
    describe('createPlayer', () => {
        let createdPlayer = null;
        
        beforeEach(() => {
            createdPlayer = {a: 'A'};
            spyOn(_AV.Player, 'fromURL').and.returnValue(createdPlayer);
        });
        
        it('creates a player from a URL', () => {
            _queues.q.id = 7;
            players.createPlayer(13);
            expect(_AV.Player.fromURL).toHaveBeenCalledWith('/rest/queue/7/queueindex/13/stream');
        });
        
        it('returns the created player', () => {
            expect(players.createPlayer()).toBe(createdPlayer);
        });
    });
    
    describe('stop', () => {
        it('does nothing when there is no player', () => {
            players.avPlayer = null;
            players.stop(); // Make sure no errors are thrown
        });
        
        it('stops the player when one exists', () => {
            spyOn(_avPlayer, 'stop').and.stub();
            players.stop();
            expect(_avPlayer.stop).toHaveBeenCalled();
        });
    });
    
    describe('updateAVPlayerDefaults', () => {
        beforeEach(() => {
            spyOn(_avPlayer, 'on').and.stub();
        });
        
        it('does nothing when not given an AV player', () => {
            players.updateAVPlayerDefaults(null, null);
        });
        
        it('does nothing when not given a song', () => {
            players.volume = 17;
            _avPlayer.volume = null;
            
            players.updateAVPlayerDefaults(_avPlayer, null);
            expect(_avPlayer.volume).toBeNull();
        });
        
        it('adds a node creation callback to the AV player', () => {
            _avPlayer.nodeCreationCallback = null;
            players.updateAVPlayerDefaults(_avPlayer, {});
            expect(_avPlayer.nodeCreationCallback).toBe(_equalizers.createEQNodes);
        });
        
        it('sets the volume on the AV player', () => {
            _avPlayer.volume = null;
            players.volume = 17;
            
            players.updateAVPlayerDefaults(_avPlayer, {});
            expect(_avPlayer.volume).toEqual(17);
        });
        
        it('adds a listener that will be notified of AV player progress changes', () => {
            players.updateAVPlayerDefaults(_avPlayer, {});
            expect(_avPlayer.on).toHaveBeenCalledWith('progress', jasmine.any(Function));
        });
        
        it('adds a listener that will be notified of the AV player song ending', () => {
            players.updateAVPlayerDefaults(_avPlayer, {});
            expect(_avPlayer.on).toHaveBeenCalledWith('end', jasmine.any(Function));
        });
    });
    
    describe('playerProgressChanged', () => {
        let _playerProgressChanges;
        
        beforeEach(() => {
            _playerProgressChanges = jasmine.createSpyObj('playerProgressChanges', ['emit']);
            players.playerProgressChanges = _playerProgressChanges;
        });
        
        it('updates the player progress percent', () => {
            players.playerProgressChanged({millis: 1000}, 75);
            expect(players.playerProgress).toEqual(7.5);
        });
        
        it('fires a player progress changed event', () => {
            players.playerProgressChanged({millis: 100}, 75);
            expect(_playerProgressChanges.emit).toHaveBeenCalledWith(75);
        });
    });
    
    describe('playerSongEnded', () => {
        beforeEach(() => {
            spyOn(players, 'playNext').and.stub();
        });
        
        it('sets the AV Player to null', () => {
            players.avPlayer = {};
            players.playerSongEnded();
            expect(players.avPlayer).toBeNull();
        });
        
        it('sets current song to null', () => {
            players.currentSong = {};
            players.playerSongEnded();
            expect(players.currentSong).toBeNull();
        });
        
        it('plays the next song', () => {
            players.playerSongEnded();
            expect(players.playNext).toHaveBeenCalled();
        });
    });
    
    describe('togglePlayback', () => {
        it('does nothing and returns false when the AV player is null', () => {
            players.avPlayer = null;
            expect(players.togglePlayback()).toEqual(false);
        });
        
        it('toggles the playback and returns true when there is an AV player', () => {
            spyOn(_avPlayer, 'togglePlayback').and.stub();
            
            expect(players.togglePlayback()).toEqual(true);
            expect(_avPlayer.togglePlayback).toHaveBeenCalled();
        });
    });
    
    describe('seekToMillis', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'isNumber').and.returnValue(true);
            spyOn(_avPlayer, 'seek').and.callFake((m) => m);
        });
        
        it('does nothing and returns null when there is no AV player', () => {
            players.avPlayer = null;
            expect(players.seekToMillis(123)).toEqual(null);
        });
        
        it('does nothing and returns null when not given a number', () => {
            _emUtils.isNumber.and.returnValue(false);
            expect(players.seekToMillis('asdf')).toEqual(null);
        });
        
        it('seeks to the given milliseond when given a postitive value', () => {
            expect(players.seekToMillis(123)).toEqual(123);
            expect(_avPlayer.seek).toHaveBeenCalledWith(123);
        });
        
        it('seeks to 0 when given a negative value', () => {
            expect(players.seekToMillis(-123)).toEqual(0);
            expect(_avPlayer.seek).toHaveBeenCalledWith(0);
        });
    });
    
    describe('seekToPercent', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'isNumber').and.returnValue(true);
            spyOn(players, 'seekToMillis').and.callFake((m) => m);
            players.avPlayer.duration = 100;
        });
        
        it('does nothing and returns null when there is no AV player', () => {
            players.avPlayer = null;
            expect(players.seekToPercent(null)).toEqual(null);
        });
        
        it('does nothing and returns null when not given a number', () => {
            _emUtils.isNumber.and.returnValue(false);
            expect(players.seekToPercent('asdf')).toEqual(null);
        });
        
        it('seeks to zero when given a negative number', () => {
            expect(players.seekToPercent(-1)).toEqual(0);
            expect(players.seekToMillis).toHaveBeenCalledWith(0);
        });
        
        it('seeks to the duration when given a number greater than 1', () => {
            players.avPlayer.duration = 1000;
            
            expect(players.seekToPercent(2)).toEqual(1000);
            expect(players.seekToMillis).toHaveBeenCalledWith(1000);
        });
        
        it('seeks to given percent of the duration when given a number between 0 and 1', () => {
            players.avPlayer.duration = 1000;
            
            expect(players.seekToPercent(0.5)).toEqual(500);
            expect(players.seekToMillis).toHaveBeenCalledWith(500);
        });
    });
    
    describe('setVolume', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'isNumber').and.returnValue(true);
            spyOn(players, 'putVolume').and.stub();
        });
        
        it('does not set the volume when not given a number', () => {
            _emUtils.isNumber.and.returnValue(false);
            
            players.volume = 5;
            players.setVolume('asdf');
            expect(players.volume).toEqual(5);
        });
        
        it('sets the volume to 0 when given a negative number', () => {
            players.volume = null;
            players.setVolume(-5);
            expect(players.volume).toEqual(0);
        });
        
        it('sets the volume to 100 when given a number greater than 100', () => {
            players.volume = null;
            players.setVolume(500);
            expect(players.volume).toEqual(100);
        });
        
        it('sets the volume to the given value when given a number between 0 and 100', () => {
            players.volume = null;
            players.setVolume(17);
            expect(players.volume).toEqual(17);
        });
        
        it('sets the volume on the AV player if one exists', () => {
            players.avPlayer.volume = null;
            players.setVolume(17);
            expect(players.avPlayer.volume).toEqual(17);
        });
        
        it('does not set the volume on the AV player if one does not exist', () => {
            players.avPlayer = null;
            players.setVolume(17); // Make sure no error is thrown
        });
        
        it('sets the volume on the server', () => {
            players.setVolume(17);
            expect(players.putVolume).toHaveBeenCalled();
        });
    });
    
    describe('putVolume', () => {
        let saveObserver;
        
        beforeEach(() => {
            spyOn(_volumeCalls, 'save').and.returnValue(Observable.create((observer) => saveObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('saves the volume', () => {
            players.putVolume(17);
            expect(_volumeCalls.save).toHaveBeenCalledWith(17);
        });
        
        it('displays an error message when the volume save fails', () => {
            players.putVolume(19);
            saveObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('loadVolume', () => {
        let getObserver;
        
        beforeEach(() => {
            spyOn(_volumeCalls, 'get').and.returnValue(Observable.create((observer) => getObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('gets the volume', () => {
            players.loadVolume();
            expect(_volumeCalls.get).toHaveBeenCalled();
        });
        
        it('sets the volume when it is loaded successfully', () => {
            players.loadVolume();
            getObserver.next(123);
            getObserver.complete();
            expect(players.volume).toEqual(123);
        });
        
        it('displays an error message when the volume fails to load', () => {
            players.loadVolume();
            getObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('playNext', () => {
        beforeEach(() => {
            spyOn(_queues, 'getNextSongQueueIndex').and.returnValue(3);
            spyOn(players, 'play').and.stub();
        });
        
        it('plays the next song when the next song\'s queue index is greater than -1', () => {
            players.playNext();
            expect(players.play).toHaveBeenCalledWith(3);
        });
        
        it('does not play the next song when the next song\'s queue index is negative', () => {
            _queues.getNextSongQueueIndex.and.returnValue(-1);
            players.playNext();
            expect(players.play).not.toHaveBeenCalled();
        });
    });
    
    describe('playPrevious', () => {
        beforeEach(() => {
            spyOn(_queues, 'getPreviousSongQueueIndex').and.returnValue(3);
            spyOn(players, 'play').and.stub();
        });
        
        it('plays the previous song when the previous song\'s queue index is greater than -1', () => {
            players.playPrevious();
            expect(players.play).toHaveBeenCalledWith(3);
        });
        
        it('does not play the next song when the next song\'s queue index is negative', () => {
            _queues.getPreviousSongQueueIndex.and.returnValue(-1);
            players.playPrevious();
            expect(players.play).not.toHaveBeenCalled();
        });
    });
    
    describe('get currentSong', () => {
        it('returns the current song', () => {
            players._currentSong = 'foo';
            expect(players.currentSong).toEqual('foo');
        });
    });
    
    describe('set currentSong', () => {
        beforeEach(() => {
            players.currentSongChanges = jasmine.createSpyObj('currentSongChanges', ['emit']);
        });
        
        it('sets the current song', () => {
            players.currentSong = 'foo';
            expect(players._currentSong).toEqual('foo');
        });
        
        it('fires an event', () => {
            players.currentSong = 'foo';
            expect(players.currentSongChanges.emit).toHaveBeenCalled();
        });
    });
});

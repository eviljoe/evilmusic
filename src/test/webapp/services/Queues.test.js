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

import {Observable} from 'rxjs';
import {Queues} from 'services/Queues';

describe(Queues.name, () => {
    let queues;
    let _alerts;
    let _emUtils;
    let _queueCalls;
    
    beforeEach(() => {
        _alerts = {
            error() {}
        };

        _emUtils = {
            isNumber() {}
        };

        _queueCalls = {
            q: {},
            
            get() {
                return Observable.create((observer) => {
                    observer.next();
                    observer.complete();
                });
            },

            addLast() {},

            remove() {},

            clear() {},
            
            setPlayIndex() {}
        };
        
        queues = new Queues(_alerts, _emUtils, _queueCalls);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(Queues.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(Queues.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        beforeEach(() => {
            spyOn(queues, 'load').and.stub();
        });
        
        it('loads a new queue', () => {
            queues.init();
            expect(queues.load).toHaveBeenCalledWith(true);
        });
    });
    
    describe('load', () => {
        let getObserver;
        
        beforeEach(() => {
            queues.q = {};
            spyOn(_queueCalls, 'get').and.returnValue(Observable.create((observer) => getObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('loads a new queue when given true', () => {
            queues.load(true);
            expect(_queueCalls.get).toHaveBeenCalledWith('default');
        });
        
        it('reloads the existing queue when given false', () => {
            queues.q.id = 7;
            queues.load(false);
            expect(_queueCalls.get).toHaveBeenCalledWith(7);
        });
        
        it('reloads the existing queue when given undefined', () => {
            queues.q.id = 7;
            queues.load();
            expect(_queueCalls.get).toHaveBeenCalledWith(7);
        });
        
        it('sets the queue when it loads successfully', () => {
            queues.load(true);
            
            getObserver.next({foo: 'bar'});
            getObserver.complete();
            
            expect(queues.q).toEqual({foo: 'bar'});
        });
        
        it('displays an error message if the queue failed to load', () => {
            queues.load(true);
            getObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('addLast', () => {
        let addLastObserver;
        
        beforeEach(() => {
            queues.q = {id: 7};
            spyOn(_queueCalls, 'addLast').and.returnValue(Observable.create((observer) => addLastObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('adds the song with the given ID to the queue', () => {
            queues.addLast(123);
            expect(_queueCalls.addLast).toHaveBeenCalledWith(7, 123);
        });
        
        it('sets the queue when the song is added successfully', () => {
            queues.addLast(123);
            
            addLastObserver.next({foo: 'bar'});
            addLastObserver.complete();
            
            expect(queues.q).toEqual({foo: 'bar'});
        });
        
        it('displays an error message if the add failed', () => {
            queues.addLast(123);
            addLastObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('remove', () => {
        let removeObserver;
        
        beforeEach(() => {
            queues.q = {id: 7};
            spyOn(_queueCalls, 'remove').and.returnValue(Observable.create((observer) => removeObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('removes the song with the given ID from the queue', () => {
            queues.remove(123);
            expect(_queueCalls.remove).toHaveBeenCalledWith(7, 123);
        });
        
        it('sets the queue when the song is removed successfully', () => {
            queues.remove(123);
            
            removeObserver.next({foo: 'bar'});
            removeObserver.complete();
            
            expect(queues.q).toEqual({foo: 'bar'});
        });
        
        it('displays an error message if the remove failed', () => {
            queues.remove(123);
            removeObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('clear', () => {
        let clearObserver;
        
        beforeEach(() => {
            queues.q = {id: 7};
            spyOn(_queueCalls, 'clear').and.returnValue(Observable.create((observer) => clearObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('clears the queue', () => {
            queues.clear();
            expect(_queueCalls.clear).toHaveBeenCalledWith(7);
        });
        
        it('sets the queue when the it is cleared successfully', () => {
            queues.clear();
            
            clearObserver.next({foo: 'bar'});
            clearObserver.complete();
            
            expect(queues.q).toEqual({foo: 'bar'});
        });
        
        it('displays an error message if the clear failed', () => {
            queues.clear();
            clearObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('getSong', () => {
        let songIndex = null;
        
        beforeEach(() => {
            spyOn(_emUtils, 'isNumber').and.returnValue(true);
            songIndex = 7;
            queues.q = {
                elements: [{
                    queueIndex: songIndex,
                    song: {}
                }]
            };
        });
        
        it('returns null when given a value that is not a number', () => {
            _emUtils.isNumber.and.returnValue(false);
            expect(queues.getSong(songIndex)).toBeNull();
        });
        
        it('returns null when there is no queue', () => {
            queues.q = null;
            expect(queues.getSong(songIndex)).toBeNull();
        });
        
        it('returns null when the queue\'s elements field is undefined/null', () => {
            queues.q.elements = undefined;
            expect(queues.getSong(songIndex)).toBeNull();
            queues.q.elements = null;
            expect(queues.getSong(songIndex)).toBeNull();
        });
        
        it('returns null when the queue\'s elements field is empty', () => {
            queues.q.elements = [];
            expect(queues.getSong(songIndex)).toBeNull();
        });
        
        it('returns null when the queue does not have an element at the given index', () => {
            expect(queues.getSong(songIndex + 1)).toBeNull();
        });
        
        it('returns null when the queue element at the given index does not have a song', () => {
            queues.q.elements[0].song = undefined;
            expect(queues.getSong(songIndex)).toBeNull();
        });
        
        it('returns the song for the queue element at the given index', () => {
            queues.q.elements[0].song = {a: 'A'};
            expect(queues.getSong(songIndex)).toEqual({a: 'A'});
        });
    });
    
    describe('getNextSongQueueIndex', () => {
        beforeEach(() => {
            queues.q = {
                playIndex: 1,
                elements: [{}, {}, {}]
            };
        });
        
        it('returns the queues play index + 1', () => {
            expect(queues.getNextSongQueueIndex()).toEqual(2);
        });
        
        it('returns -1 when the next play index does not exist', () => {
            queues.q.playIndex = 2;
            expect(queues.getNextSongQueueIndex()).toEqual(-1);
        });
    });
    
    describe('getPreviousSongQueueIndex', () => {
        beforeEach(() => {
            queues.q = {
                playIndex: 1,
                elements: [{}, {}, {}]
            };
        });
        
        it('returns the queues play index - 1', () => {
            expect(queues.getPreviousSongQueueIndex()).toEqual(0);
        });
        
        it('returns -1 when the previous play index does not exist', () => {
            queues.q.playIndex = 0;
            expect(queues.getPreviousSongQueueIndex()).toEqual(-1);
        });
    });
    
    describe('setPlayIndex', () => {
        let setPlayIndexObserver;
        
        beforeEach(() => {
            queues.q = {id: 7};
            spyOn(_queueCalls, 'setPlayIndex').and.returnValue(Observable.create((observer) => {
                setPlayIndexObserver = observer;
            }));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('sets play index on the queue', () => {
            queues.setPlayIndex(123);
            expect(_queueCalls.setPlayIndex).toHaveBeenCalledWith(7, 123);
        });
        
        it('sets the queue when the play index is set successfully', () => {
            queues.setPlayIndex(123);
            
            setPlayIndexObserver.next({foo: 'bar'});
            setPlayIndexObserver.complete();
            
            expect(queues.q).toEqual({foo: 'bar'});
        });
        
        it('displays an error message if the play index change failed', () => {
            queues.setPlayIndex(123);
            setPlayIndexObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
});

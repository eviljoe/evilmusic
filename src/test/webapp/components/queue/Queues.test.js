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

import angular from 'angular';
import Queues from 'components/queue/Queues';

describe(Queues.name, () => {
    let queues = null;
    let _window = null;
    let _emUtils = null;
    let _Queue = null;
    let $q = null;
    let $rootScope = null;
    
    beforeEach(angular.mock.inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
        
        _window = {
            alert() {}
        };
        _emUtils = {
            isNumber() {}
        };
        _Queue = {
            q: {},
            get() {
                return {$promise: $q.defer().promise};
            }
        };
        
        queues = new Queues(_window, _emUtils, _Queue);
    }));
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(Queues.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(Queues.injectID).toEqual(jasmine.any(String));
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
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            spyOn(_Queue, 'get').and.returnValue({
                $promise: qDefer.promise
            });
            spyOn(_window, 'alert').and.stub();
        });
        
        it('loads a new queue when given true', () => {
            queues.load(true);
            expect(_Queue.get).toHaveBeenCalledWith({id: 'default'});
        });
        
        it('reloads the existing queu when given false', () => {
            queues.q.id = 7;
            queues.load(false);
            expect(_Queue.get).toHaveBeenCalledWith({id: 7});
        });
        
        it('reloads the existing queu when given undefined', () => {
            queues.q.id = 7;
            queues.load();
            expect(_Queue.get).toHaveBeenCalledWith({id: 7});
        });
        
        it('displays an error message if the queue failed to load', () => {
            queues.load(true);
            qDefer.reject();
            $rootScope.$apply();
            expect(_window.alert).toHaveBeenCalled();
        });
    });
    
    describe('addLast', () => {
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            queues.q = {$promise: qDefer.promise};
            spyOn(queues, 'addLastNow').and.stub();
        });
        
        it('adds the song with the given ID once the queue has been resolved', () => {
            queues.addLast(7);
            qDefer.resolve();
            $rootScope.$apply();
            expect(queues.addLastNow).toHaveBeenCalledWith(7);
        });
    });
    
    describe('addLastNow', () => {
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            queues.q = {
                $addLast() {}
            };
            spyOn(queues.q, '$addLast').and.returnValue(qDefer.promise);
            spyOn(_window, 'alert').and.stub();
        });
        
        it('adds the song with the given ID to the end of the queue', () => {
            queues.q.id = 13;
            queues.addLastNow(7);
            
            qDefer.resolve();
            $rootScope.$apply();
            
            expect(queues.q.$addLast).toHaveBeenCalledWith({id: 13, songIDs: 7});
        });
        
        it('displays an error message if the song could not be added to the queue', () => {
            queues.addLastNow(7);
            
            qDefer.reject();
            $rootScope.$apply();
            
            expect(_window.alert).toHaveBeenCalled();
        });
    });
    
    describe('remove', () => {
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            queues.q = {$promise: qDefer.promise};
            spyOn(queues, 'removeNow').and.stub();
        });
        
        it('removes the song with at the given queue index once the queue has been resolved', () => {
            queues.remove(7);
            qDefer.resolve();
            $rootScope.$apply();
            expect(queues.removeNow).toHaveBeenCalledWith(7);
        });
    });
    
    describe('removeNow', () => {
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            queues.q = {
                $remove() {}
            };
            spyOn(queues.q, '$remove').and.returnValue(qDefer.promise);
            spyOn(_window, 'alert').and.stub();
        });
        
        it('removes the song at the given queue index', () => {
            queues.q.id = 13;
            queues.removeNow(7);
            
            qDefer.resolve();
            $rootScope.$apply();
            
            expect(queues.q.$remove).toHaveBeenCalledWith({id: 13, qIndex: 7});
        });
        
        it('displays an error message if the song could not be removed from the queue', () => {
            queues.removeNow(7);
            
            qDefer.reject();
            $rootScope.$apply();
            
            expect(_window.alert).toHaveBeenCalled();
        });
    });
    
    describe('clear', () => {
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            queues.q = {$promise: qDefer.promise};
            spyOn(queues, 'clearNow').and.stub();
        });
        
        it('clears the queue once it has been resolved', () => {
            queues.clear();
            qDefer.resolve();
            $rootScope.$apply();
            expect(queues.clearNow).toHaveBeenCalled();
        });
    });
    
    describe('clearNow', () => {
        let qDefer = null;
        
        beforeEach(() => {
            qDefer = $q.defer();
            queues.q = {
                $clear() {}
            };
            spyOn(queues.q, '$clear').and.returnValue(qDefer.promise);
            spyOn(_window, 'alert').and.stub();
        });
        
        it('clears the queue', () => {
            queues.clearNow();
            
            qDefer.resolve();
            $rootScope.$apply();
            
            expect(queues.q.$clear).toHaveBeenCalled();
        });
        
        it('displays an error message if the queue could not be cleared', () => {
            queues.clearNow();
            
            qDefer.reject();
            $rootScope.$apply();
            
            expect(_window.alert).toHaveBeenCalled();
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
        
        it('returns null when the queue element at the given index does not have a song', function() {
            queues.q.elements[0].song = undefined;
            expect(queues.getSong(songIndex)).toBeNull();
        });
        
        it('returns the song for the queue element at the given index', () => {
            queues.q.elements[0].song = {a: 'A'};
            expect(queues.getSong(songIndex)).toEqual({a: 'A'});
        });
    });
});

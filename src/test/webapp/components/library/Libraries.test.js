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

import Libraries from 'components/library/Libraries';

describe(Libraries.name, () => {
    let libraries = null;
    let _window = null;
    let _queues = null;
    let _Library = null;
    let $q = null;
    let $rootScope = null;
    
    beforeEach(angular.mock.inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
        
        _window = {alert: () => {}};
        _queues = {load: () => {}};
        _Library = {
            get: () => {
                return {$promise: $q.defer().promise};
            }
        };
        
        libraries = new Libraries(_window, _queues, _Library);
    }));
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(Libraries.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(Libraries.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('init', () => {
        beforeEach(() => {
            spyOn(libraries, 'load').and.stub();
        });
        
        it('loads the library', () => {
            libraries.init();
            expect(libraries.load).toHaveBeenCalled();
        });
    });
    
    describe('load', () => {
        let libDefer = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            spyOn(_Library, 'get').and.returnValue({
                $promise: libDefer.promise
            });
        });
        
        it('creates and sets a library', () => {
            libraries.library = null;
            libraries.load();
            expect(libraries.library).toBeDefined();
            expect(libraries.library).not.toBeNull();
        });
        
        it('displays an error message if the library fails to load', () => {
            spyOn(_window, 'alert').and.stub();
            
            libraries.load();
            libDefer.reject();
            $rootScope.$apply();
            
            expect(_window.alert).toHaveBeenCalled();
        });
    });
    
    describe('clear', () => {
        let libDefer = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            libraries.library = {$promise: libDefer.promise};
            spyOn(libraries, 'clearNow').and.stub();
        });
        
        it('clears the library after it has been loaded', () => {
            libraries.clear();
            libDefer.resolve();
            $rootScope.$apply();
            expect(libraries.clearNow).toHaveBeenCalled();
        });
    });
    
    describe('clearNow', () => {
        let libDefer = null;
        let _library = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            _library = {$delete: () => {}};
            
            spyOn(_library, '$delete').and.returnValue(libDefer.promise);
            spyOn(libraries, 'load').and.stub();
            spyOn(_queues, 'load').and.stub();
            spyOn(_window, 'alert').and.stub();
            
            libraries.library = _library;
        });
        
        it('makes a call to delete the library', function() {
            libraries.clearNow();
            expect(_library.$delete).toHaveBeenCalled();
        });
        
        it('reloads the library if the library was successfully cleared', () => {
            libraries.clearNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(libraries.load).toHaveBeenCalled();
        });
        
        it('reloads the queue if the library was successfully cleared', () => {
            libraries.clearNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(_queues.load).toHaveBeenCalledWith(true);
        });
        
        it('displays an error message if the library failed to clear', () => {
            libraries.clearNow();
            
            libDefer.reject();
            $rootScope.$apply();
            
            expect(_window.alert).toHaveBeenCalled();
        });
    });
    
    describe('rebuild', () => {
        let libDefer = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            libraries.library = {$promise: libDefer.promise};
            spyOn(libraries, 'rebuildNow').and.stub();
        });
        
        it('rebuilds the library after it has been loaded', () => {
            libraries.rebuild();
            libDefer.resolve();
            $rootScope.$apply();
            expect(libraries.rebuildNow).toHaveBeenCalled();
        });
    });
    
    describe('rebuildNow', () => {
        let libDefer = null;
        let _library = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            _library = {$rebuild: () => {}};
            
            spyOn(_library, '$rebuild').and.returnValue(libDefer.promise);
            spyOn(libraries, 'load').and.stub();
            spyOn(_queues, 'load').and.stub();
            spyOn(_window, 'alert').and.stub();
            
            libraries.library = _library;
        });
        
        it('makes a call to rebuild the library', function() {
            libraries.rebuildNow();
            expect(_library.$rebuild).toHaveBeenCalled();
        });
        
        it('reloads the library if the library was successfully rebuilt', () => {
            libraries.rebuildNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(libraries.load).toHaveBeenCalled();
        });
        
        it('reloads the queue if the library was successfully rebuilt', () => {
            libraries.rebuildNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(_queues.load).toHaveBeenCalledWith(true);
        });
        
        it('displays an error message if the library failed to rebuild', () => {
            libraries.rebuildNow();
            
            libDefer.reject();
            $rootScope.$apply();
            
            expect(_window.alert).toHaveBeenCalled();
        });
    });
});

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

import {LibraryBreadcrumbComponent} from 'components/library/library-breadcrumb/library-breadcrumb.component';

describe(LibraryBreadcrumbComponent.name, () => {
    let comp;
    
    beforeEach(() => {
        comp = new LibraryBreadcrumbComponent();
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(LibraryBreadcrumbComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(LibraryBreadcrumbComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('hasArtist', () => {
        it('returns true when the artist is non-empty', () => {
            comp.artist = 'foo';
            expect(comp.hasArtist()).toEqual(true);
        });
        
        it('returns false when the artist is undefined', () => {
            comp.artist = undefined;
            expect(comp.hasArtist()).toEqual(false);
        });
        
        it('returns false when the artist is null', () => {
            comp.artist = null;
            expect(comp.hasArtist()).toEqual(false);
        });
        
        it('returns false when the artist is empty', () => {
            comp.artist = '';
            expect(comp.hasArtist()).toEqual(false);
        });
    });
    
    describe('hasAlbum', () => {
        it('returns true when the album is non-empty', () => {
            comp.artist = 'bar';
            expect(comp.hasAlbum()).toEqual(false);
        });
        
        it('returns false when the album is undefined', () => {
            comp.artist = undefined;
            expect(comp.hasAlbum()).toEqual(false);
        });
        
        it('returns false when the album is null', () => {
            comp.artist = null;
            expect(comp.hasAlbum()).toEqual(false);
        });
        
        it('returns false when the album is empty', () => {
            comp.artist = '';
            expect(comp.hasAlbum()).toEqual(false);
        });
    });
    
    describe('isLibraryActive', () => {
        beforeEach(() => {
            spyOn(comp, 'hasArtist').and.returnValue(false);
            spyOn(comp, 'hasAlbum').and.returnValue(false);
        });
        
        it('returns true when there is no artist or album', () => {
            expect(comp.isLibraryActive()).toEqual(true);
        });
        
        it('returns false when there is an artist', () => {
            comp.hasArtist.and.returnValue(true);
            expect(comp.isLibraryActive()).toEqual(false);
        });
        
        it('returns false when there is an album', () => {
            comp.hasAlbum.and.returnValue(true);
            expect(comp.isLibraryActive()).toEqual(false);
        });
        
        it('returns false when there is an artist and album', () => {
            comp.hasArtist.and.returnValue(true);
            comp.hasAlbum.and.returnValue(true);
            expect(comp.isLibraryActive()).toEqual(false);
        });
    });
    
    describe('isArtistActive', () => {
        beforeEach(() => {
            spyOn(comp, 'hasArtist').and.returnValue(false);
            spyOn(comp, 'hasAlbum').and.returnValue(false);
        });
        
        it('returns false when there is no artist or album', () => {
            expect(comp.isArtistActive()).toEqual(false);
        });
        
        it('returns true when there is is an artist', () => {
            comp.hasArtist.and.returnValue(true);
            expect(comp.isArtistActive()).toEqual(true);
        });
        
        it('returns false when there is is an album', () => {
            comp.hasAlbum.and.returnValue(true);
            expect(comp.isArtistActive()).toEqual(false);
        });
        
        it('returns false when there is an artist and album', () => {
            comp.hasArtist.and.returnValue(true);
            comp.hasAlbum.and.returnValue(true);
            expect(comp.isArtistActive()).toEqual(false);
        });
    });
    
    describe('isAlbumActive', () => {
        beforeEach(() => {
            spyOn(comp, 'hasArtist').and.returnValue(false);
            spyOn(comp, 'hasAlbum').and.returnValue(false);
        });
        
        it('returns false when there is no artist or album', () => {
            expect(comp.isAlbumActive()).toEqual(false);
        });
        
        it('returns false when there is is an artist', () => {
            comp.hasArtist.and.returnValue(true);
            expect(comp.isAlbumActive()).toEqual(false);
        });
        
        it('returns false when there is is an album', () => {
            comp.hasAlbum.and.returnValue(true);
            expect(comp.isAlbumActive()).toEqual(false);
        });
        
        it('returns true when there is an artist and album', () => {
            comp.hasArtist.and.returnValue(true);
            comp.hasAlbum.and.returnValue(true);
            expect(comp.isAlbumActive()).toEqual(true);
        });
    });
    
    describe('libraryClicked', () => {
        beforeEach(() => {
            comp.artistCleared = jasmine.createSpyObj('artistCleared', ['emit']);
        });
        
        it('emits an event', () => {
            comp.libraryClicked();
            expect(comp.artistCleared.emit).toHaveBeenCalled();
        });
    });
    
    describe('artistClicked', () => {
        beforeEach(() => {
            comp.albumCleared = jasmine.createSpyObj('albumCleared', ['emit']);
        });
        
        it('emits an event', () => {
            comp.artistClicked();
            expect(comp.albumCleared.emit).toHaveBeenCalled();
        });
    });
});

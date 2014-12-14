'use strict';

describe('player', function() {

    var $httpBackend;
    var player;
    var eq;
    var defaultVolume = '97';
    var defaultQueue = {
        id : 3,
        elements : [
            { playIndex : -1, queueIndex : 0, song : { id : 100, title : 'test song #1' }},
            { playIndex : 0, queueIndex : 1, song : { id : 101, title : 'test song #2' }},
            { playIndex : 1, queueIndex : 2, song : { id : 102, title : 'test song #3' }}
        ]

    };
    var defaultEQ = {};

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $httpBackend.when('GET', '/rest/queue/current').respond(defaultQueue);
        $httpBackend.when('GET', '/rest/eq/current').respond(defaultEQ);
        $httpBackend.expect('GET', '/rest/config/volume').respond(defaultVolume);
    }));

    beforeEach(inject(function(_player_, _eq_) {
        player = _player_;
        eq = _eq_;
        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    describe('construction', function() {
        it('loads the volume', function() {
            expect(player.volume).toEqual(defaultVolume);
        });
    });

    describe('play', function() {

        var defaultAVPlayer;

        beforeEach(function() {
            defaultAVPlayer = {
                stop : function() {},
                play : function() {},
                on : function() {}
            };

            player.avPlayer = defaultAVPlayer;

            spyOn(player.avPlayer, 'stop').and.callThrough();
            spyOn(player.avPlayer, 'play').and.callThrough();
            spyOn(AV.Player, 'fromURL').and.returnValue(defaultAVPlayer);
            spyOn(player, 'updateAVPlayerDefaults').and.callThrough();
        });

        it('stops the currently playing song', function() {
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);
            player.play(defaultQueue.elements[0].queueIndex);
            $httpBackend.flush();
            expect(player.avPlayer.stop).toHaveBeenCalled();
        });

        it('creates a new AV player from a REST URL', function() {
            var queueIndex = defaultQueue.elements[0].queueIndex;

            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);
            player.play(queueIndex);
            $httpBackend.flush();

            expect(AV.Player.fromURL).toHaveBeenCalledWith(
                '/rest/queue/' + defaultQueue.id + '/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');
        });

        it('Updates the new AV player with defaults', function() {
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);
            player.play(defaultQueue.elements[0].queueIndex);
            $httpBackend.flush();
            expect(player.updateAVPlayerDefaults).toHaveBeenCalled();
        });

        it('Updates the current song', function() {
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);
            player.play(defaultQueue.elements[0].queueIndex);
            $httpBackend.flush();
            expect(player.currentSong).toEqual(defaultQueue.elements[0].song);
        });

        it('plays the song using the AV player', function() {
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);
            player.play(defaultQueue.elements[0].queueIndex);
            $httpBackend.flush();
            expect(player.avPlayer.play).toHaveBeenCalled();
        });

        it('does not play a song when given an invalid queue index', function() {
            player.play(17);
            expect(player.avPlayer.stop).toHaveBeenCalled();
            expect(player.avPlayer.play).not.toHaveBeenCalled();
        });
    });

    describe('updateAVPlayerDefaults', function() {

        var defaultAVPlayer;
        var defaultSong;
        var eventNames;

        beforeEach(function() {
            eventNames = [];
            defaultAVPlayer = {
                on : function(eventName, callback) {
                    eventNames.push(eventName);
                }
            };
            defaultSong = { millis : 100 };
        });

        it('updates the given AV player', function() {
            player.updateAVPlayerDefaults(defaultAVPlayer, defaultSong);

            expect(defaultAVPlayer.nodeCreationCallback).toBe(eq.createEQNodes);
            expect(defaultAVPlayer.volume).toEqual(player.volume);
            expect(eventNames.indexOf('progress') > -1).toBe(true);
            expect(eventNames.indexOf('end') > -1).toBe(true);
        });

        it('does not update a null AV player', function() {
            player.updateAVPlayerDefaults(null, defaultSong);

            expect(defaultAVPlayer.nodeCreationCallback).toBeUndefined();
            expect(defaultAVPlayer.volume).toBeUndefined();
            expect(eventNames.indexOf('progress') > -1).toBe(false);
            expect(eventNames.indexOf('end') > -1).toBe(false);
        });

        it('does not update an undefined AV player', function() {
            player.updateAVPlayerDefaults(undefined, defaultSong);

            expect(defaultAVPlayer.nodeCreationCallback).toBeUndefined();
            expect(defaultAVPlayer.volume).toBeUndefined();
            expect(eventNames.indexOf('progress') > -1).toBe(false);
            expect(eventNames.indexOf('end') > -1).toBe(false);
        });

        it('does not update an AV player when given a null song', function() {
            player.updateAVPlayerDefaults(defaultAVPlayer, null);

            expect(defaultAVPlayer.nodeCreationCallback).toBeUndefined();
            expect(defaultAVPlayer.volume).toBeUndefined();
            expect(eventNames.indexOf('progress') > -1).toBe(false);
            expect(eventNames.indexOf('end') > -1).toBe(false);
        });

        it('does not update an AV player when given an undefined song', function() {
            player.updateAVPlayerDefaults(defaultAVPlayer, undefined);

            expect(defaultAVPlayer.nodeCreationCallback).toBeUndefined();
            expect(defaultAVPlayer.volume).toBeUndefined();
            expect(eventNames.indexOf('progress') > -1).toBe(false);
            expect(eventNames.indexOf('end') > -1).toBe(false);
        });
    });

    describe('togglePlayback', function() {

        beforeEach(function() {
            player.avPlayer = { togglePlayback : function()  {} };
            spyOn(player.avPlayer, 'togglePlayback').and.callThrough();
        });

        it('toggles playback', function() {
            player.togglePlayback();
            expect(player.avPlayer.togglePlayback).toHaveBeenCalled();
        });
    });

    describe('seekToMillis', function() {

        beforeEach(function() {
            var defaultAVPlayer = {
                seek : function(ms)  {
                    return ms;
                }
            };

            player.avPlayer = defaultAVPlayer;
            spyOn(player.avPlayer, 'seek').and.callThrough();
        });

        it('to seek to the given point when given a positive number', function() {
            var millis = 17;

            expect(player.seekToMillis(millis)).toEqual(millis);
            expect(player.avPlayer.seek).toHaveBeenCalledWith(millis);
        });

        it('to seek to zero when given zero', function() {
            var millis = 0;

            expect(player.seekToMillis(millis)).toEqual(millis);
            expect(player.avPlayer.seek).toHaveBeenCalledWith(millis);
        });

        it('to seek to zero when given a negative number', function() {
            var millis = -17;
            var expectedMillis = 0;

            expect(player.seekToMillis(millis)).toEqual(expectedMillis);
            expect(player.avPlayer.seek).toHaveBeenCalledWith(expectedMillis);
        });

        it('to do nothing when given a null value', function() {
            expect(player.seekToMillis(null)).toBeNull();
            expect(player.avPlayer.seek).not.toHaveBeenCalled();
        });

        it('to do nothing when given an undefined value', function() {
            expect(player.seekToMillis(undefined)).toBeNull();
            expect(player.avPlayer.seek).not.toHaveBeenCalled();
        });

        it('to do nothing when given a value that is NaN', function() {
            expect(player.seekToMillis('hello world')).toBeNull();
            expect(player.avPlayer.seek).not.toHaveBeenCalled();
        });
    });

    describe('seekToPercent', function() {

        var defaultDuration = 100;

        beforeEach(function() {
            player.avPlayer = { duration : defaultDuration };
            spyOn(player, 'seekToMillis').and.callFake(function(millis) {
                return millis;
            });
        });

        it('to seek to the given percent when given a positive number between zero and one', function() {
            var percent = 0.25;

            player.seekToPercent(percent);
            expect(player.seekToMillis).toHaveBeenCalledWith(defaultDuration * percent);
        });

        it('to seek to 0% when given zero', function() {
            var percent = 0;

            player.seekToPercent(percent);
            expect(player.seekToMillis).toHaveBeenCalledWith(defaultDuration * percent);
        });

        it('to seek to 0% when a negative value', function() {
            var percent = -17;
            var expectedPercent = 0;

            player.seekToPercent(percent);
            expect(player.seekToMillis).toHaveBeenCalledWith(defaultDuration * expectedPercent);
        });

        it('to seek to 100% when given one', function() {
            var percent = 1;

            player.seekToPercent(percent);
            expect(player.seekToMillis).toHaveBeenCalledWith(defaultDuration * percent);
        });

        it('to seek to 100% when given a value greater than one', function() {
            var percent = 17;
            var expectedPercent = 1;

            player.seekToPercent(percent);
            expect(player.seekToMillis).toHaveBeenCalledWith(defaultDuration * expectedPercent);
        });

        it('to do nothing when given null', function() {
            player.seekToPercent(null);
            expect(player.seekToMillis).not.toHaveBeenCalled();
        });

        it('to do nothing when given undefined', function() {
            player.seekToPercent(undefined);
            expect(player.seekToMillis).not.toHaveBeenCalled();
        });

        it('to do nothing when given a value that is NaN', function() {
            player.seekToPercent('hello world');
            expect(player.seekToMillis).not.toHaveBeenCalled();
        });
    });

    describe('setVolume', function() {
        it('sets the volume when given a positive number (less than 100)', function() {
            var newVolume = 17;

            $httpBackend.expect('PUT', '/rest/config/volume/' + newVolume).respond('' + newVolume);
            player.setVolume(newVolume);
            $httpBackend.flush();
            expect(player.volume).toEqual(newVolume);
        });

        it('sets the volume to 100 when given a number greater than 100', function() {
            var expectedVolume = 100;

            $httpBackend.expect('PUT', '/rest/config/volume/' + expectedVolume).respond('' + expectedVolume);
            player.setVolume(117);
            $httpBackend.flush();
            expect(player.volume).toEqual(expectedVolume);
        });

        it('sets the volume to zero when given zero', function() {
            var newVolume = 0;

            $httpBackend.expect('PUT', '/rest/config/volume/' + newVolume).respond('' + newVolume);
            player.setVolume(newVolume);
            $httpBackend.flush();
            expect(player.volume).toEqual(newVolume);
        });

        it('sets the volume to zero when given a negative number', function() {
            var expectedVolume = 0;

            $httpBackend.expect('PUT', '/rest/config/volume/' + expectedVolume).respond('' + expectedVolume);
            player.setVolume(-17);
            $httpBackend.flush();
            expect(player.volume).toEqual(expectedVolume);
        });

        it('does not update the volume when given null', function() {
            var oldVolume = player.volume;

            player.setVolume(null);
            expect(player.volume).toEqual(oldVolume);
        });

        it('does not update the volume when given undefined', function() {
            var oldVolume = player.volume;

            player.setVolume(undefined);
            expect(player.volume).toEqual(oldVolume);
        });

        it('does not update the volume when given a value that is NaN', function() {
            var oldVolume = player.volume;

            player.setVolume('hello world');
            expect(player.volume).toEqual(oldVolume);
        });
    });
});

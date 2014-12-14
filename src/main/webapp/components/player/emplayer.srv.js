'use strict';

angular.module('EvilMusicApp')
.factory('player', ['$http', 'emUtils', 'queue', 'eq', function($http, emUtils, queue, eq) {

    var that = this;

    that.avPlayer = null;
    that.currentSong = null;
    that.playerProgress = null;
    that.volume = 100;

    that.play = function(queueIndex) {
        if(that.avPlayer) {
            that.avPlayer.stop();
        }

        if(queue.q && queue.q.id) {
            var song = queue.getSong(queueIndex);

            if(song) {
                that.avPlayer = AV.Player.fromURL(
                    '/rest/queue/' + queue.q.id + '/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');

                that.updateAVPlayerDefaults(that.avPlayer, song);

                that.currentSong = song;
                that.avPlayer.play();
                queue.load();
            }
        }
    };

    that.updateAVPlayerDefaults = function(avPlayer, song) {
        if(avPlayer && song) {
            avPlayer.nodeCreationCallback = eq.createEQNodes;
            avPlayer.volume = that.volume;

            avPlayer.on('progress', function(progress) {
                that.playerProgress = progress / song.millis * 100;
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

/**
 * Created by Souleymane on 04/11/2017.
 */
angular
    .module('deezToSpot', ['spotify'])
    .config(function (SpotifyProvider) {
        SpotifyProvider.setClientId('2c7ac924a805414aac4cc62d009ffaa0');
        SpotifyProvider.setRedirectUri('http://localhost:9000/channel');
        SpotifyProvider.setScope('playlist-read-private');
    })
    .controller('MainController', ['$scope', 'Spotify', function ($scope, Spotify) {

        $scope.searchArtist = function () {
            Spotify.search($scope.searchartist, 'artist').then(function (data) {
                $scope.artists = data.artists.items;
            });
        };

        $scope.login = function () {
            console.log("yes t la")
            Spotify.login().then(function (data) {
                console.log(data);
                alert("You are now logged in");
            }, function () {
                console.log('didn\'t log in');
            })
        };
    }]);
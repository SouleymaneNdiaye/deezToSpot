/**
 * Created by Souleymane on 04/11/2017.
 */
var app = angular.module('app', []);
app.controller("deezToSpot",['$scope',function($scope) {

    $scope.test=null;
    $scope.albums=[];
    $scope.userId=undefined;
    $scope.searchAlbum=undefined;
    $scope.userTest=undefined;
    $scope.step=0;
    $scope.user={
        display_name: undefined,
        email: undefined,
        external_urls:
            {spotify: undefined},
        followers: {href: undefined, total: undefined},
        href: undefined,
        type:undefined,
        uri: undefined
    };

    function getUperOffset(){
        if($scope.albums.length==25){
            $scope.step=$scope.step+1;
        }
        return $scope.step*20;
    }
    function getLowerOffset(){
        if($scope.step>0){
            $scope.step=$scope.step-1;
        }
        return $scope.step*20;
    }

    function login(callback) {

        var CLIENT_ID = '0ad02084b78a4e379a6b935c898a0849';
        var REDIRECT_URI = 'http://localhost:9000/viewSpot/';

        function getLoginURL(scopes) {
            return 'https://accounts.spotify.com/authorize?client_id=' + CLIENT_ID +
                '&redirect_uri=' + encodeURIComponent(REDIRECT_URI) +
                '&scope=' + encodeURIComponent(scopes.join(' ')) +
                '&response_type=token';
        }

        var url = getLoginURL([
            'user-read-email','user-library-read','user-library-modify'
        ]);

        var width = 450,
            height = 730,
            left = (screen.width / 2) - (width / 2),
            top = (screen.height / 2) - (height / 2);

        window.addEventListener("message", function (event) {
            var hash = JSON.parse(event.data);
            if (hash.type == 'access_token') {
                callback(hash.access_token);
            }
        }, false);

        var w = window.open(url);

    }


    $('#display_user').click(function () {
        console.log($scope.user);

    });
    function getUserData(accessToken) {
        return $.ajax({
            url: 'https://api.spotify.com/v1/me',
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }

        });
    }
    function getUserAlbums(accessToken,offset) {
        return $.ajax({
            url: 'https://api.spotify.com/v1/me/albums?limit=25&offset='+offset,
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }

        });
    }
    function getSearchAlbum(accessToken,queryAlbum){
            return $.ajax({
                url: 'https://api.spotify.com/v1/search?q='+queryAlbum+'&type=album&limit=1',
                headers: {
                    'Authorization': 'Bearer ' + accessToken
                }

            });
    }
    function getSingleAlbum(accessToken,href) {
        return $.ajax({
            url: 'https://api.spotify.com/v1/search?q='+query+'&type=album&limit=1',
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }

        });
    }
    loginButton = document.getElementById('btn-login');

    loginButton.addEventListener('click', function () {
        login(function (accessToken) {
            getUserData(accessToken)
                .then(function (response) {
                    loginButton.style.display = 'none';
                    var x =JSON.parse(JSON.stringify(response));
                    $scope.user=x;
                    $scope.$apply()
                });
        });
    });

    function bindUser(response) {
        $scope.user.display_name=response.display_name;
        $scope.user.email=response.email;
        $scope.test=response;
        $scope.userId=response.id;

    }

    getSearchAlbumBtn=document.getElementById('get-search-album');
    getSearchAlbumBtn.addEventListener('click', function () {
        var pathArray=window.location.href.split('=');
        var accessToken=pathArray[1].split('&')[0];
        var queryArtist=$('#search-query-artist').val();
        if((queryArtist!=null && queryArtist!='undefined')) {
            getSearchAlbum(accessToken, queryArtist)
                .then(function (response) {
                    loginButton.style.display = 'none';
                    var x = JSON.parse(JSON.stringify(response.albums));

                    $scope.searchAlbum = x;
                    $scope.$apply();
                    console.log($scope.searchAlbum);
                });
        }
    });
    getUser = document.getElementById('get_user');

    getUser.addEventListener('click', function () {
        var pathArray=window.location.href.split('=');
        var accessToken=pathArray[1].split('&')[0];

        getUserData(accessToken)
            .then(function (response) {
                loginButton.style.display = 'none';
                var x =JSON.parse(JSON.stringify(response));

                $scope.user=x;
                $scope.$apply();
            });

    });

    getUserAlbumsBtn = document.getElementById('get_albums');

    getUserAlbumsBtn.addEventListener('click', function () {
        var pathArray=window.location.href.split('=');
        var accessToken=pathArray[1].split('&')[0];

        getUserAlbums(accessToken,0)
            .then(function (response) {
                loginButton.style.display = 'none';
                var x =JSON.parse(JSON.stringify(response));
                $scope.albums=x.items;
                $scope.$apply();
            });

    });
    getNextAlbums = document.getElementById('nextStep');

    getNextAlbums.addEventListener('click', function () {
        var pathArray=window.location.href.split('=');
        var accessToken=pathArray[1].split('&')[0];
        var offset=getUperOffset();

        getUserAlbums(accessToken,offset)
            .then(function (response) {
                loginButton.style.display = 'none';
                var x =JSON.parse(JSON.stringify(response));
                $scope.albums=x.items;
                $scope.$apply();
            });

    });
    getLastAlbums = document.getElementById('lastStep');

    getLastAlbums.addEventListener('click', function () {
        var pathArray=window.location.href.split('=');
        var accessToken=pathArray[1].split('&')[0];
        var offset=getLowerOffset();

        getUserAlbums(accessToken,offset)
            .then(function (response) {
                loginButton.style.display = 'none';
                var x =JSON.parse(JSON.stringify(response));

                $scope.albums=x.items;
                $scope.$apply();
            });

    });



}]);


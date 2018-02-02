/**
 * Created by Souleymane on 04/11/2017.
 */
var deezToSpot = angular.module('deezToSpot', []);
deezToSpot.controller("deezToSpot",['$scope',function($scope,$http) {

        $scope.userId=undefined;
        $scope.userTest=undefined;
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
        'user-read-email'
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

function getUserData(accessToken) {
    return $.ajax({
        url: 'https://api.spotify.com/v1/me',
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
            });
    });
});
        getUser = document.getElementById('get_user');

        getUser.addEventListener('click', function () {
    var pathArray=window.location.href.split('=');
    var accessToken=pathArray[1].split('&')[0];

            getUserData(accessToken)
                .then(function (response) {
                    loginButton.style.display = 'none';
                    $scope.user=$.parseJSON(JSON.stringify(response));
                    $scope.userTest=$.parseJSON(JSON.stringify(response));
                    $scope.userId=response.id;
                    console.log($scope.user);
                });

    });


}]);

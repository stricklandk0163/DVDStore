var app = angular.module('dvdStore', ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    //Search bar
    .when("/search", {
        templateUrl : "search.html",
        controller : "SearchCtrl"
    })
    //Ad Listing
    .when("/adList/:query", {
        templateUrl : "adList.html",
        controller : "AdListCtrl"
    })
    //Detailed ad viewing
    .when("/viewAd", {
        templateUrl : "viewAd.html"
    })
    .otherwise({
    	redirectTo : "/search"
    });
});

/*
 * Controller for the ad listing page (generated by searching)
 */
app.controller("AdListCtrl", function($scope, $http, $routeParams) {
	$http.post("/queryForList",$routeParams.query)
		.then(function(response) {
	    $scope.welcome = response.data;
	});
	alert($routeParams.query)
});
/*
 * Controller for the search page
 */
app.controller("SearchCtrl", function($scope, $location){
	$scope.search = function(searchVal) {
		$location.path( "/adList/" + searchVal );
	}
});
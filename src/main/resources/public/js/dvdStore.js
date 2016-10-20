var app = angular.module('dvdStore', ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    //Search bar
    .when("/search", {
    	templateUrl : "search.html",
        controller : "SearchCtrl"
    })
    //Ad Listing
    .when("/viewAd/:id", {
        templateUrl : "viewAd.html",
        controller : "ViewAdCtrl"
    })
    .otherwise({
    	redirectTo : "/search"
    });
});

app.controller("ViewAdCtrl", function($scope){
	
});


/*
 * Controller for the search page
 */
app.controller("SearchCtrl", function($scope, $http){
	//Gather all search info and perform search
	$scope.search = function() {
		$http({
			method: 'POST',
			url: '/queryForList',
			data: {
				"titleQuery": $scope.searchVal,
				"genreQuery": $scope.selectedGenres,
				"ratingQuery": $scope.selectedRatings,
				"locationQuery": {
					"zipCode" : $scope.zipCode,
					"radius" : $scope.radius
				}
			}
		}).then(function successCallback(response) {
		    $scope.ads = response.data;
		}, function errorCallback(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		});
	};
	
	// Logic for toggling genre filters
	$scope.toggleGenre = function(genreName) {
		var idx = $scope.selectedGenres.indexOf(genreName);

		// is currently selected
		if (idx > -1) {
			$scope.selectedGenres.splice(idx, 1);
		}

		// is newly selected
		else {
			$scope.selectedGenres.push(genreName);
		}
		console.log($scope.selectedGenres);
	};
	
	// Logic for toggling rating filters
	$scope.toggleRating = function(ratingName) {
		var idx = $scope.selectedRatings.indexOf(ratingName);

		// is currently selected
		if (idx > -1) {
			$scope.selectedRatings.splice(idx, 1);
		}

		// is newly selected
		else {
			$scope.selectedRatings.push(ratingName);
		}
	};
	
	//Get all possible ratings
	$scope.getRatings = function(){
		$http({
			method: 'GET',
			url: '/ratings',
		}).then(function successCallback(response) {
		    $scope.ratings = response.data;
		}, function errorCallback(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		});
	};
	
	//Get all possible genres
	$scope.getGenres = function(){
		$http({
			method: 'GET',
			url: '/genres',
		}).then(function successCallback(response) {
		    $scope.genres = response.data;
		}, function errorCallback(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		});
	};
	
	//Init page
	$scope.ads = [];
	$scope.searchVal = "";
	$scope.ratings = [];
	$scope.genres = [];
	$scope.selectedRatings = [];
	$scope.selectedGenres = [];
	$scope.zipCode = "";
	$scope.radius = "";
	$scope.search();
	$scope.getRatings();
	$scope.getGenres();
});
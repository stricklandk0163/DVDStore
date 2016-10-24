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
        templateUrl : "viewItem.html",
        controller : "ViewAdCtrl"
    })
    .otherwise({
    	redirectTo : "/search"
    });
});

//Controller to view an individual advertisement
app.controller("ViewAdCtrl", function($scope,$routeParams,$http, $location){
	$scope.ad = {
		"title" : "",
		"genre" : "",
		"imdbLink" : "",
		"sellerName" : "",
		"contact" : "",
		"zipCode" : 0,
		"rating" : "",
		"price" : 0,
		"plot" : "",
		"imdbScore" : "",
		"imdbPoster" : "",
		"actors" : "",
		"year" : "",
		"imdbTitle" : ""
	}
	//Get an advertisement by id
	$http({
		method: 'POST',
		url: '/getAdById',
		data: $routeParams.id
	}).then(function successCallback(response) {
		$scope.ad.title = response.data.title;
		$scope.ad.genre = response.data.genre.toString().replace(',', ', ');
		$scope.ad.imdbLink = response.data.imdb;
		$scope.ad.sellerName = response.data.sellerName;
		$scope.ad.contact = response.data.contact;
		$scope.ad.zipCode = response.data.zipCode;
		$scope.ad.rating = response.data.rating;
		$scope.ad.price = response.data.price;
		
	    //Get additional information from imdb
		var route = response.data.imdb.split("/");
		var imbdId = route[route.length-1]
		console.log(imbdId);
		$http({
			method: 'POST',
			url: 'http://www.omdbapi.com',
			params: {
				"plot": "long",
				"r" : "json",
				"i" : imbdId
			}
		}).then(function successCallback(response) {
			$scope.ad.plot = response.data.Plot;
		    $scope.ad.imdbScore = response.data.imdbRating;
		    $scope.ad.year = response.data.Year;
		    $scope.ad.imdbPoster = response.data.Poster;
		    $scope.ad.actors = response.data.Actors;
		    $scope.ad.imdbTitle = response.data.Title;
		    console.log($scope.ad)
		});
	}, function errorCallback(response) {
	    // called asynchronously if an error occurs
	    // or server returns response with an error status.
	});
	
	$scope.goBack = function(){
		$location.path("/search")
	}
});


/*
 * Controller for the search page
 */
app.controller("SearchCtrl", function($scope, $http, $location){
	//Init page variables
	$scope.ads = [];
	$scope.searchVal = "";
	$scope.ratings = [];
	$scope.genres = [];
	$scope.selectedRatings = [];
	$scope.selectedGenres = [];
	$scope.zipCode = "";
	$scope.radius = "";
	
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
	
	$scope.openAd = function(id){
		$location.path('/viewAd/'+ id)
	}
	
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
		search();
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
		search();
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
	
	//Init page values
	$scope.search();
	$scope.getRatings();
	$scope.getGenres();
});
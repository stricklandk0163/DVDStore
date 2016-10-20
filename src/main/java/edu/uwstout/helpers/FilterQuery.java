package edu.uwstout.helpers;

import java.util.List;

public class FilterQuery {
	private String titleQuery;
	private LocationQuery locationQuery;
	private List<String> ratingQuery;
	private List<String> genreQuery;
	public String getTitleQuery() {
		return titleQuery;
	}
	public void setTitleQuery(String titleQuery) {
		this.titleQuery = titleQuery;
	}
	public LocationQuery getLocationQuery() {
		return locationQuery;
	}
	public void setLocationQuery(LocationQuery locationQuery) {
		this.locationQuery = locationQuery;
	}
	public List<String> getRatingQuery() {
		return ratingQuery;
	}
	public void setRatingQuery(List<String> ratingQuery) {
		this.ratingQuery = ratingQuery;
	}
	public List<String> getGenreQuery() {
		return genreQuery;
	}
	public void setGenreQuery(List<String> genreQuery) {
		this.genreQuery = genreQuery;
	}
	
}

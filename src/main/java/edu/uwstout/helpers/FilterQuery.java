package edu.uwstout.helpers;

import java.util.List;

public class FilterQuery {
	private String titleQuery;
	private String zipCode;
	private List<String> ratingQuery;
	private List<String> genreQuery;
	public String getTitleQuery() {
		return titleQuery;
	}
	public void setTitleQuery(String titleQuery) {
		this.titleQuery = titleQuery;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}

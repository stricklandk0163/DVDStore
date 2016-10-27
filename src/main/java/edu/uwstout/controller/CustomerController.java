package edu.uwstout.controller;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.uwstout.helpers.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;


/*
 * Controller for actions taken by customer users
 */
@RestController
public class CustomerController {
    /*
     * Take query from search bar and return list of movies matching
     */
    @RequestMapping(value = "/queryForList" , method = RequestMethod.POST)
    public @ResponseBody String queryForList(@RequestBody FilterQuery filterQuery) {
    	//Get all advertisements and filter off those not matching filter
    	List<Advertisement> advertisements = (AdvertisementJSONReader.readAdvertisements().stream())
    										 .filter(advertisement -> matchesFilterQuery(filterQuery, advertisement))
    										 .collect(Collectors.toList());
    	
    	//Send list response
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		String jsonResponse = mapper.writeValueAsString(advertisements);
    		return jsonResponse;
    	}catch(Exception ex){
    		System.out.println("Failed to write movies list to JSON");
    		return "{}";
    	}
    }
    
    /*
     * Get all possible movie ratings from ad listings
     */
    @RequestMapping(value = "/ratings" , method = RequestMethod.GET)
    public @ResponseBody String ratings() {
    	//Get all advertisements
    	List<Advertisement> advertisements = AdvertisementJSONReader.readAdvertisements();
    	
    	//Get all unique ratings for advertisements
    	HashSet<String> ratings = new HashSet<String>();
    	for(Advertisement advertisement : advertisements){
    		ratings.add(advertisement.getRating());
    	}
    	
    	//Send ratings response
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		String jsonResponse = mapper.writeValueAsString(ratings);
    		return jsonResponse;
    	}catch(Exception ex){
    		System.out.println("Failed to ratings list to JSON");
    		return "{}";
    	}
    }
    
    /*
     * Get an advertisement by id
     */
    @RequestMapping(value = "/getAdById" , method = RequestMethod.POST)
    public @ResponseBody String getAdById(@RequestBody int id) {
    	//Get all advertisements
    	List<Advertisement> advertisements = AdvertisementJSONReader.readAdvertisements();
    	
    	//Send the advertisement with the matching id in a response
    	ObjectMapper mapper = new ObjectMapper();
    	for(Advertisement advertisement : advertisements){
    		if(advertisement.getId() == id){
    			try{
    	    		String jsonResponse = mapper.writeValueAsString(advertisement);
    	    		return jsonResponse;
    	    	}catch(Exception ex){
    	    		System.out.println("Failed to ratings list to JSON");
    	    		return "{}";
    	    	}
    		}
    	}
    	return "{}";
    }
    
    /*
     * Get all possible genres from ads listings
     */
    @RequestMapping(value = "/genres" , method = RequestMethod.GET)
    public @ResponseBody String genres() {
    	List<Advertisement> advertisements = AdvertisementJSONReader.readAdvertisements();
    	HashSet<String> genres = new HashSet<String>();
    	for(Advertisement advertisement : advertisements){
    		for(String genre : advertisement.getGenre()){
    			genres.add(genre);
    		}
    	}
    	List<String> sortedGenres = new ArrayList<String>(genres);
    	Collections.sort(sortedGenres);
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		String jsonResponse = mapper.writeValueAsString(sortedGenres);
    		return jsonResponse;
    	}catch(Exception ex){
    		System.out.println("Failed to ratings list to JSON");
    		return "{}";
    	}
    }
    
    /*
     * Passed filter sent by UI and an advertisement
     * Checks if advertisement matches filter 
     */
    private boolean matchesFilterQuery(FilterQuery filter, Advertisement ad){
    	boolean matchesGenre = false;
    	boolean matchesRating = filter.getRatingQuery().contains(ad.getRating());
    	boolean matchesTitleFilter = filter.getTitleQuery() == null || filter.getTitleQuery().equals("") || ad.getTitle().toLowerCase().contains(filter.getTitleQuery().toLowerCase());
    	boolean matchesZipCodeFilter = filter.getZipCode() == null || filter.getZipCode().equals("") || ad.getZipCode().contains(filter.getZipCode());
    	for(String genre : ad.getGenre()){
    		if(filter.getGenreQuery().contains(genre)){
    			matchesGenre = true;
    		}
    	}
    	
    	return matchesGenre && matchesRating && matchesTitleFilter && matchesZipCodeFilter;
    	
    }
    
}

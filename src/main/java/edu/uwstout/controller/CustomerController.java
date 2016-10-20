package edu.uwstout.controller;

import java.util.HashSet;
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
    	
    	List<Advertisement> advertisements = (AdvertisementJSONReader.readAdvertisements().stream())
    										 .filter(advertisement -> matchesFilterQuery(filterQuery, advertisement))
    										 .collect(Collectors.toList());
    	
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
    	List<Advertisement> advertisements = AdvertisementJSONReader.readAdvertisements();
    	
    	HashSet<String> ratings = new HashSet<String>();
    	for(Advertisement advertisement : advertisements){
    		ratings.add(advertisement.getRating());
    	}
    	
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
    @RequestMapping(value = "/getAd" , method = RequestMethod.POST)
    public @ResponseBody String getAd(@RequestBody String id) {
    	List<Advertisement> advertisements = AdvertisementJSONReader.readAdvertisements();
    	
    	//Todo: get the advertisement matching the id passed id string
    	Advertisement advertisment = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		String jsonResponse = mapper.writeValueAsString(advertisment);
    		return jsonResponse;
    	}catch(Exception ex){
    		System.out.println("Failed to ratings list to JSON");
    		return "{}";
    	}
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
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		String jsonResponse = mapper.writeValueAsString(genres);
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
    	//Todo: get filtering working
    	return ad.getTitle().toLowerCase().contains(filter.getTitleQuery().toLowerCase());
    	
    }
    
}

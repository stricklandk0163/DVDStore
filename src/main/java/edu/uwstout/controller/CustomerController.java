package edu.uwstout.controller;
import java.util.HashMap;
import java.util.List;

import edu.uwstout.helpers.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
/*
 * Controller for actions taken by customer users
 */
public class CustomerController {
    /*
     * Take query from search bar and return list of movies matching
     */
    @RequestMapping(value = "/queryForList" , method = RequestMethod.POST)
    public @ResponseBody String addNewWorker(@RequestBody String jsonString) {
    	List<Advertisement> advertisments = AdvertisementJSONReader.readAdvertisements();
    	ObjectMapper mapper = new ObjectMapper();
    	try{
    		String jsonResponse = mapper.writeValueAsString(advertisments);
    		return jsonResponse;
    	}catch(Exception ex){
    		System.out.println("Failed to write movies list to JSON");
    		return "{}";
    	}
    }
}

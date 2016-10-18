package edu.uwstout.helpers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdvertisementJSONReader {
	public static List<Advertisement> readAdvertisements(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			return Arrays.asList(mapper.readValue(new File(AdvertisementJSONReader.class.getResource("/ads.json").toURI()), Advertisement[].class));
		}
		catch(URISyntaxException ex){
			System.out.println("Bad file path to json movie file");
		}
		catch(JsonParseException ex){
			System.out.println("Could not parse the json file");
		}
		catch(JsonMappingException ex){
			System.out.println("Could not map the json file to the entry object (Field mismatch?)");
		}
		catch(IOException ex){
			System.out.println("Could not read json movie file");
		}
		return new ArrayList<Advertisement>();
	}
}

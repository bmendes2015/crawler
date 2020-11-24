package com.axreng.backend.bean;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;

public class Identify {
	
	private static Identify instance = null;
    
	private HashSet<String> hashUniqueIds = new HashSet<>();
	
    private Identify() {}
    
    public static synchronized Identify getInstance () {
        if(instance == null) {
            instance = new Identify();
        }
        return instance;
    }
	
	public synchronized String generateID() throws NoSuchAlgorithmException {
		
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 8;
	    Random random = new Random();
	 
	    String id = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    if(hashUniqueIds.contains(id)) {
	    	id = generateID();
	    }
	    
	    hashUniqueIds.add(id);
	    return id;
        
	}
}

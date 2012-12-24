package util;

import txtOutPut.PageRelevanceIO;

public class GoogleSERPProcessor {

	
	public static GoogleSERPProcessor singlton = null;
	
	public static GoogleSERPProcessor getInstance(){
		if(singlton == null)
			singlton = new GoogleSERPProcessor();
		return singlton;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}

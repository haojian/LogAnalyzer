package util;

public class ParametersCollection {
	public static ParametersCollection singleton;
	
	public static ParametersCollection getInstance(){
		if(singleton == null){
			singleton = new ParametersCollection();
		}
		return singleton;
	}
	
	private String[] useridList = {
			"091401",
			"091402",
			"091404", "091405", //^091404/05 same people
			"091502",
			"092401",

			"092501",
			"092502",
			"092601",
			"092602",
			"092701",
			"092801",
			"100201",
			"100301",
			"100302",
			"100303",
			"100501",
			"100502",
			"100901",
			"100101",  // (should have been 101001)
			"101101",
			"101201",
			"101202",
			"101901",  //(should have been 101701)
			"101801",
			"101902",
			"102201"
	};
	
	public ParametersCollection(){
		
	}
	
	public String[] getUserIDList(){
		return useridList;
	}
}

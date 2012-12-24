package txtOutPut;

public class SERPRankIO {
	
	public static SERPRankIO singlton = null;
	
	public static SERPRankIO getInstance(){
		if(singlton == null)
			singlton = new SERPRankIO();
		return singlton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SERPRankIO.getInstance().Output();
	}

	public void Output(){
		
	}
}

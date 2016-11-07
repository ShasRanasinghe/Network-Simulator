package network;

//Enumerated types of algorithms
public enum ALGORITHM {
	RANDOM("Random"), FLOODING("Flooding"), SHORTESTPATH("Shortest Path"), CUSTOM("Custom");

    private String algString;

    //Create modes from string
    ALGORITHM(final String alg) {
        algString = alg;
    }

    //return as string
    public String getALGString() {
        return algString;
    }
  
    public static ALGORITHM getEnum(String str)
    {
    	for(ALGORITHM alg: ALGORITHM.values())
    	{
    		if(alg.algString.equals(str))
    		{
    			return alg;
    		}
    	}
    	return RANDOM;
    }
}


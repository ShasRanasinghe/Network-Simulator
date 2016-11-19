package network;


/**
 * Enumerated types of algorithms
 *
 */
public enum ALGORITHM {
	RANDOM("Random"), FLOODING("Flooding"), SHORTESTPATH("Shortest Path"), CUSTOM("Custom");

    private String algString;

    
    /**
     * @param alg Assign a string to an Enum
     */
    ALGORITHM(final String alg) {
        algString = alg;
    }

    
    /**
     * @return The string representation of the ALGORITHM Enum
     */
    public String getALGString() {
        return algString;
    }
  
    /**
     * @param str string representation of the Enum
     * @return The ALGORITM Enum corresponding to the given string. Returns RANDOM if string doesn't exist
     */
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


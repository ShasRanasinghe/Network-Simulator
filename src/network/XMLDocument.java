package network;
import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class XMLDocument {

	/**
	 * @param simulationNodes Simulation nodes
	 * @param file File
	 * @throws JAXBException Exception
	 */
	public static void writeSaveState(SaveState ss, File file) throws JAXBException
	{
	    JAXBContext jaxbContext = JAXBContext.newInstance(SaveState.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     
	    //Marshal the nodes list in console
	    jaxbMarshaller.marshal(ss, System.out);
	     
	    //Marshal the nodes list in file
	    jaxbMarshaller.marshal(ss, file);
	}
	
	
	

}

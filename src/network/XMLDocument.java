package network;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLDocument {


	/**
	 * Writing the XML --- Marshalling
	 * @param ss SaveState Object to be XML
	 * @param file File name
	 * @throws JAXBException Exception
	 */
	public static void writeSaveState(SaveState ss, File file) throws JAXBException
	{
	    JAXBContext jaxbContext = JAXBContext.newInstance(SaveState.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     	     
	    //Marshal the nodes list in file
	    jaxbMarshaller.marshal(ss, file);
	}
	
	
	/**
	 * Parse XML- Unmarshaller
	 * @param file File to be unmarshalled
	 * @return The SaveState object which included the list of Nodes with neighbors
	 * @throws JAXBException Exception
	 */
	public static SaveState readSaveState(File file) throws JAXBException 
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(SaveState.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	     
	    //We had written this file in marshalling example
	    return (SaveState) jaxbUnmarshaller.unmarshal(file);
	}
	
	

}

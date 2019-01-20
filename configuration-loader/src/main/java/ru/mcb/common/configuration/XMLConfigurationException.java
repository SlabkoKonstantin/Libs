package ru.mcb.common.configuration;

public class XMLConfigurationException extends Exception {
	
	private static final long serialVersionUID = 7092700375939645342L;

	public XMLConfigurationException(Exception ex) {
		super(ex);
	}
	
	public XMLConfigurationException(String ex) {
		super(ex);
	}
	
}

package ru.mcb.common.configuration;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;

public class XmlConfiguration<X> {
	private final String path;
	private String lastErrorMessage;
	private X config;

	public XmlConfiguration(X configuration, String path) {
		this.path = path;
		this.config = configuration;
		this.lastErrorMessage = "no error";
	}

	public synchronized boolean save() {
		boolean result = false;

		try {
			File file = new File(this.path);
			if (file.exists()) {
				Jaxb jaxb = new Jaxb(this.config.getClass());
				jaxb.marshal(this.config, file);
				result = true;
			} else {
				this.lastErrorMessage = "File " + this.path + " not exists.";
			}
		} catch (JAXBException e) {
			this.lastErrorMessage = e.getMessage();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public synchronized X load() {
		try {
			File file = new File(this.path);
			if (file.exists()) {
				Jaxb jaxb = new Jaxb(config.getClass());
				this.config = (X) jaxb.unmarshal(file);
				return this.config;
			} else {
				this.lastErrorMessage = "File " + this.path + " not exists.";
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage() == null) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				this.lastErrorMessage = sw.toString();
			} else {
				this.lastErrorMessage = e.getMessage();
			}
			return null;
		}
	}

	public synchronized String getLastErrorMessage() {
		return this.lastErrorMessage;
	}

}

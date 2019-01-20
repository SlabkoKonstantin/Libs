package ru.mcb.common.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class PropertyLoader
 *
 */
public class PropertyLoader 
{
	private static String lastErrorMessage = "";

	private PropertyLoader() {
	}

	public static synchronized Properties getProperties(String propName) {

		Properties props = new Properties();
		try {
			props.load(ClassLoader.getSystemResourceAsStream(propName));
		} catch (IOException e) {
			lastErrorMessage = e.getMessage();
		}
		return props;
	}

	public static synchronized String getLastErrorMessage() {
		return lastErrorMessage;
	}

	public static synchronized Properties getPluginProperties(String jarPath, String propName) throws Exception {
		Properties props = null;
		try {
			File file = new File(jarPath);
			if (file.exists() && file.isFile()) {
				props = getPluginProps(file, propName);
				if (props == null)
					throw new IllegalArgumentException("No props file found: " + jarPath);
			} else {
				throw new Exception("File " + jarPath + " is not exists or not a file.");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return props;
	}
	
	private static synchronized Properties getPluginProps(File file, String propName) throws IOException {
		Properties result = null;
		JarFile jar = new JarFile(file);
		try {
			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().equals(propName)) {
					InputStream is = null;
					try {
						is = jar.getInputStream(entry);
						result = new Properties();
						result.load(is);
					} finally {
						if (is != null)
							is.close();
					}
				}
			}
		} finally {
			jar.close();
		}
		return result;
	}
}

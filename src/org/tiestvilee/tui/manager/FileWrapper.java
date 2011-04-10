package org.tiestvilee.tui.manager;

import java.io.*;

public class FileWrapper {
	public InputStream streamFor(String fileName) {
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("There was a problem getting a stream for file:" + fileName, e);
		}
	}

	public File[] getFilesInDirectory(String directoryName) {
		return (new File(directoryName)).listFiles();
	}

	public String readFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(streamFor(file.getCanonicalPath())));

			String line;
			StringBuffer total = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				total.append(line).append("\n");
			}
			return total.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}

package org.tiestvilee.tui.manager;

import java.io.*;

public class FileWrapper {
	public InputStream inputStreamFor(String fileName) {
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamFor(file.getCanonicalPath())));

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

    public OutputStream outputStreamFor(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There was a problem getting a stream for file:" + fileName, e);
        }
    }
}

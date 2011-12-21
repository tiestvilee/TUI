package org.tiestvilee.tui.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;

public class IoIntegration implements Serializable {

    public BufferedReaderWrapper newBufferedReader(String filename) {
        return new BufferedReaderWrapper(filename);
    }

    public static class BufferedReaderWrapper implements Serializable {
        private String filename;
        //private transient BufferedReader reader;

        public BufferedReaderWrapper(String filename) {
            this.filename = filename;
        }

        public String readLine() throws Exception {
//            if(reader == null) {
//                createReader();
//            }
//            return reader.readLine();
            return null;
        }

        private void createReader() throws Exception {
//            reader = new BufferedReader(new FileReader(filename));
        }

        public void close() throws Exception {
//            if(reader != null) {
//                reader.close();
//                reader = null;
//            }
        }
    }
}

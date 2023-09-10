/*
 * @(#)FileReader.java
 *
 * This file is a part of the FormulaFox project.
 * It contains the definition of the FileReader class,
 * which is used to read data from classpath resources.
 *
 * Author: Vadym Tsudenko
 *
 * Date: September 10, 2023
 */
package ua.com.foxminded.dao;

import java.io.*;
import java.util.stream.Stream;

public class FileReader {

    /**
     * Reads the contents of a file with the given file name from the classpath resources.
     *
     * @param fileName name of file to be read.
     * @return Stream of strings.
     * @throws IOException
     * @throws FileNotFoundException If the specified file is not found in the classpath resources.
     */
    public Stream<String> read(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream != null) {
            return new BufferedReader(new InputStreamReader(inputStream)).lines();
        } else {
            throw new FileNotFoundException("No such file: " + fileName);
        }
    }
}

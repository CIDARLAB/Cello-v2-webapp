/*
 * Copyright (C) 2020 Massachusetts Institute of Technology (MIT) Boston
 * University (BU) Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cellocad.v2.webapp.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.cellocad.v2.webapp.Application;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.exception.ResourceNotFoundException;
import org.springframework.boot.system.ApplicationHome;

/**
 * Utility methods for the Cello webapp.
 *
 * @author Vincent Mirian
 * @author Timothy Jones
 * @date Oct 28, 2017
 */
public final class Utils {

  /**
   * Get the version of this project.
   *
   * @return The version of this project.
   * @throws CelloWebException Unable to get the version.
   */
  public static String getVersion() throws CelloWebException {
    String rtn = null;
    final Properties properties = new Properties();
    try {
      properties.load(Utils.class.getClassLoader().getResourceAsStream(".properties"));
    } catch (IOException e) {
      throw new CelloWebException("Unable to get version.");
    }
    rtn = properties.getProperty("org.cellocad.v2.cello-webapp.version");
    return rtn;
  }

  /**
   * Get the resource as a stream.
   *
   * @param resource A resource.
   * @return A stream of the resource.
   */
  public static InputStream getResourceAsStream(final String resource) {
    InputStream rtn = null;
    rtn = Utils.class.getClassLoader().getResourceAsStream(resource);
    return rtn;
  }

  /**
   * Get the contents at the given URL as a string. If the response is gzip-compressed, this method
   * will return the uncompressed value.
   *
   * @return The uncompressed contents of the given URL as a string.
   * @throws IOException Unable to read.
   */
  public static String getUrlContentsAsString(final URL url) throws IOException {
    String rtn = null;
    final HttpClientBuilder builder = HttpClientBuilder.create();
    final CloseableHttpClient httpClient = builder.build();
    final HttpGet httpGet = new HttpGet(url.toString());
    final HttpResponse httpResponse = httpClient.execute(httpGet);
    rtn = EntityUtils.toString(httpResponse.getEntity());
    return rtn;
  }

  /**
   * Find a {@link CObject} by name.
   *
   * @param <T> Any type extending a {@link CObject}.
   * @param name The name of the object to search for.
   * @param collection The collection in which to search for the object.
   * @return The object with the given name, if it exists.
   * @throws ResourceNotFoundException Unable to find the object.
   */
  public static <T extends CObject> T findCObjectByName(
      final String name, final Collection<T> collection) throws ResourceNotFoundException {
    T rtn = null;
    final Iterator<T> it = collection.iterator();
    while (it.hasNext()) {
      final T obj = it.next();
      if (obj.getName().equals(name)) {
        rtn = obj;
        break;
      }
    }
    if (rtn == null) {
      throw new ResourceNotFoundException();
    }
    return rtn;
  }

  /**
   * Get a resource by name as a URL.
   *
   * @param resource A resource name.
   * @return A URL pointing to the resource.
   */
  public static URL getResource(final String resource) {
    URL rtn = null;
    rtn = Utils.class.getClassLoader().getResource(resource);
    return rtn;
  }

  /**
   * Get the content of the buffered reader as a string.
   *
   * @param br A buffered reader.
   * @return The string contents of the reader.
   * @throws IOException Unable to read.
   */
  public static String getBufferedReaderContentAsString(final BufferedReader br)
      throws IOException {
    String rtn = "";
    final StringBuffer sb = new StringBuffer();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
      sb.append(Utils.getNewLine());
    }
    rtn = sb.toString();
    return rtn;
  }

  /**
   * Get the contents of a resource as a string.
   *
   * @param resource The name of a resource.
   * @return A string containing the contents of the resource.
   * @throws IOException Unable to read resource.
   */
  public static String getResourceAsString(final String resource) throws IOException {
    String rtn = "";
    final InputStream is = Utils.getResource(resource).openStream();
    final InputStreamReader isr = new InputStreamReader(is);
    final BufferedReader br = new BufferedReader(isr);
    rtn = Utils.getBufferedReaderContentAsString(br);
    br.close();
    isr.close();
    is.close();
    return rtn;
  }

  /**
   * Get the content of the given file name as a string.
   *
   * @param filepath A path to a file.
   * @return The content of the file with the given path.
   * @throws IOException Unable to read file.
   */
  public static String getFileContentAsString(final String filepath) throws IOException {
    String rtn = "";
    final File file = new File(filepath);
    final FileReader fr = new FileReader(file);
    final BufferedReader br = new BufferedReader(fr);
    rtn = Utils.getBufferedReaderContentAsString(br);
    return rtn;
  }

  /**
   * Make a directory at the given location.
   *
   * @param filepath The path of the directory to make.
   * @return Whether the directory was created.
   */
  public static boolean makeDirectory(final String filepath) {
    boolean rtn = false;
    final File file = new File(filepath);
    rtn = file.mkdir();
    return rtn;
  }

  /**
   * Create a directory only if it doesn't already exist.
   *
   * @param filepath The path of the directory to create.
   * @return False.
   * @deprecated This method is not useful. Replace with {@link #makeDirectory(String)}.
   */
  @Deprecated
  public static boolean createDirectoryIfNonExistant(final String filepath) {
    final boolean rtn = false;
    if (!Utils.isValidFilepath(filepath)) {
      Utils.makeDirectory(filepath);
    }
    return rtn;
  }

  /**
   * Create a file only if it doesn't already exist.
   *
   * @param filepath The path of the file to create.
   * @return False.
   * @deprecated This method is not useful. Replace with {@link #createFile(String)}.
   */
  @Deprecated
  public static boolean createFileIfNonExistant(final String filepath) throws IOException {
    final boolean rtn = false;
    if (!Utils.isValidFilepath(filepath)) {
      Utils.createFile(filepath);
    }
    return rtn;
  }

  /**
   * Whether a file or directory with the given path exists or not.
   *
   * @param filepath A path to a file or directory.
   * @return True if the file or directory at the given location exists.
   */
  public static boolean isValidFilepath(final String filepath) {
    boolean rtn = false;
    final File file = new File(filepath);
    rtn = file.exists();
    return rtn;
  }

  /**
   * Returns the path of the running {@link Application}.
   *
   * @return The path of the running {@link Application}.
   */
  public static String getFilepath() {
    String rtn = "";
    final ApplicationHome home = new ApplicationHome(Application.class);
    rtn += home.getDir().getPath();
    rtn += Utils.getFileSeparator();
    return rtn;
  }

  /**
   * Given a path, returns the base name of the file without an extension.
   *
   * <p>Example:
   *
   * <pre>
   * getFilename("/var/tmp/foo.txt"); // returns "foo"
   * getFilename("../../bar.log"); // returns "bar"
   * </pre>
   *
   * @return The base name, without extension, of the file at the given location.
   */
  public static String getFilename(final String name) {
    String rtn = name;
    int index = 0;
    index = rtn.lastIndexOf(Utils.getFileSeparator());
    if (index != -1) {
      rtn = rtn.substring(index + 1);
    }
    index = rtn.lastIndexOf(".");
    if (index != -1 && rtn.length() > 1) {
      rtn = rtn.substring(0, index);
    }
    return rtn;
  }

  /**
   * Create a file.
   *
   * @param filename The file name.
   * @return True if successful.
   */
  public static boolean createFile(final String filename) throws IOException {
    boolean rtn = false;
    final File file = new File(filename);
    rtn = file.createNewFile();
    return rtn;
  }

  /**
   * Append a string to a file.
   *
   * @param str A string.
   * @param filename The file name.
   */
  public static void appendToFile(final String str, final String filename) {
    try {
      final OutputStream outputStream = new FileOutputStream(filename, true);
      final Writer outputStreamWriter = new OutputStreamWriter(outputStream);
      outputStreamWriter.write(str);
      outputStreamWriter.close();
      outputStream.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Write a string to a file.
   *
   * @param str A string.
   * @param filename The file name.
   */
  public static void writeToFile(final String str, final String filename) {
    try {
      final OutputStream outputStream = new FileOutputStream(filename);
      final Writer outputStreamWriter = new OutputStreamWriter(outputStream);
      outputStreamWriter.write(str);
      outputStreamWriter.close();
      outputStream.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Delete a file.
   *
   * @param name The file name.
   * @return True if the file was deleted.
   */
  public static boolean deleteFilename(final String name) {
    boolean rtn = false;
    final File file = new File(name);
    rtn = file.delete();
    return rtn;
  }

  /**
   * Returns a string containing the operating system name.
   *
   * @return A string containing the operating system name.
   */
  private static String getOS() {
    return System.getProperty("os.name");
  }

  /**
   * Whether the operating system is Windows-based.
   *
   * @param os The operating system string.
   * @return Whether the operating system is Windows-based.
   */
  private static boolean isWin(final String os) {
    return os.toLowerCase().indexOf("win") >= 0;
  }

  /**
   * Returns a boolean flag signifying the Operating System being Windows-based.
   *
   * @return True if the Operating System is Windows-based, otherwise false.
   */
  public static boolean isWin() {
    return Utils.isWin(Utils.getOS());
  }

  /**
   * Whether the operating system is Mac-based.
   *
   * @param os The operating system string.
   * @return Whether the operating system is Mac-based.
   */
  private static boolean isMac(final String os) {
    return os.toLowerCase().indexOf("mac") >= 0;
  }

  /**
   * Returns a boolean flag signifying the Operating System being Mac-based.
   *
   * @return True if the Operating System is Mac-based, otherwise false.
   */
  public static boolean isMac() {
    return Utils.isMac(Utils.getOS());
  }

  /**
   * Whether the operating system is Unix-based.
   *
   * @param os The operating system string.
   * @return Whether the operating system is Unix-based.
   */
  private static boolean isUnix(final String os) {
    return os.indexOf("nix") >= 0
        || os.indexOf("nux") >= 0
        || os.indexOf("aix") > 0
        || os.indexOf("bsd") > 0
        || os.indexOf("irix") > 0;
  }

  /**
   * Returns a boolean flag signifying the Operating System being Unix-based.
   *
   * @return True if the Operating System is Unix-based, otherwise false.
   */
  public static boolean isUnix() {
    return Utils.isUnix(Utils.getOS());
  }

  /**
   * Executes and waits for the command in parameter <i>cmd</i>. Returns the ExecCommand instance
   * that executed the command.
   *
   * @param cmd the command
   * @return the ExecCommand instance that executed the command.
   */
  public static ExecCommand executeAndWaitForCommand(final String cmd) {
    ExecCommand rtn = null;
    rtn = new ExecCommand(cmd);
    return rtn;
  }

  /**
   * Returns a string representing the number of milliseconds since January 1, 1970, 00:00:00 GMT.
   *
   * @return a string representing the number of milliseconds since January 1, 1970, 00:00:00 GMT.
   */
  public static String getTimeString() {
    String rtn;
    final Date date = new Date();
    rtn = String.valueOf(date.getTime());
    return rtn;
  }

  /**
   * Returns a string representing the working directory.
   *
   * @return A string representing the working directory.
   */
  public static String getWorkingDirectory() {
    final String rtn = System.getProperty("user.dir").toString();
    return rtn;
  }

  /**
   * Returns a string representing the file separator on the system.
   *
   * @return A string representing the file separator on the system.
   */
  public static String getFileSeparator() {
    final String rtn = System.getProperty("file.separator").toString();
    return rtn;
  }

  /**
   * Returns a string representing the elements in the parameter {@code folders} separated by the
   * file separator of the system.
   *
   * @return A string representing the elements in the parameter {@code folders} separated by the
   *     file separator of the system.
   */
  public static String getPath(final String[] folders) {
    String rtn = "";
    for (int i = 0; i < folders.length; i++) {
      rtn += folders[i];
      rtn += Utils.getFileSeparator();
    }
    return rtn;
  }

  /**
   * Returns a string representing the elements in the parameter {@code folders} separated by the
   * file separator of the system omitting the last file separator.
   *
   * @return A string representing the elements in the parameter {@code folders} separated by the
   *     sile separator of the system omitting the last file separator.
   */
  public static String getPathFile(final String[] folders) {
    String rtn = "";
    rtn = Utils.getPath(folders);
    if (rtn.length() > 0) {
      final int length = rtn.length();
      final String lastChar = rtn.substring(length - 1, length);
      if (lastChar.contentEquals(Utils.getFileSeparator())) {
        rtn = rtn.substring(0, length - 1);
      }
    }
    return rtn;
  }

  /**
   * Returns a string representing the line separator on the system.
   *
   * @return A string representing the line separator on the system.
   */
  public static String getNewLine() {
    final String rtn = System.getProperty("line.separator").toString();
    return rtn;
  }

  /**
   * Returns a string representing the parameter {@code str} indented with parameter {@code
   * numIndent} tabulator character(s).
   *
   * @param numIndent The number of indentations.
   * @param str The string to indent.
   * @return A string representing the parameter {@code str} indented with parameter {@code
   *     numIndent} tabulator character(s).
   */
  public static String addIndent(final int numIndent, final String str) {
    String rtn = "";
    String replace = "";
    final String numTab = Utils.getTabCharacterRepeat(numIndent);
    replace = Utils.getNewLine() + numTab;
    if (!str.isEmpty()) {
      rtn = numTab + str.replace(Utils.getNewLine(), replace);
    }
    return rtn;
  }

  /**
   * Returns a string representing the tabulator character.
   *
   * @return a string representing the tabulator character.
   */
  public static String getTabCharacter() {
    final String rtn = "\t";
    return rtn;
  }

  /**
   * Returns a string representing the tabulator character repeated <i>num</i> number of times.
   *
   * @return a string representing the tabulator character repeated <i>num</i> number of times.
   */
  public static String getTabCharacterRepeat(final int num) {
    String rtn = "";
    final String tab = Utils.getTabCharacter();
    for (int i = 0; i < num; i++) {
      rtn = rtn + tab;
    }
    return rtn;
  }

  /**
   * Returns a boolean flag signifying that the parameter {@code obj} is an instance of the {@link
   * Boolean} class.
   *
   * @return True if {@code obj} is an instance of the {@link Boolean} class, otherwise false.
   */
  public static boolean isBoolean(final Object obj) {
    boolean rtn = false;
    rtn = obj instanceof Boolean;
    return rtn;
  }

  /**
   * Returns a boolean flag signifying that the parameter {@code obj} is an instance of the {@link
   * Long} class.
   *
   * @return True if {@code obj} is an instance of the {@link Long} class, otherwise false.
   */
  public static boolean isLong(final Object obj) {
    boolean rtn = false;
    rtn = obj instanceof Long;
    return rtn;
  }

  /**
   * Returns a boolean flag signifying that the parameter {@code obj} is an instance of the {@link
   * Double} class.
   *
   * @return True if {@code obj} is an instance of the {@link Double} class, otherwise false.
   */
  public static boolean isDouble(final Object obj) {
    boolean rtn = false;
    rtn = obj instanceof Double;
    return rtn;
  }

  /**
   * Returns a boolean flag signifying that the parameter {@code obj} is an instance of the {@link
   * String} class.
   *
   * @return True if {@code obj} is an instance of the {@link String} class, otherwise false.
   */
  public static boolean isString(final Object obj) {
    boolean rtn = false;
    rtn = obj instanceof String;
    return rtn;
  }

  /**
   * Returns a boolean flag signifying that the parameter <i>cObj</i> is null.
   *
   * @param cObj Object reference to check
   * @param name of the cObj, used in RuntimeException print statement
   * @return true if cObj is null, otherwise false.
   * @throws RuntimeException if cObj is null.
   */
  public static boolean isNullRuntimeException(final Object cObj, final String name) {
    boolean rtn = false;
    rtn = cObj == null;
    if (rtn) {
      throw new RuntimeException(name + " cannot be null!");
    }
    return rtn;
  }

  /**
   * Returns a {@link Boolean} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Boolean} value, otherwise an instance with
   *     the value equivalent to the parameter {@code str} content.
   */
  public static Boolean getBoolean(final String str) {
    Boolean rtn = null;
    if (str.equalsIgnoreCase("true")) {
      rtn = new Boolean(true);
    }
    if (str.equalsIgnoreCase("false")) {
      rtn = new Boolean(false);
    }
    return rtn;
  }

  /**
   * Returns a {@link Byte} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Byte} value, otherwise an instance with the
   *     value equivalent to the parameter {@code str} content.
   */
  public static Byte getByte(final String str) {
    Byte rtn = null;
    try {
      rtn = Byte.valueOf(str);
    } catch (final NumberFormatException e) {
      rtn = null;
    }
    return rtn;
  }

  /**
   * Returns a {@link Character} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Character} value, otherwise an instance with
   *     the value equivalent to the parameter {@code str} content.
   */
  public static Character getCharacter(final String str) {
    Character rtn = null;
    if (str != null & str.length() > 0) {
      rtn = str.charAt(0);
    }
    return rtn;
  }

  /**
   * Returns a {@link Short} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Short} value, otherwise an instance with the
   *     value equivalent to the parameter {@code str} content.
   */
  public static Short getShort(final String str) {
    Short rtn = null;
    try {
      rtn = Short.valueOf(str);
    } catch (final NumberFormatException e) {
      rtn = null;
    }
    return rtn;
  }

  /**
   * Returns a {@link Integer} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Integer} value, otherwise an instance with
   *     the value equivalent to the parameter {@code str} content.
   */
  public static Integer getInteger(final String str) {
    Integer rtn = null;
    try {
      rtn = Integer.valueOf(str);
    } catch (final NumberFormatException e) {
      rtn = null;
    }
    return rtn;
  }

  /**
   * Returns a {@link Long} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Long} value, otherwise an instance with the
   *     value equivalent to the parameter {@code str} content.
   */
  public static Long getLong(final String str) {
    Long rtn = null;
    try {
      rtn = Long.valueOf(str);
    } catch (final NumberFormatException e) {
      rtn = null;
    }
    return rtn;
  }

  /**
   * Returns a {@link Float} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value
   * @return Null if {@code str} is not a valid {@link Float} value, otherwise an instance with the
   *     value equivalent to the parameter {@code str} content.
   */
  public static Float getFloat(final String str) {
    Float rtn = null;
    try {
      rtn = Float.valueOf(str);
    } catch (NumberFormatException | NullPointerException e) {
      rtn = null;
    }
    return rtn;
  }

  /**
   * Returns a Double instance where the value is retrieved from the content of parameter {@code
   * str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link Double} value, otherwise an instance with the
   *     value equivalent to the parameter {@code str} content.
   */
  public static Double getDouble(final String str) {
    Double rtn = null;
    try {
      rtn = Double.valueOf(str);
    } catch (final NumberFormatException e) {
      rtn = null;
    }
    return rtn;
  }

  /**
   * Returns a {@link String} instance where the value is retrieved from the content of parameter
   * {@code str}.
   *
   * @param str String where its content is the value.
   * @return Null if {@code str} is not a valid {@link String} value, otherwise an instance with the
   *     value equivalent to the parameter {@code str} content.
   */
  public static String getString(final String str) {
    String rtn = null;
    rtn = str;
    return rtn;
  }

  /**
   * Exits the current executable with exit code equivalent to parameter {@code exit}.
   *
   * @param exit Exit code.
   */
  public static void exit(final int exit) {
    System.exit(exit);
  }

  /**
   * Returns a random integer value between a minimum value defined by parameter {@code min} and a
   * maximum value defined by parameter {@code max}.
   *
   * @param min Minimum value.
   * @param max Maximum value.
   * @return A random integer value between a minimum value defined by parameter {@code min} and a
   *     maximum value defined by parameter {@code max}.
   */
  public static int random(final int min, final int max) {
    int rtn = 0;
    final Random random = new Random();
    rtn = random.nextInt(max - min + 1) + min;
    return rtn;
  }

  /**
   * Returns a random integer value between a minimum value defined by parameter {@code min} and a
   * maximum value defined by parameter {@code max} using the seed defined by parameter {@code
   * seed}.
   *
   * @param min Minimum value.
   * @param max Maximum value.
   * @param seed The seed.
   * @return A random integer value between a minimum value defined by parameter {@code min} and a
   *     maximum value defined by parameter {@code max} using the seed defined by parameter {@code
   *     seed}.
   */
  public static int random(final int min, final int max, final long seed) {
    int rtn = 0;
    final Random random = new Random(seed);
    rtn = random.nextInt(max - min + 1) + min;
    return rtn;
  }
}

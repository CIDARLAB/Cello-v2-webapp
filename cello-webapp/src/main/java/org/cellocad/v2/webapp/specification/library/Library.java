/*
 * Copyright (C) 2019 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.cellocad.v2.webapp.specification.library;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Collection;
import org.cellocad.v2.webapp.specification.library.serialization.LibrarySerializer;

/**
 * A library.
 *
 * @author Timothy Jones
 * @date 2019-03-19
 */
@JsonSerialize(using = LibrarySerializer.class)
public class Library {

  public Collection<Gate> gates;
  public Collection<InputSensor> sensors;
  public Collection<OutputReporter> reporters;
  public Collection<Part> parts;
  public Collection<JsonNode> objects;

  /**
   * Initialize a new library.
   *
   * @param gates The gates of the library.
   * @param sensors The sensors of the library.
   * @param reporters The reporters of the library.
   * @param parts The parts of the library.
   * @param objects The other objects of the library.
   */
  public Library(
      final Collection<Gate> gates,
      final Collection<InputSensor> sensors,
      final Collection<OutputReporter> reporters,
      final Collection<Part> parts,
      final Collection<JsonNode> objects) {
    super();
    this.gates = gates;
    this.sensors = sensors;
    this.reporters = reporters;
    this.parts = parts;
    this.objects = objects;
  }

  /**
   * Getter for {@code gates}.
   *
   * @return The value of {@code gates}.
   */
  public Collection<Gate> getGates() {
    return gates;
  }

  /**
   * Getter for {@code sensors}.
   *
   * @return The value of {@code sensors}.
   */
  public Collection<InputSensor> getSensors() {
    return sensors;
  }

  /**
   * Getter for {@code reporters}.
   *
   * @return The value of {@code reporters}.
   */
  public Collection<OutputReporter> getReporters() {
    return reporters;
  }

  /**
   * Getter for {@code parts}.
   *
   * @return The value of {@code parts}.
   */
  public Collection<Part> getParts() {
    return parts;
  }

  /**
   * Getter for {@code objects}.
   *
   * @return The value of {@code objects}.
   */
  public Collection<JsonNode> getObjects() {
    return objects;
  }
}

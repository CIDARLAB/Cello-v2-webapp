/*
 * Copyright (C) 2019 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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

package org.cellocad.v2.webapp.exception;

/**
 * Exception for when a resource is not found.
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
public class ResourceNotFoundException extends Exception {

  private static final long serialVersionUID = -6378548144845862662L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(final Throwable cause) {
    super(cause);
  }
}

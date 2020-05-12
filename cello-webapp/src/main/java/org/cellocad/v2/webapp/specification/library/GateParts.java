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

import java.util.Map;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.sbolstandard.core2.ComponentDefinition;

/**
 * Parts associated with a gate.
 *
 * @author Timothy Jones
 * @date 2019-03-19
 */
public class GateParts {

  private Map<String, CassetteParts> expressionCassettes;
  private Part promoter;

  public GateParts(final Map<String, CassetteParts> expressionCassettes, final Part promoter) {
    this.expressionCassettes = expressionCassettes;
    this.promoter = promoter;
  }

  /**
   * Build gate parts from a component definition.
   *
   * @param cd The component definition.
   * @throws LibraryException Unable to build gate parts.
   */
  public GateParts(final ComponentDefinition cd) throws LibraryException {}

  public CassetteParts getCassetteParts(final String variable) {
    return getExpressionCassettes().get(variable);
  }

  /**
   * Getter for {@code expressionCassettes}.
   *
   * @return The value of {@code expressionCassettes}.
   */
  private Map<String, CassetteParts> getExpressionCassettes() {
    return expressionCassettes;
  }

  /**
   * Getter for {@code promoter}.
   *
   * @return The value of {@code promoter}.
   */
  public Part getPromoter() {
    return promoter;
  }
}

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

package org.cellocad.v2.webapp.specification.settings;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.specification.settings.serialization.SettingsDeserializer;

/**
 * Application settings.
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
@JsonDeserialize(using = SettingsDeserializer.class)
public class Settings {

  private String application;
  private Map<String, String> settings;

  public Settings(final Map<String, String> settings, final String application) {
    this.application = application;
    this.settings = settings;
  }

  /**
   * Getter for {@code application}.
   *
   * @return The value of {@code application}.
   */
  public String getApplication() {
    return application;
  }

  /**
   * Getter for {@code settings}.
   *
   * @return The value of {@code settings}.
   */
  public Map<String, String> getSettings() {
    return settings;
  }

  /**
   * Serialize settings in CSV format.
   *
   * @return Settings serialized in CSV format.
   */
  public String toCSV() {
    // FIXME
    // CsvMapper mapper = new CsvMapper();
    // CsvSchema schema = mapper.schemaFor(Map.class);
    // return mapper.writer(schema).writeValueAsString(this.getSettings());
    String rtn = "";
    for (final String key : getSettings().keySet()) {
      final String value = getSettings().get(key);
      rtn += String.format("%s,%s", key, value);
      rtn += Utils.getNewLine();
    }
    return rtn;
  }
}

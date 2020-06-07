/*
 * Copyright (C) 2020 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cellocad.v2.webapp.synbiohub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

/**
 * Tests for {@link NewCollectionDescriptor}.
 *
 * @author Timothy Jones
 * @date 2020-06-06
 */
public class NewCollectionDescriptorTest {

  @Test
  public void NewCollectionDescriptor_MockJson_ShouldSerialize()
      throws JsonMappingException, JsonProcessingException {
    final String name = "myCollection";
    final String description = "Some collection.";
    final String id = "myCollection";
    final String version = "1";
    final String citations = "Science, 2016";
    final String overwrite = "1";
    final String fmt =
        "{ \"name\": \"%s\", \"description\": \"%s\", \"id\": \"%s\", \"version\": \"%s\", \"citations\": \"%s\", \"overwrite\": \"%s\" }";
    final String json = String.format(fmt, name, description, id, version, citations, overwrite);
    ObjectMapper mapper = new ObjectMapper();
    NewCollectionDescriptor collection = mapper.readValue(json, NewCollectionDescriptor.class);
    assert (collection.getName().equals(name)
        && collection.getDescription().equals(description)
        && collection.getId().equals(id)
        && collection.getVersion().equals(version)
        && collection.getCitations().equals(citations)
        && collection.getOverwrite().equals(OverwriteMerge.OVERWRITE_IF_EXISTS));
  }

  @Test(expected = InvalidFormatException.class)
  public void NewCollectionDescriptor_MockJsonWithInvalidOverwrite_ShouldThrowException()
      throws JsonMappingException, JsonProcessingException {
    final String name = "myCollection";
    final String description = "Some collection.";
    final String id = "myCollection";
    final String version = "1";
    final String citations = "Science, 2016";
    final String overwrite = "10";
    final String fmt =
        "{ \"name\": \"%s\", \"description\": \"%s\", \"id\": \"%s\", \"version\": \"%s\", \"citations\": \"%s\", \"overwrite\": \"%s\" }";
    final String json = String.format(fmt, name, description, id, version, citations, overwrite);
    ObjectMapper mapper = new ObjectMapper();
    mapper.readValue(json, NewCollectionDescriptor.class);
  }
}

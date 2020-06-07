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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

/**
 * Tests for {@link SynBioHubSubmission}.
 *
 * @author Timothy Jones
 * @date 2020-06-06
 */
public class SynBioHubSubmissionTest {

  @Test
  public void SynBioHubSubmission_MockExistingCollectionDescriptor_ShouldDeserialize()
      throws JsonMappingException, JsonProcessingException, MalformedURLException {
    final String registry = "https://synbiohub.programmingbiology.org";
    final String projectName = "foo";
    final String resultName = "foo.xml";
    final String name = "myCollection";
    final String description = "Some collection.";
    final String id = "myCollection";
    final String version = "1";
    final String citations = "Science, 2016";
    final String overwrite = "1";
    final String fmt =
        "{ \"registry\": \"%s\", \"project\": \"%s\", \"result\": \"%s\", \"collection\": { \"name\": \"%s\", \"description\": \"%s\", \"id\": \"%s\", \"version\": \"%s\", \"citations\": \"%s\", \"overwrite\": \"%s\" } }";
    final String json =
        String.format(
            fmt,
            registry,
            projectName,
            resultName,
            name,
            description,
            id,
            version,
            citations,
            overwrite);
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<SynBioHubSubmission<NewCollectionDescriptor>> typeRef =
        new TypeReference<SynBioHubSubmission<NewCollectionDescriptor>>() {};
    SynBioHubSubmission<NewCollectionDescriptor> collection = mapper.readValue(json, typeRef);
    assert (collection.getRegistry().equals(new URL(registry))
        && collection.getProjectName().equals(projectName)
        && collection.getResultName().equals(resultName)
        && collection.getCollection().getDescription().equals(description)
        && collection.getCollection().getId().equals(id)
        && collection.getCollection().getVersion().equals(version)
        && collection.getCollection().getCitations().equals(citations)
        && collection.getCollection().getOverwrite().equals(OverwriteMerge.OVERWRITE_IF_EXISTS));
  }

  @Test
  public void SynBioHubSubmission_MockNewCollectionDescriptor_ShouldDeserialize()
      throws JsonMappingException, JsonProcessingException, MalformedURLException {
    final String registry = "https://synbiohub.programmingbiology.org";
    final String projectName = "foo";
    final String resultName = "foo.xml";
    final String name = "myCollection";
    final String description = "Some collection.";
    final String id = "myCollection";
    final String version = "1";
    final String citations = "Science, 2016";
    final String overwrite = "1";
    final String fmt =
        "{ \"registry\": \"%s\", \"project\": \"%s\", \"result\": \"%s\", \"collection\": { \"name\": \"%s\", \"description\": \"%s\", \"id\": \"%s\", \"version\": \"%s\", \"citations\": \"%s\", \"overwrite\": \"%s\" } }";
    final String json =
        String.format(
            fmt,
            registry,
            projectName,
            resultName,
            name,
            description,
            id,
            version,
            citations,
            overwrite);
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<SynBioHubSubmission<NewCollectionDescriptor>> typeRef =
        new TypeReference<SynBioHubSubmission<NewCollectionDescriptor>>() {};
    SynBioHubSubmission<NewCollectionDescriptor> collection = mapper.readValue(json, typeRef);
    assert (collection.getRegistry().equals(new URL(registry))
        && collection.getProjectName().equals(projectName)
        && collection.getResultName().equals(resultName)
        && collection.getCollection().getDescription().equals(description)
        && collection.getCollection().getId().equals(id)
        && collection.getCollection().getVersion().equals(version)
        && collection.getCollection().getCitations().equals(citations)
        && collection.getCollection().getOverwrite().equals(OverwriteMerge.OVERWRITE_IF_EXISTS));
  }
}

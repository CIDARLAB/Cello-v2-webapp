/**
 * Copyright (C) 2020 Boston University (BU)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cellocad.v2.webapp.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.cellocad.v2.DNACompiler.runtime.environment.DNACompilerRuntimeEnv;
import org.cellocad.v2.webapp.ApplicationUtils;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.specification.library.serialization.HeaderSerializationConstants;
import org.cellocad.v2.webapp.specification.library.serialization.LibrarySerializationConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Utils for application resources.
 *
 * @author Timothy Jones
 *
 * @date 2020-03-08
 *
 */
public class ResourceUtils {

    private static String getTargetDataResourcesDirectory() {
        String rtn = "";
        rtn = ApplicationUtils.getResourcesDirectory() + Utils.getFileSeparator() + "target_data";
        return rtn;
    }

    private static String getUserConstraintsFileResourcesDirectory() {
        String rtn = "";
        rtn = getTargetDataResourcesDirectory() + Utils.getFileSeparator() + "ucf";
        return rtn;
    }

    public static String getUserConstraintsFileMetaDataFile() {
        String rtn = "";
        rtn = getUserConstraintsFileResourcesDirectory() + Utils.getFileSeparator() + "metadata.json";
        return rtn;
    }

    private static String getInputSensorFileResourcesDirectory() {
        String rtn = "";
        rtn = getTargetDataResourcesDirectory() + Utils.getFileSeparator() + "input";
        return rtn;
    }

    public static String getInputSensorFileMetaDataFile() {
        String rtn = "";
        rtn = getInputSensorFileResourcesDirectory() + Utils.getFileSeparator() + "metadata.json";
        return rtn;
    }

    private static String getOutputDeviceFileResourcesDirectory() {
        String rtn = "";
        rtn = getTargetDataResourcesDirectory() + Utils.getFileSeparator() + "output";
        return rtn;
    }

    public static String getOutputDeviceFileMetaDataFile() {
        String rtn = "";
        rtn = getOutputDeviceFileResourcesDirectory() + Utils.getFileSeparator() + "metadata.json";
        return rtn;
    }

    public static String getSettingsResourcesDirectory() {
        String rtn = "";
        rtn = ApplicationUtils.getResourcesDirectory() + Utils.getFileSeparator() + "settings";
        return rtn;
    }

    public static String getSettingsFile() {
        String rtn = "";
        rtn = getSettingsResourcesDirectory() + Utils.getFileSeparator() + "settings.json";
        return rtn;
    }

    private static void initMetaDataFile(String filepath) throws IOException {
        Utils.createFileIfNonExistant(filepath);
        // write empty array
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arr = mapper.createArrayNode();
        Utils.writeToFile(arr.toString(), filepath);
    }

    private static void appendToMetaDataFile(String filepath, File resource, JsonNode header)
            throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode node = null;
        try {
            node = (ArrayNode) mapper.readTree(new File(filepath));
        } catch (IOException e) {
            throw new RuntimeException("Error with file.");
        }
        ObjectNode obj = mapper.createObjectNode();
        obj.put("file", resource.getName());
        obj.set("header", header);
        node.add(obj);
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(filepath), node);
        node = (ArrayNode) mapper.readTree(new File(filepath));
    }

    private static void initTargetDataResources(String dir, String filepath, String pattern) throws IOException {
        Utils.createDirectoryIfNonExistant(dir);
        initMetaDataFile(filepath);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resources[] = resolver.getResources(pattern);
        ObjectMapper mapper = new ObjectMapper();
        for (Resource r : resources) {
            File f = new File(dir + Utils.getFileSeparator() + r.getFilename());
            FileUtils.copyInputStreamToFile(r.getInputStream(), f);
            JsonNode node = mapper.readTree(f);
            JsonNode collection = null;
            for (JsonNode n : node) {
                if (n.get(LibrarySerializationConstants.S_UCF_COLLECTION).asText()
                        .equals(HeaderSerializationConstants.S_UCF_COLLECTION)) {
                    collection = n;
                    break;
                }
            }
            appendToMetaDataFile(filepath, f, collection);
        }
    }

    private static void initTargetDataResources() throws IOException {
        Utils.createDirectoryIfNonExistant(getTargetDataResourcesDirectory());
        String dir = "";
        String metadata = "";
        // user constraints
        dir = getUserConstraintsFileResourcesDirectory();
        metadata = getUserConstraintsFileMetaDataFile();
        initTargetDataResources(dir, metadata, "classpath:/lib/files/v2/ucf/**/*.UCF.json");
        // input sensor
        dir = getInputSensorFileResourcesDirectory();
        metadata = getInputSensorFileMetaDataFile();
        initTargetDataResources(dir, metadata, "classpath:/lib/files/v2/input/**/*.input.json");
        // output device
        dir = getOutputDeviceFileResourcesDirectory();
        metadata = getOutputDeviceFileMetaDataFile();
        initTargetDataResources(dir, metadata, "classpath:/lib/files/v2/output/**/*.output.json");
    }

    private static ObjectNode getStageNode(String name, ArrayNode stages) {
        ObjectNode rtn = null;
        for (JsonNode node : stages) {
            if (node.get("name").asText().equals(name)) {
                rtn = (ObjectNode) node;
                break;
            }
        }
        if (rtn == null) {
            rtn = stages.addObject();
            rtn.put("name", name);
            rtn.putArray("algorithms");
        }
        return rtn;
    }

    private static Boolean isDefaultAlgo(String stage, String algo, JsonNode Configuration) {
        Boolean rtn = false;
        for (JsonNode node : Configuration.get("stages")) {
            if (node.get("name").asText().equals(stage) && node.get("algorithm_name").asText().equals(algo)) {
                rtn = true;
                break;
            }
        }
        return rtn;
    }

    private static void initSettingsFile() throws IOException {
        InputStream is = DNACompilerRuntimeEnv.class.getClassLoader().getResourceAsStream("Configuration.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode Configuration = mapper.readTree(is);
        ObjectNode settings = mapper.createObjectNode();
        ArrayNode applications = settings.putArray("applications");
        ObjectNode DNACompiler = applications.addObject();
        DNACompiler.put("name", "DNACompiler");
        ArrayNode stages = DNACompiler.putArray("stages");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
                DNACompilerRuntimeEnv.class.getClassLoader());
        Resource resources[] = resolver.getResources("classpath:/algorithms/**/*.json");
        for (Resource r : resources) {
            InputStream i = r.getInputStream();
            File algo = r.getFile().getParentFile();
            File stage = algo.getParentFile().getParentFile();
            JsonNode refAlgoNode = mapper.readTree(i);
            ObjectNode stageNode = getStageNode(stage.getName(), stages);
            ArrayNode algorithms = (ArrayNode) stageNode.get("algorithms");
            ObjectNode algoNode = algorithms.addObject();
            algoNode.set("name", refAlgoNode.get("name"));
            algoNode.set("parameters", refAlgoNode.get("parameters"));
        }
        for (JsonNode node : Configuration.get("stages")) {
            ObjectNode stageNode = getStageNode(node.get("name").asText(), stages);
            stageNode.set("default", node.get("algorithm_name"));
        }
        settings.put("default", "DNACompiler");
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(getSettingsFile()), settings);
    }

    private static void initSettingsResources() throws IOException {
        Utils.createDirectoryIfNonExistant(getSettingsResourcesDirectory());
        initSettingsFile();
    }

    public static void initResources() throws IOException {
        Utils.createDirectoryIfNonExistant(ApplicationUtils.getResourcesDirectory());
        initTargetDataResources();
        initSettingsResources();
    }

}

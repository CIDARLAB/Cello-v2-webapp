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
package org.cellocad.v2.webapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test the
 * <code>{@link org.cellocad.v2.webapp.ResourceController.ResourcesController ResourceController}</code>
 * class.
 *
 * @author Timothy Jones
 *
 * @date 2020-03-07
 *
 */
public class ResourceControllerTest {

    @InjectMocks
    private ResourceController resourcesController;

    private MockMvc mockMvc;

    /**
     * Create mock controller.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resourcesController).build();
    }

    /**
     * Test user constraints files retrieval.
     *
     * @throws Exception
     */
    // TODO: repair filepath
    // @Test
    public void userConstraintsFiles_MockResponse_ShouldReturnExpectedFiles() throws Exception {
        this.mockMvc.perform(get("/resources/user_constraints_files"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"name\":\"Bth1C1G1T1.UCF.json\"},{\"name\":\"Eco1C1G1T1.UCF.json\"},{\"name\":\"Eco1C2G2T2.UCF.json\"},{\"name\":\"Eco2C1G3T1.UCF.json\"},{\"name\":\"SC1C1G1T1.UCF.json\"}]"));
    }

    /**
     * Test input sensor files listing.
     *
     * @throws Exception
     */
    @Test
    public void inputSensorFiles_MockResponse_ShouldReturnExpectedFiles() throws Exception {
        this.mockMvc.perform(get("/resources/input_sensor_files"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"name\":\"Bth1C1G1T1.input.json\"},{\"name\":\"Eco1C1G1T1.input.json\"},{\"name\":\"Eco1C2G2T2.input.json\"},{\"name\":\"Eco2C1G3T1.input.json\"},{\"name\":\"SC1C1G1T1.input.json\"}]"));
    }

    /**
     * Test output device files listing.
     *
     * @throws Exception
     */
    @Test
    public void outputDeviceFiles_MockResponse_ShouldReturnExpectedFiles() throws Exception {
        this.mockMvc.perform(get("/resources/output_device_files"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"name\":\"Bth1C1G1T1.output.json\"},{\"name\":\"Eco1C1G1T1.output.json\"},{\"name\":\"Eco1C2G2T2.output.json\"},{\"name\":\"Eco2C1G3T1.output.json\"},{\"name\":\"SC1C1G1T1.output.json\"}]"));
    }

}

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

import org.cellocad.v2.webapp.common.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ResourceControllerTest {

	@InjectMocks
	private ResourceController resourcesController;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Create mock controller.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.resourcesController).build();
	}

	/**
	 * Test user constraints files retrieval.
	 *
	 * @throws Exception
	 */
	@Test
	public void userConstraintsFiles_MockResponse_ShouldReturnExpectedFiles() throws Exception {
		String str = Utils.getResourceAsString("userConstraintsFiles_MockResponse_ShouldReturnExpectedFiles.json");
		this.mockMvc.perform(get("/resources/user_constraints_files"))
		        .andExpect(status().isOk())
		        .andExpect(content().json(str));
	}

	/**
	 * Test input sensor files listing.
	 *
	 * @throws Exception
	 */
	@Test
	public void inputSensorFiles_MockResponse_ShouldReturnExpectedFiles() throws Exception {
		String str = Utils.getResourceAsString("inputSensorFiles_MockResponse_ShouldReturnExpectedFiles.json");
		this.mockMvc.perform(get("/resources/input_sensor_files"))
		        .andExpect(status().isOk())
		        .andExpect(content().json(str));
	}

	/**
	 * Test output device files listing.
	 *
	 * @throws Exception
	 */
	@Test
	public void outputDeviceFiles_MockResponse_ShouldReturnExpectedFiles() throws Exception {
		String str = Utils.getResourceAsString("outputDeviceFiles_MockResponse_ShouldReturnExpectedFiles.json");
		this.mockMvc.perform(get("/resources/output_device_files"))
		        .andExpect(status().isOk())
		        .andExpect(content().json(str));
	}

	/**
	 * Test available settings listing.
	 *
	 * @throws Exception
	 */
	@Test
	public void settings_MockResponse_ShouldReturnExpectedJson() throws Exception {
		String str = Utils.getResourceAsString("settings_MockResponse_ShouldReturnExpectedJson.json");
		this.mockMvc.perform(get("/resources/settings"))
		        .andExpect(status().isOk())
		        .andExpect(content().json(str));
	}

}

/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.prediction.appengine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.services.prediction.Prediction;
import com.google.api.services.prediction.model.Input;
import com.google.api.services.prediction.model.Input.InputInput;
import com.google.api.services.prediction.model.Output;

/**
 * Entry servlet for the Prediction API App Engine Sample. Demonstrates how to
 * make an authenticated API call using OAuth 2 helper classes.
 */
public class APFoodPredictorServlet extends
		AbstractAppEngineAuthorizationCodeServlet {

	/**
	 * Be sure to specify the name of your application. If the application name
	 * is {@code null} or blank, the application will log a warning. Suggested
	 * format is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "gleaming-bus-576";
	private static final String ID = "";

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Get the stored credentials using the Authorization Flow
		AuthorizationCodeFlow authFlow = initializeFlow();
		Credential credential = authFlow.loadCredential(getUserId(req));
		// Build the Prediction object using the credentials
		@SuppressWarnings("unused")
		Prediction prediction = new Prediction.Builder(Utils.HTTP_TRANSPORT,
				Utils.JSON_FACTORY, credential).setApplicationName(
				APPLICATION_NAME).build();

		// Add the code to make an API call here.
		Input input = new Input();
		InputInput inputInput = new InputInput();
		List<Object> params = new ArrayList<Object>();
		params.add("GLPS- NELGULI,PUJA SAMBAR - 1200");
		inputInput.setCsvInstance(params);
		input.setInput(inputInput);
		Output predict = prediction.trainedmodels()
				.predict(APPLICATION_NAME, "fooddata40", input).execute();

		// Send the results as the response
		resp.setStatus(200);
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		writer.println(predict);
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException {
		return Utils.initializeFlow();
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		return Utils.getRedirectUri(req);
	}
}

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static final String LEARNING_DATASET = "fooddata3";
	/**
	 * Be sure to specify the name of your application. If the application name
	 * is {@code null} or blank, the application will log a warning. Suggested
	 * format is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "gleaming-bus-576";
	private static final String ID = "";
	private static final Map<String, List<String>> kitchenToSchoolMap = new HashMap<String, List<String>>();

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

		String kitchen = req.getParameter("Kitchen");
		String date = req.getParameter("Date");
		String foodItem1 = req.getParameter("FoodItem1");
		String foodItem2 = req.getParameter("FoodItem2");

		List<String> schools = kitchenToSchoolMap.get(kitchen);

		Double itemOneQuantity = 0.0;
		Double itemTwoQuantity = 0.0;
		for (String school : schools) {
			itemOneQuantity =+ getPrediction(prediction, date, school, foodItem1);
			itemTwoQuantity =+ getPrediction(prediction, date, school, foodItem2);
		}
		
		// Send the results as the response
		resp.setStatus(200);
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		writer.println(String.format("itemOneQuantity=%s, itemTwoQuantity=%s", itemOneQuantity, itemTwoQuantity));
	}

	private Double getPrediction(Prediction prediction, String dateStr,
			String school, String foodItem) throws IOException {
		Input input = new Input();
		InputInput inputInput = new InputInput();
		List<Object> params = new ArrayList<Object>();

		Date date = new Date();
		try {
			date = new SimpleDateFormat("dd-mm-yy").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String csvInput = String.format("Saturday, %s, %s", school, foodItem) ;
		params.add(csvInput);
		inputInput.setCsvInstance(params);
		input.setInput(inputInput);
		Output predict = prediction.trainedmodels()
				.predict(APPLICATION_NAME, LEARNING_DATASET, input).execute();

		String predOutputStr = predict.getOutputValue();
		Double pridictedQuantity = Double.parseDouble(predOutputStr);
		return pridictedQuantity;
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

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		loadMasterData();
	}

	private void loadMasterData() {
		List<String> schools = new ArrayList<String>();
		schools.add("DAYANANDA HPS - BENDRENAGARA");
		schools.add("DAYANADA HS - CHANDRA NAGARA");
		schools.add("GHPS CHANDRA NAGAR");
		schools.add("GHPS - KUMARASWAMY LAYOUT");
		schools.add("GLPS-GOWDANAPALYA");
		schools.add("GHPS-CHIKKALASANDRA");
		schools.add("SRI BANASHANKARI HPS - BENDRENAGARA");
		schools.add("BANASHANKARI  HS-BENDRE NAGARA");
		schools.add("BHUVANESHSWARI HPS - BENDRENAGARA");
		schools.add("GHPS- KADERNAHALLI");
		schools.add("SAHAKARI VIDAY KENDRA HPS - PADMANABHANGARA");
		schools.add("SAHAKARI VIDAY KENDRA HS - PADMANABHANGARA");
		schools.add("GHPS- KATTRIGUPPE");
		schools.add("GHPS- GIRINAGAR GUTTE");
		schools.add("GHPS- VEERABHADRANAGARA");
		
		kitchenToSchoolMap.put("IND-13-VKH-009814", schools);
	}
}

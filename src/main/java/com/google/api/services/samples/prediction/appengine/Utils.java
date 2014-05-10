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

import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.prediction.PredictionScopes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

class Utils {

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static final AppEngineDataStoreFactory DATA_STORE_FACTORY =
      AppEngineDataStoreFactory.getDefaultInstance();

  private static GoogleClientSecrets clientSecrets = null;
  static final String MAIN_SERVLET_PATH = "/APFoodPredictor-Servlet";
  static final String AUTH_CALLBACK_SERVLET_PATH = "/oauth2callback";
  static final HttpTransport HTTP_TRANSPORT = new UrlFetchTransport();
  static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static GoogleClientSecrets getClientSecrets() throws IOException {
    if (clientSecrets == null) {
      clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
          new InputStreamReader(Utils.class.getResourceAsStream("/client_secrets.json")));
      Preconditions.checkArgument(!clientSecrets.getDetails().getClientId().startsWith("Enter ")
          && !clientSecrets.getDetails().getClientSecret().startsWith("Enter "),
          "Download client_secrets.json file from "
          + "https://code.google.com/apis/console/?api=prediction#project:132487485817 into "
          + "src/main/resources/client_secrets.json");
    }
    return clientSecrets;
  }

  static GoogleAuthorizationCodeFlow initializeFlow() throws IOException {
    // Ask for only the permissions you need. Asking for more permissions will reduce the number of
    // users who finish the process for giving you access to their accounts. It will also increase
    // the amount of effort you will have to spend explaining to users what you are doing with their
    // data.
    // Here we are listing all of the available scopes. You should remove scopes that you are not
    // actually using.
    Set<String> scopes = new HashSet<String>();
    scopes.add(PredictionScopes.DEVSTORAGE_FULL_CONTROL);
    scopes.add(PredictionScopes.DEVSTORAGE_READ_ONLY);
    scopes.add(PredictionScopes.DEVSTORAGE_READ_WRITE);
    scopes.add(PredictionScopes.PREDICTION);

    return new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, getClientSecrets(), scopes)
        .setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline")
        .build();
  }

  static String getRedirectUri(HttpServletRequest req) {
    GenericUrl requestUrl = new GenericUrl(req.getRequestURL().toString());
    requestUrl.setRawPath(AUTH_CALLBACK_SERVLET_PATH);
    return requestUrl.build();
  }
}

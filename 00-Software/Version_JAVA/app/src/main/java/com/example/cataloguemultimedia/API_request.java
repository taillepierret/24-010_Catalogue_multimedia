package com.example.cataloguemultimedia;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.cataloguemultimedia.data.FakeContents;
import com.example.cataloguemultimedia.data.Content;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class API_request
{
    static JSONArray jsonArray;
    static String ZtLink;
    // Méthode pour récupérer les données à partir de l'API
    public static void fetchDataFromZt(String query, String contentType, ApiCallback callback)
    {
        new GetDataTask(callback).execute(query, contentType);
    }

    public static void getZtLink(ApiGetZtLinkCallback callback)
    {
        new GetZtLinkTask(callback).execute();
    }

    // AsyncTask pour la requête en arrière-plan
    private static class GetDataTask extends AsyncTask<String, Void, String>
    {

        private ApiCallback callback;

        public GetDataTask(ApiCallback callback)
        {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params)
        {
            String result = "";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                if (Variable_Environnement.API_is_activated)
                {
                    // Construire l'URL avec les paramètres query et type
                    String urlString = Variable_Environnement.API_url+"/search?query=" + params[0] + "&type=" + params[1]; //TODO à mettre dans un fichier de ressources
                    urlString = urlString.replaceAll("\\s+", "+");
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Lire la réponse
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    result = response.toString();
                }
                else
                {
                    // Utiliser les données de test
                    result = FakeContents.Fake_API_result;
                }

                Log.d("API Result", result);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (callback != null) {
                try {
                    // Convertir la réponse en JSON
                    JSONObject jsonResponse = new JSONObject(result);
                    String results = jsonResponse.getString("results");

                    // Convertir la chaîne JSON en un tableau JSON
                    jsonArray = new JSONArray(results);

                    // Retourner le tableau JSON à la méthode onSuccess
                    callback.onSuccess(jsonArray);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                }
            }
        }
    }

    // AsyncTask pour la requête en arrière-plan
    private static class GetZtLinkTask extends AsyncTask<String, Void, String>
    {
        private ApiGetZtLinkCallback callback;

        public GetZtLinkTask(ApiGetZtLinkCallback callback)
        {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params)
        {
            String result = "";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try
            {
                String urlString = Variable_Environnement.API_url + "/config/domain";
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Lire la réponse
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();

                Log.d("API Result", result);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (callback != null) {
                try {
                    // Convertir la réponse en JSON
                    JSONObject jsonResponse = new JSONObject(result);

                    ZtLink = jsonResponse.getString("domain");

                    // Retourner le tableau JSON à la méthode onSuccess
                    callback.onSuccess(ZtLink);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                }
            }
        }
    }
    public interface ApiGetZtLinkCallback {
        void onSuccess(String ZtLink);
        void onFailure(String error);

    }

    // Interface pour retourner les données à la classe appelante
    public interface ApiCallback {
        void onSuccess(JSONArray jsonArray);
        void onFailure(String error);

    }
}

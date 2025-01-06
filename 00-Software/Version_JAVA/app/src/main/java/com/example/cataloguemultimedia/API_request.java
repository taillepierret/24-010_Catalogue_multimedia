package com.example.cataloguemultimedia;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cataloguemultimedia.data.FakeContents;
import com.example.cataloguemultimedia.data.content;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class API_request
{
    static JSONArray jsonArray;
    // Méthode pour récupérer les données à partir de l'API
    public static void fetchData(String query, String contentType, ApiCallback callback)
    {
        new GetDataTask(callback).execute(query, contentType);
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
                // Construire l'URL avec les paramètres query et type
                String urlString = "http://192.168.1.62:5000/search?query=" + params[0] + "&type=" + params[1]; //TODO à mettre dans un fichier de ressources
                /*URL url = new URL(urlString);
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
                result = response.toString();*/

                result = FakeContents.Fake_API_result;

                Log.d("API Result", result);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            } finally
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

    // Interface pour retourner les données à la classe appelante
    public interface ApiCallback {
        void onSuccess(JSONArray jsonArray);
        void onFailure(String error);

    }
    public static ArrayList<content> getContents()
    {
        ArrayList<content> contents = new ArrayList<>();
        try
        {
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject item = jsonArray.getJSONObject(i);
                JSONArray noms = item.getJSONArray("nom");
                JSONArray bandeAudio = item.getJSONArray("bande_audio");
                JSONArray liens = item.getJSONArray("lien");
                JSONArray images = item.getJSONArray("image");
                JSONArray dates = item.getJSONArray("date_de_publication");

                // Créer un objet content pour chaque élément
                //content content = new content(noms.getString(0), bandeAudio.getString(0), liens.getString(0), images.getString(0), dates.getString(0));
                //contents.add(content);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return contents;
    }
}

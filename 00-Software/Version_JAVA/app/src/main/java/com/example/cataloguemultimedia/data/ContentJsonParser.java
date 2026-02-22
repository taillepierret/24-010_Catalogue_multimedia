package com.example.cataloguemultimedia.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentJsonParser
{
    public static ArrayList<Content> parseJSONContent(JSONArray jsonResult)
    {
        ArrayList<Content> contentList = new ArrayList<Content>();
        try
        {
            for (int i = 0; i < jsonResult.length(); i++)
            {
                JSONObject item = jsonResult.getJSONObject(i);
                JSONArray noms = item.getJSONArray("nom");
                JSONArray bandeAudio = item.getJSONArray("bande_audio");
                JSONArray liens = item.getJSONArray("lien");
                JSONArray images = item.getJSONArray("image");
                JSONArray dates = item.getJSONArray("date_de_publication");

                String title = noms.getString(0);
                String link_to_image = images.getString(0);
                ArrayList<String> link_to_download = new ArrayList<String>();
                ArrayList<String> soundtrack_available = new ArrayList<String>();
                String date = dates.getString(0);
                for (int j = 0; j < liens.length(); j++)
                {
                    link_to_download.add(liens.getString(j));
                }
                for (int j = 0; j < bandeAudio.length(); j++)
                {
                    soundtrack_available.add(bandeAudio.getString(j));
                }
                contentList.add(new Content(title, link_to_image, link_to_download, soundtrack_available,date));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return contentList;
    }
}

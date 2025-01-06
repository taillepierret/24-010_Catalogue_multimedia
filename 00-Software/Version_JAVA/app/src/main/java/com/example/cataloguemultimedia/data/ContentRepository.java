package com.example.cataloguemultimedia.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentRepository
{
    private static ArrayList<content> getAnimesContentList()
    {
        return FakeContents.getAnimesContents();
    }

    private static ArrayList<content> getMoviesContentList()
    {
        return FakeContents.getMoviesContents();
    }

    private static ArrayList<content> getSeriesContentList()
    {
        return FakeContents.getSeriesContents();
    }

    public static ArrayList<content> getContentList(content_type type, String seachQuery)
    {
        switch (type)
        {
            case MOVIE:
                return getMoviesContentList();
            case SERIES:
                return getSeriesContentList();
            case ANIME:
                return getAnimesContentList();
            default:
                return null;
        }
    }

    public static ArrayList<content> parseJSONContent(JSONArray jsonResult)
    {
        ArrayList<content> contentList = new ArrayList<content>();
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
                //String description = description.getString(0);
                ArrayList<String> link_to_image = new ArrayList<String>();
                ArrayList<String> link_to_download = new ArrayList<String>();
                ArrayList<String> soundtrack_available = new ArrayList<String>();
                String date = dates.getString(0);
                for (int j = 0; j < images.length(); j++)
                {
                    link_to_image.add(images.getString(j));
                }
                for (int j = 0; j < liens.length(); j++)
                {
                    link_to_download.add(liens.getString(j));
                }
                for (int j = 0; j < bandeAudio.length(); j++)
                {
                    soundtrack_available.add(bandeAudio.getString(j));
                }
                contentList.add(new content(title, link_to_image, link_to_download, soundtrack_available,date));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return contentList;
    }
}

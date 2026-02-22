package com.example.cataloguemultimedia.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Content
{
    private String title;
    private String description;
    private Content_type type;
    private String link_to_image;
    private ArrayList<String> link_to_download = new ArrayList<String>();
    private ArrayList<String> soundtrack_available = new ArrayList<String>();
    private String date;

    public Content(String title, String link_to_image, ArrayList<String> link_to_download, ArrayList<String> soundtrack_available, String date)
    {
        this.title = title;
        this.link_to_image = link_to_image;
        this.link_to_download = link_to_download;
        this.soundtrack_available = soundtrack_available;
        this.date = date;
    }

    public Content(String title, String description, Content_type type, String link_to_image, ArrayList<String> link_to_download, ArrayList<String> soundtrack_available)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        this.link_to_image = link_to_image;
        this.link_to_download = link_to_download;
        this.soundtrack_available = soundtrack_available;
    }

    public Content (String title, String description, Content_type type,ArrayList<String> soundtrack_available)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        this.soundtrack_available = soundtrack_available;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public Content_type getType()
    {
        return type;
    }

    public String getLinkToImage()
    {
        return link_to_image;
    }

    public ArrayList<String> getLinkToDownload()
    {
        return link_to_download;
    }

    public ArrayList<String> getSoundtrack()
    {
        return soundtrack_available;
    }

    public JSONObject toJSON()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("type", type.toString());
            jsonObject.put("link_to_image", new JSONArray(link_to_image));
            jsonObject.put("link_to_download", new JSONArray(link_to_download));
            jsonObject.put("soundtrack_available", new JSONArray(soundtrack_available));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

package com.metrics.rfa.news;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.metrics.rfa.framework.sub.SubAppContext;


/**
 * This class is responsible for building GUI to display news story data. The
 * story data are displayed after selecting a new headline.
 * 
 */
public class NewsStoryViewer
{
    NewsStoryViewer(SubAppContext appContext, String serviceName)
    {
        _appContext = appContext;
        _serviceName = serviceName;
        _story = new JTextArea(30, 80);
        _scroll = new JScrollPane(_story);
        _scroll.setAutoscrolls(true);
    }

    Component component()
    {
        return _scroll;
    }

    void setTabular(boolean tab)
    {
        if (tab)
            _story.setFont(MonospaceFont);
        else
            _story.setFont(MonospaceFont);
    }

    void openStory(String lang_ind, String pnac, String headline)
    {
        _story.setText(null);
        _story.append(headline);
        _story.append("\n");
        _story.append(pnac);
        _story.append("\n");
        if (_storyClient != null)
            _storyClient.cancel();
        //_storyClient = new NewsStoryClient(this, _appContext, _serviceName, lang_ind, pnac);
    }

    void appendStory(String story)
    {
        _story.append(story);
    }

    JTextArea _story;
    NewsStoryClient _storyClient;
    SubAppContext _appContext;
    String _serviceName;
    JScrollPane _scroll;
    
    static Font MonospaceFont = new Font("Monospaced", Font.PLAIN, 12);

}

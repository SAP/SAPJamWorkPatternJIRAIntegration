package com.sap.jam.samples.jira.plugin.issue;

import com.atlassian.applinks.api.ApplicationLinkService;
import com.atlassian.applinks.host.spi.HostApplication;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.sap.jam.samples.jira.plugin.odata.client.JamClient;
import com.sap.jam.samples.jira.plugin.odata.client.models.ExternalObject;
import java.util.Map;

public class FeedPanelContextProvider extends AbstractJiraContextProvider {

  private final JamClient jamClient;

  public FeedPanelContextProvider(
      ApplicationLinkService applicationLinkService,
      HostApplication hostApplication,
      IssueManager issueManager) {
    this.jamClient = new JamClient(applicationLinkService, hostApplication, issueManager);
  }

  public Map getContextMap(User user, JiraHelper jiraHelper) {
    Map contextMap = jiraHelper.getContextParams();

    Issue issue = (Issue) contextMap.get("issue");
    
    ExternalObject exObj = this.jamClient.getExternalObject(issue);
    
    contextMap.put("name", exObj.Name);
    contextMap.put("exidHtml", exObj.Exid);
    contextMap.put("objectTypeHtml", exObj.ObjectType);
    contextMap.put("linkHtml", exObj.ODataLink);
    contextMap.put("metadataHtml", exObj.ODataMetadata);
    contextMap.put("permalinkHtml", exObj.Permalink);
    contextMap.put("annotationsHtml", exObj.ODataAnnotations);
    
    contextMap.put("loginToken", jamClient.getSingleUseToken());

    contextMap.put("jamUrl", jamClient.getJamUrl());

    return contextMap;
  }
}

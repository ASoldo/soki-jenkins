package io.jenkins.plugins.soki;

import hudson.model.RootAction;
import java.io.IOException;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import hudson.model.RootAction;
import hudson.Extension;

@Extension
public class SokiPluginAction implements RootAction {

    private String name;

    public SokiPluginAction() {
        this.name = "Default Name";
    }

    public SokiPluginAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getIconFileName() {
        return "icon-document icon-md";
    }

    @Override
    public String getDisplayName() {
        return "Soki Plugin";
    }

    @Override
    public String getUrlName() {
        return "soki-plugin";
    }

    // Capture form input and update the name variable
    public void doSubmit(StaplerRequest req, StaplerResponse rsp) throws IOException {
        String inputValue = req.getParameter("myInput");
        if (inputValue != null && !inputValue.isEmpty()) {
            name = inputValue;
        }
        rsp.sendRedirect2(req.getContextPath() + "/soki-plugin");
    }
}

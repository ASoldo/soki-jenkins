package io.jenkins.plugins.soki;

import hudson.model.RootAction;
import java.io.IOException;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import hudson.model.RootAction;
import hudson.Extension;
import hudson.util.ListBoxModel;

@Extension
public class SokiPluginAction implements RootAction {

    private String name;
    private int numberValue;
    private int sliderValue;
    private String goalType;

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public ListBoxModel doFillGoalTypeItems() {
        ListBoxModel items = new ListBoxModel();

        items.add("Build Goal", "buildGoal");
        items.add("SpotBugs goal", "spotBugsGoal");

        return items;
    }

    public SokiPluginAction() {
        this.name = "Default Name";
        this.numberValue = 0;
        this.sliderValue = 50;
    }

    public SokiPluginAction(String name, int numberValue, int sliderValue) {
        this.name = name;
        this.numberValue = numberValue;
        this.sliderValue = sliderValue;
    }

    public String getName() {
        return name;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public int getSliderValue() {
        return sliderValue;
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

    // Capture form input and update the variables
    public void doSubmit(StaplerRequest req, StaplerResponse rsp) throws IOException {
        String inputValue = req.getParameter("myInput");
        String numberValue = req.getParameter("numberValue");
        String sliderValue = req.getParameter("sliderValue");

        if (inputValue != null && !inputValue.isEmpty()) {
            this.name = inputValue;
        }
        this.numberValue = Integer.parseInt(numberValue);
        this.sliderValue = Integer.parseInt(sliderValue);

        rsp.sendRedirect2(req.getContextPath() + "/soki-plugin");
    }
}

package io.jenkins.plugins.soki;

import hudson.model.AbstractProject;
import hudson.EnvVars;
import hudson.Launcher;
import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.ListBoxModel;
import hudson.FilePath;
import hudson.model.TaskListener;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;
import org.jenkinsci.Symbol;
import java.io.IOException;
import java.util.Map;

public class SokiPluginBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private final int numberValue;
    private final int sliderValue;
    private final String goalType;

    @DataBoundConstructor
    public SokiPluginBuilder(String name, int numberValue, int sliderValue, String goalType) {
        this.name = name;
        this.numberValue = numberValue;
        this.sliderValue = sliderValue;
        this.goalType = goalType;
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

    public String getGoalType() {
        return goalType;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("Soki Plugin is executing.");
        listener.getLogger().println("Name: " + name);
        listener.getLogger().println("Number: " + numberValue);
        listener.getLogger().println("Slider Value: " + sliderValue);
        listener.getLogger().println("Goal Type: " + goalType);

        // Inject the variables
        EnvVars env = run.getEnvironment(listener);
        env.put("PLUGIN_NAME", name);
        env.put("PLUGIN_NUMBER", String.valueOf(numberValue));
        env.put("PLUGIN_SLIDER", String.valueOf(sliderValue));
        env.put("PLUGIN_GOAL", goalType);

        // Print environment variables for logging purposes
        for (Map.Entry<String, String> entry : env.entrySet()) {
            listener.getLogger().println(entry.getKey() + " = " + entry.getValue());
        }
    }

    @Symbol("sokiPlugin")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Run Soki Plugin";
        }

        public ListBoxModel doFillGoalTypeItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("Build Goal", "buildGoal");
            items.add("SpotBugs Goal", "spotBugsGoal");
            return items;
        }
    }
}

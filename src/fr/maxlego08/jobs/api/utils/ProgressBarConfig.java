package fr.maxlego08.jobs.api.utils;

import com.google.common.base.Strings;

public record ProgressBarConfig(char symbol, String progressColor, String notCompletedColor, int size) {

    public String getProgressBar(double current, double max) {
        float percent = (float) current / (float) max;
        int progressBars = (int) ((float) size * percent);
        return Strings.repeat(progressColor + symbol, progressBars) + Strings.repeat(notCompletedColor + symbol, size - progressBars);
    }


}

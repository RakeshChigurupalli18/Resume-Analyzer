package com.resumeanalyzer.model;

import java.util.List;

public class JobMatch {
    private String role;
    private int matchPercent;
    private List<String> missingSkills;

    public JobMatch(String role, int matchPercent, List<String> missingSkills) {
        this.role = role;
        this.matchPercent = matchPercent;
        this.missingSkills = missingSkills;
    }

    public String getRole() { return role; }
    public int getMatchPercent() { return matchPercent; }
    public List<String> getMissingSkills() { return missingSkills; }
}

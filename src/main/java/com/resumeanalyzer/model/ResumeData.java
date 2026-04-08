package com.resumeanalyzer.model;

public class ResumeData {

    private String name;
    private String email;
    private String skills;
    private String education;
    private String experience;
    private String projects;
    private String suggestedRoles;
    private String suggestedCourses;
    private int resumeScore; // ✅ newly added field

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getProjects() { return projects; }
    public void setProjects(String projects) { this.projects = projects; }

    public String getSuggestedRoles() { return suggestedRoles; }
    public void setSuggestedRoles(String suggestedRoles) { this.suggestedRoles = suggestedRoles; }

    public String getSuggestedCourses() { return suggestedCourses; }
    public void setSuggestedCourses(String suggestedCourses) { this.suggestedCourses = suggestedCourses; }

    public int getResumeScore() { return resumeScore; }  // ✅ new
    public void setResumeScore(int resumeScore) { this.resumeScore = resumeScore; }  // ✅ new
}

package com.resumeanalyzer.util;
import com.resumeanalyzer.model.JobMatch;
import com.resumeanalyzer.model.ResumeData;
import java.util.*;


public class ResumeAnalyzer {

    public static void analyze(ResumeData data) {
        String skillsText = data.getSkills() != null ? data.getSkills().toLowerCase() : "";

        List<String> suggestedRoles = new ArrayList<>();
        List<String> suggestedCourses = new ArrayList<>();

        System.out.println("---- Analyzing Resume ----");
        System.out.println("Skills Text: " + skillsText);

        if (skillsText.contains("java") || skillsText.contains("spring")) {
            suggestedRoles.add("Java Backend Developer");
            suggestedCourses.add("Java Programming Masterclass");
            suggestedCourses.add("Spring Boot in Depth");
        }

        if (skillsText.contains("python") || skillsText.contains("machine learning")) {
            suggestedRoles.add("Data Scientist");
            suggestedCourses.add("Machine Learning by Andrew Ng");
            suggestedCourses.add("Python for Data Science");
        }

        if (skillsText.contains("html") || skillsText.contains("css") || skillsText.contains("javascript")) {
            suggestedRoles.add("Frontend Web Developer");
            suggestedCourses.add("Responsive Web Design by freeCodeCamp");
            suggestedCourses.add("JavaScript for Beginners");
        }

        if (skillsText.contains("sql") || skillsText.contains("database") || skillsText.contains("mysql")) {
            suggestedRoles.add("Database Administrator");
            suggestedCourses.add("SQL for Beginners");
        }

        if (skillsText.contains("cloud") || skillsText.contains("aws") || skillsText.contains("azure")) {
            suggestedRoles.add("Cloud Engineer");
            suggestedCourses.add("AWS Certified Solutions Architect");
            suggestedCourses.add("Azure Fundamentals");
        }

        if (suggestedRoles.isEmpty()) {
            suggestedRoles.add("General Software Developer");
            suggestedCourses.add("Problem Solving on LeetCode");
        }

        // ✅ Store analysis results
        data.setSuggestedRoles(String.join(", ", suggestedRoles));
        data.setSuggestedCourses(String.join(", ", suggestedCourses));

        // ✅ Debug outputs
        System.out.println("Suggested Roles: " + data.getSuggestedRoles());
        System.out.println("Suggested Courses: " + data.getSuggestedCourses());

        // ✅ Score calculation
        int score = 0;

        if (!data.getName().equalsIgnoreCase("Unknown")) {
            score += 10;
            System.out.println("✔ Name found (+10)");
        }
        if (!data.getEmail().equalsIgnoreCase("Not Found")) {
            score += 10;
            System.out.println("✔ Email found (+10)");
        }
        if (data.getSkills() != null && !data.getSkills().equalsIgnoreCase("Not Found")) {
            score += 25;
            System.out.println("✔ Skills found (+25)");
        }
        if (data.getProjects() != null && !data.getProjects().equalsIgnoreCase("Not Found")) {
            score += 20;
            System.out.println("✔ Projects found (+20)");
        }
        if (data.getEducation() != null && !data.getEducation().equalsIgnoreCase("Not Found")) {
            score += 15;
            System.out.println("✔ Education found (+15)");
        }
        if (data.getExperience() != null && !data.getExperience().equalsIgnoreCase("Not Found")) {
            score += 20;
            System.out.println("✔ Experience found (+20)");
        } else {
            System.out.println("❌ Experience NOT found (0)");
        }

        if (score > 100) score = 100;
        data.setResumeScore(score);

        System.out.println("✅ Final Resume Score: " + score + " / 100");
        System.out.println("---------------------------");
    }

public static List<JobMatch> getJobMatches(String skillsText) {
    skillsText = skillsText.toLowerCase();
    List<JobMatch> matches = new ArrayList<>();

    Map<String, List<String>> jobSkills = new LinkedHashMap<>();
    jobSkills.put("Network Engineer", Arrays.asList(
        "tcp/ip", "subnetting", "routing", "ospf", "rip", "vlan",
        "packet tracer", "linux", "dhcp", "nat", "firewall"
    ));
    jobSkills.put("Java Backend Developer", Arrays.asList(
        "java", "jsp", "servlets", "mysql", "rest", "spring boot", "hibernate", "maven"
    ));
    jobSkills.put("Frontend Web Developer", Arrays.asList(
        "html", "css", "javascript", "react", "bootstrap"
    ));
    jobSkills.put("Database Administrator", Arrays.asList(
        "sql", "mysql", "oracle", "indexing", "triggers", "backup", "recovery"
    ));
    jobSkills.put("Data Scientist", Arrays.asList(
        "python", "sql", "pandas", "numpy", "scikit-learn", "jupyter", "machine learning"
    ));
    jobSkills.put("Cybersecurity Analyst", Arrays.asList(
        "network security", "nmap", "wireshark", "firewall", "tls", "owasp", "ids", "ips"
    ));

    for (Map.Entry<String, List<String>> entry : jobSkills.entrySet()) {
        String role = entry.getKey();
        List<String> requiredSkills = entry.getValue();

        int matched = 0;
        List<String> missing = new ArrayList<>();

        for (String skill : requiredSkills) {
            if (skillsText.contains(skill)) {
                matched++;
            } else {
                missing.add(skill);
            }
        }

        int matchPercent = (int) ((matched * 100.0) / requiredSkills.size());
        matches.add(new JobMatch(role, matchPercent, missing));
    }

    return matches;
}

}
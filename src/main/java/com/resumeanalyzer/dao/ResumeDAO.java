package com.resumeanalyzer.dao;

import com.resumeanalyzer.model.ResumeData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class ResumeDAO {

    // Save ResumeData and return generated resume ID (needed for job_match FK)
    public static int save(ResumeData data) {
        String sql = "INSERT INTO resumes (name, email, skills, education, experience, projects, suggested_roles, suggested_courses, resume_score) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, data.getName());
            ps.setString(2, data.getEmail());
            ps.setString(3, data.getSkills());
            ps.setString(4, data.getEducation());
            ps.setString(5, data.getExperience());
            ps.setString(6, data.getProjects());
            ps.setString(7, data.getSuggestedRoles());
            ps.setString(8, data.getSuggestedCourses());
            ps.setInt(9, data.getResumeScore());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Saving resume failed, no rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedId;
    }
}

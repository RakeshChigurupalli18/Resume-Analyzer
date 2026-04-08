package com.resumeanalyzer.controller;

import com.resumeanalyzer.model.ResumeData;
import com.resumeanalyzer.model.JobMatch;
import com.resumeanalyzer.util.ResumeAnalyzer;
import com.resumeanalyzer.dao.ResumeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/AnalyzeResume")
public class AnalyzeResumeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract resume data from request parameters (adjust field names as per your form)
        ResumeData data = new ResumeData();
        data.setName(request.getParameter("name"));
        data.setEmail(request.getParameter("email"));
        data.setSkills(request.getParameter("skills"));
        data.setEducation(request.getParameter("education"));
        data.setExperience(request.getParameter("experience"));
        data.setProjects(request.getParameter("projects"));

        // Analyze the resume data (sets suggested roles, courses, and score)
        ResumeAnalyzer.analyze(data);

        // Get job matches based on skills
        List<JobMatch> matchList = ResumeAnalyzer.getJobMatches(data.getSkills());

      

        // Save resume data to the database
        ResumeDAO.save(data);

        // Set attributes to pass data to JSP
        request.setAttribute("data", data);
        request.setAttribute("matchList", matchList);
       
        // Forward the request to summary.jsp for displaying results
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}

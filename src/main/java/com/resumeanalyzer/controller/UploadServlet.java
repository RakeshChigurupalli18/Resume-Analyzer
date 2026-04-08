package com.resumeanalyzer.controller;

import com.resumeanalyzer.model.ResumeData;
import com.resumeanalyzer.model.JobMatch;
import com.resumeanalyzer.util.ResumeAnalyzer;
import com.resumeanalyzer.util.ResumeParser;
import com.resumeanalyzer.dao.ResumeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5 MB max
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("resume");
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();

        ResumeData data;

        if (fileName.endsWith(".pdf")) {
            data = ResumeParser.parsePDF(fileContent);
        } else if (fileName.endsWith(".docx")) {
            data = ResumeParser.parseDOCX(fileContent);
        } else {
            request.setAttribute("error", "Unsupported file format. Please upload PDF or DOCX.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        // Analyze the resume for score and suggestions
        ResumeAnalyzer.analyze(data);

        // Get job match percentage and missing skills
        List<JobMatch> matchList = ResumeAnalyzer.getJobMatches(data.getSkills());

        // Store results in request and session
        request.setAttribute("data", data);
        request.setAttribute("matchList", matchList);
        request.getSession().setAttribute("data", data);
        request.getSession().setAttribute("matchList", matchList);

        // Save to database
        ResumeDAO.save(data);

        // Forward to result.jsp
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
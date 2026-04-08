<%@ page import="java.util.List" %>
<%@ page import="com.resumeanalyzer.model.ResumeData" %>
<%@ page import="com.resumeanalyzer.model.JobMatch" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
    public String safeDisplay(String s) {
        return (s != null && !s.trim().isEmpty()) ? s : "Not provided";
    }
%>

<%
    ResumeData data = (ResumeData) request.getAttribute("data");
    if (data == null) {
%>
    <h2 style="color:red; text-align:center;">⚠ Resume data not found. Please re-upload your resume.</h2>
<%
        return;
    }

    int score = data.getResumeScore();
    String color = (score >= 80) ? "#28a745" : (score >= 50) ? "#ffc107" : "#dc3545";

    List<JobMatch> matchList = (List<JobMatch>) request.getAttribute("matchList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resume Analyzer - Result</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #74ebd5, #ACB6E5);
            margin: 0;
            padding: 40px 10px;
        }

        .card {
            background: #fff;
            padding: 30px;
            max-width: 900px;
            margin: auto;
            border-radius: 20px;
            box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
            animation: fadeIn 0.5s ease-in-out;
        }

        h2 {
            text-align: center;
            color: #6a11cb;
            font-size: 28px;
            margin-bottom: 25px;
        }

        .info {
            background: #f7f9fc;
            padding: 12px 18px;
            border-left: 6px solid #6c63ff;
            border-radius: 6px;
            margin-bottom: 12px;
        }

        .label {
            font-weight: 600;
            color: #2c3e50;
        }

        .score-box {
            background: <%= color %>;
            color: white;
            font-size: 20px;
            font-weight: bold;
            text-align: center;
            padding: 15px;
            border-radius: 12px;
            margin: 25px 0;
            box-shadow: inset 0 0 10px rgba(255,255,255,0.2);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th {
            background: linear-gradient(to right, #7f53ac, #647dee);
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            background: #fff;
            padding: 10px 12px;
            border: 1px solid #ddd;
        }

        tr:nth-child(even) td {
            background: #f0f6ff;
        }

        .verdict-strong { color: #28a745; font-weight: bold; }
        .verdict-medium { color: #ffc107; font-weight: bold; }
        .verdict-weak { color: #dc3545; font-weight: bold; }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .card {
                padding: 20px;
            }

            h2 {
                font-size: 24px;
            }

            .score-box {
                font-size: 18px;
            }
        }
    </style>
</head>
<body>

<div class="card">
    <h2>📊 Resume Analysis Result</h2>

    <div class="info"><span class="label">Name:</span> <%= safeDisplay(data.getName()) %></div>
    <div class="info"><span class="label">Email:</span> <%= safeDisplay(data.getEmail()) %></div>
    <div class="info"><span class="label">Skills:</span> <%= safeDisplay(data.getSkills()) %></div>
    <div class="info"><span class="label">Education:</span> <%= safeDisplay(data.getEducation()) %></div>
    <div class="info"><span class="label">Experience:</span> <%= safeDisplay(data.getExperience()) %></div>
    <div class="info"><span class="label">Projects:</span> <%= safeDisplay(data.getProjects()) %></div>
    <div class="info"><span class="label">Suggested Roles:</span> <%= safeDisplay(data.getSuggestedRoles()) %></div>
    <div class="info"><span class="label">Recommended Courses:</span> <%= safeDisplay(data.getSuggestedCourses()) %></div>

    <div class="score-box">
        🎯 Resume Score: <%= score %> / 100
    </div>

    <h3 style="color:#2c3e50; margin-top: 30px;">🎯 Job Role Match Summary</h3>
    <table>
        <tr>
            <th>🎯 Job Role</th>
            <th>📊 Match %</th>
            <th>❌ Missing Skills</th>
            <th>📝 Verdict</th>
        </tr>
        <%
            if (matchList != null && !matchList.isEmpty()) {
                for (JobMatch match : matchList) {
                    int percent = match.getMatchPercent();
                    String verdict = (percent >= 80) ? "⭐ Most Ready – You’re job-ready" :
                                     (percent >= 60) ? "✅ High Match – Learn key tools" :
                                     (percent >= 50) ? "⚠ Decent Start – Upskill needed" :
                                                       "🔻 Weak Match – Learn fundamentals";

                    String missing = String.join(", ", match.getMissingSkills());
                    String verdictClass = (percent >= 80) ? "verdict-strong" :
                                          (percent >= 50) ? "verdict-medium" : "verdict-weak";
        %>
        <tr>
            <td><%= match.getRole() %></td>
            <td><%= percent %> %</td>
            <td><%= missing.isEmpty() ? "None" : missing %></td>
            <td class="<%= verdictClass %>"><%= verdict %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4" style="text-align:center;">No job matches found.</td>
        </tr>
        <% } %>
    </table>
</div>

</body>
</html>

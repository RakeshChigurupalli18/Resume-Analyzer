<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Resume Analyzer - Upload</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Inter', sans-serif;
            background: linear-gradient(120deg, #d9a7c7, #fffcdc);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .upload-container {
            background: #ffffff;
            padding: 40px 50px;
            border-radius: 20px;
            box-shadow: 0 12px 50px rgba(0, 0, 0, 0.1);
            max-width: 520px;
            width: 100%;
            text-align: center;
            animation: fadeIn 0.6s ease-in-out;
        }

        h2 {
            font-size: 30px;
            color: #2d0353;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .subtitle {
            font-size: 14px;
            color: #666;
            margin-bottom: 25px;
        }

        label {
            display: block;
            text-align: left;
            font-weight: 600;
            margin-bottom: 8px;
            color: #333;
        }

        input[type="file"] {
            width: 100%;
            padding: 12px;
            border: 2px dashed #c4b5fd;
            border-radius: 12px;
            background-color: #fafbff;
            margin-bottom: 25px;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        input[type="file"]:hover {
            border-color: #8b5cf6;
        }

        input[type="submit"] {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 10px;
            background: linear-gradient(90deg, #7e30e1, #5b14b7);
            color: white;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s ease, background 0.3s ease;
        }

        input[type="submit"]:hover {
            background: linear-gradient(90deg, #6936d3, #3e0c9a);
            transform: scale(1.02);
        }

        .error-message {
            margin-top: 18px;
            color: #b30000;
            background-color: #ffe2e2;
            padding: 10px 15px;
            border: 1px solid #ff4d4d;
            border-radius: 6px;
            font-size: 14px;
        }

        .footer-note {
            margin-top: 30px;
            font-size: 12px;
            color: #888;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-15px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="upload-container">
    <h2>📄 Upload Your Resume</h2>
    <div class="subtitle">Get matched with jobs, see your resume score, and learn new skills.</div>

    <form action="UploadServlet" method="post" enctype="multipart/form-data">
        <label for="resumeInput">Upload File (PDF / DOCX):</label>
        <input id="resumeInput" type="file" name="resume" accept=".pdf,.docx" required />
        <input type="submit" value="🚀 Analyze Resume" />
    </form>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error-message"><%= error %></div>
    <%
        }
    %>

    <div class="footer-note">We value your privacy. No resumes are saved permanently.</div>
</div>

</body>
</html>

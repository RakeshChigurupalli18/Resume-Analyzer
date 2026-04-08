package com.resumeanalyzer.util;

import com.resumeanalyzer.model.ResumeData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeParser {

    // ✅ Parse PDF
    public static ResumeData parsePDF(InputStream inputStream) {
        ResumeData data = new ResumeData();

        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            fillDataFromText(text, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // ✅ Parse DOCX
    public static ResumeData parseDOCX(InputStream inputStream) {
        ResumeData data = new ResumeData();

        try (XWPFDocument doc = new XWPFDocument(inputStream)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String text = extractor.getText();
            fillDataFromText(text, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // ✅ Common parsing logic
    private static void fillDataFromText(String text, ResumeData data) {
        String cleanedText = text.replaceAll("\\r", "").replaceAll(" +", " ");
        String lowerText = cleanedText.toLowerCase();

        data.setEmail(extractEmail(cleanedText));
        data.setName(extractName(cleanedText));

        data.setSkills(extractSection(lowerText, "technical skills", "projects"));
        data.setProjects(extractSection(lowerText, "projects", "certifications"));
        data.setEducation(extractSection(lowerText, "education", "technical skills"));
        data.setExperience(extractSection(lowerText, "experience", "projects"));
    }

    private static String extractEmail(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "Not Found";
    }

    private static String extractName(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty() && !line.toLowerCase().contains("resume")) {
                return line;
            }
        }
        return "Unknown";
    }

    private static String extractSection(String text, String startHeader, String endHeader) {
        int start = text.indexOf(startHeader.toLowerCase());
        int end = text.indexOf(endHeader.toLowerCase());

        if (start == -1) return "Not Found";
        if (end == -1 || end <= start) end = start + 500;

        return text.substring(start, Math.min(end, text.length())).trim();
    }
}

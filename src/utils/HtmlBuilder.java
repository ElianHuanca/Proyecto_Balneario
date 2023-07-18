/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;

/**
 *
 * @author ELIAN
 */
public class HtmlBuilder {
    private static final String HTML_OPEN = "<!DOCTYPE html> <html lang=\"es\">";
    private static final String HTML_CLOSE = "</html>";
    
    private static final String HEAD_OPEN = "<head>";
    private static final String HEAD_CLOSE = "</head>";
    
    private static final String BODY_OPEN = "<body>";
    private static final String BODY_CLOSE = "</body>";
    
    private static final String BOOTSTRAP = "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
"    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>";

    public static String generateTable(String title, String[] headers, List<String[]> data) {
        String table_headers_html = "";
        for (String header : headers) {
            table_headers_html += "<th style=\"border: 1px solid black;\">" + header + "</th>";
        }

        String table_body_html = "";
        for (String[] element : data) {
            table_body_html += "<tr style=\"border: 1px solid black;\">";
            for (String value : element) {
                table_body_html += "<td style=\"border: 1px solid black ;\">" + value + "</td>";
            }
            table_body_html += "</tr>";
        }

        String html
                = "<center><h2>" + title + "</h2></center>"
                + "<table style=\"border: 1px solid black;\" bgcolor=\"#CCCCCC\">"
                + "<thead>"
                + table_headers_html
                + "</thead>"
                + "<tbody>"
                + table_body_html
                + "</tbody>"
                + "</table>";

        return insertInHtml(html);
    }
    
    public static String generateTableBootstrap(String title, String[] headers, List<String[]> data) {
        String table_headers_html = "";
        for (String header : headers) {
            table_headers_html += "<th>" + header + "</th>";
        }

        String table_body_html = "";
        for (String[] element : data) {
            table_body_html += "<tr>";
            for (String value : element) {
                table_body_html += "<td>" + value + "</td>";
            }
            table_body_html += "</tr>";
        }

        String html
                = "<center><h2>" + title + "</h2></center>"
                + "<table class=\"table table-dark table-striped\">"
                + "<thead>"
                + "<tr>"
                + table_headers_html
                + "</tr>"
                + "</thead>"
                + "<tbody>"
                + table_body_html
                + "</tbody>"
                + "</table>";

        return insertInHtml(html);
    }
    

    public static String generateText(String[] args) {
        String acumulative = "<center><h2>" + args[0] + "</h2></center>";
        for (int i = 1; i < args.length; i++) {
            acumulative += "<center><h3>" + args[i] + "</h3></center>";
        }
        return insertInHtml(acumulative);
    }
       
    public static String generateTableForSimpleData(String title, String[] headers, String[] data){
        String acumulative = "";
        
        for(int i = 0; i < headers.length; i++){
            acumulative += 
                    "<tr>" + 
                        "<td>" + headers[i] + "</td>" +  
                        "<td>" + data[i] + "</td>" +
                    "</tr>";
        }
        
        String table = 
                "<div align=\"center\">\n" +
                    "<h2>" + title + "<br>\n" +
                    "</h2>\n" +
                "</div>\n" +
                "<table width=\"250\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"2\" bgcolor=\"#CCCCCC\">\n" +
                    acumulative +
                "</table>";
        
        return insertInHtml(table);
    }
    
    private static String insertInHtml(String data) {
        //return HTML_OPEN + BODY_OPEN + data + BODY_CLOSE + HTML_CLOSE;
        return HTML_OPEN + HEAD_OPEN + BOOTSTRAP + HEAD_CLOSE + BODY_OPEN + data + BODY_CLOSE + HTML_CLOSE;
    }
}

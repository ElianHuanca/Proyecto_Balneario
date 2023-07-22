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

    private static final String HEAD_OPEN = "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <style type=\"text/css\">";
    private static final String HEAD_CLOSE = "</style>"
            + "</head>";

    private static final String BODY_OPEN = "<body>";
    private static final String BODY_CLOSE = "</body>";

    public static String generateTable(String title, String[] headers, List<String[]> data) {
        String table_headers_html = "<tr>";
        for (String header : headers) {
            table_headers_html += "<th>" + header + "</th>";
        }
        table_headers_html += "</tr>";

        String table_body_html = "";
        for (String[] element : data) {
            table_body_html += "<tr>";
            for (String value : element) {
                table_body_html += "<td>" + value + "</td>";
            }
            table_body_html += "</tr>";
        }
        String balneario = BalnearioHTML("");
        String body
                = " <table>\n"
                + " <caption>" + title + "</caption>\n"
                + "        <thead>\n"
                + table_headers_html
                + "        </thead>\n"
                + "        <tbody>\n"
                + table_body_html
                + "        </tbody>\n"
                + " </table>\n";
        body = balneario + body;
        String header = HeaderTable();
        return insertInHtml(header, body);
    }

    public static String BalnearioHTML(String graficas) {
        return "<div class=\"container\">\n"
                + "        <img \n"
                + "            src=\"https://scontent.fvvi1-1.fna.fbcdn.net/v/t39.30808-6/316683967_109743038627702_1616624302417732076_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=fDy8h_D60boAX99AVrO&_nc_ht=scontent.fvvi1-1.fna&oh=00_AfB6cnWdgqxwhlYJ6w1t_tC6mWw5C8cbrm90owLU9nqNVg&oe=64BDD5FD\"\n"
                + "            width=\"400\" height=\"200\" alt=\"\">\n"
                + "        <h1>Balneario Playa Caribe</h1>"
                + graficas
                + "</div>";
    }

    public static String generateText(String[] args) {
        String acumulative = "<center><h2>" + args[0] + "</h2></center>";
        for (int i = 1; i < args.length; i++) {
            acumulative += "<center><h3>" + args[i] + "</h3></center>";
        }
        return insertInHtml(acumulative);
    }

    public static String generateTableForSimpleData(String title, String[] headers, String[] data) {
        String acumulative = "";

        for (int i = 0; i < headers.length; i++) {
            acumulative
                    += "<tr>"
                    + "<td>" + headers[i] + "</td>"
                    + "<td>" + data[i] + "</td>"
                    + "</tr>";
        }

        String table
                = "<div align=\"center\">\n"
                + "<h2>" + title + "<br>\n"
                + "</h2>\n"
                + "</div>\n"
                + "<table width=\"250\"  border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"2\" bgcolor=\"#CCCCCC\">\n"
                + acumulative
                + "</table>";

        return insertInHtml(table);
    }

    private static String insertInHtml(String Body) {
        return HTML_OPEN + HEAD_OPEN + HeaderBalnearioFooter() + HEAD_CLOSE + BODY_OPEN + BalnearioHTML(Body) + FooterHTML()+ BODY_CLOSE + HTML_CLOSE;
    }

    private static String insertInHtml(String Header, String Body) {
        return HTML_OPEN + HEAD_OPEN + Header + HeaderBalnearioFooter() + HEAD_CLOSE + BODY_OPEN + Body + FooterHTML() + BODY_CLOSE + HTML_CLOSE;
    }

    private static String insertInHtml(String header, String body, String footer) {
        return HTML_OPEN + HEAD_OPEN + header + HEAD_CLOSE + BODY_OPEN + body + footer + BODY_CLOSE + HTML_CLOSE;
    }

    private static String HeaderTable() {
        return "table {\n"
                + "            margin-top: 30px;\n"
                + "            caption-side: top;\n"
                + "            margin-left: auto;\n"
                + "            margin-right: auto;\n"
                + "            border-collapse: collapse;\n"
                + "        }\n"
                + "\n"
                + "        th {\n"
                + "            background-color: black;\n"
                + "            color: white;\n"
                + "        }\n"
                + "\n"
                + "        caption {\n"
                + "            padding-bottom: 10px;\n"
                + "            font-family: Arial, Helvetica, Verdana;\n"
                + "            font-size: 25px;\n"
                + "        }\n"
                + "\n"
                + "        td,\n"
                + "        th {\n"
                + "            border: 2px solid black;\n"
                + "            padding-left: 20px;\n"
                + "            padding-right: 20px;\n"
                + "            padding-top: 10px;\n"
                + "            padding-bottom: 10px;\n"
                + "        }";
    }

    private static String HeaderBalnearioFooter() {
        return ".container {\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "\n"
                + "        .footer-container {\n"
                + "            display: flex;\n"
                + "            justify-content: center;            \n"
                + "            align-items: center;            \n"
                + "            flex-wrap: wrap;            \n"
                + "        }\n"
                + "\n"
                + "        .item {\n"
                + "            flex: 1;\n"
                + "            padding: 20px;\n"
                + "            text-align: center;            \n"
                + "        }\n"
                + "\n"
                + "        .column {\n"
                + "            display: flex;\n"
                + "            flex-direction: column;            \n"
                + "            align-items: center;            \n"
                + "        }\n"
                + "\n"
                + "        .item h5 {\n"
                + "            margin-bottom: 10px;\n"
                + "        }\n"
                + "\n"
                + "        .item img {\n"
                + "            max-width: 200px;\n"
                + "        }\n"
                + "\n"
                + "        .item a img {\n"
                + "            max-width: 400px;\n"
                + "        }";
    }

    private static String FooterHTML() {
        return "<footer id=\"footer\" class=\"footer-container\">\n"
                + "        <div class=\"item\">\n"
                + "            <h5>¿DONDE ESTAMOS?</h5>\n"
                + "            <a href=\"https://goo.gl/maps/JEzLJVeJAqVZwrWPA\"><img\n"
                + "                    src=\"https://sw1-proyects.s3.amazonaws.com/piscina/BalnearioGoogleMaps.png\" alt=\"\"></a>\n"
                + "        </div>\n"
                + "        <div class=\"item\">\n"
                + "            <h5>DESARROLLADO CON:</h5>\n"
                + "            <div class=\"column\">\n"
                + "                <img src=\"https://cloud2data.com/wp-content/uploads/2023/01/HTML-CSS-Review.png\" width=\"200px\">\n"
                + "                <img src=\"https://static.vecteezy.com/system/resources/previews/019/899/953/non_2x/java-free-download-free-png.png\"\n"
                + "                    width=\"200px\" alt=\"\">\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </footer>";
    }

    public static String generateGrafica(String title, List<String[]> data) {
        String encabezados = "";
        String valores = "";
        String encabezados2 = "";
        for (String[] element : data) {

            valores += element[1] + ",";

            encabezados += element[0] + "|";
            encabezados2 += element[0] + "%28" + element[1] + "+veces%29|";
        }
        valores = valores.substring(0, valores.length() - 1);
        encabezados = encabezados.substring(0, encabezados.length() - 1);
        encabezados2 = encabezados2.substring(0, encabezados2.length() - 1);
        String graficas = "<h2> " + title + "</h2>"
                + "<div>"
                + "<img src=\"http://chart.apis.google.com/chart?chs=600x200&cht=p&chd=t:" + valores + "&chl=" + encabezados + "\" width=\"600\" height=\"200\">"
                + "</div>\n"
                + "<div>"                
                + "<img src=\"http://chart.apis.google.com/chart?chs=600x200&cht=bhg&chco=e5f867|aaaaaa|596605&chd=t:" + valores + "&chdl=" + encabezados2 + "\" width=\"600\" height=\"200\">"
                + "</div>\n";
        //String body=BalnearioHTML(graficas);
        return insertInHtml(graficas);
    }
}

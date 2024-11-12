package pwabd;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/GenerareChitantaServlet")
public class GenerareChitantaServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    }

    public void generateResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String[] produseSelectate = (String[]) session.getAttribute("produseSelectate");
        int[] cantitatiSelectate = (int[]) session.getAttribute("cantitatiSelectate");
        int[] preturiSelectate = (int[]) session.getAttribute("preturiSelectate");
        int pretTotal = (int) session.getAttribute("pretTotal");

        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        String oras = request.getParameter("oras");
        String adresa = request.getParameter("adresa");
        String modalitatePlata = request.getParameter("modalitatePlata");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
        out.println("<TITLE>Chitanta</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY BGCOLOR='white'>");
        out.println("<H3>Chitanta</H3>");

        // Table for product information
        out.println("<H4>Produse Achizitionate</H4>");
        if (produseSelectate != null) {
            out.println("<TABLE border='1'>");
            out.println("<TR><TH>Produs</TH><TH>Pret</TH><TH>Cantitate</TH></TR>");
            for (int i = 0; i < produseSelectate.length; i++) {
                if (produseSelectate[i] != null) {
                    out.println("<TR>");
                    out.println("<TD>" + produseSelectate[i] + "</TD>");
                    out.println("<TD>" + preturiSelectate[i] + " LEI</TD>");
                    out.println("<TD>" + cantitatiSelectate[i] + "</TD>");
                    out.println("</TR>");
                }
            }
            out.println("</TABLE>");
            out.println("<P>Pret total: " + pretTotal + " LEI</P>");
        } else {
            out.println("<P>Nu ati selectat niciun produs.</P>");
        }

        // Table for client information
        out.println("<H4>Informatii Client</H4>");
        out.println("<TABLE border='1'>");
        out.println("<TR><TH>Camp</TH><TH>Valoare</TH></TR>");
        out.println("<TR><TD>Nume</TD><TD>" + nume + "</TD></TR>");
        out.println("<TR><TD>Prenume</TD><TD>" + prenume + "</TD></TR>");
        out.println("<TR><TD>Oras</TD><TD>" + oras + "</TD></TR>");
        out.println("<TR><TD>Adresa</TD><TD>" + adresa + "</TD></TR>");
        out.println("<TR><TD>Modalitate de plata</TD><TD>" + modalitatePlata + "</TD></TR>");
        out.println("</TABLE>");
        out.println("<P>Chitanta a fost generata cu succes.</P>");
        out.println("<h1>Va multumim pentru achizitie!</h1>");
        out.println("<h3>Comanda dumneavoastra va fi procesata in cel mai scurt timp posibil.</h3>");
        out.println("<FORM ACTION='index.html' METHOD='POST'>");
        out.println("<input type='submit' value='Acasa'>");
        out.println("</FORM>");

        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }
}
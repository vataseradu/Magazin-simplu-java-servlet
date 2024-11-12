package pwabd;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/prelProduseSelectate")
public class PrelProduseSelectateServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    }

    public void generateResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext context = getServletContext();
        String[] numeProduse = (String[]) context.getAttribute("produseAtribut");
        int[] pretProduse = (int[]) context.getAttribute("preturiProduseAtribut");
        int[] cantitatiProduse = (int[]) context.getAttribute("cantitatiProduseAtribut");

        String[] produseSelectate = new String[numeProduse.length];
        int[] cantitatiSelectate = new int[numeProduse.length];
        int[] preturiSelectate = new int[numeProduse.length];
        int pretTotal = 0;
        int indexSelectate = 0;

        String[] produse = request.getParameterValues("produs");
        if (produse != null) {
            for (String produs : produse) {
                int index = -1;
                for (int i = 0; i < numeProduse.length; i++) {
                    if (numeProduse[i].equals(produs)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    int cantitate = Integer.parseInt(request.getParameter("cantitate_" + produs));
                    produseSelectate[indexSelectate] = numeProduse[index];
                    cantitatiSelectate[indexSelectate] = cantitate;
                    preturiSelectate[indexSelectate] = pretProduse[index];
                    pretTotal += pretProduse[index] * cantitate;
                    indexSelectate++;
                }
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("produseSelectate", produseSelectate);
        session.setAttribute("cantitatiSelectate", cantitatiSelectate);
        session.setAttribute("preturiSelectate", preturiSelectate);
        session.setAttribute("pretTotal", pretTotal);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
        out.println("<TITLE>Produse selectate</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY>");
        out.println("<H3>Produse selectate</H3>");
        if (produse != null) {
            out.println("<TABLE>");
            out.println("<TR><TH>Produs</TH><TH>Pret</TH><TH>Cantitate</TH></TR>");
            for (int i = 0; i < indexSelectate; i++) {
                out.println("<TR>");
                out.println("<TD>" + produseSelectate[i] + "</TD>");
                out.println("<TD>" + preturiSelectate[i] + " LEI</TD>");
                out.println("<TD>x" + cantitatiSelectate[i] + "</TD>");
                out.println("</TR>");
            }
            out.println("</TABLE>");
            out.println("<P>Pret total: " + pretTotal + " LEI</P>");
            out.println("<FORM ACTION='infoClient.html' METHOD='POST'>");
            out.println("<input type='submit' value='Continua cu plata'>");
            out.println("</FORM>");
        } else {
            out.println("<P>Nu ati selectat niciun produs.</P>");
            out.println("<FORM ACTION='listaProduse' METHOD='POST'>");
            out.println("<input type='submit' value='Inapoi'>");
            out.println("</FORM>");
        }
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }
}
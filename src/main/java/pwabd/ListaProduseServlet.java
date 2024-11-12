package pwabd;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/listaProduse")
public class ListaProduseServlet extends HttpServlet {
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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>Produse</TITLE>");
        out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
        out.println("<script type='text/javascript'>");
        out.println("function validateForm() {");
        out.println("  var form = document.forms['produseForm'];");
        out.println("  var numeProduse = " + numeProduse.length + ";");
        out.println("  for (var i = 0; i < numeProduse; i++) {");
        out.println("    var checkbox = form['produs'][i];");
        out.println("    if (checkbox.checked) {");
        out.println("      var select = form['cantitate_' + checkbox.value];");
        out.println("      var cantitateaSelectata = parseInt(select.value);");
        out.println("      var cantitateaRamasa = parseInt(select.getAttribute('data-available'));");
        out.println("      if (cantitateaSelectata > cantitateaRamasa) {");
        out.println("        alert('Cantitatea selectata pentru ' + checkbox.value + ' depaseste cantitatea disponibila.');");
        out.println("        return false;");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println("  return true;");
        out.println("}");
        out.println("</script>");
        out.println("</HEAD>");
        out.println("<BODY BGCOLOR='white'>");
        out.println("<H3> Produse </H3>");
        if (numeProduse != null) {
            out.println("<FORM name='produseForm' ACTION='prelProduseSelectate' METHOD='POST' onsubmit='return validateForm()'>");
            out.println("<TABLE>");
            out.println("<TR><TH>Produs</TH><TH>Pret</TH><TH>Cantitate</TH><TH>Stoc disponibil</TH></TR>");
            for (int i = 0; i < numeProduse.length; i++) {
                out.println("<TR>");
                out.println("<TD>");
                out.println("<input type='checkbox' name='produs' value='" + numeProduse[i] + "'>" + numeProduse[i]);
                out.println("</TD>");
                out.println("<TD>");
                out.println(pretProduse[i]+" LEI");
                out.println("</TD>");
                out.println("<TD>");
                out.println("<select name='cantitate_" + numeProduse[i] + "' data-available='" + cantitatiProduse[i] + "'>");
                for (int j = 1; j <= 5; j++) {
                    out.println("<option value='" + j + "'>" + j + "</option>");
                }
                out.println("</select>");
                out.println("</TD>");
                out.println("<TD>");
                out.println("Disponibil: " + cantitatiProduse[i]);
                out.println("</TD>");
                out.println("</TR>");
            }
            out.println("</TABLE>");
            out.println("<P>Numar total produse: " + numeProduse.length + " </P>");
            out.println("<input type='submit' value='Trimite'>");
            out.println("</FORM>");
        } else {
            out.println("<P>Numar total produse: 0 </P>");
        }
        out.println("</BODY>");
        out.println("</HTML>");

        out.close();
    }
}
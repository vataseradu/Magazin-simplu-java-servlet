package pwabd;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;
import java.io.*;

public class IncarcaProduseListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        String numeFisierCuProduse = context.getInitParameter("numeFisierCuNote");
        int numarProduse = Integer.parseInt(context.getInitParameter("numarStudenti"));

        String[] produse = new String[numarProduse];
        int[] preturiProduse = new int[numarProduse];
        int[] cantitatiProduse = new int[numarProduse];
        BufferedReader produseReader = null;
        try {
            InputStream is = context.getResourceAsStream(numeFisierCuProduse);
            produseReader = new BufferedReader(new InputStreamReader(is));

            String produsPretCantitate = null;
            java.util.Scanner s = null;
            int i = 0;
            while ((produsPretCantitate = produseReader.readLine()) != null) {
                s = new java.util.Scanner(produsPretCantitate);
                produse[i] = s.next();
                preturiProduse[i] = Integer.parseInt(s.next());
                cantitatiProduse[i] = Integer.parseInt(s.next());
                i++;
            }
            context.setAttribute("produseAtribut", produse);
            context.setAttribute("preturiProduseAtribut", preturiProduse);
            context.setAttribute("cantitatiProduseAtribut", cantitatiProduse);
            System.out.println("Produse incarcate in context!");

        } catch (IOException e) {
            System.out.println("Exceptie: " + e.getMessage());
            produse = null;
            preturiProduse = null;
            cantitatiProduse = null;
        } finally {
            if (produseReader != null) {
                try {
                    produseReader.close();
                } catch (IOException ioe) {
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("produseAtribut", null);
        context.setAttribute("preturiProduseAtribut", null);
        context.setAttribute("cantitatiProduseAtribut", null);
    }
}
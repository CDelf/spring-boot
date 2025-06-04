package fr.diginamic.hello.utils;

import com.itextpdf.text.DocumentException;
import fr.diginamic.hello.models.Ville;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public final class CsvUtil {

    public static void exportVillesCsv(HttpServletResponse response, List<Ville> villes) throws IOException, DocumentException {
        response.setHeader("Content-Disposition", "attachement;filename=\"villes.csv\"");
        response.getWriter().append("Nom de la ville; Nombre d’habitants; Code du departement, Nom du departement\n");
        for(Ville v : villes) {
            response.getWriter().append(v.getNom() +";"+v.getNbHabitants()+";"+v.getDepartement().getCode()+";"+v.getDepartement().getNom()+"\n");
        }
        response.flushBuffer();
    }
}

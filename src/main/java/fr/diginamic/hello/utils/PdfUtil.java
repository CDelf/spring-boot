package fr.diginamic.hello.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public final class PdfUtil {

    public static void exportDptPdf(HttpServletResponse response,
                                    Departement dpt,
                                    List<Ville> villes) throws IOException, DocumentException {
        response.setHeader("Content-Disposition", "attachment; filename=\"departement.pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Police
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 20, Font.BOLD);
        Font cellFont = new Font(baseFont, 12);

        // Contenu
        document.addTitle("Fiche Département");
        document.newPage();
        // Titre centré
        Paragraph title = new Paragraph("Département : " + dpt.getNom(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph code = new Paragraph("Code : " + dpt.getCode(), cellFont);
        code.setAlignment(Element.ALIGN_CENTER);
        code.setSpacingAfter(20f);
        document.add(code);

        // Tableau de résultats
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setWidths(new float[]{3, 2});

        // En-tête avec fond coloré
        PdfPCell nomHeader = new PdfPCell(new Phrase("Ville", cellFont));
        nomHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        nomHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        nomHeader.setBorderColor(BaseColor.GRAY);

        PdfPCell popHeader = new PdfPCell(new Phrase("Population", cellFont));
        popHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        popHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        popHeader.setBorderColor(BaseColor.GRAY);

        table.addCell(nomHeader);
        table.addCell(popHeader);

        // Liste des villes
        for (Ville ville : villes) {
            PdfPCell nomCell = new PdfPCell(new Phrase(ville.getNom(), cellFont));
            nomCell.setBorderColor(BaseColor.LIGHT_GRAY);

            PdfPCell popCell = new PdfPCell(new Phrase(String.valueOf(ville.getNbHabitants()), cellFont));
            popCell.setBorderColor(BaseColor.LIGHT_GRAY);

            table.addCell(nomCell);
            table.addCell(popCell);
        }

        document.add(table);
        document.close();
        response.flushBuffer();
    }
}

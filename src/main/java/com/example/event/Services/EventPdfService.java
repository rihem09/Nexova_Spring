package com.example.event.Services;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.example.event.Entities.Event;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class EventPdfService {

    public byte[] generateAllEventsPdf(List<Event> events) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLUE);
        Font contentFont = new Font(Font.HELVETICA, 12);

        Paragraph mainTitle = new Paragraph("📚 Maghrebia", titleFont);
        Paragraph secondarytitle = new Paragraph("📚 Affiche Evennementielle", titleFont);
        mainTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(mainTitle);
        document.add(secondarytitle);
        document.add(Chunk.NEWLINE);

        for (Event event : events) {

            Paragraph sectionTitle = new Paragraph("🎉 " + event.getTitle(), sectionFont);
            sectionTitle.setSpacingBefore(10f);
            sectionTitle.setSpacingAfter(5f);
            document.add(sectionTitle);

            document.add(new Paragraph("📅 Date : " + event.getDateEvent(), contentFont));
            document.add(new Paragraph("📍 Lieu : " + event.getVenue(), contentFont));
            document.add(new Paragraph("⏱️ Durée : " + event.getDuration() + " heures", contentFont));
            document.add(new Paragraph("🎟️ Pass : " + event.getEventp(), contentFont));
            document.add(new Paragraph("📝 Description : " + event.getDescription(), contentFont));

        }

        document.close();
        return out.toByteArray();
    }
}

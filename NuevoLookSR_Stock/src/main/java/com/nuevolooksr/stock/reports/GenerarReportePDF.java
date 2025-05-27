package com.nuevolooksr.stock.reports;

import com.nuevolooksr.stock.dao.ProductoDAO;
import com.nuevolooksr.stock.model.Producto;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.font.constants.StandardFonts;
import java.util.List;

public class GenerarReportePDF {
    public static void generarReporte() {
        try {
            String destino = "Reporte_Stock.pdf";
            PdfWriter writer = new PdfWriter(destino);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.add(new Paragraph("Reporte de Stock").setFont(boldFont).setFontSize(14));

            Table table = new Table(new float[]{3, 3, 3});
            table.setWidth(500);
            table.addCell(new Cell().add(new Paragraph("Producto").setFont(boldFont)));
            table.addCell(new Cell().add(new Paragraph("Stock en Depósito").setFont(boldFont)));
            table.addCell(new Cell().add(new Paragraph("Stock en Local").setFont(boldFont)));

            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productos = productoDAO.obtenerProductos();

            for (Producto p : productos) {
                table.addCell(p.getNombre());
                table.addCell(String.valueOf(p.getStockDeposito()));
                table.addCell(String.valueOf(p.getStockLocal()));
            }

            document.add(table);
            document.close();
            System.out.println("✅ PDF generado exitosamente en: " + destino);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

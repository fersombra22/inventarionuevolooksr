package com.nuevolooksr.stock.reports;

import com.nuevolooksr.stock.dao.ProductoDAO;
import com.nuevolooksr.stock.model.Producto;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GenerarReportePDF {
    public static void generarReporte(String destino) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Reporte de Inventario - NuevoLookSR");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 730);
            contentStream.showText("Fecha: " + LocalDate.now());
            contentStream.endText();

            // 🔹 Dibujar encabezados de tabla
            int yOffset = 700;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("Producto");
            contentStream.newLineAtOffset(200, 0);
            contentStream.showText("Stock Depósito");
            contentStream.newLineAtOffset(120, 0);
            contentStream.showText("Stock Local");
            contentStream.endText();

            yOffset -= 20;
            contentStream.moveTo(50, yOffset);
            contentStream.lineTo(500, yOffset);
            contentStream.stroke();
            yOffset -= 10;

            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productos = productoDAO.obtenerProductos();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            for (Producto producto : productos) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yOffset);
                contentStream.showText(producto.getNombre());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText(String.valueOf(producto.getStockDeposito()));
                contentStream.newLineAtOffset(120, 0);
                contentStream.showText(String.valueOf(producto.getStockLocal()));
                contentStream.endText();

                yOffset -= 15;
            }

            contentStream.close();
            document.save(destino);
            System.out.println("✅ PDF generado correctamente en: " + destino);
        } catch (IOException e) {
            System.err.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}

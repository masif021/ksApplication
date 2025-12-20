package com.ks.application.service.soa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PdfMergerProcess {

    public static void pdfMerger(String inputDir,String outputPdfFile) throws IOException {

        File[] pdfFiles = new File(inputDir)
                .listFiles(f ->
                        f.getName().toLowerCase().endsWith(".pdf")
                        && !f.getName().startsWith("Merged_File_")
                );
        
        if (pdfFiles == null || pdfFiles.length == 0) {
            throw new IllegalStateException("No Jasper PDF files found.");
        }

        // Ensure predictable batch order (important for banks)
        Arrays.sort(pdfFiles, Comparator.comparing(File::getName));

        PDFMergerUtility merger = new PDFMergerUtility();
        PDDocument destination = new PDDocument();

        List<PDDocument> openSources = new ArrayList<>();

        try {
            for (File pdf : pdfFiles) {

                PDDocument src = PDDocument.load(pdf);
                openSources.add(src);

                int pages = src.getNumberOfPages();

                // Append document SAFELY (deep clone)
                merger.appendDocument(destination, src);

                // If odd → add blank page
                if (pages % 2 != 0) {
                    PDRectangle mb = src.getPage(pages - 1).getMediaBox();
                    PDRectangle clone = new PDRectangle(
                            mb.getLowerLeftX(), mb.getLowerLeftY(),
                            mb.getWidth(), mb.getHeight()
                    );
                    destination.addPage(new PDPage(clone));
                }

                System.out.println("Processed: " + pdf.getName() + " pages=" + pages);
            }

            destination.save(outputPdfFile);

        } finally {
            // Close everything LAST
            for (PDDocument d : openSources) {
                d.close();
            }
            destination.close();
        }
        System.out.println("Batch PDF created successfully: " + outputPdfFile);
    
    }

    
}

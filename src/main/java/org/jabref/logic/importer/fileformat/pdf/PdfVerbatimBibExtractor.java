package org.jabref.logic.importer.fileformat.pdf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.ParseException;
import org.jabref.logic.importer.fileformat.BibtexParser;
import org.jabref.logic.util.PdfUtils;
import org.jabref.model.entry.BibEntry;

import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * This importer imports a verbatim BibTeX entry from the first page of the PDF.
 */
public class PdfVerbatimBibExtractor implements PdfBibExtractor {

    private final ImportFormatPreferences importFormatPreferences;

    public PdfVerbatimBibExtractor(ImportFormatPreferences importFormatPreferences) {
        this.importFormatPreferences = importFormatPreferences;
    }

    public List<BibEntry> importDatabase(Path filePath, PDDocument document) throws IOException, ParseException {
        List<BibEntry> result;
        String firstPageContents = PdfUtils.getFirstPageContents(document);
        BibtexParser parser = new BibtexParser(importFormatPreferences);
        // TODO: Test if it will accept page with partial BibTex and partial natural langauge content.
        result = parser.parseEntries(firstPageContents);

        // TODO: Check if it's needed in {@link PdfImporter}.
        result.forEach(entry -> entry.setCommentsBeforeEntry(""));

        return result;
    }
}

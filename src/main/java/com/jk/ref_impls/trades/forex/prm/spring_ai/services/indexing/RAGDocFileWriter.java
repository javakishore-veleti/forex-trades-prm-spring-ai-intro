package com.jk.ref_impls.trades.forex.prm.spring_ai.services.indexing;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.writer.FileDocumentWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGDocFileWriter {

    public void writeDocumentsToFile(List<Document> documentList, String filePath, boolean appendFileExists) {
        // Write the documents to the file
        var writer = new FileDocumentWriter(filePath, appendFileExists, MetadataMode.ALL, appendFileExists);
        writer.accept(documentList);
    }
}

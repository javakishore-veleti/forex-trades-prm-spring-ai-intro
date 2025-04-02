package com.jk.ref_impls.trades.forex.prm.spring_ai.services.indexing;

import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.BasicIndexingRequest;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"RedundantCollectionOperation", "SpringJavaAutowiredFieldsWarningInspection"})
@Log
@Service
public class RAGBasicIndexingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RAGBasicIndexingService.class);

    @Autowired
    private RAGTikaDocumentReader ragTikaDocumentReader;

    @Autowired
    private TextSplitter textSplitter;

    @Autowired
    private RAGDocFileWriter ragDocFileWriter;

    private static final String CUSTOM_KEYWORDS_METADATA_KEY = "custom_keywords";

    private void addCustomerMetadata(Document document, List<String> keywords) {
        if(ObjectUtils.isEmpty(keywords)) {
            return;
        }

        Assert.notNull(document, "Document cannot be null");
        document.getMetadata().putAll(Map.of(CUSTOM_KEYWORDS_METADATA_KEY, keywords));
    }

    private List<Document> processDocument(Resource resource, String outputFileName, boolean appendFileExists, List<String> keywords) {
        Assert.isTrue(resource.exists(), "Resource does not exist");

        var parsedDocuments = ragTikaDocumentReader.readFrom(resource);
        var splitDocuments = textSplitter.split(parsedDocuments);
        splitDocuments.forEach(document -> addCustomerMetadata(document, keywords));
        ragDocFileWriter.writeDocumentsToFile(splitDocuments, outputFileName, appendFileExists);

        LOGGER.info("Processed document: {}", resource.getFilename());
        return splitDocuments;
    }

    public List<Document> indexDocumentFromFileSystem(BasicIndexingRequest request) {
        var resource = new FileSystemResource(request.sourcePath());
        return processDocument(resource, request.outputFileName(), request.appendFileExists(), request.keywords());
    }

    public List<Document> indexDocumentFromURL(BasicIndexingRequest request) throws Exception {
        var resource = new UrlResource(request.url());
        return processDocument(resource, request.outputFileName(), request.appendFileExists(), request.keywords());
    }

}

package com.jk.ref_impls.trades.forex.prm.spring_ai.services.vectors;

import org.springframework.ai.vectorstore.neo4j.Neo4jVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RAGVectorIndexingServiceImpl implements RAGVectorIndexingService {

    @Autowired
    private Neo4jVectorStore vectorStore;

}

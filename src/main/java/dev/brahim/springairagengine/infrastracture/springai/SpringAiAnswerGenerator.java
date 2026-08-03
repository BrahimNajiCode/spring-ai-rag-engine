package dev.brahim.springairagengine.infrastracture.springai;

import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.generation.GeneratedAnswer;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.springframework.ai.chat.client.ChatClient;

import java.util.List;

public class SpringAiAnswerGenerator implements AnswerGenerator {
    private final ChatClient chatClient;

    public SpringAiAnswerGenerator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public GeneratedAnswer generate(ProcessedQuery query, List<RetrievedDocument> documents) {
        return null;
    }
}

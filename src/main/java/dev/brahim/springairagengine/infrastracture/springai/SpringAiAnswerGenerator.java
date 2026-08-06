package dev.brahim.springairagengine.infrastracture.springai;

import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.generation.GeneratedAnswer;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class SpringAiAnswerGenerator implements AnswerGenerator {

    private static final String SYSTEM_PROMPT = """
            You are a helpful assistant.

            Answer the user's question using only the provided context.
            If the context does not contain enough information to answer,
            say that you do not have enough information.

            Context:
            {context}
            """;

    private final ChatClient chatClient;

    public SpringAiAnswerGenerator(ChatClient chatClient) {
        this.chatClient = Objects.requireNonNull(chatClient);
    }


    @Override
    public GeneratedAnswer generate(ProcessedQuery query, List<RetrievedDocument> documents)
    {
        Objects.requireNonNull(query,"Query must not be null");
        Objects.requireNonNull(documents,"Documents must not be null");

        String context = buildContext(documents);

        String answer = chatClient.prompt()
                .system(s->s.text(SYSTEM_PROMPT)
                        .param("context", context)
                )
                .user(query.query())
                .call()
                .content();
        return new GeneratedAnswer(answer);
    }

    private String buildContext(List<RetrievedDocument> documents) {
        return documents.stream()
                .map(RetrievedDocument::content)
                .collect(Collectors.joining("\n\n---\n\n"));
    }


}

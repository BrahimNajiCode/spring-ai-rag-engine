package dev.brahim.springairagengine.application.rag;

import dev.brahim.springairagengine.domain.document.DocumentReference;

import javax.swing.*;

public record RagResponse(
    String answer,
    ListModel<DocumentReference> sources
) {}

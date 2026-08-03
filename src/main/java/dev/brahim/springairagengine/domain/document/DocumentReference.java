package dev.brahim.springairagengine.domain.document;

import java.util.Map;

public record DocumentReference(
   String id,
   Map<Object, String> metadata
) {}

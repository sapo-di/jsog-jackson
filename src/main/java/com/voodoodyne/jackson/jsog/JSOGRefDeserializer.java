package com.voodoodyne.jackson.jsog;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

/**
 * Knows how to take either form of a JSOGRef (string or {@ref:string} and convert it back into a JSOGRef.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class JSOGRefDeserializer extends ValueDeserializer<JSOGRef> {
    @Override
    public JSOGRef deserialize(JsonParser jp, DeserializationContext ctx) throws JacksonException {
        JsonNode node = ctx.readValue(jp, JsonNode.class);
        if (node.isString()) {
            return new JSOGRef(node.asString());
        } else {
            return new JSOGRef(node.get(JSOGRef.REF_KEY).asString());
        }
    }

}

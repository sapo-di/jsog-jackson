package com.voodoodyne.jackson.jsog;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Knows how to take a JSOGRef and print it as @id or @ref as appropriate.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class JSOGRefSerializer extends ValueSerializer<JSOGRef> {
    @Override
    public void serialize(JSOGRef value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value.used) {
            gen.writeStartObject();
            gen.writeStringProperty(JSOGRef.REF_KEY, value.ref);
            gen.writeEndObject();
        } else {
            value.used = true;
            gen.writeString(value.ref);
        }

    }

}

package com.voodoodyne.jackson.jsog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.testng.annotations.Test;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.util.Date;

/**
 * Demonstrates the inability to round trip serialize deserialize when using DefaultTyping.NON_FINAL,
 * and version fixes for NON_CONCRETE strategies.
 */
public class Issue15Test {

    @Test // always works
    public void testIssue5WorkaroundObject() {
        testWithPolymorphicStrategy(DefaultTyping.JAVA_LANG_OBJECT);

    }

    @Test
    public void testIssue5WorkaroundObjectAndNonConcrete() {
        testWithPolymorphicStrategy(DefaultTyping.OBJECT_AND_NON_CONCRETE);

    }

    @Test
    public void testIssue5WorkaroundNonConcreteAndArrays() {
        testWithPolymorphicStrategy(DefaultTyping.NON_CONCRETE_AND_ARRAYS);

    }

    @Test
    public void testIssue5WorkaroundNonFinal() {
        testWithPolymorphicStrategy(DefaultTyping.NON_FINAL);
    }

    private void testWithPolymorphicStrategy(DefaultTyping typingStragegy) {
        Thing t = new Thing();
        t.setName("foo");

        t.setDate(new Date());

        BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator
                .builder()
                .allowIfBaseType(Thing.class)
                .allowIfBaseType(Date.class)
                .build();

        ObjectMapper mapper = JsonMapper.builder()
                .activateDefaultTyping(typeValidator, typingStragegy)
                .addMixIn(Object.class, JSOGMixin.class)
                .build();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        mapper.readValue(json, Thing.class);
    }

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class JSOGMixin {
    }
}

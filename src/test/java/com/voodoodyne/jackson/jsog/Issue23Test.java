package com.voodoodyne.jackson.jsog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.testng.annotations.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Issue23Test {

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    public static class TestType {
        public String value;
    }

    @Test
    public void shouldDeserializeWithFailOnTrailingTokensFeatureEnabled() {
        // given
        ObjectMapper objectMapper = new ObjectMapper()
                .rebuild()
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                .build();

        tryDeserializeAndAssert(objectMapper);
    }

    @Test
    public void shouldDeserializeWithFailOnTrailingTokensFeatureDisabled() {
        // given
        ObjectMapper objectMapper = new ObjectMapper()
                .rebuild()
                .disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                .build();

        tryDeserializeAndAssert(objectMapper);
    }

    private void tryDeserializeAndAssert(final ObjectMapper objectMapper) {
        // when
        List<TestType> deserialized = objectMapper.readValue(
                "[{\"@id\":\"1\",\"value\":\"testValue\"},{\"@ref\":\"1\"}]",
                new TypeReference<List<TestType>>() {
                });
        // then
        assertThat(deserialized.get(0).value, equalTo("testValue"));
        assertThat(deserialized.get(1).value, equalTo("testValue"));
    }
}

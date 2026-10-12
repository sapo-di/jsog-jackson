package com.voodoodyne.jackson.jsog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.testng.annotations.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test for https://github.com/jsog/jsog-jackson/issues/3
 */
public class Issue3Test {

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    public static class ContractNumber {
        public Project project;
    }

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    public static class Project {
        public String projectid;
        public List<ContractNumber> contractnumbers;
    }

    /**
     *
     */
    private static final String JSOGIFIED = "{\"@id\":\"1\",\"projectid\":\"1\",\"contractnumbers\":[{\"@id\":\"2\",\"project\":{\"@ref\":\"1\"}}]}";

    /**
     *
     */
    ObjectMapper mapper = new ObjectMapper();

    /**
     *
     */
    @Test
    public void deserializationWorks() {
        Project project = mapper.readValue(JSOGIFIED, Project.class);

        assertThat(project.projectid, equalTo("1"));
        assertThat(project.contractnumbers, hasSize(1));
        assertThat(project.contractnumbers.get(0).project, sameInstance(project));
    }
}

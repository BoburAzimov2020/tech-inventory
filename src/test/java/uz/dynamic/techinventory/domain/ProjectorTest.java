package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ProjectorTestSamples.*;
import static uz.dynamic.techinventory.domain.ProjectorTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ProjectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projector.class);
        Projector projector1 = getProjectorSample1();
        Projector projector2 = new Projector();
        assertThat(projector1).isNotEqualTo(projector2);

        projector2.setId(projector1.getId());
        assertThat(projector1).isEqualTo(projector2);

        projector2 = getProjectorSample2();
        assertThat(projector1).isNotEqualTo(projector2);
    }

    @Test
    void projectorTypeTest() throws Exception {
        Projector projector = getProjectorRandomSampleGenerator();
        ProjectorType projectorTypeBack = getProjectorTypeRandomSampleGenerator();

        projector.setProjectorType(projectorTypeBack);
        assertThat(projector.getProjectorType()).isEqualTo(projectorTypeBack);

        projector.projectorType(null);
        assertThat(projector.getProjectorType()).isNull();
    }
}

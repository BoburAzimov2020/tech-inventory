package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.ProjectorTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ProjectorTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectorType.class);
        ProjectorType projectorType1 = getProjectorTypeSample1();
        ProjectorType projectorType2 = new ProjectorType();
        assertThat(projectorType1).isNotEqualTo(projectorType2);

        projectorType2.setId(projectorType1.getId());
        assertThat(projectorType1).isEqualTo(projectorType2);

        projectorType2 = getProjectorTypeSample2();
        assertThat(projectorType1).isNotEqualTo(projectorType2);
    }

    @Test
    void obyektTest() throws Exception {
        ProjectorType projectorType = getProjectorTypeRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        projectorType.setObyekt(obyektBack);
        assertThat(projectorType.getObyekt()).isEqualTo(obyektBack);

        projectorType.obyekt(null);
        assertThat(projectorType.getObyekt()).isNull();
    }
}

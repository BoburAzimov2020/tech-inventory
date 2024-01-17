package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.CameraTypeTestSamples.*;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CameraTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CameraType.class);
        CameraType cameraType1 = getCameraTypeSample1();
        CameraType cameraType2 = new CameraType();
        assertThat(cameraType1).isNotEqualTo(cameraType2);

        cameraType2.setId(cameraType1.getId());
        assertThat(cameraType1).isEqualTo(cameraType2);

        cameraType2 = getCameraTypeSample2();
        assertThat(cameraType1).isNotEqualTo(cameraType2);
    }

    @Test
    void obyektTest() throws Exception {
        CameraType cameraType = getCameraTypeRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        cameraType.setObyekt(obyektBack);
        assertThat(cameraType.getObyekt()).isEqualTo(obyektBack);

        cameraType.obyekt(null);
        assertThat(cameraType.getObyekt()).isNull();
    }
}

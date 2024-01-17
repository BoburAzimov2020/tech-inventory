package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.CameraBrandTestSamples.*;
import static uz.dynamic.techinventory.domain.CameraTestSamples.*;
import static uz.dynamic.techinventory.domain.CameraTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CameraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Camera.class);
        Camera camera1 = getCameraSample1();
        Camera camera2 = new Camera();
        assertThat(camera1).isNotEqualTo(camera2);

        camera2.setId(camera1.getId());
        assertThat(camera1).isEqualTo(camera2);

        camera2 = getCameraSample2();
        assertThat(camera1).isNotEqualTo(camera2);
    }

    @Test
    void cameraTypeTest() throws Exception {
        Camera camera = getCameraRandomSampleGenerator();
        CameraType cameraTypeBack = getCameraTypeRandomSampleGenerator();

        camera.setCameraType(cameraTypeBack);
        assertThat(camera.getCameraType()).isEqualTo(cameraTypeBack);

        camera.cameraType(null);
        assertThat(camera.getCameraType()).isNull();
    }

    @Test
    void cameraBrandTest() throws Exception {
        Camera camera = getCameraRandomSampleGenerator();
        CameraBrand cameraBrandBack = getCameraBrandRandomSampleGenerator();

        camera.setCameraBrand(cameraBrandBack);
        assertThat(camera.getCameraBrand()).isEqualTo(cameraBrandBack);

        camera.cameraBrand(null);
        assertThat(camera.getCameraBrand()).isNull();
    }
}

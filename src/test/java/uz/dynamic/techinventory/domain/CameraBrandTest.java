package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.CameraBrandTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CameraBrandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CameraBrand.class);
        CameraBrand cameraBrand1 = getCameraBrandSample1();
        CameraBrand cameraBrand2 = new CameraBrand();
        assertThat(cameraBrand1).isNotEqualTo(cameraBrand2);

        cameraBrand2.setId(cameraBrand1.getId());
        assertThat(cameraBrand1).isEqualTo(cameraBrand2);

        cameraBrand2 = getCameraBrandSample2();
        assertThat(cameraBrand1).isNotEqualTo(cameraBrand2);
    }
}

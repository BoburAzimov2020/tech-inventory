package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CameraBrandDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CameraBrandDTO.class);
        CameraBrandDTO cameraBrandDTO1 = new CameraBrandDTO();
        cameraBrandDTO1.setId(1L);
        CameraBrandDTO cameraBrandDTO2 = new CameraBrandDTO();
        assertThat(cameraBrandDTO1).isNotEqualTo(cameraBrandDTO2);
        cameraBrandDTO2.setId(cameraBrandDTO1.getId());
        assertThat(cameraBrandDTO1).isEqualTo(cameraBrandDTO2);
        cameraBrandDTO2.setId(2L);
        assertThat(cameraBrandDTO1).isNotEqualTo(cameraBrandDTO2);
        cameraBrandDTO1.setId(null);
        assertThat(cameraBrandDTO1).isNotEqualTo(cameraBrandDTO2);
    }
}

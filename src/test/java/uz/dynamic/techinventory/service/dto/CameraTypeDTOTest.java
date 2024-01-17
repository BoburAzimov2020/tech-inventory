package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CameraTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CameraTypeDTO.class);
        CameraTypeDTO cameraTypeDTO1 = new CameraTypeDTO();
        cameraTypeDTO1.setId(1L);
        CameraTypeDTO cameraTypeDTO2 = new CameraTypeDTO();
        assertThat(cameraTypeDTO1).isNotEqualTo(cameraTypeDTO2);
        cameraTypeDTO2.setId(cameraTypeDTO1.getId());
        assertThat(cameraTypeDTO1).isEqualTo(cameraTypeDTO2);
        cameraTypeDTO2.setId(2L);
        assertThat(cameraTypeDTO1).isNotEqualTo(cameraTypeDTO2);
        cameraTypeDTO1.setId(null);
        assertThat(cameraTypeDTO1).isNotEqualTo(cameraTypeDTO2);
    }
}

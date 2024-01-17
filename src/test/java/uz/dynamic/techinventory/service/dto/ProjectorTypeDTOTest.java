package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ProjectorTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectorTypeDTO.class);
        ProjectorTypeDTO projectorTypeDTO1 = new ProjectorTypeDTO();
        projectorTypeDTO1.setId(1L);
        ProjectorTypeDTO projectorTypeDTO2 = new ProjectorTypeDTO();
        assertThat(projectorTypeDTO1).isNotEqualTo(projectorTypeDTO2);
        projectorTypeDTO2.setId(projectorTypeDTO1.getId());
        assertThat(projectorTypeDTO1).isEqualTo(projectorTypeDTO2);
        projectorTypeDTO2.setId(2L);
        assertThat(projectorTypeDTO1).isNotEqualTo(projectorTypeDTO2);
        projectorTypeDTO1.setId(null);
        assertThat(projectorTypeDTO1).isNotEqualTo(projectorTypeDTO2);
    }
}

package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ProjectorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectorDTO.class);
        ProjectorDTO projectorDTO1 = new ProjectorDTO();
        projectorDTO1.setId(1L);
        ProjectorDTO projectorDTO2 = new ProjectorDTO();
        assertThat(projectorDTO1).isNotEqualTo(projectorDTO2);
        projectorDTO2.setId(projectorDTO1.getId());
        assertThat(projectorDTO1).isEqualTo(projectorDTO2);
        projectorDTO2.setId(2L);
        assertThat(projectorDTO1).isNotEqualTo(projectorDTO2);
        projectorDTO1.setId(null);
        assertThat(projectorDTO1).isNotEqualTo(projectorDTO2);
    }
}

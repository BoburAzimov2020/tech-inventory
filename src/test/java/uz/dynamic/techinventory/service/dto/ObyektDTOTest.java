package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ObyektDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObyektDTO.class);
        ObyektDTO obyektDTO1 = new ObyektDTO();
        obyektDTO1.setId(1L);
        ObyektDTO obyektDTO2 = new ObyektDTO();
        assertThat(obyektDTO1).isNotEqualTo(obyektDTO2);
        obyektDTO2.setId(obyektDTO1.getId());
        assertThat(obyektDTO1).isEqualTo(obyektDTO2);
        obyektDTO2.setId(2L);
        assertThat(obyektDTO1).isNotEqualTo(obyektDTO2);
        obyektDTO1.setId(null);
        assertThat(obyektDTO1).isNotEqualTo(obyektDTO2);
    }
}

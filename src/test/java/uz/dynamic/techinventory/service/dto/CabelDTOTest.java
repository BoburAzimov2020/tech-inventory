package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CabelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CabelDTO.class);
        CabelDTO cabelDTO1 = new CabelDTO();
        cabelDTO1.setId(1L);
        CabelDTO cabelDTO2 = new CabelDTO();
        assertThat(cabelDTO1).isNotEqualTo(cabelDTO2);
        cabelDTO2.setId(cabelDTO1.getId());
        assertThat(cabelDTO1).isEqualTo(cabelDTO2);
        cabelDTO2.setId(2L);
        assertThat(cabelDTO1).isNotEqualTo(cabelDTO2);
        cabelDTO1.setId(null);
        assertThat(cabelDTO1).isNotEqualTo(cabelDTO2);
    }
}

package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CabelTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CabelTypeDTO.class);
        CabelTypeDTO cabelTypeDTO1 = new CabelTypeDTO();
        cabelTypeDTO1.setId(1L);
        CabelTypeDTO cabelTypeDTO2 = new CabelTypeDTO();
        assertThat(cabelTypeDTO1).isNotEqualTo(cabelTypeDTO2);
        cabelTypeDTO2.setId(cabelTypeDTO1.getId());
        assertThat(cabelTypeDTO1).isEqualTo(cabelTypeDTO2);
        cabelTypeDTO2.setId(2L);
        assertThat(cabelTypeDTO1).isNotEqualTo(cabelTypeDTO2);
        cabelTypeDTO1.setId(null);
        assertThat(cabelTypeDTO1).isNotEqualTo(cabelTypeDTO2);
    }
}

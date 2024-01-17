package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class LoyihaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoyihaDTO.class);
        LoyihaDTO loyihaDTO1 = new LoyihaDTO();
        loyihaDTO1.setId(1L);
        LoyihaDTO loyihaDTO2 = new LoyihaDTO();
        assertThat(loyihaDTO1).isNotEqualTo(loyihaDTO2);
        loyihaDTO2.setId(loyihaDTO1.getId());
        assertThat(loyihaDTO1).isEqualTo(loyihaDTO2);
        loyihaDTO2.setId(2L);
        assertThat(loyihaDTO1).isNotEqualTo(loyihaDTO2);
        loyihaDTO1.setId(null);
        assertThat(loyihaDTO1).isNotEqualTo(loyihaDTO2);
    }
}

package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class AvtomatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvtomatDTO.class);
        AvtomatDTO avtomatDTO1 = new AvtomatDTO();
        avtomatDTO1.setId(1L);
        AvtomatDTO avtomatDTO2 = new AvtomatDTO();
        assertThat(avtomatDTO1).isNotEqualTo(avtomatDTO2);
        avtomatDTO2.setId(avtomatDTO1.getId());
        assertThat(avtomatDTO1).isEqualTo(avtomatDTO2);
        avtomatDTO2.setId(2L);
        assertThat(avtomatDTO1).isNotEqualTo(avtomatDTO2);
        avtomatDTO1.setId(null);
        assertThat(avtomatDTO1).isNotEqualTo(avtomatDTO2);
    }
}

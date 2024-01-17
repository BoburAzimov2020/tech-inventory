package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class RozetkaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RozetkaDTO.class);
        RozetkaDTO rozetkaDTO1 = new RozetkaDTO();
        rozetkaDTO1.setId(1L);
        RozetkaDTO rozetkaDTO2 = new RozetkaDTO();
        assertThat(rozetkaDTO1).isNotEqualTo(rozetkaDTO2);
        rozetkaDTO2.setId(rozetkaDTO1.getId());
        assertThat(rozetkaDTO1).isEqualTo(rozetkaDTO2);
        rozetkaDTO2.setId(2L);
        assertThat(rozetkaDTO1).isNotEqualTo(rozetkaDTO2);
        rozetkaDTO1.setId(null);
        assertThat(rozetkaDTO1).isNotEqualTo(rozetkaDTO2);
    }
}

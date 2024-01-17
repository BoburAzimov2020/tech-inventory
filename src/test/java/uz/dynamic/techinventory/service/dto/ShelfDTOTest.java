package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ShelfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelfDTO.class);
        ShelfDTO shelfDTO1 = new ShelfDTO();
        shelfDTO1.setId(1L);
        ShelfDTO shelfDTO2 = new ShelfDTO();
        assertThat(shelfDTO1).isNotEqualTo(shelfDTO2);
        shelfDTO2.setId(shelfDTO1.getId());
        assertThat(shelfDTO1).isEqualTo(shelfDTO2);
        shelfDTO2.setId(2L);
        assertThat(shelfDTO1).isNotEqualTo(shelfDTO2);
        shelfDTO1.setId(null);
        assertThat(shelfDTO1).isNotEqualTo(shelfDTO2);
    }
}

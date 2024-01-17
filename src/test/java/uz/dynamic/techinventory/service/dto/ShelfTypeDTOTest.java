package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ShelfTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelfTypeDTO.class);
        ShelfTypeDTO shelfTypeDTO1 = new ShelfTypeDTO();
        shelfTypeDTO1.setId(1L);
        ShelfTypeDTO shelfTypeDTO2 = new ShelfTypeDTO();
        assertThat(shelfTypeDTO1).isNotEqualTo(shelfTypeDTO2);
        shelfTypeDTO2.setId(shelfTypeDTO1.getId());
        assertThat(shelfTypeDTO1).isEqualTo(shelfTypeDTO2);
        shelfTypeDTO2.setId(2L);
        assertThat(shelfTypeDTO1).isNotEqualTo(shelfTypeDTO2);
        shelfTypeDTO1.setId(null);
        assertThat(shelfTypeDTO1).isNotEqualTo(shelfTypeDTO2);
    }
}

package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ObjectTasnifiTuriDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectTasnifiTuriDTO.class);
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO1 = new ObjectTasnifiTuriDTO();
        objectTasnifiTuriDTO1.setId(1L);
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO2 = new ObjectTasnifiTuriDTO();
        assertThat(objectTasnifiTuriDTO1).isNotEqualTo(objectTasnifiTuriDTO2);
        objectTasnifiTuriDTO2.setId(objectTasnifiTuriDTO1.getId());
        assertThat(objectTasnifiTuriDTO1).isEqualTo(objectTasnifiTuriDTO2);
        objectTasnifiTuriDTO2.setId(2L);
        assertThat(objectTasnifiTuriDTO1).isNotEqualTo(objectTasnifiTuriDTO2);
        objectTasnifiTuriDTO1.setId(null);
        assertThat(objectTasnifiTuriDTO1).isNotEqualTo(objectTasnifiTuriDTO2);
    }
}

package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ObjectTasnifiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectTasnifiDTO.class);
        ObjectTasnifiDTO objectTasnifiDTO1 = new ObjectTasnifiDTO();
        objectTasnifiDTO1.setId(1L);
        ObjectTasnifiDTO objectTasnifiDTO2 = new ObjectTasnifiDTO();
        assertThat(objectTasnifiDTO1).isNotEqualTo(objectTasnifiDTO2);
        objectTasnifiDTO2.setId(objectTasnifiDTO1.getId());
        assertThat(objectTasnifiDTO1).isEqualTo(objectTasnifiDTO2);
        objectTasnifiDTO2.setId(2L);
        assertThat(objectTasnifiDTO1).isNotEqualTo(objectTasnifiDTO2);
        objectTasnifiDTO1.setId(null);
        assertThat(objectTasnifiDTO1).isNotEqualTo(objectTasnifiDTO2);
    }
}

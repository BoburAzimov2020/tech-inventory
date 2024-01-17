package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class SvitaforDetectorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SvitaforDetectorDTO.class);
        SvitaforDetectorDTO svitaforDetectorDTO1 = new SvitaforDetectorDTO();
        svitaforDetectorDTO1.setId(1L);
        SvitaforDetectorDTO svitaforDetectorDTO2 = new SvitaforDetectorDTO();
        assertThat(svitaforDetectorDTO1).isNotEqualTo(svitaforDetectorDTO2);
        svitaforDetectorDTO2.setId(svitaforDetectorDTO1.getId());
        assertThat(svitaforDetectorDTO1).isEqualTo(svitaforDetectorDTO2);
        svitaforDetectorDTO2.setId(2L);
        assertThat(svitaforDetectorDTO1).isNotEqualTo(svitaforDetectorDTO2);
        svitaforDetectorDTO1.setId(null);
        assertThat(svitaforDetectorDTO1).isNotEqualTo(svitaforDetectorDTO2);
    }
}

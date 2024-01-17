package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class TerminalServerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerminalServerDTO.class);
        TerminalServerDTO terminalServerDTO1 = new TerminalServerDTO();
        terminalServerDTO1.setId(1L);
        TerminalServerDTO terminalServerDTO2 = new TerminalServerDTO();
        assertThat(terminalServerDTO1).isNotEqualTo(terminalServerDTO2);
        terminalServerDTO2.setId(terminalServerDTO1.getId());
        assertThat(terminalServerDTO1).isEqualTo(terminalServerDTO2);
        terminalServerDTO2.setId(2L);
        assertThat(terminalServerDTO1).isNotEqualTo(terminalServerDTO2);
        terminalServerDTO1.setId(null);
        assertThat(terminalServerDTO1).isNotEqualTo(terminalServerDTO2);
    }
}

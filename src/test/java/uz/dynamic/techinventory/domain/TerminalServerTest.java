package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.TerminalServerTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class TerminalServerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerminalServer.class);
        TerminalServer terminalServer1 = getTerminalServerSample1();
        TerminalServer terminalServer2 = new TerminalServer();
        assertThat(terminalServer1).isNotEqualTo(terminalServer2);

        terminalServer2.setId(terminalServer1.getId());
        assertThat(terminalServer1).isEqualTo(terminalServer2);

        terminalServer2 = getTerminalServerSample2();
        assertThat(terminalServer1).isNotEqualTo(terminalServer2);
    }

    @Test
    void obyektTest() throws Exception {
        TerminalServer terminalServer = getTerminalServerRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        terminalServer.setObyekt(obyektBack);
        assertThat(terminalServer.getObyekt()).isEqualTo(obyektBack);

        terminalServer.obyekt(null);
        assertThat(terminalServer.getObyekt()).isNull();
    }
}

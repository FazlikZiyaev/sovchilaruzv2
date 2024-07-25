package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.NationalityTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class NationalityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationality.class);
        Nationality nationality1 = getNationalitySample1();
        Nationality nationality2 = new Nationality();
        assertThat(nationality1).isNotEqualTo(nationality2);

        nationality2.setId(nationality1.getId());
        assertThat(nationality1).isEqualTo(nationality2);

        nationality2 = getNationalitySample2();
        assertThat(nationality1).isNotEqualTo(nationality2);
    }
}

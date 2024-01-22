package org.example.csv;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConvertorTest {

    private static final String EXPECTED_HEADER = "\"Account\",\"Operation Type\",\"Material Type\",\"Entity\",\"Customer\",\"Years\",\"Scenario\",\"Version\",\"Plan Element\",\"Currency\",\"Jan_01\",\"Jan_02\",\"Jan_03\"";

    private final ConvertorImpl convertor = new ConvertorImpl();

    @Test
    void verifyCsvDataPreparedAsExpected_when_testJsonIsUsed() throws JsonProcessingException {
        CsvDto csvDto = convertor.convert(Constants.DEFAULT_INPUT);

        assertEquals(EXPECTED_HEADER, csvDto.getHeader());
        assertThat(csvDto.getRows()).usingFieldByFieldElementComparator().containsOnly(
                "\"AC_Steam Produced\",\"No Operation Type\",\"MT_Boiler Systems\",\"EN_PALM1\",\"No Customer\",\"FY22\",\"FD_Actuals\",\"OEP_Working\",\"OFS_Direct Input\",\"USD\",43.77,12.119999999999,134.81",
                "\"AC_Steam Produced\",\"No Operation Type\",\"MT_Boiler Systems\",\"EN_PALM2\",\"No Customer\",\"FY22\",\"FD_Actuals\",\"OEP_Working\",\"OFS_Direct Input\",\"USD\",18.76806640625,17.80078125,14.87060546875",
                "\"AC_Steam Produced\",\"No Operation Type\",\"MT_Total Boilers\",\"EN_PALM1\",\"No Customer\",\"FY22\",\"FD_Actuals\",\"OEP_Working\",\"OFS_Direct Input\",\"USD\",15.77,12.119999999999,14.81",
                "\"AC_Steam Produced\",\"No Operation Type\",\"MT_Total Boilers\",\"EN_PALM2\",\"No Customer\",\"FY22\",\"FD_Actuals\",\"OEP_Working\",\"OFS_Direct Input\",\"USD\",133.76806640625,140.80078125,174.87060546875"
        );
    }

    @Test
    void verifyCsvDataIsNotPrepared_when_passedInJsonIsInvalid() throws JsonProcessingException {
        assertNull(convertor.convert("{}"));
    }

}
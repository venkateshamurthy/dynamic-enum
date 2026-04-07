package io.github.venkateshamurthy.enums.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.venkateshamurthy.enums.ObjectMapped;
import io.github.venkateshamurthy.enums.spring.SpringBootBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <b>Note:</b>This is just an experimental test and bears no significant usage value. this test might as well break if the
 * name and description of the {@link Faults} and {@link Errors} don't match up with its counterpart in {@link Issues}
 * <P>
 *     While the {@link Issues} enum has all the enums; the {@link Errors}, {@link Faults} augment some more enums from
 *     application.yml just to demonstrate these dynamic-enums are augmentable
 * </P>
 */
@Slf4j
public class IssuesTest extends SpringBootBaseTest {
    //private static final Logger log = LoggerFactory.getLogger(IssuesTest.class);

    String template1 = "Credential Id {} seems {}";
    String template2 = "Credential Id {credId} seems {anomaly}";
    MessageFormat template3 = new MessageFormat("Credential Id {0} seems {1}");
    Object arg1 = UUID.randomUUID(), arg2 = "missing or invalid";
    Map<String, Object> map = Map.of("credId", arg1, "anomaly", arg2);
    ZonedDateTime now = ZonedDateTime.now();

    @Test void testSerDeser() throws JsonProcessingException {
        var mapper = ObjectMapped.getDefaultObjectMapper(Faults.class);
        log.info("SERVER Error:{}", mapper.writeValueAsString(Faults.SERVER_ERR));
    }

    @ParameterizedTest
    @ArgumentsSource(testErrors__Args.class)
    void testErrorsAndFaultsAndIssues(Issues[] issues, Errors[] errors, Faults[] faults) {
        Arrays.sort(issues, Comparator.comparing(FaultCode::name));
        Arrays.sort(errors, Comparator.comparing(FaultCode::name));
        Arrays.sort(faults, Comparator.comparing(FaultCode::name));
        for(int i=0;i<issues.length; i++) {
            System.out.format("Issue:%s, Error:%s, Fault:%s\n", issues[i].name(), errors[i].name(), faults[i].name());
            testErrorsAndFaultsWithEmptyBraces(issues[i],errors[i],faults[i]);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(testFaults__Args.class)
    void testErrorsAndFaultsWithEmptyBraces(FaultCode issue, FaultCode error,  FaultCode fault) {
        assertThat(issue.getDescription()).isEqualTo(fault.getDescription());
        assertThat(Faults.VALIDATION_ERR.ordinal()).isEqualTo(Issues.VALIDATION_ERR.ordinal());
        assertThat(issue.getDescription()).isEqualTo(error.getDescription());
        assertThat(Faults.VALIDATION_ERR.ordinal()).isEqualTo(Errors.VALIDATION_ERR.ordinal());
        var errEx = error.toCommonRTE(template1, arg1, arg2).setTimeStamp(now);
        var issEx = issue.toCommonRTE(template1, arg1, arg2).setTimeStamp(now);
        var fltEx = fault.toCommonRTE(template1, arg1, arg2).setTimeStamp(now);
        assertThat(fltEx)
                .extracting(CommonRTE::getCode, CommonRTE::getMessage,
                        CommonRTE::getDetailedMessage, CommonRTE::getHttpStatus,
                        CommonRTE::getTimeStamp)
                .containsExactly(errEx.getCode(), errEx.getMessage(), errEx.getDetailedMessage(),
                        errEx.getHttpStatus(), errEx.getTimeStamp())
                .containsExactly(issEx.getCode(), issEx.getMessage(), errEx.getDetailedMessage(),
                        issEx.getHttpStatus(), issEx.getTimeStamp());

        fltEx.logInfo();
        fltEx.logDebug();
    }

    @ParameterizedTest
    @ArgumentsSource(testFaults__Args.class)
    void testErrorsAndFaultsWithIndexedBraces(FaultCode issue, FaultCode error, FaultCode fault) {
        assertThat(Issues.valueOf(issue.name()).getStatus()).isEqualTo(Faults.valueOf(fault.name()).getStatus());
        assertThat(issue.getDescription()).isEqualTo(fault.getDescription());
        var errEx = issue.toCommonRTE(template3, arg1, arg2).setTimeStamp(now);
        var fltEx = fault.toCommonRTE(template3, arg1, arg2).setTimeStamp(now);
        new CommonRTE("My Message", null);
        assertThat(fltEx)
                .extracting(CommonRTE::getCode, CommonRTE::getMessage,
                        CommonRTE::getDetailedMessage, CommonRTE::getHttpStatus,
                        CommonRTE::getTimeStamp)
                .containsExactly(errEx.getCode(), errEx.getMessage(), errEx.getDetailedMessage(),
                        errEx.getHttpStatus(), errEx.getTimeStamp());
    }

    @ParameterizedTest
    @ArgumentsSource(testFaults__Args.class)
    void testErrorsAndFaultsWithKeyedBraces(FaultCode issue, FaultCode error,  FaultCode fault) {
        assertThat(issue.getDescription()).isEqualTo(fault.getDescription());
        assertThat(issue.getDescription()).isEqualTo(error.getDescription());
        var issEx = issue.toCommonRTE(template2, map).setTimeStamp(now);
        var errEx = error.toCommonRTE(template2, map).setTimeStamp(now);
        var fltEx = fault.toCommonRTE(template2, map).setTimeStamp(now);
        assertThat(fltEx)
                .extracting(CommonRTE::getCode, CommonRTE::getMessage,
                        CommonRTE::getDetailedMessage, CommonRTE::getHttpStatus,
                        CommonRTE::getTimeStamp)
                .containsExactly(errEx.getCode(), errEx.getMessage(), errEx.getDetailedMessage(),
                        errEx.getHttpStatus(), errEx.getTimeStamp())
                .containsExactly(issEx.getCode(), issEx.getMessage(), issEx.getDetailedMessage(),
                        issEx.getHttpStatus(), issEx.getTimeStamp());;
    }

    private  static class testFaults__Args implements ArgumentsProvider {
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Arrays.stream(Faults.values())
                    .filter(f-> EnumUtils.isValidEnum(Issues.class, f.name()))
                    .map(f->Arguments.of(Issues.valueOf(f.name()), Errors.valueOf(f.name()), f));
        }
    }

    private  static class testErrors__Args implements ArgumentsProvider {
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(Arguments.of(Issues.values(), Errors.values(), Faults.values()));
        }
    }
}
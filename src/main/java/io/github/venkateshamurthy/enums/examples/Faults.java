package io.github.venkateshamurthy.enums.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.venkateshamurthy.enums.DynamicEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.control.Try;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import static io.github.venkateshamurthy.enums.examples.HttpStatusCode.*;

/**
 * Faults is an example {@link DynamicEnum} for experimental purpose that tries to alleviate the brittle nature of {@link Enum}.
 * Please note that this is a Java-17 record type implementation of {@link DynamicEnum} whereas {@link Errors} is of class type.
 * @param name name of the fault
 * @param description sets the details of the fault
 * @param status is a {@link HttpStatusCode} that can be used n Micros services
 */
@JsonTypeName
public record Faults (
        @JsonProperty @Schema(description = "Name of the fault/error/exception")
        String name,
        @Getter @JsonProperty @Schema(description = "A short description of the fault/error", example = "File is already locked")
        String description,
        @Getter @JsonProperty @Schema(description = "A HTTP status such as BAD_REQUEST")
        HttpStatusCode status) implements DynamicEnum<Faults>, FaultCode {

    /** Unknown error. The field name and the {@link #name()} should match to see this close to an enum.*/
    public static final Faults UNKNOWN = new Faults("UNKNOWN", StringUtils.EMPTY, UNPROCESSABLE_ENTITY);

    /** File already locked fault with a corresponding {@link HttpStatusCode#LOCKED}.*/
    public static final Faults FILE_LOCKED_ERR = new Faults("FILE_LOCKED_ERR",
            "Destination file is already locked. Cannot Lock again", LOCKED);

    /** File checksum error     with a corresponding {@link HttpStatusCode#UNPROCESSABLE_ENTITY}.*/
    public static final Faults FILE_LNCK_ERR = new Faults("FILE_LNCK_ERR",
            "File length/checksum did not match", UNPROCESSABLE_ENTITY);

    /** General validation error  with a corresponding {@link HttpStatusCode#BAD_REQUEST}.*/
    public static final Faults VALIDATION_ERR = new Faults("VALIDATION_ERR", "Invalid Input", BAD_REQUEST);

    /** Server error with a corresponding {@link HttpStatusCode#INTERNAL_SERVER_ERROR}*/
    public static final Faults SERVER_ERR = new Faults("SERVER_ERR", "Internal server error", INTERNAL_SERVER_ERROR);

    /**
     * NOTE: Use compact constructor of record to register using the passed name..
     *       oe if you prefer use {@link #registerSelf()} on the object is fully constructed (not within compact constructor)
     * @param name nae of the fault that would be used to register with
     * @param description short description of the fault
     * @param status the {@link HttpStatusCode} that this fault represents - usually in the context of microservice
     */
    public Faults { register(name);}

    /**
     * Get an array of Fault instances created in this JVM.
     * @return array of {@link Faults}
     */
    public static Faults[] values() {
        return DynamicEnum.values(Faults.class);
    }


    /**
     * A valueOf function getting the Faults corresponding to name passed
     * @param name of {@link Faults}
     * @return Faults
     */
    @JsonCreator
    public static Faults valueOf(String name) {
        return Try.of(()->DynamicEnum.valueOf(Faults.class, name))
                .map(Faults.class::cast)
                .getOrElse(UNKNOWN);
    }
}
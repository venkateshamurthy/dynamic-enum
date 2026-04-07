package io.github.venkateshamurthy.enums.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.venkateshamurthy.enums.DynamicEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * An error class implementation of {@link DynamicEnum}.
 * Please refer to test code for how this can be populated from Spring
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Errors  implements  DynamicEnum<Errors>, FaultCode {
    @JsonProperty @Schema(description = "A short name", example = "FILE_LOCKED_ERROR")
    @Accessors(fluent = true)
    private final String name;

    @JsonProperty @Schema(description = "A short description of the fault/error", example = "File is already locked")
    private final String description;

    @JsonProperty @Schema(description = "A HTTP status such as BAD_REQUEST")
    private final HttpStatus status;

    /**
     * A static creation method that does registering of the instance post construction
     * @param name of the dynamic enum
     * @param description of the dynamic enum
     * @param status of the dynamic enum
     * @return Errors
     */
    public static Errors create(String name, String description, HttpStatus status) {
        return new Errors(name, description, status).registerSelf();
    }

    /** Unknown error. The field name and the {@link #name()} should match to see this close to an enum.*/
    public static final Errors UNKNOWN = create("UNKNOWN", "", UNPROCESSABLE_ENTITY);

    /** File already locked fault with a corresponding {@link HttpStatus#LOCKED}.*/
    public static final Errors FILE_LOCKED_ERR = create("FILE_LOCKED_ERR",
            "Destination file is already locked. Cannot Lock again", LOCKED);

    /** File checksum error with a corresponding {@link HttpStatus#UNPROCESSABLE_ENTITY}.*/
    public static final Errors FILE_LNCK_ERR = create("FILE_LNCK_ERR",
            "File length/checksum did not match", UNPROCESSABLE_ENTITY);

    /** General validation error  with a corresponding {@link HttpStatus#BAD_REQUEST}.*/
    public static final Errors VALIDATION_ERR = builder().name("VALIDATION_ERR")
            .description("Invalid Input").status(BAD_REQUEST).build()
            .registerSelf();

    /** Server error with a corresponding {@link HttpStatus#INTERNAL_SERVER_ERROR}*/
    public static final Errors SERVER_ERR = builder().name("SERVER_ERR")
            .description("Internal server error").status(INTERNAL_SERVER_ERROR).build()
            .registerSelf();

    /**
     * Gets an array of {@link Errors} instances similar to {@link Enum}.values()
     * @return Errors[]
     */
    public static Errors[] values() {return DynamicEnum.values(Errors.class);}

    /**
     * Get {@link Errors} for a given name
     * @param name for which {@code Errors} to be fetched
     * @return {@link Errors} corresponding to passed name
     */
    public static Errors valueOf(String name) {return DynamicEnum.valueOf(Errors.class, name);}
}


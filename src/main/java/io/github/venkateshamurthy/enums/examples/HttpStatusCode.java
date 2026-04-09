package io.github.venkateshamurthy.enums.examples;

import io.github.venkateshamurthy.enums.DynamicEnum;
import io.github.venkateshamurthy.enums.ReadWriteLockedMap;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A simple dynamic enum to represent HTTP Status codes.
 * Known error codes are defined as constants, but additional codes
 * can be registered dynamically since this is a {@link DynamicEnum}.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EqualsAndHashCode()
public class HttpStatusCode implements DynamicEnum<HttpStatusCode> {

    /**
     * The symbolic name of the HTTP status code (e.g., "NOT_FOUND").
     */
    @Accessors(fluent = true)
    private final String name;

    /**
     * The numeric HTTP status code (e.g., 404).
     */
    private final int code;

    /**
     * The human-readable reason phrase (e.g., "Not Found").
     */
    private final String reason;

    private static final String[] searchChars = new String[]{" ", "-"};
    private static final String[] replacementChars = new String[]{"_", "_"};
    private static final Map<Integer, HttpStatusCode> codeToStatus = new ReadWriteLockedMap<>(new LinkedHashMap<>());

    /**
     * Creates and registers a new {@link HttpStatusCode} instance.
     *
     * @param code   the numeric HTTP status code
     * @param reason the reason phrase
     * @return a new {@link HttpStatusCode} instance
     */
    public static HttpStatusCode of(final int code, @NonNull final String reason) {
        final String nam = StringUtils.replaceEach(reason.toUpperCase(), searchChars, replacementChars);
        final HttpStatusCode statusCode = new HttpStatusCode(nam, code, reason).registerSelf();
        codeToStatus.put(code, statusCode);
        return statusCode;
    }

    /**
     * Returns all registered {@link HttpStatusCode} values.
     *
     * @return an array of {@link HttpStatusCode} instances
     */
    public static HttpStatusCode[] values() {
        return DynamicEnum.values(HttpStatusCode.class);
    }

    /**
     * Returns the {@link HttpStatusCode} for the given numeric code.
     *
     * @param code the numeric HTTP status code
     * @return the corresponding {@link HttpStatusCode}
     * @throws RuntimeException if the code is not registered
     */
    public static HttpStatusCode valueOf(final int code) {
        return Optional.ofNullable(codeToStatus.get(code))
                .orElseThrow(() -> new RuntimeException("Invalid HTTP status code: " + code));
    }

    /**
     * Returns the {@link HttpStatusCode} for the given symbolic name.
     *
     * @param name the symbolic name of the status code
     * @return the corresponding {@link HttpStatusCode}
     * @throws RuntimeException if the name is not registered
     */
    public static HttpStatusCode valueOf(@NonNull final String name) {
        System.out.println("Checking for:" + name + " value:" + DynamicEnum.valueOf(HttpStatusCode.class, name));
        return DynamicEnum.valueOf(HttpStatusCode.class, name);
    }

    /**
     * Checks if the status code is within a given range.
     *
     * @param left  inclusive lower bound
     * @param right exclusive upper bound
     * @return true if code is within the range
     */
    protected boolean withinRange(int left, int right) {
        return code >= left && code < right;
    }

    /**
     * Checks if the status code is informational (1xx).
     *
     * @return true if code is within [100,200)
     */
    public boolean is1xxInformational() {
        return withinRange(100, 200);
    }

    /**
     * Checks if the status code is successful (2xx).
     *
     * @return true if code is within [200,300)
     */
    public boolean is2xxSuccessful() {
        return withinRange(200, 300);
    }

    /**
     * Checks if the status code is a redirection (3xx).
     *
     * @return true if code is within [300,400)
     */
    public boolean is3xxRedirection() {
        return withinRange(300, 400);
    }

    /**
     * Checks if the status code is a client error (4xx).
     *
     * @return true if code is within [400,500)
     */
    public boolean is4xxClientError() {
        return withinRange(400, 500);
    }

    /**
     * Checks if the status code is a server error (5xx).
     *
     * @return true if code is within [500,600)
     */
    public boolean is5xxServerError() {
        return withinRange(500, 600);
    }

    // ---- Constants for standard HTTP status codes ----

    /**
     * 100 Continue
     */
    public static final HttpStatusCode CONTINUE = of(100, "Continue");
    /**
     * 101 Switching Protocols
     */
    public static final HttpStatusCode SWITCHING_PROTOCOLS = of(101, "Switching Protocols");
    /**
     * 102 Processing
     */
    public static final HttpStatusCode PROCESSING = of(102, "Processing");
    /**
     * 103 Early Hints
     */
    public static final HttpStatusCode EARLY_HINTS = of(103, "Early Hints");

    /**
     * 200 OK
     */
    public static final HttpStatusCode OK = of(200, "OK");
    /**
     * 201 Created
     */
    public static final HttpStatusCode CREATED = of(201, "Created");
    /**
     * 202 Accepted
     */
    public static final HttpStatusCode ACCEPTED = of(202, "Accepted");
    /**
     * 203 Non-Authoritative Information
     */
    public static final HttpStatusCode NON_AUTHORITATIVE_INFORMATION = of(203, "Non-Authoritative Information");
    /**
     * 204 No Content
     */
    public static final HttpStatusCode NO_CONTENT = of(204, "No Content");
    /**
     * 205 Reset Content
     */
    public static final HttpStatusCode RESET_CONTENT = of(205, "Reset Content");
    /**
     * 206 Partial Content
     */
    public static final HttpStatusCode PARTIAL_CONTENT = of(206, "Partial Content");
    /**
     * 207 Multi-Status
     */
    public static final HttpStatusCode MULTI_STATUS = of(207, "Multi-Status");
    /**
     * 208 Already Reported
     */
    public static final HttpStatusCode ALREADY_REPORTED = of(208, "Already Reported");
    /**
     * 226 IM Used
     */
    public static final HttpStatusCode IM_USED = of(226, "IM Used");

    /**
     * 300 Multiple Choices
     */
    public static final HttpStatusCode MULTIPLE_CHOICES = of(300, "Multiple Choices");
    /**
     * 301 Moved Permanently
     */
    public static final HttpStatusCode MOVED_PERMANENTLY = of(301, "Moved Permanently");
    /**
     * 302 Found
     */
    public static final HttpStatusCode FOUND = of(302, "Found");
    /**
     * 303 See Other
     */
    public static final HttpStatusCode SEE_OTHER = of(303, "See Other");
    /**
     * 304 Not Modified
     */
    public static final HttpStatusCode NOT_MODIFIED = of(304, "Not Modified");
    /**
     * 305 Use Proxy
     */
    public static final HttpStatusCode USE_PROXY = of(305, "Use Proxy");
    /**
     * 307 Temporary Redirect
     */
    public static final HttpStatusCode TEMPORARY_REDIRECT = of(307, "Temporary Redirect");
    /**
     * 308 Permanent Redirect
     */
    public static final HttpStatusCode PERMANENT_REDIRECT = of(308, "Permanent Redirect");

    /**
     * 400 Bad Request
     */
    public static final HttpStatusCode BAD_REQUEST = of(400, "Bad Request");
    /**
     * 401 Unauthorized
     */
    public static final HttpStatusCode UNAUTHORIZED = of(401, "Unauthorized");
    /**
     * 402 Payment Required
     */
    public static final HttpStatusCode PAYMENT_REQUIRED = of(402, "Payment Required");
    /**
     * 403 Forbidden
     */
    public static final HttpStatusCode FORBIDDEN = of(403, "Forbidden");
    /**
     * 404 Not Found
     */
    public static final HttpStatusCode NOT_FOUND = of(404, "Not Found");
    /**
     * 405 Method Not Allowed
     */
    public static final HttpStatusCode METHOD_NOT_ALLOWED = of(405, "Method Not Allowed");
    /**
     * 406 Not Acceptable
     */
    public static final HttpStatusCode NOT_ACCEPTABLE = of(406, "Not Acceptable");
    /**
     * 407 Proxy Authentication Required
     */
    public static final HttpStatusCode PROXY_AUTHENTICATION_REQUIRED = of(407, "Proxy Authentication Required");
    /**
     * 408 Request Timeout
     */
    public static final HttpStatusCode REQUEST_TIMEOUT = of(408, "Request Timeout");
    /**
     * 409 Conflict
     */
    public static final HttpStatusCode CONFLICT = of(409, "Conflict");
    /**
     * 410 Gone
     */
    public static final HttpStatusCode GONE = of(410, "Gone");
    /**
     * 411 Length Required
     */
    public static final HttpStatusCode LENGTH_REQUIRED = of(411, "Length Required");
    /**
     * 413 Payload Too Large
     */
    public static final HttpStatusCode PAYLOAD_TOO_LARGE = of(413, "Payload Too Large");
    /**
     * 414 URI Too Long
     */
    public static final HttpStatusCode URI_TOO_LONG = of(414, "URI Too Long");
    /**
     * 415 Unsupported Media Type
     */
    public static final HttpStatusCode UNSUPPORTED_MEDIA_TYPE = of(415, "Unsupported Media Type");
    /**
     * 416 Range Not Satisfiable
     */
    public static final HttpStatusCode RANGE_NOT_SATISFIABLE = of(416, "Range Not Satisfiable");
    /**
     * 417 Expectation Failed
     */
    public static final HttpStatusCode EXPECTATION_FAILED = of(417, "Expectation Failed");
    /**
     * 418 I'm a teapot
     */
    public static final HttpStatusCode IM_A_TEAPOT = of(418, "I'm a teapot");
    /**
     * 421 Misdirected Request
     */
    public static final HttpStatusCode MISDIRECTED_REQUEST = of(421, "Misdirected Request");
    /**
     * 422 Unprocessable Entity
     */
    public static final HttpStatusCode UNPROCESSABLE_ENTITY = of(422, "Unprocessable Entity");
    /**
     * 423 Locked
     */
    public static final HttpStatusCode LOCKED = of(423, "Locked");
    /**
     * 424 Failed Dependency
     */
    public static final HttpStatusCode FAILED_DEPENDENCY = of(424, "Failed Dependency");
    /**
     * 425 Too Early
     */
    public static final HttpStatusCode TOO_EARLY = of(425, "Too Early");
    /**
     * 426 Upgrade Required
     */
    public static final HttpStatusCode UPGRADE_REQUIRED = of(426, "Upgrade Required");
    /**
     * 428 Precondition Required
     */
    public static final HttpStatusCode PRECONDITION_REQUIRED = of(428, "Precondition Required");
    /**
     * 429 Too Many Requests
     */
    public static final HttpStatusCode TOO_MANY_REQUESTS = of(429, "Too Many Requests");
    /**
     * 431 Request Header Fields Too Large
     */
    public static final HttpStatusCode REQUEST_HEADER_FIELDS_TOO_LARGE = of(431, "Request Header Fields Too Large");
    /**
     * 451 Unavailable For Legal Reasons
     */
    public static final HttpStatusCode UNAVAILABLE_FOR_LEGAL_REASONS = of(451, "Unavailable For Legal Reasons");

    /**
     * 500 Internal Server Error
     */
    public static final HttpStatusCode INTERNAL_SERVER_ERROR = of(500, "Internal Server Error");
    /**
     * 501 Not Implemented
     */
    public static final HttpStatusCode NOT_IMPLEMENTED = of(501, "Not Implemented");
    /**
     * 502 Bad Gateway
     */
    public static final HttpStatusCode BAD_GATEWAY = of(502, "Bad Gateway");
    /**
     * 503 Service Unavailable
     */
    public static final HttpStatusCode SERVICE_UNAVAILABLE = of(503, "Service Unavailable");
    /**
     * 504 Gateway Timeout
     */
    public static final HttpStatusCode GATEWAY_TIMEOUT = of(504, "Gateway Timeout");
    /**
     * 505 HTTP Version Not Supported
     */
    public static final HttpStatusCode HTTP_VERSION_NOT_SUPPORTED = of(505, "HTTP Version Not Supported");
    /**
     * 506 Variant Also Negotiates
     */
    public static final HttpStatusCode VARIANT_ALSO_NEGOTIATES = of(506, "Variant Also Negotiates");
    /**
     * 507 Insufficient Storage
     */
    public static final HttpStatusCode INSUFFICIENT_STORAGE = of(507, "Insufficient Storage");
    /**
     * 508 Loop Detected
     */
    public static final HttpStatusCode LOOP_DETECTED = of(508, "Loop Detected");
    /**
     * 510 Not Extended
     */
    public static final HttpStatusCode NOT_EXTENDED = of(510, "Not Extended");
    /**
     * 511 Network Authentication Required
     */
    public static final HttpStatusCode NETWORK_AUTHENTICATION_REQUIRED = of(511, "Network Authentication Required");
}
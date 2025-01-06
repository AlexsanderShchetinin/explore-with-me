package ewm_stats_service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExceptionParserDateTime(DateTimeParseException e) {
        log.debug("{}[33m Error with deserialized dateTime: {} {}[37m", (char) 27, e.getMessage(), (char) 27);
        return new ErrorResponse("Error with deserialized dateTime: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExceptionParserTypeValue(MethodArgumentTypeMismatchException e) {
        log.debug("{}[33m Error with convert value: {} {}[37m", (char) 27, e.getMessage(), (char) 27);
        return new ErrorResponse("Error with convert value: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalid(final MethodArgumentNotValidException e) {
        log.debug("{}[33m Invalid data: {} {}[37m", (char) 27, e.getMessage(), (char) 27);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalid(final MissingRequestHeaderException e) {
        log.debug("{}[33m Missing Request Header: {} {}[37m", (char) 27, e.getMessage(), (char) 27);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e) {
        log.warn("{}[31m Error: {} {}[37m", (char) 27, e, (char) 27);
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string
        errorResponse.setStackTrace(sStackTrace);
        return errorResponse;
    }

}

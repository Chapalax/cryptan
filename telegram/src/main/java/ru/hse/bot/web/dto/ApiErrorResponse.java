package ru.hse.bot.web.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends RuntimeException {
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    String[] stacktrace;
}

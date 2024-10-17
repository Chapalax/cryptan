package ru.hse.bot.web.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends Exception {
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    ArrayList<String> stacktrace;
}

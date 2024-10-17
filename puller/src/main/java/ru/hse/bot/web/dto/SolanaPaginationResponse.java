package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

public record SolanaPaginationResponse(
        @NotNull Integer currentPage,
        @NotNull Integer totalPages
) { }
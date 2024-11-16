package ru.hse.bot.web.dto.accounts;

import jakarta.validation.constraints.NotNull;

public record SolanaPaginationResponse(
        @NotNull Integer currentPage,
        @NotNull Integer totalPages
) { }
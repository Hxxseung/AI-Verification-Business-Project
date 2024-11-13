package com.sparta.aibusinessproject.security.jwt;

import lombok.Getter;

@Getter public enum TokenStatus {
    VALID,
    EXPIRED,
    INVALID
}

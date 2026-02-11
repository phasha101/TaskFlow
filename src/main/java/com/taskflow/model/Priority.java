package com.taskflow.model;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Priority {
    LOW,
    MEDIUM,
    HIGH
}

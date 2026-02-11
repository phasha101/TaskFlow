package com.taskflow.model;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Status{
    PENDING, COMPLETE
}
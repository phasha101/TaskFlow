package com.taskflow.model;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Category{
    WORK, STUDY, CHORE, COOK, EXERCISE
}
package com.droidablebee.kafka.tool.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation that is used by Jacoco to exclude a method or a class from its coverage report.
 * Annotation must have RetentionPolicy of CLASS or RUNTIME.
 * Existing groovy or lombok @Generated annotation can be used.
 * See <a href="https://github.com/jacoco/jacoco/pull/822/files">jacoco PR 822</a>.
 * <pre>
 * Classes and methods annotated with runtime visible and invisible annotation
 * whose simple name contains "Generated" are filtered out during generation of report.
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ExcludeFromJacocoGeneratedReport {

    /**
     * The reason for exclusion.
     */
    String[] value();
}
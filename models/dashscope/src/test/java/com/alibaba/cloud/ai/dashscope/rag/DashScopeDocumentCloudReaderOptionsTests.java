/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.dashscope.rag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test cases for DashScopeDocumentCloudReaderOptions. Tests cover default constructor and
 * parameterized constructor behavior.
 *
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 * @author brianxiadong
 * @since 1.0.0-M5.1
 */
class DashScopeDocumentCloudReaderOptionsTests {

    @Test
    void testDefaultConstructor() {
        // Test default constructor
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions();

        // Verify default value is "default"
        assertThat(options.getCategoryId()).isEqualTo("default");

        //
        assertEquals(0, options.getMaxRetryAttempts());
        assertEquals(30_000L, options.getRetryIntervalMillis());
        assertEquals(300_000L, options.getMaxRetryIntervalMillis());
        assertTrue(options.isUseExponentialBackoff());
        assertEquals(1.5, options.getBackoffMultiplier());
        assertEquals(100 * 1024 * 1024L, options.getMaxFileSize());
        assertEquals(1L, options.getMinFileSize());
        assertTrue(options.isEnableFileSizeValidation());
    }

    @Test
    void testParameterizedConstructor() {
        // Test parameterized constructor
        String customCategoryId = "custom-category";
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions(customCategoryId);

        // Verify custom value is set correctly
        assertThat(options.getCategoryId()).isEqualTo(customCategoryId);
    }


    @Test
    void testConstructorWithCategoryId() {
        String categoryId = "test-category";
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions(categoryId);

        assertEquals(categoryId, options.getCategoryId());
    }

    @Test
    void testSettersAndGetters() {
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions();

        options.setCategoryId("custom");
        options.setMaxRetryAttempts(20);
        options.setRetryIntervalMillis(15000L);
        options.setMaxRetryIntervalMillis(600000L);
        options.setUseExponentialBackoff(false);
        options.setBackoffMultiplier(2.0);
        options.setMaxFileSize(50 * 1024 * 1024L);
        options.setMinFileSize(100L);
        options.setEnableFileSizeValidation(false);

        assertEquals("custom", options.getCategoryId());
        assertEquals(20, options.getMaxRetryAttempts());
        assertEquals(15000L, options.getRetryIntervalMillis());
        assertEquals(600000L, options.getMaxRetryIntervalMillis());
        assertFalse(options.isUseExponentialBackoff());
        assertEquals(2.0, options.getBackoffMultiplier());
        assertEquals(50 * 1024 * 1024L, options.getMaxFileSize());
        assertEquals(100L, options.getMinFileSize());
        assertFalse(options.isEnableFileSizeValidation());
    }

    @Test
    void testFluentMethods() {
        DashScopeDocumentCloudReaderOptions options =
                new DashScopeDocumentCloudReaderOptions()
                        .withRetryIntervalSeconds(15)
                        .withMaxRetryIntervalSeconds(300)
                        .withMaxFileSizeMB(50)
                        .withMinFileSizeBytes(1024)
                        .withoutFileSizeValidation();

        assertEquals(15000L, options.getRetryIntervalMillis());
        assertEquals(300000L, options.getMaxRetryIntervalMillis());
        assertEquals(50 * 1024 * 1024L, options.getMaxFileSize());
        assertEquals(1024L, options.getMinFileSize());
        assertFalse(options.isEnableFileSizeValidation());
    }

    @Test
    void testIsFileSizeValid() {
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions();
        options.setMinFileSize(100L);
        options.setMaxFileSize(1000L);

        assertTrue(options.isFileSizeValid(500L));
        assertTrue(options.isFileSizeValid(100L));  // Min boundary
        assertTrue(options.isFileSizeValid(1000L)); // Max boundary
        assertFalse(options.isFileSizeValid(50L));  // Below min
        assertFalse(options.isFileSizeValid(2000L)); // Above max
    }

    @Test
    void testIsFileSizeValidWhenDisabled() {
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions();
        options.setEnableFileSizeValidation(false);
        options.setMaxFileSize(10L);

        // Should be valid even when size exceeds limit
        assertTrue(options.isFileSizeValid(1000L));
    }

    @Test
    void testGetFileSizeLimitsDescription() {
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions();

        String description = options.getFileSizeLimitsDescription();

        assertNotNull(description);
        assertTrue(description.contains("between"));
        assertTrue(description.contains("100.00 MB"));
    }

    @Test
    void testToString() {
        DashScopeDocumentCloudReaderOptions options = new DashScopeDocumentCloudReaderOptions();

        String toString = options.toString();

        assertTrue(toString.contains("categoryId"));
        assertTrue(toString.contains("maxRetryAttempts"));
        assertTrue(toString.contains("100.00 MB")); // Formatted file size
    }
}

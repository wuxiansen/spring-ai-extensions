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

import com.alibaba.cloud.ai.dashscope.rag.util.FileSizeFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nuocheng.lxm
 * @since 2024/7/22 15:14 百炼文档解析相关配置项
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashScopeDocumentCloudReaderOptions {

    /**
     * Default maximum file size: 100MB
     * DashScope API typically has a file size limit
     */
    public static final long DEFAULT_MAX_FILE_SIZE = 100 * 1024 * 1024L;

    /**
     * Default minimum file size: 1 byte
     * Empty files are not allowed
     */
    public static final long DEFAULT_MIN_FILE_SIZE = 1L;

    /**
     * Default constructor
     */
    public DashScopeDocumentCloudReaderOptions() {
        this.categoryId = "default";
        this.maxRetryAttempts = 0; // Use API default value
        this.retryIntervalMillis = 30_000L; // 30 seconds
        this.maxRetryIntervalMillis = 300_000L; // 5 minutes
        this.useExponentialBackoff = true;
        this.backoffMultiplier = 1.5;
        this.maxFileSize = DEFAULT_MAX_FILE_SIZE;
        this.minFileSize = DEFAULT_MIN_FILE_SIZE;
        this.enableFileSizeValidation = true;
    }

    /**
     * Constructor with category ID
     */
    public DashScopeDocumentCloudReaderOptions(String categoryId) {
        this();
        this.categoryId = categoryId;
    }

    /**
     * Category ID
     * If not specified, documents will be uploaded to the default category
     */
    @JsonProperty("category_id")
    private String categoryId;

    /**
     * Maximum retry attempts
     * Default value is determined by DashScopeApiConstants.MAX_TRY_COUNT
     */
    @JsonProperty("max_retry_attempts")
    private int maxRetryAttempts;

    /**
     * Retry interval in milliseconds
     * Default is 30 seconds
     */
    @JsonProperty("retry_interval_millis")
    private long retryIntervalMillis;

    /**
     * Maximum retry interval in milliseconds
     * Used for exponential backoff strategy, default is 5 minutes
     */
    @JsonProperty("max_retry_interval_millis")
    private long maxRetryIntervalMillis;

    /**
     * Whether to use exponential backoff
     * Default is true
     */
    @JsonProperty("use_exponential_backoff")
    private boolean useExponentialBackoff;

    /**
     * Backoff multiplier
     * Default is 1.5
     */
    @JsonProperty("backoff_multiplier")
    private double backoffMultiplier;

    /**
     * Maximum file size in bytes
     * Files exceeding this size will be rejected
     * Default is 100MB
     */
    @JsonProperty("max_file_size")
    private long maxFileSize;

    /**
     * Minimum file size in bytes
     * Files smaller than this size will be rejected
     * Default is 1 byte (empty files not allowed)
     */
    @JsonProperty("min_file_size")
    private long minFileSize;

    /**
     * Whether to enable file size validation
     * Default is true
     */
    @JsonProperty("enable_file_size_validation")
    private boolean enableFileSizeValidation;

    // ==================== Getters and Setters ====================

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public void setMaxRetryAttempts(int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
    }

    public long getRetryIntervalMillis() {
        return retryIntervalMillis;
    }

    public void setRetryIntervalMillis(long retryIntervalMillis) {
        this.retryIntervalMillis = retryIntervalMillis;
    }

    public long getMaxRetryIntervalMillis() {
        return maxRetryIntervalMillis;
    }

    public void setMaxRetryIntervalMillis(long maxRetryIntervalMillis) {
        this.maxRetryIntervalMillis = maxRetryIntervalMillis;
    }

    public boolean isUseExponentialBackoff() {
        return useExponentialBackoff;
    }

    public void setUseExponentialBackoff(boolean useExponentialBackoff) {
        this.useExponentialBackoff = useExponentialBackoff;
    }

    public double getBackoffMultiplier() {
        return backoffMultiplier;
    }

    public void setBackoffMultiplier(double backoffMultiplier) {
        this.backoffMultiplier = backoffMultiplier;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public long getMinFileSize() {
        return minFileSize;
    }

    public void setMinFileSize(long minFileSize) {
        this.minFileSize = minFileSize;
    }

    public boolean isEnableFileSizeValidation() {
        return enableFileSizeValidation;
    }

    public void setEnableFileSizeValidation(boolean enableFileSizeValidation) {
        this.enableFileSizeValidation = enableFileSizeValidation;
    }

    // ==================== Builder Methods ====================

    /**
     * Sets retry interval in seconds
     */
    public DashScopeDocumentCloudReaderOptions withRetryIntervalSeconds(long seconds) {
        this.retryIntervalMillis = seconds * 1000L;
        return this;
    }

    /**
     * Sets maximum retry interval in seconds
     */
    public DashScopeDocumentCloudReaderOptions withMaxRetryIntervalSeconds(long seconds) {
        this.maxRetryIntervalMillis = seconds * 1000L;
        return this;
    }

    /**
     * Sets maximum file size in megabytes
     *
     * @param megabytes maximum file size in MB
     * @return this options instance for chaining
     */
    public DashScopeDocumentCloudReaderOptions withMaxFileSizeMB(int megabytes) {
        this.maxFileSize = megabytes * 1024L * 1024L;
        return this;
    }

    /**
     * Sets minimum file size in bytes
     *
     * @param bytes minimum file size in bytes
     * @return this options instance for chaining
     */
    public DashScopeDocumentCloudReaderOptions withMinFileSizeBytes(long bytes) {
        this.minFileSize = bytes;
        return this;
    }

    /**
     * Disables file size validation
     *
     * @return this options instance for chaining
     */
    public DashScopeDocumentCloudReaderOptions withoutFileSizeValidation() {
        this.enableFileSizeValidation = false;
        return this;
    }

    // ==================== Validation Methods ====================

    /**
     * Validates if the given file size is acceptable
     *
     * @param fileSize file size in bytes
     * @return true if valid, false otherwise
     */
    public boolean isFileSizeValid(long fileSize) {
        if (!enableFileSizeValidation) {
            return true;
        }
        return fileSize >= minFileSize && fileSize <= maxFileSize;
    }

    /**
     * Gets a human-readable description of file size limits
     *
     * @return file size limits description
     */
    public String getFileSizeLimitsDescription() {
        return String.format("File size must be between %s and %s",
                             FileSizeFormatter.format(minFileSize),
                             FileSizeFormatter.format(maxFileSize));
    }


    @Override
    public String toString() {
        return "DashScopeDocumentCloudReaderOptions{" +
               "categoryId='" + categoryId + '\'' +
               ", maxRetryAttempts=" + maxRetryAttempts +
               ", retryIntervalMillis=" + retryIntervalMillis +
               ", maxRetryIntervalMillis=" + maxRetryIntervalMillis +
               ", useExponentialBackoff=" + useExponentialBackoff +
               ", backoffMultiplier=" + backoffMultiplier +
               ", maxFileSize=" + FileSizeFormatter.format(maxFileSize) +
               ", minFileSize=" + FileSizeFormatter.format(minFileSize) +
               ", enableFileSizeValidation=" + enableFileSizeValidation +
               '}';
    }
}

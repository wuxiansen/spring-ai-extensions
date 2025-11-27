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
package com.alibaba.cloud.ai.dashscope.rag.handler;


import com.alibaba.cloud.ai.dashscope.rag.context.DocumentProcessContext;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeApiSpec.CommonResponse;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeApiSpec.QueryFileResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

/**
 * Parse failed handler
 *
 * <p>Handles the PARSE_FAILED status, indicating that file parsing
 * has failed. Extracts error information from the response.
 *
 * @author kevin
 * @since 2025/11/27
 */
public class ParseFailedHandler implements FileStatusHandler {

    private static final Logger logger = LoggerFactory.getLogger(ParseFailedHandler.class);

    @Override
    public FileStatusResult handle(DocumentProcessContext context,
                                   ResponseEntity<CommonResponse<QueryFileResponseData>> response) {
        String errorCode = response.getBody().code();
        String errorMessage = response.getBody().message();

        logger.error("File parsing failed. FileId: {}, ErrorCode: {}, ErrorMessage: {}",
                     context.getFileId(), errorCode, errorMessage);

        String message = String.format(
                "File parsing failed - FileId: %s, ErrorCode: %s, ErrorMessage: %s",
                context.getFileId(), errorCode, errorMessage);

        return FileStatusResult.failure(message);
    }
}

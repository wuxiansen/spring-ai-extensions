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
import com.alibaba.cloud.ai.dashscope.spec.DashScopeApiSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

/**
 * Parsing in progress handler
 *
 * <p>Handles the PARSING and UPLOADED statuses, indicating that file parsing
 * is still in progress and the caller should continue polling.
 *
 * @author kevin
 * @since 2025/11/27
 */
public class ParsingHandler implements FileStatusHandler {

    private static final Logger logger = LoggerFactory.getLogger(ParsingHandler.class);

    /**
     * Handles parsing in progress status
     *
     * @param context document processing context
     * @param response API response
     * @return in-progress result indicating continued polling is needed
     */
    @Override
    public FileStatusResult handle(DocumentProcessContext context,
                                   ResponseEntity<DashScopeApiSpec.CommonResponse<
                                           DashScopeApiSpec.QueryFileResponseData>> response) {
        logger.debug("File is still parsing. FileId: {}", context.getFileId());
        return FileStatusResult.inProgress();
    }
}

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File status handler factory - Factory Pattern
 *
 * <p>Creates appropriate handler instances based on file status.
 * Implements the Factory Pattern to encapsulate handler creation logic.
 *
 * <p>Supported statuses:
 * <ul>
 *   <li>PARSE_SUCCESS: Returns ParseSuccessHandler</li>
 *   <li>PARSE_FAILED: Returns ParseFailedHandler</li>
 *   <li>PARSING: Returns ParsingHandler</li>
 *   <li>UPLOADED: Returns ParsingHandler</li>
 *   <li>Unknown: Returns ParsingHandler (safe default)</li>
 * </ul>
 *
 * @author kevin
 * @since 2025/11/27
 */
public class FileStatusHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(FileStatusHandlerFactory.class);

    private final ParseSuccessHandler successHandler = new ParseSuccessHandler();
    private final ParseFailedHandler failedHandler = new ParseFailedHandler();
    private final ParsingHandler parsingHandler = new ParsingHandler();

    /**
     * Gets handler based on status
     *
     * @param status file status string
     * @return appropriate handler for the status
     */
    public FileStatusHandler getHandler(String status) {
        if (status == null) {
            logger.warn("Received null status, treating as PARSING");
            return parsingHandler;
        }

        return switch (status) {
            case "PARSE_SUCCESS" -> successHandler;
            case "PARSE_FAILED" -> failedHandler;
            case "PARSING", "UPLOADED" -> parsingHandler;
            default -> {
                logger.warn("Unknown file status: {}, treating as PARSING", status);
                yield parsingHandler;
            }
        };
    }
}

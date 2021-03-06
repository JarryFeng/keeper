/**
 * Copyright (c) 2019, biezhi (biezhi.me@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.biezhi.keeper.core.jwt;

public interface JwtToken {

    /**
     * Create a token that sets the username to subject
     *
     * @param username unique identity of the user currently logged in
     * @return return a new JWT token
     */
    String create(String username);

    /**
     * Parse the username based on the incoming JWT token
     *
     * @param token jwt token
     * @return username
     */
    String getUsername(String token);

    /**
     * Verify that the incoming token has expired
     *
     * @param token jwt token
     */
    boolean isExpired(String token);

    /**
     * Verify that the incoming token can be refreshed if the claim to refresh does not expire
     *
     * @param token jwt token
     */
    boolean canRefresh(String token);

    /**
     * @return returns token of the current request context, obtained in the header
     */
    String getAuthToken();

    /**
     * Refreshing the current user's JWT token writes a new token to the header of the response.
     * the client should replace this token with the latest accessible JWT token
     *
     * @param username unique identity of the user currently logged in
     */
    String refresh(String username);

}

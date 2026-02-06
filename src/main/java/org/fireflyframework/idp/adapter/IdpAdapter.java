/*
 * Copyright 2024-2026 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.fireflyframework.idp.adapter;

import org.fireflyframework.idp.dtos.*;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Identity Provider (IdP) adapter interface that standardizes common authentication and
 * user‑management operations across different IdPs such as Keycloak, AWS Cognito, Okta, etc.
 *
 * Implementations should translate these generic operations into the specific API calls of the
 * target provider while keeping a consistent, reactive API based on Reactor's Mono.
 */
public interface IdpAdapter {

    /**
     * Authenticate a user and obtain tokens.
     *
     * Typical mapping is an OAuth2/OIDC token endpoint using Resource Owner Password Credentials,
     * or any custom username/password flow supported by the provider.
     *
     * @param request credentials and client details for the login attempt
     * @return a reactive publisher yielding a ResponseEntity with a TokenResponse on success
     */
    Mono<ResponseEntity<TokenResponse>> login(LoginRequest request);

    /**
     * Refresh the access token using a refresh token.
     *
     * @param request refresh token and (optionally) client info
     * @return a reactive publisher with a ResponseEntity containing a new TokenResponse
     */
    Mono<ResponseEntity<TokenResponse>> refresh(RefreshRequest request);

    /**
     * Invalidate tokens or perform a provider-specific logout.
     *
     * Implementations may call the IdP's logout or token revocation endpoint.
     *
     * @param request contains accessToken and refreshToken to invalidate
     */
    Mono<Void> logout(LogoutRequest request);

    /**
     * Introspect an access token to verify its activity and claims (RFC 7662).
     *
     * @param accessToken the token to introspect
     * @return a reactive publisher with a ResponseEntity containing introspection details
     */
    Mono<ResponseEntity<IntrospectionResponse>> introspect(String accessToken);

    /**
     * Retrieve OpenID Connect user info associated with the provided access token.
     *
     * @param accessToken a valid access token
     * @return a reactive publisher with a ResponseEntity containing user info claims
     */
    Mono<ResponseEntity<UserInfoResponse>> getUserInfo(String accessToken);

    /**
     * Create a new user at the identity provider.
     *
     * @param request details required to create the user (username, email, etc.)
     * @return a reactive publisher with a ResponseEntity containing the created user's summary
     */
    Mono<ResponseEntity<CreateUserResponse>> createUser(CreateUserRequest request);

    /**
     * Change a user's password.
     *
     * Implementations should enforce provider-specific requirements (e.g., old password check).
     *
     * @param request contains user identifier and new/old passwords
     */
    Mono<Void> changePassword(ChangePasswordRequest request);

    /**
     * Trigger a password reset for a given username (e.g., send reset email/SMS).
     *
     * @param username the username (or login identifier) for which to initiate reset
     */
    Mono<Void> resetPassword(String username);

    /**
     * Initiate a Multi‑Factor Authentication (MFA) challenge for a user.
     *
     * @param username the username to challenge
     * @return a reactive publisher with challenge details (delivery method, expiry, etc.)
     */
    Mono<ResponseEntity<MfaChallengeResponse>> mfaChallenge(String username);

    /**
     * Verify a previously initiated MFA challenge with the supplied code.
     *
     * @param request verification payload including challenge id and code
     */
    Mono<Void> mfaVerify(MfaVerifyRequest request);

    /**
     * Revoke an issued refresh token.
     *
     * @param refreshToken the refresh token to revoke
     */
    Mono<Void> revokeRefreshToken(String refreshToken);

    /**
     * List active sessions for a given user.
     *
     * @param userId the user identifier
     * @return a reactive publisher with a list of session information
     */
    Mono<ResponseEntity<List<SessionInfo>>> listSessions(String userId);

    /**
     * Revoke a specific session by its identifier.
     *
     * @param sessionId the session identifier to revoke
     */
    Mono<Void> revokeSession(String sessionId);

    /**
     * Retrieve the roles assigned to a user.
     *
     * @param userId the user identifier
     * @return a reactive publisher with the list of role names
     */
    Mono<ResponseEntity<List<String>>> getRoles(String userId);

    /**
     * Delete a user by its identifier in the IdP.
     *
     * @param userId the provider-specific user id
     */
    Mono<Void> deleteUser(String userId);

    /**
     * Update an existing user. Fields left null in the request should not be modified.
     *
     * @param request details to update and user identifier
     * @return a reactive publisher with a summary of the updated user
     */
    Mono<ResponseEntity<UpdateUserResponse>> updateUser(UpdateUserRequest request);

    /**
     * Create one or more roles at the IdP (realm or client depending on context).
     *
     * @param request role names (and optional context/description)
     * @return a reactive publisher with the list of created role names
     */
    Mono<ResponseEntity<CreateRolesResponse>> createRoles(CreateRolesRequest request);

    /**
     * Create a new scope (e.g., OAuth2 scope or Keycloak client scope).
     *
     * @param request scope name (and optional context/description)
     * @return a reactive publisher with created scope information
     */
    Mono<ResponseEntity<CreateScopeResponse>> createScope(CreateScopeRequest request);

    /**
     * Assign roles to a user.
     *
     * @param request user id and role names to assign
     */
    Mono<Void> assignRolesToUser(AssignRolesRequest request);

    /**
     * Remove roles from a user.
     *
     * @param request user id and role names to remove
     */
    Mono<Void> removeRolesFromUser(AssignRolesRequest request);
}

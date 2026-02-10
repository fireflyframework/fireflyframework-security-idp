# Firefly Framework - IDP Library

[![CI](https://github.com/fireflyframework/fireflyframework-idp/actions/workflows/ci.yml/badge.svg)](https://github.com/fireflyframework/fireflyframework-idp/actions/workflows/ci.yml)

A small, provider-agnostic interface to standardize Identity Provider (IdP) operations across platforms such as Keycloak, AWS Cognito, Okta, Auth0, and others. It exposes a consistent, reactive API so your application code remains clean and portable, while concrete implementations translate calls into provider‑specific requests.

## Table of Contents
- [1. Overview](#1-overview)
- [2. Features](#2-features)
- [3. Technology Stack](#3-technology-stack)
- [4. Installation](#4-installation)
- [5. Quick Start](#5-quick-start)
- [6. API Summary](#6-api-summary)
- [7. Implementation Notes](#7-implementation-notes)
- [8. Versioning](#8-versioning)
- [9. Contributing](#9-contributing)
- [10. License](#10-license)

## 1. Overview
This library defines a single adapter interface, `IdpAdapter`, and a set of DTOs to model common identity workflows:
- User authentication (login/refresh/logout)
- Token introspection and user info retrieval
- User management (create user, change/reset password)
- MFA (challenge and verification)
- Session and role management

By targeting this interface, you can swap identity providers without changing the application logic.

## 2. Features
- Unified interface for common IdP operations
- Reactive return types using Reactor `Mono` for async/non-blocking flows
- DTOs tailored to typical OAuth2/OIDC and MFA scenarios
- Spring-friendly responses via `ResponseEntity`

## 3. Technology Stack
- Java 25 (default, Java 21+ compatible)
- Reactor Core / Spring WebFlux (`Mono`, `ResponseEntity`)
- Lombok for DTO boilerplate reduction
- Maven build

## 4. Installation
Add the dependency to your Maven project. Replace the version as appropriate.

```xml
<dependency>
    <groupId>org.fireflyframework</groupId>
    <artifactId>fireflyframework-idp</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

If you are using Gradle (Kotlin DSL):
```kotlin
implementation("org.fireflyframework:fireflyframework-idp:1.0.0-SNAPSHOT")
```

Note: This module provides only the abstraction (interface + DTOs). You will need an implementation module for your chosen provider.

## 5. Quick Start
1) Implement the `IdpAdapter` for your target IdP (e.g., Keycloak):

```java
public class KeycloakIdpAdapter implements IdpAdapter {
    @Override
    public Mono<ResponseEntity<TokenResponse>> login(LoginRequest request) {
        // Call Keycloak token endpoint and map response
        return Mono.empty();
    }

    @Override
    public Mono<ResponseEntity<TokenResponse>> refresh(RefreshRequest request) { return Mono.empty(); }

    @Override
    public void logout(String accessToken) { /* call provider logout/revoke */ }

    @Override
    public Mono<ResponseEntity<IntrospectionResponse>> introspect(String accessToken) { return Mono.empty(); }

    @Override
    public Mono<ResponseEntity<UserInfoResponse>> getUserInfo(String accessToken) { return Mono.empty(); }

    @Override
    public Mono<ResponseEntity<CreateUserResponse>> createUser(CreateUserRequest request) { return Mono.empty(); }

    @Override
    public void changePassword(ChangePasswordRequest request) { }

    @Override
    public void resetPassword(String username) { }

    @Override
    public Mono<ResponseEntity<MfaChallengeResponse>> mfaChallenge(String username) { return Mono.empty(); }

    @Override
    public void mfaVerify(MfaVerifyRequest request) { }

    @Override
    public void revokeRefreshToken(String refreshToken) { }

    @Override
    public Mono<ResponseEntity<List<SessionInfo>>> listSessions(String userId) { return Mono.empty(); }

    @Override
    public void revokeSession(String sessionId) { }

    @Override
    public Mono<ResponseEntity<List<String>>> getRoles(String userId) { return Mono.empty(); }
}
```

2) Inject and use your implementation wherever needed:
```java
Mono<ResponseEntity<TokenResponse>> result = idpAdapter.login(
    LoginRequest.builder()
        .username("alice")
        .password("password123")
        .clientId("my-client")
        .scope("openid profile email")
        .build()
);
```

## 6. API Summary
The main entry point is `org.fireflyframework.idp.adapter.IdpAdapter`.

Basic operations:
- `Mono<ResponseEntity<TokenResponse>> login(LoginRequest request)`
- `Mono<ResponseEntity<TokenResponse>> refresh(RefreshRequest request)`
- `void logout(String accessToken)`
- `Mono<ResponseEntity<IntrospectionResponse>> introspect(String accessToken)`
- `Mono<ResponseEntity<UserInfoResponse>> getUserInfo(String accessToken)`
- `Mono<ResponseEntity<CreateUserResponse>> createUser(CreateUserRequest request)`

Advanced operations:
- `void changePassword(ChangePasswordRequest request)`
- `void resetPassword(String username)`
- `Mono<ResponseEntity<MfaChallengeResponse>> mfaChallenge(String username)`
- `void mfaVerify(MfaVerifyRequest request)`
- `void revokeRefreshToken(String refreshToken)`
- `Mono<ResponseEntity<List<SessionInfo>>> listSessions(String userId)`
- `void revokeSession(String sessionId)`
- `Mono<ResponseEntity<List<String>>> getRoles(String userId)`

DTOs are located under `org.fireflyframework.idp.dtos` and cover requests and responses for the above methods.

## 7. Implementation Notes
- Error Handling: Return appropriate HTTP status codes in `ResponseEntity` (e.g., 401 for invalid credentials, 400 for invalid requests, 500 for unexpected provider errors). Wrap provider errors consistently.
- Security: Never log secrets (passwords, client secrets, tokens). Consider encrypting at rest and masking logs.
- Threading: Since the API is reactive, avoid blocking calls. If the provider SDK is blocking, delegate to bounded elastic schedulers or use non-blocking HTTP clients.
- Portability: Keep provider-specific objects within your implementation; expose only the DTOs defined in this library.

## 8. Versioning
This project follows semantic versioning as much as possible during its evolution. Breaking changes in interfaces or DTOs will result in a major version increment.

## 9. Contributing
Contributions are welcome. Please open an issue to discuss proposed changes before submitting a PR. Ensure code compiles and includes documentation updates when necessary.

## 10. License
This project is licensed under the terms of the LICENSE file included in the repository.

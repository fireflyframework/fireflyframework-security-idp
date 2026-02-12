# Firefly Framework - Identity Provider (IDP)

[![CI](https://github.com/fireflyframework/fireflyframework-idp/actions/workflows/ci.yml/badge.svg)](https://github.com/fireflyframework/fireflyframework-idp/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://openjdk.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)

> Identity provider abstraction layer defining contracts for user management, authentication, and token operations.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)

## Overview

Firefly Framework IDP defines the port (adapter) contracts for identity provider integration across the Firefly ecosystem. It provides the `IdpAdapter` interface with comprehensive user management, authentication, token, and role management operations that concrete providers must implement.

The module includes DTOs for all IDP operations including user creation, login, logout, token refresh, MFA challenges, role management, scope management, and session introspection. It is designed as a pure contract library with no implementation, serving as the dependency for all IDP provider modules.

Provider implementations (AWS Cognito, Keycloak, Internal DB) are published as separate modules that implement the `IdpAdapter` interface.

## Features

- `IdpAdapter` interface defining all identity provider operations
- User management DTOs: create, update, change password
- Authentication DTOs: login, logout, token response, refresh
- MFA support: challenge response, verification
- Role and scope management contracts
- Session introspection and token validation
- Provider-agnostic design enabling swappable IDP backends

## Requirements

- Java 21+
- Spring Boot 3.x
- Maven 3.9+

## Installation

```xml
<dependency>
    <groupId>org.fireflyframework</groupId>
    <artifactId>fireflyframework-idp</artifactId>
    <version>26.02.04</version>
</dependency>
```

## Quick Start

```java
import org.fireflyframework.idp.adapter.IdpAdapter;
import org.fireflyframework.idp.dtos.*;

@Service
public class AuthService {

    private final IdpAdapter idpAdapter;

    public Mono<TokenResponse> login(LoginRequest request) {
        return idpAdapter.login(request);
    }

    public Mono<CreateUserResponse> register(CreateUserRequest request) {
        return idpAdapter.createUser(request);
    }
}
```

## Configuration

No configuration is required for the contracts module. Configuration is provided by the specific IDP provider implementation.

## Documentation

No additional documentation available for this project.

## Contributing

Contributions are welcome. Please read the [CONTRIBUTING.md](CONTRIBUTING.md) guide for details on our code of conduct, development process, and how to submit pull requests.

## License

Copyright 2024-2026 Firefly Software Solutions Inc.

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.

/*
 * Copyright 2024-2026 Firefly Software Foundation
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

package org.fireflyframework.idp.port;

import org.fireflyframework.idp.dtos.ChangePasswordRequest;
import org.fireflyframework.idp.dtos.CreateUserRequest;
import org.fireflyframework.idp.dtos.CreateUserResponse;
import org.fireflyframework.idp.dtos.UpdateUserRequest;
import org.fireflyframework.idp.dtos.UpdateUserResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * Segregated IdP capability port for user administration (CRUD + password operations).
 */
public interface UserAdminPort {

    default Mono<ResponseEntity<CreateUserResponse>> createUser(CreateUserRequest request) {
        return Mono.error(new UnsupportedOperationException("createUser is not supported by this IdP provider"));
    }

    default Mono<Void> changePassword(ChangePasswordRequest request) {
        return Mono.error(new UnsupportedOperationException("changePassword is not supported by this IdP provider"));
    }

    default Mono<Void> resetPassword(String username) {
        return Mono.error(new UnsupportedOperationException("resetPassword is not supported by this IdP provider"));
    }

    default Mono<Void> deleteUser(String userId) {
        return Mono.error(new UnsupportedOperationException("deleteUser is not supported by this IdP provider"));
    }

    default Mono<ResponseEntity<UpdateUserResponse>> updateUser(UpdateUserRequest request) {
        return Mono.error(new UnsupportedOperationException("updateUser is not supported by this IdP provider"));
    }
}

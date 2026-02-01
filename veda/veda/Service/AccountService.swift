//
//  AccountService.swift
//  veda
//
//  Created by Воскобович Максим on 1.02.26.
//

import Foundation

protocol AccountServiceProtocol {
    func register(data: AuthRequest.Register) async throws -> AuthResponse.Auth
    func checkEmail(email: String) async throws -> AuthResponse.Availability
    func getProfile(token: String) async throws -> AuthResponse.Account
}

final class AccountService {
    private let config: NetworkConfig
    private let session: URLSession
    
    init(config: NetworkConfig, session: URLSession = .shared) {
        self.config = config
        self.session = session
    }
}

//
//  vedaApp.swift
//  veda
//
//  Created by Евгений Петрукович on 1/23/26.
//

import SwiftUI

@main
struct vedaApp: App {
    @StateObject private var session = SessionManager.shared
    
    var body: some Scene {
        WindowGroup {
            if session.isLoggedIn {
                ContentView()
            } else {
                AuthView()
            }
        }
    }
}

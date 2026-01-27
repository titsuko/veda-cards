//
//  ContentView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/23/26.
//

import SwiftUI

enum SelectedTab: Hashable {
    case main, collection, settings
}

struct ContentView: View {
    @State private var selectedTab: SelectedTab = .main
    
    var body: some View {
        content
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            .safeAreaInset(edge: .bottom) { AppTabBar(selectedTab: $selectedTab) }
            .ignoresSafeArea()
    }
    
    @ViewBuilder
    private var content: some View {
        switch selectedTab {
        case .main:
            MainView()
        case .collection:
            CollectionsView()
        case .settings:
            SettingsView()
        }
    }
}

#Preview {
    ContentView()
}

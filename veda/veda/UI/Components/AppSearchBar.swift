//
//  AppSearchBar.swift
//  veda
//
//  Created by Евгений Петрукович on 1/28/26.
//

import SwiftUI

struct AppSearchBar: View {
    let title: String
    let height: CGFloat
    
    @FocusState private var isFocused: Bool
    @Binding var searchText: String
    
    init(title: String, height: CGFloat = 40, searchText: Binding<String>) {
        self.title = title
        self.height = height
        self._searchText = searchText
    }
    
    var body: some View {
        ZStack {
            background
            ZStack {
                placeholder
                textField
            }
        }
        .animation(.spring(duration: 0.2), value: searchText)
        .animation(.spring(duration: 0.2), value: isFocused)
    }
    
    @ViewBuilder
    private var background: some View {
        RoundedRectangle(cornerRadius: 30)
            .fill(.gray.opacity(0.2))
            .frame(height: height)
    }
    
    @ViewBuilder
    private var placeholder: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .font(.system(size: 18))
            
            if searchText.isEmpty {
                Text(title)
                    .font(.system(size: 18))
            }
            Spacer()
        }
        .foregroundStyle(.secondary)
        .padding(.leading, 10)
    }
    
    @ViewBuilder
    private var textField: some View {
        HStack {
            TextField("", text: $searchText)
                .focused($isFocused)
                .padding(.leading, 40)
            
            if !searchText.isEmpty {
                Button(action: { searchText = "" }) {
                    Image(systemName: "xmark.circle.fill")
                        .font(.system(size: 18))
                }
                .foregroundStyle(.secondary)
                .padding(.trailing)
            }
        }
    }
}

#Preview {
    ContentView()
}

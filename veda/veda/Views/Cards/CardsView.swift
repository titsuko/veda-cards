//
//  CardView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/28/26.
//

import SwiftUI

struct CardsView: View {
    let title: String
    let description: String
    let quantity: Int
    let color: Color
    let image: String
    
    @Environment(\.colorScheme) private var colorScheme: ColorScheme
    
    var body: some View {
        background
            .overlay(
                VStack {
                    imageCard
                    Spacer()
                    text
                    quantityCard
                }
            )
    }
    
    @ViewBuilder
    private var background: some View {
        RoundedRectangle(cornerRadius: 20)
            .fill(colorScheme == .dark ? color.gradient.opacity(0.05) : color.gradient.opacity(0.20))
            .stroke(color.gradient, lineWidth: 1)
            .frame(height: 200)
    }
    
    @ViewBuilder
    private var imageCard: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 8)
                .fill(colorScheme == .dark ? color.gradient.opacity(0.15) : color.gradient.opacity(0.35))
                .stroke(color, lineWidth: 0.3)
                .frame(width: 38, height: 38)
            
            Image(systemName: image)
                .foregroundStyle(color)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
        .padding(15)
    }
    
    @ViewBuilder
    private var text: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(title)
                .font(.system(size: 12, weight: .semibold))
                .multilineTextAlignment(.leading)
                .lineLimit(2)
            
            Text(description)
                .font(.system(size: 11, weight: .semibold))
                .foregroundStyle(.secondary)
                .multilineTextAlignment(.leading)
                .lineLimit(3)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
        .padding(.leading, 15)
    }
    
    @ViewBuilder
    private var quantityCard: some View {
        HStack {
            ZStack {
                RoundedRectangle(cornerRadius: 30)
                    .fill(colorScheme == .dark ? color.gradient.opacity(0.1) : color.gradient.opacity(0.3))
                    .frame(width: 110, height: 30)
                
                Text("\(quantity) карточек")
                    .font(.system(size: 13, weight: .bold))
                    .foregroundStyle(color.gradient)
            }
            Spacer()
        }
        .padding(.bottom, 10)
        .padding(.leading, 10)
    }
}

#Preview {
    ContentView()
}

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
    
    var body: some View {
        background
            .overlay(
                VStack {
                    imageCard
                    text
                    quantityCard
                }
            )
    }
    
    @ViewBuilder
    private var background: some View {
        RoundedRectangle(cornerRadius: 12)
            .fill(.gray.opacity(0.15))
            .stroke(color.gradient, lineWidth: 2)
            .frame(width: 150, height: 200)
    }
    
    @ViewBuilder
    private var imageCard: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 8)
                .fill(color.gradient.opacity(0.15))
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
            
            Text(description)
                .font(.system(size: 11, weight: .semibold))
                .foregroundStyle(.secondary)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
        .padding(.leading, 15)
    }
    
    @ViewBuilder
    private var quantityCard: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 30)
                .fill(color.gradient.opacity(0.1))
                .frame(width: 110, height: 30)
            
            Text("\(quantity) карточек")
                .font(.system(size: 13, weight: .bold))
                .foregroundStyle(color.gradient)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
        .padding(.leading, 15)
    }
}

#Preview {
    HStack(spacing: 20) {
        VStack(spacing: 20) {
            CardsView(
                title: "Праздники",
                description: "Традиционные торжества и их смысл",
                quantity: 24,
                color: .orange,
                image: "sun.min"
            )
            CardsView(
                title: "Символы",
                description: "Знаки и их значения в культуре",
                quantity: 32,
                color: .green,
                image: "wand.and.sparkles"
            )
        }
        VStack(spacing: 20) {
            CardsView(
                title: "Персонажи",
                description: "Мифологические образы и герои",
                quantity: 18,
                color: .purple,
                image: "person"
            )
            CardsView(
                title: "Обряды",
                description: "Ритуалы и священные практики",
                quantity: 12,
                color: .red,
                image: "flame"
            )
        }
    }
}

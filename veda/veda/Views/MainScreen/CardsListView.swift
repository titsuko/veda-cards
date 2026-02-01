//
//  CardsListView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/28/26.
//

import SwiftUI

struct CardsListView: View {
    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.dismiss) private var dismiss
    
    @State private var searchText: String = ""
    @State private var searchFocused = false
    @State private var showSearchBar: Bool = false
    
    let category: CardCategory
    
    var headerHeight: CGFloat = 130
    
    var body: some View {
        NavigationStack {
            ScrollView(showsIndicators: false) {
                cardsListView
                    .padding(.top, headerHeight)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .overlay(alignment: .top) { header }
            .ignoresSafeArea()
            .background(.mainBackground)
        }
        .navigationBarHidden(true)
        .animation(.spring(duration: 0.35), value: showSearchBar)
        .onTapGesture { hideKeyboard() }
    }
    
    @ViewBuilder
    private var header: some View {
        VStack(spacing: 0) {
            ZStack(alignment: .bottom) {
                Rectangle()
                    .fill(.header.opacity(0.6))
                    .background(TransparentBlur())
                    .frame(height: headerHeight)
                
                ZStack {
                    if showSearchBar {
                        searchHeader
                            .transition(.blurAndFade)
                    } else {
                        normalHeader
                            .transition(.blurAndFade)
                    }
                }
                .padding(.horizontal)
                .padding(.bottom, 10)
            }
            Divider()
        }
    }
    
    @ViewBuilder
    private var normalHeader: some View {
        ZStack {
            HStack {
                AppButton(systemImage: "chevron.backward", width: 25, height: 35, style: .clear) {
                    dismiss()
                }
                Spacer()
                VStack(alignment: .center, spacing: 5) {
                    Text(category.title)
                        .font(.system(size: 18, weight: .semibold))
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
                    
                    Text("\(category.quantity) карточек")
                        .font(.system(size: 12, weight: .bold))
                        .foregroundStyle(category.color.gradient)
                        .padding(.horizontal, 10)
                        .padding(.vertical, 3)
                        .overlay(
                            Capsule()
                                .fill(colorScheme == .dark ? category.color.gradient.opacity(0.1) : category.color.gradient.opacity(0.3))
                        )
                        .glassEffect()
                }
                Spacer()
                AppButton(systemImage: "magnifyingglass", width: 25, height: 35, style: .clear) {
                    showSearchBar = true
                    searchFocused = true
                }
            }
        }
    }
    
    @ViewBuilder
    private var searchHeader: some View {
        HStack(spacing: 0) {
            AppSearchBar(title: "Поиск по карточкам", height: 45, isFocused: $searchFocused, searchText: $searchText)
            Spacer()
            AppButton(systemImage: "xmark", width: 25, height: 35, style: .clear) {
                showSearchBar = false
                searchFocused = false
                searchText = ""
            }
        }
    }
    
    @ViewBuilder
    private var cardsListView: some View {
        VStack(spacing: 0) {
            ForEach(Array(category.items.enumerated()), id: \.offset) { index, item in
                VStack(spacing: 0) {
                    if index == 0 { Divider() }
                    NavigationLink(destination: CardReview()) {
                        cardsView(item: item)
                    }
                    .buttonStyle(ButtonPressStyle())
                    
                    Divider()
                }
                .background(
                    LinearGradient(
                        gradient: Gradient(stops: [
                            .init(color: item.rarity.color.opacity(0.22), location: 0.0),
                            .init(color: item.rarity.color.opacity(0.10), location: 0.45),
                            .init(color: item.rarity.color.opacity(0.04), location: 0.75),
                            .init(color: .clear, location: 1.0),
                        ]),
                        startPoint: .leading,
                        endPoint: .trailing
                    )
                )
            }
        }
    }
    
    @ViewBuilder
    private func cardsView(item: CardItem) -> some View {
        HStack(alignment: .center, spacing: 10) {
            ZStack {
                RoundedRectangle(cornerRadius: 6)
                    .fill(
                        LinearGradient(
                            colors: [
                                item.rarity.color.opacity(0.85),
                                item.rarity.color.opacity(0.45)
                            ],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 65, height: 75)
                
                Image(systemName: category.image)
                    .font(.system(size: 16))
                    .foregroundStyle(.white)
                    .bold()
            }
            VStack(alignment: .leading, spacing: 5) {
                Text(item.title)
                    .font(.system(size: 16, weight: .semibold))
                    .multilineTextAlignment(.leading)
                    .lineLimit(1)
                
                Text("Праздники")
                    .font(.system(size: 12, weight: .semibold))
                    .foregroundStyle(item.rarity.color)
                    .multilineTextAlignment(.leading)
                    .lineLimit(1)
                
                Text(item.description)
                    .font(.system(size: 12, weight: .semibold))
                    .foregroundStyle(.secondary)
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
            }
            Spacer()
            
            Text(item.rarity.name)
                .font(.system(size: 14))
                .foregroundStyle(item.rarity.color.gradient)
                .padding(.horizontal, 10)
                .padding(.vertical, 5)
                .overlay(
                    Capsule()
                        .fill(item.rarity.color.opacity(0.2))
                        .stroke(item.rarity.color.opacity(0.6), lineWidth: 1)
                )
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding(.vertical, 10)
        .padding(.horizontal, 10)
        
    }
}

#Preview {
    CardsListView(
        category: CardCategory(
            title: "Праздники",
            description: "Традиции и обряды солнцестояния с кострами, гаданиями и древними ",
            quantity: 5,
            color: .orange,
            image: "sun.max",
            items: [
                CardItem(
                    title: "Цветок папоротника",
                    description: "Мифический цветок, который, по легенде, расцветает в ночь на Ивана Купалу и приносит удачу",
                    rarity: .epic
                ),
                CardItem(
                    title: "Ночь Ивана Купалы",
                    description: "Праздник летнего солнцестояния с кострами, гаданиями и древними обрядами",
                    rarity: .rare
                ),
                CardItem(
                    title: "Коляда",
                    description: "Зимний обрядовый праздник, связанный с рождением нового солнца",
                    rarity: .common
                ),
                CardItem(
                    title: "Масленица",
                    description: "Проводы зимы с блинами, гуляниями и сожжением чучела",
                    rarity: .rare
                ),
                CardItem(
                    title: "Дзяды",
                    description: "Древний обряд поминовения предков, сохранившийся в белорусской традиции",
                    rarity: .legendary
                )
            ]
        )
    )
}

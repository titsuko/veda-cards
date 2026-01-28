//
//  HomeView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/27/26.
//

import SwiftUI

struct MainView: View {
    @State private var searchText: String = ""
    @State private var selectedCategory: CardCategory?
    
    private let columns = [
        GridItem(.flexible(), spacing: 10),
        GridItem(.flexible(), spacing: 10)
    ]
    
    let categories: [CardCategory] = [
        CardCategory(
            title: "Праздники",
            description: "Традиционные торжества и их смысл",
            quantity: 4,
            color: .orange,
            image: "sun.min",
            items: [
                CardItem(title: "Коляда", description: "Зимний праздник"),
                CardItem(title: "Купала", description: "Летний обряд")
            ]
        ),
        CardCategory(
            title: "Символы",
            description: "Знаки и их значения",
            quantity: 3,
            color: .green,
            image: "wand.and.sparkles",
            items: [
                CardItem(title: "Солнце", description: "Источник жизни"),
                CardItem(title: "Древо", description: "Связь миров")
            ]
        ),
        CardCategory(
            title: "Обряды",
            description: "Ритуалы и священные практики",
            quantity: 2,
            color: .red,
            image: "flame",
            items: [
                CardItem(title: "Солнце", description: "Источник жизни"),
                CardItem(title: "Древо", description: "Связь миров")
            ]
        ),
        CardCategory(
            title: "Персонажи",
            description: "Мифологические образы и герои",
            quantity: 3,
            color: .purple,
            image: "person",
            items: [
                CardItem(title: "Солнце", description: "Источник жизни"),
                CardItem(title: "Древо", description: "Связь миров")
            ]
        )
    ]
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                ScrollView(showsIndicators: true) {
                    cardsView
                }
                .safeAreaInset(edge: .top) {header}
                .edgesIgnoringSafeArea(.top)
            }
            .background(.nightBlue)
        }
        .onTapGesture { hideKeyboard() }
        .sheet(item: $selectedCategory) { category in
            NavigationStack {
                CardsListView(category: category)
            }
        }
    }
    
    @ViewBuilder
    private var header: some View {
        VStack(spacing: 0) {
            ZStack(alignment: .bottom) {
                Rectangle()
                    .fill(.midnightBlue.opacity(0.5))
                    .background(TransparentBlur())
                    .frame(height: 120)
                
                AppSearchBar(title: "Поиск карточек", searchText: $searchText)
                    .padding(.horizontal, 10)
                    .padding(.bottom, 10)
            }
            Divider()
        }
    }
    
    @ViewBuilder
    private var cardsView: some View {
        LazyVGrid(columns: columns, spacing: 10) {
            ForEach(categories) { category in
                Button(action: {selectedCategory = category}) {
                    CardsView(
                        title: category.title,
                        description: category.description,
                        quantity: category.quantity,
                        color: category.color,
                        image: category.image
                    )
                }
                .foregroundStyle(.primary)
            }
        }
        .padding(.horizontal, 10)
        .padding(.top, 5)
    }
}

#Preview {
    ContentView()
}

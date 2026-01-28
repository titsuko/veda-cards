//
//  CardCategory.swift
//  veda
//
//  Created by Евгений Петрукович on 1/28/26.
//

import SwiftUI

struct CardCategory: Identifiable {
    let id = UUID()
    let title: String
    let description: String
    let quantity: Int
    let color: Color
    let image: String
    let items: [CardItem]
}

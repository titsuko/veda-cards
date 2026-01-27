//
//  SignInView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/24/26.
//

import SwiftUI

struct SignInView: View {
    @State var email: String = ""
    @State var password: String = ""
    @State var isAgreed: Bool = false
    
    var body: some View {
        VStack(spacing: 20) {
            logo
            VStack(spacing: 30) {
                textField
                Spacer()
                button
            }
            .padding(.horizontal, 20)
        }
        .contentShape(Rectangle())
        .gesture(
            DragGesture(minimumDistance: 0)
                .onChanged { _ in
                    hideKeyboard()
                }
        )
    }
    
    @ViewBuilder
    private var logo: some View {
        ZStack {
            Image("logo")
                .resizable()
                .frame(width: 230, height: 230)
        }
    }
    
    @ViewBuilder
    private var textField: some View {
        VStack(spacing: 30) {
            AppTextField(field: "Email", secure: false, text: $email)
            AppTextField(field: "Пароль", secure: true, text: $password)
        }
    }
    
    @ViewBuilder
    private var button: some View {
        AppButtonFill(title: "Войти", action: {})
    }
}

#Preview {
    SignInView()
}


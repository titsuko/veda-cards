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
    }
    
    @ViewBuilder
    private var logo: some View {
        ZStack {
            Image("logoCircle")
                .resizable()
                .frame(width: 260, height: 225)
            
            VStack(spacing: -10) {
                Image("logoEar")
                    .resizable()
                    .frame(width: 55, height: 80)
                    .rotationEffect(.degrees(60))
                
                Image("logoEar")
                    .resizable()
                    .frame(width: 45, height: 80)
                    .scaleEffect(x: 1, y: 1)
                    .rotationEffect(.degrees(260))
            }
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


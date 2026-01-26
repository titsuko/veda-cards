//
//  SignUpView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/23/26.
//

import SwiftUI

struct SignUpView: View {
    @State var name: String = ""
    @State var email: String = ""
    @State var password: String = ""
    @State var isAgreed: Bool = false
    
    var body: some View {
        VStack(spacing: 80) {
            logo
            VStack(spacing: 30) {
                textField
                userAgreement
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
            AppTextField(field: "Ваше имя", secure: false, text: $name)
            AppTextField(field: "Email", secure: false, text: $email)
            AppTextField(field: "Пароль", secure: true, text: $password)
        }
    }
    
    @ViewBuilder
    private var userAgreement: some View {
        HStack(spacing: 15) {
            Button(action: { isAgreed.toggle() }) {
                ZStack {
                    RoundedRectangle(cornerRadius: 6)
                        .stroke(.gold)
                        .frame(width: 20, height: 20)
                    
                    if isAgreed {
                        Image(systemName: "checkmark")
                            .font(.system(size: 14))
                            .foregroundColor(.primary)
                    }
                }
                Text("Пользовательское соглашение")
                    .foregroundStyle(.goldText)
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.vertical, 15)
    }
    
    @ViewBuilder
    private var button: some View {
        AppButtonFill(title: "Создать аккаунт", action: {})
    }
}

#Preview {
    SignUpView()
}

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
    
    private var agreementText: AttributedString {
        var part1 = AttributedString("Регистрируясь, вы подтверждаете, что ознакомились и соглашаетесь с условиями ")
        part1.foregroundColor = .secondary
        
        var part2 = AttributedString("пользовательского соглашения.")
        part2.foregroundColor = .goldText
        part2.underlineStyle = .single
        
        return part1 + part2
    }
    
    var body: some View {
        VStack(spacing: 40) {
            logo
            VStack(spacing: 30) {
                textField
                userAgreement
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
            AppTextField(field: "Ваше имя", secure: false, text: $name)
            AppTextField(field: "Email", secure: false, text: $email)
            AppTextField(field: "Пароль", secure: true, text: $password)
        }
    }
    
    @ViewBuilder
    private var userAgreement: some View {
        Button(action: { isAgreed.toggle() }) {
            HStack(alignment: .center) {
                ZStack {
                    RoundedRectangle(cornerRadius: 16)
                        .fill(isAgreed ? .gold : .clear)
                        .stroke(.gold, lineWidth: 1)
                        .frame(width: 22, height: 22)
                    
                    if isAgreed {
                        Image(systemName: "checkmark")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.white)
                    }
                }
                Spacer()
                
                Text(agreementText)
                    .font(.system(size: 14))
                    .multilineTextAlignment(.leading)
                
                Spacer()
            }
        }
    }
    
    @ViewBuilder
    private var button: some View {
        AppButtonFill(title: "Создать аккаунт", action: {})
    }
}

#Preview {
    SignUpView()
}

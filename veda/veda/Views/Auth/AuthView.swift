//
//  AuthView.swift
//  veda
//
//  Created by Евгений Петрукович on 1/23/26.
//

import SwiftUI

struct AuthView: View {
    @State private var signInTapped: Bool = false
    @State private var signUpTapped: Bool = false
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 10) {
                Spacer()
                logo
                description
                Spacer()
                buttons
            }
            .navigationDestination(isPresented: $signInTapped) { SignInView() }
            .navigationDestination(isPresented: $signUpTapped) { SignUpView() }
        }
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
    private var description: some View {
        VStack(spacing: 10) {
            Text("VEDA.cards")
                .font(.custom("CrimsonText-Regular", size: 48))
                .foregroundStyle(.goldText)
            
            Text("СОБИРАЙ ЗНАНИЯ")
                .font(.custom("CrimsonText-Regular", size: 24))
                .foregroundStyle(.grayText)
            
            Text("Энциклопедия знаний  в  формате коллекционных карточек")
                .font(.custom("CrimsonText-Regular", size: 16))
                .multilineTextAlignment(.center)
                .foregroundStyle(.grayText)
        }
    }
    
    @ViewBuilder
    private var buttons: some View {
        VStack(spacing: 20) {
            AppButtonFill(title: "Войти", action: { signInTapped = true })
            AppButtonClear(title: "Создать аккаунт", action: { signUpTapped = true })
        }
        .padding(.horizontal, 20)
    }
}

#Preview {
    AuthView()
}

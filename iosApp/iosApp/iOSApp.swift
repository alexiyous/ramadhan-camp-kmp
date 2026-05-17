import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        AlarmeePlatformConfiguration.initialize()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .preferredColorScheme(.dark)
                .onOpenURL { url in
                    DeepLinkHandler.shared.handleDeepLink(uri: url.absoluteString)
                }
        }
    }
}
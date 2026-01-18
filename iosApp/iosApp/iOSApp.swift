import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        KoinHelper.shared.doInit()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

注意
----
IntelliJ IDEA の New Project の Wizard の Kotlin Multiplatform のサポートは Kotlin Plugin の 1.9.10-release-459 まで
となっています。 KMP 向けに Project 設定ツールとして [Amper](https://github.com/JetBrains/amper) が用意されています。
今後こちらを利用するのが望ましいです。

旧来のプラグインを使いたいときは Android Studio を使ってください。

template-gradle-kmp-library
------------------------------

* IntelliJ IDEA にて Koltin + Library (JVM+JS+Native) を指定して新規にプロジェクト作成
* buildSrc に共用処理を追加
* GitHub Packages にアップロードワークフロー追加
* sample code を追加

参考
---
* [IntelliJ IDEA overview](https://www.jetbrains.com/help/idea/discover-intellij-idea.html)
* [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
* [GitHub Actions](https://docs.github.com/ja/actions)
* [GitHub Packages](https://docs.github.com/ja/packages)
* [Publish a multiplatform library](https://kotlinlang.org/docs/mpp-publish-lib.html)
* [Kotlin Plugin for IDEA](https://plugins.jetbrains.com/plugin/6954-kotlin)
* [Amper](https://github.com/JetBrains/amper)

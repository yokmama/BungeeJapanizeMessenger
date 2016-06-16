BungeeJapanizeMessenger for Slack
========================

このバージョンはオリジナルの BungeeJapanMessenger にSlackとの連携機能を追加したバージョンです。
オリジナルは v1.1.0 をベースにしています。

BungeeJapanMessenger　に関する詳細はこちらのURLを参考にしてください。

https://github.com/ucchyocean/BungeeJapanizeMessenger


# インストール

PluginフォルダにできたBungeeJapanMessengerの下にconfig.ymlというファイルができています。
そこに以下の設定があるはずです。
これをそれぞれ変更してください。

*SlackのIncomoingWebHookのURLを設定
webhook: https://hooks.slack.com/services/

*SlackのOutogingWebHookのPortを設定
listen-port: 8080

*SlackのOutgoingWebHookのトークンを設定
token: <Please set the outgoing token>

SlackのOutgoing,Incomingの設定は割愛します。

# 使い方

Minecraftで喋った会話は自動的にSlackのチャンネルにいきます。

Slackでの会話はキーワードを設定しているのであれば　
キーワード: メッセージ
で会話ができます。

例
mc: こんにちは

それから、プレイヤーに対してメッセージを投げる場合は

mc: .yokmama こんにちは

と . をつけると個別にメッセージを投げることができます。
## 前提条件
- node.js,npmがインストール済み

https://github.com/swagger-api/swagger-node

## Swaggerのインストール
`npm install -g swagger`

## プロジェクト作成
`swagger project create XXXX`

> ex. swagger project create sample-rest-service

Frameworkを聞かれたら `express` を選択します
```
? Framework?
  connect
> express       expressが汎用的らしい
  hapi
  restify
  sails
```

以下はswaggerプロジェクトディレクトリに移動してから実施してください

## Swaggerエディタを開く
`swagger project edit`

## 起動
`swagger project start`

# sample-rest-service
JavaEE7 JAX-RSを利用したサンプルRESTサービスです。

## サービス仕様
API詳細は `sample-rest-service-webapi-doc` を確認してください。

## 動作確認環境
| Platform | Version | Edition |
| -------- | ------- | ------- |
| IBM WebSphere Application Server | 9.0.0.9 | traditional |
| IBM WebSphere Application Server | 18.0.0.3 | Liberty |
| IBM Java | 1.8.0_181 | |
| DB2 | 11.1.4.4 | |

## 利用技術
| FW/OSS | Version |
| ------ | ------- |
| JavaEE | 7 |
| Jackson | 2.9.8 |
| logback | 1.2.3 |

## 環境情報
1. DB2
    - DB名: MYDB
    - スキーマ名: MYSCHEMA

DB構築手順は [sample-db](https://github.com/akihisa-matsubara/sample-db) を参照

## 環境構築
1. WASの任意のEditionとIBM Java8をインストールします  
   ※IBM Java8はWAS関連製品(ex. WAS traditional, WAS liberty, etc...)と一緒にインストールできますがここでは割愛します  
   ※以降はWAS Liberty Profile(以降WLP)前提の手順で記載しています  
   https://developer.ibm.com/wasdev/downloads/#asset/runtimes-wlp-javaee7
   
1. WDT (IBM WebSphere Application Server Developer Tools) をEclipseにインストールします  
   https://developer.ibm.com/wasdev/docs/websphere-developer-tools-releases/
   
1. `tools/was-liberty/*` をWLP解凍先に配布（template以外）

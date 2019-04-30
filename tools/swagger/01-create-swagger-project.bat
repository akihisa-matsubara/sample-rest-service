@echo off

cd ../../sample-rest-service-webapi-doc

move /Y sample-rest-service ../tools/swagger

echo ">express を選択してください"

swagger project create sample-rest-service

rem エラー終了（アクセス拒否）するので手動で移動してあげる必要があるかも
move /Y ../tools/swagger/sample-rest-service ./

pause

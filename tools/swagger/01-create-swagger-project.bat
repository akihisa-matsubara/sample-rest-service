@echo off

cd ../../sample-rest-service-webapi-doc

move /Y sample-rest-service ../tools/swagger

echo ">express ��I�����Ă�������"

swagger project create sample-rest-service

rem �G���[�I���i�A�N�Z�X���ہj����̂Ŏ蓮�ňړ����Ă�����K�v�����邩��
move /Y ../tools/swagger/sample-rest-service ./

pause

import socket,sys,java,os
# �ȉ��̃I�v�V������t����Jython V2.1�Ƃ��Ď��s���Ă��������BV2.7�ɂ͑Ή����Ă��܂���B
# -conntype soap -usejython21 true -f
# �{�c�[�����s��͔O�̂���WAS���ċN�����Ă�������
# �ċN�����Ȃ��ꍇ�A�ߋ���Connection���c���ăe�X�g�ڑ������܂������Ȃ��\��������܂�
# JCA�̏�Ԃ��m�F(��Ԃ̊m�F������)�����Connection���c���Ă��邩�m�F�\�ł�

# -----------------------------------------------------------------------
# ������
# -----------------------------------------------------------------------
# �T�[�o�[���
nodeName = AdminControl.getNode()
cellName = AdminControl.getCell()
server = AdminControl.queryNames('node=' + nodeName + ',type=Server,*')
serverName = AdminControl.getAttribute(server, "name")
scope = AdminConfig.getid("/Node:" + nodeName + "/Server:" + serverName + "/")

print "�m�[�h��[" + nodeName + "]�A�T�[�o�[��[" + serverName + "]���Z�b�g�A�b�v���܂��B"

# �s��؂�
lineSep = java.lang.System.getProperty('line.separator')

# JVM�q�[�v�T�C�Y
jvmxms = 512
jvmxmx = 1024

# DB2 Java�N���C�A���g���C�u�����C���X�g�[���f�B���N�g��
db2dir = ""
db2dirList = []
db2dirList.append("C:/IBM/SQLLIB")
db2dirList.append("C:/Program Files/IBM/SQLLIB")
for dir in db2dirList:
    if (os.path.exists(dir)):
        db2dir = dir
        break
    else:
        continue
    #endIf
#next
if (db2dir == ""):
    print "Error : DB2 Java�N���C�A���g�̃p�X���Q�Ƃł��܂���B�X�N���v�g���I�����܂��B"
    os._exit(1)
else:
    print "�f�B���N�g��[" + db2dir + "]��DB2 Java�N���C�A���g�p�X�Ƃ��ēK�p���܂��B"
    pass
#endIf

# JPA���C�u�����i�[�f�B���N�g�����
jpalibdir = ""
jpadirList = []
jpadirList.append("C:/IBM/WebSphere/AppServer/plugins")
jpadirList.append("C:/Program Files/IBM/WebSphere/AppServer/plugins")
for dir in jpadirList:
    if (os.path.exists(dir)):
        jpalibdir = dir
        break
    else:
        continue
    #endIf
#next
if (jpalibdir == ""):
    print "Error : WebSphere�̃v���O�C���p�X���Q�Ƃł��܂���A�X�N���u�g�𒆎~���܂��B"
    os._exit(1)
else:
    print "�f�B���N�g��[" + jpalibdir + "]��WebSphere�v���O�C���p�X�Ƃ��ēK�p���܂��B"
    pass
#endIf


# -----------------------------------------------------------------------
# fuction getInput
# -----------------------------------------------------------------------
def getInput (prompt, defaultValue):
    print prompt
    retValue = raw_input()
    if (retValue == ""):
        retValue = defaultValue
    #endIf
    return retValue
#endDef

# -----------------------------------------------------------------------
# fuction wsadminToList
# -----------------------------------------------------------------------
def wsadminToList(inStr):
    outList=[]
    if (len(inStr)>0 and inStr[0]=='[' and inStr[-1]==']'):
        inStr = inStr[1:-1]
        tmpList = inStr.split(" ")
    else:
        tmpList = inStr.split("\n")
    for item in tmpList:
        item = item.rstrip();
        if (len(item)>0):
            outList.append(item)
    return outList
#endDef

# -----------------------------------------------------------------------
# fuction createAuthDataEntry
# -----------------------------------------------------------------------
def createAuthDataEntry(dbName, dbuser, dbpassword):
    authDataName = "auth-" + dbName
    # -----------------------------------------------------------------------
    # J2C�F�؃f�[�^�̑��݃`�F�b�N���s���A���ɑ��݂���ꍇ�͍폜����
    # -----------------------------------------------------------------------
    parms = ["-alias", nodeName + "/" + authDataName]
    try:
        authDataEntry = AdminTask.getAuthDataEntry(parms)
        print "�쐬�ς݂�JAAS - J2C �F�؃f�[�^[" + authDataName + "]���폜���܂�"
        AdminTask.deleteAuthDataEntry(parms)
    except:
        parms = []
    #endTry

    # -----------------------------------------------------------------------
    # J2C�F�؃f�[�^���쐬����
    # -----------------------------------------------------------------------
    try:
        parms = ["-alias", authDataName, "-user", dbuser, "-password", dbpassword, "-description"]
        AdminTask.createAuthDataEntry(parms)
        print "JAAS - J2C �F�؃f�[�^[" + authDataName + "]���쐬���܂�"
        AdminConfig.save()
    except:
        print "Error : JAAS - J2C �F�؃f�[�^[" + authDataName + "]�̍쐬�Ɏ��s���܂���"
    #endTry
#endDef

# -----------------------------------------------------------------------
# fuction createJDBCProvider
# -----------------------------------------------------------------------
def createJDBCProvider(dbName):
    jdbcProviderName = "jdbc-provider-" + dbName
    jdbc = AdminConfig.getid("/Node:" + nodeName + "/Server:" + serverName + "/JDBCProvider:" + jdbcProviderName + "/")
    if (len(jdbc) == 0):
        print "JDBC�v���o�C�_�[[" + jdbcProviderName + "]���쐬���܂�"
        classpath = ["${DB2_JCC_DRIVER_PATH}/db2jcc.jar", "${UNIVERSAL_JDBC_DRIVER_PATH}/db2jcc_license_cu.jar", "${DB2_JCC_DRIVER_PATH}/db2jcc_license_cisuz.jar", "${PUREQUERY_PATH}/pdq.jar", "${PUREQUERY_PATH}/pdqmgmt.jar"]
        nativepath = ["${DB2_JCC_DRIVER_NATIVEPATH}"]

        parms = ["-scope", "Node=" + nodeName + ",Server=" + serverName]
        parms.extend(["-databaseType", "DB2", "-providerType", "DB2 Universal JDBC Driver Provider"])
        parms.extend(["-implementationType", "XA �f�[�^�E�\�[�X", "-implementationClassName", "com.ibm.db2.jcc.DB2XADataSource"])
        parms.extend(["-name", jdbcProviderName, "-description", ""])
        parms.extend(["-classpath", classpath, "-nativePath", nativepath])
        AdminTask.createJDBCProvider(parms)
        AdminConfig.save()

        # -----------------------------------------------------------------------
        # createVariableSubstitutionEntry
        # -----------------------------------------------------------------------
        varSubstitutions = AdminConfig.list("VariableSubstitutionEntry", scope).split(lineSep)

        for varSubst in varSubstitutions:
            getVarName = AdminConfig.showAttribute(varSubst, "symbolicName")
            if (getVarName == "DB2_JCC_DRIVER_PATH"):
                print "WebSphere�ϐ� DB2_JCC_DRIVER_PATH���폜���܂�"
                AdminConfig.remove(varSubst)
                AdminConfig.save()
            #endIf
        #endFor

        print "WebSphere�ϐ� DB2_JCC_DRIVER_PATH���쐬���܂�"
        varName = "(cells/" + cellName + "/nodes/" + nodeName + "/servers/" + serverName + "|variables.xml#VariableMap_1)"
        db2lib = db2dir + "/java"
        varValue = [["symbolicName", "DB2_JCC_DRIVER_PATH"], ["description", ""], ["value", db2lib]]
        AdminConfig.create('VariableSubstitutionEntry', varName, varValue)
        AdminConfig.save()

    else:
        print "JDBC�v���o�C�_�[[" + jdbcProviderName + "]�͍쐬�ςł�"
    #endIf
    print AdminTask.listJDBCProviders()
#endDef

# -----------------------------------------------------------------------
# fuction createDataSource
# -----------------------------------------------------------------------
def createDataSource(dbName, schemaName, dbServer, dbport):
    authDataName = "auth-" + dbName
    jdbcProviderName = "jdbc-provider-" + dbName
    datasourceName = dbName
    jndiName = "jdbc/" + dbName
    templateId = "DB2 Universal JDBC Driver DataSource (templates/system|jdbc-resource-provider-templates.xml#DataSource_DB2_UNI_1)"

    # -----------------------------------------------------------------------
    # �f�[�^�\�[�X�̑��݃`�F�b�N���s���A���ɑ��݂���ꍇ�͍폜����
    # -----------------------------------------------------------------------
    datasource = AdminConfig.getid("/Node:" + nodeName + "/Server:" + serverName + "/JDBCProvider:" + jdbcProviderName + "/DataSource:" + datasourceName + "/")
    if (len(datasource) != 0):
        datasourceId = datasource[datasource.find("(")+1:datasource.find(")")]
        AdminTask.deleteDatasource("DB2 Universal JDBC Driver Provider (XA)(" + datasourceId + ")")
        print "�����̃f�[�^�\�[�X[" + datasourceId + "]���폜���܂���"
    else:
        pass
    #endIf

    # -----------------------------------------------------------------------
    # �f�[�^�\�[�X���쐬����
    # -----------------------------------------------------------------------
    print "�f�[�^�\�[�X[" + jndiName + "]���쐬���܂�"
    resProplist = [[["name", "databaseName"], ["type", "java.lang.String"], ["value" , dbName]]]
    resProplist.append([["name", "driverType"], ["type", "java.lang.Integer"], ["value", 4]])
    resProplist.append([["name", "serverName"], ["type", "java.lang.String"], ["value", dbServer]])
    resProplist.append([["name", "portNumber"], ["type", "java.lang.Integer"], ["value", dbport]])
    resProplist.append([["name", "currentSchema"], ["type", "java.lang.String"], ["value", schemaName.upper()], ["required", "false"], ["supportsDynamicUpdates", "false"]])

    attributes = [["jndiName", jndiName], ["authDataAlias", nodeName + "/" + authDataName], ["authMechanismPreference", "BASIC_PASSWORD"]]
    attributes.append(["propertySet", [["resourceProperties", resProplist]]])
    attributes.append(["xaRecoveryAuthAlias", nodeName + "/" + authDataName])
    attributes.append(["mapping", [["mappingConfigAlias", "DefaultPrincipalMapping"], ["authDataAlias", nodeName + "/" + authDataName]]])
    attributes.append(["connectionPool", [["connectionTimeout", "10"], ["maxConnections", "20"], ["minConnections", "5"], ["purgePolicy", "EntirePool"]]])
    AdminJDBC.createDataSourceUsingTemplate(nodeName, serverName, jdbcProviderName, templateId, datasourceName, attributes)
    AdminConfig.save()
#endDef

# -----------------------------------------------------------------------
# fuction createSessionTable
# -----------------------------------------------------------------------
def createSessionTable():
    sessionTableName = "SessionTable"
    wc = AdminCOnfig.list('WebContainer', scope)
    wc_att_validationExpression =["validationExpression" ,""]
    wc_att_name = ["name", sessionTableName]
    wc_att_value = ["value", schemaName.upper() + ".SESSI0NS"]
    wc_att_required = ["required", "false"]
    wcAttrs = [wc_att_validationExpression, wc_att_name, wc_att_value, wc_att_required]
    varSubstitutions = AdminConfig.list("Property", wc).split(lineSep)

    if varSubstitutions == ['']:
        print "session table���쐬���܂�"
        AdminConfig.create('Property', wc, wcAttrs)
    else:
        for varSubst in varSubstitutions:
            getVarName = AdminConfig.showAttribute(varsubst, "name")
            if (getVarName != sessionTableName):
                print "session table���쐬���܂�"
                AdminConfig.create('Property', wc, wcAttrs)
            else:
                print "session table�͍쐬�ςł�"
            #endlf
        #endFor
    #endlf
    AdminCOnfig.save()
#endDef

# -----------------------------------------------------------------------
# createDataSourceForSessionDB
# -----------------------------------------------------------------------
def createDataSourceForSessionDB(dbName, dbport, datasourceName):
    authDataName = "auth-" + dbName
    jndiName = "jdbc/" + datasourceName
    jdbcProviderName = "jdbc-provider-" + dbName
    datasourceSessions = AdminConfig.getid("/Node:" + nodeName + "/Server:" + serverName + "/JDBCProvider:" + jdbcProviderName + "/DataSource:" + datasourceName + "/")
    if (len(datasourceSessions) == 0):
        print "�f�[�^�\�[�X" + datasourceName + "���쐬���܂�"
        dataStoreHelperClassName = "com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper"
        alias = nodeName + "/" + authDataName
        otherAttributesList = [['containerManagedPersistence', 'true'], ['xaRecoveryAuthAlias', alias], ['componentManagedAuthenticationAlias', alias]]
        resourceAttributesList = [['serverName', 'localhost'], ['driverType', 4], ['portNumber', dbport]]
        ds_id = AdminJDBC.createDataSourceAtScope(scope, jdbcProviderName, datasourceName, jndiName, dataStoreHelperClassName, dbName, otherAttributesList, resourceAttributesList)

        print "�R���e�i�[�Ǘ��F�ؕʖ���ݒ肵�܂�"
        map_att_1 = ['authDataAlias', alias]
        map_att_2 = ['mappingConfigAlias', '']
        mapAttrs = [map_att_1, map_att_2]

        AdminConfig.create('MappingModule', ds_id, mapAttrs)
        AdminConfig.save()
        print "�f�[�^�\�[�X" + datasourceName + "���쐬���܂���"

    else:
        print "�f�[�^�\�[�X" + datasourceName + "�͍쐬�ςł�"
    #endlf
#endDef

# -----------------------------------------------------------------------
# fuctlon setSesslonPerslstence
# -----------------------------------------------------------------------
def setSessionPersistence(dbuser, dbpassword, datasourceName):
    jndiName = "jdbc/" + datasourceName
    print "SessionDatabasePersistence��ݒ肵�܂�"
    AdminConfig.modify(AdminConfig.list('SessionManager', scope), '[[sessionPersistenceMode "DATABASE"]]')

    sdbp = AdminConfig.list('SessionDatabasePersistence', scope)
    sdbp_att_password = ['password', dbpassword]
    sdbp_att_userId = ['userId', dbuser]
    sdbp_att_tableSpaceName = ['tableSpaceName', ""]
    sdbp_att_jndiName = ['datasourceJNDIName', jndiName]
    sdbp_att_db2RowSize = ['db2RowSize', "ROW_SIZE_4KB"]
    sdbpAttrs = [sdbp_att_password, sdbp_att_userId, sdbp_att_tableSpaceName, sdbp_att_jndiName, sdbp_att_db2RowSize]

    AdminConfig.modify(sdbp, SdbpAttrs)
    AdminConfig.save()
#endDef

# -----------------------------------------------------------------------
# fuction createJvmCustomProperty
# -----------------------------------------------------------------------
def createJvmCustomProperty(node, server, propName, value):
    serverl = AdminConfig.getid("/Node:" + node + "/Server:" + server + "/")
    jvm = AdminConfig.list("JavaVirtualMachine", serverl)

    props = AdminConfig.showAttribute(jvm, "systemProperties")
    props = wsadminToList(props)
    propertyName = ""
    for prop in props:
        name = AdminConfig.showAttribute(prop, "name")
        name = name.rstrip()
        if (name == propName):
            propertyName = name
            break
        #endIf
    #endFor

    if (propertyName == ""):
        print "JVM�J�X�^���v���p�e�B[" + propName + "]��ݒ肵�܂�"
        parms = [["name", propName], ["value", value], ["required", "false"]]
        attrList = AdminConfig.create("Property" ,jvm, parms)
        AdminConfig.save()
    else:
        print "JVM�J�X�^���v���p�e�B[" + propName + "]�͐ݒ�ςł�"
    #endlf
#endDef

# -----------------------------------------------------------------------
# fuction createDefaultJTADataSourceJNDIName
# -----------------------------------------------------------------------
def createDefaultJTADataSourceJNDIName():
    serverl = AdminConfig.getid("/Node:" + nodeName + "/Server:" + serverName)
    servl = AdminConfig.list('JavaPersistenceAPIService', serverl)
    if (len(servl) == 0):
        print "�V�K�]defaultJTADataSourceJNDIName"
        #varName = "(cells/" + cellName + "/nodes/" + nodeName + "/servers/" + serverName + "|server.xml#ApplicationServer_%)"
        varName = AdminConfig.list('ApplicationServer', serverl)
        varValue = [["defaultJTADataSourceJNDIName", ""], ["defaultNonJTADataSourceJNDIName", ""], ["defaultPersistenceProvider", "org.apache.openjpa.persistence.PersistenceProviderImpl"], ["enable", "true"], ["properties", ""]]
        AdminConfig.create('JavaPersistenceAPIService', varName, varValue)
    else:
        print "�X�V - defaultJTADataSourceJNDIName"
        print AdminConfig.showall(servl)
        AdminConfig.modify(servl, [['defaultPersistenceProvider', 'org.apache.openjpa.persistence.PersistenceProviderlmpl']])
    #endIf
    AdminConfig.save()
#endDef

# -----------------------------------------------------------------------
# fuctlon updateHeapSize
# -----------------------------------------------------------------------
def updateHeapSize():
    serverl = AdminConfig.getid("/Node:" + nodeName + "/Server:" + serverName)
    servl = AdminConfig.list('JavaProcessDef', serverl)
    serv2 = AdminConfig.list('JavaVirtualMachine', servl)

    AdminConfig.modify(serv2, [['initialHeapSize', jvmxms]])
    AdminConfig.modify(serv2, [['maximumHeapSize', jvmxmx]])
    AdminConfig.save()
    print "Heap�ݒ�ς�"
#endDef

# -----------------------------------------------------------------------
# Main ����
# -----------------------------------------------------------------------
# JAAS - J2C �F�؃f�[�^�쐬(dbName, dbuser, dbpassword)
createAuthDataEntry("mydb", "db2admin", "db2admin")

# JDBC�v���o�C�_�[�쐬
createJDBCProvider("mydb")

# �f�[�^�\�[�X�쐬(dbName, schemaName, dbServer, dbport)
createDataSource("mydb", "MYSCHEMA", "localhost", "50000")

# createDefaultJTADataSourceJNDIName()
updateHeapSize()

# javaagent�ݒ�
# setJVMProperties()

# �Z�b�V�����o�[�V�X�^���X�쐬
# createSessionTable()
# createDataSourceForSessionDB("mydb", "50000", "Sessions")
# setSessionPersistence("db2admin", "db2admin", "Sessions")

# JVM�J�X�^���v���o�e�B�ݒ�
# createJvmCustomProperty(nodeName, serverName, "java.net.preferIPv4Stack", "true")
# createJvmCustomProperty(nodeName, serverName, "db2.jcc.ccsid943Mapping", "2")
#!/usr/bin/groovy
def call(String projectFile, configuration = 'Release', platofrm = 'AnyCPU', args = '') {
    if (isUnix()) {
        sh "\"${tool name: 'xbuild', type: 'msbuild'}\" \"${projectFile}\" /p:Configuration=${configuration} /p:Platform=${platofrm} /t:Rebuild ${args}"
    } else {
        bat "\"${tool name: 'msbuild', type: 'msbuild'}\" \"${projectFile}\" /p:Configuration=${configuration} /p:Platform=${platofrm} /t:Rebuild ${args}"
    }
}

def call(HashMap argsMap, String projectFile) {
    argsMap.configuration = argsMap.configuration ?: 'Release'
    argsMap.platform = argsMap.platform ?: 'AnyCPU'
    argsMap.args = argsMap.args ?: ''
    call(projectFile, argsMap.configuration, argsMap.platform, argsMap.args)
}

def call(HashMap argsMap) {
    argsMap.configuration = argsMap.configuration ?: 'Release'
    argsMap.platform = argsMap.platform ?: 'AnyCPU'
    argsMap.args = argsMap.args ?: ''
    call(argsMap.projectFile, argsMap.configuration, argsMap.platform, argsMap.args)
}
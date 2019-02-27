#!/usr/bin/groovy
def call(String projectFile, configuration = 'Release', platofrm = 'AnyCPU', args = '') {
    if (isUnix()) {
        sh "\"${tool name: 'xbuild', type: 'msbuild'}\" \"${projectFile}\" /p:Configuration=${configuration} /p:Platform=${platofrm} /t:Rebuild ${args}"
    } else {
        bat "\"${tool name: 'msbuild', type: 'msbuild'}\" \"${projectFile}\" /p:Configuration=${configuration} /p:Platform=${platofrm} /t:Rebuild ${args}"
    }
}

/**
 * params: projectFile ( required ), configuration: 'Release', platform: 'AnyCPU', args: ''
 */
def call(HashMap argsMap, String projectFile) {
    argsMap.configuration = argsMap.get("configuration", "Release")
    argsMap.platform = argsMap.get("platform", "AnyCPU")
    argsMap.args = argsMap.get("args", "")
    call(projectFile, argsMap.configuration, argsMap.platform, argsMap.args)
}

/**
 * params: projectFile ( required ), configuration: 'Release', platform: 'AnyCPU', args: ''
 */
def call(HashMap argsMap) {
    argsMap.configuration = argsMap.configuration ?: 'Release'
    argsMap.platform = argsMap.platform ?: 'AnyCPU'
    argsMap.args = argsMap.args ?: ''
    call(argsMap.projectFile, argsMap.configuration, argsMap.platform, argsMap.args)
}
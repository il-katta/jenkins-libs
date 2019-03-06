#!/usr/bin/groovy
def call(String projectFile, configuration = 'Release', platofrm = 'AnyCPU', target='Rebuild', args = '') {
    if (isUnix()) {
        sh "\"${tool name: 'xbuild', type: 'msbuild'}\" \"${projectFile}\" /p:Configuration=${configuration} /p:Platform=${platofrm} /t:${target} ${args}"
    } else {
        bat "\"${tool name: 'msbuild', type: 'msbuild'}\" \"${projectFile}\" /p:Configuration=${configuration} /p:Platform=${platofrm} /t:${target} ${args}"
    }
}

/**
 * params: projectFile ( required ), configuration: 'Release', platform: 'AnyCPU', target: 'Rebuild', args: ''
 */
def call(HashMap argsMap, String projectFile) {
    argsMap.configuration = argsMap.get("configuration", "Release")
    argsMap.platform = argsMap.get("platform", "AnyCPU")
    argsMap.args = argsMap.get("args", "")
    argsMap.target = argsMap.get("target", "Rebuild")
    call(projectFile, argsMap.configuration, argsMap.platform, argsMap.target, argsMap.args)
}

/**
 * params: projectFile ( required ), configuration: 'Release', platform: 'AnyCPU', target: 'Rebuild', args: ''
 */
def call(HashMap argsMap) {
    argsMap.configuration = argsMap.get("configuration", "Release")
    argsMap.platform = argsMap.get("platform", "AnyCPU")
    argsMap.args = argsMap.get("args", "")
    argsMap.target = argsMap.get("target", "Rebuild")
    call(argsMap.projectFile, argsMap.configuration, argsMap.platform, argsMap.target, argsMap.args)
}
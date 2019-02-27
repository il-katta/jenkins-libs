#!/usr/bin/groovy

@groovy.transform.Field
def deployer_url = "https://repository.loopback.it/deployer/deployer.exe"

def download_exe() {
    // download
    if (isUnix()) {
        ret = sh(
            returnStatus: true, 
            script: """
                curl -o deployer.exe \"${deployer_url}\"
            """
        )
    } else {
        ret = powershell(
            returnStatus: true, 
            script: """
                \$sourceNugetExe = \"${deployer_url}\"
                \$targetNugetExe = \"deployer.exe\"
                Invoke-WebRequest \$sourceNugetExe -OutFile \$targetNugetExe
                Set-Alias nuget \$targetNugetExe -Scope Global -Verbose
                exit 0
            """
        )
    }
}


def run(String args) {
    if (isUnix()) {
        sh """
            mono deployer.exe ${args}
        """
    } else {
        powershell """
            .\\deployer.exe ${args}
        """
    }
}

/**
 * params: credentialsId, name, directory
 * optionals: s3Endpoint, pyFile, s3Region
 */
def upload(HashMap argsMap) {
    download_exe()

    withCredentials([usernamePassword( \
            credentialsId: argsMap.credentialsId, \
            usernameVariable: 'S3_KEY', \
            passwordVariable: 'S3_SECRET' \
    )]) {
        String args = "upload "
        args += "--name \"${argsMap.name}\" "
        args += "--directory \"${argsMap.directory}\" "
        args += "--s3-secret \"${env.S3_SECRET}\" "
        args += "--s3-key \"${env.S3_KEY}\" "
        
        if (argsMap.containsKey("s3Endpoint")) {
            args += " --s3-endpoint \"${argsMap.s3Endpoint}\" "
        }

        if (argsMap.containsKey("pyFile")) {
            args += " --py-file \"${argsMap.pyFile}\" "
        }

        if (argsMap.containsKey("s3Region")) {
            args += " --s3-region \"${argsMap.s3Region}\" "
        }
        
        run(args)
    }
}
#!/usr/bin/groovy

@groovy.transform.Field
def nuget_url = "https://dist.nuget.org/win-x86-commandline/latest/nuget.exe"

def call() {
    def ret = 0
    if (isUnix()) {
        ret = sh(
            returnStatus: true, 
            script: """
                curl -o \"${workspace}/nuget.exe\" \"${nuget_url}\"
            """
        )
    } else {
        ret = powershell(
            returnStatus: true, 
            script: """
                $sourceNugetExe = \"${nuget_url}\"
                $targetNugetExe = \"nuget.exe\"
                Invoke-WebRequest $sourceNugetExe -OutFile $targetNugetExe
                Set-Alias nuget $targetNugetExe -Scope Global -Verbose
                exit 0
            """
        )
    }
    if (ret == 0) {
        
    } else {

    }

}
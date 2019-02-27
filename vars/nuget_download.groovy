#!/usr/bin/groovy

def call() {
    def ret = 0
    if (isUnix()) {
        ret = sh(
            returnStatus: true, 
            script: '''
                curl -o nuget.exe "https://dist.nuget.org/win-x86-commandline/latest/nuget.exe"
            '''
        )
    } else {
        ret = powershell(
            returnStatus: true, 
            script: '''
                $sourceNugetExe = "https://dist.nuget.org/win-x86-commandline/latest/nuget.exe"
                $targetNugetExe = "nuget.exe"
                Invoke-WebRequest $sourceNugetExe -OutFile $targetNugetExe
                Set-Alias nuget $targetNugetExe -Scope Global -Verbose
                exit 0
            '''
        )
    }
    if (ret == 0) {
        
    } else {

    }

}
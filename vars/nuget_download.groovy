#!/usr/bin/groovy

def download_unix() {
    def ret = sh(
        returnStatus: true, 
        script: '''
            curl -o nuget.exe "https://dist.nuget.org/win-x86-commandline/latest/nuget.exe"
        '''
    )
    if (ret == 0) {
        print "nuget_download returns code ${ret}"
    } else {
        error "nuget_download returns error code ${ret}"
        throw new Exception("nuget_download returns error code ${ret}")
    }
}

def download_windows() {
    def ret = powershell(
        returnStatus: true, 
        script: '''
            $sourceNugetExe = "https://dist.nuget.org/win-x86-commandline/latest/nuget.exe"
            $targetNugetExe = "nuget.exe"
            Invoke-WebRequest $sourceNugetExe -OutFile $targetNugetExe
            Set-Alias nuget $targetNugetExe -Scope Global -Verbose
            exit 0
        '''
    )
    if (ret == 0) {
        print "nuget_download returns code ${ret}"
    } else {
        error "nuget_download returns error code ${ret}"
        throw new Exception("nuget_download returns error code ${ret}")
    }
}


def call() {
    print "starting download nuget.exe ..."
    if (isUnix()) {
        download_unix()
    } else {
        download_windows()
    }
}


def call(String slnFile) {
    if ((new File("nuget.exe")).exists()) {
        print "starting download nuget.exe ..."
        if (isUnix()) {
            download_unix()
        } else {
            download_windows()
        }
    }

    print "starting restoring packages for ${slnFile} ..."
    if (isUnix()) {
        sh "mono nuget.exe restore \"${slnFile}\""
    } else {
        powershell "nuget restore \"${slnFile}\""
    }
}
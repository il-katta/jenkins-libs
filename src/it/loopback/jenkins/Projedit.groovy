package it.loopback.jenkins

class Projedit implements Serializable {
    def script
    Projedit(script) {this.script = script}
    String projedit(project_type, filepath)
    {
        def scriptCode = script.libraryResource("it/loopback/jenkins/projedit.py")
        script.writeFile(file: "projedit.py", text: scriptCode)
        if (script.isUnix()) {
            return script.sh (
                script: "python3 projedit.py \"${project_type}\" \"${filepath}\"",
                returnStdout: true
            ).trim()
        } else {
            return script.powershell (
                script: "python3 projedit.py \"${project_type}\" \"${filepath}\"",
                returnStdout: true
            ).trim()
        }
    }
}
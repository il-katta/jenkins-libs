package it.loopback.jenkins

class Projedit implements Serializable {
    def script
    Projedit(script) {this.script = script}
    String projedit(project_type, filepath)
    {
        return this.increase_version(project_type, filepath)
    }

    String increase_version(project_type, filepath)
    {
        def script_path = this._restore_script_file();
        this.execute("python3 \"${script_path}\" \"${project_type}\" \"${filepath}\"")
    }

    String set_version(project_type, filepath, version) 
    {
        def script_path = this._restore_script_file();
        this.execute("python3 \"${script_path}\" \"${project_type}\" \"${filepath}\" \"${version}\"")
    }

    String _restore_script_file() {
        def scriptCode = script.libraryResource("it/loopback/jenkins/projedit.py")
        this.script.writeFile(file: "projedit.py", text: scriptCode)
        return "projedit.py"
    }

    void execute(String cmd) {
        if (this.script.isUnix()) {
            return script.sh (
                script: cmd,
                returnStdout: true
            ).trim()
        } else {
            return this.script.powershell (
                script: cmd,
                returnStdout: true
            ).trim()
        }
    }
}
#!/usr/bin/groovy
import groovy.json.*
import java.text.SimpleDateFormat
import java.util.*

String get_date_string() {
    def dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm")
    return dateFormat.format(new Date())
}

LinkedHashMap get_data() {
    def data = [:]
    data.BUILD = env.BUILD_TAG
    data.COMMIT = env.GIT_COMMIT
    data.BANCH = env.GIT_BRANCH
    data.DATE = get_date_string()
    return data
}

def delete_if_exists (String fileName) {
    File file = null
    if (isUnix()) {
        file = new File("${workspace}", fileName);
       
    } else {
        file = new File("${workspace}\\${fileName}")
    }
    if (file.exists()) {
        //file.delete()
        print "${file}: exists"
    }
    if (isUnix()) {
        sh "rm -f \"${file}\""
    } else {
        bat "del \"${file}\" /s /f /q"
    }
}

def to_json_file(String fileName) {
    def data = get_data()
    def jsonStr = groovy.json.JsonOutput.toJson(data)
    delete_if_exists(fileName)
    writeFile file: fileName, text: jsonStr, encoding: 'UTF-8'

    // non funziona ( java.lang.UnsupportedOperationException: must specify $class with an implementation of interface net.sf.json.JSON )
    // writeJSON file: fileName, json: data, pretty: true 

    /* 
    // non funziona ( java.io.NotSerializableException: groovy.json.JsonBuilder )

    def builder = new JsonBuilder(data)

    json_str = builder.toPrettyString()

    print json_str

    writeFile file: fileName, text: json_str, encoding: 'UTF-8'
    */
}

def to_yaml_file(String fileName) {
    def data = get_data()

    print data.toString()
    
    delete_if_exists(fileName)
    
    writeYaml file: fileName, data: data
}

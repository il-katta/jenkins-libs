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
    data.BRANCH_NAME = env.BRANCH_NAME
    data.BUILD = env.BUILD_TAG
    data.COMMIT = env.GIT_COMMIT
    data.BANCH = env.GIT_BRANCH
    data.DATE = get_date_string()
    return data
}

def to_json_file(String fileName) {
    def data = get_data()

    writeJSON file: fileName, json: data, pretty: true 
    /* 
    // non funziona ( java.io.NotSerializableException: groovy.json.JsonBuilder )

    def builder = new JsonBuilder(data)

    json_str = builder.toPrettyString()

    print json_str

    writeFile file: fileName, text: json_str
    */
}

def to_yaml_file(String fileName) {
    def data = get_data()

    print data.toString()

    writeYaml file: fileName, data: data
}
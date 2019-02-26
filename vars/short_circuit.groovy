#!/usr/bin/groovy

def call(Closure body) {
    node {
        script {
            def changeLogSets = currentBuild.changeSets
            if (changeLogSets != null && changeLogSets.size() > 0) {
                def entries = changeLogSets[changeLogSets.size()-1].items
                def entry = entries[entries.length-1]
                if (entry.author.getDisplayName().contains('jenkins')) {
                    print "skip build ( author: \"${entry.author.getDisplayName()}\" , commit: \"${entry.msg}\", id: ${entry.commitId})"
                } else {
                    print "last commit - author: \"${entry.author}\" , commit: \"${entry.msg}\", id: ${entry.commitId}"
                    body()
                }
            }
        }
    }
}
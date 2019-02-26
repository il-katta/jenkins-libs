#!/usr/bin/groovy

boolean call(String username) {
    def changeLogSets = currentBuild.changeSets
    if (changeLogSets != null && changeLogSets.size() > 0) {
        def entries = changeLogSets[changeLogSets.size()-1].items
        def entry = entries[entries.length-1]
        if (entry.author.getDisplayName().contains(username)) {
            print "skip build ( author: \"${entry.author.getDisplayName()}\" , commit: \"${entry.msg}\", id: ${entry.commitId})"
            return true
        } else {
            print "last commit - author: \"${entry.author}\" , commit: \"${entry.msg}\", id: ${entry.commitId}"
            return false
        }
    }
}

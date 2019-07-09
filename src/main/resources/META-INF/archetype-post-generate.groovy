import groovy.io.FileType

import java.nio.file.Paths

def projectPath = Paths.get(request.outputDirectory, request.artifactId)
def configurationModule = "spring-cloud-starter-stream-${request.properties.appType}-${request.artifactId}"
def configuration = projectPath.resolve(configurationModule)

def create(File parent, String[] files) {
    if (files.length == 0) return parent
    File head = new File(parent, files.head())
    String[] tail = files.tail()
    create(head, tail)
}

def dir = configuration.toFile()
def deleteTargets = []
def deleteTarget = null
dir.eachFileRecurse(FileType.ANY) { file ->
    if (file.isDirectory()) {
        if (file.name.contains('-')) {
            deleteTarget = file
            def dirs = []
            file.name.split('-').each {
                dirs << it
            }
            file = create(file.parentFile, dirs as String[])
            if (file.mkdirs()) {
                dir = file
            }
        } else if (deleteTarget != null && file.parentFile == deleteTarget) {
            dir = new File(dir, file.name)
            if (!dir.exists()) {
                dir.mkdir()
                deleteTargets << deleteTarget
            }
        } else {
            dir = file
        }
    } else if (file.name.endsWith('.java')) {
        def artifactId = (request.artifactId.contains('-')) ?
                request.artifactId.split('-').inject('') { result, word ->
                    result += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()
                    result
                } : request.artifactId.substring(0, 1).toUpperCase() + request.artifactId.substring(1).toLowerCase()
        def appType = request.properties.appType.substring(0, 1).toUpperCase() + request.properties.appType.substring(1).toLowerCase()
        def renameTo = file.name.replace(request.artifactId, artifactId).replace(request.properties.appType, appType)
        file.renameTo(new File(dir, renameTo))
    }
}

if (!deleteTargets.isEmpty()) {
    (deleteTargets as File[]).each {
        it.deleteDir()
    }
}

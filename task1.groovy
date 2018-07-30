@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.RESTClient
import org.apache.http.entity.*
import hudson.model.*

void main() {
    def nexus_server = 'http://192.168.1.4/repository/'
    def username = "admin"
    def password = "admin123"

    // Get arguments
    CliBuilder cli = new CliBuilder(
            usage: 'groovy task1.groovy [-h] -a {artifact} -c {command} -r {repo}')
    cli.h(args: 1, argName: 'help', 'Show usage information',required: false)
    cli.a(args: 1, argName: 'artifact_name', 'ARTIFACT_NAME which created after building', required: true)
    cli.c(args: 1, argName: 'command', 'COMMAND operation for upload/download a file', required: true)
    cli.r(args: 1, argName: 'repo', 'Name of the repository', required: true)
    def options = cli.parse(args)
    if (!options) {
        println('There are not arguments!')
        return
    }
    if (options.h) {
        cli.usage()
        return
    }
    ////////////////////////////////////////
    if (options.a == 'NONE'){
        options.a = System.getenv('ARTIFACT_NAME')
        println(options.a)
    }

    version = options.a.split('.tar.gz')[0].split('-')[-1]
    groupid = artifactid = options.a.split("-${version}.tar.gz")[0]
    def rest = new RESTClient(nexus_server)
    rest.auth.basic "${username}", "${password}"

    if (options.c == 'push') {
        rest.encoder.'application/x-gzip' = this.&encodeZipFile
        resp = rest.put(
                path: "${options.r}/${groupid}/${artifactid}/${version}/${options.a}",
                body: new File(options.a),
                requestContentType: 'application/x-gzip'
        )
        assert resp.status == 201
    } else if(options.c == 'pull'){
        resp = rest.get(
                path: "${nexus_server}${options.r}/${groupid}/${artifactid}/${version}/${options.a}"

        )
        assert resp.status == 200
        new File("./${options.a}") << resp.data
    } else {
        Println ('Command is not correct!')
    }
}


def encodeZipFile( Object data ) throws UnsupportedEncodingException {
        def entity = new FileEntity( (File) data, "application/x-gzip" );
        entity.setContentType( "application/x-gzip" );
        return entity
}

main()

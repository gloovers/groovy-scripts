@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonSlurper

nexus = "http://192.168.1.4/service/rest/v1/script/"
def username = "admin"
def password = "admin123"

def rest = new RESTClient(nexus)
//rest.auth.basic(username, password)
rest.setHeaders([Authorization: "Basic YWRtaW46YWRtaW4xMjM="])

json_cp = """{  "name": "CP",
                "content": "security.securitySystem.changePassword('admin','admin456')",
                "type": "groovy"}"""

def resp = rest.post( path: nexus,
        contentType: JSON,
        body: new JsonSlurper().parseText(json_cp),
        headers: [Accept: 'application/json']
)

def resp_run = rest.post( path: "${nexus}CP/run",
        contentType: "text/plain",
        headers: [Accept: 'application/json']
)


json_user = """{  "name": "CU",
                "content": "security.addUser('Ivan', 'Ivan', 'Ivan', 'ivan@ivan.ivan', true, 'ivan', ['nx-admin'])",
                "type": "groovy"}"""

def resp_u = rest.post( path: nexus,
        contentType: JSON,
        body: new JsonSlurper().parseText(json_user),
        headers: [Accept: 'application/json']
)

def resp_u_run = rest.post( path: "${nexus}CU/run",
        contentType: "text/plain",
        headers: [Accept: 'application/json']
)


json_repo = """{  "name": "CR",
                "content": "repository.createMavenHosted('new-repo')",
                "type": "groovy"}"""

def resp_r = rest.post( path: nexus,
        contentType: JSON,
        body: new JsonSlurper().parseText(json_repo),
        headers: [Accept: 'application/json']
)

def resp_r_run = rest.post( path: "${nexus}CR/run",
        contentType: "text/plain",
        headers: [Accept: 'application/json']
)
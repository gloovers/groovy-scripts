import groovy.json.JsonSlurper
//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.apache.http.entity.*
import hudson.model.*

def url = 'http://192.168.1.4/service/rest/v1/script'
def username = "admin"
def password = "admin123"

def rest = new RESTClient(url)
rest.auth.basic "${username}", "${password}"

    resp = rest.get(
            path: "/",
            //contentType: "JSON",
            //headers:[
            //        "Accept": "application/json"
            //]
    )
    assert resp.status == 200

def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText(resp.)
//println(object)













//parsed_args = new JsonSlurper().parseText(args)
//security.setAnonymousAccess(Boolean.valueOf(parsed_args.anonymous_access))
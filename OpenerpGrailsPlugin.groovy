import grails.spring.*
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator

class OpenerpGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.6 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def loadAfter = ['services']
    
    def license = "APACHE"
    def organization = [ name: "openfellas", url: "http://openfellas.com/" ]
    def developers = [ [ name: "Kristofer Eriksson", email: "kristofer.erikssonopenfellas.com" ] ]
    def issueManagement = [ system: "JIRA", url: "http://jira.codehaus.org/browse/GRAILSPLUGINS" ]
    def scm = [ url: "https://github.com/keriksson/grails-openerp" ]
    
    def author = "Kristofer Eriksson"
    def authorEmail = "kristofer.eriksson@openfellas.com"
    def title = "OpenERP"
    
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/openerp"
    
    def description = '''\\
The Grails OpenERP plugin simplifies integration with the platform over the XML-RPC interface, which allows external access to the complete set of data and functionality.
'''

    
    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
        openerpClientSessionAutoProxyCreator(AnnotationAwareAspectJAutoProxyCreator) {
            proxyTargetClass = true
        }

        openerpClientSessionAspect(ClientSessionAspect) {
        }
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
